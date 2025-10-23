package com.segat.trujilloinformado.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.segat.trujilloinformado.model.dao.UsuarioDao;
import com.segat.trujilloinformado.model.dao.ZonaDao;
import com.segat.trujilloinformado.model.entity.Usuario;
import com.segat.trujilloinformado.model.entity.Zona;
import com.segat.trujilloinformado.model.entity.enums.Role;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;


@Configuration
public class DataSeeder {
    private static final int SRID_WGS84 = 4326;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID_WGS84);

    @Bean
    @Order(1)
    CommandLineRunner seedZones(ZonaDao zonaRepository, ObjectMapper objectMapper) throws Exception {
        return args -> {
            if (zonaRepository.count() > 0) {
                System.out.println("ℹ️ Las zonas ya están cargadas");
                return;
            }

            // Leer archivo JSON
            Resource resource = new ClassPathResource("data/zonas.json");
            JsonNode zonasJson = objectMapper.readTree(resource.getInputStream());

            for (JsonNode zonaJson : zonasJson) {
                String nombre = zonaJson.get("name").asText();
                Integer zonaNumero = zonaJson.get("number").asInt();

                JsonNode coordenadasJson = zonaJson.get("boundaries");
                Coordinate[] coords = new Coordinate[coordenadasJson.size()];

                for (int i = 0; i < coordenadasJson.size(); i++) {
                    JsonNode coord = coordenadasJson.get(i);
                    coords[i] = new Coordinate(
                            coord.get(0).asDouble(), // lng
                            coord.get(1).asDouble()  // lat
                    );
                }

                Polygon poligono = geometryFactory.createPolygon(coords);
                poligono.setSRID(SRID_WGS84);

                Zona zona = Zona.builder()
                        .name(nombre)
                        .number(zonaNumero)
                        .boundaries(poligono)
                        .build();

                zonaRepository.save(zona);
            }

            System.out.println("✅ Zonas cargadas desde JSON: " + zonaRepository.count());
        };
    }

    @Bean
    @Order(2)
    CommandLineRunner seedUsers(UsuarioDao usuarioRepository, ZonaDao zonaDao,PasswordEncoder passwordEncoder) {
        return args -> {
            for (int i = 1; i <= 5; i++) {
                String email = "supervisorzona" + i + "@gmail.com";
                if (usuarioRepository.findByEmail(email).isEmpty()) {
                    usuarioRepository.save(Usuario.builder()
                            .email(email)
                            .password(passwordEncoder.encode("supervisorzona" + i))
                            .role(Role.SUPERVISOR)
                            .zone(zonaDao.findByNumber(i).orElseThrow())
                            .firstname("Supervisor")
                            .lastname("Zona " + i)
                            .phone("987654321")
                            .birthDate(LocalDate.of(1990, 1, 1))
                            .build());
                }
            }
            if (usuarioRepository.findByEmail("supervisorzona6mañana@gmail.com").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .email("supervisorzona6mañana@gmail.com")
                        .password(passwordEncoder.encode("supervisorzona6mañana"))
                        .role(Role.SUPERVISOR)
                        .zone(zonaDao.findByNumber(6).orElseThrow())
                        .firstname("Supervisor")
                        .lastname("Zona 6 Mañana")
                        .phone("987654321")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build());
            }
            if (usuarioRepository.findByEmail("supervisorzona6tarde@gmail.com").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .email("supervisorzona6tarde@gmail.com")
                        .password(passwordEncoder.encode("supervisorzona6tarde"))
                        .role(Role.SUPERVISOR)
                        .zone(zonaDao.findByNumber(6).orElseThrow())
                        .firstname("Supervisor")
                        .lastname("Zona 6 Tarde")
                        .phone("987654321")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build());
            }
            if (usuarioRepository.findByEmail("supervisorzona6noche@gmail.com").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .email("supervisorzona6noche@gmail.com")
                        .password(passwordEncoder.encode("supervisorzona6noche"))
                        .role(Role.SUPERVISOR)
                        .firstname("Supervisor")
                        .zone(zonaDao.findByNumber(6).orElseThrow())
                        .lastname("Zona 6 Noche")
                        .phone("987654321")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build());
            }
            if (usuarioRepository.findByEmail("colaboradorzona4-1@gmail.com").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .email("colaboradorzona4-1@gmail.com")
                        .password(passwordEncoder.encode("colaboradorzona4-1"))
                        .role(Role.TRABAJADOR)
                        .zone(zonaDao.findByNumber(4).orElseThrow())
                        .firstname("Colaborador")
                        .lastname("Zona 4 - 1")
                        .phone("964144695")
                        .birthDate(LocalDate.of(1995, 1, 1))
                        .build());
            }
        };
    }

}
