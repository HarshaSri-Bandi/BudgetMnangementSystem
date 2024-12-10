package com.example.IncomeService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotNull;


import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Income {
    @Id
    private UUID IncomeId;

    @NotNull
    private int customerId;

    @NotNull
    private String category;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    @NotNull
    private double incomeAmount;

}
