package com.system.banking.exceptions;

public class EmailIdAlreadyRegisteredException extends Exception{
    public EmailIdAlreadyRegisteredException() {
        super("This email id is already registered");
    }
}
