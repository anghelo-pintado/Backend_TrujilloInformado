package com.segat.trujilloinformado.controller;

import com.segat.trujilloinformado.model.dto.ReporteDto;
import com.segat.trujilloinformado.model.dto.TareaDto;
import com.segat.trujilloinformado.model.entity.Reporte;
import com.segat.trujilloinformado.model.entity.Tarea;
import com.segat.trujilloinformado.model.entity.interno.Location;
import com.segat.trujilloinformado.model.payload.MessageResponse;
import com.segat.trujilloinformado.service.ITareaService;
import com.segat.trujilloinformado.service.impl.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TareaController {
    @Autowired
    private ITareaService tareaService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("tarea")
    public ResponseEntity<?> create(@RequestBody TareaDto tareaDto) {
        Tarea tareaSave = null;
        try {
            tareaSave = tareaService.save(tareaDto);
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .objecto(TareaDto.builder()
                                    .id(tareaSave.getId())
                                    .reportId(tareaSave.getReportId())
                                    .workerId(tareaSave.getWorkerId())
                                    .supervisorId(tareaSave.getSupervisorId())
                                    .title(tareaSave.getTitle())
                                    .description(tareaSave.getDescription())
                                    .notes(tareaSave.getNotes())
                                    .type(tareaSave.getType())
                                    .location(Location.builder()
                                            .lat(tareaSave.getLat())
                                            .lng(tareaSave.getLng())
                                            .address(tareaSave.getAddress())
                                            .build())
                                    .status(tareaSave.getStatus())
                                    .priority(tareaSave.getPriority())
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

    @PutMapping("tarea/{id}")
    public ResponseEntity<?> update(@RequestBody TareaDto tareaDto, @PathVariable Long id) {
        Tarea tareaUpdate = null;
        try {
            if (tareaService.existsById(id)) {
                tareaDto.setId(id);
                tareaUpdate = tareaService.save(tareaDto);
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .objecto(TareaDto.builder()
                                        .id(tareaUpdate.getId())
                                        .status(tareaUpdate.getStatus())
                                        .completedAt(String.valueOf(tareaUpdate.getCompletedAt()))
                                        .notes(tareaUpdate.getNotes())
                                        .build())
                                .build()
                        , HttpStatus.CREATED);
            }
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .mensaje("La tarea con ID " + id + " no existe.")
                            .build()
                    , HttpStatus.NOT_FOUND);
        }
        catch (DataAccessException e) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .mensaje("Error al actualizar la tarea: " + e.getMessage())
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/tareas")
    public ResponseEntity<?> showAll() {
        List<Tarea> tareas = tareaService.findAll();
        List<TareaDto> dtos = tareas.stream().map(tarea ->
                TareaDto.builder()
                        .id(tarea.getId())
                        .reportId(tarea.getReportId())
                        .workerId(tarea.getWorkerId())
                        .supervisorId(tarea.getSupervisorId())
                        .title(tarea.getTitle())
                        .description(tarea.getDescription())
                        .notes(tarea.getNotes())
                        .type(tarea.getType())
                        .location(
                                Location.builder()
                                        .lat(tarea.getLat())
                                        .lng(tarea.getLng())
                                        .address(tarea.getAddress())
                                        .build()
                        )
                        .status(tarea.getStatus())
                        .priority(tarea.getPriority())
                        .build()
        ).toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/tarea/cargar")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        String simulatedUrl = cloudinaryService.uploadFile(file, "tareas");
        return ResponseEntity.ok(simulatedUrl);
    }
}
