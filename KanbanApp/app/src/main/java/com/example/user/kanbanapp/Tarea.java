package com.example.user.kanbanapp;

/**
 * Created by User on 10/04/2018.
 */

public class Tarea {

    private String nombre;
    private String descripcion;
    private int prioridad;

    public Tarea(String n, String d){
        this.nombre = n;
        this.descripcion = d;
        this.prioridad = 0;
    }
    public Tarea(){}
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

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
