package com.hospital.sanrafael.service;

import com.hospital.sanrafael.dao.GenericDAO;
import com.hospital.sanrafael.dao.PostgreUserDAO;
import com.hospital.sanrafael.database.DatabaseConnection;
import com.hospital.sanrafael.model.User;
import java.util.List;

public class AuthService {
    private static final String USERS_FILE = "usuarios.dat";
    private final GenericDAO<User> fileDAO;
    private final PostgreUserDAO dbDAO;
    private final boolean useDatabase;

    public AuthService() {
        this.fileDAO = new GenericDAO<>(USERS_FILE, User.class);
        this.dbDAO = new PostgreUserDAO();
        this.useDatabase = DatabaseConnection.testConnection();
        createDefaultAdmin();
    }

    private void createDefaultAdmin() {
        List<User> users = getAllUsers();
        if (users.isEmpty()) {
            User admin = new User("admin", "admin@hospital.com", PasswordUtils.hashPassword("admin123"), "Hospital Administrator", "Administrador");
            if (useDatabase) {
                dbDAO.save(admin);
            } else {
                fileDAO.save(admin);
            }
        }
    }

    public User login(String username, String password) {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getUsername().equals(username) && PasswordUtils.verifyPassword(password, u.getPassword())) {
                return u;
            }
        }
        return null;
    }

    public boolean register(String username, String email, String password, String fullName, String role) {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false;
            }
        }
        User newUser = new User(username, email, PasswordUtils.hashPassword(password), fullName, role);
        if (useDatabase) {
            dbDAO.save(newUser);
        } else {
            fileDAO.save(newUser);
        }
        return true;
    }

    public boolean usernameExists(String username) {
        List<User> users = getAllUsers();
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public User getUser(String username) {
        if (useDatabase) {
            return dbDAO.getByUsername(username);
        }
        List<User> users = fileDAO.getAll();
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    private List<User> getAllUsers() {
        return useDatabase ? dbDAO.getAll() : fileDAO.getAll();
    }
}
