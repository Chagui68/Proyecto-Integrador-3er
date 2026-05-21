package com.hospital.sanrafael.service;

import com.hospital.sanrafael.dao.MateriaDAO;
import com.hospital.sanrafael.model.Materia;

import java.util.List;

public class MateriaService {
    private final MateriaDAO materiaDAO;

    public MateriaService() {
        materiaDAO = new MateriaDAO();
    }

    public List<Materia> obtenerTodasMaterias() {
        return materiaDAO.getAll();
    }

    public Materia obtenerMateriaPorCodigo(String codigo) {
        return materiaDAO.getByCodigo(codigo);
    }

    public Materia registrarMateria(Materia materia) {
        if (materiaDAO.getByCodigo(materia.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una materia con ese código");
        }
        materiaDAO.save(materia);
        return materia;
    }

    public Materia actualizarMateria(Materia materia) {
        if (materiaDAO.getByCodigo(materia.getCodigo()) == null) {
            throw new IllegalArgumentException("No existe una materia con ese código");
        }
        materiaDAO.update(materia);
        return materia;
    }

    public void eliminarMateria(String codigo) {
        materiaDAO.delete(codigo);
    }

    public List<Materia> buscarPorSemestre(int semestre) {
        return materiaDAO.getBySemestre(semestre);
    }

    public List<Materia> buscarPorProfesor(String profesor) {
        return materiaDAO.getByProfesor(profesor);
    }
}
