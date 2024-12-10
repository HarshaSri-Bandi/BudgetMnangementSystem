package com.example.IncomeService.Controller;

import com.example.IncomeService.Entity.Income;
import com.example.IncomeService.service.IncomeService;
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
@RequestMapping("/api/income")
public class incomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(incomeController.class);

    @Autowired
    public IncomeService incomeService;

    /**
     * method to get all the transaction details by user
     * @param userId
     * @return income
     */
    @GetMapping("/getIncomingById/{userId}")
    public ResponseEntity<?> getIncomingTransactions(@PathVariable int userId){
        Optional<List<Income>> incomes = incomeService.getIncomeById(userId);

        if (incomes.isEmpty()) {
            LOGGER.warn("Customer with this userID  {} is not available......", userId);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("null");
        }

        LOGGER.info("Fetching user details from user Id");
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(incomes);
    }

    /**
     * method to save incoming transaction
     * @param income
     * @return null
     */
    @PostMapping("/saveIncome")
    public String saveIncome(@RequestBody Income income) {

        Optional<List<Income>> incomeList = incomeService.getIncomeById(income.getCustomerId());;

        if(incomeList.isEmpty()){
            LOGGER.info("your first income is added......");
        }

        LOGGER.info("saving income details......");
        UUID incomeId = UUID.randomUUID();

        income.setIncomeId(incomeId);
        incomeService.saveIncoming(income);

        return "Amount received successfully......";
        //return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Amount received successfully......");
    }

    /**
     * method to get all the transaction details by user on specific date
     * @param userId
     * @param date
     * @return income details
     */
    @GetMapping("/getIncomingByIdAndDate/{userId}/{date}")
    public ResponseEntity<?> getIncomingTransactions(@PathVariable int userId, @PathVariable Date date){
        Optional<List<Income>> incomes = incomeService.getIncomeByIdAndDate(userId, date);

        LOGGER.info("fetching Income details by userID and date");
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(incomes);
    }

    /**
     * method to get all the transaction details by user for specific category
     * @param userId
     * @param category
     * @return Income details
     */
    @GetMapping("/getIncomingByIdAndCategory/{userId}/{category}")
    public ResponseEntity<?> getIncomingTransactions(@PathVariable int userId, @PathVariable String category){
        Optional<List<Income>> incomes = incomeService.getIncomeByIdAndCategory(userId, category);

        if (incomes.isEmpty()) {
            LOGGER.warn("Customer with this userID  {} and category {} is not available......", userId, category);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("null");
        }
        LOGGER.info("fetching Income details by userID and category");
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(incomes);
    }

}
