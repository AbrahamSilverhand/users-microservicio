package cl.milsabores.users.service;

import cl.milsabores.users.service.model.Usuario;
import cl.milsabores.users.service.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsers(UsuarioRepository repository) {
        return args -> {
            repository.save(
                    new Usuario(
                            "Juan Pérez",
                            "juan@example.com",
                            "+56911111111",
                            "1234" // <--- Agregada password
                    )
            );

            repository.save(
                    new Usuario(
                            "María López",
                            "maria@example.com",
                            "+56922222222",
                            "1234" // <--- Agregada password
                    )
            );
        };
    }
}