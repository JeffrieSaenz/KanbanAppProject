package com.example.user.kanbanapp;

import java.util.ArrayList;

public class Tab {
    private String title;
    private int pos;
    private ArrayList<Tarea> tareas;

    public Tab() {
    }

    public Tab(String title, int pos, ArrayList<Tarea> tareas) {
        this.title = title;
        this.pos = pos;
        this.tareas = (tareas == null) ? new ArrayList<Tarea>() : tareas;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    @Override
    public String toString() {
        return "Tab{" +
                "title='" + title + '\'' +
                ", pos=" + pos +
                ", tareas=" + tareas +
                '}';
    }
}
