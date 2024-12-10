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
public class ExpenseDTO {

    private UUID expenseId;
    @NotNull
    private int userId;
    @NotNull
    private double amountSpent;
    @NotNull
    private String category;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;
}
