package com.parkmate.paymentservice.payments.application;

import com.parkmate.paymentservice.payments.dto.request.PaymentRequestDto;
import com.parkmate.paymentservice.payments.dto.response.HostParkingLotDto;
import com.parkmate.paymentservice.payments.dto.response.SettlementPaymentResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {

    void confirmPayment(PaymentRequestDto paymentRequestDto);

    List<SettlementPaymentResponseDto> getSettlementPayments(
            String hostUuid,
            String parkingLotUuid,
            LocalDate startDate,
            LocalDate endDate
    );

    List<HostParkingLotDto> getHostParkingLotPairsByDate(LocalDate targetDate);

    List<HostParkingLotDto> getHostParkingLotPairsBetween(LocalDate startDate, LocalDate endDate);
}
