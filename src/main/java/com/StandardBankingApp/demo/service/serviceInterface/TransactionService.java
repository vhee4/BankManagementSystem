package com.StandardBankingApp.demo.service.serviceInterface;

import com.StandardBankingApp.demo.dto.TransactionDto;
import com.StandardBankingApp.demo.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
    List<TransactionDto> fetchAllTransactionsByUser(User newUser);
//    List<TransactionDto> getAllTransactionsByDate(String date);


    List<TransactionDto> filterByAmount(double minAmount, double maxAmount);
}
