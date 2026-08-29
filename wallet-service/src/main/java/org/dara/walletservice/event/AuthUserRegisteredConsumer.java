package org.dara.walletservice.event;

import lombok.RequiredArgsConstructor;
import org.dara.cryptoevent.Dto.AuthUserRegisteredEvent;
import org.dara.walletservice.service.WalletService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserRegisteredConsumer {

    private final WalletService walletService;

    @KafkaListener(
            topics = "auth.user.registered",
            groupId = "wallet-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(AuthUserRegisteredEvent event) {
        walletService.createWallet(event.userUUID());
    }
}
