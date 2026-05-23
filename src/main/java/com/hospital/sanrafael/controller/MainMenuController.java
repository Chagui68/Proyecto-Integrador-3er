package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.service.DatosInicialesService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenuController {
    private final ViewFactory viewFactory;
    private MainController mainController;
    private static boolean datosInicialesCargados = false;

    public MainMenuController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
        if (!datosInicialesCargados) {
            cargarDatosIniciales();
            datosInicialesCargados = true;
        }
    }

    private void cargarDatosIniciales() {
        try {
            DatosInicialesService datosService = new DatosInicialesService();
            datosService.cargarDatosEjemplo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Pane getView() {
        HBox root = new HBox();
        root.setPadding(new Insets(0));

        VBox leftPanel = createLeftPanel();
        ImageView hospitalImage = createHospitalImage();

        leftPanel.setMinWidth(420);
        leftPanel.setMaxWidth(420);

        root.getChildren().addAll(leftPanel, hospitalImage);
        return root;
    }

    private VBox createLeftPanel() {
        VBox panel = new VBox(20);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(50, 30, 40, 30));
        panel.setStyle("-fx-background-color: white;");

        VBox logoBox = new VBox(5);
        logoBox.setAlignment(Pos.CENTER);

        try {
            Image logoImg = new Image(new java.io.FileInputStream("imagenes/WhatsApp Image 2026-05-23 at 7.45.54 AM.jpeg"));
            ImageView logoView = new ImageView(logoImg);
            logoView.setFitWidth(300);
            logoView.setPreserveRatio(true);
            logoBox.getChildren().add(logoView);
        } catch (Exception e) {
            Label logoText = new Label("🏥");
            logoText.setFont(Font.font(60));
            Label title = new Label("Hospital Universitario\nSan Rafael de Tunja");
            title.setFont(Font.font("Arial Bold", 20));
            title.setStyle("-fx-text-fill: #1a5f7a; -fx-text-alignment: center;");
            logoBox.getChildren().addAll(logoText, title);
        }

        VBox buttonBox = new VBox(14);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(30, 0, 0, 0));

        buttonBox.getChildren().addAll(
            createMenuButton("Gestion Estudiantes", "#2C3E8F"),
            createMenuButton("Gestion Doctores", "#27AE60"),
            createMenuButton("Materias", "#8E44AD"),
            createMenuButton("Horarios", "#E74C3C"),
            createMenuButton("Registros", "#E67E22")
        );

        HBox logoutBox = new HBox();
        logoutBox.setAlignment(Pos.BOTTOM_RIGHT);
        logoutBox.setPadding(new Insets(30, 0, 0, 0));
        VBox.setVgrow(logoutBox, Priority.ALWAYS);

        Button logoutBtn = new Button("Cerrar Sesión");
        logoutBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #e74c3c; -fx-font-size: 13px; -fx-cursor: hand; -fx-border-color: #e74c3c; -fx-border-radius: 8; -fx-padding: 8 20;");
        logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 13px; -fx-cursor: hand; -fx-border-radius: 8; -fx-padding: 8 20;"));
        logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #e74c3c; -fx-font-size: 13px; -fx-cursor: hand; -fx-border-color: #e74c3c; -fx-border-radius: 8; -fx-padding: 8 20;"));
        logoutBtn.setOnAction(e -> {
            if (mainController != null) mainController.navigateTo("login");
        });
        logoutBox.getChildren().add(logoutBtn);

        panel.getChildren().addAll(logoBox, buttonBox, logoutBox);
        return panel;
    }

    private Button createMenuButton(String text, String color) {
        Button btn = new Button(text);
        btn.setPrefSize(320, 60);
        btn.setFont(Font.font("Arial", FontWeight.NORMAL, 17));

        btn.setStyle(
            "-fx-background-color: #e8e8e8;" +
            "-fx-text-fill: #2c2c2c;" +
            "-fx-border-radius: 30;" +
            "-fx-background-radius: 30;" +
            "-fx-font-size: 17px;" +
            "-fx-cursor: hand;"
        );

        btn.setOnMouseEntered(e -> {
            btn.setStyle(
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-border-radius: 30;" +
                "-fx-background-radius: 30;" +
                "-fx-font-size: 17px;" +
                "-fx-cursor: hand;"
            );
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle(
                "-fx-background-color: #e8e8e8;" +
                "-fx-text-fill: #2c2c2c;" +
                "-fx-border-radius: 30;" +
                "-fx-background-radius: 30;" +
                "-fx-font-size: 17px;" +
                "-fx-cursor: hand;"
            );
        });

        btn.setOnAction(e -> {
            String navAction = text.toLowerCase();
            if (text.equals("Gestion Estudiantes")) navAction = "gestión estudiantes";
            else if (text.equals("Gestion Doctores")) navAction = "gestión doctores";
            else if (text.equals("Registros")) navAction = "registros";
            else if (text.equals("Materias")) navAction = "materias";
            else if (text.equals("Horarios")) navAction = "horarios";
            if (mainController != null) mainController.navigateTo(navAction);
        });

        return btn;
    }

    private ImageView createHospitalImage() {
        ImageView hospitalView = new ImageView();
        try {
            Image hospitalImage = new Image(getClass().getResourceAsStream("/imagenes/hospital.jpeg"));
            if (hospitalImage == null || hospitalImage.isError() || hospitalImage.getWidth() == 0) {
                throw new Exception("Resource image error");
            }
            hospitalView = new ImageView(hospitalImage);
        } catch (Exception e) {
            try {
                Image hospitalImage = new Image(new java.io.FileInputStream("src/main/resources/imagenes/hospital.jpeg"));
                hospitalView = new ImageView(hospitalImage);
            } catch (Exception ex) {
                try {
                    Image hospitalImage = new Image(new java.io.FileInputStream("imagenes/WhatsApp Image 2026-05-23 at 7.45.51 AM.jpeg"));
                    hospitalView = new ImageView(hospitalImage);
                } catch (Exception ex2) {
                    System.err.println("No se encontró la imagen del hospital");
                    return new ImageView();
                }
            }
        }

        hospitalView.setPreserveRatio(true);
        hospitalView.setSmooth(true);
        return hospitalView;
    }
}
