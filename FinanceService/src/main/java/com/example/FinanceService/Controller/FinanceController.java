package com.example.FinanceService.Controller;

import com.example.FinanceService.Entity.Finance;
import com.example.FinanceService.Repository.FinanceRepository;
import com.example.FinanceService.Service.FinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/Finance")
public class FinanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceController.class);
    @Autowired
    private FinanceRepository financeRepository;

    @Autowired
    private FinanceService financeService;

    /**
     *  Saving finance details
     * @param finance
     * @return string
     */

    @PostMapping("/addFinance")
    public String addFinance(@RequestBody Finance finance){

        UUID financeId = UUID.randomUUID();
        finance.setFinanceId(financeId);
        Optional<Finance> financeById = financeService.getFinanceById(finance.getCustomerId());

        if(!financeById.isEmpty()){
            LOGGER.info("Finance for this customer already exists");
            return "finance exists";
        }
        financeService.saveFinanance(finance);

        LOGGER.info("Saving finance details");
        return "finance saved";
    }

    /**
     * getting finance details by customer Id
     * @param customerid
     * @return
     */
    @GetMapping("/getFinanceById/{customerid}")
    public ResponseEntity<?> getFinanceById(@PathVariable int customerid){

        Optional<Finance> financebyId = financeService.getFinanceById(customerid);

        LOGGER.info("Fetched the finance details of customer with customer id : {}", customerid);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(financebyId);

    }

    @PutMapping("/updateAmountByCustomerId/{customerId}/{incomeAmount}")
    public String updateFinanceById(@PathVariable int customerId, @PathVariable double incomeAmount){
        
        Finance finance = financeService.getExtFinanceById(customerId);

        if(finance == null){
            LOGGER.warn("Customer with specific Id is not present");

            return "Customer with specific Id is not present";
        }

        finance.setAmount(finance.getAmount() + incomeAmount);

        financeService.saveFinanance(finance);
        return "finance amount updated successfully";
    }

    @PutMapping("/updateamountbyExpense/{customerId}/{expenseAmount}")
    public String updateFinanceAfterExpense(@PathVariable int customerId, @PathVariable double expenseAmount){
        Finance finance = financeService.getExtFinanceById(customerId);

        if(finance == null){
            LOGGER.warn("Customer with specific Id is not present");
            return "Customer with specific Id is not present";
        }
        finance.setAmount(finance.getAmount() - expenseAmount);

        financeService.saveFinanance(finance);
        return "finance amount updated successfully";
    }


}
