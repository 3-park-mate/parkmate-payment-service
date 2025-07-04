package com.parkmate.paymentservice.payments.presentation;

import com.parkmate.paymentservice.payments.application.PaymentService;
import com.parkmate.paymentservice.payments.dto.response.HostParkingLotDto;
import com.parkmate.paymentservice.payments.dto.response.SettlementPaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/settlements")
public class PaymentInternalController {

    private final PaymentService paymentService;

    @GetMapping
    public List<SettlementPaymentResponseDto> getSettlementPayments(
            @RequestHeader("X-Host-UUID") String hostUuid,
            @RequestParam String parkingLotUuid,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return paymentService.getSettlementPayments(
                hostUuid,
                parkingLotUuid,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }

    @GetMapping("/pairs/by-date")
    public List<HostParkingLotDto> getPairsByDate(@RequestParam String date) {
        return paymentService.getHostParkingLotPairsByDate(LocalDate.parse(date));
    }

    @GetMapping("/pairs/by-range")
    public List<HostParkingLotDto> getPairsByRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return paymentService.getHostParkingLotPairsBetween(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }
}