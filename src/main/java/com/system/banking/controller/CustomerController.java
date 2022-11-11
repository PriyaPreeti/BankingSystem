package com.system.banking.controller;

import com.system.banking.service.CustomerPrincipalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class CustomerController {

    private CustomerPrincipalService customerPrincipalService;

    @GetMapping("/login")
    public Map<String, Object> login(Principal principal) {
        String userName = principal.getName();
        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("email", customerPrincipalService.loadUserByUsername(userName).getUsername());

        return customerDetails;
    }
}

