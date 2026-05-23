package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.service.AuthService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RegisterController {
    private final ViewFactory viewFactory;
    private final AuthService authService;
    private MainController mainController;
    private TextField usernameField, emailField, nombreField;
    private PasswordField passwordField, confirmField;
    private ComboBox<String> roleCombo;

    public RegisterController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        this.authService = new AuthService();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Pane getView() {
        HBox root = new HBox();

        VBox leftPanel = createLeftPanel();
        VBox rightPanel = createRightPanel();

        leftPanel.setMinWidth(700);
        leftPanel.setMaxWidth(700);

        root.getChildren().addAll(leftPanel, rightPanel);
        return root;
    }

    private VBox createLeftPanel() {
        VBox panel = new VBox();
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a5f3a, #27AE60);");
        panel.setPadding(new Insets(60, 40, 60, 40));

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(500);

        Label icon = new Label("📝");
        icon.setFont(Font.font(80));

        Label title = new Label("Crear tu Cuenta");
        title.setFont(Font.font("Arial Bold", 32));
        title.setStyle("-fx-text-fill: white; -fx-text-alignment: center;");
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Label subtitle = new Label("Regístrate según tu rol\npara acceder al sistema");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setStyle("-fx-text-fill: rgba(255,255,255,0.8); -fx-text-alignment: center;");
        subtitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        VBox features = new VBox(12);
        features.setAlignment(Pos.CENTER_LEFT);
        features.setPadding(new Insets(30, 0, 0, 0));

        features.getChildren().addAll(
            featureRow("👨‍⚕️", "Doctores: gestiona pacientes y horarios"),
            featureRow("👤", "Estudiantes: acceso a materias y notas"),
            featureRow("🔧", "Administradores: control total del sistema")
        );

        content.getChildren().addAll(icon, title, subtitle, features);
        panel.getChildren().add(content);
        return panel;
    }

    private HBox featureRow(String icon, String text) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font(24));
        Label textLbl = new Label(text);
        textLbl.setFont(Font.font("Arial", 14));
        textLbl.setStyle("-fx-text-fill: rgba(255,255,255,0.9);");
        row.getChildren().addAll(iconLbl, textLbl);
        return row;
    }

    private VBox createRightPanel() {
        VBox panel = new VBox();
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: white;");
        panel.setPadding(new Insets(40, 50, 40, 50));
        HBox.setHgrow(panel, Priority.ALWAYS);

        VBox card = new VBox(14);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMaxWidth(380);

        Label title = new Label("Registro");
        title.setFont(Font.font("Arial Bold", 28));
        title.setStyle("-fx-text-fill: #2c3e50;");

        Label instruct = new Label("Completa todos los campos para registrarte");
        instruct.setFont(Font.font("Arial", 14));
        instruct.setStyle("-fx-text-fill: #95a5a6;");

        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER_LEFT);

        form.getChildren().addAll(
            fieldLabel("Nombre completo"),
            nombreField = createField("Ej: Juan Pérez"),

            fieldLabel("Correo electrónico"),
            emailField = createField("Ej: correo@ejemplo.com"),

            fieldLabel("Nombre de usuario"),
            usernameField = createField("Ej: juanperez"),

            fieldLabel("Rol"),
            roleCombo = createRoleCombo(),

            fieldLabel("Contraseña"),
            passwordField = createPasswordField("Mínimo 4 caracteres"),

            fieldLabel("Confirmar contraseña"),
            confirmField = createPasswordField("Repite la contraseña")
        );

        Button registerBtn = new Button("Crear Cuenta");
        registerBtn.setPrefWidth(380);
        registerBtn.setPrefHeight(48);
        registerBtn.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle("-fx-background-color: #219a52; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;"));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;"));
        registerBtn.setOnAction(e -> doRegister());

        HBox loginRow = new HBox(5);
        loginRow.setAlignment(Pos.CENTER);
        Label hasAccount = new Label("¿Ya tienes cuenta?");
        hasAccount.setFont(Font.font("Arial", 13));
        hasAccount.setStyle("-fx-text-fill: #7f8c8d;");
        Label loginLink = new Label("Inicia sesión");
        loginLink.setFont(Font.font("Arial Bold", 13));
        loginLink.setStyle("-fx-text-fill: #2C3E8F; -fx-cursor: hand; -fx-underline: true;");
        loginLink.setOnMouseClicked(e -> {
            if (mainController != null) mainController.navigateTo("login");
        });
        loginRow.getChildren().addAll(hasAccount, loginLink);

        card.getChildren().addAll(title, instruct, form, registerBtn, loginRow);
        panel.getChildren().add(card);
        return panel;
    }

    private Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial Bold", 13));
        l.setStyle("-fx-text-fill: #555;");
        return l;
    }

    private TextField createField(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setPrefWidth(380);
        f.setPrefHeight(42);
        f.setStyle("-fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10; -fx-border-color: #ddd; -fx-font-size: 14px;");
        return f;
    }

    private PasswordField createPasswordField(String prompt) {
        PasswordField f = new PasswordField();
        f.setPromptText(prompt);
        f.setPrefWidth(380);
        f.setPrefHeight(42);
        f.setStyle("-fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10; -fx-border-color: #ddd; -fx-font-size: 14px;");
        return f;
    }

    private ComboBox<String> createRoleCombo() {
        ComboBox<String> cb = new ComboBox<>();
        cb.setItems(FXCollections.observableArrayList("Seleccione un rol", "Administrador", "Doctor", "Estudiante"));
        cb.setValue("Seleccione un rol");
        cb.setPrefWidth(380);
        cb.setPrefHeight(42);
        cb.setStyle("-fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 5; -fx-border-color: #ddd; -fx-font-size: 14px;");
        return cb;
    }

    private void doRegister() {
        String role = roleCombo.getValue();
        String user = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String nombre = nombreField.getText().trim();
        String pass = passwordField.getText().trim();
        String confirm = confirmField.getText().trim();

        if (role.equals("Seleccione un rol")) {
            showAlert("Error", "Por favor seleccione un rol");
            return;
        }
        if (user.isEmpty() || email.isEmpty() || nombre.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "Todos los campos son obligatorios");
            return;
        }
        if (!pass.equals(confirm)) {
            showAlert("Error", "Las contraseñas no coinciden");
            return;
        }
        if (pass.length() < 4) {
            showAlert("Error", "La contraseña debe tener al menos 4 caracteres");
            return;
        }
        if (authService.usernameExists(user)) {
            showAlert("Error", "El nombre de usuario ya existe");
            return;
        }

        if (authService.register(user, email, pass, nombre, role)) {
            showAlert("Éxito", "Cuenta creada correctamente. Ahora puedes iniciar sesión.");
            if (mainController != null) mainController.navigateTo("login");
        } else {
            showAlert("Error", "No se pudo crear la cuenta");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
