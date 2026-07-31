package com.Swifty.transaction_gateway.consumer;

import com.Swifty.transaction_gateway.event.PaymentInitiatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentConsumer.class);

    @KafkaListener(
            topics = "payment-initiated-topic",
            groupId = "transaction-group"
    )
    public void consume(PaymentInitiatedEvent event) {

        logger.info("======================================");
        logger.info("Payment Event Received");
        logger.info("Transaction Id : {}", event.getTransactionId());
        logger.info("Sender Id      : {}", event.getSenderId());
        logger.info("Receiver Id    : {}", event.getReceiverId());
        logger.info("Amount         : {}", event.getAmount());
        logger.info("Currency       : {}", event.getCurrency());
        logger.info("======================================");

    }
}