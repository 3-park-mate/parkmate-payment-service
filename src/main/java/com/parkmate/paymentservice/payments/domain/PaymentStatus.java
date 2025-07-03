package com.parkmate.paymentservice.payments.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.parkmate.paymentservice.common.exception.BaseException;
import com.parkmate.paymentservice.common.response.ResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

    READY("READY"),
    IN_PROGRESS("IN_PROGRESS"),
    WAITING_FOR_DEPOSIT("WAITING_FOR_DEPOSIT"),
    DONE("DONE"),
    CANCELED("CANCELED"),
    ABORTED("ABORTED"),
    EXPIRED("EXPIRED");

    private final String paymentStatus;

    @JsonValue
    public String getPaymentStatus() { return paymentStatus; }

    @JsonCreator
    public static PaymentStatus fromString(String value) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.paymentStatus.equals(value)) {
                return paymentStatus;
            }
        }
        throw new BaseException(ResponseStatus.RESOURCE_NOT_FOUND);
    }
}
