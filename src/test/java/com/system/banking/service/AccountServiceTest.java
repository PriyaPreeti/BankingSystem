package com.system.banking.service;

import com.system.banking.exceptions.AccountNumberNotFoundException;
import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private CustomerRepository customerRepository;

    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        customerRepository = mock(CustomerRepository.class);
        accountRepository = mock(AccountRepository.class);
    }

    @Test
    void shouldBeAbleToGenerateAccountDetailsWhenCustomerDetailsAreStored() {
        Customer customer = new Customer("Preeti", "Priya@example.com", "1111111111", "123456789", "xyz", "password");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        Account account = new Account(new BigDecimal("0.0"), "ACTIVE", customer, new Date());
        AccountService accountService = new AccountService(accountRepository, customerRepository);

        accountService.createAccount(customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getIdentityCard(), customer.getAddress(), customer.getPassword());

        verify(accountRepository).save(account);
    }

    @Test
    void shouldBeAbleToGetAccountWhenAccountNumberIsProvided() throws AccountNumberNotFoundException {
        Customer customer = new Customer("Preeti", "Priya@example.com", "1111111111", "123456789", "xyz", "password");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        Account account = new Account(new BigDecimal("0.0"), "ACTIVE", customer, new Date());
        AccountService accountService = new AccountService(accountRepository, customerRepository);
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
        accountService.createAccount(customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getIdentityCard(), customer.getAddress(), customer.getPassword());

        Account fetchedAccount = accountService.getAccount(account.getAccountNumber());

        assertEquals(account, fetchedAccount);
    }

    @Test
    void shouldBeAbleToThrowExceptionWhenAccountNumberDoesNotExist() {
        Long accountNumber=8L;
        AccountService accountService = new AccountService(accountRepository, customerRepository);

        assertThrows(AccountNumberNotFoundException.class, () -> accountService.getAccount(accountNumber));
    }
}
