package com.hospital.sanrafael;

import com.hospital.sanrafael.controller.MainController;
import com.hospital.sanrafael.view.FXViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            MainController mainController = new MainController(new FXViewFactory());
            mainController.initialize(primaryStage);
            primaryStage.setTitle("Hospital San Rafael - Sistema de Gestión");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
