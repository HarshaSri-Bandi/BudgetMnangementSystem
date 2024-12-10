package com.example.IncomeService.Repository;

import com.example.IncomeService.Entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID> {
    Optional<List<Income>> findByCustomerId(int userId);

    Optional<List<Income>> findByCustomerIdAndDate(int userId, Date date);

    Optional<List<Income>> findByCustomerIdAndCategory(int userId, String category);
}
