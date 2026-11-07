package com.example.bank;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String s) {
        super(s);
    }
}
