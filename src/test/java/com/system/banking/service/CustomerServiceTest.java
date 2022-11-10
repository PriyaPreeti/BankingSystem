package com.system.banking.service;

import com.system.banking.model.Customer;
import com.system.banking.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CustomerServiceTest {
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        customerRepository = mock(CustomerRepository.class);
    }

    @Test
    void shouldBeAbleToSaveCustomer() {
        Customer customer = new Customer("Preeti", "Priya@example.com", "1111111111", "123456789", "xyz", "password");
        CustomerService customerService = new CustomerService(customerRepository);

        customerService.saveCustomer(customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getIdentityCard(), customer.getAddress(), customer.getPassword());

        verify(customerRepository).save(customer);
    }

}
