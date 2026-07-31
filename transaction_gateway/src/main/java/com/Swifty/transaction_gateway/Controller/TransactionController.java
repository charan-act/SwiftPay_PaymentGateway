package com.Swifty.transaction_gateway.Controller;

import com.Swifty.transaction_gateway.dto.PaymentRequest;
import com.Swifty.transaction_gateway.dto.PaymentResponse;
import com.Swifty.transaction_gateway.Service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "Transaction APIs",
        description = "Operations related to payment transactions"
)
@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    @Operation(
            summary = "Create Transaction",
            description = "Creates a new payment transaction"
    )

    @PostMapping
    public ResponseEntity<PaymentResponse> createTransaction(
            @Valid @RequestBody PaymentRequest request) {
       log.info("TransactionController.createTransaction {}",request);
        PaymentResponse response = transactionService.createTransaction(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get Transaction",
            description = "Fetch transaction details using transaction ID"
    )
    @GetMapping("/{transactionId}")
    public ResponseEntity<PaymentResponse> getTransactionByTransactionId(
            @PathVariable String transactionId) {

        PaymentResponse response =
                transactionService.getTransactionByTransactionId(transactionId);

        return ResponseEntity.ok(response);
    }
}