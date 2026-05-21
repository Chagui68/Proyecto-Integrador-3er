package com.hospital.sanrafael.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public interface ViewFactory {
    Pane createMainMenuView();
    Scene createEstudianteScene();
    Scene createDoctorScene();
    Scene createMateriasScene();
    Scene createHorarioScene();
    Scene createRegistroEscena();
}
