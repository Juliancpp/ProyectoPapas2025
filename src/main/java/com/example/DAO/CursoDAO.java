package com.example.DAO;

import com.example.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO extends MySQLDataHelper implements IGenericDAO<Curso, String> {

    private Curso map(ResultSet rs) throws SQLException {
        return new Curso(
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getInt("creditos"),
                rs.getString("codigo")
        );
    }

    @Override
    public void insertar(Curso curso) throws Exception {
        String sql = "INSERT INTO cursos (codigo, nombre, descripcion, creditos) VALUES (?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, curso.getCodigo());
            ps.setString(2, curso.getNombre());
            ps.setString(3, curso.getDescripcion());
            ps.setInt(4, curso.getCreditos());

            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Curso curso) throws Exception {
        String sql = "UPDATE cursos SET nombre=?, descripcion=?, creditos=? WHERE codigo=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, curso.getNombre());
            ps.setString(2, curso.getDescripcion());
            ps.setInt(3, curso.getCreditos());
            ps.setString(4, curso.getCodigo());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(String codigo) throws Exception {
        String sql = "DELETE FROM cursos WHERE codigo=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.executeUpdate();
        }
    }

    @Override
    public Curso obtenerPorId(String codigo) throws Exception {
        String sql = "SELECT * FROM cursos WHERE codigo=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<Curso> obtenerTodos() throws Exception {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(map(rs));
            }
        }

        return lista;
    }

    @Override
    public boolean existe(String codigo) throws Exception {
        String sql = "SELECT 1 FROM cursos WHERE codigo=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        }
    }

    @Override
    public long contar() throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM cursos";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong("total");
            }
        }
        return 0;
    }

    @Override
    public void insertarBatch(List<Curso> cursos) throws Exception {
        String sql = "INSERT INTO cursos (codigo, nombre, descripcion, creditos) VALUES (?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Curso curso : cursos) {
                ps.setString(1, curso.getCodigo());
                ps.setString(2, curso.getNombre());
                ps.setString(3, curso.getDescripcion());
                ps.setInt(4, curso.getCreditos());
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    @Override
    public void eliminarBatch(List<String> codigos) throws Exception {
        String sql = "DELETE FROM cursos WHERE codigo=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String codigo : codigos) {
                ps.setString(1, codigo);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public List<Curso> buscarPorCampo(String campo, Object valor) throws Exception {
        String sql = "SELECT * FROM cursos WHERE " + campo + "=?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, valor);
            ResultSet rs = ps.executeQuery();

            List<Curso> lista = new ArrayList<>();
            while (rs.next()) lista.add(map(rs));

            return lista;
        }
    }

    @Override
    public List<Curso> buscarLike(String campo, String patron) throws Exception {
        String sql = "SELECT * FROM cursos WHERE " + campo + " LIKE ?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + patron + "%");
            ResultSet rs = ps.executeQuery();

            List<Curso> lista = new ArrayList<>();
            while (rs.next()) lista.add(map(rs));

            return lista;
        }
    }

    @Override
    public List<Curso> obtenerPagina(int offset, int limite) throws Exception {
        String sql = "SELECT * FROM cursos LIMIT ? OFFSET ?";

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();
            List<Curso> lista = new ArrayList<>();

            while (rs.next()) lista.add(map(rs));

            return lista;
        }
    }
}
