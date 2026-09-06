package org.dara.marketservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.marketservice.cache.PriceCache;
import org.dara.marketservice.client.CoinGeckoClient;
import org.dara.marketservice.service.MarketPriceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarketPriceServiceImpl implements MarketPriceService {

    private final CoinGeckoClient coinGeckoClient;
    private final PriceCache priceCache;

    @Override
    public void updatePrices() {
        Map<String, Map<String, BigDecimal>> prices = coinGeckoClient.getPrice();
        updateCache("BTC", prices.get("bitcoin"));
        updateCache("ETH", prices.get("ethereum"));
        updateCache("USDT", prices.get("tether"));

    }

    private void updateCache(String symbol, Map<String, BigDecimal> priceData) {
        if (priceData == null)
            return;
        BigDecimal usdPrice = priceData.get("usd");
        if (usdPrice != null)
            priceCache.update(symbol, usdPrice);

    }

    @Override
    public BigDecimal getPrice(String symbol) {
        return priceCache.getPrice(symbol);
    }

    @Override
    public Map<String, BigDecimal> getAllPrices() {
        return priceCache.getAllPrices();
    }
}
