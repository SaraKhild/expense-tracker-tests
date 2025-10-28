package com.example.expense.tracker.mapper.impl;

import com.example.expense.tracker.dto.ExpensePostUpdateDto;
import com.example.expense.tracker.dto.ExpenseViewDto;
import com.example.expense.tracker.mapper.ExpenseMapperService;
import com.example.expense.tracker.model.Expense;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseMapperServiceImpl implements ExpenseMapperService {

    @Override
    public List<ExpenseViewDto> convertExpensesToViewDto(List<Expense> expenses) {

        ArrayList<ExpenseViewDto> expenseViewDtos = new ArrayList<>();
        expenses.forEach(expense -> {
            expenseViewDtos.add(convertExpenseToViewDto(expense));
        });

        return expenseViewDtos;

    }

    @Override
    public ExpenseViewDto convertExpenseToViewDto(Expense expense) {
        ExpenseViewDto expenseViewDto = new ExpenseViewDto();

        expenseViewDto.setId(expense.getId());
        expenseViewDto.setTitle(expense.getTitle());
        expenseViewDto.setCategory(expense.getCategory());
        expenseViewDto.setAmount(expense.getAmount());
        expenseViewDto.setDate(expense.getDate());

        return expenseViewDto;

    }

    @Override
    public Expense convertPostUpdateDtoToEntity(ExpensePostUpdateDto expensePostUpdateDto) {
        Expense expense = new Expense();

        expense.setId(expensePostUpdateDto.getId());
        expense.setTitle(expensePostUpdateDto.getTitle());
        expense.setCategory(expensePostUpdateDto.getCategory());
        expense.setAmount(expensePostUpdateDto.getAmount());
        expense.setDate(expensePostUpdateDto.getDate());

        return expense;

    }

}