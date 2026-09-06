package org.dara.marketservice.controller;

import lombok.RequiredArgsConstructor;
import org.dara.marketservice.service.MarketPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketTestController {

    private final MarketPriceService marketPriceService;

    @GetMapping("/prices")
    public Map<String, BigDecimal> getPrices() {
        return marketPriceService.getAllPrices();
    }

    @GetMapping("/prices/{symbol}")
    public BigDecimal getPrice(@PathVariable String symbol) {
        return marketPriceService.getPrice(symbol);
    }
}
