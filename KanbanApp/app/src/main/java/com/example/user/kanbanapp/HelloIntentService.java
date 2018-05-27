package com.example.user.kanbanapp;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HelloIntentService extends IntentService {

    ArrayList<Tarea> tareas;
    Timer timer = new Timer();
    Tab tab;
    Date fechaHoy;
    int cont = 0;
    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */




    public HelloIntentService() {

        super("HelloIntentService");
    tareas = new ArrayList<>();
        readTabs();
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        try {
            //validarFechas();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                        validarFechas();
                    }else{
                        tareas.clear();
                        Thread.currentThread().interrupt();
                    }
                }
            }, 5*1000, 5*1000);
        } catch (Exception e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }

    public void readTabs() {

        DatabaseReference mtabs = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                tareas.clear();
               tab = dataSnapshot.getValue(Tab.class);//Detecta la tabla en que hice el cambio

                if(tab.getTareas() != null) {
                    for (Tarea t : tab.getTareas()) {
                        if (!tareas.contains(t)) {
                            tareas.add(t);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                tareas.clear();
                tab = dataSnapshot.getValue(Tab.class); //Detecta la tabla en que hice el cambio
                if(tab.getTareas() != null) {
                    for (Tarea t : tab.getTareas()) {
                        if (!tareas.contains(t)) {
                            tareas.add(t);
                        }
                    }
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Mensaje("Remove");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                //Mensaje("Moved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Mensaje("Cancelled");
            }
        };
        mtabs.addChildEventListener(childEventListener);

    }

    public void validarFechas(){
        fechaHoy = new Date();
        for (Tarea t : tareas){
            if(fechaHoy.after(t.getFecha())){
                System.out.println("La fecha "+ t.getFecha() +" está vencida");
                Notificacion.creaNotificacion(cont++,"ALERTA: Tarea vencida!",
                        t.getNombre()+"",
                        "http://es.stackoverflow.com", getApplicationContext());

            }
        }

    }


}