package com.example.UserService.ExceptionHandling;

public class ExpenseNotFoundException extends Exception {

    public ExpenseNotFoundException(String s) {

        super(s);
    }
}
