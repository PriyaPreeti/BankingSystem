package com.system.banking.service;

import com.system.banking.model.Customer;
import com.system.banking.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerPrincipalServiceTest {

    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        customerRepository = mock(CustomerRepository.class);
    }

    @Test
    void shouldBeAbleToSaveCustomer() {
        Customer customer = new Customer("Preeti", "Priya@example.com", "1111111111", "123456789", "xyz", "password");
        CustomerPrincipalService customerPrincipalService = new CustomerPrincipalService(customerRepository);

        customerPrincipalService.saveCustomer(customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getIdentityCard(), customer.getAddress(), customer.getPassword());

        verify(customerRepository).save(customer);
    }

    @Test
    void shouldBeAbleToGetCustomerWhenEmailIsProvided() {
        Customer customer = new Customer("Preeti", "Priya@example.com", "1111111111", "123456789", "xyz", "password");
        CustomerPrincipalService customerPrincipalService = new CustomerPrincipalService(customerRepository);
        customerPrincipalService.saveCustomer(customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getIdentityCard(), customer.getAddress(), customer.getPassword());
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        Customer fetchedCustomer = customerPrincipalService.getCustomer(customer.getEmail());

        assertEquals(customer, fetchedCustomer);
    }

}
