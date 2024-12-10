package com.example.UserService.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDTO {
    private UUID IncomeId;
    private int customerId;
    private String category;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private double incomeAmount;
}
