package com.example.ExpenseService.ServiceImplementation;

import com.example.ExpenseService.Entity.Expense;
import com.example.ExpenseService.Repository.ExpenseRepository;
import com.example.ExpenseService.Service.ExpenseService;
import com.example.ExpenseService.exception.ExpenseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImplementation implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Override
    public List<Expense> getexpenseByCustomerId(int userId) throws ExpenseNotFoundException {
        List<Expense> expenses = expenseRepository.findByUserId(userId);

        if(expenses != null) {
            return  expenses;
        }else {
            throw new ExpenseNotFoundException("expense for the user id "+ userId + " is not present");
        }
    }

    @Override
    public String saveExpenses(Expense expense) {
        expenseRepository.save(expense);
        return "expense successfully added";
    }

    @Override
    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getExpenseByUserAndCategory(int customerId, String category) {
        return expenseRepository.findByUserIdAndCategory(customerId,category);
    }

    @Override
    public List<Expense> getExpenseByUserAndDate(int customerId, Date date) {
        return expenseRepository.findByUserIdAndDate(customerId, date);
    }

    @Override
    public List<Expense> getExpenseByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    @Override
    public List<Expense> getExpenseByDate(Date date) {
        return expenseRepository.findByDate(date);
    }
}
