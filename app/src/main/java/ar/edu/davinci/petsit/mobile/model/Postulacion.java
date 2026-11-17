package ar.edu.davinci.petsit.mobile.model;

public class Postulacion {

    private Long id;
    private Long adopcionId;
    private String mascotaNombre;
    private String mascotaFoto;
    private Long publicadorId;
    private String publicadorNombre;
    private Long usuarioId;
    private String usuarioNombre;
    private String usuarioCorreo;
    private String usuarioTelefono;
    private String fecha;
    private String mensaje;

    public Long getId() {
        return id;
    }

    public Long getAdopcionId() {
        return adopcionId;
    }

    public String getMascotaNombre() {
        return mascotaNombre;
    }

    public String getMascotaFoto() {
        return mascotaFoto;
    }

    public Long getPublicadorId() {
        return publicadorId;
    }

    public String getPublicadorNombre() {
        return publicadorNombre;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public String getUsuarioTelefono() {
        return usuarioTelefono;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMensaje() {
        return mensaje;
    }
}
