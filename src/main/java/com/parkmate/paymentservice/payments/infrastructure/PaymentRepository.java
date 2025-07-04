package com.parkmate.paymentservice.payments.infrastructure;

import com.parkmate.paymentservice.payments.domain.Payment;
import com.parkmate.paymentservice.payments.domain.PaymentStatus;
import com.parkmate.paymentservice.payments.dto.response.HostParkingLotDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p " +
            "WHERE p.hostUuid = :hostUuid " +
            "AND p.parkingLotUuid = :parkingLotUuid " +
            "AND p.approvedAt >= :startDateTime " +
            "AND p.approvedAt < :endDateTime " +
            "AND p.paymentStatus = :paymentStatus")
    List<Payment> findByHostUuidAndParkingLotUuidAndApprovedAtBetweenAndPaymentStatus(
            @Param("hostUuid") String hostUuid,
            @Param("parkingLotUuid") String parkingLotUuid,
            @Param("startDateTime") ZonedDateTime startDateTime,
            @Param("endDateTime") ZonedDateTime endDateTime,
            @Param("paymentStatus") PaymentStatus paymentStatus
    );

    @Query("SELECT new com.parkmate.paymentservice.payments.dto.response.HostParkingLotDto(p.hostUuid, p.parkingLotUuid) " +
            "FROM Payment p " +
            "WHERE p.approvedAt >= :startDateTime " +
            "AND p.approvedAt < :endDateTime " +
            "AND p.paymentStatus = :paymentStatus " +
            "GROUP BY p.hostUuid, p.parkingLotUuid")
    List<HostParkingLotDto> findDistinctPairsBetweenWithStatus(
            @Param("startDateTime") ZonedDateTime startDateTime,
            @Param("endDateTime") ZonedDateTime endDateTime,
            @Param("paymentStatus") PaymentStatus paymentStatus
    );
}
