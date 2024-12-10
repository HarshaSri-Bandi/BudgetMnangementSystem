package com.example.FinanceService.Service;

import com.example.FinanceService.Entity.Finance;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface FinanceService {


    void saveFinanance(Finance finance);

    Optional<Finance> getFinanceById(int customerId);

    Finance getExtFinanceById(int customerId);
}
