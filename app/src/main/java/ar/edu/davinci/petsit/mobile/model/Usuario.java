package ar.edu.davinci.petsit.mobile.model;

public class Usuario {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String tipo; // DUENO | VETERINARIO | REFUGIO | ADMINISTRADOR
    private String telefono;
    private String fotoPerfil;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public String getNombreCompleto() {
        if (apellido == null || apellido.isEmpty()) return nombre;
        return nombre + " " + apellido;
    }
}
