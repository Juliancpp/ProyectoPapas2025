package com.example.DAO;

import com.example.model.Inscripcion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO extends MySQLDataHelper implements IGenericDAO<Inscripcion, Integer> {

    private Inscripcion map(ResultSet rs) throws SQLException {
        return new Inscripcion(
                rs.getInt("id"),
                rs.getInt("estudiante_id"),
                rs.getString("curso_codigo"),
                rs.getDate("fecha").toLocalDate()
        );
    }

    @Override
    public void insertar(Inscripcion inscripcion) throws Exception {
        String sql = "INSERT INTO inscripciones (estudiante_id, curso_codigo, fecha) VALUES (?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inscripcion.getEstudianteId());
            ps.setString(2, inscripcion.getCursoCodigo());
            ps.setDate(3, Date.valueOf(inscripcion.getFecha()));

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                inscripcion.setId(keys.getInt(1));
            }
        }
    }

    @Override
    public void actualizar(Inscripcion inscripcion) throws Exception {
        String sql = "UPDATE inscripciones SET estudiante_id=?, curso_codigo=?, fecha=? WHERE id=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, inscripcion.getEstudianteId());
            ps.setString(2, inscripcion.getCursoCodigo());
            ps.setDate(3, Date.valueOf(inscripcion.getFecha()));
            ps.setInt(4, inscripcion.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        String sql = "DELETE FROM inscripciones WHERE id=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Inscripcion obtenerPorId(Integer id) throws Exception {
        String sql = "SELECT * FROM inscripciones WHERE id=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return map(rs);
        }
        return null;
    }

    @Override
    public List<Inscripcion> obtenerTodos() throws Exception {
        List<Inscripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM inscripciones";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(map(rs));
        }

        return lista;
    }

    @Override
    public boolean existe(Integer id) throws Exception {
        String sql = "SELECT 1 FROM inscripciones WHERE id=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        }
    }

    @Override
    public long contar() throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM inscripciones";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getLong("total");
        }
        return 0;
    }

    @Override
    public void insertarBatch(List<Inscripcion> inscripciones) throws Exception {
        String sql = "INSERT INTO inscripciones (estudiante_id, curso_codigo, fecha) VALUES (?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Inscripcion ins : inscripciones) {
                ps.setInt(1, ins.getEstudianteId());
                ps.setString(2, ins.getCursoCodigo());
                ps.setDate(3, Date.valueOf(ins.getFecha()));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public void eliminarBatch(List<Integer> ids) throws Exception {
        String sql = "DELETE FROM inscripciones WHERE id=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Integer id : ids) {
                ps.setInt(1, id);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public List<Inscripcion> buscarPorCampo(String campo, Object valor) throws Exception {
        String sql = "SELECT * FROM inscripciones WHERE " + campo + "=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, valor);

            ResultSet rs = ps.executeQuery();
            List<Inscripcion> lista = new ArrayList<>();

            while (rs.next()) lista.add(map(rs));

            return lista;
        }
    }

    @Override
    public List<Inscripcion> buscarLike(String campo, String patron) throws Exception {
        String sql = "SELECT * FROM inscripciones WHERE " + campo + " LIKE ?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + patron + "%");
            ResultSet rs = ps.executeQuery();

            List<Inscripcion> lista = new ArrayList<>();
            while (rs.next()) lista.add(map(rs));

            return lista;
        }
    }

    @Override
    public List<Inscripcion> obtenerPagina(int offset, int limite) throws Exception {
        String sql = "SELECT * FROM inscripciones LIMIT ? OFFSET ?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();
            List<Inscripcion> resultado = new ArrayList<>();

            while (rs.next()) resultado.add(map(rs));

            return resultado;
        }
    }

    public List<Inscripcion> obtenerPorEstudiante(int estudianteId) throws Exception {
        return buscarPorCampo("estudiante_id", estudianteId);
    }

    public List<Inscripcion> obtenerPorCurso(String cursoCodigo) throws Exception {
        return buscarPorCampo("curso_codigo", cursoCodigo);
    }

    public Inscripcion obtenerPorEstudianteYCurso(int estudianteId, String cursoCodigo) throws Exception {
        String sql = "SELECT * FROM inscripciones WHERE estudiante_id=? AND curso_codigo=?";

        try (Connection conn = openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, estudianteId);
            ps.setString(2, cursoCodigo);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }
}
