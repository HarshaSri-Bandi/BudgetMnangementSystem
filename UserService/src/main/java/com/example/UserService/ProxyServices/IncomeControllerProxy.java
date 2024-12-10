package com.example.UserService.ProxyServices;

import com.example.UserService.DTO.IncomeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;

@FeignClient(name = "income-Service", url="localhost:8082/api/income" )
public interface IncomeControllerProxy {

    @GetMapping("/getIncomingById/{userId}")
    public ResponseEntity<?> getIncomingTransactions(@PathVariable int userId);

    @PostMapping("/saveIncome")
    public String saveIncome(@RequestBody IncomeDTO income);

    @GetMapping("/getIncomingByIdAndDate/{userId}/{date}")
    public ResponseEntity<?> getIncomingTransactions(@PathVariable int userId, @PathVariable Date date);

    @GetMapping("/getIncomingByIdAndCategory/{userId}/{category}")
    public ResponseEntity<?> getIncomingTransactions(@PathVariable int userId, @PathVariable String category);

}
