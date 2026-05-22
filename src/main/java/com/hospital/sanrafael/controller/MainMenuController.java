package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.service.DatosInicialesService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
        VBox mainContainer = new VBox(25);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(40));
        
        Background gradient = new Background(new BackgroundFill(
            new javafx.scene.paint.LinearGradient(0, 0, 1, 1, true, 
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0, Color.valueOf("#667eea")),
                new javafx.scene.paint.Stop(1, Color.valueOf("#764ba2"))),
            CornerRadii.EMPTY, Insets.EMPTY));
        
        mainContainer.setBackground(gradient);

        VBox header = createHeader();
        
        GridPane menuGrid = new GridPane();
        menuGrid.setHgap(25);
        menuGrid.setVgap(25);
        menuGrid.setAlignment(Pos.CENTER);
        menuGrid.setPadding(new Insets(30));

        menuGrid.add(createMenuButton("Gestión de Estudiantes", "estudiantes", "🎓", "#3498db"), 0, 0);
        menuGrid.add(createMenuButton("Gestión de Doctores", "doctores", "👨‍⚕️", "#e74c3c"), 1, 0);
        
        menuGrid.add(createMenuButton("Materias", "materias", "📚", "#27ae60"), 0, 1);
        menuGrid.add(createMenuButton("Horarios", "horario", "📅", "#f39c12"), 1, 1);
        
        menuGrid.add(createMenuButton("Registros", "registro", "📝", "#9b59b6"), 0, 2);

        mainContainer.getChildren().addAll(header, menuGrid);
        return mainContainer;
    }

    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        
        Circle logo = new Circle(50);
        logo.setFill(Color.WHITE);
        logo.setStroke(Color.valueOf("#2c3e50"));
        logo.setStrokeWidth(3);
        
        Label logoLabel = new Label("✚");
        logoLabel.setFont(new Font("Arial Bold", 60));
        logoLabel.setStyle("-fx-text-fill: #2c3e50;");
        
        Label titleLabel = new Label("Hospital San Rafael");
        titleLabel.setFont(Font.font("Arial Bold", 36));
        titleLabel.setStyle("-fx-text-fill: white; -fx-effect: dropshadow(gaussian, black, 10, 0, 0, 2);");
        
        Label subtitleLabel = new Label("Sistema de Gestión Académica");
        subtitleLabel.setFont(Font.font("Arial", 18));
        subtitleLabel.setStyle("-fx-text-fill: #ecf0f1;");
        
        header.getChildren().addAll(logoLabel, titleLabel, subtitleLabel);
        return header;
    }

    private Button createMenuButton(String text, String action, String icon, String color) {
        Button button = new Button(icon + "  " + text);
        button.setPrefSize(220, 80);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setRadius(10);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        button.setEffect(shadow);

        button.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;"
        );

        button.setOnMouseEntered(e -> {
            button.setStyle(
                "-fx-background-color: derive(" + color + ", 80%);" +
                "-fx-text-fill: white;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;"
            );
            button.setScaleX(1.05);
            button.setScaleY(1.05);
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;"
            );
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        button.setOnAction(e -> {
            System.out.println("Navegando a: " + action);
            if (mainController != null) {
                mainController.navigateTo(action);
            } else {
                System.out.println("Error: mainController es null");
            }
        });

        return button;
    }
}
