package com.segat.trujilloinformado.model.enums;

public enum TipoProblema {
    RESIDUOS_SOLIDOS("Residuos Sólidos"),
    MALEZA("Maleza"),
    BARRIDO("Barrido"),;

    private final String descripcion;

    TipoProblema(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
