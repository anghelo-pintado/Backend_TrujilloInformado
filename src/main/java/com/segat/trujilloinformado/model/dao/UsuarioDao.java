package com.segat.trujilloinformado.model.dao;

import com.segat.trujilloinformado.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
