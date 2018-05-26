package com.example.user.kanbanapp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 10/04/2018.
 */

public class Tarea {

    private String nombre;
    private String descripcion;
    private int prioridad;
    private ArrayList<File> listFiles;
    private Date fecha;

    public Tarea(String n, String d, ArrayList<File> a, Date f){
        this.nombre = n;
        this.descripcion = d;
        this.prioridad = 0;
        this.listFiles = a;
        this.fecha = f;
    }

    public Tarea() {
    }

    public ArrayList<File> getListFiles() {
        return (listFiles != null) ? listFiles : new ArrayList<File>();
    }

    public void setListFiles(ArrayList<File> listFiles) {
        this.listFiles = listFiles;
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
