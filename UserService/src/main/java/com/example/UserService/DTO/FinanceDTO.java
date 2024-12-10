package com.example.UserService.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceDTO {
    private UUID financeId;
    private int customerId;
    private double amount;
    private double budgetLimit;
}
