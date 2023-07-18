package com.StandardBankingApp.demo.service.serviceImpl;

import com.StandardBankingApp.demo.dto.EmailDetails;
import com.StandardBankingApp.demo.service.serviceInterface.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public String sendSimpleEmail(EmailDetails emailDetails) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDetails.getRecipientEmail());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getMessageBody());

            javaMailSender.send(simpleMailMessage);
            return "Mail sent successfully";
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }

//    public String sendSimpleEmail() {
//        try{
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setFrom(senderEmail);
//            simpleMailMessage.setTo("oyahebholokennethizosea@gmail.con");
//            simpleMailMessage.setText("hi");
//            simpleMailMessage.setSubject("letter");
//
//            javaMailSender.send(simpleMailMessage);
//            return "Mail sent successfully";
//        }catch (MailException e){
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public String sendEmailWithAttachment(EmailDetails emailDetails) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(emailDetails.getRecipientEmail());
            mimeMessageHelper.setText(emailDetails.getMessageBody());
            mimeMessageHelper.setSubject(emailDetails.getSubject());

            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);

            javaMailSender.send(mimeMessage);
            return "Mail sent successfully";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
