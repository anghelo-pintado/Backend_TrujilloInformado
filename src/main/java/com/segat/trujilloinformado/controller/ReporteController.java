package com.segat.trujilloinformado.controller;

import com.segat.trujilloinformado.model.dto.ReporteDto;
import com.segat.trujilloinformado.model.entity.Reporte;
import com.segat.trujilloinformado.model.entity.Usuario;
import com.segat.trujilloinformado.model.entity.interno.Location;
import com.segat.trujilloinformado.model.payload.MessageResponse;
import com.segat.trujilloinformado.service.IReporteService;
import com.segat.trujilloinformado.service.impl.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1")
public class ReporteController {
    @Autowired
    private IReporteService reporteService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("reporte")
    public ResponseEntity<?> create(@RequestBody ReporteDto reporteDto) {
        Reporte reporteSave = null;
        Usuario usuario = null;
        try {
            reporteSave = reporteService.save(reporteDto);
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .objecto(ReporteDto.builder()
                                    .id(reporteSave.getId())
                                    .type(reporteSave.getType())
                                    .description(reporteSave.getDescription())
                                    .location(Location.builder()
                                            .lat(reporteSave.getLat())
                                            .lng(reporteSave.getLng())
                                            .address(reporteSave.getAddress())
                                            .build())
                                    .photos(reporteDto.getPhotos())
                                    .priority(reporteSave.getPriority())
                                    .zone(reporteSave.getZone())
                                    .status(reporteSave.getStatus())
                                    .citizenId(String.valueOf(reporteSave.getCitizen().getId()))
                                    .citizenName(reporteSave.getCitizen().getFirstname() + " " + reporteSave.getCitizen().getLastname())
                                    .citizenPhone(reporteSave.getCitizen().getPhone())
                                    .citizenEmail(reporteSave.getCitizen().getEmail())
                                    .createdAt(reporteSave.getCreatedAt())
                                    .build())
                            .build()
                    , HttpStatus.CREATED);
        }
        catch (DataAccessException e) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .mensaje("Error al insertar el reporte: " + e.getMessage())
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("reporte/{id}")
    public ResponseEntity<?> update(@RequestBody ReporteDto reporteDto, @PathVariable Long id) {
        Reporte reporteUpdate = null;
        try {
            if (reporteService.existsById(id)) {
                reporteDto.setId(id);
                reporteUpdate = reporteService.save(reporteDto);
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .objecto(ReporteDto.builder()
                                        .id(reporteUpdate.getId())
                                        .status(reporteUpdate.getStatus())
                                        .assignedTo(reporteUpdate.getAssignedTo())
                                        .assignedBy(reporteUpdate.getAssignedBy())
                                        .build())
                                .build()
                        , HttpStatus.CREATED);
            }
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .mensaje("El reporte con ID " + id + " no existe.")
                            .build()
                    , HttpStatus.NOT_FOUND);
        }
        catch (DataAccessException e) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .mensaje("Error al actualizar el reporte: " + e.getMessage())
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

//    @DeleteMapping("reporte/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        Reporte reporte = null;
//        try {
//            reporte = reporteService.findById(id);
//            if (reporte == null) {
//                return new ResponseEntity<>(
//                        MessageResponse.builder()
//                                .mensaje("El reporte con ID " + id + " no existe.")
//                                .build()
//                        , HttpStatus.NOT_FOUND);
//            }
//            reporteService.delete(reporte);
//            // 204 No Content se utiliza cuando la eliminación es exitosa y no se envía contenido en la respuesta.
//            return ResponseEntity.noContent().build();
//        }
//        catch (DataAccessException e) {
//            return new ResponseEntity<>(
//                    MessageResponse.builder()
//                            .mensaje("Error al eliminar el reporte: " + e.getMessage())
//                            .build()
//                    , HttpStatus.METHOD_NOT_ALLOWED);
//        }
//    }
//
    @GetMapping("reporte/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Reporte reporte = reporteService.findById(id);
        if (reporte == null) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .mensaje("El reporte con ID " + id + " no existe.")
                            .build()
                    , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .objecto(ReporteDto.builder()
                                .id(reporte.getId())
                                .description(reporte.getDescription())
                                .type(reporte.getType())
                                .status(reporte.getStatus())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

//    @GetMapping("/reportes")
//    public ResponseEntity<?> showAll() {
//        List<Reporte> reportes = reporteService.findAll();
//        List<ReporteDto> dtos = reportes.stream().map(reporte ->
//                ReporteDto.builder()
//                        .id(reporte.getId())
//                        .type(reporte.getType())
//                        .description(reporte.getDescription())
//                        .location(
//                                Location.builder()
//                                        .lat(reporte.getLat())
//                                        .lng(reporte.getLng())
//                                        .address(reporte.getAddress())
//                                        .build()
//                        )
//                        .photos(Arrays.asList(reporte.getPhotos() != null ? reporte.getPhotos().split(",") : new String[0]))
//                        .priority(reporte.getPriority())
//                        .zone(reporte.getZone())
//                        .status(reporte.getStatus())
//                        .citizenId(reporte.getCitizenId() != null ? reporte.getCitizenId().toString() : null)
//                        .citizenName(reporte.getCitizenName())
//                        .citizenPhone(reporte.getCitizenPhone())
//                        .createdAt(reporte.getCreatedAt())
//                        .updatedAt(reporte.getUpdatedAt())
//                        .build()
//        ).toList();
//
//        return ResponseEntity.ok(dtos);
//    }

    @GetMapping("/reportes")
    public ResponseEntity<?> getReports(Pageable pageable) {
        Page<Reporte> reportes = reporteService.findAll(pageable);
        Page<ReporteDto> dtos = reportes.map(reporte -> ReporteDto.builder()
                .id(reporte.getId())
                .type(reporte.getType())
                .description(reporte.getDescription())
                .location(Location.builder()
                        .lat(reporte.getLat())
                        .lng(reporte.getLng())
                        .address(reporte.getAddress())
                        .build())
                .photos(Arrays.asList(reporte.getPhotos() != null ? reporte.getPhotos().split(",") : new String[0]))
                .priority(reporte.getPriority())
                .zone(reporte.getZone())
                .status(reporte.getStatus())
                .citizenId(reporte.getCitizen() != null ? String.valueOf(reporte.getCitizen().getId()) : null)
                .citizenName(reporte.getCitizen() != null ? reporte.getCitizen().getFirstname() + " " + reporte.getCitizen().getLastname() : null)
                .citizenPhone(reporte.getCitizen() != null ? reporte.getCitizen().getPhone() : null)
                .citizenEmail(reporte.getCitizen() != null ? reporte.getCitizen().getEmail() : null)
                .createdAt(reporte.getCreatedAt())
                .updatedAt(reporte.getUpdatedAt())
                .build());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/reporte/cargar")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        String simulatedUrl = cloudinaryService.uploadFile(file, "reportes");
        return ResponseEntity.ok(simulatedUrl);
    }
}
