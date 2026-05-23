package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.model.Doctor;
import com.hospital.sanrafael.service.DoctorService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class DoctorController extends BaseDashboardController {
    private final DoctorService doctorService;
    private TableView<Doctor> tableView;
    private TextField idField, nombreField, apellidoField, emailField, telefonoField;
    private TextField fechaNacimientoField, generoField, direccionField;
    private TextField especialidadField, colegiadoField, areaField, experienciaField;

    public DoctorController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.doctorService = new DoctorService();
    }

    @Override protected String getSidebarColor() { return "#2C3E8F"; }
    @Override protected String getSidebarLogo() { return "🏥 MEDjestic"; }
    @Override protected String getSidebarLetter() { return "D"; }
    @Override protected String getModuleName() { return "Doctores"; }
    @Override protected String getModuleRole() { return "Módulo Médico"; }
    @Override protected String getTitle() { return "Gestión de Doctores"; }

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
        int total = doctorService.obtenerTodosDoctores().size();
        stats.getChildren().addAll(
            statCard("TOTAL", String.valueOf(total), "#2C3E8F"),
            statCard("ESPECIALIDADES", "8", "#27AE60"),
            statCard("ÁREAS", "4", "#E67E22"),
            statCard("EXPERIENCIA", "5+ años", "#E74C3C")
        );

        VBox tableSection = new VBox(10);
        tableSection.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        Label sectionTitle = new Label("Lista de Doctores");
        sectionTitle.setFont(Font.font("Arial Bold", 16));
        sectionTitle.setStyle("-fx-text-fill: #2c3e50;");

        tableView = new TableView<>();
        tableView.setItems(getDoctoresData());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(180);
        createColumns();

        VBox formSection = new VBox(10);
        formSection.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10; -fx-padding: 15;");

        Label formTitle = new Label("Formulario de Doctor");
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
        especialidadField = createField();
        colegiadoField = createField();
        areaField = createField();
        experienciaField = createField();

        int r = 0;
        grid.add(fieldLabel("ID:"), 0, r); grid.add(idField, 1, r++);
        grid.add(fieldLabel("Nombre:"), 2, 0); grid.add(nombreField, 3, 0);
        grid.add(fieldLabel("Apellido:"), 0, r); grid.add(apellidoField, 1, r);
        grid.add(fieldLabel("Email:"), 2, r); grid.add(emailField, 3, r++);
        grid.add(fieldLabel("Teléfono:"), 0, r); grid.add(telefonoField, 1, r);
        grid.add(fieldLabel("Fecha Nac.:"), 2, r); grid.add(fechaNacimientoField, 3, r++);
        grid.add(fieldLabel("Género:"), 0, r); grid.add(generoField, 1, r);
        grid.add(fieldLabel("Dirección:"), 2, r); grid.add(direccionField, 3, r++);
        grid.add(fieldLabel("Especialidad:"), 0, r); grid.add(especialidadField, 1, r);
        grid.add(fieldLabel("N° Colegiado:"), 2, r); grid.add(colegiadoField, 3, r++);
        grid.add(fieldLabel("Área:"), 0, r); grid.add(areaField, 1, r);
        grid.add(fieldLabel("Años Exp.:"), 2, r); grid.add(experienciaField, 3, r++);

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
        TableColumn<Doctor, String> c1 = new TableColumn<>("ID");
        c1.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getId()));
        TableColumn<Doctor, String> c2 = new TableColumn<>("Nombre");
        c2.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombreCompleto()));
        TableColumn<Doctor, String> c3 = new TableColumn<>("Especialidad");
        c3.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEspecialidad()));
        TableColumn<Doctor, String> c4 = new TableColumn<>("N° Colegiado");
        c4.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNumeroColegiado()));
        TableColumn<Doctor, String> c5 = new TableColumn<>("Área");
        c5.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getAreaAsignada()));
        tableView.getColumns().addAll(c1, c2, c3, c4, c5);
    }

    private void fillForm(Doctor d) {
        idField.setText(d.getId());
        nombreField.setText(d.getNombre());
        apellidoField.setText(d.getApellido());
        emailField.setText(d.getEmail());
        telefonoField.setText(d.getTelefono());
        fechaNacimientoField.setText(d.getFechaNacimiento());
        generoField.setText(d.getGenero());
        direccionField.setText(d.getDireccion());
        especialidadField.setText(d.getEspecialidad());
        colegiadoField.setText(d.getNumeroColegiado());
        areaField.setText(d.getAreaAsignada());
        experienciaField.setText(String.valueOf(d.getAniosExperiencia()));
    }

    private void clearForm() {
        idField.clear(); nombreField.clear(); apellidoField.clear(); emailField.clear();
        telefonoField.clear(); fechaNacimientoField.clear(); generoField.clear();
        direccionField.clear(); especialidadField.clear(); colegiadoField.clear();
        areaField.clear(); experienciaField.clear();
    }

    private void save() {
        try {
            Doctor d = new Doctor(idField.getText(), nombreField.getText(), apellidoField.getText(),
                emailField.getText(), telefonoField.getText(), fechaNacimientoField.getText(), generoField.getText(),
                direccionField.getText(), especialidadField.getText(), colegiadoField.getText(),
                areaField.getText(), Integer.parseInt(experienciaField.getText()));
            doctorService.registrarDoctor(d);
            tableView.setItems(getDoctoresData());
            clearForm();
            show("Éxito", "Doctor registrado");
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private void update() {
        try {
            Doctor d = new Doctor(idField.getText(), nombreField.getText(), apellidoField.getText(),
                emailField.getText(), telefonoField.getText(), fechaNacimientoField.getText(), generoField.getText(),
                direccionField.getText(), especialidadField.getText(), colegiadoField.getText(),
                areaField.getText(), Integer.parseInt(experienciaField.getText()));
            doctorService.actualizarDoctor(d);
            tableView.setItems(getDoctoresData());
            show("Éxito", "Doctor actualizado");
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private void delete() {
        try {
            String id = idField.getText();
            if (!id.isEmpty()) {
                doctorService.eliminarDoctor(id);
                tableView.setItems(getDoctoresData());
                clearForm();
                show("Éxito", "Doctor eliminado");
            }
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private ObservableList<Doctor> getDoctoresData() {
        return FXCollections.observableArrayList(doctorService.obtenerTodosDoctores());
    }
}
