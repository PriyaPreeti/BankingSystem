package com.system.banking.service;

import com.system.banking.exceptions.AccountNumberNotFoundException;
import com.system.banking.model.Account;
import com.system.banking.model.Transaction;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import com.system.banking.repo.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    @Transactional
    public void performTransaction(String transactionType, BigDecimal amount, long accountNumber) throws AccountNumberNotFoundException {
        AccountService accountService = new AccountService(accountRepository, customerRepository);
        Account fetchedAccount = accountService.getAccount(accountNumber);
        Transaction transaction = new Transaction(transactionType, amount, fetchedAccount);
        transactionRepository.save(transaction);
        if (transactionType.equals("CREDIT")) fetchedAccount.setBalance(fetchedAccount.getBalance().add(amount));
        else fetchedAccount.setBalance(fetchedAccount.getBalance().subtract(amount));

    }
}
