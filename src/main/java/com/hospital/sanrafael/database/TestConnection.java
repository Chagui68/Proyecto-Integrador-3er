package com.hospital.sanrafael.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("Probando Conexión PostgreSQL");
        System.out.println("=================================\n");

        try {
            // Probar conexión
            Connection conn = DatabaseConnection.getConnection();
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ ¡Conexión Exitosa!");
                
                // Obtener información de la base de datos
                System.out.println("\nInformación de la conexión:");
                System.out.println("  URL: " + conn.getMetaData().getURL());
                System.out.println("  Usuario: " + conn.getMetaData().getUserName());
                System.out.println("  Producto: " + conn.getMetaData().getDatabaseProductName() + 
                                   " " + conn.getMetaData().getDatabaseProductVersion());
                
                // Contar registros
                System.out.println("\nRegistros en la base de datos:");
                Statement stmt = conn.createStatement();
                
                ResultSet rs = stmt.executeQuery("""
                    SELECT 
                        (SELECT COUNT(*) FROM persona WHERE tipo_persona = 'ESTUDIANTE') AS estudiantes,
                        (SELECT COUNT(*) FROM persona WHERE tipo_persona = 'DOCTOR') AS doctores,
                        (SELECT COUNT(*) FROM materia) AS materias,
                        (SELECT COUNT(*) FROM horario) AS horarios
                    """);
                
                if (rs.next()) {
                    System.out.println("  Estudiantes: " + rs.getInt("estudiantes"));
                    System.out.println("  Doctores: " + rs.getInt("doctores"));
                    System.out.println("  Materias: " + rs.getInt("materias"));
                    System.out.println("  Horarios: " + rs.getInt("horarios"));
                }
                
                // Listar primeros 5 estudiantes
                System.out.println("\nPrimeros 5 estudiantes:");
                ResultSet rs2 = stmt.executeQuery("""
                    SELECT p.nombre, p.apellido, e.carrera, e.semestre
                    FROM persona p
                    JOIN estudiante e ON p.id = e.id_persona
                    WHERE p.tipo_persona = 'ESTUDIANTE'
                    LIMIT 5
                    """);
                
                while (rs2.next()) {
                    System.out.println("  - " + rs2.getString("nombre") + " " + 
                                     rs2.getString("apellido") + 
                                     " (" + rs2.getString("carrera") + 
                                     " - " + rs2.getInt("semestre") + "° semestre)");
                }
                
                rs2.close();
                rs.close();
                stmt.close();
                
                System.out.println("\n=================================");
                System.out.println("✅ ¡Todo funciona correctamente!");
                System.out.println("=================================");
            } else {
                System.out.println("❌ No se pudo establecer la conexión");
            }
            
            DatabaseConnection.closeConnection();
            
        } catch (Exception e) {
            System.out.println("\n❌ Error de conexión:");
            System.out.println("  " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.out.println("\nPosibles soluciones:");
            System.out.println("  1. Verifica que PostgreSQL esté instalado");
            System.out.println("  2. Verifica que la base de datos 'hospital_san_rafael' exista");
            System.out.println("  3. Revisa el archivo database.properties");
            System.out.println("  4. Ejecuta el script SQL en database/script.sql");
            e.printStackTrace();
        }
    }
}
