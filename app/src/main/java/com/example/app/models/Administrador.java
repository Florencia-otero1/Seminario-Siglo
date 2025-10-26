package com.example.app.models;
/**
 * Clase Administrador que hereda de Usuario.
 *
 * Aplicación de POO:
 * - Herencia: reutiliza atributos y métodos de Usuario.
 * - Polimorfismo: un Administrador puede ser tratado como Usuario.
 */
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public void aprobarReporte() {
        System.out.println("✔️ El administrador puede aprobar reportes.");
    }

    public void bloquearUsuario(Usuario u) {
        System.out.println("⛔ El administrador puede bloquear al usuario: " + u.getNombre());
    }
}