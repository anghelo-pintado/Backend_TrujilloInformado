package com.segat.trujilloinformado.model.dao;

import com.segat.trujilloinformado.model.entity.Reporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteDao extends JpaRepository<Reporte, Long> {
    Page<Reporte> findByCitizenId(Long citizenId, Pageable pageable);
}
