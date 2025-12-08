package com.example.service;

import com.example.DAO.CursoDAO;
import com.example.model.Curso;
import com.example.model.User;

import java.util.List;

public class CursoService {

    private final CursoDAO cursoDAO = new CursoDAO();

    public void crearCurso(Curso curso, User usuarioLogueado) throws Exception {

        if (usuarioLogueado.getRol() != com.example.model.Rol.PROFESOR) {
            throw new Exception("Solo un profesor puede crear cursos.");
        }

        Curso existente = cursoDAO.obtenerPorCodigo(curso.getCodigo());
        if (existente != null) {
            throw new Exception("El código del curso ya existe.");
        }

        cursoDAO.insertar(curso);
    }

    public void actualizarCurso(Curso curso, User usuarioLogueado) throws Exception {
        if (usuarioLogueado.getRol() != com.example.model.Rol.PROFESOR) {
            throw new Exception("Solo un profesor puede editar cursos.");
        }

        cursoDAO.actualizar(curso);
    }

    public void eliminarCurso(int codigo, User usuarioLogueado) throws Exception {
        if (usuarioLogueado.getRol() != com.example.model.Rol.PROFESOR) {
            throw new Exception("Solo un profesor puede eliminar cursos.");
        }

        cursoDAO.eliminar(codigo);
    }

    public Curso obtenerCurso(int codigo) throws Exception {
        return cursoDAO.obtenerPorCodigo(codigo);
    }

    public List<Curso> obtenerTodos() throws Exception {
        return cursoDAO.obtenerTodos();
    }

    public List<Curso> obtenerCursosPorMateria(String materia) throws Exception {
        return cursoDAO.obtenerCursosPorMateria(materia);
    }
}
