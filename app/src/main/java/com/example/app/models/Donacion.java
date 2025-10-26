package com.example.app.models;

import java.time.LocalDateTime;
/**
 * Representa una donación realizada por un usuario.
 * La clase se usa principalmente para almacenar y transferir datos.
 */
public class Donacion {
    private Integer idDonacion;
    private Integer idUsuario;
    private Double monto;
    private LocalDateTime fecha;
    private String estado;
    private String comprobanteUrl;

    // Getters y setters (Encapsulamiento)
    public Integer getIdDonacion() { return idDonacion; }
    public void setIdDonacion(Integer idDonacion) { this.idDonacion = idDonacion; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getComprobanteUrl() { return comprobanteUrl; }
    public void setComprobanteUrl(String comprobanteUrl) { this.comprobanteUrl = comprobanteUrl; }
}
