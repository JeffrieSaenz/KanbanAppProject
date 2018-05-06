package com.example.user.kanbanapp;

import android.app.IntentService;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.view.View;
import android.widget.Toast;

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

    ArrayList<Date> fechas;
    Timer timer = new Timer();
    Tab tab;
    Date fechaHoy;
    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */




    public HelloIntentService() {

        super("HelloIntentService");
    fechas = new ArrayList<>();
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
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    validarFechas();
                }
            }, 5*1000, 5*1000);
        } catch (Exception e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }

    public void readTabs() {
        //tbs = new ArrayList<>();
        //ViewPagerAdapter vpa_db = new ViewPagerAdapter(getSupportFragmentManager());
        //DatosVentanas dv = DatosVentanas.getInstance();
        DatabaseReference mtabs = FirebaseDatabase.getInstance().getReference("tabs");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

               tab = dataSnapshot.getValue(Tab.class);

                //Mensaje("TAB: " + tab.getTitle());
                //tbs.add(tab);
                //Main_Content mc = new Main_Content();
                //mc.setPosicion(tab.getPos());
                //mc.setTareas(tab.getTareas());
                for( Tarea t: tab.getTareas()){
                    if(!fechas.contains(t.getFecha())) {
                        fechas.add(t.getFecha());
                    }
                }
                //vpa.addFragments(mc, tab.getTitle());
                //dv.getBacklog().add(tab);
                //vpa.notifyDataSetChanged();
                //if (tab.getPos() == 0)
                  //  setTitle(tab.getTitle());
                //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                //Mensaje("Change");
                //DatosVentanas dv = DatosVentanas.getInstance();
                //ArrayList<Tab> tbs = dv.getBacklog();
                //vpa.getItem(viewPager.getCurrentItem());
                //vpa.notifyDataSetChanged();
                tab = dataSnapshot.getValue(Tab.class);
                for( Tarea t: tab.getTareas()){
                    if(!fechas.contains(t.getFecha())) {
                        fechas.add(t.getFecha());
                    }
                }
                //vpa.setItem(t);
                //vpa.notifyDataSetChanged();
                //((Main_Content) vpa.getItem(t.getPos())).verificarParaInsertar();

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
        for (Date t : fechas){
            if(fechaHoy.after(t)){
                System.out.println("La fecha "+ t +" está vencida");
            }
        }

    }
}