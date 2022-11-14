package com.system.banking.controller.request;

import com.system.banking.model.TransactionType;
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
    private TransactionType transactionType;
    private BigDecimal amount;
}
