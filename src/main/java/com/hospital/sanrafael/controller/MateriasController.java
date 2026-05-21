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

public class MateriasController {
    private final ViewFactory viewFactory;
    private final MateriaService materiaService;
    private TableView<Materia> tableView;
    private TextField codigoField, nombreField, descripcionField;
    private TextField creditosField, semestreRecomendadoField, profesorField, aulaField;

    public MateriasController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.materiaService = new MateriaService();
    }

    public Pane getView() {
        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: #ecf0f1;");

        Label titleLabel = new Label("Gestión de Materias");
        titleLabel.setFont(new Font("Arial Bold", 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");
        titleLabel.setPadding(new Insets(10));

        tableView = new TableView<>();
        tableView.setItems(getMateriasData());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        createColumns();

        VBox formPane = createForm();

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(10));
        
        Button btnNew = new Button("Nueva");
        Button btnSave = new Button("Guardar");
        Button btnUpdate = new Button("Actualizar");
        Button btnDelete = new Button("Eliminar");
        Button btnBack = new Button("Volver");
        
        btnNew.setOnAction(e -> clearForm());
        btnSave.setOnAction(e -> saveMateria());
        btnUpdate.setOnAction(e -> updateMateria());
        btnDelete.setOnAction(e -> deleteMateria());
        btnBack.setOnAction(e -> {
            if (mainController != null) {
                mainController.navigateTo("main");
            }
        });

        buttonBar.getChildren().addAll(btnNew, btnSave, btnUpdate, btnDelete, btnBack);

        mainPane.setTop(titleLabel);
        mainPane.setCenter(tableView);
        mainPane.setBottom(new VBox(10, formPane, buttonBar));
        VBox.setMargin(formPane, new Insets(10));
        VBox.setMargin(buttonBar, new Insets(10));

        tableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    fillForm(newSelection);
                }
            }
        );

        return mainPane;
    }

    private void createColumns() {
        TableColumn<Materia, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigo()));
        
        TableColumn<Materia, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        
        TableColumn<Materia, String> colProfesor = new TableColumn<>("Profesor");
        colProfesor.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getProfesor()));
        
        TableColumn<Materia, Number> colCreditos = new TableColumn<>("Créditos");
        colCreditos.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCreditos()));
        
        TableColumn<Materia, Number> colSemestre = new TableColumn<>("Semestre");
        colSemestre.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getSemestreRecomendado()));

        tableView.getColumns().addAll(colCodigo, colNombre, colProfesor, colCreditos, colSemestre);
    }

    private VBox createForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        codigoField = new TextField();
        nombreField = new TextField();
        descripcionField = new TextField();
        creditosField = new TextField();
        semestreRecomendadoField = new TextField();
        profesorField = new TextField();
        aulaField = new TextField();

        int row = 0;
        grid.add(new Label("Código:"), 0, row);
        grid.add(codigoField, 1, row++);
        grid.add(new Label("Nombre:"), 0, row);
        grid.add(nombreField, 1, row++);
        grid.add(new Label("Descripción:"), 0, row);
        grid.add(descripcionField, 1, row++);
        grid.add(new Label("Créditos:"), 0, row);
        grid.add(creditosField, 1, row++);
        grid.add(new Label("Semestre Recomendado:"), 0, row);
        grid.add(semestreRecomendadoField, 1, row++);
        grid.add(new Label("Profesor:"), 0, row);
        grid.add(profesorField, 1, row++);
        grid.add(new Label("Aula:"), 0, row);
        grid.add(aulaField, 1, row++);

        VBox vbox = new VBox(grid);
        vbox.setStyle("-fx-background-color: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        return vbox;
    }

    private void fillForm(Materia materia) {
        codigoField.setText(materia.getCodigo());
        nombreField.setText(materia.getNombre());
        descripcionField.setText(materia.getDescripcion());
        creditosField.setText(String.valueOf(materia.getCreditos()));
        semestreRecomendadoField.setText(String.valueOf(materia.getSemestreRecomendado()));
        profesorField.setText(materia.getProfesor());
        aulaField.setText(materia.getAula());
    }

    private void clearForm() {
        codigoField.clear();
        nombreField.clear();
        descripcionField.clear();
        creditosField.clear();
        semestreRecomendadoField.clear();
        profesorField.clear();
        aulaField.clear();
    }

    private void saveMateria() {
        try {
            Materia materia = new Materia(
                codigoField.getText(),
                nombreField.getText(),
                descripcionField.getText(),
                Integer.parseInt(creditosField.getText()),
                Integer.parseInt(semestreRecomendadoField.getText()),
                profesorField.getText(),
                aulaField.getText()
            );
            materiaService.registrarMateria(materia);
            tableView.setItems(getMateriasData());
            clearForm();
            showAlert("Éxito", "Materia registrada correctamente");
        } catch (Exception e) {
            showAlert("Error", "Error al registrar materia: " + e.getMessage());
        }
    }

    private void updateMateria() {
        try {
            Materia materia = new Materia(
                codigoField.getText(),
                nombreField.getText(),
                descripcionField.getText(),
                Integer.parseInt(creditosField.getText()),
                Integer.parseInt(semestreRecomendadoField.getText()),
                profesorField.getText(),
                aulaField.getText()
            );
            materiaService.actualizarMateria(materia);
            tableView.setItems(getMateriasData());
            showAlert("Éxito", "Materia actualizada correctamente");
        } catch (Exception e) {
            showAlert("Error", "Error al actualizar materia: " + e.getMessage());
        }
    }

    private void deleteMateria() {
        try {
            String codigo = codigoField.getText();
            if (!codigo.isEmpty()) {
                materiaService.eliminarMateria(codigo);
                tableView.setItems(getMateriasData());
                clearForm();
                showAlert("Éxito", "Materia eliminada correctamente");
            }
        } catch (Exception e) {
            showAlert("Error", "Error al eliminar materia: " + e.getMessage());
        }
    }

    private ObservableList<Materia> getMateriasData() {
        return FXCollections.observableArrayList(materiaService.obtenerTodasMaterias());
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
