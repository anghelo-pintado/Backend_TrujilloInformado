package com.segat.trujilloinformado.service;

import com.segat.trujilloinformado.model.dto.ReporteDto;
import com.segat.trujilloinformado.model.entity.Reporte;

import java.util.List;

public interface IReporteService {
    Reporte save(ReporteDto reporteDto);
    Reporte findById(Long id);
    void delete(Reporte reporte);
    boolean existsById(Long id);
    List<Reporte> findAll();
}
