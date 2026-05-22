package com.hospital.sanrafael.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DatabaseConnection {
    private static Connection connection = null;
    private static Properties properties = new Properties();
    private static boolean initialized = false;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        if (initialized) return;
        
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.out.println("Usando configuraci�n por defecto");
        }
        initialized = true;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            String url = properties.getProperty("db.url", "jdbc:postgresql://localhost:5432/hospital_san_rafael");
            String username = properties.getProperty("db.username", "postgres");
            
            // Contrase�as a probar en orden
            String[] passwordsToTry = {"admin", "8253", "postgres", "password"};
            String passwordFromConfig = properties.getProperty("db.password", "admin");
            
            // A�adir la contrase�a de la configuraci�n al inicio de la lista
            passwordsToTry = new String[] {passwordFromConfig, "admin", "8253", "postgres", "password"};
            
            Exception lastException = null;
            
            for (String password : passwordsToTry) {
                try {
                    String driver = properties.getProperty("db.driver", "org.postgresql.Driver");
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, username, password);
                    
                    System.out.println("✅ Conexi�n exitosa a PostgreSQL!");
                    System.out.println("   URL: " + url);
                    System.out.println("   Usuario: " + username);
                    System.out.println("   Contrase�a: " + password);
                    System.out.println("   Base de datos: hospital_san_rafael");
                    
                    return connection;
                } catch (SQLException e) {
                    lastException = e;
                    // Continuar con la siguiente contrase�a
                }
            }
            
            // Si llegamos aqu�, ninguna contrase�a funcion�
            throw new SQLException("Ninguna contrase�a funcion�. �ltimo error: " + 
                (lastException != null ? lastException.getMessage() : "Desconocido"));
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexi�n cerrada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            boolean isConnected = conn != null && !conn.isClosed();
            if (isConnected) {
                System.out.println("✅ PostgreSQL conectado correctamente");
            }
            return isConnected;
        } catch (Exception e) {
            System.err.println("❌ Error de conexi�n: " + e.getMessage());
            System.err.println("   La aplicaci�n usar� almacenamiento local (archivos .dat)");
            return false;
        }
    }
}
