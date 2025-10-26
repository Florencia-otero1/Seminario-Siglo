package com.example.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

/**
 * Clase responsable de administrar la conexión a la base de datos MySQL.
 * Implementa el patrón Singleton, permitiendo que toda la aplicación utilice
 * una única conexión compartida, evitando múltiples conexiones innecesarias
 * y mejorando la eficiencia.
 */
public class ConexionBD {
    private static Connection conexion; // ✅ ÚNICA conexión en toda la app

    static {
        cargarConexion();
    }

         /**
         * Método encargado de leer el archivo de configuración y establecer
         * la conexión con la base de datos.
         */
    private static void cargarConexion() {
        try {
            Properties props = new Properties();

            // ✅ Cargar config desde resources
            InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("config.properties");

            if (input == null) {
                throw new IllegalStateException("No se encontró config.properties en resources/");
            }

            props.load(input);

            // Se obtienen los valores definidos en el archivo de configuración
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            // Se realiza la conexión a la base de datos
            conexion = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Conexión exitosa a la base de datos.");

        } catch (Exception e) {
            System.out.println("❌ Error al conectar a la BD: " + e.getMessage());
            conexion = null;
        }
    }

    public static Connection getConexion() {
        // Si se perdió la conexión, volver a crearla
        try {
            if (conexion == null || conexion.isClosed()) {
                cargarConexion();
            }
        } catch (SQLException e) {
            cargarConexion();
        }
        return conexion;
    }
}
