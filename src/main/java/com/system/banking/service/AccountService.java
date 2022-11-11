package com.system.banking.service;

import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    public void createAccount(String name, String email, String mobileNumber, String identityCardNumber, String address, String password) {
        CustomerPrincipalService customerPrincipalService = new CustomerPrincipalService(customerRepository);
        // saveCustomer(name,email,mobileNumber,identityCardNumber,address,password);
        customerPrincipalService.saveCustomer(name, email, mobileNumber, identityCardNumber, address, password);
        Customer savedCustomer = customerPrincipalService.getCustomer(email);
        Account account = new Account(new BigDecimal(0), "ACTIVE", savedCustomer, new Date());
        accountRepository.save(account);

    }

    public Customer getCustomer(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public void saveCustomer(String name, String email, String mobileNumber, String identityCardNumber, String address, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Customer customer = new Customer(name, email, mobileNumber, identityCardNumber, address, encoder.encode(password));
        customerRepository.save(customer);
    }

}
