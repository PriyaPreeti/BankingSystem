package com.system.banking.controller;

import com.system.banking.controller.request.SignUpRequest;
import com.system.banking.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void save(@RequestBody SignUpRequest signupRequest) throws IOException {
        accountService.createAccount(signupRequest.getName(), signupRequest.getEmail(), signupRequest.getMobileNumber(), signupRequest.getIdentityCard(), signupRequest.getAddress(), signupRequest.getPassword());
    }

    @GetMapping("/summary")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> getSummary(Principal principal) {
        String email = principal.getName();
        Map<String, Object> summary = accountService.getSummary(email);
        return summary;

    }


}
