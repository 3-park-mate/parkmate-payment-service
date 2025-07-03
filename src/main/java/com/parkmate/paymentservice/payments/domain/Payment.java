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

//    @Comment("결제 코드(서버에서 생성)")
//    @Column(name = "payment_code", nullable = false)
//    private String paymentCode;

    @Comment("결제 코드(서버에서 생성)")
    @Column(name = "payment_UUID", nullable = false)
    private String paymentUuid;

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

    @Enumerated(EnumType.STRING)
    @Comment("결제 타입")
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

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

    private ZonedDateTime requestedAt;

    private ZonedDateTime approvedAt;

    @Builder
    private Payment(Long id, String paymentUuid, String paymentKey, String orderId, Long totalAmount, String userUuid, PaymentType paymentType, PaymentMethod paymentMethod, PGProvider pgProvider, PaymentStatus paymentStatus, ZonedDateTime requestedAt, ZonedDateTime approvedAt) {
        this.id = id;
        this.paymentUuid = paymentUuid;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.userUuid = userUuid;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.pgProvider = pgProvider;
        this.paymentStatus = paymentStatus;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }

}
