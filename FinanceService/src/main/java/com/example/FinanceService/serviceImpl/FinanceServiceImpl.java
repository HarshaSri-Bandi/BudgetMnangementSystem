package com.example.FinanceService.serviceImpl;

import com.example.FinanceService.Entity.Finance;
import com.example.FinanceService.Repository.FinanceRepository;
import com.example.FinanceService.Service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceRepository financeRepository;


    @Override
    public void saveFinanance(Finance finance) {

        financeRepository.save(finance);
    }

    @Override
    public Optional<Finance> getFinanceById(int customerId) {
        Optional<Finance> finance = financeRepository.findByCustomerId(customerId);
        return finance;
    }

    @Override
    public Finance getExtFinanceById(int customerId) {
        return  financeRepository.findByCustomerId(customerId).orElse(null);
    }
}
