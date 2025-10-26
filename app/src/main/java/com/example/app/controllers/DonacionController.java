package com.example.app.controllers;

import com.example.app.dao.DonacionDAO;
import com.example.app.models.Donacion;
import com.example.app.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
/**
 * Controlador de la pantalla de registro de donaciones.
 *
 * Esta pantalla permite al usuario realizar una donación seleccionando método de pago
 * y opcionalmente adjuntando un comprobante en caso de transferencia.
 *
 * Demuestra:
 *  - Validación de datos en la interfaz
 *  - Creación de objetos (POO + encapsulamiento)
 *  - Separación por capas al delegar la persistencia a DonacionDAO
 */
public class DonacionController {

    // Campos comunes
    @FXML private TextField txtMonto;
    @FXML private ComboBox<String> cbMetodo;
    @FXML private Label lblEstado;

    // Sección tarjeta
    @FXML private VBox seccionTarjeta;
    @FXML private TextField txtNombreTarjeta;
    @FXML private TextField txtNumeroTarjeta;
    @FXML private TextField txtVencimiento;
    @FXML private PasswordField txtCVV;

    // Sección transferencia
    @FXML private VBox seccionTransferencia;
    @FXML private TextField txtComprobante;

    private final DonacionDAO donacionDAO = new DonacionDAO();
    private Usuario usuarioActual;

    public void setUsuarioActual(Usuario u) {
        this.usuarioActual = u;
    }
    // Este método se ejecuta automáticamente al abrir la vista
    @FXML
    public void initialize() {
        cbMetodo.getItems().addAll(
                "Tarjeta de crédito/débito",
                "Transferencia bancaria"
        );
    }
    // Mostrar/ocultar campos según método
    @FXML
    public void onMetodoCambio() {
        String metodo = cbMetodo.getValue();
        seccionTarjeta.setVisible("Tarjeta de crédito/débito".equals(metodo));
        seccionTransferencia.setVisible("Transferencia bancaria".equals(metodo));
    }

    // Seleccionar comprobante (transferencia)
    @FXML
    public void onSeleccionarComprobante(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar comprobante");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos PDF o Imagen", "*.pdf", "*.jpg", "*.png")
        );
        File file = chooser.showOpenDialog(txtMonto.getScene().getWindow());
        if (file != null) {
            txtComprobante.setText(file.getAbsolutePath());
        }
    }

    // Guardar donación
    @FXML
    public void onGuardar(ActionEvent e) {
        lblEstado.setText("");

        try {
            if (txtMonto.getText().isEmpty()) {
                lblEstado.setText("⚠️ Ingrese un monto válido.");
                return;
            }

            // Convertir el monto
            double monto = Double.parseDouble(txtMonto.getText());

            // POO — Crear objeto Donacion encapsulando datos
            Donacion d = new Donacion();
            d.setIdUsuario(usuarioActual.getIdUsuario());
            d.setMonto(monto);
            d.setEstado("pendiente");// Estado inicial

            // Si se trata de transferencia, guarda comprobante
            if ("Transferencia bancaria".equals(cbMetodo.getValue())) {
                d.setComprobanteUrl(txtComprobante.getText());
            } else {
                d.setComprobanteUrl(null);
            }

            // Persistencia en la BD mediante DAO
            boolean ok = donacionDAO.insertarDonacion(d);

            if (ok) {
                lblEstado.setStyle("-fx-text-fill: green;");
                lblEstado.setText("✅ Donación registrada correctamente (pendiente de aprobación).");
                limpiarCampos();
            } else {
                lblEstado.setStyle("-fx-text-fill: red;");
                lblEstado.setText("❌ Error al registrar la donación.");
            }

        } catch (NumberFormatException ex) {
            lblEstado.setText("⚠️ El monto debe ser un número válido.");
        } catch (Exception ex) {
            lblEstado.setText("⚠️ Error inesperado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarCampos() {
        txtMonto.clear();
        txtComprobante.clear();
        txtNombreTarjeta.clear();
        txtNumeroTarjeta.clear();
        txtVencimiento.clear();
        txtCVV.clear();
        cbMetodo.setValue(null);
        seccionTarjeta.setVisible(false);
        seccionTransferencia.setVisible(false);
    }

    // Volver al menú principal
    @FXML
    public void onVolver(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/menu-principal.fxml"));
            Scene scene = new Scene(loader.load());
            MenuPrincipalController controller = loader.getController();
            controller.setUsuarioActual(usuarioActual);

            Stage stage = (Stage) txtMonto.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menú principal");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblEstado.setText("⚠️ Error al volver al menú.");
        }
    }
}
