package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.model.Estudiante;
import com.hospital.sanrafael.service.EstudianteService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class EstudianteController extends BaseDashboardController {
    private final EstudianteService estudianteService;
    private TableView<Estudiante> tableView;
    private TextField idField, nombreField, apellidoField, emailField, telefonoField;
    private TextField fechaNacimientoField, generoField, direccionField;
    private TextField carreraField, semestreField, turnoField;

    public EstudianteController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.estudianteService = new EstudianteService();
    }

    @Override protected String getSidebarColor() { return "#2C3E8F"; }
    @Override protected String getSidebarLogo() { return "🏥 MEDjestic"; }
    @Override protected String getSidebarLetter() { return "E"; }
    @Override protected String getModuleName() { return "Estudiantes"; }
    @Override protected String getModuleRole() { return "Módulo Académico"; }
    @Override protected String getTitle() { return "Gestión de Estudiantes"; }

    @Override
    protected VBox createSidebarMenuItems() {
        VBox menu = new VBox(5);
        Button listBtn = sidebarBtn("📋 Listado", true);
        Button newBtn = sidebarBtn("➕ Nuevo", false);
        listBtn.setOnAction(e -> {});
        newBtn.setOnAction(e -> clearForm());
        Button backBtn = sidebarBtn("⬅️ Volver", false);
        backBtn.setOnAction(e -> { if (mainController != null) mainController.navigateTo("main"); });
        menu.getChildren().addAll(listBtn, newBtn, backBtn);
        return menu;
    }

    @Override
    protected VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(25));
        content.setStyle("-fx-background-color: #f0f4f8;");

        HBox stats = new HBox(15);
        int total = estudianteService.obtenerTodosEstudiantes().size();
        stats.getChildren().addAll(
            statCard("TOTAL", String.valueOf(total), "#2C3E8F"),
            statCard("SEMESTRES", "10", "#27AE60"),
            statCard("CARRERAS", "5", "#E67E22"),
            statCard("TURNO", "Mañana/Tarde", "#E74C3C")
        );

        VBox tableSection = new VBox(10);
        tableSection.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        Label sectionTitle = new Label("Lista de Estudiantes");
        sectionTitle.setFont(Font.font("Arial Bold", 16));
        sectionTitle.setStyle("-fx-text-fill: #2c3e50;");

        tableView = new TableView<>();
        tableView.setItems(getEstudiantesData());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(180);
        createColumns();

        VBox formSection = new VBox(10);
        formSection.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10; -fx-padding: 15;");

        Label formTitle = new Label("Formulario de Estudiante");
        formTitle.setFont(Font.font("Arial Bold", 14));
        formTitle.setStyle("-fx-text-fill: #2c3e50;");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);

        idField = createField();
        nombreField = createField();
        apellidoField = createField();
        emailField = createField();
        telefonoField = createField();
        fechaNacimientoField = createField();
        generoField = createField();
        direccionField = createField();
        carreraField = createField();
        semestreField = createField();
        turnoField = createField();

        int r = 0;
        grid.add(fieldLabel("ID:"), 0, r); grid.add(idField, 1, r++);
        grid.add(fieldLabel("Nombre:"), 2, 0); grid.add(nombreField, 3, 0);
        grid.add(fieldLabel("Apellido:"), 0, r); grid.add(apellidoField, 1, r);
        grid.add(fieldLabel("Email:"), 2, r); grid.add(emailField, 3, r++);
        grid.add(fieldLabel("Teléfono:"), 0, r); grid.add(telefonoField, 1, r);
        grid.add(fieldLabel("Fecha Nac.:"), 2, r); grid.add(fechaNacimientoField, 3, r++);
        grid.add(fieldLabel("Género:"), 0, r); grid.add(generoField, 1, r);
        grid.add(fieldLabel("Dirección:"), 2, r); grid.add(direccionField, 3, r++);
        grid.add(fieldLabel("Carrera:"), 0, r); grid.add(carreraField, 1, r);
        grid.add(fieldLabel("Semestre:"), 2, r); grid.add(semestreField, 3, r++);
        grid.add(fieldLabel("Turno:"), 0, r); grid.add(turnoField, 1, r);

        HBox buttons = new HBox(12);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10, 0, 0, 0));

        Button guardar = actionBtn("💾 Guardar", "#27AE60");
        Button actualizar = actionBtn("✏️ Actualizar", "#F39C12");
        Button eliminar = actionBtn("🗑️ Eliminar", "#E74C3C");
        Button limpiar = actionBtn("🔄 Limpiar", "#95A5A6");

        guardar.setOnAction(e -> save());
        actualizar.setOnAction(e -> update());
        eliminar.setOnAction(e -> delete());
        limpiar.setOnAction(e -> clearForm());

        buttons.getChildren().addAll(guardar, actualizar, eliminar, limpiar);
        formSection.getChildren().addAll(formTitle, grid, buttons);
        tableSection.getChildren().addAll(sectionTitle, tableView, formSection);
        content.getChildren().addAll(stats, tableSection);

        tableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, sel) -> { if (sel != null) fillForm(sel); }
        );

        return content;
    }

    private void createColumns() {
        TableColumn<Estudiante, String> c1 = new TableColumn<>("ID");
        c1.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getId()));
        TableColumn<Estudiante, String> c2 = new TableColumn<>("Nombre");
        c2.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombreCompleto()));
        TableColumn<Estudiante, String> c3 = new TableColumn<>("Carrera");
        c3.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCarrera()));
        TableColumn<Estudiante, Number> c4 = new TableColumn<>("Semestre");
        c4.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getSemestre()));
        TableColumn<Estudiante, String> c5 = new TableColumn<>("Email");
        c5.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEmail()));
        tableView.getColumns().addAll(c1, c2, c3, c4, c5);
    }

    private void fillForm(Estudiante e) {
        idField.setText(e.getId());
        nombreField.setText(e.getNombre());
        apellidoField.setText(e.getApellido());
        emailField.setText(e.getEmail());
        telefonoField.setText(e.getTelefono());
        fechaNacimientoField.setText(e.getFechaNacimiento());
        generoField.setText(e.getGenero());
        direccionField.setText(e.getDireccion());
        carreraField.setText(e.getCarrera());
        semestreField.setText(String.valueOf(e.getSemestre()));
        turnoField.setText(e.getTurno());
    }

    private void clearForm() {
        idField.clear(); nombreField.clear(); apellidoField.clear(); emailField.clear();
        telefonoField.clear(); fechaNacimientoField.clear(); generoField.clear();
        direccionField.clear(); carreraField.clear(); semestreField.clear(); turnoField.clear();
    }

    private void save() {
        try {
            String fn = convertirFecha(fechaNacimientoField.getText());
            Estudiante e = new Estudiante(idField.getText(), nombreField.getText(), apellidoField.getText(),
                emailField.getText(), telefonoField.getText(), fn, generoField.getText(),
                direccionField.getText(), carreraField.getText(), Integer.parseInt(semestreField.getText()), turnoField.getText());
            estudianteService.registrarEstudiante(e);
            tableView.setItems(getEstudiantesData());
            clearForm();
            show("Éxito", "Estudiante registrado");
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private void update() {
        try {
            Estudiante e = new Estudiante(idField.getText(), nombreField.getText(), apellidoField.getText(),
                emailField.getText(), telefonoField.getText(), fechaNacimientoField.getText(), generoField.getText(),
                direccionField.getText(), carreraField.getText(), Integer.parseInt(semestreField.getText()), turnoField.getText());
            estudianteService.actualizarEstudiante(e);
            tableView.setItems(getEstudiantesData());
            show("Éxito", "Estudiante actualizado");
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private void delete() {
        try {
            String id = idField.getText();
            if (!id.isEmpty()) {
                estudianteService.eliminarEstudiante(id);
                tableView.setItems(getEstudiantesData());
                clearForm();
                show("Éxito", "Estudiante eliminado");
            }
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private String convertirFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) return "2000-01-01";
        fecha = fecha.trim();
        if (fecha.matches("\\d{4}-\\d{2}-\\d{2}")) return fecha;
        if (fecha.contains("/")) {
            String[] p = fecha.split("/");
            if (p.length == 3) return String.format("%s-%s-%s", p[2], p[1], p[0]);
        }
        return fecha;
    }

    private ObservableList<Estudiante> getEstudiantesData() {
        return FXCollections.observableArrayList(estudianteService.obtenerTodosEstudiantes());
    }
}
