package com.parkmate.paymentservice.kafka.producer;

import com.parkmate.paymentservice.kafka.constant.KafkaTopics;
import com.parkmate.paymentservice.kafka.event.payment.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    private final KafkaTemplate<String, PaymentEvent> paymentKafkaTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void send(PaymentEvent event) {
        log.info("event{}", event);
        paymentKafkaTemplate.send(KafkaTopics.PAYMENT_TOPIC, event.getPaymentCode(), event);
    }

}