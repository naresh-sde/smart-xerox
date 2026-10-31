package com.smartxerox.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_files")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderFile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private PrintOrder order;

    private String originalFilename;
    private String storedFilename;
    private String storageKey;
    private String mimeType;
    private long sizeBytes;
    private LocalDateTime createdAt;
}
