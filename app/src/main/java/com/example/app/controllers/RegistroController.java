package com.example.app.controllers;

import com.example.app.dao.UsuarioDAO;
import com.example.app.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * Controlador de la pantalla de registro de nuevos usuarios.
 *
 * Permite que una persona cree una cuenta ingresando:
 *  - Nombre
 *  - Email
 *  - Contraseña (con confirmación)
 *
 * Este controlador aplica:
 *  - Validaciones en la interfaz
 *  - Creación de objetos (POO)
 *  - Acceso a la capa DAO para persistencia
 */
public class RegistroController {
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPassword2;
    @FXML private Label lblEstado;

    // Acceso a la base de datos mediante el DAO
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    /**
     * Evento que se ejecuta al presionar el botón "Registrar".
     * Valida los datos y crea un objeto Usuario si todo es correcto.
     */
    @FXML
    public void onRegistrar(ActionEvent e) {
        lblEstado.setText("");
        String nombre = safe(txtNombre.getText());
        String email = safe(txtEmail.getText());
        String pass1 = txtPassword.getText();
        String pass2 = txtPassword2.getText();

        // Validaciones básicas de formulario
        if (nombre.isEmpty() || email.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
            lblEstado.setText("Todos los campos son obligatorios.");
            return;
        }
        if (!pass1.equals(pass2)) {
            lblEstado.setText("Las contraseñas no coinciden.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            lblEstado.setText("Email inválido.");
            return;
        }

        try {
            // POO → Se crea un objeto Usuario con sus atributos encapsulados
            Usuario nuevo = new Usuario(null, nombre, email, pass1, "usuario", "activo");
            boolean ok = usuarioDAO.registrar(nuevo);
            if (ok) {
                lblEstado.setStyle("-fx-text-fill: green;");
                lblEstado.setText("Usuario creado. Ahora podés iniciar sesión.");
            } else {
                lblEstado.setText("No se pudo registrar (¿email duplicado?).");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            lblEstado.setText("Error al registrar: " + ex.getMessage());
        }
    }

    /**
     * Regresa a la pantalla de login.
     */
    @FXML
    public void onVolver(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/login-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Iniciar sesión");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblEstado.setText("No se pudo volver al login.");
        }
    }
    /**
     * Convierte valores nulos en cadena vacía y elimina espacios.
     * Mejora la seguridad y estabilidad en validaciones.
     */
    private String safe(String s) {
        return s == null ? "" : s.trim();
    }
}
