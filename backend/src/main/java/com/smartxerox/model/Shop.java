package com.smartxerox.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shops")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String shopCode;

    @Column(unique = true, nullable = false)
    private String qrToken;

    private String name;
    private String branchLabel;
    private String address;
    private String phone;
    private String email;
    private String ownerName;

    @Enumerated(EnumType.STRING)
    private ShopStatus status;

    @Builder.Default
    private boolean acceptOrders = true;

    @Builder.Default
    private String defaultPrintType = "BLACK_WHITE";
    @Builder.Default
    private String defaultPaperSize = "A4";
    @Builder.Default
    private int defaultCopies = 1;
    @Builder.Default
    private String defaultSides = "ONE_SIDED";

    private int retentionDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime approvedAt;
}
