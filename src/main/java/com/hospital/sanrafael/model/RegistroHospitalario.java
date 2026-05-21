package com.hospital.sanrafael.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroHospitalario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idRegistro;
    private String idPersona;
    private String nombrePersona;
    private String tipo; 
    private LocalDate fecha;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private String area;
    private String actividad;
    private String observaciones;

    public RegistroHospitalario() {
    }

    public RegistroHospitalario(String idRegistro, String idPersona, String nombrePersona, 
                                String tipo, LocalDate fecha, LocalTime horaEntrada, 
                                LocalTime horaSalida, String area, String actividad, 
                                String observaciones) {
        this.idRegistro = idRegistro;
        this.idPersona = idPersona;
        this.nombrePersona = nombrePersona;
        this.tipo = tipo;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.area = area;
        this.actividad = actividad;
        this.observaciones = observaciones;
    }

    public String getIdRegistro() { return idRegistro; }
    public void setIdRegistro(String idRegistro) { this.idRegistro = idRegistro; }

    public String getIdPersona() { return idPersona; }
    public void setIdPersona(String idPersona) { this.idPersona = idPersona; }

    public String getNombrePersona() { return nombrePersona; }
    public void setNombrePersona(String nombrePersona) { this.nombrePersona = nombrePersona; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(LocalTime horaEntrada) { this.horaEntrada = horaEntrada; }

    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return String.format("%s - %s (%s) - %s %s", 
                           fecha, nombrePersona, tipo, actividad, area != null ? "en " + area : "");
    }
}
