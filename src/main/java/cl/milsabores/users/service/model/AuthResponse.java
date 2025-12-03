package cl.milsabores.users.service.model;

public class AuthResponse {

    private Usuario usuario;

    public AuthResponse() {
    }

    public AuthResponse(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
