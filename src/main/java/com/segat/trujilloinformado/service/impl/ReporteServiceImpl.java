package com.segat.trujilloinformado.service.impl;

import com.segat.trujilloinformado.model.dao.ReporteDao;
import com.segat.trujilloinformado.model.dto.ReporteDto;
import com.segat.trujilloinformado.model.entity.Reporte;
import com.segat.trujilloinformado.model.entity.Usuario;
import com.segat.trujilloinformado.model.entity.Zona;
import com.segat.trujilloinformado.model.entity.enums.Status;
import com.segat.trujilloinformado.service.IReporteService;
import com.segat.trujilloinformado.service.IUsuarioService;
import com.segat.trujilloinformado.service.IZonaService;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteServiceImpl implements IReporteService {
    @Autowired
    private ReporteDao reporteDao;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IZonaService zonaService;

    @Transactional
    @Override
    public Reporte save(ReporteDto dto) {
        // 1) Validar ciudadano
        Usuario usuario = usuarioService.findById(Long.valueOf(dto.getCitizenId()));
        if (usuario == null) {
            throw new IllegalArgumentException("El ciudadano con ID " + dto.getCitizenId() + " no existe.");
        }

        // 2) Tomar lng/lat (¡en ese orden!)
        double lng = dto.getLocation().getLng();
        double lat = dto.getLocation().getLat();

        // 3) Encontrar zona obligatoria (si no hay, 400)
        Zona zona = zonaService.classifyLocation(lng, lat)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La ubicación (" + lat + ", " + lng + ") no pertenece a ninguna zona definida."));

        // 4) Construir el Point con SRID 4326
        Point point = zonaService.createPoint(lng, lat);

        // 5) Armar la entidad y guardar
        Reporte reporte = Reporte.builder()
                .id(dto.getId())
                .type(dto.getType())
                .description(dto.getDescription())
                .lat(lat)
                .lng(lng)
                .address(dto.getLocation().getAddress())
                .point(point)
                .photos(dto.getPhotos() != null ? String.join(",", dto.getPhotos()) : null)
                .priority(dto.getPriority())
                .zone(zona) // <-- garantizado
                .citizen(usuario)
                .status(dto.getStatus() != null ? dto.getStatus() : Status.PENDIENTE)
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .assignedAt(dto.getAssignedAt())
                .completedAt(dto.getCompletedAt())
                .build();

        return reporteDao.save(reporte);
    }

    @Transactional(readOnly = true)
    @Override
    public Reporte findById(Long id) {
        return reporteDao.findById(id).orElse(null);
    }

    @Override
    public Page<Reporte> findByCitizenEmail(String email, Pageable pageable) {
        return reporteDao.findByCitizenEmail(email, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reporte> findByZoneNumber(Integer number, Pageable pageable) {
        return reporteDao.findByZoneNumber(number, pageable);
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
