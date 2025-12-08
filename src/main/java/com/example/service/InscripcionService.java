package com.example.service;

import com.example.DAO.CursoDAO;
import com.example.DAO.InscripcionDAO;
import com.example.DAO.UserDAO;
import com.example.model.Curso;
import com.example.model.Inscripcion;
import com.example.model.User;

import java.time.LocalDate;
import java.util.List;

public class InscripcionService {

    private final InscripcionDAO inscripcionDAO;
    private final CursoDAO cursoDAO;
    private final UserDAO userDAO;

    public InscripcionService() {
        this.inscripcionDAO = new InscripcionDAO();
        this.cursoDAO = new CursoDAO();
        this.userDAO = new UserDAO();
    }

    public void inscribirEstudiante(int estudianteId, int cursoCodigo) throws Exception {

        User estudiante = userDAO.obtenerPorId(estudianteId);
        if (estudiante == null) {
            throw new Exception("El estudiante no existe.");
        }

        if (estudiante.getRol() != com.example.model.Rol.ESTUDIANTE) {
            throw new Exception("Solo los estudiantes pueden inscribirse en cursos.");
        }

        Curso curso = cursoDAO.obtenerPorCodigo(cursoCodigo);
        if (curso == null) {
            throw new Exception("El curso con código " + cursoCodigo + " no existe.");
        }

        if (inscripcionDAO.estaInscrito(estudianteId, cursoCodigo)) {
            throw new Exception("El estudiante ya está inscrito en este curso.");
        }

        if (curso.getCapacidad() <= 0) {
            throw new Exception("No hay cupos disponibles para este curso.");
        }

        Inscripcion inscripcion = new Inscripcion(estudianteId, cursoCodigo, LocalDate.now());
        inscripcionDAO.insertar(inscripcion);

        cursoDAO.reducirCupo(cursoCodigo);
    }

    public void retirarEstudianteDeCurso(int estudianteId, int cursoCodigo) throws Exception {

        User estudiante = userDAO.obtenerPorId(estudianteId);
        if (estudiante == null) {
            throw new Exception("El estudiante no existe.");
        }

        if (estudiante.getRol() != com.example.model.Rol.ESTUDIANTE) {
            throw new Exception("Solo un estudiante puede retirarse de un curso.");
        }

        Curso curso = cursoDAO.obtenerPorCodigo(cursoCodigo);
        if (curso == null) {
            throw new Exception("El curso con código " + cursoCodigo + " no existe.");
        }

        if (!inscripcionDAO.estaInscrito(estudianteId, cursoCodigo)) {
            throw new Exception("El estudiante no está inscrito en este curso.");
        }

        inscripcionDAO.eliminar(estudianteId, cursoCodigo);

        cursoDAO.aumentarCupo(cursoCodigo);
    }

    public void reservarCupoComoProfesor(int profesorId, int estudianteId, int cursoCodigo) throws Exception {

        User profesor = userDAO.obtenerPorId(profesorId);
        if (profesor == null || profesor.getRol() != com.example.model.Rol.PROFESOR) {
            throw new Exception("Solo un profesor puede reservar cupos.");
        }

        inscribirEstudiante(estudianteId, cursoCodigo);
    }

    public List<Inscripcion> obtenerInscripcionesDeEstudiante(int estudianteId) throws Exception {
        return inscripcionDAO.listarPorEstudiante(estudianteId);
    }
}
