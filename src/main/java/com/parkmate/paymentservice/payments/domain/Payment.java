package com.parkmate.paymentservice.payments.domain;

import com.parkmate.paymentservice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("결제 코드(서버에서 생성), UUID")
    @Column(name = "payment_code", nullable = false)
    private String paymentCode;

    @Comment("[토스]결제 키")
    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Comment("[토스]주문 아이디")
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Comment("[토스]금액")
    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Comment("유저 UUID")
    @Column(name = "user_uuid", nullable = false, length = 36)
    private String userUuid;

    @Comment("호스트 UUID")
    @Column(name = "host_uuid", nullable = false, length = 36)
    private String hostUuid;

    @Comment("주차장 UUID") // ✅ 추가된 부분
    @Column(name = "parking_lot_uuid", nullable = false, length = 36)
    private String parkingLotUuid;

    @Enumerated(EnumType.STRING)
    @Comment("결제 방식")
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Comment("PG사 종류")
    @Column(name = "pg_provider", nullable = false)
    private PGProvider pgProvider;

    @Enumerated(EnumType.STRING)
    @Comment("결제 상태")
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "requested_at")
    private ZonedDateTime requestedAt;

    @Column(name = "approved_at")
    private ZonedDateTime approvedAt;

    @Builder
    private Payment(Long id, String paymentCode, String paymentKey, String orderId, Long totalAmount, String userUuid, String hostUuid, String parkingLotUuid, PaymentMethod paymentMethod, PGProvider pgProvider, PaymentStatus paymentStatus, ZonedDateTime requestedAt, ZonedDateTime approvedAt) {
        this.id = id;
        this.paymentCode = paymentCode;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.userUuid = userUuid;
        this.hostUuid = hostUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.paymentMethod = paymentMethod;
        this.pgProvider = pgProvider;
        this.paymentStatus = paymentStatus;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }

}
