package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.view.ViewFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
    private final ViewFactory viewFactory;
    private Stage primaryStage;
    private MainMenuController mainMenuController;

    public MainController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }

    public void initialize(Stage stage) {
        this.primaryStage = stage;
        
        mainMenuController = new MainMenuController(viewFactory);
        mainMenuController.setMainController(this);
        
        Scene scene = new Scene(mainMenuController.getView(), 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hospital San Rafael - Sistema de Gestión");
        
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
    }

    public void navigateTo(String view) {
        System.out.println("Navegando a vista: " + view);
        Scene scene = null;
        
        try {
            switch (view) {
                case "estudiantes":
                    EstudianteController estudianteController = new EstudianteController(viewFactory);
                    estudianteController.setMainController(this);
                    scene = new Scene(estudianteController.getView(), 1200, 700);
                    break;
                case "doctores":
                    DoctorController doctorController = new DoctorController(viewFactory);
                    doctorController.setMainController(this);
                    scene = new Scene(doctorController.getView(), 1200, 700);
                    break;
                case "materias":
                    MateriasController materiasController = new MateriasController(viewFactory);
                    materiasController.setMainController(this);
                    scene = new Scene(materiasController.getView(), 1200, 700);
                    break;
                case "horario":
                    HorarioController horarioController = new HorarioController(viewFactory);
                    horarioController.setMainController(this);
                    scene = new Scene(horarioController.getView(), 1200, 700);
                    break;
                case "registro":
                    RegistroController registroController = new RegistroController(viewFactory);
                    registroController.setMainController(this);
                    scene = new Scene(registroController.getView(), 1200, 700);
                    break;
                case "main":
                default:
                    mainMenuController = new MainMenuController(viewFactory);
                    mainMenuController.setMainController(this);
                    scene = new Scene(mainMenuController.getView(), 1200, 700);
            }
            
            if (scene != null) {
                primaryStage.setScene(scene);
                scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al navegar a: " + view);
        }
    }
}
