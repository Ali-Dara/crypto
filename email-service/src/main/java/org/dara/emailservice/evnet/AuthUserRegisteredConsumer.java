package org.dara.emailservice.evnet;

import lombok.RequiredArgsConstructor;
import org.dara.cryptoevent.Dto.AuthUserRegisteredEvent;
import org.dara.emailservice.service.EmailSenderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserRegisteredConsumer {

    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "auth.user.registered", groupId = "email-service", containerFactory = "kafkaListenerContainerFactory")
    public void consume(AuthUserRegisteredEvent event) {
        emailSenderService.sendWelcomeEmail(event.email(), event.username());
    }
}
