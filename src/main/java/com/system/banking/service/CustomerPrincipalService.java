package com.system.banking.service;

import com.system.banking.exceptions.EmailIdAlreadyRegisteredException;
import com.system.banking.model.Customer;
import com.system.banking.model.CustomerPrincipal;
import com.system.banking.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerPrincipalService implements UserDetailsService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerPrincipalService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = getCustomer(email);
        return new CustomerPrincipal(customer);

    }

    public Customer getCustomer(String email) {

        return customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public void saveCustomer(String name, String email, String mobileNumber, String identityCardNumber, String address, String password) throws EmailIdAlreadyRegisteredException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Customer> fetchedCustomer = customerRepository.findByEmail(email);
        if(fetchedCustomer.isPresent()) throw new EmailIdAlreadyRegisteredException();
        Customer customer = new Customer(name, email, mobileNumber, identityCardNumber, address, encoder.encode(password));
        customerRepository.save(customer);

    }


}
