package com.example.app.dao;

import com.example.app.database.ConexionBD;
import com.example.app.models.Reporte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import java.sql.*;
/**
 * DAO encargado de administrar las operaciones relacionadas con los reportes
 * de mascotas perdidas o encontradas dentro del sistema.
 *
 * Este DAO demuestra:
 *  - Inserción compuesta (Reporte + Mascota) → Transacción lógica.
 *  - Uso de JOIN para combinar datos entre varias tablas.
 *  - Filtrado dinámico de reportes según el tipo.
 *  - Cambio de estado para control administrativo.
 */
public class ReporteDAO {

    // Cargar tipos de reporte desde la tabla TipoReporte
    public void cargarTipos(ComboBox<String> combo) {
        String sql = "SELECT nombre FROM TipoReporte";
        try (Connection cn = ConexionBD.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                combo.getItems().add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar tipos: " + e.getMessage());
        }
    }

    // Cargar especies desde la tabla Especie
    public void cargarEspecies(ComboBox<String> combo) {
        String sql = "SELECT nombre FROM Especie";
        try (Connection cn = ConexionBD.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                combo.getItems().add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar especies: " + e.getMessage());
        }
    }

    // ===========================================================
    // INSERTAR REPORTE (Mascota + Reporte)
    // ===========================================================
    public boolean insertarReporte(Reporte r) {
        String sqlMascota = "INSERT INTO Mascota (nombre, id_especie, sexo, edad_aproximada, color) VALUES (?, ?, ?, ?, ?)";
        String sqlReporte = "INSERT INTO Reporte (id_mascota, id_usuario, id_tipo_reporte, id_estado_reporte, descripcion, ubicacion, foto_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = ConexionBD.getConexion()) {

            int idTipo = obtenerIdPorNombre(cn, "TipoReporte", r.getTipoReporte());
            int idEspecie = obtenerIdPorNombre(cn, "Especie", r.getEspecie());
            int idEstado = obtenerIdPorNombre(cn, "EstadoReporte", "pendiente");

            // Insertar mascota
            PreparedStatement psMascota = cn.prepareStatement(sqlMascota, Statement.RETURN_GENERATED_KEYS);

            if (r.getNombreMascota() == null || r.getNombreMascota().isBlank()) {
                psMascota.setNull(1, Types.VARCHAR);
            } else {
                psMascota.setString(1, r.getNombreMascota());
            }

            psMascota.setInt(2, idEspecie);
            psMascota.setString(3, r.getSexo());
            if (r.getEdadAprox() == null) psMascota.setNull(4, Types.INTEGER);
            else psMascota.setInt(4, r.getEdadAprox());
            psMascota.setString(5, r.getColor());
            psMascota.executeUpdate();

            ResultSet keysMascota = psMascota.getGeneratedKeys();
            keysMascota.next();
            int idMascota = keysMascota.getInt(1);

            // Insertar reporte
            PreparedStatement psReporte = cn.prepareStatement(sqlReporte);
            psReporte.setInt(1, idMascota);
            psReporte.setInt(2, r.getIdUsuario());
            psReporte.setInt(3, idTipo);
            psReporte.setInt(4, idEstado);
            psReporte.setString(5, r.getDescripcion());
            psReporte.setString(6, r.getUbicacion());
            psReporte.setString(7, r.getFotoUrl());
            psReporte.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar reporte: " + e.getMessage());
            return false;
        }
    }

    // ===========================================================
    // OBTENER REPORTES PENDIENTES (para el Administrador)
    // ===========================================================
    public ObservableList<Reporte> obtenerReportesPendientes() {
        ObservableList<Reporte> lista = FXCollections.observableArrayList();
        String sql = """
                SELECT r.id_reporte, tr.nombre AS tipo, e.nombre AS especie, r.descripcion, r.ubicacion
                FROM Reporte r
                JOIN TipoReporte tr ON r.id_tipo_reporte = tr.id_tipo_reporte
                JOIN Mascota m ON r.id_mascota = m.id_mascota
                JOIN Especie e ON m.id_especie = e.id_especie
                WHERE r.id_estado_reporte = (SELECT id_estado_reporte FROM EstadoReporte WHERE nombre='pendiente');
                """;

        try (Connection cn = ConexionBD.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Reporte(
                        rs.getInt("id_reporte"),
                        rs.getString("tipo"),
                        rs.getString("especie"),
                        rs.getString("descripcion"),
                        rs.getString("ubicacion")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener reportes pendientes: " + e.getMessage());
        }

        return lista;
    }

    // ===========================================================
    // FILTRAR REPORTES POR TIPO
    // ===========================================================
    public ObservableList<Reporte> obtenerReportesPorTipo(String tipo) {
        ObservableList<Reporte> lista = FXCollections.observableArrayList();
        String sql = """
                SELECT r.id_reporte, tr.nombre AS tipo, e.nombre AS especie, r.descripcion, r.ubicacion
                FROM Reporte r
                JOIN TipoReporte tr ON r.id_tipo_reporte = tr.id_tipo_reporte
                JOIN Mascota m ON r.id_mascota = m.id_mascota
                JOIN Especie e ON m.id_especie = e.id_especie
                WHERE tr.nombre = ?;
                """;

        try (Connection cn = ConexionBD.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Reporte(
                        rs.getInt("id_reporte"),
                        rs.getString("tipo"),
                        rs.getString("especie"),
                        rs.getString("descripcion"),
                        rs.getString("ubicacion")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al filtrar reportes: " + e.getMessage());
        }

        return lista;
    }

    // Obtener todos los reportes (sin importar estado)
    public ObservableList<Reporte> obtenerTodosLosReportes() {
        ObservableList<Reporte> lista = FXCollections.observableArrayList();
        String sql = """
            SELECT r.id_reporte, t.nombre AS tipo, e.nombre AS especie, 
                   r.descripcion, r.ubicacion
            FROM Reporte r
            JOIN TipoReporte t ON r.id_tipo_reporte = t.id_tipo_reporte
            JOIN Mascota m ON r.id_mascota = m.id_mascota
            JOIN Especie e ON m.id_especie = e.id_especie
            ORDER BY r.id_reporte DESC
            """;

        try (Connection cn = ConexionBD.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Reporte r = new Reporte();
                r.setIdReporte(rs.getInt("id_reporte"));
                r.setTipoReporte(rs.getString("tipo"));
                r.setEspecie(rs.getString("especie"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setUbicacion(rs.getString("ubicacion"));

                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener reportes: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // ===========================================================
    // CAMBIAR ESTADO (Aprobar / Rechazar)
    // ===========================================================
    public void cambiarEstado(int idReporte, String nuevoEstado) {
        try (Connection cn = ConexionBD.getConexion()) {

            int idEstado = obtenerIdPorNombre(cn, "EstadoReporte", nuevoEstado);

            PreparedStatement ps = cn.prepareStatement(
                    "UPDATE Reporte SET id_estado_reporte = ? WHERE id_reporte = ?"
            );
            ps.setInt(1, idEstado);
            ps.setInt(2, idReporte);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al cambiar estado: " + e.getMessage());
        }
    }

    // Busca PK por nombre
    // Dado el nombre de una entidad paramétrica, recupera su ID. Esto evita consultas duplicadas y mejora mantenibilidad.
    private int obtenerIdPorNombre(Connection cn, String tabla, String nombre) throws SQLException {
        String sql = """
                SELECT id_%s FROM %s WHERE nombre = ?
                """.formatted(
                tabla.equals("TipoReporte") ? "tipo_reporte" :
                        tabla.equals("EstadoReporte") ? "estado_reporte" :
                                "especie", tabla);

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
            throw new SQLException("No encontrado: " + nombre);
        }
    }
}
