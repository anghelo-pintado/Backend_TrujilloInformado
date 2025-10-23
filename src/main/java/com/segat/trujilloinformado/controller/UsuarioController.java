package com.segat.trujilloinformado.controller;

import com.segat.trujilloinformado.model.dto.TrabajadorDto;
import com.segat.trujilloinformado.model.entity.Usuario;
import com.segat.trujilloinformado.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/trabajadores")
    public ResponseEntity<?> getCurrentUserWorkers(@AuthenticationPrincipal UserDetails userDetails) {
        String supervisorEmail = userDetails.getUsername();
        Usuario supervisor = usuarioService.findByEmailWithZone(supervisorEmail)
                .orElseThrow(() -> new IllegalStateException("Supervisor not found"));
        Integer zoneNumber = supervisor.getZone().getNumber();
        List<Usuario> trabajadores = usuarioService.findTrabajadoresByZoneNumber(zoneNumber);
        List<TrabajadorDto> dtos = trabajadores.stream().map(trabajador ->
            TrabajadorDto.builder()
                    .id(trabajador.getId())
                    .name(trabajador.getFirstname())
                    .build()
        ).toList();
        return ResponseEntity.ok(dtos);
    }
}
