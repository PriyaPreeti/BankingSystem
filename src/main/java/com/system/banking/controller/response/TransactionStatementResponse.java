package com.system.banking.controller.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class TransactionStatementResponse {
    private Long accountNumber;
    private String name;
    private BigDecimal balance;
    private List<TransactionResponse> transactionResponse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionStatementResponse that = (TransactionStatementResponse) o;
        return accountNumber.equals(that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
