package com.system.banking.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {
    private String transactionType;
    private BigDecimal amount;
    private Long accountNumber;
}
