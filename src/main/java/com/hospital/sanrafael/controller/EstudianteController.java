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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EstudianteController {
    private final ViewFactory viewFactory;
    private final EstudianteService estudianteService;
    private TableView<Estudiante> tableView;
    private TextField idField, nombreField, apellidoField, emailField, telefonoField;
    private TextField fechaNacimientoField, generoField, direccionField;
    private TextField carreraField, semestreField, turnoField;
    private MainController mainController;

    public EstudianteController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.estudianteService = new EstudianteService();
    }

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    public Pane getView() {
        BorderPane mainPane = new BorderPane();
        
        Background gradient = new Background(new BackgroundFill(
            new javafx.scene.paint.LinearGradient(0, 0, 1, 1, true, 
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0, Color.valueOf("#667eea")),
                new javafx.scene.paint.Stop(1, Color.valueOf("#764ba2"))),
            CornerRadii.EMPTY, Insets.EMPTY));
        mainPane.setBackground(gradient);

        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15));
        
        Label titleLabel = new Label("🎓 Gestión de Estudiantes");
        titleLabel.setFont(new Font("Arial Bold", 28));
        titleLabel.setStyle("-fx-text-fill: white; -fx-effect: dropshadow(gaussian, black, 5, 0, 0, 2);");
        
        header.getChildren().add(titleLabel);
        mainPane.setTop(header);

        tableView = new TableView<>();
        tableView.setItems(getEstudiantesData());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPadding(new Insets(10));
        
        createColumns();

        VBox formPane = createForm();

        HBox buttonBar = createButtonBar();

        VBox bottomPanel = new VBox(15, formPane, buttonBar);
        bottomPanel.setPadding(new Insets(15));
        
        mainPane.setCenter(tableView);
        mainPane.setBottom(bottomPanel);

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
        TableColumn<Estudiante, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        colId.setPrefWidth(80);
        
        TableColumn<Estudiante, String> colNombre = new TableColumn<>("Nombre Completo");
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreCompleto()));
        colNombre.setPrefWidth(200);
        
        TableColumn<Estudiante, String> colCarrera = new TableColumn<>("Carrera");
        colCarrera.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCarrera()));
        colCarrera.setPrefWidth(150);
        
        TableColumn<Estudiante, Number> colSemestre = new TableColumn<>("Semestre");
        colSemestre.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getSemestre()));
        colSemestre.setPrefWidth(80);
        
        TableColumn<Estudiante, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        colEmail.setPrefWidth(200);

        tableView.getColumns().addAll(colId, colNombre, colCarrera, colSemestre, colEmail);
    }

    private VBox createForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-background-radius: 10;");

        idField = createTextField();
        nombreField = createTextField();
        apellidoField = createTextField();
        emailField = createTextField();
        telefonoField = createTextField();
        fechaNacimientoField = createTextField();
        generoField = createTextField();
        direccionField = createTextField();
        carreraField = createTextField();
        semestreField = createTextField();
        turnoField = createTextField();

        int row = 0;
        grid.add(createLabel("ID:"), 0, row);
        grid.add(idField, 1, row++);
        grid.add(createLabel("Nombre:"), 0, row);
        grid.add(nombreField, 1, row++);
        grid.add(createLabel("Apellido:"), 0, row);
        grid.add(apellidoField, 1, row++);
        grid.add(createLabel("Email:"), 0, row);
        grid.add(emailField, 1, row++);
        grid.add(createLabel("Teléfono:"), 0, row);
        grid.add(telefonoField, 1, row++);
        grid.add(createLabel("Fecha Nacimiento:"), 0, row);
        grid.add(fechaNacimientoField, 1, row++);
        grid.add(createLabel("Género:"), 0, row);
        grid.add(generoField, 1, row++);
        grid.add(createLabel("Dirección:"), 0, row);
        grid.add(direccionField, 1, row++);
        grid.add(createLabel("Carrera:"), 0, row);
        grid.add(carreraField, 1, row++);
        grid.add(createLabel("Semestre:"), 0, row);
        grid.add(semestreField, 1, row++);
        grid.add(createLabel("Turno:"), 0, row);
        grid.add(turnoField, 1, row++);

        VBox vbox = new VBox(grid);
        return vbox;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font("Arial Bold", 12));
        return label;
    }

    private TextField createTextField() {
        TextField field = new TextField();
        field.setPrefWidth(200);
        field.setStyle("-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
        return field;
    }

    private HBox createButtonBar() {
        HBox buttonBar = new HBox(15);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(15));
        buttonBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 10;");
        
        Button btnNew = createButton("➕ Nuevo", "#3498db");
        Button btnSave = createButton("💾 Guardar", "#27ae60");
        Button btnUpdate = createButton("✏️ Actualizar", "#f39c12");
        Button btnDelete = createButton("🗑️ Eliminar", "#e74c3c");
        Button btnBack = createButton("⬅️ Volver", "#95a5a6");
        
        btnNew.setOnAction(e -> clearForm());
        btnSave.setOnAction(e -> saveEstudiante());
        btnUpdate.setOnAction(e -> updateEstudiante());
        btnDelete.setOnAction(e -> deleteEstudiante());
        btnBack.setOnAction(e -> {
            if (mainController != null) {
                mainController.navigateTo("main");
            }
        });

        buttonBar.getChildren().addAll(btnNew, btnSave, btnUpdate, btnDelete, btnBack);
        return buttonBar;
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(new Font("Arial Bold", 13));
        button.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8 15;"
        );
        
        button.setOnMouseEntered(e -> 
            button.setStyle("-fx-background-color: derive(" + color + ", 80%); -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8 15;")
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8 15;")
        );
        
        return button;
    }

    private void fillForm(Estudiante estudiante) {
        idField.setText(estudiante.getId());
        nombreField.setText(estudiante.getNombre());
        apellidoField.setText(estudiante.getApellido());
        emailField.setText(estudiante.getEmail());
        telefonoField.setText(estudiante.getTelefono());
        fechaNacimientoField.setText(estudiante.getFechaNacimiento());
        generoField.setText(estudiante.getGenero());
        direccionField.setText(estudiante.getDireccion());
        carreraField.setText(estudiante.getCarrera());
        semestreField.setText(String.valueOf(estudiante.getSemestre()));
        turnoField.setText(estudiante.getTurno());
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
        carreraField.clear();
        semestreField.clear();
        turnoField.clear();
    }

    private void saveEstudiante() {
        try {
            System.out.println("📝 Intentando guardar estudiante desde la interfaz...");
            
            // Convertir fecha al formato correcto (AAAA-MM-DD) si es necesario
            String fechaNacimiento = convertirFecha(fechaNacimientoField.getText());
            
            Estudiante estudiante = new Estudiante(
                idField.getText(),
                nombreField.getText(),
                apellidoField.getText(),
                emailField.getText(),
                telefonoField.getText(),
                fechaNacimiento,
                generoField.getText(),
                direccionField.getText(),
                carreraField.getText(),
                Integer.parseInt(semestreField.getText()),
                turnoField.getText()
            );
            
            estudianteService.registrarEstudiante(estudiante);
            
            // Refrescar la tabla desde la base de datos
            tableView.setItems(getEstudiantesData());
            clearForm();
            showAlert("Éxito", "Estudiante registrado correctamente en PostgreSQL");
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error completo en la consola
            showAlert("Error", "No se pudo guardar en la base de datos:\n" + e.getMessage());
        }
    }
    
    /**
     * Convierte varios formatos de fecha al formato estándar SQL (AAAA-MM-DD)
     * Acepta: AAAA-MM-DD, DD/MM/AAAA, DD-MM-AAAA
     */
    private String convertirFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return "2000-01-01"; // Valor por defecto si está vacío
        }
        
        fecha = fecha.trim();
        
        // Si ya está en formato AAAA-MM-DD, devolverla tal cual
        if (fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return fecha;
        }
        
        // Intentar convertir DD/MM/AAAA a AAAA-MM-DD
        if (fecha.contains("/")) {
            String[] partes = fecha.split("/");
            if (partes.length == 3) {
                return String.format("%s-%s-%s", partes[2], partes[1], partes[0]);
            }
        }
        
        // Intentar convertir DD-MM-AAAA a AAAA-MM-DD
        if (fecha.contains("-") && !fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] partes = fecha.split("-");
            if (partes.length == 3) {
                return String.format("%s-%s-%s", partes[2], partes[1], partes[0]);
            }
        }
        
        // Si no coincide ningún formato, devolver la fecha original y dejar que PostgreSQL intente parsearla
        return fecha;
    }

    private void updateEstudiante() {
        try {
            System.out.println("✏️ Intentando actualizar estudiante...");
            
            Estudiante estudiante = new Estudiante(
                idField.getText(),
                nombreField.getText(),
                apellidoField.getText(),
                emailField.getText(),
                telefonoField.getText(),
                fechaNacimientoField.getText(),
                generoField.getText(),
                direccionField.getText(),
                carreraField.getText(),
                Integer.parseInt(semestreField.getText()),
                turnoField.getText()
            );
            estudianteService.actualizarEstudiante(estudiante);
            tableView.setItems(getEstudiantesData());
            showAlert("Éxito", "Estudiante actualizado correctamente en PostgreSQL");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo actualizar en la base de datos:\n" + e.getMessage());
        }
    }

    private void deleteEstudiante() {
        try {
            String id = idField.getText();
            if (!id.isEmpty()) {
                System.out.println("🗑️ Intentando eliminar estudiante: " + id);
                estudianteService.eliminarEstudiante(id);
                tableView.setItems(getEstudiantesData());
                clearForm();
                showAlert("Éxito", "Estudiante eliminado correctamente de PostgreSQL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo eliminar de la base de datos:\n" + e.getMessage());
        }
    }

    private ObservableList<Estudiante> getEstudiantesData() {
        return FXCollections.observableArrayList(estudianteService.obtenerTodosEstudiantes());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
