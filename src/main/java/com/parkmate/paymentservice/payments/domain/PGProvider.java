package com.parkmate.paymentservice.payments.domain;

import lombok.Getter;

@Getter
public enum PGProvider {

    TOSS("토스"),
    KAKAO_BANK("카카오뱅크");

    private final String description;

    PGProvider(String description) {
        this.description = description;
    }
}
