package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("emailSenderService")
@EnableAsync
public class EmailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
    private final JavaMailSender javaMailSender;

    @Value("${url-for-mail}")
    private String urlForMail;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendRegistrationConfirmationEmail(String toAddress, String token, Long id) {
        String mailBody =
                "Your registration information for confirmation: \n" +
                        "Id: " + id + "\n" +
                        "Token: " + token + "\n" +
                        "You can confirm your registration by clicking the link below\n" +
                        urlForMail +
                        "/confirmation/" + token + "/" + id;
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
                        urlForMail +
                        "/login";
        String subject = "Successful confirmation!";
        sendMail(toAddress, subject, mailBody);
        logger.info("Successful confirmation e-mail sent to user with id: " + id);
    }

    @Async
    public void sendNewSuggestionNotificationEmail(Suggestion suggestion, List<User> userList) {
        String subject = "New suggestion";
        String mailBodyEnd =
                "You can read the suggestions in the link below\n" +
                        urlForMail +
                        "/suggestion-box";
        for (User user : userList) {
            String mailBodyStart = suggestion.getUser().getId().equals(user.getId()) ? "Hello! Your suggestion have been registered.\n"
                    : "Hello! New suggestion was created.\n";
            String mailBody = mailBodyStart + mailBodyEnd;
            sendMail(user.getEmail(), subject, mailBody);
        }
        logger.info("New suggestion notification emails sent for suggestion id: " + suggestion.getId());
    }


    public void sendNewPostNotificationEmail(Post post, List<User> userList) {
        String subject = "New post";
        String mailBodyEnd = "You can read the post details in the link below\n" +
                urlForMail +
                "/posts/" + post.getId() + "\n" +
                "Or you can read all the posts in the link below\n" +
                urlForMail +
                "/posts";
        for (User user : userList) {
            String mailBodyStart = post.getAuthor().getId().equals(user.getId()) ? "Hello! Your post have been saved.\n" : "Hello! New post was created.\n";
            String mailBody = mailBodyStart + mailBodyEnd;
            sendMail(user.getEmail(), subject, mailBody);
        }
        logger.info("New suggestion notification emails sent for post id: " + post.getId());
    }


    @Async
    public void sendNewCommentNotification(Comment comment) {
        String subject = "New comment";
        String mailBody = "" +
                "Hello! New comment was added to your post.\n" +
                "You can open your post details page in the link below\n" +
                urlForMail +
                "/posts/" + comment.getPost().getId();
        User author = comment.getPost().getAuthor();
        String toAddress = author.getEmail();
        sendMail(toAddress, subject, mailBody);
        logger.info("New comment notification email sent to user with id: " + author.getId());
    }

    @Async
    public void sendNewSuggestionStatusChangeNotificationEmail(Suggestion suggestion) {
        String subject = "Suggestion status changed";
        String mailBody = "Hello! Your suggestion with id: " + suggestion.getId() + " has been given a new status: " + suggestion.getStatus().getDisplayName();
        String toAddress = suggestion.getUser().getEmail();
        sendMail(toAddress, subject, mailBody);
        logger.info("Suggestion status change notification email sent to user with id: " + suggestion.getUser().getId());
    }

    @Async
    public void sendNewOpenPositionNotificationEmail(OpenPosition openPosition, List<User> userList) {
        String subject = "New open position";
        String mailBodyEnd =
                "You can read about the open positions in the link below\n" +
                        urlForMail +
                        "/open-position";
        for (User user : userList) {
            String mailBodyStart = openPosition.getUser().getId().equals(user.getId()) ? "Hello! Your new open position have been registered.\n"
                    : "Hello! New open position was created.\n";
            String mailBody = mailBodyStart + mailBodyEnd;
            sendMail(user.getEmail(), subject, mailBody);
        }
        logger.info("New open position notification emails sent for open position id: " + openPosition.getId());
    }

    @Async
    public void sendMeetingStatusChangeNotification(MeetingReservation meetingReservation, List<User> userList) {
        String subject = "Meeting status changed";
        String mailBody =
                "Meeting with title: " + meetingReservation.getTitle() + " starting at:  " + meetingReservation.getStartDate() +
                        " status is changed to: " + meetingReservation.getMeetingStatus().getDisplayName() + "\n";
        for (User user : userList) {
            sendMail(user.getEmail(), subject, mailBody);
        }
        logger.info("Meeting status change notification is send with id:  " + meetingReservation.getId() + "new status is: " +
                meetingReservation.getMeetingStatus().getDisplayName());
    }

    @Async
    public void sendMail(String toAddress, String subject, String mailBody) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(mailBody);
        javaMailSender.send(mailMessage);
    }
}
