package com.example.expense.tracker.UnitTest;

import com.example.expense.tracker.dto.ExpensePostUpdateDto;
import com.example.expense.tracker.dto.ExpenseViewDto;
import com.example.expense.tracker.enumeration.Category;
import com.example.expense.tracker.mapper.ExpenseMapperService;
import com.example.expense.tracker.model.Expense;
import com.example.expense.tracker.repository.ExpenseRepository;
import com.example.expense.tracker.service.impl.ExpenseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// -----------Given (Arrange) — When (Act) — Then (Assert / Verify)----------------
@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;
    @Mock
    private ExpenseMapperService expenseMapperService;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    // add test
    @Test
    void shouldCreateExpenseSuccessfully() {
        // given
        Expense expense = new Expense();
        ExpensePostUpdateDto post = new ExpensePostUpdateDto();
        post.setId(1L);
        post.setTitle("Lunch");
        post.setCategory(Category.FOOD);
        post.setAmount(BigDecimal.valueOf(25.0));
        post.setDate(LocalDate.now());

        when(expenseMapperService.convertPostUpdateDtoToEntity(post)).thenReturn(expense);
        when(expenseRepository.save(expense)).thenReturn(expense);

        // when
        expenseService.addExpense(post);

        // then
        verify(expenseMapperService, times(1)).convertPostUpdateDtoToEntity(post);
        verify(expenseRepository, times(1)).save(any(Expense.class));

    }

    // update test
    @Test
    void shouldUpdateExpenseSuccessfully() {
        // given
        Expense expense = new Expense();
        ExpensePostUpdateDto update = new ExpensePostUpdateDto();
        update.setId(1L);
        update.setTitle("Lunch");
        update.setCategory(Category.FOOD);
        update.setAmount(BigDecimal.valueOf(25.0));
        update.setDate(LocalDate.now());

        when(expenseRepository.findById(update.getId())).thenReturn(Optional.of(expense));
        when(expenseMapperService.convertPostUpdateDtoToEntity(update)).thenReturn(expense);
        when(expenseRepository.save(expense)).thenReturn(expense);

        // when
        expenseService.updateExpense(update);

        // then
        verify(expenseRepository, times(1)).findById(update.getId());
        verify(expenseMapperService, times(1)).convertPostUpdateDtoToEntity(update);
        verify(expenseRepository, times(1)).save(any(Expense.class));

    }

    // get by id test
    @Test
    void shouldGetExpenseByIdSuccessfully() {
        // given
        Expense expense = new Expense();
        expense.setId(1L);

        ExpenseViewDto viewDto = new ExpenseViewDto();
        viewDto.setId(1L);
        viewDto.setTitle("Lunch");
        viewDto.setCategory(Category.FOOD);
        viewDto.setAmount(BigDecimal.valueOf(25.0));
        viewDto.setDate(LocalDate.now());

        when(expenseRepository.findById(expense.getId())).thenReturn(Optional.of(expense));
        when(expenseMapperService.convertExpenseToViewDto(expense)).thenReturn(viewDto);

        // when
        ExpenseViewDto result = expenseService.getExpenseById(expense.getId());

        // then
        assertNotNull(result);
        assertEquals(viewDto.getId(), result.getId());

        verify(expenseRepository, times(1)).findById(1L);
        verify(expenseMapperService, times(1)).convertExpenseToViewDto(expense);

    }

    // get all test
    @Test
    void shouldGetAllExpensesSuccessfully() {
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

        ExpenseViewDto viewDto1 = new ExpenseViewDto();
        viewDto1.setId(2L);
        viewDto1.setTitle("Aspin");
        viewDto1.setCategory(Category.TRAVEL);
        viewDto1.setAmount(BigDecimal.valueOf(25.0));
        viewDto1.setDate(LocalDate.now());

        ExpenseViewDto viewDto2 = new ExpenseViewDto();
        viewDto1.setId(2L);
        viewDto1.setTitle("Aspin");
        viewDto1.setCategory(Category.TRAVEL);
        viewDto1.setAmount(BigDecimal.valueOf(25.0));
        viewDto1.setDate(LocalDate.now());

        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(expense1);
        expenses.add(expense2);
        ArrayList<ExpenseViewDto> viewDtos = new ArrayList<>();
        viewDtos.add(viewDto1);
        viewDtos.add(viewDto2);

        when(expenseRepository.findAll()).thenReturn(expenses);
        when(expenseMapperService.convertExpensesToViewDto(expenses)).thenReturn(viewDtos);

        // when
        List<ExpenseViewDto> result = expenseService.getAllExpenses();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(expenseRepository, times(1)).findAll();
        verify(expenseMapperService, times(1)).convertExpensesToViewDto(expenses);

    }

    //  when id not found
    @Test
    void shouldNotGetExpenseByIdWhenIdNotFound() {
        // given
        Expense expense = new Expense();
        expense.setId(1L);

        when(expenseRepository.findById(expense.getId())).thenReturn(Optional.empty());

        // when
        ExpenseViewDto result = expenseService.getExpenseById(expense.getId());

        // then
        assertNull(result);

        verify(expenseRepository, times(1)).findById(expense.getId());
    }

    // delete test
    @Test
    void shouldDeleteExpenseSuccessfully() {
        // given
        Expense expense = new Expense();
        expense.setId(1L);

        // when
        expenseService.deleteExpense(expense.getId());

        // then
        verify(expenseRepository, times(1)).deleteById(1L);
    }

}