package org.dara.walletservice.grpcClient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.dara.crypto.market.contract.AssetPrice;
import org.dara.crypto.market.contract.GetPricesRequest;
import org.dara.crypto.market.contract.GetPricesResponse;
import org.dara.crypto.market.contract.MarketServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MarketGrpcClient {

    private final MarketServiceGrpc.MarketServiceBlockingStub blockingStub;

    public MarketGrpcClient(@Value("${market.grpc.host}") String host, @Value("${market.grpc.port}") int port) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        this.blockingStub = MarketServiceGrpc.newBlockingStub(channel);
    }
    public Map<String, BigDecimal> getPrices(List<String> symbols){

        GetPricesRequest request = GetPricesRequest.newBuilder()
                .addAllSymbols(symbols)
                .build();
        GetPricesResponse response = blockingStub.getPrices(request);

        return response.getPricesList()
                .stream()
                .collect(Collectors.toMap(
                        AssetPrice::getSymbol,
                        assetPrice -> new BigDecimal(assetPrice.getPrice())
                ));
    }
}
