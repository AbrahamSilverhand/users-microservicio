package cl.milsabores.users.service.controller;

import cl.milsabores.users.service.model.Usuario;
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

    // 🔹 REGISTRO (crear cuenta)
    @PostMapping("/register")
    public Usuario registrar(@RequestBody Usuario usuario) {
        // validación básica: email único
        repository.findByEmail(usuario.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("El correo ya está registrado");
                });

        // si quieres, puedes dejar telefono vacío por ahora
        if (usuario.getTelefono() == null) {
            usuario.setTelefono("");
        }

        return repository.save(usuario);
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest request) {
        Usuario usuario = repository
                .findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        return ResponseEntity.ok(usuario);
    }

    // (opcional) SIGUE EXISTIENDO EL CREATE GENÉRICO
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }
}
