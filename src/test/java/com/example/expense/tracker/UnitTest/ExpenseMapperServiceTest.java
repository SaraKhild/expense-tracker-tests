package com.example.expense.tracker.UnitTest;

import com.example.expense.tracker.dto.ExpensePostUpdateDto;
import com.example.expense.tracker.dto.ExpenseViewDto;
import com.example.expense.tracker.enumeration.Category;
import com.example.expense.tracker.mapper.ExpenseMapperService;
import com.example.expense.tracker.mapper.impl.ExpenseMapperServiceImpl;
import com.example.expense.tracker.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExpenseMapperServiceTest {

    private ExpenseMapperService expenseMapperService;

    @BeforeEach
    void setUp() {
        expenseMapperService = new ExpenseMapperServiceImpl();
    }

    @Test
    void shouldConvertExpensePostUpdateDtoToExpenseSuccessfully() {
        // give
        ExpensePostUpdateDto expensePostUpdateDto = new ExpensePostUpdateDto();
        expensePostUpdateDto.setId(1L);
        expensePostUpdateDto.setTitle("Lunch");
        expensePostUpdateDto.setCategory(Category.FOOD);
        expensePostUpdateDto.setAmount(BigDecimal.valueOf(25.0));
        expensePostUpdateDto.setDate(LocalDate.now());

        // when
        Expense expense = expenseMapperService.convertPostUpdateDtoToEntity(expensePostUpdateDto);

        // then
        assertNotNull(expense);

    }

    @Test
    void shouldConvertExpenseToViewDtoSuccessfully() {
        // given
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setTitle("Lunch");
        expense.setCategory(Category.FOOD);
        expense.setAmount(BigDecimal.valueOf(25.0));
        expense.setDate(LocalDate.now());

        // when
        ExpenseViewDto result = expenseMapperService.convertExpenseToViewDto(expense);

        // then
        assertNotNull(result);
        assertEquals(expense.getId(), result.getId());
        assertEquals(expense.getTitle(), result.getTitle());
        assertEquals(expense.getCategory(), result.getCategory());
        assertEquals(expense.getAmount(), result.getAmount());
        assertEquals(expense.getDate(), result.getDate());

    }

    @Test
    void shouldConvertExpensesToViewDtoSuccessfully() {
        // given
        Expense expense1 = new Expense();
        expense1.setId(1L);
        expense1.setTitle("Lunch");
        expense1.setCategory(Category.FOOD);
        expense1.setAmount(BigDecimal.valueOf(25.0));
        expense1.setDate(LocalDate.now());

        Expense expense2 = new Expense();
        expense2.setId(1L);
        expense2.setTitle("Lunch");
        expense2.setCategory(Category.FOOD);
        expense2.setAmount(BigDecimal.valueOf(25.0));
        expense2.setDate(LocalDate.now());

        // when
        List<ExpenseViewDto> result = expenseMapperService.convertExpensesToViewDto(List.of(expense1, expense2));

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

    }

}