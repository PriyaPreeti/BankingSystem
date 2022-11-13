package com.system.banking.controller;

import com.system.banking.BankingApplication;
import com.system.banking.controller.request.TransactionRequest;
import com.system.banking.exceptions.AccountNumberNotFoundException;
import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.model.Transaction;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import com.system.banking.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = BankingApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransactionControllerTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    TransactionService transactionService;


    @BeforeEach
    public void before() {
        accountRepository.deleteAll();
        customerRepository.deleteAll();

    }

    @AfterEach
    public void after() {
        accountRepository.deleteAll();
        customerRepository.deleteAll();

    }

    @BeforeEach
    void setUp() {

        transactionService = mock(TransactionService.class);
    }

    @Test
    void shouldBeAbleToPerformTransactionWhenAccountIsCreated() throws IOException, AccountNumberNotFoundException {
        Customer customer = new Customer("Preeti", "Preeti@gmail.com", "1234567890", "1234", "xyz", "password");
        Account account = new Account(new BigDecimal(0), "Active", customer, new Date());
        TransactionRequest transactionRequest = new TransactionRequest("CREDIT", new BigDecimal(5), account.getAccountNumber());
        Transaction transaction = new Transaction(transactionRequest.getTransactionType(), transactionRequest.getAmount(), account);
        TransactionController transactionController = new TransactionController(transactionService);

        transactionController.performTransaction(transactionRequest);

        verify(transactionService).performTransaction(transaction.getTransactionType(), transaction.getAmount(), transaction.getAccount().getAccountNumber());
    }
}
