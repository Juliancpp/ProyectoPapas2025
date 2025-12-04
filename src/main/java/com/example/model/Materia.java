package com.example.model;

public class Materia {
    private int id;
    private String nombre;
    private int profesorId;

    public Materia() {}

    public Materia(int id, String nombre, int profesorId) {
        this.id = id;
        this.nombre = nombre;
        this.profesorId = profesorId;
    }

    public Materia(String nombre, int profesorId) {
        this.nombre = nombre;
        this.profesorId = profesorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getProfesorId() { return profesorId; }
    public void setProfesorId(int profesorId) { this.profesorId = profesorId; }
}
