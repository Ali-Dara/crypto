package org.dara.marketservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dara.marketservice.service.MarketPriceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceUpdateScheduler {

    private final MarketPriceService marketPriceService;

    @Scheduled(initialDelay = 1000, fixedRate = 3 * 60 * 1000)
    public void updatePrices() {
        log.info("Updating prices....");
        marketPriceService.updatePrices();
        log.info("Prices updated.");
    }
}
