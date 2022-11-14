package com.system.banking.model;

import java.math.BigDecimal;

public enum TransactionType {
    CREDIT(new BigDecimal(1)), DEBIT(new BigDecimal(-1));

    private BigDecimal multiplicationFactor;

    TransactionType(BigDecimal multiplicationFactor) {
        this.multiplicationFactor = multiplicationFactor;
    }

    public BigDecimal getMultiplicationFactor() {
        return multiplicationFactor;
    }
}
