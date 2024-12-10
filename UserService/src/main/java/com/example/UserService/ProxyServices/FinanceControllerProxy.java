package com.example.UserService.ProxyServices;

import com.example.UserService.DTO.FinanceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "finance-Service", url="localhost:8081/api/Finance")
public interface FinanceControllerProxy {

    @GetMapping("/getFinanceById/{customerId}")
    public FinanceDTO getFinanceById(@PathVariable int customerId);

    @PutMapping("/updateAmountByCustomerId/{customerId}/{incomeAmount}")
    public String updateFinanceById(@PathVariable int customerId, @PathVariable double incomeAmount);

    @PutMapping("/updateamountbyExpense/{customerId}/{expenseAmount}")
    public String updateFinanceAfterExpense(@PathVariable int customerId, @PathVariable double expenseAmount);

    @PostMapping("/addFinance")
    public String addFinance(@RequestBody FinanceDTO finance);
}
