package org.dara.walletservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "crypto.kafka")
public record KafkaProperties(
        String bootstrapServers
) {
}
