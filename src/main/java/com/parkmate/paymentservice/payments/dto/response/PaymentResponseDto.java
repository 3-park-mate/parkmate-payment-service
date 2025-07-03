package com.parkmate.paymentservice.payments.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkmate.paymentservice.payments.domain.PGProvider;
import com.parkmate.paymentservice.payments.domain.Payment;
import com.parkmate.paymentservice.payments.domain.PaymentMethod;
import com.parkmate.paymentservice.payments.domain.PaymentStatus;
import com.parkmate.paymentservice.payments.dto.request.PaymentRequestDto;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponseDto {

    private String paymentKey;
    private String orderId;
    private Long totalAmount;
    private String status; //결제 상태 (READY, IN_PROGRESS, DONE 등)
    private String method; //결제 수단 (카드 ,가상계좌, 간편결제 등)
    private ZonedDateTime requestedAt;
    private ZonedDateTime approvedAt;

    public Payment toEntity(PaymentResponseDto paymentResponseDto,
                            PaymentRequestDto paymentRequestDto) {
        return Payment.builder()
                .paymentCode(UUID.randomUUID().toString())
                .paymentKey(paymentResponseDto.getPaymentKey())
                .orderId((paymentResponseDto.getOrderId()))
                .totalAmount(paymentResponseDto.getTotalAmount())
                .userUuid(paymentRequestDto.getUserUuid())
                .paymentMethod(PaymentMethod.fromString(paymentResponseDto.getMethod()))
                .pgProvider(PGProvider.TOSS)
                .paymentStatus(PaymentStatus.fromString(paymentResponseDto.getStatus()))
                .requestedAt(paymentResponseDto.getRequestedAt())
                .approvedAt(paymentResponseDto.getApprovedAt())
                .build();
    }

}
