package com.StandardBankingApp.demo.controller;

import com.StandardBankingApp.demo.dto.TransactionDto;
import com.StandardBankingApp.demo.service.serviceInterface.TransactionService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @GetMapping("/filterByAmount")
    public List<TransactionDto> filterByAmount(@RequestParam(name = "minAmount") double minAmount, @RequestParam(name = "maxAmount") double maxAmount){
        return transactionService.filterByAmount(minAmount,maxAmount);
    }


}
