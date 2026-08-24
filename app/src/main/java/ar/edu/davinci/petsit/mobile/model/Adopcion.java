package ar.edu.davinci.petsit.mobile.model;

public class Adopcion {

    private Long id;
    private Long mascotaId;
    private String mascotaNombre;
    private String mascotaFoto;
    private Long usuarioId;
    private String usuarioNombre;
    private String fechaPublicacion;
    private String estado;
    private String descripcion;
    private String foto;
    private String ubicacion;
    private Long refugioId;
    private String refugioNombre;

    public Long getId() {
        return id;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public String getMascotaNombre() {
        return mascotaNombre;
    }

    public String getMascotaFoto() {
        return mascotaFoto;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Long getRefugioId() {
        return refugioId;
    }

    public String getRefugioNombre() {
        return refugioNombre;
    }
}
