package com.system.banking.service;

import com.system.banking.exceptions.AccountNumberNotFoundException;
import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.model.Transaction;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import com.system.banking.repo.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private CustomerRepository customerRepository;

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setup() {
        customerRepository = mock(CustomerRepository.class);
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
    }

    @Test
    void shouldBeAbleToPerformTransaction() throws AccountNumberNotFoundException {
        Customer customer = new Customer("Preeti", "Preeti@gmail.com", "1234567890", "1234", "xyz", "password");
        Account account = new Account(new BigDecimal(0), "Active", customer, new Date());
        customerRepository.save(customer);
        accountRepository.save(account);
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
        BigDecimal creditAmount = new BigDecimal(5);
        Transaction transaction = new Transaction("CREDIT", creditAmount, account);
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository,customerRepository);

        transactionService.performTransaction(transaction.getTransactionType(), transaction.getAmount(), transaction.getAccount().getAccountNumber());

        verify(transactionRepository).save(transaction);
    }

    @Test
    void shouldBeAbleToIncreaseInCurrentWhenThereIsAnyCredit() throws AccountNumberNotFoundException {
        Customer customer = new Customer("Preeti", "Preeti@gmail.com", "1234567890", "1234", "xyz", "password");
        Account account = new Account(new BigDecimal(5), "Active", customer, new Date());
        customerRepository.save(customer);
        accountRepository.save(account);
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
        BigDecimal creditAmount = new BigDecimal(5);
        BigDecimal expectedBalance = new BigDecimal(10);
        Transaction transaction = new Transaction("CREDIT", creditAmount, account);
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository,customerRepository);

        transactionService.performTransaction(transaction.getTransactionType(), transaction.getAmount(), transaction.getAccount().getAccountNumber());

        assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    void shouldBeAbleToDecreaseInCurrentWhenThereIsAnyDebit() throws AccountNumberNotFoundException {
        Customer customer = new Customer("Preeti", "Preeti@gmail.com", "1234567890", "1234", "xyz", "password");
        Account account = new Account(new BigDecimal(5), "Active", customer, new Date());
        customerRepository.save(customer);
        accountRepository.save(account);
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
        BigDecimal creditAmount = new BigDecimal(5);
        BigDecimal expectedBalance = new BigDecimal(0);
        Transaction transaction = new Transaction("DEBIT", creditAmount, account);
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository,customerRepository);

        transactionService.performTransaction(transaction.getTransactionType(), transaction.getAmount(), transaction.getAccount().getAccountNumber());

        assertEquals(expectedBalance, account.getBalance());
    }
}
