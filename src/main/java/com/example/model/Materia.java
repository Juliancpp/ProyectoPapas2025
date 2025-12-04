package com.example.model;

public class Materia {

    private int id;
    private String nombre;
    private String codigo;
    private String profesor;
    private int cursoId;

    public Materia() {}

    public Materia(String nombre, String codigo, String profesor, int cursoId) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.profesor = profesor;
        this.cursoId = cursoId;
    }

    public Materia(int id, String nombre, String codigo, String profesor, int cursoId) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.profesor = profesor;
        this.cursoId = cursoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public String toString() {
        return nombre + " (" + codigo + ")";
    }
}
