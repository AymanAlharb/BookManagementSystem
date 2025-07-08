package com.example.bookmanagementsystem.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${key}")
    private String key;

    @Value(("${senderEmail}"))
    private String senderEmail;

    public void sendEmail(String email, String name) throws IOException {

        Email fromEmail = new Email(senderEmail);
        String subject = "Welcome to Book App!";
        Content content = new Content("text/plain",
                "Welcome " + name + " to Book Management App!\n\n" +
                        "Thank you for signing up. We're glad to have you on board.\n\n" +
                        "If you have questions, reply any time!"
        );
        Email userEmail = new Email(email);
        Mail mail = new Mail(fromEmail, subject, userEmail, content);
        SendGrid sendGrid = new SendGrid(key);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
    }

}
