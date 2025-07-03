package com.parkmate.paymentservice.payments.vo.request;

import lombok.Getter;

@Getter
public class PaymentRequestVo {

    private String paymentKey;
    private String orderId;
    private Long totalAmount;

}
