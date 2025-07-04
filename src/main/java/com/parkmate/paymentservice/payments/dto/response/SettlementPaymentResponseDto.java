package com.parkmate.paymentservice.payments.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class SettlementPaymentResponseDto {

    private String userUuid;
    private String hostUuid;
    private Long totalAmount;
    private ZonedDateTime approvedAt;

    @Builder
    private SettlementPaymentResponseDto(String userUuid,
                                         String hostUuid,
                                         Long totalAmount,
                                         ZonedDateTime approvedAt) {
        this.userUuid = userUuid;
        this.hostUuid = hostUuid;
        this.totalAmount = totalAmount;
        this.approvedAt = approvedAt;
    }
}

