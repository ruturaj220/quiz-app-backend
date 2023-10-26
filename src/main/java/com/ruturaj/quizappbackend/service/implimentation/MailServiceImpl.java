package com.ruturaj.quizappbackend.service.implimentation;

import com.ruturaj.quizappbackend.entity.MailStructure;
import com.ruturaj.quizappbackend.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String mailTo, MailStructure mailStructure) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(mailTo);

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendOTPEmail(String email, String generatedOtp) {
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Password Reset OTP");
        mailStructure.setMessage("Your OTP for password reset is: " + generatedOtp);
        sendMail(email, mailStructure);
    }
}
