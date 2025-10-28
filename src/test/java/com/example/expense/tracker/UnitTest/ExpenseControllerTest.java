package com.example.expense.tracker.UnitTest;

import com.example.expense.tracker.controller.ExpenseController;
import com.example.expense.tracker.dto.ExpensePostUpdateDto;
import com.example.expense.tracker.dto.ExpenseViewDto;
import com.example.expense.tracker.enumeration.Category;
import com.example.expense.tracker.service.impl.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class ExpenseControllerTest {

    @Mock
    private ExpenseServiceImpl expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();
    }

    @Test
    void shouldGetExpensesSuccessfully() throws Exception {
        ExpenseViewDto expenseViewDto = new ExpenseViewDto();

        expenseViewDto.setId(1L);
        expenseViewDto.setTitle("Lunch");
        expenseViewDto.setCategory(Category.FOOD);
        expenseViewDto.setAmount(BigDecimal.valueOf(26));
        expenseViewDto.setDate(LocalDate.of(2025, 10, 26));

        List<ExpenseViewDto> expenses = List.of(expenseViewDto);

        when(expenseService.getAllExpenses()).thenReturn(expenses);

        String json = """
                [
                   {
                     "id": 1,
                     "title": "Lunch",
                     "category": "FOOD",
                     "amount": 26.0,
                     "date": [2025, 10, 26]
                   }
                ]
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/expense"))
                .andExpect(status().isOk())
                .andExpect(content().json(json, JsonCompareMode.STRICT));

        verify(expenseService, times(1)).getAllExpenses();

    }

    @Test
    void shouldGetExpenseByIdSuccessfully() throws Exception {
        ExpenseViewDto expenseViewDto = new ExpenseViewDto();

        expenseViewDto.setId(1L);
        expenseViewDto.setTitle("Lunch");
        expenseViewDto.setCategory(Category.FOOD);
        expenseViewDto.setAmount(BigDecimal.valueOf(26));
        expenseViewDto.setDate(LocalDate.of(2025, 10, 26));

        when(expenseService.getExpenseById(expenseViewDto.getId())).thenReturn(expenseViewDto);

        String json = """
                   {
                     "id": 1,
                     "title": "Lunch",
                     "category": "FOOD",
                     "amount": 26.0,
                     "date": [2025, 10, 26]
                   }
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/expense/{id}", expenseViewDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(json, JsonCompareMode.STRICT));

        verify(expenseService, times(1)).getExpenseById(expenseViewDto.getId());

    }

    @Test
    void shouldAddExpenseSuccessfully() throws Exception {
        String json = """
                {
                  "title": "Lunch",
                  "category": "FOOD",
                  "amount": 25.0,
                  "date": "2025-10-26"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully added expense"));

        verify(expenseService, times(1)).addExpense(any(ExpensePostUpdateDto.class));

    }

    @Test
    void shouldUpdateExpenseSuccessfully() throws Exception {
        String json = """
                {
                  "id": 1,
                  "title": "Lunch",
                  "category": "FOOD",
                  "amount": 26.0,
                  "date": "2025-10-26"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully updated expense"));

        verify(expenseService, times(1)).updateExpense(any(ExpensePostUpdateDto.class));

    }

    @Test
    void shouldDeleteExpenseByIdSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/expense/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully deleted expense"));

        verify(expenseService, times(1)).deleteExpense(1L);

    }

}