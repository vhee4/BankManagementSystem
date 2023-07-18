package com.StandardBankingApp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String transactionType;
    private String accountNumber;
    private BigDecimal amount;
    private LocalDate transactionDate;
}
