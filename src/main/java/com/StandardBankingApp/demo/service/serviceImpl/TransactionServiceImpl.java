package com.StandardBankingApp.demo.service.serviceImpl;

import com.StandardBankingApp.demo.dto.TransactionDto;
import com.StandardBankingApp.demo.entity.Transaction;
import com.StandardBankingApp.demo.entity.User;
import com.StandardBankingApp.demo.repository.TransactionRepository;
import com.StandardBankingApp.demo.service.serviceInterface.TransactionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction newTransaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .build();

        transactionRepository.save(newTransaction);

    }

    @Cacheable(cacheNames = "fetchAllTransactions")
    public List<TransactionDto> fetchAllTransactionsByUser(User user){
        List<Transaction> transactionList = transactionRepository.findAll();
        return transactionList.stream().map(list->TransactionDto.builder()
                        .transactionType(list.getTransactionType())
                        .accountNumber(list.getAccountNumber())
                        .amount(list.getAmount())
                        .transactionDate(list.getCreatedAt())
                        .build()).collect(Collectors.toList());
    }

//    public List<TransactionDto> getAllTransactionsByDate(String date){
//        List<Transaction> transactionList = transactionRepository.findByCreatedAt(date);
//        return transactionList.stream().map((transaction -> mappedToTransferResponse(transaction))).collect(Collectors.toList());
//    }

    @Override
    @Cacheable(cacheNames = "filterByAmount")
    public List<TransactionDto> filterByAmount(double minAmount, double maxAmount) {
//        System.out.println("fetching records from cache");
        List<Transaction> transactionList = getRecordFromDB(minAmount,maxAmount);
        return transactionList.stream().map((transaction -> mappedToTransferResponse(transaction))).collect(Collectors.toList());
    }

    private List<Transaction> getRecordFromDB(double minAmount, double maxAmount) {
        System.out.println("fetching records from DB");
        return transactionRepository.findByAmountBetween(minAmount, maxAmount);
    }


    private TransactionDto mappedToTransferResponse(Transaction transaction) {
        return TransactionDto.builder()
                .amount(transaction.getAmount())
                .transactionDate(transaction.getCreatedAt())
                .accountNumber(transaction.getAccountNumber())
                .transactionType(transaction.getTransactionType())
                .build();
    }


}
