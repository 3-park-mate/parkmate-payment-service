package com.parkmate.paymentservice.payments.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostParkingLotDto {

    private String hostUuid;
    private String parkingLotUuid;

    @Builder
    private HostParkingLotDto(String hostUuid, String parkingLotUuid) {
        this.hostUuid = hostUuid;
        this.parkingLotUuid = parkingLotUuid;
    }
}