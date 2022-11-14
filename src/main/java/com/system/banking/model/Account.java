package com.system.banking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "account")
@NoArgsConstructor
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String accountStatus;


    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false)
    private Date createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Account(BigDecimal balance, String accountStatus, Customer customer, Date creationDate) {
        this.balance = balance;
        this.accountStatus = accountStatus;
        this.customer = customer;
        this.createdAt = creationDate;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCreationDate(Date creationDate) {
        this.createdAt = creationDate;
    }
}
