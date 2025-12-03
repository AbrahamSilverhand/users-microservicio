package cl.milsabores.users.service.controller;

import cl.milsabores.users.service.model.Usuario;
import cl.milsabores.users.service.model.AuthResponse;
import cl.milsabores.users.service.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    // LISTAR
    @GetMapping
    public List<Usuario> listar() {
        return repository.findAll();
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    }

    // 🔹 REGISTRO
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrar(@RequestBody Usuario usuario) {
        repository.findByEmail(usuario.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("El correo ya está registrado");
                });

        if (usuario.getTelefono() == null) {
            usuario.setTelefono("");
        }

        Usuario guardado = repository.save(usuario);
        return ResponseEntity.ok(new AuthResponse(guardado));
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Usuario usuario = repository
                .findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        return ResponseEntity.ok(new AuthResponse(usuario));
    }

    // OPCIONAL: crear genérico
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }
}
