package com.StandardBankingApp.demo.service.serviceImpl;

import com.StandardBankingApp.demo.dto.SmsDetails;
import com.StandardBankingApp.demo.service.serviceInterface.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {
    @Value("${TWILIO_ACCOUNT_SID}")
    String ACCOUNT_SID;
    @Value("${TWILIO_AUTH_TOKEN}")
    String AUTH_TOKEN;
    @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
    String OUTGOING_SMS_NUMBER;

    @PostConstruct
    private void setup() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }


    @Override
    public String sendSms(SmsDetails smsDetails){
        Message message = Message.creator(
                new PhoneNumber(smsDetails.getRecipientPhoneNumber()),
                new PhoneNumber(OUTGOING_SMS_NUMBER),
                smsDetails.getMessage()).create();
return message.getStatus().toString();
    }
}
