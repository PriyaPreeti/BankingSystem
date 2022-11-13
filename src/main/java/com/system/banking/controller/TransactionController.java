package com.system.banking.controller;

import com.system.banking.controller.request.TransactionRequest;
import com.system.banking.exceptions.AccountNumberNotFoundException;
import com.system.banking.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    @PostMapping("/transaction")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void performTransaction(@RequestBody TransactionRequest transactionRequest) throws IOException, AccountNumberNotFoundException {
        transactionService.performTransaction(transactionRequest.getTransactionType(), transactionRequest.getAmount(), transactionRequest.getAccountNumber());

    }
}
