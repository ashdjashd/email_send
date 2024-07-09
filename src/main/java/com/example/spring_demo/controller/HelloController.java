package com.example.spring_demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;


@RestController
public class HelloController {

    @GetMapping("/email/test")
    public Object email() {
        var email = new JSONObject();
        {
            String username = "";
            String password = "";
            String recipientEmail = "";
            String smtpHost = "";
            Integer port = 0;
            sendSTMPEmailTest(username, password, recipientEmail, smtpHost, port);
        }
        return email;
    }

    public static void sendSTMPEmailTest(String sender, String token, String recipient, String smtp_server, Integer smtp_port) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable ", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", smtp_server);
        properties.put("mail.smtp.port", smtp_port);
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, token);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Test Email");
            message.setText("This is a test email");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}