package com.example.ExpenseService.Repository;

import com.example.ExpenseService.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findByUserId(int userId);

    List<Expense> findByUserIdAndCategory(int customerId, String category);

    List<Expense> findByUserIdAndDate(int customerId, Date date);

    List<Expense> findByCategory(String category);

    List<Expense> findByDate(Date date);
}
