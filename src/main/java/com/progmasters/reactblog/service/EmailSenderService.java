package com.progmasters.reactblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendRegistrationConfirmationEmail(String toMail, String token, Long id) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toMail);
        String mailBody =
                "You registration information for confirmation: \n" +
                        "Id: " + id + "\n" +
                        "Token: " + token + "\n" +
                        "You can confirm your registration by clicking the link below\n" +
                        " http://localhost:4200/confirmation/" + token + "/" + id;
        mailMessage.setSubject("Confirmation!");
        mailMessage.setText(mailBody);
//        mailMessage.setText("To confirm your account, please click here : "
        //              + "http://localhost:4200/confirmation/" + token + "/" + id);
        javaMailSender.send(mailMessage);
    }
}
