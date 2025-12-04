package com.example.service;

import com.example.DAO.InscripcionDAO;
import com.example.DAO.UserDAO;
import com.example.model.Inscripcion;
import com.example.model.User;

import java.time.LocalDate;
import java.util.List;

public class InscripcionService {

    private final InscripcionDAO inscripcionDAO;
    private final UserDAO userDAO;

    public InscripcionService() {
        this.inscripcionDAO = new InscripcionDAO();
        this.userDAO = new UserDAO();
    }

    public boolean inscribirEstudiante(int estudianteId, String cursoCodigo) throws Exception {

        User user = userDAO.obtenerPorId(estudianteId);

        if (user == null) {
            System.out.println("El usuario no existe.");
            return false;
        }

        if (!user.getRol().equalsIgnoreCase("ESTUDIANTE")) {
            System.out.println("Solo los estudiantes pueden inscribirse.");
            return false;
        }

        Inscripcion existente = inscripcionDAO.obtenerPorEstudianteYCurso(estudianteId, cursoCodigo);
        if (existente != null) {
            System.out.println("Ya estás inscrito en este curso.");
            return false;
        }

        Inscripcion nueva = new Inscripcion(estudianteId, cursoCodigo, LocalDate.now());
        inscripcionDAO.insertar(nueva);

        return true;
    }

    public boolean inscribirProfesorAEstudiante(int profesorId, int estudianteId, String cursoCodigo) throws Exception {

        User profesor = userDAO.obtenerPorId(profesorId);
        if (profesor == null || !profesor.getRol().equalsIgnoreCase("PROFESOR")) {
            System.out.println("Este usuario no es un profesor.");
            return false;
        }

        User estudiante = userDAO.obtenerPorId(estudianteId);
        if (estudiante == null || !estudiante.getRol().equalsIgnoreCase("ESTUDIANTE")) {
            System.out.println("El usuario indicado no es un estudiante.");
            return false;
        }

        Inscripcion existente = inscripcionDAO.obtenerPorEstudianteYCurso(estudianteId, cursoCodigo);
        if (existente != null) {
            System.out.println("El estudiante ya está inscrito en ese curso.");
            return false;
        }

        Inscripcion nueva = new Inscripcion(estudianteId, cursoCodigo, LocalDate.now());
        inscripcionDAO.insertar(nueva);

        return true;
    }

    public List<Inscripcion> obtenerInscripcionesPorEstudiante(int estudianteId) throws Exception {
        return inscripcionDAO.obtenerPorEstudiante(estudianteId);
    }

    public List<Inscripcion> obtenerInscripcionesPorCurso(String cursoCodigo) throws Exception {
        return inscripcionDAO.obtenerPorCurso(cursoCodigo);
    }

    public boolean eliminarInscripcion(int id) throws Exception {
        inscripcionDAO.eliminar(id);
        return true;
    }
}
