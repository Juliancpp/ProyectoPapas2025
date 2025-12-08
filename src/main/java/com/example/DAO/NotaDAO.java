package com.example.DAO;

import com.example.model.Nota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    private Connection connection;

    public NotaDAO(Connection connection) {
        this.connection = connection;
    }

    // Crear una nueva nota
    public boolean insertarNota(Nota nota) {
        String sql = "INSERT INTO nota (estudiante_id, curso_codigo, tipo, valor, peso) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, nota.getEstudianteId());
            stmt.setInt(2, nota.getCursoCodigo());
            stmt.setString(3, nota.getTipo());
            stmt.setDouble(4, nota.getValor());
            stmt.setDouble(5, nota.getPeso());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener notas por estudiante
    public List<Nota> obtenerNotasPorEstudiante(int estudianteId) {
        String sql = "SELECT * FROM nota WHERE estudiante_id = ?";
        List<Nota> notas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, estudianteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                notas.add(new Nota(
                        rs.getInt("estudiante_id"),
                        rs.getInt("curso_codigo"),
                        rs.getString("tipo"),
                        rs.getDouble("valor"),
                        rs.getDouble("peso")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notas;
    }

    // Obtener notas por curso
    public List<Nota> obtenerNotasPorCurso(int cursoCodigo) {
        String sql = "SELECT * FROM nota WHERE curso_codigo = ?";
        List<Nota> notas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cursoCodigo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                notas.add(new Nota(
                        rs.getInt("estudiante_id"),
                        rs.getInt("curso_codigo"),
                        rs.getString("tipo"),
                        rs.getDouble("valor"),
                        rs.getDouble("peso")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notas;
    }

    // Actualizar una nota
    public boolean actualizarNota(Nota nota) {
        String sql = "UPDATE nota SET tipo=?, valor=?, peso=? WHERE estudiante_id=? AND curso_codigo=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nota.getTipo());
            stmt.setDouble(2, nota.getValor());
            stmt.setDouble(3, nota.getPeso());
            stmt.setInt(4, nota.getEstudianteId());
            stmt.setInt(5, nota.getCursoCodigo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar una nota
    public boolean eliminarNota(int estudianteId, int cursoCodigo, String tipo) {
        String sql = "DELETE FROM nota WHERE estudiante_id=? AND curso_codigo=? AND tipo=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, estudianteId);
            stmt.setInt(2, cursoCodigo);
            stmt.setString(3, tipo);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
