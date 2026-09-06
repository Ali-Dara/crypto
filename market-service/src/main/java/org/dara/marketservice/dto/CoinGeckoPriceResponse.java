package org.dara.marketservice.dto;

import java.math.BigDecimal;
import java.util.Map;

public record CoinGeckoPriceResponse(
      Map<String, Map<String, BigDecimal>> price
) {}
