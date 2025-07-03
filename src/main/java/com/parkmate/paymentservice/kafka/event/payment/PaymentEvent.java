package com.parkmate.paymentservice.kafka.event.payment;

import com.parkmate.paymentservice.payments.domain.Payment;
import com.parkmate.paymentservice.payments.domain.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PaymentEvent {

    private PaymentStatus eventType;
    private String paymentCode;
    private String orderCode;
    private long amount;

    @Builder
    private PaymentEvent(PaymentStatus eventType, String paymentCode, String orderCode, long amount) {
        this.eventType = eventType;
        this.paymentCode = paymentCode;
        this.orderCode = orderCode;
        this.amount = amount;
    }

    public static PaymentEvent from(Payment payment) {
        return PaymentEvent.builder()
                .eventType(payment.getPaymentStatus())
                .paymentCode(payment.getPaymentCode())
                .orderCode(payment.getOrderId())
                .amount(payment.getTotalAmount())
                .build();
    }

}