package com.parkmate.paymentservice.payments.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.parkmate.paymentservice.common.exception.BaseException;
import com.parkmate.paymentservice.common.response.ResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {

    CARD("카드"),
    BANK_TRANSFER("계좌이체"),
    MOBILE("휴대폰"),
    VIRTUAL_ACCOUNT("가상계좌"),
    SIMPLE_PAYMENT("간편결제"),
    CULTURE_GIFT_CERTIFICATE("문화상품권"),
    BOOK_CULTURE_GIFT_CERTIFICATE("도서문화상품권"),
    GAME_CULTURE_GIFT_CERTIFICATE("게임문화상품권"),
    ;

    private final String paymentMethod;

    @JsonValue
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @JsonCreator
    public static PaymentMethod fromString(String value) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.paymentMethod.equals(value)) {
                return paymentMethod;
            }
        }
        throw new BaseException(ResponseStatus.RESOURCE_NOT_FOUND);
    }

}
