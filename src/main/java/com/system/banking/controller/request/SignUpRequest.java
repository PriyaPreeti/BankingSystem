package com.system.banking.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    private String name;
    private String email;
    private String mobileNumber;
    private String identityCard;
    private String address;
    private String password;

}
