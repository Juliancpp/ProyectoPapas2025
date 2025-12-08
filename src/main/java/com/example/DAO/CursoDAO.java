package com.example.DAO;

import com.example.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO extends MySQLDataHelper {

    private static final String TABLE = "cursos";

    public void insertar(Curso c) throws Exception {
        String sql = "INSERT INTO " + TABLE +
                " (nombre, descripcion, creditos, codigo, id_profesor, horario, aula, capacidad, id_materias) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getDescripcion());
            stmt.setInt(3, c.getCreditos());
            stmt.setInt(4, c.getCodigo());
            stmt.setInt(5, c.getId_profesor());
            stmt.setString(6, c.getHorario());
            stmt.setString(7, c.getAula());
            stmt.setInt(8, c.getCapacidad());
            stmt.setString(9, c.getId_materias());

            stmt.executeUpdate();
        }
    }

    public void actualizar(Curso c) throws Exception {
        String sql = "UPDATE " + TABLE + " SET nombre=?, descripcion=?, creditos=?, id_profesor=?, horario=?, aula=?, capacidad=?, id_materias=? WHERE codigo=?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getDescripcion());
            stmt.setInt(3, c.getCreditos());
            stmt.setInt(4, c.getId_profesor());
            stmt.setString(5, c.getHorario());
            stmt.setString(6, c.getAula());
            stmt.setInt(7, c.getCapacidad());
            stmt.setString(8, c.getId_materias());
            stmt.setInt(9, c.getCodigo());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int codigo) throws Exception {
        String sql = "DELETE FROM " + TABLE + " WHERE codigo = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }

    public Curso obtenerPorCodigo(int codigo) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE codigo = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapCurso(rs);
            }
        }
        return null;
    }

    public List<Curso> obtenerTodos() throws Exception {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE;

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapCurso(rs));
            }
        }
        return lista;
    }

    public List<Curso> obtenerCursosPorMateria(String idMateria) throws Exception {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos WHERE id_materias = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapCurso(rs));
            }
        }
        return lista;
    }

    private Curso mapCurso(ResultSet rs) throws Exception {
        return new Curso(
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getInt("creditos"),
                rs.getInt("codigo"),
                rs.getInt("id_profesor"),
                rs.getString("horario"),
                rs.getString("aula"),
                rs.getInt("capacidad"),
                rs.getString("id_materias")
        );
    }

    public boolean reducirCupo(int cursoCodigo) throws Exception {
        String sql = "UPDATE cursos SET capacidad = capacidad - 1 WHERE codigo = ? AND capacidad > 0";

        try (Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cursoCodigo);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            throw new Exception("Error al reducir cupo del curso " + cursoCodigo + ": " + e.getMessage(), e);
        }
    }

    public boolean aumentarCupo(int cursoCodigo) throws Exception {
        String sql = "UPDATE cursos SET capacidad = capacidad + 1 WHERE codigo = ?";

        try (Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cursoCodigo);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            throw new Exception("Error al aumentar cupo del curso " + cursoCodigo + ": " + e.getMessage(), e);
        }
    }
}
