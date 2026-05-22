package com.hospital.sanrafael.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DebugConnection {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  DEBUG: Probando conexi�n PostgreSQL");
        System.out.println("  Hospital San Rafael");
        System.out.println("==========================================\n");
        
        // Configuraci�n exacta
        String url = "jdbc:postgresql://localhost:5432/hospital_san_rafael";
        String username = "postgres";
        String password = "admin";
        String driver = "org.postgresql.Driver";
        
        System.out.println("Intentando conectar con:");
        System.out.println("  URL: " + url);
        System.out.println("  Usuario: " + username);
        System.out.println("  Password: " + password);
        System.out.println();
        
        try {
            // Cargar driver
            System.out.println("1. Cargando driver...");
            Class.forName(driver);
            System.out.println("   ✅ Driver cargado: " + driver);
            
            // Conectar
            System.out.println("\n2. Conectando a la base de datos...");
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("   ✅ ¡Conexi�n EXITOSA!");
            
            // Verificar estado
            System.out.println("\n3. Verificando estado...");
            if (conn != null && !conn.isClosed()) {
                System.out.println("   ✅ Conexi�n activa y v�lida");
            }
            
            // Obtener informaci�n
            System.out.println("\n4. Informaci�n del servidor:");
            System.out.println("   Product Name: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("   Product Version: " + conn.getMetaData().getDatabaseProductVersion());
            System.out.println("   URL: " + conn.getMetaData().getURL());
            System.out.println("   User: " + conn.getMetaData().getUserName());
            
            // Probar consulta
            System.out.println("\n5. Probando consulta SQL...");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM persona");
            if (rs.next()) {
                int count = rs.getInt("total");
                System.out.println("   ✅ Tabla 'persona' existe con " + count + " registros");
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            System.out.println("\n==========================================");
            System.out.println("  �TODO FUNCIONA CORRECTAMENTE!");
            System.out.println("  La aplicaci�n JavaFX deber�a funcionar");
            System.out.println("==========================================\n");
            
        } catch (Exception e) {
            System.out.println("\n❌ ERROR: " + e.getClass().getSimpleName());
            System.out.println("   Mensaje: " + e.getMessage());
            System.out.println("\nPosibles causas:");
            System.out.println("   1. La base de datos no existe en el puerto 5432");
            System.out.println("   2. La contrase�a 'admin' es incorrecta");
            System.out.println("   3. PostgreSQL no est� corriendo");
            System.out.println("\nDetalles del error:");
            e.printStackTrace();
        }
    }
}
