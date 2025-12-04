package com.example.model;

public class User {
    private int id;
    private String nombre;
    private String correo;
    private String passwordHash;
    private String rol;

    public User() {}

    public User(int id, String nombre, String correo, String passwordHash, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public User(String nombre, String correo, String passwordHash, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
