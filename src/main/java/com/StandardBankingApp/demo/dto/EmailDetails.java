package com.StandardBankingApp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmailDetails {
    private String recipientEmail;
    private String subject;
    private String messageBody;
    private String attachment;

}
