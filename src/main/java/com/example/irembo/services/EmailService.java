package com.example.irembo.services;

import java.util.Properties;

import org.springframework.stereotype.Service;

import com.twilio.*;
import com.twilio.type.PhoneNumber;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private static final String username = "rex90danny@gmail.com";
    private static final String password = "helloboss";

    public boolean send2FACode(String email, String mfaCode) throws AddressException, MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

        message.setSubject("Two FA Code from User management");
        message.setText("Your Two Factor Authentication is: " + mfaCode);

        Transport.send(message);
        return true;
    }

    public boolean send2FASMSCode(String phoneNumber, String mfaCode) {
        Twilio.init("sid",
                "password");

        com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber(phoneNumber),
                new PhoneNumber("phonenumber"), "Your Code is " + mfaCode).create();
        return true;
    }
}
