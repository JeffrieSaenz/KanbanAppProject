package com.example.user.kanbanapp;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;

/**
 * Created by User on 10/04/2018.
 */

public class Tarea {

    private String nombre;
    private String descripcion;
    private int prioridad;
    private ArrayList<Image> imagenes;
    private Date fecha;

    public Tarea(String n, String d, ArrayList<Image> a, Date f){
        this.nombre = n;
        this.descripcion = d;
        this.prioridad = 0;
        this.imagenes = a;
        this.fecha = f;
    }

    public Tarea() {
    }

    public ArrayList<Image> getImagenes() {
        return (imagenes != null) ? imagenes : new ArrayList<Image>();
    }

    public void setImagenes(ArrayList<Image> imagenes) {
        this.imagenes = imagenes;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
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

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
