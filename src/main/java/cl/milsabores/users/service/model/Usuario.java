package cl.milsabores.users.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String telefono;

    // Campo password agregado correctamente
    private String password;

    // Constructor vacío requerido por JPA
    public Usuario() {
    }

    // Constructor con 4 parámetros (Corrección: agregada la asignación de password)
    public Usuario(String nombre, String email, String telefono, String password) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.password = password; // <--- ¡Esto faltaba!
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // 👇 ESTOS ERAN LOS QUE FALTABAN PARA QUE SPRING NO FALLE 👇
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}