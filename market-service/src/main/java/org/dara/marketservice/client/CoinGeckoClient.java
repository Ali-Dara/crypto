package org.dara.marketservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class CoinGeckoClient {

    private final RestClient restClient;
    private final String apiKey;

    public CoinGeckoClient(RestClient.Builder restClientBuilder,
                           @Value("${market.provider.coingecko.base-url}") String baseUrl,
                           @Value("${market.provider.coingecko.api-key}") String apiKey) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public Map<String, Map<String, BigDecimal>> getPrice() {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/simple/price")
                        .queryParam("ids", "bitcoin,ethereum,tether")
                        .queryParam("vs_currencies", "usd")
                        .build()
                )
                .header("x-cg-demo-api-key", apiKey)
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<>() {});
    }
}
