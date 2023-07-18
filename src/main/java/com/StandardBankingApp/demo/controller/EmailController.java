package com.StandardBankingApp.demo.controller;

import com.StandardBankingApp.demo.dto.EmailDetails;
import com.StandardBankingApp.demo.service.serviceInterface.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api")
public class EmailController {
        @Autowired
        EmailService emailService;
        @PostMapping("/sendmail")
        public String sendEmail(@RequestBody EmailDetails emailDetails){
            return emailService.sendSimpleEmail(emailDetails);
        }
}
