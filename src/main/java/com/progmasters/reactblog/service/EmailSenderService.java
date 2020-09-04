package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.Comment;
import com.progmasters.reactblog.domain.Post;
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

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
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
        sendMail(toAddress, subject, mailBody);
        logger.info("confirmation e-mail sent to user with id: " + id);
    }

    @Async
    public void sendConfirmationSuccessfulEmail(String toAddress, Long id) {
        String mailBody =
                "You Successfully confirmed your registration with \n" +
                        "Id: " + id + "\n" +
                        "You can read the suggestions in the link below\n" +
                        "http://localhost:4200/login";
        String subject = "Successful confirmation!";
        sendMail(toAddress, subject, mailBody);
        logger.info("Successful confirmation e-mail sent to user with id: " + id);
    }

    @Async
    public void sendNewSuggestionNotificationEmail(Suggestion suggestion, List<User> userList) {
        String subject = "New suggestion";
        String mailBody = "" +
                "Hello! New suggestion was created.\n" +
                "You can read the suggestions in the link below\n" +
                "http://localhost:4200/suggestion-box";
        for (User user : userList) {
            sendMail(user.getEmail(), subject, mailBody);
        }
        logger.info("New suggestion notification emails sent for suggestion id: " + suggestion.getId());
    }

    @Async
    public void sendNewPostNotificationEmail(Long postId, List<User> userList) {
        String subject = "New post";
        String mailBody = "" +
                "Hello! New post was created.\n" +
                "You can read the post details in the link below\n" +
                "http://localhost:4200/posts/" + postId + "\n" +
                "Or you can read all the posts in the link below\n" +
                "http://localhost:4200/posts";
        for (User user : userList) {
            sendMail(user.getEmail(), subject, mailBody);
        }
        logger.info("New suggestion notification emails sent for post id: " + postId);
    }


    public void sendMail(String toAddress, String subject, String mailBody) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(mailBody);
        javaMailSender.send(mailMessage);
    }

    @Async
    public void sendNewCommentNotification(Comment comment) {
        String subject = "New comment";
        String mailBody = "" +
                "Hello! New comment was added to your post.\n" +
                "You can open your post details page in the link below\n" +
                "http://localhost:4200/posts/" + comment.getPost().getId();
        User author = comment.getPost().getAuthor();
        String toAddress = author.getEmail();
        sendMail(toAddress, subject, mailBody);
        logger.info("New comment notification email sent to user with id: " + author.getId());
    }
}
