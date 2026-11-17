package ar.edu.davinci.petsit.mobile.model;

public class CrearAdopcionRequest {

    private final Long mascotaId;
    private final String descripcion;
    private final String ubicacion;

    public CrearAdopcionRequest(Long mascotaId, String descripcion, String ubicacion) {
        this.mascotaId = mascotaId;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }
}
