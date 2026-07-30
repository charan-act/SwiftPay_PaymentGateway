package com.Swifty.transaction_gateway.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class PaymentRequest {

    @NotNull(message = "Sender Id is required")
    private Long senderId;

    @NotNull(message = "Receiver Id is required")
    private Long receiverId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.00", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    private String currency;
}
