package com.hospital.sanrafael.dao;

import com.hospital.sanrafael.database.DatabaseConnection;
import com.hospital.sanrafael.model.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreEstudianteDAO {

    public List<Estudiante> getAll() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String query = """
            SELECT p.*, e.carrera, e.semestre, e.turno, e.estado
            FROM persona p
            JOIN estudiante e ON p.id = e.id_persona
            WHERE p.tipo_persona = 'ESTUDIANTE'
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                estudiantes.add(mapResultSetToEstudiante(rs));
            }
        } catch (Exception e) {
            System.err.println("❌ Error al obtener estudiantes: " + e.getMessage());
            e.printStackTrace();
        }
        return estudiantes;
    }

    public Estudiante getById(String id) {
        String query = """
            SELECT p.*, e.carrera, e.semestre, e.turno, e.estado
            FROM persona p
            JOIN estudiante e ON p.id = e.id_persona
            WHERE p.id = ? AND p.tipo_persona = 'ESTUDIANTE'
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToEstudiante(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Estudiante estudiante) {
        String insertPersona = """
            INSERT INTO persona (id, nombre, apellido, email, telefono, fecha_nacimiento, genero, direccion, tipo_persona)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'ESTUDIANTE')
            """;
        
        String insertEstudiante = """
            INSERT INTO estudiante (id_persona, carrera, semestre, turno)
            VALUES (?, ?, ?, ?)
            """;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Iniciar transacción
            
            System.out.println("💾 Intentando guardar estudiante: " + estudiante.getId() + " - " + estudiante.getNombre());
            
            try (PreparedStatement stmt1 = conn.prepareStatement(insertPersona)) {
                stmt1.setString(1, estudiante.getId());
                stmt1.setString(2, estudiante.getNombre());
                stmt1.setString(3, estudiante.getApellido());
                stmt1.setString(4, estudiante.getEmail());
                stmt1.setString(5, estudiante.getTelefono());
                
                // Convertir String a java.sql.Date para fecha_nacimiento
                String fechaStr = estudiante.getFechaNacimiento();
                if (fechaStr != null && !fechaStr.isEmpty()) {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(fechaStr);
                    stmt1.setDate(6, sqlDate);
                } else {
                    stmt1.setDate(6, null);
                }
                
                stmt1.setString(7, estudiante.getGenero());
                stmt1.setString(8, estudiante.getDireccion());
                int rows1 = stmt1.executeUpdate();
                System.out.println("   -> Filas insertadas en persona: " + rows1);
            }
            
            try (PreparedStatement stmt2 = conn.prepareStatement(insertEstudiante)) {
                stmt2.setString(1, estudiante.getId());
                stmt2.setString(2, estudiante.getCarrera());
                stmt2.setInt(3, estudiante.getSemestre());
                stmt2.setString(4, estudiante.getTurno());
                int rows2 = stmt2.executeUpdate();
                System.out.println("   -> Filas insertadas en estudiante: " + rows2);
            }
            
            conn.commit(); // Confirmar transacción
            System.out.println("✅ Estudiante guardado correctamente en PostgreSQL");
            
        } catch (Exception e) {
            System.err.println("❌ Error al guardar estudiante: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar en la base de datos: " + e.getMessage(), e);
        }
    }

    public void update(Estudiante estudiante) {
        String updatePersona = """
            UPDATE persona
            SET nombre = ?, apellido = ?, email = ?, telefono = ?, 
                fecha_nacimiento = ?, genero = ?, direccion = ?
            WHERE id = ?
            """;
        
        String updateEstudiante = """
            UPDATE estudiante
            SET carrera = ?, semestre = ?, turno = ?
            WHERE id_persona = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmt1 = conn.prepareStatement(updatePersona)) {
                stmt1.setString(1, estudiante.getNombre());
                stmt1.setString(2, estudiante.getApellido());
                stmt1.setString(3, estudiante.getEmail());
                stmt1.setString(4, estudiante.getTelefono());
                stmt1.setString(5, estudiante.getFechaNacimiento());
                stmt1.setString(6, estudiante.getGenero());
                stmt1.setString(7, estudiante.getDireccion());
                stmt1.setString(8, estudiante.getId());
                stmt1.executeUpdate();
            }
            
            try (PreparedStatement stmt2 = conn.prepareStatement(updateEstudiante)) {
                stmt2.setString(1, estudiante.getCarrera());
                stmt2.setInt(2, estudiante.getSemestre());
                stmt2.setString(3, estudiante.getTurno());
                stmt2.setString(4, estudiante.getId());
                stmt2.executeUpdate();
            }
            
            conn.commit();
            System.out.println("✅ Estudiante actualizado correctamente");
            
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar estudiante: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar en la base de datos: " + e.getMessage(), e);
        }
    }

    public void delete(String id) {
        String query = "DELETE FROM persona WHERE id = ? AND tipo_persona = 'ESTUDIANTE'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, id);
            int rows = stmt.executeUpdate();
            System.out.println("   -> Filas eliminadas: " + rows);
            System.out.println("✅ Estudiante eliminado correctamente");
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar estudiante: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar de la base de datos: " + e.getMessage(), e);
        }
    }

    private Estudiante mapResultSetToEstudiante(ResultSet rs) throws SQLException {
        Estudiante estudiante = new Estudiante(
            rs.getString("id"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("email"),
            rs.getString("telefono"),
            rs.getString("fecha_nacimiento") != null ? rs.getString("fecha_nacimiento") : "",
            rs.getString("genero"),
            rs.getString("direccion"),
            rs.getString("carrera"),
            rs.getInt("semestre"),
            rs.getString("turno")
        );
        return estudiante;
    }
}
