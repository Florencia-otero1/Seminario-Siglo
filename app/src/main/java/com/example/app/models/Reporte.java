package com.example.app.models;

import javafx.beans.property.*;
/**
 * Representa un reporte de mascota perdida / encontrada dentro del sistema.
 * Utiliza propiedades JavaFX para vincular datos con la interfaz (TableView).
 */
public class Reporte {

    // Properties para la UI (JavaFX Binding)
    private IntegerProperty idReporte;
    private StringProperty tipoReporte;
    private StringProperty especie;
    private StringProperty descripcion;
    private StringProperty ubicacion;

    // Campos adicionales para guardar info si es necesario
    private String sexo;
    private String color;
    private Integer edadAprox;
    private String fotoUrl;
    private int idUsuario;
    private String nombreMascota;

    // Constructor usado para mostrar reportes en tabla
    public Reporte(int idReporte, String tipoReporte, String especie, String descripcion, String ubicacion) {
        this.idReporte = new SimpleIntegerProperty(idReporte);
        this.tipoReporte = new SimpleStringProperty(tipoReporte);
        this.especie = new SimpleStringProperty(especie);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.ubicacion = new SimpleStringProperty(ubicacion);
    }

    // Constructor vacío para crear nuevos reportes desde el formulario
    public Reporte() {}

    // Getters para TableView
    public int getIdReporte() { return idReporte.get(); }
    public IntegerProperty idProperty() { return idReporte; }
    public void setIdReporte(int idReporte) {
        if (this.idReporte == null)
            this.idReporte = new SimpleIntegerProperty();
        this.idReporte.set(idReporte);
    }

    public String getTipoReporte() { return tipoReporte.get(); }
    public StringProperty tipoReporteProperty() { return tipoReporte; }
    public void setTipoReporte(String tipoReporte) {
        if (this.tipoReporte == null) this.tipoReporte = new SimpleStringProperty();
        this.tipoReporte.set(tipoReporte);
    }

    public String getEspecie() { return especie.get(); }
    public StringProperty especieProperty() { return especie; }
    public void setEspecie(String especie) {
        if (this.especie == null) this.especie = new SimpleStringProperty();
        this.especie.set(especie);
    }

    public String getDescripcion() { return descripcion.get(); }
    public StringProperty descripcionProperty() { return descripcion; }
    public void setDescripcion(String descripcion) {
        if (this.descripcion == null) this.descripcion = new SimpleStringProperty();
        this.descripcion.set(descripcion);
    }

    public String getUbicacion() { return ubicacion.get(); }
    public StringProperty ubicacionProperty() { return ubicacion; }
    public void setUbicacion(String ubicacion) {
        if (this.ubicacion == null) this.ubicacion = new SimpleStringProperty();
        this.ubicacion.set(ubicacion);
    }

    // Campos usados al crear reporte
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getEdadAprox() { return edadAprox; }
    public void setEdadAprox(Integer edadAprox) { this.edadAprox = edadAprox; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreMascota() { return nombreMascota; }
    public void setNombreMascota(String nombreMascota) { this.nombreMascota = nombreMascota; }
}
