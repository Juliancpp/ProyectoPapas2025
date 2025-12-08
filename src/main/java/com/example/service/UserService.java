package com.example.service;

import com.example.DAO.UserDAO;
import com.example.model.Rol;
import com.example.model.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void registrarUsuario(String nombre, String correo, String password, Rol rol) throws Exception {

        if (userDAO.obtenerPorCorreo(correo) != null) {
            throw new Exception("Ya existe un usuario con este correo.");
        }

        User nuevo = new User(nombre, correo, password, rol.name());
        userDAO.insertar(nuevo);
    }

    public User login(String correo, String password) throws Exception {
        User user = userDAO.obtenerPorCorreo(correo);

        if (user == null) {
            throw new Exception("Correo incorrecto.");
        }

        if (!user.getPassword().equals(password)) {
            throw new Exception("Contraseña incorrecta.");
        }

        return user;
    }

    public User obtenerUsuario(int id) throws Exception {
        return userDAO.obtenerPorId(id);
    }

    public List<User> listarUsuarios() throws Exception {
        return userDAO.obtenerTodos();
    }

    public void eliminarUsuario(int id) throws Exception {
        if (!userDAO.existe(id)) {
            throw new Exception("El usuario no existe.");
        }
        userDAO.eliminar(id);
    }

    public void actualizarUsuario(User user) throws Exception {
        userDAO.actualizar(user);
    }
}
