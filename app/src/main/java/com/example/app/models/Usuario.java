package com.example.app.models;
/**
 * Representa a un usuario dentro del sistema.
 * Esta clase funciona como clase base para diferentes tipos de usuarios.
 *
 * Aplicación de POO:
 * - Encapsulamiento: todos los atributos son privados.
 * - Abstracción: el objeto modela un usuario real dentro del sistema.
 */
public class Usuario {
    private Integer idUsuario;
    private String nombre;
    private String email;
    private String contrasena;
    private String rol;    // usuario | administrador | voluntario
    private String estado; // activo | bloqueado

    public Usuario() {}
    /**
     * Constructor que permite inicializar un usuario con todos sus atributos.
     */
    public Usuario(Integer idUsuario, String nombre, String email, String contrasena, String rol, String estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
    }
    // Getters y setters (Encapsulamiento)
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
