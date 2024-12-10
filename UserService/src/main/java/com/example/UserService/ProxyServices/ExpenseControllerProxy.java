package com.example.UserService.ProxyServices;

import com.example.UserService.DTO.ExpenseDTO;
import com.example.UserService.ExceptionHandling.ExpenseNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.List;

@FeignClient(name = "expense-Service", url="localhost:8083/api/expense")
public interface ExpenseControllerProxy {

    @PostMapping("/saveExpense")
    public String saveExpenceforUser(@RequestBody ExpenseDTO expense);

    @GetMapping("/getExpensesByCustomerId/{customerId}")
    public List<ExpenseDTO> getExpensesByCustomer(@PathVariable int customerId);

    @GetMapping("/getAllExpenses")
    public ResponseEntity<?> getAllExpenses () throws ExpenseNotFoundException;

    @GetMapping("/getExpenesBYUserAndCategory/{customerId}/{category}")
    public ResponseEntity<?> getExpensesByUserAndCategory (@PathVariable int customerId, @PathVariable String category) throws ExpenseNotFoundException;

    @GetMapping("/getExpenesBYUserAndDate/{customerId}/{date}")
    public ResponseEntity<?> getExpensesByUserAndDate(@PathVariable int customerId, @PathVariable Date date) throws ExpenseNotFoundException;

    @GetMapping("/getExpenseByCategory/{category}")
    public ResponseEntity<?> getExpenseByCategory(@PathVariable String category) throws ExpenseNotFoundException;

    @GetMapping("/getExpenseByDate/{date}")
    public ResponseEntity<?> getExpenseByDate(@PathVariable Date date) throws ExpenseNotFoundException;

}
