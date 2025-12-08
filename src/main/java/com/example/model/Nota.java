package com.example.model;

public class Nota {

    private int estudianteId;
    private int cursoCodigo;
    private String tipo;
    private double valor; 
    private double peso;  

    public Nota(int estudianteId, int cursoCodigo, String tipo, double valor, double peso) {
        this.estudianteId = estudianteId;
        this.cursoCodigo = cursoCodigo;
        this.tipo = tipo;
        this.valor = valor;
        this.peso = peso;
    }

    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }

    public int getCursoCodigo() { return cursoCodigo; }
    public void setCursoCodigo(int cursoCodigo) { this.cursoCodigo = cursoCodigo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
}
