package ar.edu.davinci.petsit.mobile.model;

public class Veterinaria {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String horarioAtencion;
    private String ubicacion;
    private String foto;
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

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getFoto() {
        return foto;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
