package cl.milsabores.users.service.controller;

import cl.milsabores.users.service.model.Usuario;
import cl.milsabores.users.service.model.AuthResponse;
import cl.milsabores.users.service.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrar(@RequestBody Usuario usuario) {
        repository.findByEmail(usuario.getEmail())
                .ifPresent(u -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
                });

        if (usuario.getTelefono() == null) {
            usuario.setTelefono("");
        }

        Usuario guardado = repository.save(usuario);
        return ResponseEntity.ok(new AuthResponse(guardado));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Usuario usuario = repository
                .findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas")
                );

        return ResponseEntity.ok(new AuthResponse(usuario));
    }


    // opcional
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }
}
