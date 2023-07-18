package com.StandardBankingApp.demo.repository;

import com.StandardBankingApp.demo.dto.TransactionDto;
import com.StandardBankingApp.demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
//    List<Transaction> findByCreatedAt(String date);
    List<Transaction> findByAmountBetween(double minAmount, double maxAmount);

}
