package com.example.model;

public class Nota {
    private int id;
    private int estudianteId;
    private int materiaId;
    private String tipo;
    private double valor; 
    private double peso;  

    public Nota() {}

    public Nota(int estudianteId, int materiaId, String tipo, double valor, double peso) {
        this.estudianteId = estudianteId;
        this.materiaId = materiaId;
        this.tipo = tipo;
        this.valor = valor;
        this.peso = peso;
    }

    public Nota(int id, int estudianteId, int materiaId, String tipo, double valor, double peso) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.materiaId = materiaId;
        this.tipo = tipo;
        this.valor = valor;
        this.peso = peso;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }

    public int getMateriaId() { return materiaId; }
    public void setMateriaId(int materiaId) { this.materiaId = materiaId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
}
