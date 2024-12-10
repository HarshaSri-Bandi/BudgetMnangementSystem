package com.example.ExpenseService.Service;

import com.example.ExpenseService.Entity.Expense;
import com.example.ExpenseService.exception.ExpenseNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getexpenseByCustomerId(int userId) throws ExpenseNotFoundException;

    String saveExpenses(Expense expense);

    List<Expense> getAllExpense();

    List<Expense> getExpenseByUserAndCategory(int customerId, String category);

    List<Expense> getExpenseByUserAndDate(int customerId, Date date);

    List<Expense> getExpenseByCategory(String category);

    List<Expense> getExpenseByDate(Date date);
}
