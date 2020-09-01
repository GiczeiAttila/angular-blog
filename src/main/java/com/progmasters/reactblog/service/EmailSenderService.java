package com.progmasters.reactblog.service;

import com.progmasters.reactblog.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
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
        javaMailSender.send(mailMessage);
        logger.info("confirmation e-mail sent to user with id: " + id);
    }

    @Async
    public void sendConfirmationSuccessfulEmail(String toMail, Long id) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toMail);
        String mailBody =
                "You Successfully confirmed your registration with \n" +
                        "Id: " + id + "\n";
        mailMessage.setSubject("Successful confirmation!");
        mailMessage.setText(mailBody);
        javaMailSender.send(mailMessage);
        logger.info("Successful confirmation e-mail sent to user with id: " + id);
    }
}
