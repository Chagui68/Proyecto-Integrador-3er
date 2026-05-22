package com.hospital.sanrafael.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTester {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  Probando conexi�n a PostgreSQL");
        System.out.println("  Hospital San Rafael");
        System.out.println("==========================================\n");
        
        // Puertos a probar
        int[] ports = {5432, 5433};
        String[] passwords = {"admin", "postgres", "password"};
        
        for (int port : ports) {
            for (String password : passwords) {
                testConnection(port, password);
            }
        }
    }
    
    private static void testConnection(int port, String password) {
        String url = "jdbc:postgresql://localhost:" + port + "/hospital_san_rafael";
        String username = "postgres";
        String driver = "org.postgresql.Driver";
        
        System.out.println("Probando: " + url + " (pass: " + password + ")");
        
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            
            System.out.println("  �CONEXI�N EXITOSA!");
            System.out.println("  Puerto: " + port);
            System.out.println("  Contrase�a: " + password);
            
            // Verificar si existen las tablas
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM persona");
            rs.next();
            int count = rs.getInt(1);
            System.out.println("  Tablas encontradas: " + count + " registros en persona");
            
            rs.close();
            stmt.close();
            conn.close();
            
            System.out.println("\n  ==> ACTUALIZA database.properties CON:");
            System.out.println("      db.url=jdbc:postgresql://localhost:" + port + "/hospital_san_rafael");
            System.out.println("      db.password=" + password);
            System.out.println("\n==========================================\n");
            
        } catch (Exception e) {
            System.out.println("  Fall�: " + e.getMessage());
        }
    }
}
