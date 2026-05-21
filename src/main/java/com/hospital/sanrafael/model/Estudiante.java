package com.hospital.sanrafael.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String carrera;
    private int semestre;
    private String turno;
    private List<Materia> materias;
    private List<Horario> horarioSemanal;

    public Estudiante() {
        this.materias = new ArrayList<>();
        this.horarioSemanal = new ArrayList<>();
    }

    public Estudiante(String id, String nombre, String apellido, String email, String telefono,
                      String fechaNacimiento, String genero, String direccion,
                      String carrera, int semestre, String turno) {
        super(id, nombre, apellido, email, telefono, fechaNacimiento, genero, direccion);
        this.carrera = carrera;
        this.semestre = semestre;
        this.turno = turno;
        this.materias = new ArrayList<>();
        this.horarioSemanal = new ArrayList<>();
    }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public List<Materia> getMaterias() { return materias; }
    public void setMaterias(List<Materia> materias) { this.materias = materias; }

    public void agregarMateria(Materia materia) {
        this.materias.add(materia);
    }

    public void eliminarMateria(String codigoMateria) {
        materias.removeIf(m -> m.getCodigo().equals(codigoMateria));
    }

    public List<Horario> getHorarioSemanal() { return horarioSemanal; }
    public void setHorarioSemanal(List<Horario> horarioSemanal) { this.horarioSemanal = horarioSemanal; }

    public void agregarHorario(Horario horario) {
        this.horarioSemanal.add(horario);
    }

    public void generarHorarioDesdeMaterias() {
        this.horarioSemanal.clear();
        for (Materia materia : materias) {
            for (Horario horario : materia.getHorarios()) {
                this.horarioSemanal.add(new Horario(
                    horario.getDia(),
                    horario.getHoraInicio(),
                    horario.getHoraFin(),
                    materia.getNombre(),
                    materia.getProfesor(),
                    horario.getAula()
                ));
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Estudiante: %s %s - Carrera: %s - Semestre: %d", 
                           getNombre(), getApellido(), carrera, semestre);
    }
}
