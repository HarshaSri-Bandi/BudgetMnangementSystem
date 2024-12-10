package com.example.ExpenseService.exception;

public class ExpenseNotFoundException extends Exception {
    public ExpenseNotFoundException(String s) {
        super(s);
    }
}
