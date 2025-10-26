package com.example.app.controllers;

import com.example.app.dao.ReporteDAO;
import com.example.app.models.Reporte;
import com.example.app.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
/**
 * Controlador de la pantalla de gestión de reportes.
 * Esta pantalla es accesible únicamente para usuarios con rol "administrador".
 *
 * Funcionalidades:
 *  - Listar reportes pendientes.
 *  - Filtrar reportes por tipo.
 *  - Aprobar o rechazar reportes (cambiando su estado).
 *  - Generar texto para difusión (copiado al portapapeles).
 *  - Volver al menú principal.
 */
public class AdminGestionReportesController {

    @FXML private TableView<Reporte> tablaReportes;
    @FXML private TableColumn<Reporte, Integer> colId;
    @FXML private TableColumn<Reporte, String> colTipo;
    @FXML private TableColumn<Reporte, String> colEspecie;
    @FXML private TableColumn<Reporte, String> colDescripcion;
    @FXML private TableColumn<Reporte, String> colUbicacion;
    @FXML private ComboBox<String> cbFiltro;
    @FXML private Label lblEstado;

    private final ReporteDAO reporteDAO = new ReporteDAO();
    private Usuario usuarioActual;

    public void setUsuarioActual(Usuario u) { this.usuarioActual = u; }
    /**
     * Inicializa la tabla, columnas y opciones del filtro.
     * Este método se ejecuta automáticamente al cargar la vista.
     */
    @FXML
    public void initialize() {
        // Configurar columnas
        colId.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        colTipo.setCellValueFactory(c -> c.getValue().tipoReporteProperty());
        colEspecie.setCellValueFactory(c -> c.getValue().especieProperty());
        colDescripcion.setCellValueFactory(c -> c.getValue().descripcionProperty());
        colUbicacion.setCellValueFactory(c -> c.getValue().ubicacionProperty());

        // Cargar reportes pendientes
        tablaReportes.setItems(reporteDAO.obtenerReportesPendientes());

        // Cargar opciones de filtro
        cbFiltro.getItems().addAll("perdida", "encontrada", "callejero");

        cbFiltro.setOnAction(e -> {
            String tipo = cbFiltro.getValue();
            if (tipo != null) {
                tablaReportes.setItems(reporteDAO.obtenerReportesPorTipo(tipo));
            }
        });
    }
    /**
     * Aprueba el reporte seleccionado cambiando su estado a "validado".
     */
    @FXML
    public void onAprobar() {
        Reporte r = tablaReportes.getSelectionModel().getSelectedItem();
        if (r == null) { lblEstado.setText("⚠ Seleccione un reporte."); return; }
        reporteDAO.cambiarEstado(r.getIdReporte(), "validado");
        tablaReportes.setItems(reporteDAO.obtenerReportesPendientes());
        lblEstado.setText("✅ Reporte aprobado.");
    }
    /**
     * Marca el reporte seleccionado como resuelto.
     */
    @FXML
    public void onRechazar() {
        Reporte r = tablaReportes.getSelectionModel().getSelectedItem();
        if (r == null) { lblEstado.setText("⚠ Seleccione un reporte."); return; }
        reporteDAO.cambiarEstado(r.getIdReporte(), "resuelto");
        tablaReportes.setItems(reporteDAO.obtenerReportesPendientes());
        lblEstado.setText("❌ Reporte marcado como resuelto.");
    }
    /**
     * Muestra todos los reportes sin importar su estado.
     */
    @FXML
    public void onMostrarTodos() {
        tablaReportes.setItems(reporteDAO.obtenerTodosLosReportes());
        cbFiltro.setValue(null);
        lblEstado.setText("📋 Mostrando todos los reportes.");
    }
    /**
     * Vuelve a mostrar solo los reportes pendientes.
     */
    @FXML
    public void onMostrarPendientes() {
        tablaReportes.setItems(reporteDAO.obtenerReportesPendientes());
        cbFiltro.setValue(null);
        lblEstado.setText("⏳ Mostrando solo pendientes.");
    }
    /**
     * Genera texto listo para difusión y lo copia al portapapeles.
     */
    @FXML
    public void onGenerarTexto() {
        Reporte r = tablaReportes.getSelectionModel().getSelectedItem();
        if (r == null) { lblEstado.setText("⚠ Seleccione un reporte."); return; }

        String texto = """
                🚨 *Caso de Mascota %s* 🚨
                Tipo: %s
                Ubicación: %s
                
                Descripción:
                %s

                Por favor compartir 🙏🐾
                """.formatted(r.getEspecie(), r.getTipoReporte(), r.getUbicacion(), r.getDescripcion());

        ClipboardContent content = new ClipboardContent();
        content.putString(texto);
        Clipboard.getSystemClipboard().setContent(content);

        lblEstado.setText("📋 Texto copiado al portapapeles.");
    }
    /**
     * Regresa al menú principal manteniendo el usuario logueado.
     */
    @FXML
    public void onVolver(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/menu-principal.fxml"));
            Scene scene = new Scene(loader.load());

            MenuPrincipalController controller = loader.getController();
            controller.setUsuarioActual(usuarioActual);

            Stage stage = (Stage) lblEstado.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menú Principal");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
