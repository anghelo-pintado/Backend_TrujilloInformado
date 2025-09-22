package com.segat.trujilloinformado.model.dao;

import com.segat.trujilloinformado.model.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaDao extends JpaRepository<Tarea, Long> {
}
