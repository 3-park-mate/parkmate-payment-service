package com.parkmate.paymentservice.payments.domain;

import lombok.Getter;

@Getter
public enum PaymentType {

    PG("피지사"),
    POINT("포인트");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

}
