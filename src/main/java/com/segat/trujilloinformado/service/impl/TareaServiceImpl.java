package com.segat.trujilloinformado.service.impl;

import com.segat.trujilloinformado.model.dao.TareaDao;
import com.segat.trujilloinformado.model.dto.TareaDto;
import com.segat.trujilloinformado.model.entity.Tarea;
import com.segat.trujilloinformado.service.ITareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class TareaServiceImpl implements ITareaService {
    @Autowired
    private TareaDao tareaDao;

    @Transactional
    @Override
    public Tarea save(TareaDto tareaDto) {
        Tarea tarea = Tarea.builder()
                .id(tareaDto.getId())
                .reportId(tareaDto.getReportId())
                .workerId(tareaDto.getWorkerId())
                .supervisorId(tareaDto.getSupervisorId())
                .title(tareaDto.getTitle())
                .description(tareaDto.getDescription())
                .notes(tareaDto.getNotes())
                .type(tareaDto.getType())
                .lat(tareaDto.getLocation().getLat())
                .lng(tareaDto.getLocation().getLng())
                .address(tareaDto.getLocation().getAddress())
                .status(tareaDto.getStatus())
                .priority(tareaDto.getPriority())
                .completedAt(tareaDto.getCompletedAt() != null ? Instant.parse(tareaDto.getCompletedAt()) : null)
                .build();
        return tareaDao.save(tarea);
    }

    @Transactional(readOnly = true)
    @Override
    public Tarea findById(Long id) {
        return tareaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Tarea tarea) {
        tareaDao.delete(tarea);
    }

    @Override
    public boolean existsById(Long id) {
        return tareaDao.existsById(id);
    }

    @Override
    public List<Tarea> findAll() {
        return (List<Tarea>) tareaDao.findAll();
    }
}
