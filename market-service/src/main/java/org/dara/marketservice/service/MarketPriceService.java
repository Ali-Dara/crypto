package org.dara.marketservice.service;

import java.math.BigDecimal;
import java.util.Map;

public interface MarketPriceService {

    void updatePrices();

    BigDecimal getPrice(String symbol);

    Map<String, BigDecimal> getAllPrices();
}
