package com.example.DAO;

import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends MySQLDataHelper implements IGenericDAO<User, Integer> {

    private static final String TABLE = "users";

    private User map(ResultSet rs) throws Exception {
        return new User(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("password"),
                rs.getString("rol")
        );
    }

    @Override
    public void insertar(User u) throws Exception {
        String sql = "INSERT INTO " + TABLE + " (nombre, correo, password, rol) VALUES (?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getRol());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) u.setId(rs.getInt(1));
        }
    }

    @Override
    public void actualizar(User u) throws Exception {
        String sql = "UPDATE " + TABLE + " SET nombre = ?, correo = ?, password = ?, rol = ? WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getRol());
            stmt.setInt(5, u.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public User obtenerPorId(Integer id) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);
        }
        return null;
    }

    @Override
    public List<User> obtenerTodos() throws Exception {
        String sql = "SELECT * FROM " + TABLE;

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<User> lista = new ArrayList<>();
            while (rs.next()) lista.add(map(rs));
            return lista;
        }
    }

    @Override
    public boolean existe(Integer id) throws Exception {
        String sql = "SELECT 1 FROM " + TABLE + " WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    @Override
    public long contar() throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM " + TABLE;

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getLong("total");
        }
        return 0;
    }

    @Override
    public void insertarBatch(List<User> lista) throws Exception {
        String sql = "INSERT INTO " + TABLE + " (nombre, correo, password, rol) VALUES (?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (User u : lista) {
                stmt.setString(1, u.getNombre());
                stmt.setString(2, u.getCorreo());
                stmt.setString(3, u.getPassword());
                stmt.setString(4, u.getRol());
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    @Override
    public void eliminarBatch(List<Integer> ids) throws Exception {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int id : ids) {
                stmt.setInt(1, id);
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    @Override
    public List<User> buscarPorCampo(String campo, Object valor) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE " + campo + " = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, valor);

            ResultSet rs = stmt.executeQuery();
            List<User> lista = new ArrayList<>();

            while (rs.next()) lista.add(map(rs));
            return lista;
        }
    }

    @Override
    public List<User> buscarLike(String campo, String patron) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE " + campo + " LIKE ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + patron + "%");

            ResultSet rs = stmt.executeQuery();
            List<User> lista = new ArrayList<>();

            while (rs.next()) lista.add(map(rs));
            return lista;
        }
    }

    @Override
    public List<User> obtenerPagina(int offset, int limite) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " LIMIT ? OFFSET ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limite);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();
            List<User> lista = new ArrayList<>();

            while (rs.next()) lista.add(map(rs));
            return lista;
        }
    }

    public User login(String correo, String password) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE correo = ? AND password = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }
}
