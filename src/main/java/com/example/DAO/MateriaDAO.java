package com.example.DAO;

import com.example.model.Materia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO extends MySQLDataHelper implements IGenericDAO<Materia , Integer> {

    private static final String TABLE = "materias";

    @Override
    public Materia obtenerPorId (Integer id) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        try (Connection conn = MySQLDataHelper.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToMateria(rs);
            }
            return null;
        }
    }

    @Override
    public List<Materia> obtenerTodos () throws Exception {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE;

        try (Connection conn = MySQLDataHelper.openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                materias.add(mapResultSetToMateria(rs));
            }
        }

        return materias;
    }

    @Override
    public void insertar (Materia materia) throws Exception {
        String sql = "INSERT INTO " + TABLE + " (nombre, codigo, profesor, curso_id) VALUES (?,?,?,?)";

        try (Connection conn = MySQLDataHelper.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, materia.getNombre());
            stmt.setString(2, materia.getCodigo());
            stmt.setString(3, materia.getProfesor());
            stmt.setInt(4, materia.getCursoId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar (Materia materia) throws Exception {
        String sql = "UPDATE " + TABLE + " SET nombre=?, codigo=?, profesor=?, curso_id=? WHERE id=?";

        try (Connection conn = MySQLDataHelper.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, materia.getNombre());
            stmt.setString(2, materia.getCodigo());
            stmt.setString(3, materia.getProfesor());
            stmt.setInt(4, materia.getCursoId());
            stmt.setInt(5, materia.getId());
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

    public List<Materia> findByCurso(int cursoId) throws Exception {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " WHERE curso_id = ?";

        try (Connection conn = MySQLDataHelper.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cursoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                materias.add(mapResultSetToMateria(rs));
            }
        }

        return materias;
    }



    private Materia mapResultSetToMateria(ResultSet rs) throws SQLException {
        return new Materia(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("codigo"),
                rs.getString("profesor"),
                rs.getInt("curso_id")
        );
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
    public void insertarBatch(List<Materia> materias) throws Exception {
        String sql = "INSERT INTO " + TABLE + " (nombre, codigo, profesor, curso_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Materia m : materias) {
                stmt.setString(1, m.getNombre());
                stmt.setString(2, m.getCodigo());
                stmt.setString(3, m.getProfesor());
                stmt.setInt(4, m.getCursoId());
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

            for (Integer id : ids) {
                stmt.setInt(1, id);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public List<Materia> buscarPorCampo(String campo, Object valor) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE " + campo + " = ?";

        try (Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, valor);
            ResultSet rs = stmt.executeQuery();

            List<Materia> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapResultSetToMateria(rs));

            return lista;
        }
    }

    @Override
    public List<Materia> buscarLike(String campo, String patron) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " WHERE " + campo + " LIKE ?";

        try (Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + patron + "%");
            ResultSet rs = stmt.executeQuery();

            List<Materia> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapResultSetToMateria(rs));

            return lista;
        }
    }

    @Override
    public List<Materia> obtenerPagina(int offset, int limite) throws Exception {
        String sql = "SELECT * FROM " + TABLE + " LIMIT ? OFFSET ?";

        try (Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limite);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            List<Materia> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapResultSetToMateria(rs));

            return lista;
        }
    }
}
