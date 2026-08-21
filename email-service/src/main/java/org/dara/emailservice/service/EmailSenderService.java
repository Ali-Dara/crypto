package org.dara.emailservice.service;

public interface EmailSenderService {

    void sendWelcomeEmail(String to, String userName);
}
