package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.model.Estudiante;
import com.hospital.sanrafael.model.Horario;
import com.hospital.sanrafael.service.EstudianteService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class HorarioController {
    private final ViewFactory viewFactory;
    private final EstudianteService estudianteService;
    private TextField idEstudianteField;
    private TextArea horarioArea;

    public HorarioController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.estudianteService = new EstudianteService();
    }

    public Pane getView() {
        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: #ecf0f1;");

        Label titleLabel = new Label("Consulta de Horarios");
        titleLabel.setFont(new Font("Arial Bold", 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");
        titleLabel.setPadding(new Insets(10));

        VBox contentPane = new VBox(15);
        contentPane.setPadding(new Insets(20));
        contentPane.setAlignment(Pos.CENTER);

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        
        Label searchLabel = new Label("ID del Estudiante:");
        searchLabel.setFont(new Font("Arial", 14));
        
        idEstudianteField = new TextField();
        idEstudianteField.setPrefWidth(200);
        idEstudianteField.setPromptText("Ingrese ID del estudiante");
        
        Button btnSearch = new Button("Buscar");
        btnSearch.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;"
        );
        btnSearch.setOnAction(e -> searchHorario());

        idEstudianteField.setOnAction(e -> searchHorario());
        
        searchBox.getChildren().addAll(searchLabel, idEstudianteField, btnSearch);

        Label resultLabel = new Label("Horario Semanal");
        resultLabel.setFont(new Font("Arial Bold", 18));
        resultLabel.setStyle("-fx-text-fill: #2c3e50;");

        horarioArea = new TextArea();
        horarioArea.setPrefHeight(400);
        horarioArea.setPrefWidth(700);
        horarioArea.setEditable(false);
        horarioArea.setFont(new Font("Consolas", 12));
        horarioArea.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

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

        contentPane.getChildren().addAll(searchBox, resultLabel, horarioArea, btnBack);
        mainPane.setCenter(contentPane);

        return mainPane;
    }

    private void searchHorario() {
        String idEstudiante = idEstudianteField.getText().trim();
        if (idEstudiante.isEmpty()) {
            showAlert("Error", "Por favor ingrese el ID del estudiante");
            return;
        }

        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(idEstudiante);
        if (estudiante == null) {
            showAlert("Error", "No se encontró un estudiante con ese ID");
            horarioArea.setText("");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("HORARIO DE: ").append(estudiante.getNombreCompleto()).append("\n");
        sb.append("Carrera: ").append(estudiante.getCarrera()).append(" - ").append(estudiante.getSemestre()).append("° Semestre\n");
        sb.append("Turno: ").append(estudiante.getTurno()).append("\n");
        sb.append("=".repeat(80)).append("\n\n");

        if (estudiante.getMaterias().isEmpty()) {
            sb.append("No tiene materias registradas\n");
        } else {
            sb.append("MATERIAS INSCRITAS:\n");
            sb.append("-".repeat(40)).append("\n");
            for (var materia : estudiante.getMaterias()) {
                sb.append("• ").append(materia.getNombre())
                  .append(" (").append(materia.getCodigo()).append(")\n");
                sb.append("  Profesor: ").append(materia.getProfesor()).append("\n");
                sb.append("  Aula: ").append(materia.getAula()).append("\n");
                sb.append("  Créditos: ").append(materia.getCreditos()).append("\n\n");
            }

            sb.append("\nHORARIO SEMANAL:\n");
            sb.append("-".repeat(40)).append("\n");

            String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
            for (String dia : dias) {
                boolean tieneClase = false;
                sb.append("\n").append(dia.toUpperCase()).append(":\n");
                
                for (Horario h : estudiante.getHorarioSemanal()) {
                    if (h.getDia().equalsIgnoreCase(dia)) {
                        sb.append(String.format("  %s-%s: %s (%s) - %s\n",
                            h.getHoraInicio(), h.getHoraFin(),
                            h.getActividad(), h.getResponsable(), h.getAula()));
                        tieneClase = true;
                    }
                }
                
                if (!tieneClase) {
                    sb.append("  Sin clases\n");
                }
            }
        }

        horarioArea.setText(sb.toString());
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
