package com.parkmate.paymentservice.payments.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.parkmate.paymentservice.common.config.TossPaymentConfig;
import com.parkmate.paymentservice.kafka.event.payment.PaymentEvent;
import com.parkmate.paymentservice.payments.domain.Payment;
import com.parkmate.paymentservice.payments.domain.PaymentStatus;
import com.parkmate.paymentservice.payments.dto.request.PaymentRequestDto;
import com.parkmate.paymentservice.payments.dto.response.HostParkingLotDto;
import com.parkmate.paymentservice.payments.dto.response.PaymentResponseDto;
import com.parkmate.paymentservice.payments.dto.response.SettlementPaymentResponseDto;
import com.parkmate.paymentservice.payments.infrastructure.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final TossPaymentConfig tossPaymentConfig;
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    @Override
    public void confirmPayment(PaymentRequestDto paymentRequestDto) {

        HttpHeaders headers = tossPaymentConfig.getHeaders();

        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", paymentRequestDto.getPaymentKey());
        body.put("orderId", paymentRequestDto.getOrderId());
        body.put("amount", paymentRequestDto.getAmount());

        try {
            String jsonBody = objectMapper.writeValueAsString(body);

        } catch (Exception ex) {
            log.warn(">>> JSON 직렬화 실패: {}", ex.getMessage());

        }

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    tossPaymentConfig.getBaseUrl() + "/confirm",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new ParameterNamesModule());
            mapper.registerModule(new Jdk8Module());
            mapper.registerModule(new JavaTimeModule());

            PaymentResponseDto paymentResponseDto = mapper.convertValue(response.getBody(), PaymentResponseDto.class);

            Payment payment = paymentResponseDto.toEntity(paymentResponseDto, paymentRequestDto);

            paymentRepository.save(payment);

            eventPublisher.publishEvent(PaymentEvent.from(payment));

        } catch (HttpClientErrorException e) {
            log.error(">>> Toss API Error Body: {}", e.getResponseBodyAsString());
            log.error(">>> Toss API Error Status: {}", e.getStatusCode());
        }


    }

    @Transactional
    @Override
    public List<SettlementPaymentResponseDto> getSettlementPayments(String hostUuid, String parkingLotUuid,
                                                                    LocalDate startDate, LocalDate endDate) {
        ZonedDateTime startDateTime = startDate.atStartOfDay().atZone(ZonedDateTime.now().getZone());
        ZonedDateTime endDateTime = endDate.plusDays(1).atStartOfDay().atZone(ZonedDateTime.now().getZone());

        List<Payment> payments = paymentRepository.findByHostUuidAndParkingLotUuidAndApprovedAtBetweenAndPaymentStatus(
                hostUuid,
                parkingLotUuid,
                startDateTime,
                endDateTime,
                PaymentStatus.DONE
        );

        return payments.stream()
                .map(p -> SettlementPaymentResponseDto.builder()
                        .userUuid(p.getUserUuid())
                        .hostUuid(p.getHostUuid())
                        .totalAmount(p.getTotalAmount())
                        .approvedAt(p.getApprovedAt())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<HostParkingLotDto> getHostParkingLotPairsByDate(LocalDate targetDate) {
        ZonedDateTime startOfDay = targetDate.atStartOfDay().atZone(ZonedDateTime.now().getZone());
        ZonedDateTime endOfDay = targetDate.plusDays(1).atStartOfDay().atZone(ZonedDateTime.now().getZone());

        return paymentRepository.findDistinctPairsBetweenWithStatus(startOfDay, endOfDay, PaymentStatus.DONE);
    }

    @Transactional
    @Override
    public List<HostParkingLotDto> getHostParkingLotPairsBetween(LocalDate startDate, LocalDate endDate) {
        ZonedDateTime startDateTime = startDate.atStartOfDay().atZone(ZonedDateTime.now().getZone());
        ZonedDateTime endDateTime = endDate.plusDays(1).atStartOfDay().atZone(ZonedDateTime.now().getZone());

        return paymentRepository.findDistinctPairsBetweenWithStatus(startDateTime, endDateTime, PaymentStatus.DONE);
    }

}
