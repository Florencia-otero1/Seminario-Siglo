package com.example.app.dao;

import com.example.app.models.Usuario;
import com.example.app.database.ConexionBD;
import java.sql.*;
/**
 * DAO (Data Access Object) para la entidad Usuario.
 * Encargado de realizar operaciones de acceso y modificación de datos
 * en la tabla Usuario dentro de la base de datos.
 */
public class UsuarioDAO {
    //Verifica si las credenciales ingresadas corresponden a un usuario válido.Se utiliza en el proceso de inicio de sesión.
    public Usuario login(String email, String contrasena) throws SQLException {
        String sql = "SELECT id_usuario, nombre, email, contrasena, rol, estado " +
                "FROM Usuario WHERE email = ? AND contrasena = ? AND estado = 'activo'";

        try (Connection cn = ConexionBD.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUsuario(rs);// Polimorfismo indirecto: puede retornar Admin o Usuario dependiendo del rol.
                }
                return null;
            }
        }
    }
    // Registra un nuevo usuario en la base de datos.
    public boolean registrar(Usuario u) throws SQLException {
        String sql = "INSERT INTO Usuario (nombre, email, contrasena, rol, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionBD.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getContrasena());
            ps.setString(4, u.getRol() == null ? "usuario" : u.getRol());
            ps.setString(5, u.getEstado() == null ? "activo" : u.getEstado());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                // Se recupera el ID asignado automáticamente
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) u.setIdUsuario(keys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }
    //Convierte un ResultSet (fila de la BD) en un objeto Usuario. Se utiliza para reutilizar el código y respetar la cohesión.
    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("contrasena"),
                rs.getString("rol"),
                rs.getString("estado")
        );
    }
}
