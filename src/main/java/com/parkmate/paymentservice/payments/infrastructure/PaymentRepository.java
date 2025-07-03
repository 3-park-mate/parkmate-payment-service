package com.parkmate.paymentservice.payments.infrastructure;

import com.parkmate.paymentservice.payments.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
