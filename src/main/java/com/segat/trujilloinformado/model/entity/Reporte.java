package com.segat.trujilloinformado.model.entity;

import com.segat.trujilloinformado.model.entity.enums.Priority;
import com.segat.trujilloinformado.model.entity.enums.Status;
import com.segat.trujilloinformado.model.entity.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long citizenId;
    private String citizenName;
    private String citizenEmail;
    private String citizenPhone;

    @Column(nullable = false)
    private Type type;
    private String description;

    private double lat;
    private double lng;
    private String address;

    @Column(columnDefinition = "TEXT") // Almacenar URLs separadas por comas
    private String photos;

    private Status status;

    private Priority priority;
    private String zone;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    private String assignedTo;
    private String assignedBy;

    @UpdateTimestamp
    private Instant assignedAt;
}


