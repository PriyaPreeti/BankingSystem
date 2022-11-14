package com.system.banking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.banking.BankingApplication;
import com.system.banking.controller.request.TransactionRequest;
import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import com.system.banking.repo.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BankingApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
public class TransactionControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    public void before() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();

    }

    @AfterEach
    public void after() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();

    }

    @Test
    void shouldBeAbleToInvokeTransactionApiWhenCustomerWantCreditOrDebitMoney() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Customer customer = new Customer("preeti", "Priya@gmail.com", "1234", "12345667", "abc", encoder.encode("password"));
        Customer savedCustomer = customerRepository.save(customer);
        Account account = new Account(new BigDecimal(0), "ACTIVE", savedCustomer, new Date());
        Account savedAccount = accountRepository.save(account);
        TransactionRequest transactionRequest = new TransactionRequest("CREDIT", new BigDecimal(0), savedAccount.getAccountNumber());
        String requestJson = new ObjectMapper().writeValueAsString(transactionRequest);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldThrowErrorWhenAccountNumberIsNotCorrect() throws Exception {
        mockMvc.perform(post("/transaction"))
                .andExpect(status().isBadRequest());
    }
}
