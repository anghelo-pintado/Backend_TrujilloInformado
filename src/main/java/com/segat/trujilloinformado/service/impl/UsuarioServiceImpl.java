package com.segat.trujilloinformado.service.impl;

import com.segat.trujilloinformado.model.dao.UsuarioDao;
import com.segat.trujilloinformado.model.dto.authentication.RegisterRequest;
import com.segat.trujilloinformado.model.entity.Usuario;
import com.segat.trujilloinformado.model.entity.enums.Role;
import com.segat.trujilloinformado.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    private UsuarioDao usuarioDao;

    @Transactional
    @Override
    public Usuario save(RegisterRequest registerRequest) {
        Usuario usuario = Usuario.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .birthDate(LocalDate.parse(registerRequest.getBirthdate()))
                .password(registerRequest.getPassword())
                .role(Role.CIUDADANO)
                .build();
        return usuarioDao.save(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findById(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioDao.findAll();
    }

    @Override
    public List<Usuario> findTrabajadoresByZoneNumber(Integer number) {
        return usuarioDao.findTrabajadoresByZoneNumber(number);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioDao.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmailWithZone(String email) {
        return usuarioDao.findByEmailWithZone(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return usuarioDao.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        usuarioDao.deleteById(id);
    }
}
