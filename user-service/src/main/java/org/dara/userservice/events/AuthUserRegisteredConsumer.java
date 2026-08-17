package org.dara.userservice.events;

import lombok.RequiredArgsConstructor;
import org.dara.cryptoevent.Dto.AuthUserRegisteredEvent;
import org.dara.userservice.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserRegisteredConsumer {

    private final UserService userservice;

    @KafkaListener(
            topics = "auth.user.registered",
            groupId = "user-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(AuthUserRegisteredEvent event) {
        throw new RuntimeException("TEST RETRY");
        //userservice.createUserByRegisterEvent(event);
    }
}
