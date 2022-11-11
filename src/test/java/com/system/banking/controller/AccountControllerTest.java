package com.system.banking.controller;

import com.system.banking.BankingApplication;
import com.system.banking.controller.request.SignUpRequest;
import com.system.banking.model.Customer;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import com.system.banking.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = BankingApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountControllerTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    AccountService accountService;


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

        accountService = mock(AccountService.class);
    }


    @Test
    void shouldBeAbleToCreateAccountSuccessfully() throws IOException {
        SignUpRequest signupRequest = new SignUpRequest("Preeti", "Priya1@example.com", "9999999999", "011", "bihar", "preeti@123");
        AccountController accountController = new AccountController(accountService);
        Customer customer = new Customer(signupRequest.getName(), signupRequest.getEmail(), signupRequest.getMobileNumber(), signupRequest.getIdentityCard(), signupRequest.getAddress(), signupRequest.getPassword()
        );
        when(accountService.getCustomer(signupRequest.getEmail())).thenReturn(customer);

        accountController.save(signupRequest);

        verify(accountService).createAccount(customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getIdentityCard(), customer.getAddress(), customer.getPassword());
    }


}
