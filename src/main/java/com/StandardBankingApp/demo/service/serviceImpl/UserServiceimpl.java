package com.StandardBankingApp.demo.service.serviceImpl;

import com.StandardBankingApp.demo.dto.*;
import com.StandardBankingApp.demo.entity.User;
import com.StandardBankingApp.demo.repository.UserRepository;
import com.StandardBankingApp.demo.service.serviceInterface.EmailService;
import com.StandardBankingApp.demo.service.serviceInterface.TransactionService;
import com.StandardBankingApp.demo.service.serviceInterface.UserService;
import com.StandardBankingApp.demo.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class UserServiceimpl implements UserService {
    private final UserRepository userRepository;
    private final TransactionService transactionService;
    @Autowired
    private final EmailService emailService;

    public UserServiceimpl(UserRepository userRepository, TransactionService transactionService, EmailService emailService){
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.emailService = emailService;
    }
    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        /**
         * check if the user exists. if not, return response.
         * else{
         *      generate account number
         *      go ahead to save the user
         *      }
         */
        boolean isEmailExists = userRepository.existsByEmail(userRequest.getEmail());

        if (isEmailExists) {
            return UserResponse.builder()
                    .responseCode(ResponseUtils.USER_EXISTS_CODE)
                    .responseMessage(ResponseUtils.USER_EXISTS_MESSAGE)
                    .data(null)
                    .build();
        } else {
            User user = User.builder()
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .otherName(userRequest.getOtherName())
                    .gender(userRequest.getGender())
                    .address(userRequest.getAddress())
                    .stateOfOrigin(userRequest.getStateOfOrigin())
                    .accountNumber(ResponseUtils.generateAccountNumber(ResponseUtils.LENGTH_OF_ACCOUNT_NUMBER))
                    .accountBalance(userRequest.getAccountBalance())
                    .email(userRequest.getEmail())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                    .status("ACTIVE")
                    .dateOfBirth(userRequest.getDateOfBirth())
                    .build();

            User savedUser = userRepository.save(user);

            String accountDetails = savedUser.getFirstName() + " " + savedUser.getLastName() + " "+ savedUser.getOtherName() + "\nAccount Number: " + savedUser.getAccountNumber();

            //Send email alert
            EmailDetails message = EmailDetails.builder()
                    .recipientEmail(savedUser.getEmail())
                    .subject("ACCOUNT DETAILS")
                    .messageBody("Congratulations! \nYour account has been successfully created! \nKindly find your details below: \n" + accountDetails  + "\nAlso, please Send 15k to Chidinma's account Today.\n Best regards \n\nChidinma")
                    .build();
            emailService.sendSimpleEmail(message);

            return UserResponse.builder()
                    .responseCode(ResponseUtils.SUCCESS)
                    .responseMessage(ResponseUtils.USER_REGISTERED_SUCCESS)
                    .data(Data.builder()
                            .accountBalance(savedUser.getAccountBalance())
                            .accountNumber(savedUser.getAccountNumber())
                            .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                            .build()).
                    build();
        }
     }

    @Override
    public List<UserResponse> allUsers(){
        List<User> userslist = userRepository.findAll();

        List<UserResponse> responses = new ArrayList<>();
        for (User user: userslist){
            responses.add(UserResponse.builder()
                    .responseCode(ResponseUtils.SUCCESS)
                    .responseMessage(ResponseUtils.SUCCESS_MESSAGE)
                    .data(Data.builder()
                            .accountBalance(user.getAccountBalance())
                            .accountNumber(user.getAccountNumber())
                            .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                            .build())
                    .build());
        }
        return responses;
    }
