package com.example.model;

public class Curso {

    private String nombre;
    private String descripcion;
    private int creditos;
    private String codigo;

    public Curso() {}

    public Curso(String nombre, String descripcion, int creditos, String codigo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
