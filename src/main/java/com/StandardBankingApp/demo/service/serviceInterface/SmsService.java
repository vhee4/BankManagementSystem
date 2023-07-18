package com.StandardBankingApp.demo.service.serviceInterface;

import com.StandardBankingApp.demo.dto.SmsDetails;

public interface SmsService {
    String sendSms(SmsDetails smsDetails);
}
