package com.parkmate.paymentservice.payments.application;

import com.parkmate.paymentservice.payments.dto.request.PaymentRequestDto;

public interface PaymentService {

    void confirmPayment(PaymentRequestDto paymentRequestDto);

}
