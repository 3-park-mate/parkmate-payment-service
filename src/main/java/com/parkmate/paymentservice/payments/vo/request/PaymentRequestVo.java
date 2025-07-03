package com.parkmate.paymentservice.payments.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestVo {

    private String paymentKey;
    private String orderId;
    private Long amount;

}
