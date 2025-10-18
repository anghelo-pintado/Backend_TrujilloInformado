package com.segat.trujilloinformado.service;

import com.segat.trujilloinformado.model.dto.ReporteDto;
import com.segat.trujilloinformado.model.entity.Reporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReporteService {
    Reporte save(ReporteDto reporteDto);
    Reporte findById(Long id);
    Page<Reporte> findByCitizenEmail(String email, Pageable pageable);
    void delete(Reporte reporte);
    boolean existsById(Long id);
    List<Reporte> findAll();
    Page<Reporte> findAll(Pageable pageable);
}
