package com.nassih.bankapi.exceptions;

public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(String message) {
    super(message);
    }
}
