package com.system.banking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    private Date transactionDate;

    public Transaction(String transactionType, BigDecimal amount, Account account,Date transactionDate) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.account = account;
        this.transactionDate=transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAccount(Long account_number) {
        this.account = account;
    }
}
