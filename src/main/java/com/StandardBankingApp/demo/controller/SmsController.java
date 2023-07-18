package com.StandardBankingApp.demo.controller;

import com.StandardBankingApp.demo.dto.SmsDetails;
import com.StandardBankingApp.demo.service.serviceInterface.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class SmsController {
    @Autowired
    SmsService smsService;
    @PostMapping("/sms")
    public String sendSms(@RequestBody SmsDetails smsDetails){
        return smsService.sendSms(smsDetails);
    }
}
