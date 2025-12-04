package com.example.model;

public class Nota {
    private int id;
    private int estudianteId;
    private int materiaId;
    private double nota1;
    private double nota2;
    private double notaFinal;

    public Nota() {}

    public Nota(int id, int estudianteId, int materiaId, double nota1, double nota2, double notaFinal) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.materiaId = materiaId;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.notaFinal = notaFinal;
    }

    public Nota(int estudianteId, int materiaId, double nota1, double nota2, double notaFinal) {
        this.estudianteId = estudianteId;
        this.materiaId = materiaId;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.notaFinal = notaFinal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }

    public int getMateriaId() { return materiaId; }
    public void setMateriaId(int materiaId) { this.materiaId = materiaId; }

    public double getNota1() { return nota1; }
    public void setNota1(double nota1) { this.nota1 = nota1; }

    public double getNota2() { return nota2; }
    public void setNota2(double nota2) { this.nota2 = nota2; }

    public double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(double notaFinal) { this.notaFinal = notaFinal; }
}
