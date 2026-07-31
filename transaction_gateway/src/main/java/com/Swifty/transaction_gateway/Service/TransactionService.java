package com.Swifty.transaction_gateway.Service;

import com.Swifty.transaction_gateway.dto.PaymentRequest;
import com.Swifty.transaction_gateway.dto.PaymentResponse;

public interface TransactionService {
    PaymentResponse createTransaction(PaymentRequest request);
    PaymentResponse getTransactionByTransactionId(String transactionId);

}
