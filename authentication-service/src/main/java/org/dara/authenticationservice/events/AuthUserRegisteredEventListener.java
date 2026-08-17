package org.dara.authenticationservice.events;

import lombok.RequiredArgsConstructor;
import org.dara.cryptoevent.Dto.AuthUserRegisteredEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AuthUserRegisteredEventListener {

    private final UserEventPublisher userEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(AuthUserRegisteredEvent event) {
        userEventPublisher.publish(event);
    }
}
