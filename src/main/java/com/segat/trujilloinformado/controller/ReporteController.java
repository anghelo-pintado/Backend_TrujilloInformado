package com.segat.trujilloinformado.controller;

import com.segat.trujilloinformado.model.dto.ReporteDto;
import com.segat.trujilloinformado.model.entity.Reporte;
import com.segat.trujilloinformado.model.payload.MessageResponse;
import com.segat.trujilloinformado.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ReporteController {
    @Autowired
    private IReporteService reporteService;

    @PostMapping("reporte")
    public ResponseEntity<?> create(@RequestBody ReporteDto reporteDto) {
        Reporte reporteSave = null;
        try {
            reporteSave = reporteService.save(reporteDto);
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .objecto(ReporteDto.builder().
                                    idReporte(reporteSave.getId())
                                    .descripcion(reporteSave.getDescripcion())
                                    .tipoProblema(reporteSave.getTipoProblema())
                                    .estado(reporteSave.getEstado())
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
                reporteDto.setIdReporte(id);
                reporteUpdate = reporteService.save(reporteDto);
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .objecto(ReporteDto.builder()
                                        .idReporte(reporteUpdate.getId())
                                        .descripcion(reporteUpdate.getDescripcion())
                                        .tipoProblema(reporteUpdate.getTipoProblema())
                                        .estado(reporteUpdate.getEstado())
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

    @DeleteMapping("reporte/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Reporte reporte = null;
        try {
            reporte = reporteService.findById(id);
            if (reporte == null) {
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .mensaje("El reporte con ID " + id + " no existe.")
                                .build()
                        , HttpStatus.NOT_FOUND);
            }
            reporteService.delete(reporte);
            // 204 No Content se utiliza cuando la eliminación es exitosa y no se envía contenido en la respuesta.
            return ResponseEntity.noContent().build();
        }
        catch (DataAccessException e) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .mensaje("Error al eliminar el reporte: " + e.getMessage())
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

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
                                .idReporte(reporte.getId())
                                .descripcion(reporte.getDescripcion())
                                .tipoProblema(reporte.getTipoProblema())
                                .estado(reporte.getEstado())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("reportes")
    public ResponseEntity<?> showAll() {
        List<Reporte> reportes = reporteService.findAll();

        if (reportes.isEmpty()) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .mensaje("No hay reportes registrados.")
                            .build()
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .objecto(reportes)
                        .build()
                , HttpStatus.OK);
    }
}
