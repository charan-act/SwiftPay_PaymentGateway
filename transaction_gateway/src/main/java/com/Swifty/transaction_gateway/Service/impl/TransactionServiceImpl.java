package com.Swifty.transaction_gateway.Service.impl;

import com.Swifty.transaction_gateway.Entity.Transaction;
import com.Swifty.transaction_gateway.Repository.TransactionRepository;
import com.Swifty.transaction_gateway.Service.TransactionService;
import com.Swifty.transaction_gateway.dto.PaymentRequest;
import com.Swifty.transaction_gateway.dto.PaymentResponse;
import com.Swifty.transaction_gateway.enums.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public PaymentResponse createTransaction(PaymentRequest request) {

        // Create Transaction Entity
        Transaction transaction = Transaction.builder()
                .transactionId(generateTransactionId())
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(TransactionStatus.PENDING)
                .build();

        // Save to Database
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Convert Entity to Response DTO
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

    /**
     * Generates a unique transaction ID.
     */
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
