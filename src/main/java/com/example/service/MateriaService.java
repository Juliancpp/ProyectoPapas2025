package com.example.service;

import com.example.DAO.MateriaDAO;
import com.example.model.Materia;

import java.util.List;

public class MateriaService {

    private final MateriaDAO materiaDAO;

    public MateriaService() {
        this.materiaDAO = new MateriaDAO();
    }

    public boolean crearMateria(Materia materia) throws Exception {

        List<Materia> existentes = materiaDAO.buscarPorCampo("codigo", materia.getCodigo());
        if (!existentes.isEmpty()) {
            System.out.println("⚠ Ya existe una materia con ese código.");
            return false;
        }

        materiaDAO.insertar(materia);
        return true;
    }

    public boolean actualizarMateria(Materia materia) throws Exception {
        if (!materiaDAO.existe(materia.getId())) {
            System.out.println("⚠ La materia no existe.");
            return false;
        }

        materiaDAO.actualizar(materia);
        return true;
    }

    public boolean eliminarMateria(int id) throws Exception {
        if (!materiaDAO.existe(id)) {
            System.out.println("⚠ La materia no existe.");
            return false;
        }

        materiaDAO.eliminar(id);
        return true;
    }

    public Materia obtenerMateria(int id) throws Exception {
        return materiaDAO.obtenerPorId(id);
    }

    public List<Materia> obtenerTodas() throws Exception {
        return materiaDAO.obtenerTodos();
    }

    public List<Materia> buscarPorNombre(String nombre) throws Exception {
        return materiaDAO.buscarLike("nombre", nombre);
    }

    public List<Materia> obtenerMateriasPorCurso(int cursoId) throws Exception {
        return materiaDAO.findByCurso(cursoId);
    }

    public List<Materia> obtenerMateriasPorProfesor(String profesor) throws Exception {
        return materiaDAO.buscarPorCampo("profesor", profesor);
    }

}
