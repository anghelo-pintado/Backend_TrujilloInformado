package com.segat.trujilloinformado.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "zonas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zona {
    @Id
    private UUID id;

    private String nombre;

    // supervisor is an auth user id in supabase — store as UUID
    @Column(name = "supervisor_id")
    private UUID supervisorId;

    // jsonb field for boundaries
    @Column(columnDefinition = "jsonb")
    private String limites;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;
}
