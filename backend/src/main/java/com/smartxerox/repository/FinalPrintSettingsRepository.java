package com.smartxerox.repository;

import com.smartxerox.model.FinalPrintSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FinalPrintSettingsRepository extends JpaRepository<FinalPrintSettings, UUID> {
}
