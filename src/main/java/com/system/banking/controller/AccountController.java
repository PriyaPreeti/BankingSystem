package com.system.banking.controller;

import com.system.banking.controller.request.SignUpRequest;
import com.system.banking.service.AccountService;
//import com.system.banking.service.CustomerPrincipalService;
import com.system.banking.service.CustomerPrincipalService;
import com.system.banking.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class AccountController {


    private CustomerService customerService;
    private AccountService accountService;

    private CustomerPrincipalService customerPrincipalService;

    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void save(@RequestBody SignUpRequest signupRequest) throws IOException {
        customerService.saveCustomer(signupRequest.getName(), signupRequest.getEmail(), signupRequest.getMobileNumber(), signupRequest.getIdentityCard(), signupRequest.getAddress(), signupRequest.getPassword());
        accountService.createAccount(new BigDecimal(0), "ACTIVE", signupRequest.getIdentityCard(), new Date());
    }


    @GetMapping("/login")
    public Map<String, Object> login(Principal principal) {
        String userName = principal.getName();
        System.out.println(userName);
        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("customerId", customerPrincipalService.loadUserByUsername(userName));

        return customerDetails;
    }


}
