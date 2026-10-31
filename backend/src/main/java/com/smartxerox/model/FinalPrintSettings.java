package com.smartxerox.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "final_print_settings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FinalPrintSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private PrintOrder order;

    private String printType;
    private String paperSize;
    private int copies;
    private String sides;
    private LocalDateTime updatedAt;
}
