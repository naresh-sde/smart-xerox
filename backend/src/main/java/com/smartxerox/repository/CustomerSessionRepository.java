package com.smartxerox.repository;

import com.smartxerox.model.CustomerSession;
import com.smartxerox.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CustomerSessionRepository extends JpaRepository<CustomerSession, UUID> {
    Optional<CustomerSession> findByShopAndSessionUid(Shop shop, String sessionUid);
    Optional<CustomerSession> findBySessionUid(String sessionUid);
}
