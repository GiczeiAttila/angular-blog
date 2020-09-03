package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.Suggestion;
import com.progmasters.reactblog.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("emailSenderService")
public class EmailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
    private final JavaMailSender javaMailSender;
    private final UserService userService;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    @Async
    public void sendRegistrationConfirmationEmail(String toAddress, String token, Long id) {
        String mailBody =
                "You registration information for confirmation: \n" +
                        "Id: " + id + "\n" +
                        "Token: " + token + "\n" +
                        "You can confirm your registration by clicking the link below\n" +
                        " http://localhost:4200/confirmation/" + token + "/" + id;
        String subject = "Confirmation!";
        sendMail(toAddress,subject,mailBody);
        logger.info("confirmation e-mail sent to user with id: " + id);
    }

    @Async
    public void sendConfirmationSuccessfulEmail(String toAddress, Long id) {
        String mailBody =
                "You Successfully confirmed your registration with \n" +
                        "Id: " + id + "\n";
        String subject = "Successful confirmation!";
        sendMail(toAddress,subject,mailBody);
        logger.info("Successful confirmation e-mail sent to user with id: " + id);
    }

    @Async
    public void sendNewSuggestionNotificationEmail(Suggestion suggestion) {
        String subject = "New suggestion";
        String mailBody = "" +
                "Hello! New suggestion was created.\n" +
                "You can read the suggestions in the link below\n" +
                "http://localhost:4200/suggestion-box";
        List<User> userList = userService.findAllUsers();
        for (User user: userList) {
            sendMail(user.getEmail(),subject, mailBody);
        }
        logger.info("New suggestion notification emails sent");
    }

    public void sendMail(String toAddress, String subject, String mailBody){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(mailBody);
        javaMailSender.send(mailMessage);
    }

}
