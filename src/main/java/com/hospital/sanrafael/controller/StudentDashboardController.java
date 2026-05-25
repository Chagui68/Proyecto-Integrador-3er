package com.hospital.sanrafael.controller;

import com.hospital.sanrafael.model.Notification;
import com.hospital.sanrafael.model.Student;
import com.hospital.sanrafael.service.NotificationService;
import com.hospital.sanrafael.service.StudentService;
import com.hospital.sanrafael.view.ViewFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.time.format.DateTimeFormatter;

public class StudentDashboardController extends BaseDashboardController {
private final StudentService studentService;
private final NotificationService notificationService;

// Campos del formulario
private TextField idField, firstNameField, lastNameField, emailField, phoneField;
private TextField birthDateField, genderField, addressField, careerField, semesterField, arlDateField;
private ComboBox<String> shiftField;

public StudentDashboardController(ViewFactory viewFactory) {
this.viewFactory = viewFactory;
this.studentService = new StudentService();
this.notificationService = NotificationService.getInstance();
}

    @Override
    protected String getSidebarColor() { return "#27AE60"; }
    
    @Override
    protected String getSidebarLogo() { return "MEDjestic"; }
    
    @Override
    protected String getSidebarLetter() { return "E"; }
    
    @Override
    protected String getModuleName() { return "Estudiante"; }
    
    @Override
    protected String getModuleRole() { return "Panel del Estudiante"; }
    
    @Override
    protected String getTitle() { 
        switch(currentSection) {
            case "profile": return "Modificar Datos";
            case "schedule": return "Mi Horario";
            case "view-notifications": return "Notificaciones";
            default: return "Panel del Estudiante";
        }
    }

    @Override
    protected VBox createSidebarMenuItems() {
        VBox menu = new VBox(5);
        menu.setPadding(new Insets(10, 0, 0, 0));
        
        boolean isProfile = currentSection.equals("profile");
        boolean isSchedule = currentSection.equals("schedule");
        boolean isViewNotif = currentSection.equals("view-notifications");
        
        Button profileBtn = sidebarBtn("📝 Modificar Datos", isProfile);
        Button scheduleBtn = sidebarBtn("📅 Mi Horario", isSchedule);
        Button viewNotifBtn = sidebarBtn("🔔 Ver Notificaciones", isViewNotif);
        
        profileBtn.setOnAction(e -> { currentSection = "profile"; refreshContent(); });
        scheduleBtn.setOnAction(e -> { currentSection = "schedule"; refreshContent(); });
        viewNotifBtn.setOnAction(e -> { currentSection = "view-notifications"; refreshContent(); });
        
        menu.getChildren().addAll(profileBtn, scheduleBtn, viewNotifBtn);
        return menu;
    }

