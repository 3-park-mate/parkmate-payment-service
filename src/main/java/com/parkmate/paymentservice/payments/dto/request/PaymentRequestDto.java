package com.parkmate.paymentservice.payments.dto.request;

import com.parkmate.paymentservice.payments.domain.Payment;
import com.parkmate.paymentservice.payments.domain.PaymentMethod;
import com.parkmate.paymentservice.payments.domain.PaymentStatus;
import com.parkmate.paymentservice.payments.dto.response.PaymentResponseDto;
import com.parkmate.paymentservice.payments.vo.request.PaymentRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {

    private String userUuid;
    private String paymentKey;
    private String orderId;
    private Long totalAmount;

    @Builder
    private PaymentRequestDto(String userUuid,
                              String paymentKey,
                              String orderId,
                              Long totalAmount) {
        this.userUuid = userUuid;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public static PaymentRequestDto of(String userUuid, PaymentRequestVo paymentRequestVo) {
        return PaymentRequestDto.builder()
                .userUuid(userUuid)
                .paymentKey(paymentRequestVo.getPaymentKey())
                .orderId(paymentRequestVo.getOrderId())
                .totalAmount(paymentRequestVo.getTotalAmount())
                .build();
    }

    public Payment toEntity(PaymentResponseDto paymentResponseDto) {
        return Payment.builder()
                .paymentUuid(UUID.randomUUID().toString())
                .userUuid(userUuid)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .totalAmount(totalAmount)
                .paymentStatus(PaymentStatus.fromString(paymentResponseDto.getStatus()))
                .paymentMethod(PaymentMethod.fromString(paymentResponseDto.getStatus()))
                .requestedAt(paymentResponseDto.getRequestedAt())
                .build();
    }

}
