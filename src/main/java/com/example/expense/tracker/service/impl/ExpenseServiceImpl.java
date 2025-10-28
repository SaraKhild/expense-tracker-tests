package com.example.expense.tracker.service.impl;

import com.example.expense.tracker.dto.ExpensePostUpdateDto;
import com.example.expense.tracker.dto.ExpenseViewDto;
import com.example.expense.tracker.mapper.ExpenseMapperService;
import com.example.expense.tracker.model.Expense;
import com.example.expense.tracker.repository.ExpenseRepository;
import com.example.expense.tracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapperService expenseMapperService;

    @Override
    public List<ExpenseViewDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenseMapperService.convertExpensesToViewDto(expenses);
    }

    @Override
    public ExpenseViewDto getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expenseMapperService.convertExpenseToViewDto(expense.get());
    }

    @Override
    public void addExpense(ExpensePostUpdateDto expensePostUpdateDto) {
        Expense expense = expenseMapperService.convertPostUpdateDtoToEntity(expensePostUpdateDto);
        expenseRepository.save(expense);
    }

    @Override
    public void updateExpense(ExpensePostUpdateDto expensePostUpdateDto) {
        Optional<Expense> expenseById = expenseRepository.findById(expensePostUpdateDto.getId());
        if (expenseById.isPresent()) {
            Expense expense = expenseMapperService.convertPostUpdateDtoToEntity(expensePostUpdateDto);
            expenseRepository.save(expense);
        }
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

}