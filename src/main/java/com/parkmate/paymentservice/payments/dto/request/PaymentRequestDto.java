package com.parkmate.paymentservice.payments.dto.request;

import com.parkmate.paymentservice.payments.vo.request.PaymentRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
