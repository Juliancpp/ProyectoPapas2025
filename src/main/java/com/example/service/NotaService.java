package com.example.service;

import com.example.DAO.NotaDAO;
import com.example.model.Nota;

import java.util.List;

public class NotaService {

    private final NotaDAO notaDAO = new NotaDAO();

    // Agregar una nota (quiz, parcial, etc.)
    public void agregarNota(Nota nota) throws Exception {
        if (nota.getPeso() < 0 || nota.getPeso() > 1)
            throw new Exception("El peso debe ser entre 0 y 1.");

        if (nota.getValor() < 0 || nota.getValor() > 10)
            throw new Exception("La nota debe estar entre 0 y 10.");

        notaDAO.insertar(nota);
    }

    // Editar una nota ya existente
    public void editarNota(Nota nota) throws Exception {
        notaDAO.actualizar(nota);
    }

    // Eliminar una nota
    public void eliminarNota(int id) throws Exception {
        notaDAO.eliminar(id);
    }

    // Obtener todas las notas del estudiante en una materia
    public List<Nota> obtenerNotas(int estudianteId, int materiaId) throws Exception {
        return notaDAO.obtenerNotasDeEstudianteMateria(estudianteId, materiaId);
    }

    // Cálculo automático de nota final
    public double calcularNotaFinal(int estudianteId, int materiaId) throws Exception {
        List<Nota> notas = notaDAO.obtenerNotasDeEstudianteMateria(estudianteId, materiaId);

        double total = 0;
        for (Nota n : notas) {
            total += n.getValor() * n.getPeso();
        }

        return total;
    }

    // Validar que los pesos suman 1 (opcional)
    public boolean pesosCompletos(int estudianteId, int materiaId) throws Exception {
        List<Nota> notas = notaDAO.obtenerNotasDeEstudianteMateria(estudianteId, materiaId);

        double suma = 0;
        for (Nota n : notas) suma += n.getPeso();

        return Math.abs(suma - 1.0) < 0.0001;
    }
}
