package com.segat.trujilloinformado.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "evidencias")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;
    private Long uploadedBy;
    private String url;

    @CreationTimestamp
    private Instant createdAt;
}
