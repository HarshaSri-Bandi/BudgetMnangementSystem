package com.example.ExpenseService.Controller;

import com.example.ExpenseService.Entity.Expense;
import com.example.ExpenseService.Service.ExpenseService;
import com.example.ExpenseService.exception.ExpenseNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseController.class);

    @Autowired
    private ExpenseService expenseService;

    /**
     * expense addition
     * @param expense
     * @return
     */
    @PostMapping("/saveExpense")
    public String saveExpenceforUser(@RequestBody Expense expense) {

//        List<Expense> expenses = expenseService.getexpenseByCustomerId(expense.getUserId());
//
//        if(expenses.isEmpty()){
//            LOGGER.info("Your first expense is being added........");
//        }

        UUID expenseId = UUID.randomUUID();

        expense.setExpenseId(expenseId);

        String response = expenseService.saveExpenses(expense);
        LOGGER.info("expense successfully added");

        return response;
    }

    @GetMapping("/getExpensesByCustomerId/{customerId}")
    public List<Expense> getExpensesByCustomer(@PathVariable int customerId) throws ExpenseNotFoundException {

        List<Expense> expenses = expenseService.getexpenseByCustomerId(customerId);

        if(expenses.isEmpty()){
            LOGGER.info("there are no expenses for the customer with id {} ........", customerId);

            throw  new ExpenseNotFoundException("expense for the user id "+ customerId + " is not present");
            //return  ResponseEntity.ok("null");
           // return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("expense for the user id "+ customerId + " is not present");
        }

        LOGGER.info("fetching all the expenses for the user Id {} ", customerId );
       // return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(expenses);

        return  expenses;
    }

    @GetMapping("/getAllExpenses")
    public ResponseEntity<?> getAllExpenses() throws ExpenseNotFoundException {
        List<Expense> expenses = expenseService.getAllExpense();

        if(expenses.isEmpty()) {
            LOGGER.info("there are no expenses in the database");

            throw new ExpenseNotFoundException("there are no expenses in the database");
        }
            LOGGER.info("fetching all the expenses......");

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(expenses);
    }

    @GetMapping("/getExpenesBYUserAndCategory/{customerId}/{category}")
    public ResponseEntity<?> getExpensesByUserAndCategory(@PathVariable int customerId, @PathVariable String category) throws ExpenseNotFoundException {
        List<Expense> expenses = expenseService.getExpenseByUserAndCategory(customerId,category);

        if (expenses.isEmpty()) {
            LOGGER.warn("Customer with this userID  {} and category {} is not available......", customerId, category);

            throw  new ExpenseNotFoundException("expense for the user id "+ customerId + " and Category "+ category+" is not present");
//            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("null");
        }
        LOGGER.info("fetching Expense details.......");
        return  ResponseEntity.status(HttpStatusCode.valueOf(200)).body(expenses);
    }

    @GetMapping("/getExpenesBYUserAndDate/{customerId}/{date}")
    public ResponseEntity<?> getExpensesByUserAndDate(@PathVariable int customerId, @PathVariable Date date) throws ExpenseNotFoundException {
        List<Expense> expenses = expenseService.getExpenseByUserAndDate(customerId,date);

        if (expenses.isEmpty()) {
            LOGGER.warn("Customer with this userID  {} and date {} is not available......", customerId, date);

            throw  new ExpenseNotFoundException("expense for the user id "+ customerId + " and date "+ date+" is not present");
            //return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("null");
        }
        LOGGER.info("fetching Expense details.......");
        return  ResponseEntity.status(HttpStatusCode.valueOf(200)).body(expenses);
    }

    @GetMapping("/getExpenseByCategory/{category}")
    public ResponseEntity<?> getExpenseByCategory(@PathVariable String category) throws ExpenseNotFoundException {
        List<Expense> expenses = expenseService.getExpenseByCategory(category);

        if (expenses.isEmpty()) {
            LOGGER.warn("Customer with this category {} is not available......", category);

            throw  new ExpenseNotFoundException("expense for the category "+ category + " is not present");
//            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("null");
        }
        LOGGER.info("fetching Expense details.......");
        return  ResponseEntity.status(HttpStatusCode.valueOf(200)).body(expenses);
    }

    @GetMapping("/getExpenseByDate/{date}")
    public ResponseEntity<?> getExpenseByDate(@PathVariable Date date) throws ExpenseNotFoundException {
        List<Expense> expenses = expenseService.getExpenseByDate(date);

        if (expenses.isEmpty()) {
            LOGGER.warn("Customer with this date {} is not available......", date);
            throw  new ExpenseNotFoundException("expense for the date "+ date + " is not present");
//            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("null");
        }
        LOGGER.info("fetching Expense details.......");
        return  ResponseEntity.status(HttpStatusCode.valueOf(200)).body(expenses);
    }

}
