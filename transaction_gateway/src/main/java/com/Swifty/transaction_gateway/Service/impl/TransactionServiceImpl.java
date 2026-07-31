package com.Swifty.transaction_gateway.Service.impl;

import com.Swifty.transaction_gateway.Entity.Transaction;
import com.Swifty.transaction_gateway.Exceptions.ResourceNotFoundException;
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
