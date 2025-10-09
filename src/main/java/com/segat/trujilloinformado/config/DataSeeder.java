package com.segat.trujilloinformado.config;

import com.segat.trujilloinformado.model.dao.UsuarioDao;
import com.segat.trujilloinformado.model.entity.Usuario;
import com.segat.trujilloinformado.model.entity.enums.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedUsers(UsuarioDao usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByEmail("ciudadano1@gmail.com").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .email("ciudadano1@gmail.com")
                        .password(passwordEncoder.encode("ciudadano1"))
                        .role(Role.CIUDADANO)
                        .firstname("ciudadano1")
                        .lastname("User")
                        .phone("111111111")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build());
            }
            if (usuarioRepository.findByEmail("ciudadano2@gmail.com").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .email("ciudadano2@gmail.com")
                        .password(passwordEncoder.encode("ciudadano2"))
                        .role(Role.CIUDADANO)
                        .firstname("ciudadano2")
                        .lastname("User")
                        .phone("111111111")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build());
            }
            if (usuarioRepository.findByEmail("supervisor@gmail.com").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .email("supervisor@gmail.com")
                        .password(passwordEncoder.encode("supervisor"))
                        .role(Role.SUPERVISOR)
                        .firstname("Supervisor")
                        .lastname("User")
                        .phone("111111111")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build());
            }
            if (usuarioRepository.findByEmail("trabajador@gmail.com").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .email("trabajador@gmail.com")
                        .password(passwordEncoder.encode("trabajador"))
                        .role(Role.TRABAJADOR)
                        .firstname("Worker")
                        .lastname("User")
                        .phone("222222222")
                        .birthDate(LocalDate.of(1995, 1, 1))
                        .build());
            }
        };
    }
}
