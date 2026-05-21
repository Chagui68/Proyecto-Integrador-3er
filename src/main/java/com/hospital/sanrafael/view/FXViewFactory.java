package com.hospital.sanrafael.view;

import com.hospital.sanrafael.controller.*;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class FXViewFactory implements ViewFactory {
    
    @Override
    public Pane createMainMenuView() {
        MainMenuController controller = new MainMenuController(this);
        return controller.getView();
    }

    @Override
    public Scene createEstudianteScene() {
        EstudianteController controller = new EstudianteController(this);
        return new Scene(controller.getView(), 900, 600);
    }

    @Override
    public Scene createDoctorScene() {
        DoctorController controller = new DoctorController(this);
        return new Scene(controller.getView(), 900, 600);
    }

    @Override
    public Scene createMateriasScene() {
        MateriasController controller = new MateriasController(this);
        return new Scene(controller.getView(), 900, 600);
    }

    @Override
    public Scene createHorarioScene() {
        HorarioController controller = new HorarioController(this);
        return new Scene(controller.getView(), 900, 600);
    }

    @Override
    public Scene createRegistroEscena() {
        RegistroController controller = new RegistroController(this);
        return new Scene(controller.getView(), 900, 600);
    }
}
