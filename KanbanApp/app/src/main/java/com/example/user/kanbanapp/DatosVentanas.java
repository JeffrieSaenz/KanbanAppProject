package com.example.user.kanbanapp;

import java.util.ArrayList;

/**
 * Created by User on 10/04/2018.
 */

public class DatosVentanas {

    private ArrayList<Tarea> backlog = new ArrayList<>();

    private static DatosVentanas instance = null;

    protected DatosVentanas() {}

    public static DatosVentanas getInstance() {
        if(instance == null) {instance = new DatosVentanas(); }
        return instance;
    }

    public ArrayList<Tarea> getBacklog() {
        return backlog;
    }

    public void setBacklog(ArrayList<Tarea> backlog) {
        this.backlog = backlog;
    }

    public void agregarTareaBacklog(Tarea a){
        this.backlog.add(a);
    }
}// fin de la clase de variables globales