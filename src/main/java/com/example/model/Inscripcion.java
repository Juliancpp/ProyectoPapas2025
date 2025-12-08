package com.example.model;

import java.time.LocalDate;

public class Inscripcion {

    private int id;
    private int estudianteId;
    private int cursoCodigo;
    private LocalDate fecha;

    public Inscripcion() {}

    public Inscripcion(int estudianteId, int cursoCodigo, LocalDate fecha) {
        this.estudianteId = estudianteId;
        this.cursoCodigo = cursoCodigo;
        this.fecha = fecha;
    }

    public Inscripcion(int id, int estudianteId, int cursoCodigo, LocalDate fecha) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.cursoCodigo = cursoCodigo;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }

    public int getCursoCodigo() { return cursoCodigo; }
    public void setCursoCodigo(int cursoCodigo) { this.cursoCodigo = cursoCodigo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
