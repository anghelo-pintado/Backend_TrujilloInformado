package com.segat.trujilloinformado.model.dao;

import com.segat.trujilloinformado.model.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteDao extends JpaRepository<Reporte, Long> {
}
