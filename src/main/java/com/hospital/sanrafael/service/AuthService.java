package com.hospital.sanrafael.service;

import com.hospital.sanrafael.dao.GenericDAO;
import com.hospital.sanrafael.model.Usuario;
import java.util.List;

public class AuthService {
    private static final String USERS_FILE = "usuarios.dat";
    private final GenericDAO<Usuario> usuarioDAO;

    public AuthService() {
        this.usuarioDAO = new GenericDAO<>(USERS_FILE, Usuario.class);
        crearAdminPorDefecto();
    }

    private void crearAdminPorDefecto() {
        List<Usuario> usuarios = usuarioDAO.getAll();
        if (usuarios.isEmpty()) {
            usuarioDAO.save(new Usuario("admin", "admin@hospital.com", "admin123", "Administrador Hospital", "Administrador"));
        }
    }

    public Usuario login(String username, String password) {
        List<Usuario> usuarios = usuarioDAO.getAll();
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public boolean register(String username, String email, String password, String nombreCompleto, String rol) {
        List<Usuario> usuarios = usuarioDAO.getAll();
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                return false;
            }
        }
        Usuario nuevo = new Usuario(username, email, password, nombreCompleto, rol);
        usuarioDAO.save(nuevo);
        return true;
    }

    public boolean usernameExists(String username) {
        List<Usuario> usuarios = usuarioDAO.getAll();
        return usuarios.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public Usuario getUsuario(String username) {
        List<Usuario> usuarios = usuarioDAO.getAll();
        return usuarios.stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst()
            .orElse(null);
    }
}
