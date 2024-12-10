package com.example.IncomeService.service;

import com.example.IncomeService.Entity.Income;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface IncomeService {
     Optional<List<Income>> getIncomeById(int userId) ;

     void saveIncoming(Income income) ;

     Optional<List<Income>> getIncomeByIdAndDate(int userId, Date date) ;

     Optional<List<Income>> getIncomeByIdAndCategory(int userId, String category) ;
}
