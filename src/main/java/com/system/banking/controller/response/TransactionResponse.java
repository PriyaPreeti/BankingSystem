package com.system.banking.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class TransactionResponse {

    private Long id;
    private String transactionType;
    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponse that = (TransactionResponse) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
