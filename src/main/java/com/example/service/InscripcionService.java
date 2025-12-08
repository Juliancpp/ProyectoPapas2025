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

    /**
     * Inscribe a un estudiante en un curso.
     * Reglas:
     *  - Solo estudiantes pueden inscribirse.
     *  - No se puede inscribir dos veces al mismo curso.
     *  - Debe haber cupos disponibles.
     */
    public void inscribirEstudiante(int estudianteId, int cursoCodigo) throws Exception {

        // 1. Validar que el estudiante existe
        User estudiante = userDAO.obtenerPorId(estudianteId);
        if (estudiante == null) {
            throw new Exception("El estudiante no existe.");
        }

        // 2. Validar rol
        if (estudiante.getRol() != com.example.model.Rol.ESTUDIANTE) {
            throw new Exception("Solo los estudiantes pueden inscribirse en cursos.");
        }

        // 3. Verificar que el curso exista y obtener datos actualizados desde la BD
        Curso curso = cursoDAO.obtenerPorCodigo(cursoCodigo);
        if (curso == null) {
            throw new Exception("El curso con código " + cursoCodigo + " no existe.");
        }

        // 4. Revisar si ya está inscrito
        if (inscripcionDAO.estaInscrito(estudianteId, cursoCodigo)) {
            throw new Exception("El estudiante ya está inscrito en este curso.");
        }

        // 5. Verificar cupos actuales desde la BD
        if (curso.getCapacidad() <= 0) {
            throw new Exception("No hay cupos disponibles para este curso.");
        }

        // 6. Crear la inscripción
        Inscripcion inscripcion = new Inscripcion(estudianteId, cursoCodigo, LocalDate.now());
        inscripcionDAO.insertar(inscripcion);

        // 7. Reducir cupo en la BD
        cursoDAO.reducirCupo(cursoCodigo);
    }

        /**
     * Permite que un estudiante se retire de un curso.
     * Reglas:
     *  - Solo estudiantes pueden retirarse.
     *  - Debe estar inscrito en ese curso.
     *  - Se devuelve el cupo al curso.
     */
    public void retirarEstudianteDeCurso(int estudianteId, int cursoCodigo) throws Exception {

        // 1. Validar estudiante
        User estudiante = userDAO.obtenerPorId(estudianteId);
        if (estudiante == null) {
            throw new Exception("El estudiante no existe.");
        }

        if (estudiante.getRol() != com.example.model.Rol.ESTUDIANTE) {
            throw new Exception("Solo un estudiante puede retirarse de un curso.");
        }

        // 2. Validar que el curso existe
        Curso curso = cursoDAO.obtenerPorCodigo(cursoCodigo);
        if (curso == null) {
            throw new Exception("El curso con código " + cursoCodigo + " no existe.");
        }

        // 3. Validar que realmente está inscrito
        if (!inscripcionDAO.estaInscrito(estudianteId, cursoCodigo)) {
            throw new Exception("El estudiante no está inscrito en este curso.");
        }

        // 4. Eliminar inscripción
        inscripcionDAO.eliminar(estudianteId, cursoCodigo);

        // 5. Aumentar cupo en BD
        cursoDAO.aumentarCupo(cursoCodigo);
    }


    /**
     * Método solo para PROFESORES: reservar cupo para un estudiante específico.
     */
    public void reservarCupoComoProfesor(int profesorId, int estudianteId, int cursoCodigo) throws Exception {

        // 1. Verificar que el profesor existe y es profesor
        User profesor = userDAO.obtenerPorId(profesorId);
        if (profesor == null || profesor.getRol() != com.example.model.Rol.PROFESOR) {
            throw new Exception("Solo un profesor puede reservar cupos.");
        }

        // 2. Simplemente reutilizamos la misma lógica de inscripción
        inscribirEstudiante(estudianteId, cursoCodigo);
    }

    public List<Inscripcion> obtenerInscripcionesDeEstudiante(int estudianteId) throws Exception {
        return inscripcionDAO.listarPorEstudiante(estudianteId);
    }
}
