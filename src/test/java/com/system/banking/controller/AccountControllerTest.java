package com.system.banking.controller;

import com.system.banking.controller.request.SignUpRequest;
import com.system.banking.exceptions.EmailIdAlreadyRegisteredException;
import com.system.banking.model.Customer;
import com.system.banking.service.AccountService;
import com.system.banking.service.CustomerPrincipalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.Principal;

import static org.mockito.Mockito.*;

public class AccountControllerTest {
    Principal principal;

    AccountService accountService;

    CustomerPrincipalService customerPrincipalService;

    @BeforeEach
    void setUp() {

        accountService = mock(AccountService.class);
        customerPrincipalService = mock(CustomerPrincipalService.class);
        principal = mock(Principal.class);
    }


    @Test
    void shouldBeAbleToCreateAccountSuccessfully() throws IOException, EmailIdAlreadyRegisteredException {
        SignUpRequest signupRequest = new SignUpRequest("Preeti", "Priya1@example.com", "9999999999", "011", "bihar", "preeti@123");
        AccountController accountController = new AccountController(accountService);
        Customer customer = new Customer(signupRequest.getName(), signupRequest.getEmail(), signupRequest.getMobileNumber(), signupRequest.getIdentityCard(), signupRequest.getAddress(), signupRequest.getPassword()
        );
        when(customerPrincipalService.getCustomer(signupRequest.getEmail())).thenReturn(customer);

        accountController.save(signupRequest);

        verify(accountService).createAccount(customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getIdentityCard(), customer.getAddress(), customer.getPassword());
    }

    @Test
    void shouldBeAbleToGetSummaryOfParticularCustomer() {
        AccountController accountController = new AccountController(accountService);
        when(principal.getName()).thenReturn("Preeti");
        accountController.getSummary(principal);

        verify(accountService).getSummary(principal.getName());
    }
}
