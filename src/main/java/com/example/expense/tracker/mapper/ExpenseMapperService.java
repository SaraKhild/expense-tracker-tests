package com.example.expense.tracker.mapper;

import com.example.expense.tracker.dto.ExpensePostUpdateDto;
import com.example.expense.tracker.dto.ExpenseViewDto;
import com.example.expense.tracker.model.Expense;

import java.util.List;

public interface ExpenseMapperService {

    Expense convertPostUpdateDtoToEntity(ExpensePostUpdateDto expensePostUpdateDto);

    ExpenseViewDto convertExpenseToViewDto(Expense expense);

    List<ExpenseViewDto> convertExpensesToViewDto(List<Expense> expenses);

}