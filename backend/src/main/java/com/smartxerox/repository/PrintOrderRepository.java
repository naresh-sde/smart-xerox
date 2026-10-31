package com.smartxerox.repository;

import com.smartxerox.model.PrintOrder;
import com.smartxerox.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrintOrderRepository extends JpaRepository<PrintOrder, UUID> {
    List<PrintOrder> findByShopOrderBySubmittedAtDesc(Shop shop);
    Optional<PrintOrder> findByPublicOrderNumber(String publicOrderNumber);
    Optional<PrintOrder> findByIdempotencyKey(String idempotencyKey);
}
