package com.Swifty.transaction_gateway.dto;

import com.Swifty.transaction_gateway.enums.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String transactionId;

    private Long senderId;

    private Long receiverId;

    private BigDecimal amount;

    private String currency;

    private TransactionStatus status;

    private LocalDateTime createdAt;

    private String message;
}