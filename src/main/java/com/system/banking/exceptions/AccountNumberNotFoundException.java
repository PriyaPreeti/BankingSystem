package com.system.banking.exceptions;

public class AccountNumberNotFoundException extends Exception{
    public AccountNumberNotFoundException() {
        super("This account number does not exist");
    }
}
