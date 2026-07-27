package org.dara.authenticationservice.service.impl;

import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.service.SendEmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    @Override
    @EventListener
    public void sendEmail(AuthUser authUser) throws Exception {

    }
}
