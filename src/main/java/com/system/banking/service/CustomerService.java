package com.system.banking.service;

import com.system.banking.model.Customer;
import com.system.banking.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {


    private CustomerRepository customerRepository;

    public void saveCustomer(String name, String email, String mobileNumber, String identityCardNumber, String address, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Customer customer = new Customer(name, email, mobileNumber, identityCardNumber, address, encoder.encode(password));
        customerRepository.save(customer);
    }

}
