package com.hospital.sanrafael;

import com.hospital.sanrafael.controller.MainController;
import com.hospital.sanrafael.database.DatabaseConnection;
import com.hospital.sanrafael.view.FXViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            if (DatabaseConnection.testConnection()) {
                System.out.println("✅ Conexión a PostgreSQL exitosa");
            } else {
                System.out.println("⚠️  No se pudo conectar a PostgreSQL");
                System.out.println("   La aplicación usará almacenamiento local (archivos .dat)");
            }
            
            MainController mainController = new MainController(new FXViewFactory());
            mainController.initialize(primaryStage);
            primaryStage.setTitle("Hospital San Rafael - Sistema de Gestión");
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
            
            // Si falla todo, mostrar mensaje y continuar con almacenamiento local
            System.out.println("\n⚠️  Iniciando en modo offline (sin base de datos)");
            try {
                MainController mainController = new MainController(new FXViewFactory());
                mainController.initialize(primaryStage);
                primaryStage.setTitle("Hospital San Rafael - Modo Offline");
                primaryStage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
