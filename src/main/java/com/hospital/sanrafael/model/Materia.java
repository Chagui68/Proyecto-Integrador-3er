package com.hospital.sanrafael.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Materia implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String codigo;
    private String nombre;
    private String descripcion;
    private int creditos;
    private int semestreRecomendado;
    private String profesor;
    private String aula;
    private List<Horario> horarios;
    private List<String> requisitos;

    public Materia() {
        this.horarios = new ArrayList<>();
        this.requisitos = new ArrayList<>();
    }

    public Materia(String codigo, String nombre, String descripcion, int creditos, 
                   int semestreRecomendado, String profesor, String aula) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.semestreRecomendado = semestreRecomendado;
        this.profesor = profesor;
        this.aula = aula;
        this.horarios = new ArrayList<>();
        this.requisitos = new ArrayList<>();
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    public int getSemestreRecomendado() { return semestreRecomendado; }
    public void setSemestreRecomendado(int semestreRecomendado) { this.semestreRecomendado = semestreRecomendado; }

    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }

    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }

    public List<Horario> getHorarios() { return horarios; }
    public void setHorarios(List<Horario> horarios) { this.horarios = horarios; }

    public void agregarHorario(Horario horario) {
        this.horarios.add(horario);
    }

    public List<String> getRequisitos() { return requisitos; }
    public void setRequisitos(List<String> requisitos) { this.requisitos = requisitos; }

    public void agregarRequisito(String requisito) {
        if (!requisitos.contains(requisito)) {
            requisitos.add(requisito);
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d créditos)", codigo, nombre, creditos);
    }
}
