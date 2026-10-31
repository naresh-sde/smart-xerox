package com.smartxerox.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "print_orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PrintOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_session_id")
    private CustomerSession customerSession;

    @Column(unique = true)
    private String publicOrderNumber; // SX-XXXXXXX

    private String customerName; // display only

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String idempotencyKey;

    private LocalDateTime submittedAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    private LocalDateTime cancelledAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderFile> files = new ArrayList<>();
}
