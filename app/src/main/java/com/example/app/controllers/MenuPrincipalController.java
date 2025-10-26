package com.example.app.controllers;

import com.example.app.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * Controlador del menú principal.
 *
 * Aquí se muestra el nombre del usuario logueado y se habilitan o deshabilitan
 * las opciones de menú según su rol (ejemplo: el rol administrador tiene acceso
 * a la gestión de reportes).
 */
public class MenuPrincipalController {

    @FXML private Label lblUsuario;
    @FXML private Button btnGestionReportes; // botón exclusivo para administradores

    // Guarda al usuario actualmente logueado
    private Usuario usuarioActual;

    // Se llama al entrar al menú
    public void setUsuarioActual(Usuario u) {
        this.usuarioActual = u;

        if (lblUsuario != null && u != null) {
            lblUsuario.setText("👤 " + u.getNombre() + "  |  Rol: " + u.getRol());
        }

        // Mostrar botón admin solo si el rol del usuario es "administrador"
        if (btnGestionReportes != null) {
            btnGestionReportes.setVisible(u != null && u.getRol().equalsIgnoreCase("administrador"));
        }
    }

    // ---------------- ACCIONES DE MENÚ ---------------- //

    @FXML
    public void onLogout() {
        cambiarPantalla("/com/example/app/login-view.fxml", "Iniciar Sesión");
    }

    @FXML
    public void onReportar(ActionEvent e) {
        cambiarPantallaConUsuario("/com/example/app/reporte-view.fxml", "Reportar Mascota");
    }

    @FXML
    public void onDonaciones(ActionEvent e) {
        cambiarPantallaConUsuario("/com/example/app/donacion-view.fxml", "Registrar Donación");
    }

    @FXML
    public void onDifusion(ActionEvent e) {
        cambiarPantallaConUsuario("/com/example/app/difusion-view.fxml", "Difusión");
    }

    @FXML
    public void onGestionReportes(ActionEvent e) {
        cambiarPantallaConUsuario("/com/example/app/admin-gestion-reportes.fxml", "Gestión de Reportes");
    }

    // ---------------- MÉTODOS DE CAMBIO DE PANTALLA ---------------- //
    /**
     * Cambia de ventana SIN pasar el usuario.
     */
    private void cambiarPantalla(String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) lblUsuario.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Cambia de ventana y pasa el usuario actual al siguiente controlador.
     *
     * Se realiza mediante reflexión para no acoplar controladores entre sí.
     * → Esto mejora la escalabilidad del sistema.
     */
    private void cambiarPantallaConUsuario(String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Scene scene = new Scene(loader.load());

            // → Pasar usuario si el controlador tiene setUsuarioActual()
            try {
                Object controller = loader.getController();
                controller.getClass().getMethod("setUsuarioActual", Usuario.class)
                        .invoke(controller, usuarioActual);
            } catch (Exception ignored) {}

            Stage stage = (Stage) lblUsuario.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
