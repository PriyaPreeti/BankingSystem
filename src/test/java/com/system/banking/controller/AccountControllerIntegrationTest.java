package com.system.banking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.banking.BankingApplication;
import com.system.banking.controller.request.SignUpRequest;
import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = BankingApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

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

    @Test
    void shouldBeAbleToCreateAccount() throws Exception {
        SignUpRequest signupRequest = new SignUpRequest("Preeti", "Priya1@example.com", "9999999999", "011", "bihar", "preeti@123");
        String requestJson = new ObjectMapper().writeValueAsString(signupRequest);

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());

    }

    @Test
    void shouldThrowExceptionWhenExistingEmailIdIsProvided() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Customer customer = new Customer("preeti", "Priya@gmail.com", "1234", "12345667", "abc", encoder.encode("password"));
        Customer savedCustomer = customerRepository.save(customer);
        Account account = new Account(new BigDecimal(0), "ACTIVE", savedCustomer, new Date());
        accountRepository.save(account);
        SignUpRequest signupRequest = new SignUpRequest("Preeti", "Priya@gmail.com", "9999999999", "011", "bihar", "preeti@123");
        String requestJson = new ObjectMapper().writeValueAsString(signupRequest);

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldBeAbleToGetAccountSummary() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Customer customer = new Customer("preeti", "Priya@gmail.com", "1234", "12345667", "abc", encoder.encode("password"));
        Customer savedCustomer = customerRepository.save(customer);
        Account account = new Account(new BigDecimal(0), "ACTIVE", savedCustomer, new Date());
        accountRepository.save(account);

        mockMvc.perform(get("/summary")
                        .with((httpBasic("Priya@gmail.com", "password"))))
                .andExpect(status().isOk());
    }
}
