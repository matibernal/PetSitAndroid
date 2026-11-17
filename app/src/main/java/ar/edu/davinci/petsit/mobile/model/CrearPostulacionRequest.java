package ar.edu.davinci.petsit.mobile.model;

public class CrearPostulacionRequest {

    private final Long adopcionId;
    private final String mensaje;

    public CrearPostulacionRequest(Long adopcionId, String mensaje) {
        this.adopcionId = adopcionId;
        this.mensaje = mensaje;
    }
}
