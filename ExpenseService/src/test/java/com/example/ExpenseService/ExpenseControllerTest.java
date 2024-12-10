package com.example.ExpenseService;

import com.example.ExpenseService.Entity.Expense;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        // Create a User object
        Expense expense = new Expense(1,200.0,"food");


        // Convert User object to JSON string
        String userJson = objectMapper.writeValueAsString(expense);

        // Perform POST request and validate the response
        mockMvc.perform(post("/api/expense/saveExpense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))  // <- .content() method here
                .andExpect(status().isOk()) ; // <- .andExpect() method here

    }
    @Test
    public void testGetExpensesByUserIdAndCategory() throws Exception {
        int userId = 1;
        String category = "food";

        mockMvc.perform(get("/api/expense/getExpenesBYUserAndCategory/{userId}/{category}", userId, category)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))  // Expect 2 expenses in the response
                .andExpect(jsonPath("$[0].userId", is(userId)))
                .andExpect(jsonPath("$[0].category", is(category)))
                .andExpect(jsonPath("$[0].amount", is(200.0)))
                .andExpect(jsonPath("$[1].userId", is(userId)))
                .andExpect(jsonPath("$[1].category", is(category)))
                .andExpect(jsonPath("$[1].amount", is(200.0)))

    }

    @Test
    public void testGetExpensesByUserIdDate() throws Exception {
        int userId = 1;
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) formatter.parse("2024-08-29");

        mockMvc.perform(get("/api/expense/getExpenesBYUserAndDate/{userId}/{date}", userId, date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))  // Expect 2 expenses in the response
                .andExpect(jsonPath("$[0].userId", is(userId)))
                .andExpect(jsonPath("$[0].date", is(date)))
                .andExpect(jsonPath("$[0].amount", is(200.0)))
                .andExpect(jsonPath("$[1].userId", is(userId)))
                .andExpect(jsonPath("$[1].date", is(date)))
                .andExpect(jsonPath("$[1].amount", is(200.0)));

    }

    @Test
    public void testGetExpensesByUserId() throws Exception {
        int userId = 1;
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) formatter.parse("2024-08-29");

        mockMvc.perform(get("/api/expense/getExpensesByCustomerId/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))  // Expect 2 expenses in the response
                .andExpect(jsonPath("$[0].userId", is(userId)))
                .andExpect(jsonPath("$[0].date", is(date)))
                .andExpect(jsonPath("$[0].amount", is(200.0)))
                .andExpect(jsonPath("$[1].userId", is(userId)))
                .andExpect(jsonPath("$[1].date", is(date)))
                .andExpect(jsonPath("$[1].amount", is(200.0)));
    }


}




