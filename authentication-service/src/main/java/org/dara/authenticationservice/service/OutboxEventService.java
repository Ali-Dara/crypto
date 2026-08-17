package org.dara.authenticationservice.service;

import java.util.UUID;

public interface OutboxEventService {

    void save(UUID aggregateId, String eventType, String topic, Object event);
}
