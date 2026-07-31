package com.Swifty.transaction_gateway.Service.impl;

import com.Swifty.transaction_gateway.Entity.Transaction;
import com.Swifty.transaction_gateway.Exceptions.DuplicateTransactionException;
import com.Swifty.transaction_gateway.Exceptions.ResourceNotFoundException;
import com.Swifty.transaction_gateway.Repository.TransactionRepository;
import com.Swifty.transaction_gateway.Service.RedisService;
import com.Swifty.transaction_gateway.Service.TransactionService;
import com.Swifty.transaction_gateway.dto.PaymentRequest;
import com.Swifty.transaction_gateway.dto.PaymentResponse;
import com.Swifty.transaction_gateway.enums.TransactionStatus;
import com.Swifty.transaction_gateway.event.PaymentInitiatedEvent;
import com.Swifty.transaction_gateway.producer.PaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final RedisService redisService;
    private final PaymentProducer paymentProducer;


    @Override
    public PaymentResponse createTransaction(
            String idempotencyKey,
            PaymentRequest request) {

        if (redisService.hasKey(idempotencyKey)) {
            throw new DuplicateTransactionException(
                    "Duplicate payment request detected.");
        }

        // Existing logic to create and save the transaction
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(TransactionStatus.PENDING)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);


        // Create Event

        PaymentInitiatedEvent event =
                PaymentInitiatedEvent.builder()
                        .transactionId(savedTransaction.getTransactionId())
                        .senderId(savedTransaction.getSenderId())
                        .receiverId(savedTransaction.getReceiverId())
                        .amount(savedTransaction.getAmount())
                        .currency(savedTransaction.getCurrency())
                        .build();

        // Publish to Kafka
        paymentProducer.publishPaymentInitiatedEvent(event);



        // Save the idempotency key in Redis
        redisService.saveKey(idempotencyKey);

        return PaymentResponse.builder()
                .transactionId(savedTransaction.getTransactionId())
                .senderId(savedTransaction.getSenderId())
                .receiverId(savedTransaction.getReceiverId())
                .amount(savedTransaction.getAmount())
                .currency(savedTransaction.getCurrency())
                .status(savedTransaction.getStatus())
                .createdAt(savedTransaction.getCreatedAt())
                .message("Transaction created successfully")
                .build();
    }




    @Override
    public PaymentResponse getTransactionByTransactionId(String transactionId) {

        Transaction transaction = transactionRepository
                .findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found with ID : " + transactionId));

        return PaymentResponse.builder()
                .transactionId(transaction.getTransactionId())
                .senderId(transaction.getSenderId())
                .receiverId(transaction.getReceiverId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .message("Transaction fetched successfully")
                .build();
    }
    /**
     * Generates a unique transaction ID.
     */
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
