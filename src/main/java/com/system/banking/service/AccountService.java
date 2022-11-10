package com.system.banking.service;

import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    public void createAccount(BigDecimal balance, String account_status, String identityCardNumber, Date creationDate) {
        Customer customer = customerRepository.findByIdentityCard(identityCardNumber);
        Account account = new Account(balance, account_status, customer, creationDate);
        accountRepository.save(account);
    }
}
