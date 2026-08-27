package org.dara.emailservice.evnet;

import lombok.RequiredArgsConstructor;
import org.dara.cryptoevent.Dto.AuthUserRegisteredEvent;
import org.dara.cryptoevent.Dto.EmailVerificationRequestedEvent;
import org.dara.emailservice.service.EmailSenderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailVerificationRequestedConsumer {

    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "email.verification.requested", groupId = "email-service", containerFactory = "kafkaListenerContainerFactory")
    public void consume(EmailVerificationRequestedEvent event) {
        emailSenderService.sendVerificationEmail(event.email(), event.username(), event.verificationToken());
    }
}
