package com.hospital.sanrafael.service;

import com.hospital.sanrafael.model.Estudiante;
import com.hospital.sanrafael.model.Doctor;
import com.hospital.sanrafael.model.Materia;
import com.hospital.sanrafael.model.Horario;

public class DatosInicialesService {
    
    private final EstudianteService estudianteService;
    private final DoctorService doctorService;
    private final MateriaService materiaService;

    public DatosInicialesService() {
        estudianteService = new EstudianteService();
        doctorService = new DoctorService();
        materiaService = new MateriaService();
    }

    public void cargarDatosEjemplo() {
        cargarDoctoresEjemplo();
        
        cargarMateriasEjemplo();
        
        cargarEstudiantesEjemplo();
    }

    private void cargarDoctoresEjemplo() {
        if (doctorService.obtenerTodosDoctores().isEmpty()) {
            Doctor doctor1 = new Doctor(
                "D001", "Carlos", "Mendoza", "carlos.mendoza@hospital.com", "555-0101",
                "1985-03-15", "M", "Av. Principal 123",
                "Medicina Interna", "COL-12345", "Pabellón A", 10
            );
            
            Doctor doctor2 = new Doctor(
                "D002", "Ana", "Rodríguez", "ana.rodriguez@hospital.com", "555-0102",
                "1990-07-22", "F", "Calle 45 #678",
                "Pediatría", "COL-67890", "Pabellón B", 7
            );
            
            Doctor doctor3 = new Doctor(
                "D003", "Luis", "García", "luis.garcia@hospital.com", "555-0103",
                "1982-11-30", "M", "Carrera 15 #90",
                "Cirugía General", "COL-11111", "Quirófano", 12
            );
            
            Doctor doctor4 = new Doctor(
                "D004", "María", "López", "maria.lopez@hospital.com", "555-0104",
                "1988-05-18", "F", "Transversal 20 #45",
                "Ginecología", "COL-22222", "Pabellón C", 8
            );
            
            Doctor doctor5 = new Doctor(
                "D005", "Roberto", "Silva", "roberto.silva@hospital.com", "555-0105",
                "1979-09-25", "M", "Calle 100 #15",
                "Cardiología", "COL-33333", "Cardiología", 15
            );

            try {
                doctorService.registrarDoctor(doctor1);
                doctorService.registrarDoctor(doctor2);
                doctorService.registrarDoctor(doctor3);
                doctorService.registrarDoctor(doctor4);
                doctorService.registrarDoctor(doctor5);
                
                doctor1.agregarHorarioAtencion(new Horario("Lunes", "08:00", "12:00", "Consulta", "Dr. Carlos Mendoza", "Pabellón A"));
                doctor1.agregarHorarioAtencion(new Horario("Miércoles", "08:00", "12:00", "Consulta", "Dr. Carlos Mendoza", "Pabellón A"));
                doctor1.agregarHorarioAtencion(new Horario("Viernes", "08:00", "12:00", "Consulta", "Dr. Carlos Mendoza", "Pabellón A"));
                
                doctorService.actualizarDoctor(doctor1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cargarMateriasEjemplo() {
        if (materiaService.obtenerTodasMaterias().isEmpty()) {
            Materia materia1 = new Materia("MED101", "Anatomía Humana I", "Estudio de la anatomía del cuerpo humano", 4, 1, "Dr. Juan Pérez", "Aula 101");
            Materia materia2 = new Materia("MED102", "Fisiología", "Principios de fisiología humana", 4, 1, "Dra. Laura Gómez", "Aula 102");
            Materia materia3 = new Materia("MED201", "Patología General", "Introducción a la patología", 5, 2, "Dr. Pedro Sánchez", "Aula 201");
            Materia materia4 = new Materia("MED202", "Farmacología I", "Fundamentos de farmacología", 4, 2, "Dra. Carmen Díaz", "Aula 202");
            Materia materia5 = new Materia("MED301", "Medicina Interna I", "Principios de medicina interna", 6, 3, "Dr. Carlos Mendoza", "Aula 301");
            Materia materia6 = new Materia("MED302", "Pediatría I", "Fundamentos de pediatría", 5, 3, "Dra. Ana Rodríguez", "Aula 302");
            Materia materia7 = new Materia("MED401", "Cirugía General I", "Principios de cirugía", 6, 4, "Dr. Luis García", "Quirófano 1");
            Materia materia8 = new Materia("MED402", "Ginecología I", "Fundamentos de ginecología", 5, 4, "Dra. María López", "Aula 402");
            Materia materia9 = new Materia("MED501", "Cardiología Avanzada", "Cardiología clínica", 6, 5, "Dr. Roberto Silva", "Cardiología");
            Materia materia10 = new Materia("MED502", "Prácticas Hospitalarias", "Prácticas en Hospital San Rafael", 8, 5, "Varios", "Hospital San Rafael");

            try {
                materiaService.registrarMateria(materia1);
                materiaService.registrarMateria(materia2);
                materiaService.registrarMateria(materia3);
                materiaService.registrarMateria(materia4);
                materiaService.registrarMateria(materia5);
                materiaService.registrarMateria(materia6);
                materiaService.registrarMateria(materia7);
                materiaService.registrarMateria(materia8);
                materiaService.registrarMateria(materia9);
                materiaService.registrarMateria(materia10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cargarEstudiantesEjemplo() {
        if (estudianteService.obtenerTodosEstudiantes().isEmpty()) {
            Estudiante estudiante1 = new Estudiante(
                "E001", "José", "Martínez", "jose.martinez@estudiante.com", "555-1001",
                "2000-01-15", "M", "Calle 10 #20-30",
                "Medicina General", 1, "Mañana"
            );
            
            Estudiante estudiante2 = new Estudiante(
                "E002", "Sofía", "Hernández", "sofia.hernandez@estudiante.com", "555-1002",
                "2001-03-22", "F", "Carrera 5 #15-40",
                "Medicina General", 2, "Mañana"
            );
            
            Estudiante estudiante3 = new Estudiante(
                "E003", "Diego", "Torres", "diego.torres@estudiante.com", "555-1003",
                "1999-07-10", "M", "Av. 68 #90-10",
                "Enfermería", 3, "Tarde"
            );
            
            Estudiante estudiante4 = new Estudiante(
                "E004", "Valentina", "Ramírez", "valentina.ramirez@estudiante.com", "555-1004",
                "2000-11-05", "F", "Calle 72 #15-30",
                "Medicina General", 4, "Mañana"
            );
            
            Estudiante estudiante5 = new Estudiante(
                "E005", "Andrés", "Vargas", "andres.vargas@estudiante.com", "555-1005",
                "1998-09-18", "M", "Transversal 15 #80-20",
                "Enfermería", 5, "Tarde"
            );

            try {
                estudianteService.registrarEstudiante(estudiante1);
                estudianteService.registrarEstudiante(estudiante2);
                estudianteService.registrarEstudiante(estudiante3);
                estudianteService.registrarEstudiante(estudiante4);
                estudianteService.registrarEstudiante(estudiante5);
                
                Materia materia1 = materiaService.obtenerMateriaPorCodigo("MED101");
                Materia materia2 = materiaService.obtenerMateriaPorCodigo("MED102");
                
                if (materia1 != null && materia2 != null) {
                    estudiante1.agregarMateria(materia1);
                    estudiante1.agregarMateria(materia2);
                    estudiante1.generarHorarioDesdeMaterias();
                    estudianteService.actualizarEstudiante(estudiante1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