    @Override
    protected VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(25));
        content.setStyle("-fx-background-color: #f0f4f8;");
        
        switch(currentSection) {
            case "profile":
                content.getChildren().add(createProfileSection());
                break;
            case "schedule":
                content.getChildren().add(createScheduleSection());
                break;
            case "view-notifications":
                content.getChildren().add(createViewNotificationsSection());
                break;
            default:
                content.getChildren().add(createProfileSection());
        }
        
        return content;
    }
    
    private VBox createProfileSection() {
        VBox section = new VBox(15);
        section.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");
        
        Label title = new Label("Modificar Datos Personales");
        title.setFont(Font.font("Arial Bold", 16));
        title.setStyle("-fx-text-fill: #2c3e50;");
        
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);
        
        idField = createField();
        firstNameField = createField();
        lastNameField = createField();
        emailField = createField();
        phoneField = createField();
        birthDateField = createField();
        genderField = createField();
        addressField = createField();
        careerField = createField();
        semesterField = createField();
        shiftField = new ComboBox<>();
        shiftField.getItems().addAll("Mañana", "Tarde", "Noche");
        arlDateField = createField();
        
        int r = 0;
        grid.add(fieldLabel("ID:"), 0, r); grid.add(idField, 1, r++);
        grid.add(fieldLabel("Nombres:"), 0, r); grid.add(firstNameField, 1, r++);
        grid.add(fieldLabel("Apellidos:"), 0, r); grid.add(lastNameField, 1, r++);
        grid.add(fieldLabel("Email:"), 0, r); grid.add(emailField, 1, r++);
        grid.add(fieldLabel("Teléfono:"), 0, r); grid.add(phoneField, 1, r++);
        grid.add(fieldLabel("Fecha Nacimiento:"), 0, r); grid.add(birthDateField, 1, r++);
        grid.add(fieldLabel("Género:"), 0, r); grid.add(genderField, 1, r++);
        grid.add(fieldLabel("Dirección:"), 0, r); grid.add(addressField, 1, r++);
        grid.add(fieldLabel("Carrera:"), 0, r); grid.add(careerField, 1, r++);
        grid.add(fieldLabel("Semestre:"), 0, r); grid.add(semesterField, 1, r++);
        grid.add(fieldLabel("Turno:"), 0, r); grid.add(shiftField, 1, r++);
        grid.add(fieldLabel("Vencimiento ARL:"), 0, r); grid.add(arlDateField, 1, r++);
        
        loadCurrentStudent();
        
        HBox buttons = new HBox(12);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10, 0, 0, 0));
        
        Button saveBtn = actionBtn("Guardar Cambios", "#27AE60");
        Button cancelBtn = actionBtn("Cancelar", "#95A5A6");
        
        saveBtn.setOnAction(e -> saveChanges());
        cancelBtn.setOnAction(e -> loadCurrentStudent());
        
        buttons.getChildren().addAll(saveBtn, cancelBtn);
        section.getChildren().addAll(title, grid, buttons);
        
        return section;
    }
    
    private VBox createScheduleSection() {
        VBox section = new VBox(15);
        section.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");
        
        Label title = new Label("Mi Horario de Clases");
        title.setFont(Font.font("Arial Bold", 16));
        title.setStyle("-fx-text-fill: #2c3e50;");
        
        Label scheduleText = new Label("Lunes a Viernes: 8:00 AM - 12:00 PM\nTurno: Mañana\nCarrera: Medicina");
        scheduleText.setFont(Font.font("Arial", 13));
        scheduleText.setStyle("-fx-text-fill: #555; -fx-line-height: 1.6;");
        
        section.getChildren().addAll(title, scheduleText);
        return section;
    }
    
    private Student findCurrentStudent() {
        String userEmail = mainController != null && mainController.getCurrentUser() != null ?
                mainController.getCurrentUser().getEmail() : null;
        if (userEmail == null) {
            var students = studentService.getAllStudents();
            return students.isEmpty() ? null : students.get(0);
        }
        for (var s : studentService.getAllStudents()) {
            if (userEmail.equalsIgnoreCase(s.getEmail())) {
                return s;
            }
        }
        var students = studentService.getAllStudents();
        return students.isEmpty() ? null : students.get(0);
    }

    private void loadCurrentStudent() {
        var currentStudent = findCurrentStudent();
        
        if (currentStudent != null) {
            idField.setText(currentStudent.getId());
            firstNameField.setText(currentStudent.getFirstName());
            lastNameField.setText(currentStudent.getLastName());
            emailField.setText(currentStudent.getEmail());
            phoneField.setText(currentStudent.getPhone());
            birthDateField.setText(currentStudent.getBirthDate());
            genderField.setText(currentStudent.getGender());
            addressField.setText(currentStudent.getAddress());
            careerField.setText(currentStudent.getCareer());
            semesterField.setText(String.valueOf(currentStudent.getSemester()));
            shiftField.setValue(currentStudent.getShift() != null ? currentStudent.getShift().getDisplayName() : "");
            arlDateField.setText(currentStudent.getArlExpirationDate() != null ? 
                currentStudent.getArlExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "");
        }
    }
    
    private void saveChanges() {
        var currentStudent = findCurrentStudent();
        
        if (currentStudent != null) {
            try {
                currentStudent.setFirstName(firstNameField.getText().trim());
                currentStudent.setLastName(lastNameField.getText().trim());
                currentStudent.setEmail(emailField.getText().trim());
                currentStudent.setPhone(phoneField.getText().trim());
                currentStudent.setBirthDate(birthDateField.getText().trim());
                currentStudent.setGender(genderField.getText().trim());
                currentStudent.setAddress(addressField.getText().trim());
                currentStudent.setCareer(careerField.getText().trim());
                currentStudent.setSemester(Integer.parseInt(semesterField.getText().trim()));
                
                studentService.updateStudent(currentStudent);
                show("Éxito", "Datos actualizados correctamente");
            } catch (Exception e) {
                show("Error", "No se pudo guardar: " + e.getMessage());
            }
        }
    }
    
    private VBox createViewNotificationsSection() {
        VBox section = new VBox(15);
        section.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);");
        
        Label title = new Label("Bandeja de Notificaciones");
        title.setFont(Font.font("Arial Bold", 16));
        title.setStyle("-fx-text-fill: #2c3e50;");
        
        Student currentStudent = findCurrentStudent();
        String currentStudentId = currentStudent != null ? currentStudent.getId() : null;
        
        var allNotifications = notificationService.getAllNotifications();
        var notifications = allNotifications.stream()
                .filter(n -> currentStudentId == null || currentStudentId.equals(n.getPersonId()) || n.getPersonId() == null)
                .toList();
        
        if (notifications.isEmpty()) {
            Label noNotif = new Label("No hay notificaciones para ti");
            noNotif.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
            section.getChildren().addAll(title, noNotif);
        } else {
            Label info = new Label("Tus notificaciones: " + notifications.size());
            info.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13px;");
            
            VBox notifList = new VBox(10);
            notifList.setPadding(new Insets(10, 0, 0, 0));
            
            for (var notif : notifications) {
                VBox notifBox = new VBox(5);
                notifBox.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8; -fx-padding: 12; -fx-border-color: #ddd; -fx-border-radius: 8;");
                
                Label typeLabel = new Label("[" + notif.getType() + "] " + notif.getMessage());
                typeLabel.setFont(Font.font("Arial Bold", 12));
                typeLabel.setStyle("-fx-text-fill: #2c3e50;");
                
                Label detailLabel = new Label(notif.getDetails() != null ? notif.getDetails() : "");
                detailLabel.setFont(Font.font("Arial", 11));
                detailLabel.setStyle("-fx-text-fill: #555;");
                
                Label dateLabel = new Label("Fecha: " + notif.getDate());
                dateLabel.setFont(Font.font("Arial", 10));
                dateLabel.setStyle("-fx-text-fill: #999;");
                
                notifBox.getChildren().addAll(typeLabel, detailLabel, dateLabel);
                notifList.getChildren().add(notifBox);
            }
            
            section.getChildren().addAll(title, info, notifList);
        }
        
        return section;
    }
}
