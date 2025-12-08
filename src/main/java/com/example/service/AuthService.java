package com.example.service;

import com.example.DAO.UserDAO;
import com.example.model.User;

import java.util.regex.Pattern;

public class AuthService {

    private final UserDAO userDAO;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User login(String correo, String password) throws Exception {
        User user = userDAO.obtenerPorCorreo(correo);

        if (user == null) {
            throw new Exception("El usuario no existe.");
        }

        if (!user.getPassword().equals(password)) {
            throw new Exception("Contraseña incorrecta.");
        }

        return user;
    }

    public void registrar(String nombre, String correo, String password, String rol) throws Exception {

        if (!EMAIL_PATTERN.matcher(correo).matches()) {
            throw new Exception("El correo no tiene un formato válido.");
        }

        User existente = userDAO.obtenerPorCorreo(correo);
        if (existente != null) {
            throw new Exception("El correo ya está registrado.");
        }

        User nuevo = new User(nombre, correo, password, rol);
        userDAO.insertar(nuevo);
    }
}
