package com.StandardBankingApp.demo.service.serviceInterface;

import com.StandardBankingApp.demo.dto.EmailDetails;

public interface EmailService {
    String sendSimpleEmail(EmailDetails emailDetails);
//    String sendSimpleEmail();
    String sendEmailWithAttachment(EmailDetails emailDetails);
}
