package org.dara.marketservice.grpc;

import io.grpc.stub.StreamObserver;
import org.dara.crypto.market.contract.AssetPrice;
import org.dara.crypto.market.contract.GetPricesRequest;
import org.dara.crypto.market.contract.GetPricesResponse;
import org.dara.crypto.market.contract.MarketServiceGrpc;
import org.dara.marketservice.cache.PriceCache;
import org.springframework.grpc.server.service.GrpcService;

import java.math.BigDecimal;

@GrpcService
public class MarketGrpcService extends MarketServiceGrpc.MarketServiceImplBase {

    private final PriceCache priceCache;

    public MarketGrpcService(PriceCache priceCache) {
        this.priceCache = priceCache;
    }

    @Override
    public void getPrices(GetPricesRequest request, StreamObserver<GetPricesResponse> responseObserver) {
        GetPricesResponse.Builder responseBuilder = GetPricesResponse.newBuilder();

        for(String symbol: request.getSymbolsList()){
            BigDecimal price = priceCache.getPrice(symbol);
            if (price == null)
                continue;
            AssetPrice assetPrice = AssetPrice.newBuilder()
                    .setSymbol(symbol.toUpperCase())
                    .setPrice(price.toPlainString())
                    .build();

            responseBuilder.addPrices(assetPrice);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

}
