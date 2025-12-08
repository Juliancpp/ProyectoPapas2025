package com.example.service;

import com.example.DAO.NotaDAO;
import com.example.model.Nota;

import java.util.List;

public class NotaService {

    private NotaDAO notaDAO;

    public NotaService(NotaDAO notaDAO) {
        this.notaDAO = notaDAO;
    }

    public boolean agregarNota(Nota nota) {
        return notaDAO.insertarNota(nota);
    }

    public List<Nota> obtenerNotasPorEstudiante(int estudianteId) {
        return notaDAO.obtenerNotasPorEstudiante(estudianteId);
    }

    public List<Nota> obtenerNotasPorCurso(int cursoCodigo) {
        return notaDAO.obtenerNotasPorCurso(cursoCodigo);
    }

    public boolean actualizarNota(Nota nota) {
        return notaDAO.actualizarNota(nota);
    }

    public boolean eliminarNota(int estudianteId, int cursoCodigo, String tipo) {
        return notaDAO.eliminarNota(estudianteId, cursoCodigo, tipo);
    }

    public double calcularNotaFinal(List<Nota> notas) {
        double suma = 0;

        for (Nota n : notas) {
            suma += n.getValor() * n.getPeso();
        }

        return suma;
    }
}
