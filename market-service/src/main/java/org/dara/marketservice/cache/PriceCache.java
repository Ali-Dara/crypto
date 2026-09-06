package org.dara.marketservice.cache;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PriceCache {

    ConcurrentHashMap<String, BigDecimal> priceCache = new ConcurrentHashMap<String, BigDecimal>();

    public void update(String symbol, BigDecimal price) {
        priceCache.put(symbol, price);
    }

    public BigDecimal getPrice(String symbol) {
        return priceCache.get(symbol.toUpperCase());
    }

    public Map<String, BigDecimal> getAllPrices() {
        return Map.copyOf(priceCache);
    }
}
