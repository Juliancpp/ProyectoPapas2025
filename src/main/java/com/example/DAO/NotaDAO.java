package com.example.DAO;

import com.example.model.Nota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO extends MySQLDataHelper {

    private static final String TABLE = "notas";

    private Nota map(ResultSet rs) throws SQLException {
        return new Nota(
                rs.getInt("id"),
                rs.getInt("estudiante_id"),
                rs.getInt("materia_id"),
                rs.getString("tipo"),
                rs.getDouble("valor"),
                rs.getDouble("peso")
        );
    }

    public void insertar(Nota nota) throws Exception {
        String sql = "INSERT INTO " + TABLE +
                " (estudiante_id, materia_id, tipo, valor, peso) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, nota.getEstudianteId());
            stmt.setInt(2, nota.getMateriaId());
            stmt.setString(3, nota.getTipo());
            stmt.setDouble(4, nota.getValor());
            stmt.setDouble(5, nota.getPeso());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) nota.setId(rs.getInt(1));
        }
    }

    public void actualizar(Nota nota) throws Exception {
        String sql = "UPDATE " + TABLE +
                " SET tipo = ?, valor = ?, peso = ? WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nota.getTipo());
            stmt.setDouble(2, nota.getValor());
            stmt.setDouble(3, nota.getPeso());
            stmt.setInt(4, nota.getId());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Nota obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);
        }
        return null;
    }

    public List<Nota> obtenerNotasDeEstudianteMateria(int estudianteId, int materiaId) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE estudiante_id = ? AND materia_id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estudianteId);
            stmt.setInt(2, materiaId);

            ResultSet rs = stmt.executeQuery();

            List<Nota> lista = new ArrayList<>();
            while (rs.next()) lista.add(map(rs));
            return lista;
        }
    }

    public List<Nota> obtenerTodos() throws Exception {
        String sql = "SELECT * FROM " + TABLE;

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Nota> lista = new ArrayList<>();
            while (rs.next()) lista.add(map(rs));

            return lista;
        }
    }
}
