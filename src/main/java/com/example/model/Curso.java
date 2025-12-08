package com.example.model;

public class Curso {

    private String nombre;
    private String descripcion;
    private int creditos;
    private int codigo;
    private int id_profesor;
    private String horario;
    private String aula;
    private int capacidad;
    private String id_materias;

    public Curso (String nombre, String descripcion, int creditos, int codigo, int id_profesor, String horario, String aula, int capacidad, String id_materias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.codigo = codigo;
        this.id_profesor = id_profesor;
        this.horario = horario;
        this.aula = aula;
        this.capacidad = capacidad;
        this.id_materias = id_materias;
    }

    // Getters and Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; } 

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    
    public int getId_profesor() { return id_profesor; }
    public void setId_profesor(int id_profesor) { this.id_profesor = id_profesor; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    
    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getId_materias() { return id_materias; }
    public void setId_materias(String id_materias) { this.id_materias = id_materias; }

}
