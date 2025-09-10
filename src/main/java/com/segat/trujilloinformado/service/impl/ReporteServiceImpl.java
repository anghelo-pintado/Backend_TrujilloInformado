package com.segat.trujilloinformado.service.impl;

import com.segat.trujilloinformado.model.dao.ReporteDao;
import com.segat.trujilloinformado.model.dto.ReporteDto;
import com.segat.trujilloinformado.model.entity.Reporte;
import com.segat.trujilloinformado.model.enums.EstadoReporte;
import com.segat.trujilloinformado.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReporteServiceImpl implements IReporteService {
    @Autowired
    private ReporteDao reporteDao;

    @Transactional
    @Override
    public Reporte save(ReporteDto reporteDto) {
        Reporte reporte = Reporte.builder()
                .id(reporteDto.getIdReporte())
                .descripcion(reporteDto.getDescripcion())
                .tipoProblema(reporteDto.getTipoProblema())
                .estado(EstadoReporte.PENDIENTE)
                .build();
        return reporteDao.save(reporte);
    }

    @Transactional(readOnly = true)
    @Override
    public Reporte findById(Long id) {
        return reporteDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Reporte reporte) {
        reporteDao.delete(reporte);
    }

    @Override
    public boolean existsById(Long id) {
        return reporteDao.existsById(id);
    }

    @Override
    public List<Reporte> findAll() {
        return (List<Reporte>) reporteDao.findAll();
    }
}
