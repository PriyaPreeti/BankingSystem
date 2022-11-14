package com.system.banking.service;

import com.system.banking.controller.response.TransactionResponse;
import com.system.banking.controller.response.TransactionStatementResponse;
import com.system.banking.exceptions.AccountNumberNotFoundException;
import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.model.Transaction;
import com.system.banking.model.TransactionType;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import com.system.banking.repo.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private CustomerRepository customerRepository;

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    private AccountService accountService;

    private CustomerPrincipalService customerPrincipalService;

    @BeforeEach
    public void setup() {
        customerRepository = mock(CustomerRepository.class);
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        accountService = mock(AccountService.class);
        customerPrincipalService = mock(CustomerPrincipalService.class);
    }

    @Test
    void shouldBeAbleToPerformTransaction() throws AccountNumberNotFoundException {
        Customer customer = new Customer("Preeti", "Preeti@gmail.com", "1234567890", "1234", "xyz", "password");
        Account account = new Account(new BigDecimal(0), "Active", customer, new Date());
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(customerPrincipalService.getCustomer(customer.getEmail())).thenReturn(customer);
        when(accountService.getAccount(account.getId())).thenReturn(account);
        when(accountService.findAccountByCustomer(customer.getId())).thenReturn(account);

        BigDecimal creditAmount = new BigDecimal(5);
        Transaction transaction = new Transaction(TransactionType.CREDIT.toString(), creditAmount, account, new Date());
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository, customerRepository, accountService);

        transactionService.performTransaction(customer.getEmail(), TransactionType.CREDIT, transaction.getAmount());

        verify(transactionRepository).save(transaction);
    }

    @Test
    void shouldBeAbleToIncreaseInCurrentBalanceWhenThereIsAnyCredit() throws AccountNumberNotFoundException {
        Customer customer = new Customer("Preeti", "Preeti@gmail.com", "1234567890", "1234", "xyz", "password");
        Account account = new Account(new BigDecimal(5), "Active", customer, new Date());
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(customerPrincipalService.getCustomer(customer.getEmail())).thenReturn(customer);
        when(accountService.getAccount(account.getId())).thenReturn(account);
        when(accountService.findAccountByCustomer(customer.getId())).thenReturn(account);
        BigDecimal creditAmount = new BigDecimal(5);
        BigDecimal expectedBalance = new BigDecimal(10);
        Transaction transaction = new Transaction(TransactionType.CREDIT.toString(), creditAmount, account, new Date());
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository, customerRepository, accountService);

        transactionService.performTransaction(customer.getEmail(), TransactionType.CREDIT, transaction.getAmount());

        assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    void shouldBeAbleToDecreaseInCurrentBalanceWhenThereIsAnyDebit() throws AccountNumberNotFoundException {
        Customer customer = new Customer("Preeti", "Preeti@gmail.com", "1234567890", "1234", "xyz", "password");
        Account account = new Account(new BigDecimal(5), "Active", customer, new Date());
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(customerPrincipalService.getCustomer(customer.getEmail())).thenReturn(customer);
        when(accountService.getAccount(account.getId())).thenReturn(account);
        when(accountService.findAccountByCustomer(customer.getId())).thenReturn(account);
        BigDecimal creditAmount = new BigDecimal(5);
        BigDecimal expectedBalance = new BigDecimal(0);
        Transaction transaction = new Transaction(TransactionType.DEBIT.toString(), creditAmount, account, new Date());
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository, customerRepository, accountService);

        transactionService.performTransaction(customer.getEmail(), TransactionType.DEBIT, transaction.getAmount());

        assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    void shouldBeAbleToGetStatement() throws AccountNumberNotFoundException {
        Customer customer = new Customer("Preeti", "Preeti@gmail.com", "1234567890", "1234", "xyz", "password");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        Account account = new Account(new BigDecimal(5), "Active", customer, new Date());
        when(accountService.getAccount(account.getId())).thenReturn(account);
        when(accountService.findAccountByCustomer(customer.getId())).thenReturn(account);
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository, customerRepository, accountService);
        Transaction transaction1 = new Transaction(TransactionType.CREDIT.toString(), new BigDecimal(20), account, new Date());
        Transaction transaction2 = new Transaction("DEBIT", new BigDecimal(5), account, new Date());
        transactionService.performTransaction(customer.getEmail(), TransactionType.CREDIT, transaction1.getAmount());
        transactionService.performTransaction(customer.getEmail(), TransactionType.DEBIT, transaction2.getAmount());
        List<TransactionResponse> transactions = new ArrayList<>();
        TransactionResponse transactionResponse = new TransactionResponse(transaction1.getId(), transaction1.getTransactionType(), transaction1.getAmount());
        TransactionResponse transactionResponse1 = new TransactionResponse(transaction2.getId(), transaction2.getTransactionType(), transaction2.getAmount());
        transactions.add(transactionResponse1);
        transactions.add(transactionResponse);

        TransactionStatementResponse expectedStatement = new TransactionStatementResponse(account.getId(), customer.getName(), account.getBalance(), transactions);

        TransactionStatementResponse actualStatement = transactionService.getStatement(customer.getEmail());

        assertEquals(expectedStatement, actualStatement);
    }
}
