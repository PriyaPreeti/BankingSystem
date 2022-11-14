package com.system.banking.service;

import com.system.banking.controller.response.TransactionResponse;
import com.system.banking.controller.response.TransactionStatementResponse;
import com.system.banking.model.Account;
import com.system.banking.model.Customer;
import com.system.banking.model.Transaction;
import com.system.banking.model.TransactionType;
import com.system.banking.repo.AccountRepository;
import com.system.banking.repo.CustomerRepository;
import com.system.banking.repo.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private AccountService accountService;


    @Transactional
    public void performTransaction(String userName, TransactionType transactionType, BigDecimal amount) {
        CustomerPrincipalService customerPrincipalService = new CustomerPrincipalService(customerRepository);
        Customer customer = customerPrincipalService.getCustomer(userName);
        Account fetchedAccount = accountService.findAccountByCustomer(customer.getId());
        Transaction transaction = new Transaction(transactionType.toString(), amount, fetchedAccount, new Date());
        transactionRepository.save(transaction);
        fetchedAccount.setBalance((transactionType.getMultiplicationFactor().multiply(amount)).add(fetchedAccount.getBalance()));

    }

    public TransactionStatementResponse getStatement(String email) {
        CustomerPrincipalService customerPrincipalService = new CustomerPrincipalService(customerRepository);
        Customer customer = customerPrincipalService.getCustomer(email);
        Account account = accountService.findAccountByCustomer(customer.getId());
        List<TransactionResponse> transactionResponse = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findByAccount_id(account.getId());
        for (Transaction transaction : transactions) {
            transactionResponse.add(new TransactionResponse(transaction.getId(), transaction.getTransactionType(), transaction.getAmount()));
        }
        TransactionStatementResponse transactionStatementResponse = new TransactionStatementResponse(account.getId(), customer.getName(), account.getBalance(), transactionResponse);
        return transactionStatementResponse;

    }
}
