package com.system.banking.service;

import com.system.banking.exceptions.AccountNumberNotFoundException;
import com.system.banking.exceptions.EmailIdAlreadyRegisteredException;
import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private CustomerRepository customerRepository;

    private AccountRepository accountRepository;


    private CustomerPrincipalService customerPrincipalService;

    @BeforeEach
    public void setup() {
        customerRepository = mock(CustomerRepository.class);
        accountRepository = mock(AccountRepository.class);
        customerPrincipalService = mock(CustomerPrincipalService.class);
    }

    @Test
    void shouldBeAbleToGenerateAccountDetailsWhenCustomerDetailsAreStored() throws EmailIdAlreadyRegisteredException {
        Customer customer = new Customer("Preeti", "Priya1123@example.com", "1111111111", "123456789", "xyz", "password");
        when(customerPrincipalService.getCustomer(customer.getEmail())).thenReturn(customer);
        Account account = new Account(new BigDecimal("0.0"), "ACTIVE", customer, new Date());
        AccountService accountService = new AccountService(accountRepository, customerRepository, customerPrincipalService);

        accountService.createAccount(customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getIdentityCard(), customer.getAddress(), customer.getPassword());

        verify(accountRepository).save(account);
    }

    @Test
    void shouldBeAbleToGetAccountWhenAccountNumberIsProvided() throws AccountNumberNotFoundException, EmailIdAlreadyRegisteredException {
        Customer customer = new Customer("Preeti", "Priya11@example.com", "1111111111", "123456789", "xyz", "password");
        Account account = new Account(new BigDecimal("0.0"), "ACTIVE", customer, new Date());
        AccountService accountService = new AccountService(accountRepository, customerRepository, customerPrincipalService);
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        accountService.createAccount(customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getIdentityCard(), customer.getAddress(), customer.getPassword());

        Account fetchedAccount = accountService.getAccount(account.getId());

        assertEquals(account, fetchedAccount);
    }

    @Test
    void shouldBeAbleToThrowExceptionWhenAccountNumberDoesNotExist() {
        Long accountNumber = 8L;
        AccountService accountService = new AccountService(accountRepository, customerRepository, customerPrincipalService);

        assertThrows(AccountNumberNotFoundException.class, () -> accountService.getAccount(accountNumber));
    }

    @Test
    void shouldBeAbleToGetSummaryOfAccount() {
        Customer customer = new Customer("Preeti", "Priya@example.com", "1111111111", "123456789", "xyz", "password");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        Account account = new Account(new BigDecimal(0), "ACTIVE", customer, new Date());
        when(accountRepository.findByCustomerId(customer.getId())).thenReturn(account);
        Map<String, Object> expectedSummary = new HashMap<>();
        expectedSummary.put("Name", customer.getName());
        expectedSummary.put("Account Number", account.getId());
        expectedSummary.put("Balance", account.getBalance());
        AccountService accountService = new AccountService(accountRepository, customerRepository, customerPrincipalService);

        Map<String, Object> actualSummary = accountService.getSummary(customer.getEmail());

        assertEquals(expectedSummary, actualSummary);
    }
}
