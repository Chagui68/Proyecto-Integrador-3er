package com.hospital.sanrafael.service;

import com.hospital.sanrafael.dao.PostgreEstudianteDAO;
import com.hospital.sanrafael.model.Estudiante;
import com.hospital.sanrafael.model.Horario;
import com.hospital.sanrafael.model.Materia;

import java.util.List;

public class EstudianteService {
    private final PostgreEstudianteDAO estudianteDAO;

    public EstudianteService() {
        estudianteDAO = new PostgreEstudianteDAO();
    }

    public List<Estudiante> obtenerTodosEstudiantes() {
        return estudianteDAO.getAll();
    }

    public Estudiante obtenerEstudiantePorId(String id) {
        return estudianteDAO.getById(id);
    }

    public Estudiante registrarEstudiante(Estudiante estudiante) {
        // Esto lanzará una excepción si falla, permitiendo al controlador manejar el error
        estudianteDAO.save(estudiante);
        return estudiante;
    }

    public Estudiante actualizarEstudiante(Estudiante estudiante) {
        estudianteDAO.update(estudiante);
        return estudiante;
    }

    public void eliminarEstudiante(String id) {
        estudianteDAO.delete(id);
    }

    public List<Estudiante> buscarPorCarrera(String carrera) {
        // Implementación pendiente si se requiere filtrado avanzado
        return obtenerTodosEstudiantes(); 
    }

    public List<Estudiante> buscarPorSemestre(int semestre) {
        // Implementación pendiente si se requiere filtrado avanzado
        return obtenerTodosEstudiantes();
    }

    public void agregarMateriaAEstudiante(String idEstudiante, Materia materia) {
        Estudiante estudiante = estudianteDAO.getById(idEstudiante);
        if (estudiante != null) {
            estudiante.agregarMateria(materia);
            estudiante.generarHorarioDesdeMaterias();
            estudianteDAO.update(estudiante);
        }
    }

    public void eliminarMateriaDeEstudiante(String idEstudiante, String codigoMateria) {
        Estudiante estudiante = estudianteDAO.getById(idEstudiante);
        if (estudiante != null) {
            estudiante.eliminarMateria(codigoMateria);
            estudiante.generarHorarioDesdeMaterias();
            estudianteDAO.update(estudiante);
        }
    }

    public List<Horario> obtenerHorarioEstudiante(String idEstudiante) {
        Estudiante estudiante = estudianteDAO.getById(idEstudiante);
        if (estudiante != null) {
            return estudiante.getHorarioSemanal();
        }
        return List.of();
    }
}
