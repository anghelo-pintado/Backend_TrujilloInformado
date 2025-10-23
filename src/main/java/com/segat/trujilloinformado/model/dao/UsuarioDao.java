package com.segat.trujilloinformado.model.dao;

import com.segat.trujilloinformado.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);

    @Query("select u from Usuario u where u.zone.number = :number and u.role = com.segat.trujilloinformado.model.entity.enums.Role.TRABAJADOR")
    List<Usuario> findTrabajadoresByZoneNumber(@Param("number") Integer number);

    @Query("select u from Usuario u left join fetch u.zone where u.email = :email")
    Optional<Usuario> findByEmailWithZone(@Param("email") String email);
}
