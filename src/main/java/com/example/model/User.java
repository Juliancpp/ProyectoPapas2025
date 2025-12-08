package com.example.model;

public class User {

    private int id;
    private String nombre;
    private String correo;
    private String password;
    private Rol rol;

    public User(String nombre, String correo, String password, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = Rol.valueOf(rol.toUpperCase());
    }

    public User(int id, String nombre, String correo, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = Rol.valueOf(rol.toUpperCase());
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

    public String getCorreo() { 
        return correo; 
    }

    public void setCorreo(String correo) { 
        this.correo = correo; 
    }

    public String getPassword() { 
        return password; 
    }

    public void setPassword(String password) { 
        this.password = password; 
    }

    public Rol getRol() { 
        return rol; 
    }

    public void setRol(Rol rol) { 
        this.rol = rol; 
    }

    public void setRol(String rol) {
        this.rol = Rol.valueOf(rol.toUpperCase());
    }
}
