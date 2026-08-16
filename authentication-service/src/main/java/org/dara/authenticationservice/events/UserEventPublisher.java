package org.dara.authenticationservice.events;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.dto.AuthUserRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(AuthUserRegisteredEvent event) {
        kafkaTemplate.send("auth.user.registered", event.userUUID().toString(), event);
    }
}
