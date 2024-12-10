package com.example.UserService.Controller;

import com.example.UserService.DTO.ExpenseDTO;
import com.example.UserService.DTO.FinanceDTO;
import com.example.UserService.DTO.IncomeDTO;
import com.example.UserService.Email.EmailController;
import com.example.UserService.Entity.CustomerEntity;
import com.example.UserService.Entity.JwtRequest;
import com.example.UserService.Entity.JwtResponse;
import com.example.UserService.ExceptionHandling.ExpenseNotFoundException;
import com.example.UserService.ProxyServices.ExpenseControllerProxy;
import com.example.UserService.ProxyServices.FinanceControllerProxy;
import com.example.UserService.ProxyServices.IncomeControllerProxy;
import com.example.UserService.Repository.CustomerRepository;

import com.example.UserService.security.CustomerDetailsService;
import com.example.UserService.security.JwtService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/api/user")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private FinanceControllerProxy financeControllerProxy;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private IncomeControllerProxy incomeControllerProxy;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ExpenseControllerProxy expenseControllerProxy;

    @Autowired
    private EmailController emailController;

    /**
     *JWT token is generated for logging in
     * @param jwtRequest
     * @return jwt token
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {

            LOGGER.warn("Bad Credentials");
            return ResponseEntity.badRequest().body("Bad Credential");
        }

        UserDetails userDetails = customerDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtService.generateToken(userDetails.getUsername());
        System.out.println(token);

        LOGGER.info("JWT token is generated for logging in");
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * Adding new Customer......
     * @param customerEntity
     * @return
     */
    @ApiResponse(responseCode = "201", description = "adding new customer to the application",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = CustomerEntity.class))})
    @PostMapping("/addUser")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerEntity customerEntity ){

        Optional<CustomerEntity> customer = customerRepo.findByEmail(customerEntity.getEmail());

        if(!customer.isEmpty()){

            LOGGER.warn("User already exists, please login");
            return  ResponseEntity.badRequest().body("User already exists, please login");
        }

        customerEntity.setPassword(encoder.encode(customerEntity.getPassword()));
        customerRepo.save(customerEntity);

        LOGGER.info("Adding new Customer......");
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Customer added sucessfully!!!!");

    }

    /**
     * Fetching details of all the customers.......
     * @return customer list
     */

    @ApiResponse(responseCode = "200", description = "Get all the user details ",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = CustomerEntity.class))})
    @GetMapping("/getCustomers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllCustomers(){
        List<CustomerEntity> customersList = customerRepo.findAll();

        LOGGER.info("Fetching details of all the customers.......");
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(customersList);
    }

    /**
     * Fetching finance details of specific user.....
     * @param customerId
     * @return finance details
     */
    @ApiResponse(responseCode = "200", description = "Get user's finance details",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = FinanceDTO.class))})
    @GetMapping("/getFinanceByCustomer/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getFinanceDetails(@PathVariable int customerId){

        LOGGER.info("Fetching finance details of specific user.....");
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(financeControllerProxy.getFinanceById(customerId));
    }

    /**
     * New income is successfully added to the customer
     * @param customerId
     * @param income
     * @return
     */
    @ApiResponse(responseCode = "201", description = "adding new Income to customer,",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = IncomeDTO.class))})
    @PostMapping("/postNewIncomeById/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> postNewIncomeByID(@PathVariable int customerId, @RequestBody IncomeDTO income){

        income.setCustomerId(customerId);

        double incomeAmount = income.getIncomeAmount();

        financeControllerProxy.updateFinanceById(customerId,incomeAmount);
        String response = incomeControllerProxy.saveIncome(income);

        LOGGER.info("incomer added to the user with customer Id {}",customerId);

        return  ResponseEntity.status(HttpStatusCode.valueOf(201)).body(response);

    }

    @ApiResponse(responseCode = "200", description = "Adding new expense to customer",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = ExpenseDTO.class))})
    @PostMapping("/PostNewExpenseForCustomer/{customerId}")
    public ResponseEntity<?> postNewExpenseById(@PathVariable int customerId, @RequestBody ExpenseDTO expense){
        expense.setUserId(customerId);

         CustomerEntity customerEntity = customerRepo.findById(customerId).orElse(null);

        assert customerEntity != null;
        String customerEmail = customerEntity.getEmail();


        double expenseAmount = expense.getAmountSpent();

        double budgetLimit = financeControllerProxy.getFinanceById(customerId).getBudgetLimit();

        if(budgetLimit < expenseAmount){
            LOGGER.error("Your expense amount is greater than budget limit");


           // String email = emailController.sendEmail(customerEmail, "Expense notification"," Your expense amount reached the budget Limit, expense can not be happening...");
            return  ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Expense can not happen");
        }

        LOGGER.info("customer Id : " + customerId + " AmountSpent : "+ expenseAmount + ".....................................................");
        financeControllerProxy.updateFinanceAfterExpense(customerId,expenseAmount);

        String response = expenseControllerProxy.saveExpenceforUser(expense);

        return  ResponseEntity.status(HttpStatusCode.valueOf(201)).body(response);

    }


    @PostMapping("/addFinanceByCustomer/{customerId}")
    public  ResponseEntity<?> addFinanceById(@PathVariable int customerId, @RequestBody FinanceDTO finance){

        finance.setCustomerId(customerId);
        String response = financeControllerProxy.addFinance(finance);

        return  ResponseEntity.status(HttpStatusCode.valueOf(201)).body(response);

    }

    @ApiResponse(responseCode = "200", description = "getting expense details by Id",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = ExpenseDTO.class))})
    @GetMapping("/getExpensesById/{customerId}")
    public  ResponseEntity<?> getExpensesById(@PathVariable int customerId) throws ExpenseNotFoundException {

        List<ExpenseDTO> expenses = expenseControllerProxy.getExpensesByCustomer(customerId);

        if(expenses.isEmpty()){
            LOGGER.info("there are no expenses for the customer with id {} ........", customerId);

            throw  new ExpenseNotFoundException("expense for the user id "+ customerId + " is not present");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(expenses);

    }

    /**
     *
     * @param customerId
     * @param category
     * @return
     * @throws ExpenseNotFoundException
     */
    @ApiResponse(responseCode = "200", description = "getting expense details by Id and category",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = ExpenseDTO.class))})
    @GetMapping("/getExpensesByIdAndCategory/{customerId}/{category}")
    public  ResponseEntity<?> getExpensesByCategory(@PathVariable int customerId, @PathVariable String category) throws ExpenseNotFoundException {

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(expenseControllerProxy.getExpensesByUserAndCategory(customerId,category));

    }


    @ApiResponse(responseCode = "200", description = "getting expense details by Id and date",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = ExpenseDTO.class))})
    @GetMapping("/getExpensesByIdAndDate/{customerId}/{date}")
    public  ResponseEntity<?> getExpensesByDate(@PathVariable int customerId, @PathVariable Date date) throws ExpenseNotFoundException {

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(expenseControllerProxy.getExpensesByUserAndDate(customerId,date));

    }
}
