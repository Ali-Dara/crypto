package org.dara.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.events.OutboxEvent;
import org.dara.authenticationservice.repository.OutboxEventRepository;
import org.dara.authenticationservice.service.OutboxEventService;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxEventServiceImpl implements OutboxEventService {

    private final OutboxEventRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(UUID aggregateId, String eventType, String topic, Object event) {
        try {
            OutboxEvent outboxEvent = new OutboxEvent();

            outboxEvent.setAggregateId(aggregateId);
            outboxEvent.setEventType(eventType);
            outboxEvent.setTopic(topic);
            outboxEvent.setPayload(objectMapper.writeValueAsString(event));
            outboxEvent.setCreatedAt(Instant.now());
            outboxEvent.setPublished(false);

            repository.save(outboxEvent);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Could not serialize outbox event",
                    e
            );
        }
    }
}
