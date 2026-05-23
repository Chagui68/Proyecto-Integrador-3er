package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.model.Materia;
import com.hospital.sanrafael.service.MateriaService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class MateriasController extends BaseDashboardController {
    private final MateriaService materiaService;
    private TableView<Materia> tableView;
    private TextField codigoField, nombreField, descripcionField;
    private TextField creditosField, semestreField, profesorField, aulaField;

    public MateriasController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.materiaService = new MateriaService();
    }

    @Override protected String getSidebarColor() { return "#8E44AD"; }
    @Override protected String getSidebarLogo() { return "🏫 MEDjestic"; }
    @Override protected String getSidebarLetter() { return "M"; }
    @Override protected String getModuleName() { return "Materias"; }
    @Override protected String getModuleRole() { return "Módulo Académico"; }
    @Override protected String getTitle() { return "Gestión de Materias"; }

    @Override
    protected VBox createSidebarMenuItems() {
        VBox menu = new VBox(5);
        Button listBtn = sidebarBtn("📋 Listado", true);
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

        HBox stats = new HBox(15);
        int total = materiaService.obtenerTodasMaterias().size();
        stats.getChildren().addAll(
            statCard("TOTAL MATERIAS", String.valueOf(total), "#8E44AD"),
            statCard("PROFESORES", "—", "#27AE60"),
            statCard("SEMESTRES", "10", "#E67E22"),
            statCard("CRÉDITOS PROM.", "—", "#E74C3C")
        );

        VBox tableSection = new VBox(10);
        tableSection.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");

        Label sectionTitle = new Label("Lista de Materias");
        sectionTitle.setFont(Font.font("Arial Bold", 16));
        sectionTitle.setStyle("-fx-text-fill: #2c3e50;");

        tableView = new TableView<>();
        tableView.setItems(getMateriasData());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(180);
        createColumns();

        VBox formSection = new VBox(10);
        formSection.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10; -fx-padding: 15;");

        Label formTitle = new Label("Formulario de Materias");
        formTitle.setFont(Font.font("Arial Bold", 14));
        formTitle.setStyle("-fx-text-fill: #2c3e50;");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);

        codigoField = createField();
        nombreField = createField();
        descripcionField = createField();
        creditosField = createField();
        semestreField = createField();
        profesorField = createField();
        aulaField = createField();

        int r = 0;
        grid.add(fieldLabel("Código:"), 0, r); grid.add(codigoField, 1, r++);
        grid.add(fieldLabel("Nombre:"), 2, 0); grid.add(nombreField, 3, 0);
        grid.add(fieldLabel("Descripción:"), 0, r); grid.add(descripcionField, 1, r);
        grid.add(fieldLabel("Créditos:"), 2, r); grid.add(creditosField, 3, r++);
        grid.add(fieldLabel("Semestre:"), 0, r); grid.add(semestreField, 1, r);
        grid.add(fieldLabel("Profesor:"), 2, r); grid.add(profesorField, 3, r++);
        grid.add(fieldLabel("Aula:"), 0, r); grid.add(aulaField, 1, r);

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
        TableColumn<Materia, String> c1 = new TableColumn<>("Código");
        c1.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCodigo()));
        TableColumn<Materia, String> c2 = new TableColumn<>("Nombre");
        c2.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombre()));
        TableColumn<Materia, String> c3 = new TableColumn<>("Profesor");
        c3.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getProfesor()));
        TableColumn<Materia, Number> c4 = new TableColumn<>("Créditos");
        c4.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getCreditos()));
        TableColumn<Materia, Number> c5 = new TableColumn<>("Semestre");
        c5.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getSemestreRecomendado()));
        tableView.getColumns().addAll(c1, c2, c3, c4, c5);
    }

    private void fillForm(Materia m) {
        codigoField.setText(m.getCodigo());
        nombreField.setText(m.getNombre());
        descripcionField.setText(m.getDescripcion());
        creditosField.setText(String.valueOf(m.getCreditos()));
        semestreField.setText(String.valueOf(m.getSemestreRecomendado()));
        profesorField.setText(m.getProfesor());
        aulaField.setText(m.getAula());
    }

    private void clearForm() {
        codigoField.clear(); nombreField.clear(); descripcionField.clear();
        creditosField.clear(); semestreField.clear(); profesorField.clear(); aulaField.clear();
    }

    private void save() {
        try {
            Materia m = new Materia(codigoField.getText(), nombreField.getText(), descripcionField.getText(),
                Integer.parseInt(creditosField.getText()), Integer.parseInt(semestreField.getText()),
                profesorField.getText(), aulaField.getText());
            materiaService.registrarMateria(m);
            tableView.setItems(getMateriasData());
            clearForm();
            show("Éxito", "Materia registrada");
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private void update() {
        try {
            Materia m = new Materia(codigoField.getText(), nombreField.getText(), descripcionField.getText(),
                Integer.parseInt(creditosField.getText()), Integer.parseInt(semestreField.getText()),
                profesorField.getText(), aulaField.getText());
            materiaService.actualizarMateria(m);
            tableView.setItems(getMateriasData());
            show("Éxito", "Materia actualizada");
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private void delete() {
        try {
            String codigo = codigoField.getText();
            if (!codigo.isEmpty()) {
                materiaService.eliminarMateria(codigo);
                tableView.setItems(getMateriasData());
                clearForm();
                show("Éxito", "Materia eliminada");
            }
        } catch (Exception ex) {
            show("Error", "Error: " + ex.getMessage());
        }
    }

    private ObservableList<Materia> getMateriasData() {
        return FXCollections.observableArrayList(materiaService.obtenerTodasMaterias());
    }
}
