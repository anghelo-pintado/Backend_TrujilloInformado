package com.segat.trujilloinformado.model.dto;

import com.segat.trujilloinformado.model.enums.EstadoReporte;
import com.segat.trujilloinformado.model.enums.TipoProblema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class ReporteDto implements Serializable {
    private Long idReporte;
    private String descripcion;
    private TipoProblema tipoProblema;
    private EstadoReporte estado;
}
