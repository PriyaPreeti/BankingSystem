package com.system.banking.service;

import com.system.banking.exceptions.AccountNumberNotFoundException;
import com.system.banking.exceptions.EmailIdAlreadyRegisteredException;
import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    public void createAccount(String name, String email, String mobileNumber, String identityCardNumber, String address, String password) throws EmailIdAlreadyRegisteredException {
        CustomerPrincipalService customerPrincipalService = new CustomerPrincipalService(customerRepository);
        //   if(customerPrincipalService.getCustomer(email).) throw new EmailIdAlreadyRegisteredException();
        customerPrincipalService.saveCustomer(name, email, mobileNumber, identityCardNumber, address, password);
        Customer savedCustomer = customerPrincipalService.getCustomer(email);
        Account account = new Account(new BigDecimal(0), "ACTIVE", savedCustomer, new Date());
        accountRepository.save(account);

    }

    public Account getAccount(Long accountNumber) throws AccountNumberNotFoundException {
        return accountRepository.findById(accountNumber).orElseThrow(() -> new AccountNumberNotFoundException());

    }

    public Account findAccountByCustomer(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    public Map<String, Object> getSummary(String email) {
        Map<String, Object> summary = new HashMap<>();
        CustomerPrincipalService customerPrincipalService = new CustomerPrincipalService(customerRepository);
        Customer customer = customerPrincipalService.getCustomer(email);
        String name = customer.getName();
        Account account = accountRepository.findByCustomerId(customer.getId());
        summary.put("Name", name);
        summary.put("Account Number", account.getId());
        summary.put("Balance", account.getBalance());
        return summary;

    }

}
