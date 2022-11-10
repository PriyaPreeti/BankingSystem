package com.system.banking.service;

import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private CustomerRepository customerRepository;

    private AccountRepository accountRepository;
    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        customerRepository = mock(CustomerRepository.class);
        accountRepository = mock(AccountRepository.class);
    }

    @Test
    void shouldBeAbleToSaveAccountDetailsWhenCustomerDetailsAreStored() {
        Customer customer = mock(Customer.class);
        String identityCard = "1234";
        when(customerRepository.findByIdentityCard(identityCard)).thenReturn(customer);
        Customer fetchedCustomer = customerRepository.findByIdentityCard(identityCard);
        Account account = new Account(new BigDecimal("0.0"), "ACTIVE", fetchedCustomer, new Date());
        AccountService accountService = new AccountService(accountRepository, customerRepository);
        when(accountRepository.save(account)).thenReturn(account);

        accountService.createAccount(account.getBalance(), account.getAccountStatus(), account.getCustomer().getIdentityCard(), account.getCreatedAt());

        verify(accountRepository).save(account);
    }
}
