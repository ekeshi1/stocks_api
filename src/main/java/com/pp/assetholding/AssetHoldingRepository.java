package com.pp.assetholding;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetHoldingRepository extends CrudRepository<AssetHoldingJpa, UUID> {

    Optional<AssetHoldingJpa> findByAccountIDAndAssetSymbol(UUID accountId, String assetSymbol);
}
