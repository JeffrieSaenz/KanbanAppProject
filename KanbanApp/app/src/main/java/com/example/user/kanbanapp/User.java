package com.example.user.kanbanapp;

public class User {

    private String nombre;
    private String correo;

    public User(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public User(){
        this.correo = "";
        this.nombre = "";
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
}
