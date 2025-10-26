package com.example.app.controllers;

import com.example.app.database.ConexionBD;
import com.example.app.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;
/**
 * Controlador de la pantalla de difusión.
 *
 * Esta vista muestra los reportes que ya fueron validados o difundidos,
 * permitiendo que los usuarios puedan visualizarlos y compartirlos.
 *
 * Representa la etapa en la que un caso pasa de ser interno a ser publicado
 * para colaboración comunitaria.
 */
public class DifusionController {

    @FXML private TableView<ReporteItem> tablaReportes;
    @FXML private TableColumn<ReporteItem, String> colTipo;
    @FXML private TableColumn<ReporteItem, String> colDescripcion;
    @FXML private TableColumn<ReporteItem, String> colUbicacion;
    @FXML private Label lblEstado;

    private Usuario usuarioActual;

    // Lista observable para mostrar los reportes en la UI
    private ObservableList<ReporteItem> listaReportes = FXCollections.observableArrayList();

    public void setUsuarioActual(Usuario u) {
        this.usuarioActual = u;
    }
    /**
     * Inicializa la vista configurando columnas y cargando reportes.
     * Se ejecuta automáticamente al cargar el FXML.
     */
    @FXML
    public void initialize() {
        colTipo.setCellValueFactory(data -> data.getValue().tipoReporteProperty());
        colDescripcion.setCellValueFactory(data -> data.getValue().descripcionProperty());
        colUbicacion.setCellValueFactory(data -> data.getValue().ubicacionProperty());
        cargarReportes();
    }

    @FXML
    public void onActualizar() {
        cargarReportes();
    }
    /**
     * Carga desde la base de datos los reportes que están validados o difundidos.
     * Se utiliza una consulta con JOIN para obtener información combinada.
     */
    private void cargarReportes() {
        listaReportes.clear();
        String sql = """
            SELECT tr.nombre AS tipo, r.descripcion, r.ubicacion
            FROM Reporte r
            JOIN TipoReporte tr ON r.id_tipo_reporte = tr.id_tipo_reporte
            JOIN EstadoReporte er ON r.id_estado_reporte = er.id_estado_reporte
            WHERE er.nombre IN ('validado', 'difundido')
            ORDER BY r.fecha DESC
            """;

        try (Connection cn = ConexionBD.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                listaReportes.add(new ReporteItem(
                        rs.getString("tipo"),
                        rs.getString("descripcion"),
                        rs.getString("ubicacion")
                ));
            }

            tablaReportes.setItems(listaReportes);
            lblEstado.setText("✅ Reportes cargados correctamente.");

        } catch (SQLException e) {
            lblEstado.setText("❌ Error al cargar reportes: " + e.getMessage());
        }
    }
    /**
     * Regresa a la pantalla del menú principal manteniendo el usuario logueado.
     */
    @FXML
    public void onVolver(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/menu-principal.fxml"));
            Scene scene = new Scene(loader.load());
            MenuPrincipalController controller = loader.getController();
            controller.setUsuarioActual(usuarioActual);

            Stage stage = (Stage) tablaReportes.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menú principal");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblEstado.setText("⚠️ Error al volver al menú.");
        }
    }
    /**
     * Clase interna utilizada para mostrar los datos en la tabla.
     * Esta clase no representa una entidad principal,
     * sino un objeto adaptado para la interfaz gráfica.
     */
    public static class ReporteItem {
        private final javafx.beans.property.SimpleStringProperty tipoReporte;
        private final javafx.beans.property.SimpleStringProperty descripcion;
        private final javafx.beans.property.SimpleStringProperty ubicacion;

        public ReporteItem(String tipo, String desc, String ubi) {
            this.tipoReporte = new javafx.beans.property.SimpleStringProperty(tipo);
            this.descripcion = new javafx.beans.property.SimpleStringProperty(desc);
            this.ubicacion = new javafx.beans.property.SimpleStringProperty(ubi);
        }

        public javafx.beans.property.StringProperty tipoReporteProperty() { return tipoReporte; }
        public javafx.beans.property.StringProperty descripcionProperty() { return descripcion; }
        public javafx.beans.property.StringProperty ubicacionProperty() { return ubicacion; }
    }
}
