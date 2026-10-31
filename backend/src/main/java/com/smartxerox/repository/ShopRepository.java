package com.smartxerox.repository;

import com.smartxerox.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {
    Optional<Shop> findByQrToken(String qrToken);
    Optional<Shop> findByShopCode(String shopCode);
}
