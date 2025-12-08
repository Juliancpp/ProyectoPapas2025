package com.example.DAO;

import com.example.model.Rol;
import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends MySQLDataHelper {

    public void insertar(User user) throws Exception {
        String sql = "INSERT INTO users (nombre, correo, password, rol) VALUES (?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getNombre());
            stmt.setString(2, user.getCorreo());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRol().name());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        }
    }

    public User obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }
            return null;
        }
    }

    public User obtenerPorCorreo(String correo) throws Exception {
        String sql = "SELECT * FROM users WHERE correo = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }
            return null;
        }
    }

    public List<User> obtenerTodos() throws Exception {
        String sql = "SELECT * FROM users";

        List<User> lista = new ArrayList<>();

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapUser(rs));
            }
        }

        return lista;
    }

    public void actualizar(User user) throws Exception {
        String sql = " UPDATE users SET nombre = ?, correo = ?, password = ?, rol = ? WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getNombre());
            stmt.setString(2, user.getCorreo());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRol().name());
            stmt.setInt(5, user.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean existe(int id) throws Exception {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt(1) > 0;
            return false;
        }
    }

    private User mapUser(ResultSet rs) throws Exception {
        return new User(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("password"),
                rs.getString("rol")
        );
    }
}
