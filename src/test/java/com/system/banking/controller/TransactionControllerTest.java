package com.system.banking.controller;

import com.system.banking.controller.request.TransactionRequest;
import com.system.banking.exceptions.AccountNumberNotFoundException;
import com.system.banking.model.TransactionType;
import com.system.banking.service.AccountService;
import com.system.banking.service.CustomerPrincipalService;
import com.system.banking.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;

import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    TransactionService transactionService;

    AccountService accountService;

    CustomerPrincipalService customerPrincipalService;
    //    @Mock
    Principal principal;

    @BeforeEach
    void setUp() {

        transactionService = mock(TransactionService.class);
        accountService = mock(AccountService.class);
        customerPrincipalService = mock(CustomerPrincipalService.class);
        principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Preeti");
    }

    @Test
    void shouldBeAbleToPerformTransactionWhenAccountIsCreated() throws IOException, AccountNumberNotFoundException {
        TransactionRequest transactionRequest = new TransactionRequest(TransactionType.CREDIT, new BigDecimal(5));
        TransactionController transactionController = new TransactionController(transactionService);

        transactionController.performTransaction(principal, transactionRequest);

        verify(transactionService).performTransaction(principal.getName(), TransactionType.CREDIT, transactionRequest.getAmount());
    }

    @Test
    void shouldBeAbleToGetStatement() {
        TransactionController transactionController = new TransactionController(transactionService);

        transactionController.getStatement(principal);

        verify(transactionService).getStatement(principal.getName());
    }
}
