package com.parkmate.paymentservice.payments.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.parkmate.paymentservice.common.config.TossPaymentConfig;
import com.parkmate.paymentservice.payments.domain.Payment;
import com.parkmate.paymentservice.payments.dto.request.PaymentRequestDto;
import com.parkmate.paymentservice.payments.dto.response.PaymentResponseDto;
import com.parkmate.paymentservice.payments.infrastructure.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final TossPaymentConfig tossPaymentConfig;
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;

    private final RestTemplate restTemplate;

    @Transactional
    @Override
    public void confirmPayment(PaymentRequestDto paymentRequestDto) {

        HttpHeaders headers = tossPaymentConfig.getHeaders();

        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", paymentRequestDto.getPaymentKey());
        body.put("orderId", paymentRequestDto.getOrderId());
        body.put("amount", paymentRequestDto.getAmount());

        log.info(">>> [TOSS 요청 준비]");
        log.info(">>> paymentKey: {}", paymentRequestDto.getPaymentKey());
        log.info(">>> orderId: {}", paymentRequestDto.getOrderId());
        log.info(">>> amount: {}", paymentRequestDto.getAmount());

        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            log.info(">>> JSON Body: {}", jsonBody);
            log.info(">>> Headers: {}", headers.toSingleValueMap());
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

        } catch (HttpClientErrorException e) {
            log.error(">>> Toss API Error Body: {}", e.getResponseBodyAsString());
            log.error(">>> Toss API Error Status: {}", e.getStatusCode());
        }


    }

}
