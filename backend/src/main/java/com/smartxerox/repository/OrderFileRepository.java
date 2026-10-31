package com.smartxerox.repository;

import com.smartxerox.model.OrderFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrderFileRepository extends JpaRepository<OrderFile, UUID> {
}
