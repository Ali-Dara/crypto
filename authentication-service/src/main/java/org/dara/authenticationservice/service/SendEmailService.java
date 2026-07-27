package org.dara.authenticationservice.service;

import org.dara.authenticationservice.model.AuthUser;

public interface SendEmailService {

    void sendEmail(AuthUser authUser) throws Exception;
}
