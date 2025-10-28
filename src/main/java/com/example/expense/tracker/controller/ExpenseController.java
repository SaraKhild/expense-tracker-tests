package com.example.expense.tracker.controller;

import com.example.expense.tracker.dto.ExpensePostUpdateDto;
import com.example.expense.tracker.dto.ExpenseViewDto;
import com.example.expense.tracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseViewDto>> getExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseViewDto> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PostMapping
    public ResponseEntity<String> addExpense(@RequestBody ExpensePostUpdateDto expense) {
        expenseService.addExpense(expense);
        return ResponseEntity.ok("Successfully added expense");
    }

    @PutMapping
    public ResponseEntity<String> updateExpense(@RequestBody ExpensePostUpdateDto expense) {
        expenseService.updateExpense(expense);
        return ResponseEntity.ok("Successfully updated expense");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Successfully deleted expense");
    }

}