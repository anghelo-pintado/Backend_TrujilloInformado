package com.segat.trujilloinformado.service.impl;

import com.segat.trujilloinformado.model.dao.ReporteDao;
import com.segat.trujilloinformado.model.dto.ReporteDto;
import com.segat.trujilloinformado.model.entity.Reporte;
import com.segat.trujilloinformado.model.entity.enums.Status;
import com.segat.trujilloinformado.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                .id(reporteDto.getId())
                .type(reporteDto.getType())
                .description(reporteDto.getDescription())
                .lat(reporteDto.getLocation().getLat())
                .lng(reporteDto.getLocation().getLng())
                .address(reporteDto.getLocation().getAddress())
                .photos(String.join(",", reporteDto.getPhotos()))
                .priority(reporteDto.getPriority())
                .zone(reporteDto.getZone())
                .citizenId(Long.valueOf(reporteDto.getCitizenId()))
                .citizenName(reporteDto.getCitizenName())
                .citizenPhone(reporteDto.getCitizenPhone())
                .citizenEmail(reporteDto.getCitizenEmail())
                .status(reporteDto.getStatus() != null ? reporteDto.getStatus() : Status.PENDIENTE)
                .createdAt(reporteDto.getCreatedAt())
                .updatedAt(reporteDto.getUpdatedAt())
                .assignedTo(reporteDto.getAssignedTo())
                .assignedBy(reporteDto.getAssignedBy())
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

    @Override
    public Page<Reporte> findAll(Pageable pageable) {
        return reporteDao.findAll(pageable);
    }
}
