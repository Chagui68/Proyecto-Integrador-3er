package com.hospital.sanrafael.model;

import java.io.Serializable;

public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String dia;
    private String horaInicio;
    private String horaFin;
    private String actividad;
    private String responsable;
    private String aula;

    public Horario() {
    }

    public Horario(String dia, String horaInicio, String horaFin, String actividad, 
                   String responsable, String aula) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.actividad = actividad;
        this.responsable = responsable;
        this.aula = aula;
    }

    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }

    @Override
    public String toString() {
        return String.format("%s %s-%s: %s (%s) - %s", 
                           dia, horaInicio, horaFin, actividad, responsable, aula);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Horario horario = (Horario) obj;
        return dia != null ? dia.equals(horario.dia) : horario.dia == null &&
               horaInicio != null ? horaInicio.equals(horario.horaInicio) : horario.horaInicio == null;
    }

    @Override
    public int hashCode() {
        int result = dia != null ? dia.hashCode() : 0;
        result = 31 * result + (horaInicio != null ? horaInicio.hashCode() : 0);
        return result;
    }
}
