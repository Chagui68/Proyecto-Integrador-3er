package com.hospital.sanrafael.service;

import com.hospital.sanrafael.dao.DoctorDAO;
import com.hospital.sanrafael.model.Doctor;
import com.hospital.sanrafael.model.Horario;

import java.util.List;

public class DoctorService {
    private final DoctorDAO doctorDAO;

    public DoctorService() {
        doctorDAO = new DoctorDAO();
    }

    public List<Doctor> obtenerTodosDoctores() {
        return doctorDAO.getAll();
    }

    public Doctor obtenerDoctorPorId(String id) {
        return doctorDAO.getById(id);
    }

    public Doctor obtenerDoctorPorNumeroColegiado(String numeroColegiado) {
        return doctorDAO.getByNumeroColegiado(numeroColegiado);
    }

    public Doctor registrarDoctor(Doctor doctor) {
        if (doctorDAO.getById(doctor.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un doctor con ese ID");
        }
        doctorDAO.save(doctor);
        return doctor;
    }

    public Doctor actualizarDoctor(Doctor doctor) {
        if (doctorDAO.getById(doctor.getId()) == null) {
            throw new IllegalArgumentException("No existe un doctor con ese ID");
        }
        doctorDAO.update(doctor);
        return doctor;
    }

    public void eliminarDoctor(String id) {
        doctorDAO.delete(id);
    }

    public List<Doctor> buscarPorEspecialidad(String especialidad) {
        return doctorDAO.getByEspecialidad(especialidad);
    }

    public void asignarEstudianteADoctor(String idDoctor, String idEstudiante) {
        Doctor doctor = doctorDAO.getById(idDoctor);
        if (doctor != null) {
            doctor.agregarEstudianteSupervisado(idEstudiante);
            doctorDAO.update(doctor);
        }
    }

    public void eliminarEstudianteDeDoctor(String idDoctor, String idEstudiante) {
        Doctor doctor = doctorDAO.getById(idDoctor);
        if (doctor != null) {
            doctor.eliminarEstudianteSupervisado(idEstudiante);
            doctorDAO.update(doctor);
        }
    }

    public void agregarHorarioAtencion(Doctor doctor, Horario horario) {
        doctor.agregarHorarioAtencion(horario);
        doctorDAO.update(doctor);
    }

    public void eliminarHorarioAtencion(Doctor doctor, Horario horario) {
        doctor.eliminarHorarioAtencion(horario);
        doctorDAO.update(doctor);
    }

    public List<Horario> obtenerHorarioAtencion(String idDoctor) {
        Doctor doctor = doctorDAO.getById(idDoctor);
        if (doctor != null) {
            return doctor.getHorarioAtencion();
        }
        return List.of();
    }
}
