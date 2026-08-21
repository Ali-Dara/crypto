package org.dara.emailservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.emailservice.service.EmailSenderService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(String to, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Welcome to Crypto");
        message.setText("""
            Dear %s,

            Welcome to Crypto!

            Your account has been successfully created.

            Thanks for joining us.
            """.formatted(userName));
        mailSender.send(message);
    }
}
