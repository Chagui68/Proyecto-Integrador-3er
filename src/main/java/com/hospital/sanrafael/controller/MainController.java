package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.view.ViewFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
    private final ViewFactory viewFactory;
    private Stage primaryStage;

    public MainController(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }

    public void initialize(Stage stage) {
        this.primaryStage = stage;

        LoginController loginController = new LoginController(viewFactory);
        loginController.setMainController(this);

        Scene scene = new Scene(loginController.getView(), 1400, 750);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hospital San Rafael - Sistema de Gestión");
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
    }

    public void navigateTo(String view) {
        Scene scene = null;

        try {
            switch (view) {
                case "login":
                    LoginController loginController = new LoginController(viewFactory);
                    loginController.setMainController(this);
                    scene = new Scene(loginController.getView(), 1400, 750);
                    break;
                case "register":
                    RegisterController registerController = new RegisterController(viewFactory);
                    registerController.setMainController(this);
                    scene = new Scene(registerController.getView(), 1400, 750);
                    break;
                case "main":
                    MainMenuController mainMenuController = new MainMenuController(viewFactory);
                    mainMenuController.setMainController(this);
                    scene = new Scene(mainMenuController.getView(), 1400, 750);
                    break;
                case "gestión estudiantes":
                    EstudianteController estudianteController = new EstudianteController(viewFactory);
                    estudianteController.setMainController(this);
                    scene = new Scene(estudianteController.getView(), 1400, 750);
                    break;
                case "gestión doctores":
                    DoctorController doctorController = new DoctorController(viewFactory);
                    doctorController.setMainController(this);
                    scene = new Scene(doctorController.getView(), 1400, 750);
                    break;
                case "registros":
                    RegistroController registroController = new RegistroController(viewFactory);
                    registroController.setMainController(this);
                    scene = new Scene(registroController.getView(), 1400, 750);
                    break;
                case "materias":
                    MateriasController materiasController = new MateriasController(viewFactory);
                    materiasController.setMainController(this);
                    scene = new Scene(materiasController.getView(), 1400, 750);
                    break;
                case "horarios":
                    HorarioController horarioController = new HorarioController(viewFactory);
                    horarioController.setMainController(this);
                    scene = new Scene(horarioController.getView(), 1400, 750);
                    break;
                default:
                    MainMenuController mmc = new MainMenuController(viewFactory);
                    mmc.setMainController(this);
                    scene = new Scene(mmc.getView(), 1400, 750);
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
