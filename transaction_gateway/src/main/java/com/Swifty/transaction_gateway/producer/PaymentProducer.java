package com.Swifty.transaction_gateway.producer;

import com.Swifty.transaction_gateway.event.PaymentInitiatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "payment-initiated-topic";

    public void publishPaymentInitiatedEvent(
            PaymentInitiatedEvent event) {

        logger.info("Publishing Payment Initiated Event : {}", event);

        kafkaTemplate.send(TOPIC, event);

        logger.info("Event Published Successfully");
    }
}