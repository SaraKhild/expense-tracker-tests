# Comprehensive Testing in Spring Boot: Unit & Integration Tests
![https://www.guru99.com/images/Unit-Test-vs-Integration-Test.png](https://www.guru99.com/images/Unit-Test-vs-Integration-Test.png)
<br>

## Overview
 -  <strong>Unit Testing:</strong>
      <br>
      <mark>Unit tests</mark> focus on <mark>testing individual components or classes</mark> in isolation. They <mark>verify</mark> that each <mark>method or function behaves as expected without relying on external systems</mark> like databases or APIs.
    * Typically uses JUnit and Mockito for mocking dependencies.
    * Fast to execute.
-  <strong>Integration Testing:</strong>
      <br>
       <mark>Integration tests</mark> focus on testing <mark>how different components of the application work together</mark>. They often involve <mark>real components like databases, REST endpoints, or messaging systems</mark>.
    * Typically uses Spring Boot Test with @SpringBootTest, TestRestTemplate, or MockMvc.
    * Slower than unit tests but ensures the system behaves correctly end-to-end.  
  
## Usages
-  Junit  
-  Mockito
-  Spring Test
-  MySql

## Architecture of the Project

 ### 1-src folder
   - controller
   - dto
   - model
   - enumeration
   - repository
   - service
     
 ### 2-resources folder
   - application.properties

### 3- test folder
   -  integrationTest
   -  unitTest
     
### 2-Maven pom.xml
<br> 
    
```
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter</artifactId>
			<version>5.10.0</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-junit-jupiter</artifactId>
			<version>5.12.0</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
 ```

<br>

 ## Let's Start :mechanical_arm:
 ### 1- Unit Test:
 #### • Expense Mapper Service Test

##### :pencil2: `Sets up a new ExpenseMapperServiceImpl instance before every test run.` 

###### Code :computer:

<br>

```java
    private ExpenseMapperService expenseMapperService;

    @BeforeEach
    void setUp() {
        expenseMapperService = new ExpenseMapperServiceImpl();
    }
```

---

##### :pencil2: `shouldConvertExpensePostUpdateDtoToExpenseSuccessfully()` 
   Tests that ExpenseMapperService correctly converts an ExpensePostUpdateDto object into an Expense entity.
##### :pencil2: `shouldConvertExpenseToViewDtoSuccessfully()` 
   Verifies that an Expense entity is accurately mapped to an ExpenseViewDto, ensuring all fields are copied correctly.
##### :pencil2: `shouldConvertExpensesToViewDtoSuccessfully()` 
   Checks that a list of Expense entities is properly converted into a list of ExpenseViewDto objects.   

###### Code :computer:

<br>

```java
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
```

<br>

###### Result :star_struck:

<img width="1164" height="193" alt="Screenshot 1447-05-07 at 1 58 28 PM" src="https://github.com/user-attachments/assets/68c2cbc3-3371-4929-b633-f79ed7390b31" />

<br>  <br>

---

 ### • Expense Service Test

##### :pencil2: `@ExtendWith(MockitoExtension.class)` 
  Enables Mockito support in JUnit 5 tests — allows the use of @Mock and @InjectMocks annotations.
##### :pencil2: `@Mock`
 Creates mock objects expenseRepository and expenseMapperService for simulates.
##### :pencil2: `@InjectMocks` 
  Automatically injects the above mocks into ExpenseServiceImp.

###### Code :computer:

<br>


```java
    @ExtendWith(MockitoExtension.class)
    public class ExpenseServiceTest {
    
        @Mock
        private ExpenseRepository expenseRepository;
        @Mock
        private ExpenseMapperService expenseMapperService;
    
        @InjectMocks
        private ExpenseServiceImpl expenseService;
```

---

##### :pencil2: `shouldCreateExpenseSuccessfully()` 
   This test verifies that a new expense is created correctly.
   It sets up an ExpensePostUpdateDto object with sample data and mocks the behavior of expenseMapperService and expenseRepository.
   After calling expenseService.addExpense(post), the test checks that both the mapper’s convertPostUpdateDtoToEntity()
   and the repository’s save() methods are called exactly once — confirming that the expense creation process works as expected.
##### :pencil2: `shouldUpdateExpenseSuccessfully()` 
   This test ensures that an existing expense is updated properly.
   It mocks the repository to return an existing Expense when searched by ID, and it mocks the mapper to convert the incoming update DTO to an entity.
   After executing expenseService.updateExpense(update), it verifies that the repository findById(), 
   mapper convertPostUpdateDtoToEntity(), and repository save() methods are all called once — confirming that the update flow functions correctly.
##### :pencil2: `shouldGetExpenseByIdSuccessfully()` 
   This test checks that an expense can be retrieved by its ID.
   It mocks the repository to return an Expense and the mapper to convert it into an ExpenseViewDto.
   After invoking expenseService.getExpenseById(id), it asserts that the result is not null and that the returned DTO’s ID matches the expected value.
   Finally, it verifies that the repository findById() and mapper convertExpenseToViewDto() are both called once —
   confirming proper interaction between service, repository, and mapper.  
##### :pencil2: `shouldGetAllExpensesSuccessfully()` 
   This test verifies that the service can successfully fetch and map all expenses.
   It prepares two Expense objects and their corresponding ExpenseViewDto objects, then mocks the repository and mapper accordingly.
   When expenseService.getAllExpenses() is called, it asserts that the result is not null and has the expected size.
   The test also confirms that findAll() and convertExpensesToViewDto() are invoked once — ensuring the “get all” functionality works correctly. 
##### :pencil2: `shouldNotGetExpenseByIdWhenIdNotFound()` 
   This test ensures that when an expense with the given ID does not exist, the service handles it gracefully.
   It mocks the repository to return an empty Optional.
   After calling expenseService.getExpenseById(id), it asserts that the returned value is null and verifies that the repository’s findById() method was called once — 
   confirming correct behavior for non-existing records.   
##### :pencil2: `shouldDeleteExpenseSuccessfully()` 
   This test confirms that the delete operation works properly.
   It calls expenseService.deleteExpense(id) directly and then verifies that the repository’s deleteById() method is invoked once with the correct ID —
   ensuring that the delete functionality triggers as expected.  

###### Code :computer:

<br>

```java

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
```

<br>

###### Result :star_struck:

<img width="1473" height="258" alt="Screenshot 1447-05-07 at 2 46 11 PM" src="https://github.com/user-attachments/assets/44a6ce97-890b-46e6-a71c-1d3108340c18" />

<br> <br>

---

 ### • Expense Controller Test

##### :pencil2: `@ExtendWith(MockitoExtension.class)` 
  Enables Mockito support in JUnit 5 tests — allows the use of @Mock and @InjectMocks annotations.
##### :pencil2: `@Mock`
  Creates mock object ExpenseServiceImpl for simulate.
##### :pencil2: `MockMvc` 
  To simulate HTTP requests to your controller endpoints.
##### :pencil2: `@BeforeEach` 
  Set up expenseController for initializes the MockMvc object before every test. , meaning you’re testing only this controller (not the whole Spring context).  

###### Code :computer:

<br>

```java
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
```

---

##### :pencil2: `shouldGetExpensesSuccessfully()` 
  This test verifies that all expenses are retrieved and mapped correctly.
  It sets up a list of ExpenseViewDto objects with sample data and mocks the behavior of expenseService.getAllExpenses().
  After performing a GET request, the test checks that the JSON response matches the expected structure and that the service method is called exactly once — 
  confirming that the mapping from service DTOs to JSON works as expected.
##### :pencil2: `shouldGetExpenseByIdSuccessfully()` 
  This test verifies that a single expense is retrieved and mapped correctly by its ID.
  It sets up an ExpenseViewDto with sample data and mocks expenseService.getExpenseById(id) to return it.
  After performing a GET request with the expense ID, the test checks that the JSON response matches the expected fields and 
  ensures that the service method is called exactly once with the correct ID — confirming proper mapping and retrieval of a single expense.
##### :pencil2: `shouldAddExpenseSuccessfully()` 
  This test verifies that a new expense is added correctly.
  It prepares a JSON payload representing an ExpensePostUpdateDto and performs a POST request.
  The test checks that expenseService.addExpense() is called exactly once with a DTO containing the expected data,
  and confirms that the response message indicates successful creation — ensuring that incoming JSON is correctly mapped to a service DTO and processed. 
##### :pencil2: `shouldUpdateExpenseSuccessfully()` 
 This test verifies that an existing expense is updated correctly.
 It prepares a JSON payload with the updated fields and performs a PUT request.
 The test ensures that expenseService.updateExpense() is called exactly once with the correctly mapped ExpensePostUpdateDto and
 that the response confirms successful update — validating the mapping and update workflow.
##### :pencil2: `shouldDeleteExpenseByIdSuccessfully()` 
 This test verifies that an expense is deleted correctly by its ID.
 It performs a DELETE request with the expense ID and checks that expenseService.deleteExpense(id) is called exactly once.
 The test also confirms that the response indicates successful deletion — ensuring the path variable is correctly mapped and processed by the service.     

###### Code :computer:

<br>

```java

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
```

<br>

###### Result :star_struck:

<img width="1411" height="225" alt="Screenshot 1447-05-07 at 3 30 18 PM" src="https://github.com/user-attachments/assets/4b39c93e-c523-4bcc-886a-8097324b0d38" />

<br> <br>

---

 ### 2- Integration Test:

  ### • Expense Integration Test

##### :pencil2: `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)` 
  It runs the full Spring context on a random port to test actual HTTP requests and persistence behavior.
##### :pencil2: `@BeforeAll`
  A static RestTemplate is initialized in @BeforeAll to perform HTTP requests to the running application.
##### :pencil2: `@BeforeEach` 
 This method prepares the test data before each test:
  - Constructs the baseUrl pointing to the running API on the random port.
  - Creates an ExpensePostUpdateDto with sample data (title, category, amount, date) and calls expenseService.addExpense(post) to persist it.
  - Retrieves all expenses from the service, maps the first entry to an ExpenseViewDto, and stores it in expenseViewDto.
  - Calls expenseService.getExpenseById(id) to ensure the retrieval mapping works and to simulate a typical read operation.
##### :pencil2: `@AfterEach` 
 Cleans up the database after each test by calling expenseRepository.deleteAll().

###### Code :computer:

<br>

```java

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
```

---

##### :pencil2: `getAllExpense()` 
  This test verifies that all expenses can be retrieved successfully via the REST API.
  It performs a GET request to the /api/expense endpoint using RestTemplate and expects a list of ExpenseViewDto objects.
  The test asserts that the response is not null, has HTTP status 200 OK, and contains the expected number of expense entries.
  This confirms that the service retrieves and maps entities to DTOs correctly and that the API returns them properly in JSON format.
  ##### :pencil2: `getExpenseById()` 
  This test verifies that a single expense can be retrieved by its ID.
  It performs a GET request to /api/expense/{id} and expects an ExpenseViewDto.
  The test asserts that the response is not null, has HTTP status 200 OK, and that the returned DTO contains the correct title.
  This ensures proper mapping of the entity to the DTO and correct endpoint behavior for fetching individual expenses.
##### :pencil2: `addExpense()` 
  This test verifies that a new expense can be created through the REST API.
  It constructs an ExpensePostUpdateDto with sample data and performs a POST request to /api/expense.
  The test asserts that the response has HTTP status 200 OK and returns the expected success message.
  This confirms that the API correctly maps the incoming DTO to the entity, calls the service to persist it, and returns a proper response.
##### :pencil2: `updateExpense()` 
  This test verifies that an existing expense can be updated via the REST API.
  It modifies the title of an existing ExpenseViewDto and performs a PUT request to /api/expense.
  Then it fetches the updated expense with a GET request and asserts that the changes are persisted.
  This ensures that updates correctly map from the DTO to the entity, are processed by the service, and are reflected in subsequent API responses.
##### :pencil2: `deleteExpense()` 
  This test verifies that an expense can be deleted through the REST API.
  It first confirms the current number of expenses, performs a DELETE request to /api/expense/{id}, and then checks that the expense count has decreased.
  This confirms that the entity is correctly removed from the repository via the service and that the API reflects this deletion.

###### Code :computer:

<br>

```java

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
```

<br>

###### Result :star_struck:

<img width="1137" height="254" alt="Screenshot 1447-05-07 at 4 07 10 PM" src="https://github.com/user-attachments/assets/9d94e883-657a-4946-9b04-3431952674c2" />

---

### Good Luck <img src="https://media.giphy.com/media/hvRJCLFzcasrR4ia7z/giphy.gif" width="30px"> 
