package com.StandardBankingApp.demo.controller;

import com.StandardBankingApp.demo.dto.*;
import com.StandardBankingApp.demo.service.serviceInterface.TransactionService;
import com.StandardBankingApp.demo.service.serviceInterface.UserService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Banking Application",
                description = "Spring Boot Banking Application REST APIs Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Chidinma",
                        email = "vivianafogu@gmail.com",
                        url = "https://github.com/vhee4"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://github.com/vhee4"
                )

        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Banking Application",
                url = "https://github.com/vhee4"
        )
//        @Tag(
//
//        )

)
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;


    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public UserResponse registerUser(@RequestBody UserRequest uSerRequest) {
        return userService.registerUser(uSerRequest);
    }

    @GetMapping("/{userid}")
    public UserResponse fetchUser(@PathVariable("userid") Long userId){
        return userService.fetchUser(userId);
    }

    @GetMapping
    public List<UserResponse> allUsers(){
        return userService.allUsers();
    }

    @GetMapping("/balEnquiry")
    public UserResponse balanceEnquiry(@RequestParam("accountNumber") String accountNumber){
        return userService.balanceEnquiry(accountNumber);
    }
    @GetMapping("/nameEnquiry")
    public UserResponse nameEnquiry(@RequestParam("accountNumber") String accountNumber){
        return userService.nameEnquiry(accountNumber);
    }

    @PutMapping("/credit")
    public UserResponse credit(@RequestBody TransactionRequest transactionRequest){
        return userService.credit(transactionRequest);
    }

    @PutMapping("/debit")
    public UserResponse debit(@RequestBody TransactionRequest transactionRequest){
        return userService.debit(transactionRequest);
    }

    @DeleteMapping("/delete")
    public UserResponse deleteUser(@RequestBody String accountNumber){
        return userService.deleteUser(accountNumber);
    }

    @GetMapping("/allTransactions")
    public List<TransactionDto> fetchAllTransactionsByUser(@RequestBody String accountNumber){
        return userService.fetchAllTransactionsByUser(accountNumber);
    }

//    @GetMapping("/transactionsByDate")
//    public List<TransactionDto> transactionsByDate(@RequestParam String date){
//        return userService.fetchTransactionsByDate(date);
//    }


//    @GetMapping("/allTransactionsByDate")
//    public List<TransactionDto> getAllTransactionsByDate(@RequestParam String date){
//        return userService.getAllTransactionsByDate(date);
//    }

    @PutMapping("/transfer")
    public UserResponse transfer(@RequestBody TransferRequest transferRequest){
        return userService.transfer(transferRequest);
    }








}
