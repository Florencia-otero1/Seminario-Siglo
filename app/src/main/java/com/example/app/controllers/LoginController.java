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
 * Controlador de la pantalla de inicio de sesión.
 * Se encarga de validar las credenciales ingresadas y redirigir al menú principal.
 */
public class LoginController {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblEstado;

    // Acceso a la capa de datos (DAO)
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Evento que se ejecuta al hacer clic en el botón "Iniciar Sesión".
     * Valida campos, consulta la BD y aplica polimorfismo según rol.
     */
    @FXML
    public void onLogin(ActionEvent e) {
        lblEstado.setText("");
        String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
        String pass = txtPassword.getText() == null ? "" : txtPassword.getText();

        // Validación simple de campos
        if (email.isEmpty() || pass.isEmpty()) {
            lblEstado.setText("Completá email y contraseña.");
            return;
        }

        try {
            Usuario u = usuarioDAO.login(email, pass);
            if (u == null) {
                lblEstado.setText("Credenciales inválidas o usuario bloqueado.");
                return;
            }

            Usuario usuarioActual;

            /*
              POLIMORFISMO APLICADO
              Si el rol es "administrador", el objeto se convierte en Administrador.
              Esto permite que el sistema diferencie las acciones disponibles
              sin cambiar el código del resto de la aplicación.
             */
            if (u.getRol().equalsIgnoreCase("administrador")) {
                com.example.app.models.Administrador admin = new com.example.app.models.Administrador();
                admin.setIdUsuario(u.getIdUsuario());
                admin.setNombre(u.getNombre());
                admin.setEmail(u.getEmail());
                admin.setRol(u.getRol());
                admin.setEstado(u.getEstado());
                usuarioActual = admin; // POLIMORFISMO
            } else {
                usuarioActual = u; // usuario normal
            }

            // Ir al menú principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/menu-principal.fxml"));
            Scene scene = new Scene(loader.load());

            // Pasar el usuario logueado al siguiente controlador
            MenuPrincipalController controller = loader.getController();
            controller.setUsuarioActual(usuarioActual);

            // Cambiar escena
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menú principal");
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            lblEstado.setText("Error al iniciar sesión: " + ex.getMessage());
        }
    }
    /**
     * Navega a la pantalla de registro de usuarios.
     */
    @FXML
    public void onIrARegistro(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/registro-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Registro");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblEstado.setText("No se pudo abrir la pantalla de registro.");
        }
    }
}
