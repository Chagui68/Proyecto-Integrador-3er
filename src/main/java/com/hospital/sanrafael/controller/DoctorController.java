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

public class DoctorController {
    private final ViewFactory viewFactory;
    private final DoctorService doctorService;
    private TableView<Doctor> tableView;
    private TextField idField, nombreField, apellidoField, emailField, telefonoField;
    private TextField fechaNacimientoField, generoField, direccionField;
    private TextField especialidadField, numeroColegiadoField, areaAsignadaField, aniosExperienciaField;

    public DoctorController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.doctorService = new DoctorService();
    }

    public Pane getView() {
        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: #ecf0f1;");

        Label titleLabel = new Label("Gestión de Doctores");
        titleLabel.setFont(new Font("Arial Bold", 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");
        titleLabel.setPadding(new Insets(10));

        tableView = new TableView<>();
        tableView.setItems(getDoctoresData());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        createColumns();

        VBox formPane = createForm();

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(10));
        
        Button btnNew = new Button("Nuevo");
        Button btnSave = new Button("Guardar");
        Button btnUpdate = new Button("Actualizar");
        Button btnDelete = new Button("Eliminar");
        Button btnBack = new Button("Volver");
        
        btnNew.setOnAction(e -> clearForm());
        btnSave.setOnAction(e -> saveDoctor());
        btnUpdate.setOnAction(e -> updateDoctor());
        btnDelete.setOnAction(e -> deleteDoctor());
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
        TableColumn<Doctor, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        
        TableColumn<Doctor, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreCompleto()));
        
        TableColumn<Doctor, String> colEspecialidad = new TableColumn<>("Especialidad");
        colEspecialidad.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEspecialidad()));
        
        TableColumn<Doctor, String> colColegiado = new TableColumn<>("N° Colegiado");
        colColegiado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNumeroColegiado()));
        
        TableColumn<Doctor, String> colArea = new TableColumn<>("Área Asignada");
        colArea.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAreaAsignada()));

        tableView.getColumns().addAll(colId, colNombre, colEspecialidad, colColegiado, colArea);
    }

    private VBox createForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        idField = new TextField();
        nombreField = new TextField();
        apellidoField = new TextField();
        emailField = new TextField();
        telefonoField = new TextField();
        fechaNacimientoField = new TextField();
        generoField = new TextField();
        direccionField = new TextField();
        especialidadField = new TextField();
        numeroColegiadoField = new TextField();
        areaAsignadaField = new TextField();
        aniosExperienciaField = new TextField();

        int row = 0;
        grid.add(new Label("ID:"), 0, row);
        grid.add(idField, 1, row++);
        grid.add(new Label("Nombre:"), 0, row);
        grid.add(nombreField, 1, row++);
        grid.add(new Label("Apellido:"), 0, row);
        grid.add(apellidoField, 1, row++);
        grid.add(new Label("Email:"), 0, row);
        grid.add(emailField, 1, row++);
        grid.add(new Label("Teléfono:"), 0, row);
        grid.add(telefonoField, 1, row++);
        grid.add(new Label("Fecha Nacimiento:"), 0, row);
        grid.add(fechaNacimientoField, 1, row++);
        grid.add(new Label("Género:"), 0, row);
        grid.add(generoField, 1, row++);
        grid.add(new Label("Dirección:"), 0, row);
        grid.add(direccionField, 1, row++);
        grid.add(new Label("Especialidad:"), 0, row);
        grid.add(especialidadField, 1, row++);
        grid.add(new Label("N° Colegiado:"), 0, row);
        grid.add(numeroColegiadoField, 1, row++);
        grid.add(new Label("Área Asignada:"), 0, row);
        grid.add(areaAsignadaField, 1, row++);
        grid.add(new Label("Años Experiencia:"), 0, row);
        grid.add(aniosExperienciaField, 1, row++);

        VBox vbox = new VBox(grid);
        vbox.setStyle("-fx-background-color: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        return vbox;
    }

    private void fillForm(Doctor doctor) {
        idField.setText(doctor.getId());
        nombreField.setText(doctor.getNombre());
        apellidoField.setText(doctor.getApellido());
        emailField.setText(doctor.getEmail());
        telefonoField.setText(doctor.getTelefono());
        fechaNacimientoField.setText(doctor.getFechaNacimiento());
        generoField.setText(doctor.getGenero());
        direccionField.setText(doctor.getDireccion());
        especialidadField.setText(doctor.getEspecialidad());
        numeroColegiadoField.setText(doctor.getNumeroColegiado());
        areaAsignadaField.setText(doctor.getAreaAsignada());
        aniosExperienciaField.setText(String.valueOf(doctor.getAniosExperiencia()));
    }

    private void clearForm() {
        idField.clear();
        nombreField.clear();
        apellidoField.clear();
        emailField.clear();
        telefonoField.clear();
        fechaNacimientoField.clear();
        generoField.clear();
        direccionField.clear();
        especialidadField.clear();
        numeroColegiadoField.clear();
        areaAsignadaField.clear();
        aniosExperienciaField.clear();
    }

    private void saveDoctor() {
        try {
            Doctor doctor = new Doctor(
                idField.getText(),
                nombreField.getText(),
                apellidoField.getText(),
                emailField.getText(),
                telefonoField.getText(),
                fechaNacimientoField.getText(),
                generoField.getText(),
                direccionField.getText(),
                especialidadField.getText(),
                numeroColegiadoField.getText(),
                areaAsignadaField.getText(),
                Integer.parseInt(aniosExperienciaField.getText())
            );
            doctorService.registrarDoctor(doctor);
            tableView.setItems(getDoctoresData());
            clearForm();
            showAlert("Éxito", "Doctor registrado correctamente");
        } catch (Exception e) {
            showAlert("Error", "Error al registrar doctor: " + e.getMessage());
        }
    }

    private void updateDoctor() {
        try {
            Doctor doctor = new Doctor(
                idField.getText(),
                nombreField.getText(),
                apellidoField.getText(),
                emailField.getText(),
                telefonoField.getText(),
                fechaNacimientoField.getText(),
                generoField.getText(),
                direccionField.getText(),
                especialidadField.getText(),
                numeroColegiadoField.getText(),
                areaAsignadaField.getText(),
                Integer.parseInt(aniosExperienciaField.getText())
            );
            doctorService.actualizarDoctor(doctor);
            tableView.setItems(getDoctoresData());
            showAlert("Éxito", "Doctor actualizado correctamente");
        } catch (Exception e) {
            showAlert("Error", "Error al actualizar doctor: " + e.getMessage());
        }
    }

    private void deleteDoctor() {
        try {
            String id = idField.getText();
            if (!id.isEmpty()) {
                doctorService.eliminarDoctor(id);
                tableView.setItems(getDoctoresData());
                clearForm();
                showAlert("Éxito", "Doctor eliminado correctamente");
            }
        } catch (Exception e) {
            showAlert("Error", "Error al eliminar doctor: " + e.getMessage());
        }
    }

    private ObservableList<Doctor> getDoctoresData() {
        return FXCollections.observableArrayList(doctorService.obtenerTodosDoctores());
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
