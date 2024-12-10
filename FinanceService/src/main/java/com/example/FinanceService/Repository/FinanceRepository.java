package com.example.FinanceService.Repository;

import com.example.FinanceService.Entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FinanceRepository extends JpaRepository<Finance, UUID> {



    Optional<Finance> findByCustomerId(int customerId);
}
