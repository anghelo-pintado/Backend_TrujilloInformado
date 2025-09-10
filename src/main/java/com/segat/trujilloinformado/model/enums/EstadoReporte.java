package com.segat.trujilloinformado.model.enums;

public enum EstadoReporte {
    PENDIENTE("Pendiente"),
    EN_PROGRESO("En progreso"),
    RESUELTO("Resuelto");

    private final String descripcion;

    EstadoReporte(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
