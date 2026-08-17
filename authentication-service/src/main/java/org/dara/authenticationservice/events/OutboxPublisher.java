package org.dara.authenticationservice.events;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.repository.OutboxEventRepository;
import org.dara.cryptoevent.Dto.AuthUserRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    public void publishEvents() {
        List<OutboxEvent> events = outboxEventRepository.findTop100ByPublishedFalseOrderByCreatedAtAsc();
        for (OutboxEvent event : events) {
            try {
                AuthUserRegisteredEvent payload = objectMapper.readValue(event.getPayload(), AuthUserRegisteredEvent.class);
                kafkaTemplate
                        .send(event.getTopic(), event.getAggregateId().toString(), payload)
                        .whenComplete((result, exception) -> {
                            if (exception == null) {
                                event.setPublished(true);
                                outboxEventRepository.save(event);
                            }
                        });
            } catch (Exception e) {

            }
        }
    }
}
