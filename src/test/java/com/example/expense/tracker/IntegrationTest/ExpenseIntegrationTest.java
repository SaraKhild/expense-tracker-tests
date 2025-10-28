package com.example.expense.tracker.IntegrationTest;


import com.example.expense.tracker.dto.ExpensePostUpdateDto;
import com.example.expense.tracker.dto.ExpenseViewDto;
import com.example.expense.tracker.enumeration.Category;
import com.example.expense.tracker.repository.ExpenseRepository;
import com.example.expense.tracker.service.ExpenseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExpenseIntegrationTest {

    private static RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseService expenseService;

    private String baseUrl;
    private ExpenseViewDto expenseViewDto;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/expense";

        ExpensePostUpdateDto post = new ExpensePostUpdateDto();
        post.setTitle("Lunch");
        post.setCategory(Category.FOOD);
        post.setAmount(BigDecimal.valueOf(40.0));
        post.setDate(LocalDate.now());

        expenseService.addExpense(post);

        List<ExpenseViewDto> expenseViewDtoList = expenseService.getAllExpenses();

        expenseViewDto = new ExpenseViewDto();
        expenseViewDto.setId(expenseViewDtoList.get(0).getId());
        expenseViewDto.setTitle(expenseViewDtoList.get(0).getTitle());
        expenseViewDto.setCategory(expenseViewDtoList.get(0).getCategory());
        expenseViewDto.setAmount(expenseViewDtoList.get(0).getAmount());
        expenseViewDto.setDate(expenseViewDtoList.get(0).getDate());

        expenseService.getExpenseById(expenseViewDto.getId());

    }

    @AfterEach
    public void cleanUp() {
        expenseRepository.deleteAll();
    }

    @Test
    void getAllExpense() {
        ResponseEntity<List<ExpenseViewDto>> responses = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ExpenseViewDto>>() {
                }
        );

        assertNotNull(responses);
        assertEquals(HttpStatus.OK, responses.getStatusCode());
        assertNotNull(responses.getBody());
        assertEquals(1, responses.getBody().size());

    }

    @Test
    void getExpenseById() {
        ResponseEntity<ExpenseViewDto> response = restTemplate.getForEntity(baseUrl + "/" + expenseViewDto.getId(), ExpenseViewDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Lunch", response.getBody().getTitle());

    }

    @Test
    void addExpense() {
        ExpensePostUpdateDto post = new ExpensePostUpdateDto();

        post.setTitle("Dinner");
        post.setCategory(Category.FOOD);
        post.setAmount(BigDecimal.valueOf(30.0));
        post.setDate(LocalDate.now());

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, post, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully added expense", response.getBody());

    }

    @Test
    void updateExpense() {
        expenseViewDto.setTitle("Lunch :)");

        restTemplate.put(baseUrl, expenseViewDto);

        ResponseEntity<ExpenseViewDto> response = restTemplate.getForEntity(baseUrl + "/" + expenseViewDto.getId(), ExpenseViewDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Lunch :)", response.getBody().getTitle());

    }

    @Test
    void deleteExpense() {
        int recount = expenseService.getAllExpenses().size();
        assertEquals(1, recount);

        restTemplate.delete(baseUrl + "/" + expenseViewDto.getId());

        assertEquals(0, expenseService.getAllExpenses().size());

    }

}