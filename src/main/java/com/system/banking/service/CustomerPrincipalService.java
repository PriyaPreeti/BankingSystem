package com.system.banking.service;

import com.system.banking.model.Customer;
import com.system.banking.model.CustomerPrincipal;
import com.system.banking.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerPrincipalService implements UserDetailsService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerPrincipalService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = findUserByUsername(email);
        return new CustomerPrincipal(customer);

    }

    public Customer findUserByUsername(String email) {

        return customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }


}
