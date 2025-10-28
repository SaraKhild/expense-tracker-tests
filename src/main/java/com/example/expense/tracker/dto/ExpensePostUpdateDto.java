package com.example.expense.tracker.dto;

import com.example.expense.tracker.enumeration.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class ExpensePostUpdateDto {

    private Long id;
    private String title;
    private BigDecimal amount;
    private Category category;
    private LocalDate date;

}