package com.StandardBankingApp.demo.service.serviceInterface;

import com.StandardBankingApp.demo.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRequest userRequest);

    List<UserResponse> allUsers();
     UserResponse fetchUser(Long userId);

     UserResponse balanceEnquiry(String accountNumber);
    UserResponse nameEnquiry(String accountNumber);

    UserResponse credit(TransactionRequest transactionRequest);
    UserResponse debit(TransactionRequest transactionRequest);

    UserResponse transfer(TransferRequest transferRequest);

    UserResponse deleteUser(String accountNumber);
    List<TransactionDto> fetchAllTransactionsByUser(String accountNumber);
//    List<TransactionDto> getAllTransactionsByDate(String date);


//    List<TransactionDto> fetchTransactionsByDate(String date);

}
