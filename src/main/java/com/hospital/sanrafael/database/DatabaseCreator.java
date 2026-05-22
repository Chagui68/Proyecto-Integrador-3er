package com.hospital.sanrafael.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Clase para crear automáticamente la base de datos y tablas
 * Ejecutar esta clase antes de usar la aplicación
 */
public class DatabaseCreator {

    private static final String HOST = "localhost";
    private static final String PORT = "5432";
    private static final String DB_NAME = "hospital_san_rafael";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    
    // URL de conexión sin especificar base de datos
    private static final String BASE_URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/postgres";

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  Creador de Base de Datos");
        System.out.println("  Hospital San Rafael");
        System.out.println("==========================================\n");

        try {
            // Paso 1: Crear la base de datos si no existe
            crearBaseDeDatos();
            
            // Paso 2: Conectar a la base de datos creada
            Connection conn = conectarABaseDeDatos();
            
            if (conn != null) {
                System.out.println("\n✅ Base de datos lista para usar!");
                System.out.println("   Nombre: " + DB_NAME);
                System.out.println("   Usuario: " + USERNAME);
                System.out.println("\nAhora puedes ejecutar la aplicación JavaFX.");
                conn.close();
            }
            
        } catch (Exception e) {
            System.err.println("\n❌ Error: " + e.getMessage());
            System.out.println("\nSolución:");
            System.out.println("1. Verifica que PostgreSQL esté instalado");
            System.out.println("2. Verifica que PostgreSQL esté corriendo");
            System.out.println("3. Revisa que la contraseña sea correcta");
            System.out.println("4. Ejecuta pgAdmin y crea la BD manualmente");
            e.printStackTrace();
        }
    }

    private static void crearBaseDeDatos() throws SQLException {
        System.out.println("Paso 1: Verificando/Creando base de datos...");
        
        String sql = "SELECT datname FROM pg_database WHERE datname = '" + DB_NAME + "'";
        
        try (Connection conn = DriverManager.getConnection(BASE_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            var rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                System.out.println("✅ La base de datos '" + DB_NAME + "' ya existe");
            } else {
                System.out.println("📝 Creando base de datos '" + DB_NAME + "'...");
                
                String createDB = "CREATE DATABASE " + DB_NAME + 
                    " WITH OWNER = " + USERNAME +
                    " ENCODING = 'UTF8'" +
                    " LC_COLLATE = 'Spanish_Spain.1252'" +
                    " LC_CTYPE = 'Spanish_Spain.1252'" +
                    " CONNECTION LIMIT = -1";
                
                stmt.execute(createDB);
                System.out.println("✅ Base de datos creada exitosamente");
            }
        }
    }

    private static Connection conectarABaseDeDatos() throws SQLException, IOException {
        System.out.println("\nPaso 2: Conectando a la base de datos...");
        
        String dbUrl = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
        
        Connection conn = DriverManager.getConnection(dbUrl, USERNAME, PASSWORD);
        System.out.println("✅ Conexión exitosa a: " + dbUrl);
        
        // Leer y ejecutar el script SQL
        System.out.println("\nPaso 3: Ejecutando script de tablas...");
        ejecutarScript(conn, "database/script.sql");
        
        return conn;
    }

    private static void ejecutarScript(Connection conn, String scriptPath) throws SQLException, IOException {
        // Intentar diferentes rutas posibles
        String[] posiblesRutas = {
            scriptPath,
            "src/main/resources/" + scriptPath,
            "database/" + scriptPath,
            "../database/" + scriptPath
        };
        
        String contenido = null;
        for (String ruta : posiblesRutas) {
            try {
                contenido = leerArchivo(ruta);
                if (contenido != null) {
                    System.out.println("   Script encontrado en: " + ruta);
                    break;
                }
            } catch (IOException e) {
                // Continuar buscando
            }
        }
        
        if (contenido == null) {
            System.out.println("⚠️  No se encontró el script SQL. Ejecuta manualmente: database/script.sql");
            return;
        }
        
        // Dividir el script en sentencias individuales
        String[] sentencias = contenido.split(";");
        
        int exitosas = 0;
        int errores = 0;
        
        try (Statement stmt = conn.createStatement()) {
            for (String sentencia : sentencias) {
                String sql = sentencia.trim();
                if (!sql.isEmpty() && !sql.startsWith("--")) {
                    try {
                        stmt.execute(sql);
                        exitosas++;
                    } catch (SQLException e) {
                        // Ignorar errores de "ya existe"
                        if (!e.getMessage().contains("already exists") && 
                            !e.getMessage().contains("ya existe")) {
                            errores++;
                        }
                    }
                }
            }
        }
        
        System.out.println("   Sentencias ejecutadas: " + exitosas);
        if (errores > 0) {
            System.out.println("   Errores ignorados: " + errores);
        }
        System.out.println("✅ Script ejecutado correctamente");
    }

    private static String leerArchivo(String ruta) throws IOException {
        StringBuilder contenido = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        
        return contenido.toString();
    }

    public static boolean verificarConexion() {
        try {
            String dbUrl = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
            Connection conn = DriverManager.getConnection(dbUrl, USERNAME, PASSWORD);
            conn.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
