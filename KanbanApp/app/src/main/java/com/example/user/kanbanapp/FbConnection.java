package com.example.user.kanbanapp;

import android.content.Intent;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class FbConnection {
    private DatabaseReference mDatabase;
    private ArrayList<Tab> tabs;
    private boolean aux = false;
    private DatosVentanas dv;
    private static FbConnection instance = null;

    public static FbConnection getInstance() {
        if (instance == null) {
            instance = new FbConnection();
        }
        return instance;
    }

    public FbConnection() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dv = DatosVentanas.getInstance();
    }

    public ArrayList<Tab> getTabs() {
        return tabs;
    }

    public boolean isAux() {
        return aux;
    }

    public void setAux(boolean aux) {
        this.aux = aux;
    }

    public void setTabs(ArrayList<Tab> tabs) {
        this.tabs = tabs;
    }

    public void addFirebase(String a1, String a2) {
        Tarea t = new Tarea(a1, a2,new ArrayList<>(),new Date());
        mDatabase.child("tareas").child(a1).setValue(t);
    }

    public void addTabs(Tab tab){
        mDatabase.child(dv.getUserlogged().getCorreo().split("@")[0]).child(String.valueOf(tab.getPos())).setValue(tab);
    }

    public void addImageURL(String a1,String a2,String a3,String a4){
        mDatabase.child("url").child(a1).child(a2).child(a3).setValue(a4);
    }

}
