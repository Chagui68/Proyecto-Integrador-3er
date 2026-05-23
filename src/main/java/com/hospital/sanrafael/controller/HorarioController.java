package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.model.Doctor;
import com.hospital.sanrafael.model.Estudiante;
import com.hospital.sanrafael.model.Horario;
import com.hospital.sanrafael.service.DoctorService;
import com.hospital.sanrafael.service.EstudianteService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class HorarioController extends BaseDashboardController {
    private final EstudianteService estudianteService;
    private final DoctorService doctorService;
    private TableView<Horario> tableView;
    private ComboBox<String> tipoCombo;
    private TextField idField;
    private TextField diaField, horaInicioField, horaFinField;
    private TextField actividadField, responsableField, aulaField;
    private Label personInfoLabel;
    private String currentPersonId;
    private boolean isDoctorMode;

    public HorarioController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.estudianteService = new EstudianteService();
        this.doctorService = new DoctorService();
    }

    @Override protected String getSidebarColor() { return "#E74C3C"; }
    @Override protected String getSidebarLogo() { return "🕐 MEDjestic"; }
    @Override protected String getSidebarLetter() { return "H"; }
    @Override protected String getModuleName() { return "Horarios"; }
    @Override protected String getModuleRole() { return "Gestión de Horarios"; }
    @Override protected String getTitle() { return "Gestión de Horarios"; }

    @Override
    protected VBox createSidebarMenuItems() {
        VBox menu = new VBox(5);
        Button listBtn = sidebarBtn("📋 Horarios", true);
        Button backBtn = sidebarBtn("⬅️ Volver", false);
        backBtn.setOnAction(e -> { if (mainController != null) mainController.navigateTo("main"); });
        menu.getChildren().addAll(listBtn, backBtn);
        return menu;
    }

    @Override
    protected VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(25));
        content.setStyle("-fx-background-color: #f0f4f8;");

        HBox searchRow = new HBox(12);
        searchRow.setAlignment(Pos.CENTER_LEFT);
        searchRow.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        Label tipoLabel = new Label("Tipo:");
        tipoLabel.setFont(Font.font("Arial", 13));
        tipoLabel.setStyle("-fx-text-fill: #555;");

        tipoCombo = new ComboBox<>();
        tipoCombo.getItems().addAll("Estudiante", "Doctor");
        tipoCombo.setValue("Estudiante");
        tipoCombo.setStyle("-fx-font-size: 13px; -fx-padding: 4;");
        isDoctorMode = false;

        Label idLabel = new Label("ID:");
        idLabel.setFont(Font.font("Arial", 13));
        idLabel.setStyle("-fx-text-fill: #555;");

        idField = new TextField();
        idField.setPrefWidth(180);
        idField.setPromptText("Ingrese ID");
        idField.setStyle("-fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8; -fx-border-color: #ddd; -fx-font-size: 13px;");

        Button searchBtn = actionBtn("🔍 Buscar", "#E74C3C");
        searchBtn.setOnAction(e -> buscarPersona());
        idField.setOnAction(e -> buscarPersona());

        personInfoLabel = new Label("Seleccione un tipo e ID para consultar horarios");
        personInfoLabel.setFont(Font.font("Arial", 13));
        personInfoLabel.setStyle("-fx-text-fill: #999;");

        searchRow.getChildren().addAll(tipoLabel, tipoCombo, idLabel, idField, searchBtn, personInfoLabel);

        VBox tableSection = new VBox(10);
        tableSection.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        Label sectionTitle = new Label("Horario Semanal");
        sectionTitle.setFont(Font.font("Arial Bold", 16));
        sectionTitle.setStyle("-fx-text-fill: #2c3e50;");

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(150);
        createTableColumns();

        VBox formSection = new VBox(10);
        formSection.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10; -fx-padding: 15;");

        Label formTitle = new Label("Agregar Horario");
        formTitle.setFont(Font.font("Arial Bold", 14));
        formTitle.setStyle("-fx-text-fill: #2c3e50;");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);

        diaField = createField();
        horaInicioField = createField();
        horaFinField = createField();
        actividadField = createField();
        responsableField = createField();
        aulaField = createField();

        int r = 0;
        grid.add(fieldLabel("Día:"), 0, r); grid.add(diaField, 1, r++);
        grid.add(fieldLabel("Hora Inicio:"), 2, 0); grid.add(horaInicioField, 3, 0);
        grid.add(fieldLabel("Hora Fin:"), 0, r); grid.add(horaFinField, 1, r);
        grid.add(fieldLabel("Actividad:"), 2, r); grid.add(actividadField, 3, r++);
        grid.add(fieldLabel("Responsable:"), 0, r); grid.add(responsableField, 1, r);
        grid.add(fieldLabel("Aula:"), 2, r); grid.add(aulaField, 3, r++);

        HBox buttons = new HBox(12);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10, 0, 0, 0));

        Button agregar = actionBtn("➕ Agregar", "#27AE60");
        Button eliminar = actionBtn("🗑️ Eliminar", "#E74C3C");
        Button limpiar = actionBtn("🔄 Limpiar", "#95A5A6");

        agregar.setOnAction(e -> agregarHorario());
        eliminar.setOnAction(e -> eliminarHorario());
        limpiar.setOnAction(e -> limpiarFormulario());

        buttons.getChildren().addAll(agregar, eliminar, limpiar);
        formSection.getChildren().addAll(formTitle, grid, buttons);
        tableSection.getChildren().addAll(sectionTitle, tableView, formSection);
        content.getChildren().addAll(searchRow, tableSection);

        return content;
    }

    private void createTableColumns() {
        TableColumn<Horario, String> c1 = new TableColumn<>("Día");
        c1.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getDia()));
        TableColumn<Horario, String> c2 = new TableColumn<>("Inicio");
        c2.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getHoraInicio()));
        TableColumn<Horario, String> c3 = new TableColumn<>("Fin");
        c3.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getHoraFin()));
        TableColumn<Horario, String> c4 = new TableColumn<>("Actividad");
        c4.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getActividad()));
        TableColumn<Horario, String> c5 = new TableColumn<>("Responsable");
        c5.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getResponsable()));
        TableColumn<Horario, String> c6 = new TableColumn<>("Aula");
        c6.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getAula()));
        tableView.getColumns().addAll(c1, c2, c3, c4, c5, c6);
    }

    private void buscarPersona() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            show("Error", "Ingrese un ID");
            return;
        }

        isDoctorMode = tipoCombo.getValue().equals("Doctor");
        currentPersonId = id;

        if (isDoctorMode) {
            Doctor doc = doctorService.obtenerDoctorPorId(id);
            if (doc == null) {
                show("Error", "No se encontró Doctor con ID: " + id);
                tableView.setItems(FXCollections.observableArrayList());
                personInfoLabel.setText("Doctor no encontrado");
                return;
            }
            personInfoLabel.setText("Dr. " + doc.getNombreCompleto() + " — " + doc.getEspecialidad());
            tableView.setItems(FXCollections.observableArrayList(doc.getHorarioAtencion()));
        } else {
            Estudiante est = estudianteService.obtenerEstudiantePorId(id);
            if (est == null) {
                show("Error", "No se encontró Estudiante con ID: " + id);
                tableView.setItems(FXCollections.observableArrayList());
                personInfoLabel.setText("Estudiante no encontrado");
                return;
            }
            personInfoLabel.setText(est.getNombreCompleto() + " — " + est.getCarrera() + " (" + est.getSemestre() + "° Semestre)");
            tableView.setItems(FXCollections.observableArrayList(est.getHorarioSemanal()));
        }
    }

    private void agregarHorario() {
        if (currentPersonId == null || currentPersonId.isEmpty()) {
            show("Error", "Primero busque una persona");
            return;
        }

        try {
            Horario h = new Horario(
                diaField.getText(), horaInicioField.getText(), horaFinField.getText(),
                actividadField.getText(), responsableField.getText(), aulaField.getText()
            );

            if (isDoctorMode) {
                Doctor doc = doctorService.obtenerDoctorPorId(currentPersonId);
                if (doc != null) {
                    doctorService.agregarHorarioAtencion(doc, h);
                    tableView.setItems(FXCollections.observableArrayList(doc.getHorarioAtencion()));
                }
            } else {
                Estudiante est = estudianteService.obtenerEstudiantePorId(currentPersonId);
                if (est != null) {
                    est.agregarHorario(h);
                    estudianteService.actualizarEstudiante(est);
                    tableView.setItems(FXCollections.observableArrayList(est.getHorarioSemanal()));
                }
            }
            limpiarFormulario();
            show("Éxito", "Horario agregado");
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private void eliminarHorario() {
        if (currentPersonId == null || currentPersonId.isEmpty()) {
            show("Error", "Primero busque una persona");
            return;
        }

        Horario selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            show("Error", "Seleccione un horario de la tabla");
            return;
        }

        try {
            if (isDoctorMode) {
                Doctor doc = doctorService.obtenerDoctorPorId(currentPersonId);
                if (doc != null) {
                    doctorService.eliminarHorarioAtencion(doc, selected);
                    tableView.setItems(FXCollections.observableArrayList(doc.getHorarioAtencion()));
                }
            } else {
                Estudiante est = estudianteService.obtenerEstudiantePorId(currentPersonId);
                if (est != null) {
                    est.getHorarioSemanal().remove(selected);
                    estudianteService.actualizarEstudiante(est);
                    tableView.setItems(FXCollections.observableArrayList(est.getHorarioSemanal()));
                }
            }
            show("Éxito", "Horario eliminado");
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        diaField.clear(); horaInicioField.clear(); horaFinField.clear();
        actividadField.clear(); responsableField.clear(); aulaField.clear();
    }
}
