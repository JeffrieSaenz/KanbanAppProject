package com.example.user.kanbanapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Backlog extends AppCompatActivity {

    /*
        TareaAdapter adapter;
        EditText editText;
        EditText editText2;
        List<Tarea> itemList;
        DatosVentanas vd;
    */
    DatosVentanas vd;

    public ViewPagerAdapter vpa;
    public ViewPager viewPager;
    public FbConnection conn;
    private ArrayList<Tab> tbs = new ArrayList<>();
    private ViewPagerAdapter vpa_aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.cont);
        initPagerAdapter();
        //viewPager = new ViewPager(this);

        //mViewPager.setAdapter(mSectionsPagerAdapter);
        //addFIni();
        viewPager.setAdapter(vpa);
        //verificar();
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        readTabs();

        //Mensaje("Regrese");

        //this.setTitle(vpa.getPageTitle(viewPager.getCurrentItem()));


    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setTitle(vpa.getPageTitle(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    });

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateTabs();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // addFragment();
        //addFIni();
        //Mensaje("Pase por onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Mensaje("Pase por onResume");
    }

    public void Mensaje(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    Intent intento = new Intent();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.header_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_tab:
                Mensaje("Primero");
                addNewTab();
                break;

            case R.id.editTab:{
                editTab();
                break;
            }
            default:
                Mensaje("No clasificado");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initPagerAdapter() {
        vpa = new ViewPagerAdapter(getSupportFragmentManager());
    }

    public void addFragment() {
        vpa.addFragments(new Main_Content(), "New one");
        vpa.notifyDataSetChanged();
        //viewPager.setAdapter(vpa);
    }

    public void addFIni() {
        conn = FbConnection.getInstance();
        //vd = DatosVentanas.getInstance();
        //vd.agregaInicial();

        //vpa.addFragments(new Main_Content(), "New one");
        Main_Content mc  = new Main_Content();
        mc.setPosicion(0);
        vpa.addFragments(mc, "New Tab");
        ArrayList<Tarea> a = new ArrayList<Tarea>();
        a.add(new Tarea("MAMA","aaa"));
        Tab t = new Tab("New Tab",0,a);
        conn.addTabs(t);
        vpa.notifyDataSetChanged();
        //viewPager.setAdapter(vpa);
    }

    public void verificar() {
        conn = FbConnection.getInstance();
        //conn.readTabs();

        ArrayList<Tab> tbs = conn.getTabs();
        Mensaje("TABS:" + tbs.size());

        if(tbs.isEmpty())
            addFIni();
        else{
            for (Tab t: tbs){
                //addFragment();
                Main_Content mc  = new Main_Content();
                mc.setPosicion(t.getPos());
                mc.setTareas(t.getTareas());
                vpa.addFragments(mc,t.getTitle());
            }
        }
        vpa.notifyDataSetChanged();
    }

    public void editTab() {
        final EditText txtUrl = new EditText(this);

        // Set the default text to a link of the Queen
        //txtUrl.setHint("http://www.librarising.com/astrology/celebs/images2/QR/queenelizabethii.jpg");

        new AlertDialog.Builder(this)
                .setTitle("Edit tab")
                .setMessage(" Name ")
                .setView(txtUrl)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String url = txtUrl.getText().toString();
                        //vd.agregaInicial();
                        DatosVentanas vd = DatosVentanas.getInstance();

                        Tab t = new Tab(url,viewPager.getCurrentItem(), vd.getTab(viewPager.getCurrentItem()).getTareas());
                        conn.addTabs(t);
                        setTitle(t.getTitle());


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
        //vd.agregaInicial();
        //vpa.addFragments(new Main_Content(), "New one");
        //vpa.notifyDataSetChanged();
        //viewPager.setAdapter(vpa);
    }



    public void addNewTab() {
        final EditText txtUrl = new EditText(this);

        // Set the default text to a link of the Queen
        //txtUrl.setHint("http://www.librarising.com/astrology/celebs/images2/QR/queenelizabethii.jpg");

        new AlertDialog.Builder(this)
                .setTitle("Add new tab")
                .setMessage(" Name ")
                .setView(txtUrl)
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String url = txtUrl.getText().toString();
                        //vd.agregaInicial();
                        Main_Content mc  = new Main_Content();
                        vpa = (ViewPagerAdapter) viewPager.getAdapter();
                        mc.setPosicion(vpa.getCount());
                        Tab t = new Tab(url,vpa.getCount(),new ArrayList<Tarea>());
                        conn.addTabs(t);
                        viewPager.setCurrentItem(vpa.getCount()+1);
                        setTitle(t.getTitle());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
        //vd.agregaInicial();
        //vpa.addFragments(new Main_Content(), "New one");
        //vpa.notifyDataSetChanged();
        //viewPager.setAdapter(vpa);
    }

    public void readTabs(){
        tbs = new ArrayList<>();
        ViewPagerAdapter vpa_db = new ViewPagerAdapter(getSupportFragmentManager());
        DatosVentanas dv = DatosVentanas.getInstance();
        DatabaseReference mtabs = FirebaseDatabase.getInstance().getReference("tabs");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Tab tab = dataSnapshot.getValue(Tab.class);
                //Mensaje("TAB: " + tab.getTitle());
                Main_Content mc  = new Main_Content();
                mc.setPosicion(tab.getPos());
                mc.setTareas(tab.getTareas());
                vpa.addFragments(mc,tab.getTitle());
                dv.getBacklog().add(tab);
                vpa.notifyDataSetChanged();
                if(tab.getPos() == 0)
                    setTitle(tab.getTitle());
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                //Mensaje("Change");
                DatosVentanas dv = DatosVentanas.getInstance();
                ArrayList<Tab> tbs = dv.getBacklog();
                vpa.getItem(viewPager.getCurrentItem());
                vpa.notifyDataSetChanged();
                Tab t= dataSnapshot.getValue(Tab.class);
                vpa.setItem(t);
                vpa.notifyDataSetChanged();
                ((Main_Content)vpa.getItem(t.getPos())).verificarParaInsertar();

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

    public void updateTabs(){
        DatosVentanas dv = DatosVentanas.getInstance();
        ArrayList<Tab> tbs = dv.getBacklog();
        conn = FbConnection.getInstance();
        if(tbs.size() > 0)
            for(Tab t : tbs) {
                conn.addTabs(t);
                vpa.notifyDataSetChanged();
            }
    }
}
