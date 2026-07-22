package ar.edu.davinci.petsit.mobile.model;

public class Mascota {

    private Long id;
    private String nombre;
    private String raza;
    private String color;
    private String tamano; // "Chico" | "Mediano" | "Grande"
    private String descripcion;
    private String foto;
    private String sexo; // "Macho" | "Hembra"
    private Long duenoId;
    private String duenoNombre;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Long getDuenoId() {
        return duenoId;
    }

    public String getDuenoNombre() {
        return duenoNombre;
    }
}