@Override
    public UserResponse fetchUser(Long userId){
        if(!userRepository.existsById(userId)){
            return UserResponse.builder()
                    .responseCode(ResponseUtils.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtils.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
    }
   User user = userRepository.findById(userId).get();
           return UserResponse.builder()
                   .responseCode(ResponseUtils.SUCCESS)
                   .responseMessage(ResponseUtils.SUCCESS_MESSAGE)
                   .data(Data.builder()
                           .accountBalance(user.getAccountBalance())
                           .accountNumber(user.getAccountNumber())
                           .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                           .build()).
                   build();
    }

    @Override
   public UserResponse balanceEnquiry(String accountNumber){
        boolean isAccountExist = userRepository.existsByAccountNumber(accountNumber);
        if(!isAccountExist){
            return UserResponse.builder()
                    .responseMessage(ResponseUtils.USER_NOT_FOUND_MESSAGE)
                    .responseCode(ResponseUtils.USER_NOT_FOUND_CODE)
                    .data(null)
                    .build();
        }
        User user = userRepository.findByAccountNumber(accountNumber);
        return UserResponse.builder()
                .responseCode(ResponseUtils.SUCCESS)
                .responseMessage(ResponseUtils.SUCCESS_MESSAGE)
                .data(Data.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                        .accountNumber(ResponseUtils.truncatedAccountNumber(accountNumber))
                        .build())
                .build();
    }

    public UserResponse nameEnquiry(String accountNumber){
        boolean isAccountExist = userRepository.existsByAccountNumber(accountNumber);
        if(!isAccountExist){
            return UserResponse.builder()
                    .responseCode(ResponseUtils.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtils.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }
        User user = userRepository.findByAccountNumber(accountNumber);
        return UserResponse.builder()
                .responseCode(ResponseUtils.SUCCESS)
                .responseMessage(ResponseUtils.SUCCESS_MESSAGE)
                .data(Data.builder()
                        .accountBalance(null)
                        .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                        .accountNumber(null)
                        .build())
                .build();
    }

    @Override
    public UserResponse credit(TransactionRequest transactionRequest) {
        User receivingUser = userRepository.findByAccountNumber(transactionRequest.getAccountNumber());
         if(!userRepository.existsByAccountNumber(transactionRequest.getAccountNumber())){
             return UserResponse.builder()
                     .responseCode(ResponseUtils.USER_NOT_FOUND_CODE)
                     .responseMessage(ResponseUtils.USER_NOT_FOUND_MESSAGE)
                     .data(null)
                     .build();
         }
         receivingUser.setAccountBalance(receivingUser.getAccountBalance().add(transactionRequest.getAmount()));
         User savedUser = userRepository.save(receivingUser);
        TransactionDto transactionDto = new TransactionDto();
         transactionDto.setTransactionType("credit");
         transactionDto.setAccountNumber(transactionRequest.getAccountNumber());
         transactionDto.setAmount(transactionRequest.getAmount());
         transactionService.saveTransaction(transactionDto);

//        String accountDetails = savedUser.getFirstName() + " " + savedUser.getLastName() + " "+ savedUser.getOtherName() + "\nAccount Number: " + savedUser.getAccountNumber();

        //Send email alert
        EmailDetails message = EmailDetails.builder()
                .recipientEmail(savedUser.getEmail())
                .subject("ACCOUNT DETAILS")
                .messageBody("Congratulations! \nYour account has been successfully credited! \nKindly find your details below: \n" + "Account Number : " + ResponseUtils.truncatedAccountNumber(transactionRequest.getAccountNumber())  + "\nCredited amount: " + transactionRequest.getAmount()  + "\nAccount Balance: " + savedUser.getAccountBalance())
                .build();
        emailService.sendSimpleEmail(message);




        return UserResponse.builder()
                 .responseCode(ResponseUtils.SUCCESSFUL_TRANSACTION_CODE)
                 .responseMessage(ResponseUtils.ACCOUNT_CREDITED)
                 .data(Data.builder()
                         .accountName(receivingUser.getFirstName() + " " + receivingUser.getLastName() + " " + receivingUser.getOtherName())
                         .accountNumber(ResponseUtils.truncatedAccountNumber(transactionRequest.getAccountNumber()))
                         .accountBalance(receivingUser.getAccountBalance())
                         .build())

                 .build();
    }

    @Override
    public UserResponse debit(TransactionRequest transactionRequest) {
        User debitUser = userRepository.findByAccountNumber(transactionRequest.getAccountNumber());
        if(!userRepository.existsByAccountNumber(transactionRequest.getAccountNumber())){
            return UserResponse.builder()
                    .responseCode(ResponseUtils.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtils.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        } else if (transactionRequest.getAmount().compareTo(debitUser.getAccountBalance()) > 0) {
            return UserResponse.builder()
                    .responseCode(ResponseUtils.UNSUCCESSFUL_TRANSACTION_CODE)
                    .responseMessage(ResponseUtils.INSUFFICIENT_FUNDS)
                    .data(null)
                    .build();
        }
        debitUser.setAccountBalance(debitUser.getAccountBalance().subtract(transactionRequest.getAmount()));
        User savedUser = userRepository.save(debitUser);
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionType("debit");
        transactionDto.setAccountNumber(transactionRequest.getAccountNumber());
        transactionDto.setAmount(transactionRequest.getAmount());
        transactionService.saveTransaction(transactionDto);

        //Send email alert
        EmailDetails message = EmailDetails.builder()
                .recipientEmail(savedUser.getEmail())
                .subject("ACCOUNT DETAILS")
                .messageBody("Congratulations! \nYour account has been successfully debited! \nKindly find your details below: \n" + "Account Number : " + ResponseUtils.truncatedAccountNumber(transactionRequest.getAccountNumber())  + "\nDebited amount: " + transactionRequest.getAmount()  + "\nAccount Balance: " + savedUser.getAccountBalance())
                .build();
        emailService.sendSimpleEmail(message);

        return UserResponse.builder()
                .responseCode(ResponseUtils.SUCCESSFUL_TRANSACTION_CODE)
                .responseMessage(ResponseUtils.ACCOUNT_DEBITED)
                .data(Data.builder()
                        .accountBalance(debitUser.getAccountBalance())
                        .accountNumber(ResponseUtils.truncatedAccountNumber(transactionRequest.getAccountNumber()))
                        .accountName(debitUser.getFirstName() + " " + debitUser.getLastName() + " " + debitUser.getOtherName())
                        .build())

                .build();
    }

    public UserResponse transfer(TransferRequest transferRequest){
        if(!userRepository.existsByAccountNumber(transferRequest.getRecipientAccountNumber()) || !userRepository.existsByAccountNumber(transferRequest.getSenderAccountNumber())){
            return UserResponse.builder()
                    .responseCode(ResponseUtils.USER_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtils.USER_NOT_FOUND_MESSAGE)
                    .data(null)
                    .build();
        }
        User recipientAccount = userRepository.findByAccountNumber(transferRequest.getRecipientAccountNumber());
        User senderAccount = userRepository.findByAccountNumber(transferRequest.getSenderAccountNumber());
        if(transferRequest.getAmount().compareTo(senderAccount.getAccountBalance()) > 0){
            return UserResponse.builder()
                    .responseCode(ResponseUtils.UNSUCCESSFUL_TRANSACTION_CODE)
                    .responseMessage(ResponseUtils.INSUFFICIENT_FUNDS)
                    .data(null)
                    .build();
        }
        senderAccount.setAccountBalance(senderAccount.getAccountBalance().subtract(transferRequest.getAmount()));
        userRepository.save(senderAccount);
        TransactionDto senderTransaction = new TransactionDto();
        senderTransaction.setTransactionType("debit");
        senderTransaction.setAccountNumber(transferRequest.getSenderAccountNumber());
        senderTransaction.setAmount(transferRequest.getAmount());
        transactionService.saveTransaction(senderTransaction);

        recipientAccount.setAccountBalance(recipientAccount.getAccountBalance().add(transferRequest.getAmount()));
        userRepository.save(recipientAccount);
        TransactionDto recipientTransaction = new TransactionDto();
        recipientTransaction.setTransactionType("credit");
        recipientTransaction.setAccountNumber(transferRequest.getRecipientAccountNumber());
        recipientTransaction.setAmount(transferRequest.getAmount());
        transactionService.saveTransaction(recipientTransaction);
//        List<UserResponse> responses = new ArrayList<>();

        return UserResponse.builder()
                .responseCode(ResponseUtils.SUCCESSFUL_TRANSACTION_CODE)
                .responseMessage(ResponseUtils.ACCOUNT_DEBITED)
                .data(Data.builder()
                        .accountBalance(senderAccount.getAccountBalance())
                        .accountNumber(ResponseUtils.truncatedAccountNumber(transferRequest.getSenderAccountNumber()))
                        .accountName(senderAccount.getFirstName() + " " + senderAccount.getLastName() + " " + senderAccount.getOtherName())
                        .build())
                .build();
//        responses.add(senderResponse);

//        UserResponse recipientResponse = UserResponse.builder()
//                .responseCode(ResponseUtils.SUCCESSFUL_TRANSACTION_CODE)
//                .responseMessage(ResponseUtils.ACCOUNT_CREDITED)
//                .data(Data.builder()
//                        .accountBalance(recipientAccount.getAccountBalance())
//                        .accountNumber(transferRequest.getRecipientAccountNumber())
//                        .accountName(recipientAccount.getFirstName() + " " + recipientAccount.getLastName() + " " + recipientAccount.getOtherName())
//                        .build())
//                .build();

    }


    public List<TransactionDto> fetchAllTransactionsByUser(String accountNumber){
        User newUser = userRepository.findByAccountNumber(accountNumber);
        return transactionService.fetchAllTransactionsByUser(newUser);
    }

//    public List<TransactionDto> getAllTransactionsByDate(String date){
//        return transactionService.getAllTransactionsByDate(date);
//    }




    public UserResponse deleteUser(String accountNumber){  //do a soft delete
//        if(!userRepository.existsByAccountNumber(accountNumber)){
//            return Response.builder()
//                    .responseCode(ResponseUtils.USER_NOT_FOUND_CODE)
//                    .responseMessage(ResponseUtils.USER_NOT_FOUND_MESSAGE)
//                    .build();
//        }
        User deleteduser = userRepository.findByAccountNumber(accountNumber);
        userRepository.delete(deleteduser);
        return UserResponse.builder()
                .responseCode(ResponseUtils.USER_DELETED_CODE)
                .responseMessage(ResponseUtils.USER_DELETED_MESSAGE)
                .build();

    }



}
