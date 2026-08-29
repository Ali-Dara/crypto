package org.dara.walletservice.repository;

import org.dara.walletservice.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {

    Optional<Asset> findBySymbol(String symbol);
    boolean existsBySymbol(String symbol);
}
