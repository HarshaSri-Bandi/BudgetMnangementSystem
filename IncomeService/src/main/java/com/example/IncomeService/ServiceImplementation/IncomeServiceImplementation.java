package com.example.IncomeService.ServiceImplementation;

import com.example.IncomeService.Entity.Income;
import com.example.IncomeService.Repository.IncomeRepository;
import com.example.IncomeService.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImplementation implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public Optional<List<Income>> getIncomeById(int userId) {

        return incomeRepository.findByCustomerId(userId);
    }

    @Override
    public void saveIncoming(Income income) {
        incomeRepository.save(income);
    }

    @Override
    public Optional<List<Income>> getIncomeByIdAndDate(int userId, Date date) {
        return incomeRepository.findByCustomerIdAndDate(userId,date);
    }

    @Override
    public Optional<List<Income>> getIncomeByIdAndCategory(int userId, String category) {
        return incomeRepository.findByCustomerIdAndCategory(userId, category);
    }
}
