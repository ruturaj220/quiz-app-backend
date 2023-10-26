package com.ruturaj.quizappbackend.service;

import com.ruturaj.quizappbackend.entity.MailStructure;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendMail(String mailTo, MailStructure mailStructure);

    void sendOTPEmail(String email, String generatedOtp);
}
