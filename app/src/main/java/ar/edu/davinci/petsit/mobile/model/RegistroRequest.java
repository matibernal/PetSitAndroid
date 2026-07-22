package ar.edu.davinci.petsit.mobile.model;

public class RegistroRequest {

    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String telefono;

    public RegistroRequest(String nombre, String apellido, String correo, String contrasena, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }
}
