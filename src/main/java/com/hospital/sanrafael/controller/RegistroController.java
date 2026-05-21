package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.model.Estudiante;
import com.hospital.sanrafael.model.Doctor;
import com.hospital.sanrafael.service.EstudianteService;
import com.hospital.sanrafael.service.DoctorService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RegistroController {
    private final ViewFactory viewFactory;
    private final EstudianteService estudianteService;
    private final DoctorService doctorService;
    private TextField idPersonaField;
    private TextArea registroArea;
    private ComboBox<String> tipoRegistroCombo;

    public RegistroController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.estudianteService = new EstudianteService();
        this.doctorService = new DoctorService();
    }

    public Pane getView() {
        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: #ecf0f1;");

        Label titleLabel = new Label("Registro de Ingreso - Hospital San Rafael");
        titleLabel.setFont(new Font("Arial Bold", 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");
        titleLabel.setPadding(new Insets(10));

        VBox contentPane = new VBox(15);
        contentPane.setPadding(new Insets(20));
        contentPane.setAlignment(Pos.CENTER);

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        
        Label searchLabel = new Label("ID:");
        searchLabel.setFont(new Font("Arial", 14));
        
        idPersonaField = new TextField();
        idPersonaField.setPrefWidth(200);
        idPersonaField.setPromptText("Ingrese ID");
        
        tipoRegistroCombo = new ComboBox<>();
        tipoRegistroCombo.getItems().addAll("Estudiante", "Doctor");
        tipoRegistroCombo.setValue("Estudiante");
        tipoRegistroCombo.setPrefWidth(150);
        
        Button btnSearch = new Button("Registrar Ingreso");
        btnSearch.setStyle(
            "-fx-background-color: #27ae60;" +
            "-fx-text-fill: white;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;"
        );
        btnSearch.setOnAction(e -> registrarIngreso());

        idPersonaField.setOnAction(e -> registrarIngreso());
        
        searchBox.getChildren().addAll(searchLabel, idPersonaField, tipoRegistroCombo, btnSearch);

        Label resultLabel = new Label("Registro del Día: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        resultLabel.setFont(new Font("Arial Bold", 18));
        resultLabel.setStyle("-fx-text-fill: #2c3e50;");

        registroArea = new TextArea();
        registroArea.setPrefHeight(400);
        registroArea.setPrefWidth(700);
        registroArea.setEditable(false);
        registroArea.setFont(new Font("Consolas", 12));
        registroArea.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

        mostrarResumenDiario();

        Button btnBack = new Button("Volver");
        btnBack.setStyle(
            "-fx-background-color: #95a5a6;" +
            "-fx-text-fill: white;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;"
        );
        btnBack.setOnAction(e -> {
            if (mainController != null) {
                mainController.navigateTo("main");
            }
        });

        contentPane.getChildren().addAll(searchBox, resultLabel, registroArea, btnBack);
        mainPane.setCenter(contentPane);

        return mainPane;
    }

    private void registrarIngreso() {
        String id = idPersonaField.getText().trim();
        if (id.isEmpty()) {
            showAlert("Error", "Por favor ingrese un ID");
            return;
        }

        String tipo = tipoRegistroCombo.getValue();
        StringBuilder sb = new StringBuilder();
        sb.append("HORA DE REGISTRO: ").append(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).append("\n");
        sb.append("FECHA: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        sb.append("=".repeat(60)).append("\n\n");

        if (tipo.equals("Estudiante")) {
            Estudiante estudiante = estudianteService.obtenerEstudiantePorId(id);
            if (estudiante == null) {
                showAlert("Error", "No se encontró un estudiante con ese ID");
                return;
            }
            sb.append("ESTUDIANTE:\n");
            sb.append("  Nombre: ").append(estudiante.getNombreCompleto()).append("\n");
            sb.append("  ID: ").append(estudiante.getId()).append("\n");
            sb.append("  Carrera: ").append(estudiante.getCarrera()).append("\n");
            sb.append("  Semestre: ").append(estudiante.getSemestre()).append("\n");
            sb.append("  Turno: ").append(estudiante.getTurno()).append("\n");
            sb.append("  Email: ").append(estudiante.getEmail()).append("\n");
            
            if (!estudiante.getMaterias().isEmpty()) {
                sb.append("\nMATERIAS INSCRITAS:\n");
                for (var materia : estudiante.getMaterias()) {
                    sb.append("  - ").append(materia.getNombre())
                      .append(" (").append(materia.getCodigo()).append(")\n");
                }
            }
            
            if (!estudiante.getHorarioSemanal().isEmpty()) {
                sb.append("\nPRÓXIMA CLASE:\n");
                sb.append("  ").append(estudiante.getHorarioSemanal().get(0));
            }
        } else {
            Doctor doctor = doctorService.obtenerDoctorPorId(id);
            if (doctor == null) {
                showAlert("Error", "No se encontró un doctor con ese ID");
                return;
            }
            sb.append("DOCTOR:\n");
            sb.append("  Nombre: ").append(doctor.getNombreCompleto()).append("\n");
            sb.append("  ID: ").append(doctor.getId()).append("\n");
            sb.append("  Especialidad: ").append(doctor.getEspecialidad()).append("\n");
            sb.append("  N° Colegiado: ").append(doctor.getNumeroColegiado()).append("\n");
            sb.append("  Área Asignada: ").append(doctor.getAreaAsignada()).append("\n");
            sb.append("  Años de Experiencia: ").append(doctor.getAniosExperiencia()).append("\n");
            sb.append("  Email: ").append(doctor.getEmail()).append("\n");
            
            if (!doctor.getEstudiantesAsignados().isEmpty()) {
                sb.append("\nESTUDIANTES ASIGNADOS: ").append(doctor.getEstudiantesAsignados().size()).append("\n");
            }
        }

        registroArea.setText(sb.toString());
        showAlert("Éxito", "Registro completado correctamente");
    }

    private void mostrarResumenDiario() {
        StringBuilder sb = new StringBuilder();
        sb.append("RESUMEN DEL DÍA\n");
        sb.append("=".repeat(60)).append("\n\n");
        
        sb.append("Total Estudiantes Registrados: ")
          .append(estudianteService.obtenerTodosEstudiantes().size()).append("\n");
        sb.append("Total Doctores Registrados: ")
          .append(doctorService.obtenerTodosDoctores().size()).append("\n\n");
        
        sb.append("ESTUDIANTES POR SEMESTRE:\n");
        for (int i = 1; i <= 10; i++) {
            int semestre = i;
            long count = estudianteService.obtenerTodosEstudiantes().stream()
                .filter(e -> e.getSemestre() == semestre)
                .count();
            if (count > 0) {
                sb.append("  ").append(semestre).append("° Semestre: ").append(count).append(" estudiantes\n");
            }
        }
        
        sb.append("\nDOCTORES POR ESPECIALIDAD:\n");
        doctorService.obtenerTodosDoctores().stream()
            .collect(java.util.stream.Collectors.groupingBy(
                Doctor::getEspecialidad, java.util.stream.Collectors.counting()))
            .forEach((especialidad, cantidad) -> 
                sb.append("  ").append(especialidad).append(": ").append(cantidad).append("\n")
            );
        
        registroArea.setText(sb.toString());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private MainController mainController;
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }
}
