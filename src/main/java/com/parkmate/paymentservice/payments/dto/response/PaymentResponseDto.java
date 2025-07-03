package com.parkmate.paymentservice.payments.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponseDto {

    private String paymentKey;
    private String orderId;
    private Long totalAmount;
    private String status; //결제 상태
    private String method; //결제 수단
    private ZonedDateTime requestedAt;
    private ZonedDateTime approvedAt;

}
