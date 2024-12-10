package com.example.ExpenseService.Entity;

import com.example.ExpenseService.Validation.ValidateCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expense {

    @Id
    private UUID expenseId;

    @NotNull
    private int userId;

    @NotBlank
    private double amountSpent;

    @ValidateCategory
    private String category;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    public Expense(int i, double v, String category) {
        this.userId = i;
        this.amountSpent = v;
        this.category = category;
    }
}
