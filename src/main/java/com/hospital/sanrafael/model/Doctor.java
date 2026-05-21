package com.hospital.sanrafael.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String especialidad;
    private String numeroColegiado;
    private String areaAsignada;
    private int aniosExperiencia;
    private List<String> estudiantesAsignados;
    private List<Horario> horarioAtencion;

    public Doctor() {
        this.estudiantesAsignados = new ArrayList<>();
        this.horarioAtencion = new ArrayList<>();
    }

    public Doctor(String id, String nombre, String apellido, String email, String telefono,
                  String fechaNacimiento, String genero, String direccion,
                  String especialidad, String numeroColegiado, String areaAsignada, int aniosExperiencia) {
        super(id, nombre, apellido, email, telefono, fechaNacimiento, genero, direccion);
        this.especialidad = especialidad;
        this.numeroColegiado = numeroColegiado;
        this.areaAsignada = areaAsignada;
        this.aniosExperiencia = aniosExperiencia;
        this.estudiantesAsignados = new ArrayList<>();
        this.horarioAtencion = new ArrayList<>();
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getNumeroColegiado() { return numeroColegiado; }
    public void setNumeroColegiado(String numeroColegiado) { this.numeroColegiado = numeroColegiado; }

    public String getAreaAsignada() { return areaAsignada; }
    public void setAreaAsignada(String areaAsignada) { this.areaAsignada = areaAsignada; }

    public int getAniosExperiencia() { return aniosExperiencia; }
    public void setAniosExperiencia(int aniosExperiencia) { this.aniosExperiencia = aniosExperiencia; }

    public List<String> getEstudiantesAsignados() { return estudiantesAsignados; }
    public void setEstudiantesAsignados(List<String> estudiantesAsignados) { 
        this.estudiantesAsignados = estudiantesAsignados; 
    }

    public void agregarEstudianteSupervisado(String idEstudiante) {
        if (!estudiantesAsignados.contains(idEstudiante)) {
            estudiantesAsignados.add(idEstudiante);
        }
    }

    public void eliminarEstudianteSupervisado(String idEstudiante) {
        estudiantesAsignados.remove(idEstudiante);
    }

    public List<Horario> getHorarioAtencion() { return horarioAtencion; }
    public void setHorarioAtencion(List<Horario> horarioAtencion) { this.horarioAtencion = horarioAtencion; }

    public void agregarHorarioAtencion(Horario horario) {
        this.horarioAtencion.add(horario);
    }

    public void eliminarHorarioAtencion(Horario horario) {
        this.horarioAtencion.remove(horario);
    }

    @Override
    public String toString() {
        return String.format("Dr. %s %s - Especialidad: %s - Colegiado: %s", 
                           getNombre(), getApellido(), especialidad, numeroColegiado);
    }
}
