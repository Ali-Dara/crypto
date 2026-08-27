package org.dara.emailservice.service;

public interface EmailSenderService {

    void sendWelcomeEmail(String to, String userName);
    void sendVerificationEmail(String to, String userName, String verificationToken);
}
