package com.example.app.controllers;

import com.example.app.dao.ReporteDAO;
import com.example.app.models.Reporte;
import com.example.app.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
/**
 * Controlador del formulario para crear reportes de mascotas perdidas/encontradas.
 *
 * Esta pantalla permite que un usuario registre un caso cargando:
 *  - Tipo de reporte
 *  - Datos de la mascota (especie, sexo, color, edad)
 *  - Descripción y ubicación
 *  - Foto opcional
 *
 * El reporte se guarda en la base de datos a través del ReporteDAO.
 */
public class ReporteController {

    // Elementos del formulario
    @FXML private ComboBox<String> cbTipoReporte;
    @FXML private ComboBox<String> cbEspecie;
    @FXML private ComboBox<String> cbSexo;
    @FXML private TextField txtNombreMascota; // 🆕 Nuevo campo opcional
    @FXML private TextField txtColor;
    @FXML private TextField txtEdad;
    @FXML private TextArea txtDescripcion;
    @FXML private TextField txtUbicacion;
    @FXML private TextField txtFoto;
    @FXML private Label lblEstado;

    private final ReporteDAO reporteDAO = new ReporteDAO();
    private Usuario usuarioActual;

    // Método llamado desde el menú principal
    public void setUsuarioActual(Usuario u) {
        this.usuarioActual = u;
    }

    @FXML
    public void initialize() {
        // Cargar datos iniciales
        cbSexo.getItems().addAll("macho", "hembra");
        reporteDAO.cargarTipos(cbTipoReporte);
        reporteDAO.cargarEspecies(cbEspecie);
    }

    // Seleccionar imagen desde el explorador
    @FXML
    public void onSeleccionarImagen(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de mascota");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(txtFoto.getScene().getWindow());
        if (file != null) {
            txtFoto.setText(file.getAbsolutePath());
        }
    }

    // Guardar reporte en la base de datos
    @FXML
    public void onGuardar(ActionEvent e) {
        lblEstado.setText("");

        try {
            // Validaciones básicas
            if (cbTipoReporte.getValue() == null || cbEspecie.getValue() == null || cbSexo.getValue() == null ||
                    txtDescripcion.getText().isEmpty() || txtUbicacion.getText().isEmpty()) {
                lblEstado.setText("⚠️ Complete los campos obligatorios.");
                return;
            }

            // Crear objeto Reporte (POO: encapsulación de datos)
            Reporte r = new Reporte();
            r.setTipoReporte(cbTipoReporte.getValue());
            r.setEspecie(cbEspecie.getValue());
            r.setSexo(cbSexo.getValue());
            r.setColor(txtColor.getText());
            r.setEdadAprox(txtEdad.getText().isEmpty() ? null : Integer.parseInt(txtEdad.getText()));
            r.setDescripcion(txtDescripcion.getText());
            r.setUbicacion(txtUbicacion.getText());
            r.setFotoUrl(txtFoto.getText());
            r.setIdUsuario(usuarioActual.getIdUsuario());

            // Nombre de la mascota (opcional)
            r.setNombreMascota(txtNombreMascota.getText().isEmpty() ? null : txtNombreMascota.getText());

            // Insertar en base de datos
            boolean ok = reporteDAO.insertarReporte(r);

            if (ok) {
                lblEstado.setStyle("-fx-text-fill: green;");
                lblEstado.setText("✅ Reporte guardado correctamente.");
                limpiarCampos();
            } else {
                lblEstado.setStyle("-fx-text-fill: red;");
                lblEstado.setText("❌ No se pudo guardar el reporte.");
            }

        } catch (NumberFormatException ex) {
            lblEstado.setStyle("-fx-text-fill: red;");
            lblEstado.setText("⚠️ La edad debe ser un número.");
        } catch (Exception ex) {
            ex.printStackTrace();
            lblEstado.setStyle("-fx-text-fill: red;");
            lblEstado.setText("⚠️ Error: " + ex.getMessage());
        }
    }

    // Limpiar campos luego de guardar
    private void limpiarCampos() {
        cbTipoReporte.setValue(null);
        cbEspecie.setValue(null);
        cbSexo.setValue(null);
        txtNombreMascota.clear(); // 🆕 limpiar campo nuevo
        txtColor.clear();
        txtEdad.clear();
        txtDescripcion.clear();
        txtUbicacion.clear();
        txtFoto.clear();
    }

    // Volver al menú principal
    @FXML
    public void onVolver(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/menu-principal.fxml"));
            Scene scene = new Scene(loader.load());
            MenuPrincipalController controller = loader.getController();
            controller.setUsuarioActual(usuarioActual);

            Stage stage = (Stage) cbTipoReporte.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menú principal");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblEstado.setText("⚠️ Error al volver al menú.");
        }
    }
}
