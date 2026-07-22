package ar.edu.davinci.petsit.mobile.model;

public class Refugio {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String foto;
    private String ubicacion;
    private Double lat;
    private Double lng;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFoto() {
        return foto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
