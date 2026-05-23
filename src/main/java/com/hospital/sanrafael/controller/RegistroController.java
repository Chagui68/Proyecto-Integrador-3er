package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.model.Doctor;
import com.hospital.sanrafael.model.Estudiante;
import com.hospital.sanrafael.service.DoctorService;
import com.hospital.sanrafael.service.EstudianteService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class RegistroController extends BaseDashboardController {
    private final EstudianteService estudianteService;
    private final DoctorService doctorService;
    private TextField idField;
    private ComboBox<String> tipoCombo;
    private TextArea resultArea;

    public RegistroController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.estudianteService = new EstudianteService();
        this.doctorService = new DoctorService();
    }

    @Override protected String getSidebarColor() { return "#2C3E8F"; }
    @Override protected String getSidebarLogo() { return "📋 MEDjestic"; }
    @Override protected String getSidebarLetter() { return "R"; }
    @Override protected String getModuleName() { return "Registros"; }
    @Override protected String getModuleRole() { return "Módulo de Ingreso"; }
    @Override protected String getTitle() { return "Registro de Ingreso"; }

    @Override
    protected VBox createSidebarMenuItems() {
        VBox menu = new VBox(5);
        Button dashBtn = sidebarBtn("📋 Dashboard", true);
        Button backBtn = sidebarBtn("⬅️ Volver", false);
        backBtn.setOnAction(e -> { if (mainController != null) mainController.navigateTo("main"); });
        menu.getChildren().addAll(dashBtn, backBtn);
        return menu;
    }

    @Override
    protected VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(25));
        content.setStyle("-fx-background-color: #f0f4f8;");

        HBox stats = new HBox(15);
        int totalEst = estudianteService.obtenerTodosEstudiantes().size();
        int totalDoc = doctorService.obtenerTodosDoctores().size();
        stats.getChildren().addAll(
            statCard("ESTUDIANTES", String.valueOf(totalEst), "#2C3E8F"),
            statCard("DOCTORES", String.valueOf(totalDoc), "#27AE60"),
            statCard("TOTAL", String.valueOf(totalEst + totalDoc), "#E67E22"),
            statCard("ESTADO", "Activo", "#E74C3C")
        );

        VBox searchSection = new VBox(10);
        searchSection.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        Label sectionTitle = new Label("Buscar Persona");
        sectionTitle.setFont(Font.font("Arial Bold", 16));
        sectionTitle.setStyle("-fx-text-fill: #2c3e50;");

        HBox searchRow = new HBox(12);
        searchRow.setAlignment(Pos.CENTER_LEFT);

        Label tipoLabel = new Label("Tipo:");
        tipoLabel.setFont(Font.font("Arial", 13));
        tipoLabel.setStyle("-fx-text-fill: #555;");

        tipoCombo = new ComboBox<>();
        tipoCombo.getItems().addAll("Estudiante", "Doctor");
        tipoCombo.setValue("Estudiante");
        tipoCombo.setStyle("-fx-font-size: 13px; -fx-padding: 4;");

        Label idLabel = new Label("ID:");
        idLabel.setFont(Font.font("Arial", 13));
        idLabel.setStyle("-fx-text-fill: #555;");

        idField = new TextField();
        idField.setPrefWidth(200);
        idField.setPromptText("Ingrese ID");
        idField.setStyle("-fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8; -fx-border-color: #ddd; -fx-font-size: 13px;");

        Button searchBtn = actionBtn("🔍 Buscar", "#2C3E8F");
        searchBtn.setOnAction(e -> buscar());
        idField.setOnAction(e -> buscar());

        searchRow.getChildren().addAll(tipoLabel, tipoCombo, idLabel, idField, searchBtn);

        resultArea = new TextArea();
        resultArea.setPrefHeight(250);
        resultArea.setEditable(false);
        resultArea.setFont(Font.font("Consolas", 12));
        resultArea.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ddd; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10;");

        searchSection.getChildren().addAll(sectionTitle, searchRow, resultArea);
        content.getChildren().addAll(stats, searchSection);

        return content;
    }

    private void buscar() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            show("Error", "Ingrese un ID");
            return;
        }

        StringBuilder sb = new StringBuilder();
        if (tipoCombo.getValue().equals("Estudiante")) {
            Estudiante est = estudianteService.obtenerEstudiantePorId(id);
            if (est == null) {
                show("Error", "Estudiante no encontrado");
                resultArea.clear();
                return;
            }
            sb.append("═══════════════════════════════════════\n");
            sb.append("  DATOS DEL ESTUDIANTE\n");
            sb.append("═══════════════════════════════════════\n\n");
            sb.append("  ID: ").append(est.getId()).append("\n");
            sb.append("  Nombre: ").append(est.getNombreCompleto()).append("\n");
            sb.append("  Email: ").append(est.getEmail()).append("\n");
            sb.append("  Teléfono: ").append(est.getTelefono()).append("\n");
            sb.append("  Carrera: ").append(est.getCarrera()).append("\n");
            sb.append("  Semestre: ").append(est.getSemestre()).append("°\n");
            sb.append("  Turno: ").append(est.getTurno()).append("\n");
            sb.append("  Materias inscritas: ").append(est.getMaterias().size()).append("\n");
        } else {
            Doctor doc = doctorService.obtenerDoctorPorId(id);
            if (doc == null) {
                show("Error", "Doctor no encontrado");
                resultArea.clear();
                return;
            }
            sb.append("═══════════════════════════════════════\n");
            sb.append("  DATOS DEL DOCTOR\n");
            sb.append("═══════════════════════════════════════\n\n");
            sb.append("  ID: ").append(doc.getId()).append("\n");
            sb.append("  Nombre: ").append(doc.getNombreCompleto()).append("\n");
            sb.append("  Email: ").append(doc.getEmail()).append("\n");
            sb.append("  Teléfono: ").append(doc.getTelefono()).append("\n");
            sb.append("  Especialidad: ").append(doc.getEspecialidad()).append("\n");
            sb.append("  N° Colegiado: ").append(doc.getNumeroColegiado()).append("\n");
            sb.append("  Área: ").append(doc.getAreaAsignada()).append("\n");
            sb.append("  Años Experiencia: ").append(doc.getAniosExperiencia()).append("\n");
            sb.append("  Estudiantes supervisados: ").append(doc.getEstudiantesAsignados().size()).append("\n");
        }
        sb.append("\n───────────────────────────────────────\n");
        sb.append("  Registro consultado exitosamente\n");
        sb.append("───────────────────────────────────────\n");
        resultArea.setText(sb.toString());
    }
}
