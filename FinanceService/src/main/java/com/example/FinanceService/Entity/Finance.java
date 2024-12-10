package com.example.FinanceService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Finance {

    @Id
    private UUID financeId;

    @NotNull
    private int customerId;

    @NotNull
    private double amount;

    @NotNull
    private double budgetLimit;
}
