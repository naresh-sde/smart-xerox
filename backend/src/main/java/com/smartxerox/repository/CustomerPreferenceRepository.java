package com.smartxerox.repository;

import com.smartxerox.model.CustomerPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CustomerPreferenceRepository extends JpaRepository<CustomerPreference, UUID> {
}
