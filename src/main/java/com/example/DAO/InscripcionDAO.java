package com.example.DAO;

import com.example.model.Inscripcion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO extends MySQLDataHelper {

    public void insertar(Inscripcion inscripcion) throws Exception {
        String sql = "INSERT INTO inscripciones (estudiante_id, curso_codigo, fecha) VALUES (?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, inscripcion.getEstudianteId());
            stmt.setInt(2, inscripcion.getCursoCodigo());
            stmt.setDate(3, Date.valueOf(inscripcion.getFecha()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                inscripcion.setId(rs.getInt(1));
            }
        }
    }

    public void eliminar(int estudianteId, int cursoCodigo) throws Exception {
    String sql = "DELETE FROM inscripciones WHERE estudiante_id = ? AND curso_codigo = ?";
    try (Connection conn = openConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, estudianteId);
        stmt.setInt(2, cursoCodigo);
        stmt.executeUpdate();

    } catch (SQLException e) {
        throw new Exception("Error al eliminar la inscripción: " + e.getMessage(), e);
    }
}


    public boolean estaInscrito(int estudianteId, int cursoCodigo) throws Exception {
        String sql = "SELECT id FROM inscripciones WHERE estudiante_id = ? AND curso_codigo = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estudianteId);
            stmt.setInt(2, cursoCodigo);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public List<Inscripcion> listarPorEstudiante(int estudianteId) throws Exception {
        List<Inscripcion> lista = new ArrayList<>();

        String sql = "SELECT id, estudiante_id, curso_codigo, fecha FROM inscripciones WHERE estudiante_id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estudianteId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Inscripcion ins = new Inscripcion(
                        rs.getInt("id"),
                        rs.getInt("estudiante_id"),
                        rs.getInt("curso_codigo"),
                        rs.getDate("fecha").toLocalDate()
                );
                lista.add(ins);
            }
        }

        return lista;
    }

    public List<Inscripcion> listarPorCurso(int cursoCodigo) throws Exception {
        List<Inscripcion> lista = new ArrayList<>();

        String sql = "SELECT id, estudiante_id, curso_codigo, fecha FROM inscripciones WHERE curso_codigo = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cursoCodigo);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Inscripcion ins = new Inscripcion(
                        rs.getInt("id"),
                        rs.getInt("estudiante_id"),
                        rs.getInt("curso_codigo"),
                        rs.getDate("fecha").toLocalDate()
                );
                lista.add(ins);
            }
        }

        return lista;
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM inscripciones WHERE id = ?";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
