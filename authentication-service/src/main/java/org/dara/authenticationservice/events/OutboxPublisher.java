package org.dara.authenticationservice.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dara.authenticationservice.repository.OutboxEventRepository;
import org.dara.cryptoevent.Dto.AuthUserRegisteredEvent;
import org.dara.cryptoevent.Dto.EmailVerificationRequestedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 100000)
    public void publishEvents() {
        List<OutboxEvent> events = outboxEventRepository.findTop100ByPublishedFalseOrderByCreatedAtAsc();
        for (OutboxEvent event : events) {
            Object payload;
            switch (event.getEventType()) {
                case "AuthUserRegisteredEvent" ->
                    payload = objectMapper.readValue(event.getPayload(), AuthUserRegisteredEvent.class);
                case "EmailVerificationRequestedEvent" ->
                    payload = objectMapper.readValue(event.getPayload(), EmailVerificationRequestedEvent.class);
                default -> throw new IllegalArgumentException("Invalid event type");
            }
            try {
                kafkaTemplate
                        .send(event.getTopic(), event.getAggregateId().toString(), payload)
                        .whenComplete((result, exception) -> {
                            if (exception == null) {
                                event.setPublished(true);
                                outboxEventRepository.save(event);
                            }else {
                                log.error(
                                        "Failed to publish event id={}",
                                        event.getId(),
                                        exception
                                );
                            }
                        });
            } catch (Exception e) {
                log.error(
                        "Failed to process outbox event id={}, type={}",
                        event.getId(),
                        event.getEventType(),
                        e
                );
            }
        }
    }
}
