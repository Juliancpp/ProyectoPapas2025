package com.example.model;

public class Inscripcion {
    private int id;
    private int estudianteId;
    private int materiaId;

    public Inscripcion() {}

    public Inscripcion(int id, int estudianteId, int materiaId) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.materiaId = materiaId;
    }

    public Inscripcion(int estudianteId, int materiaId) {
        this.estudianteId = estudianteId;
        this.materiaId = materiaId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }

    public int getMateriaId() { return materiaId; }
    public void setMateriaId(int materiaId) { this.materiaId = materiaId; }
}
