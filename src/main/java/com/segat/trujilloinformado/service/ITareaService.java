package com.segat.trujilloinformado.service;

import com.segat.trujilloinformado.model.dto.TareaDto;
import com.segat.trujilloinformado.model.entity.Tarea;

import java.util.List;

public interface ITareaService {
    Tarea save(TareaDto tareaDto);
    Tarea findById(Long id);
    void delete(Tarea tarea);
    boolean existsById(Long id);
    List<Tarea> findAll();
}
