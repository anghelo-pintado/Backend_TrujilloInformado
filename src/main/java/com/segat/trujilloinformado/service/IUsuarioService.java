package com.segat.trujilloinformado.service;

import com.segat.trujilloinformado.model.dto.authentication.RegisterRequest;
import com.segat.trujilloinformado.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Usuario save(RegisterRequest registerRequest);
    Usuario findById(Long id);
    List<Usuario> findAll();
    Usuario findByEmail(String email);
    Boolean existsByEmail(String email);
    void deleteById(Long id);
}
