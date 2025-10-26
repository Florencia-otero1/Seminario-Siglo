package com.example.app.dao;

import com.example.app.database.ConexionBD;
import com.example.app.models.Donacion;

import java.sql.*;

public class DonacionDAO {

    // DAO encargado de gestionar las operaciones CRUD relacionadas con la entidad Donación.
    public boolean insertarDonacion(Donacion d) {
        String sql = "INSERT INTO Donacion (id_usuario, monto, estado, comprobante_url) VALUES (?, ?, ?, ?)";

        try (Connection cn = ConexionBD.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Carga de parámetros
            ps.setInt(1, d.getIdUsuario());
            ps.setDouble(2, d.getMonto());
            ps.setString(3, d.getEstado() != null ? d.getEstado() : "pendiente");
            ps.setString(4, d.getComprobanteUrl());

            // Ejecución
            ps.executeUpdate();

            // Obtener ID generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idDonacion = rs.getInt(1);
                    d.setIdDonacion(idDonacion);
                    System.out.println("✅ Donación registrada con ID: " + idDonacion);
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar donación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
