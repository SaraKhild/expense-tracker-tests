package com.example.expense.tracker.service;

import com.example.expense.tracker.dto.ExpensePostUpdateDto;
import com.example.expense.tracker.dto.ExpenseViewDto;

import java.util.List;

public interface ExpenseService {

    List<ExpenseViewDto> getAllExpenses();

    ExpenseViewDto getExpenseById(Long id);

    void addExpense(ExpensePostUpdateDto expensePostUpdateDto);

    void updateExpense(ExpensePostUpdateDto expensePostUpdateDto);

    void deleteExpense(Long id);

}