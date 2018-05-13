package com.example.user.kanbanapp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 10/04/2018.
 */

public class DatosVentanas {

    private ArrayList<Tab> tabs = new ArrayList<>();
    Date reminder;

    private static DatosVentanas instance = null;


    protected DatosVentanas() {
        // agregaInicial();

    }

    /*public void agregaInicial() {
        this.tabs.add(new ArrayList<Tarea>());
    }*/

    public static DatosVentanas getInstance() {
        if (instance == null) {
            instance = new DatosVentanas();
        }
        return instance;
    }

    public ArrayList<Tab> getBacklog() {
        return tabs;
    }
    public Tab getTab(int pos){
        for(Tab t: tabs)
            if(pos == t.getPos()) return t;
        return null;
    }
    public void setBacklog(ArrayList<Tab> backlog) {
        this.tabs = backlog;
    }

    public void agregarTareaBacklog(Tarea a, Integer i) {

        for(Tab t : tabs)
            if(t.getPos() == i) {
                if(t.getTareas() == null)
                    t.setTareas(new ArrayList<Tarea>());
                t.getTareas().add(a);
            }
    }

    public void setDate(Date d)
    {
        this.reminder = d;
    }
}// fin de la clase de variables globales