package com.Swifty.transaction_gateway.Exceptions;

public class DuplicateTransactionException extends RuntimeException {
    public DuplicateTransactionException(String message) {
        super(message);
    }

}
