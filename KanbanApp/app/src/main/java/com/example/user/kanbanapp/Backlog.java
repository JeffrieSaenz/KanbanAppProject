package com.example.user.kanbanapp;

import android.content.DialogInterface;
import android.content.Intent;
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
    private DatabaseReference mDatabase;
    public ViewPagerAdapter vpa;
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        viewPager = (ViewPager) findViewById(R.id.cont);
        initPagerAdapter();
        //viewPager = new ViewPager(this);

        //mViewPager.setAdapter(mSectionsPagerAdapter);
        // addFIni();
        verificar();
        viewPager.setAdapter(vpa);
        Mensaje("Regrese");

        this.setTitle(vpa.getPageTitle(viewPager.getCurrentItem()));
        readFirebase();

        //verificarParaInsertar();
        /*
        itemList=new ArrayList<Tarea>();
        adapter=new TareaAdapter(this,R.layout.item, (ArrayList<Tarea>) itemList);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);



        verificarParaInsertar();

    */

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), IngresoItem.class);
                //intento.putExtra("pos",viewPager.getCurrentItem());
                startActivity(intento);
            }
        });*/
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

        //Mensaje("Pase por OnStart");
    }

    ;

    @Override
    protected void onRestart() {
        super.onRestart();
        // addFragment();
        //addFIni();
        //Mensaje("Pase por onRestart");
    }

    ;

    @Override
    protected void onResume() {
        super.onResume();
        //Mensaje("Pase por onResume");
    }

    ;


    /*
    private void verificarParaInsertar() {
        vd = DatosVentanas.getInstance();
        for(int i = 0; i<vd.getBacklog().size(); i++){
            itemList.add(vd.getBacklog().get(i));
        }
    }
*/

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
        vd = DatosVentanas.getInstance();
        vd.agregaInicial();
        vpa.addFragments(new Main_Content(), "New one");
        vpa.notifyDataSetChanged();
        //viewPager.setAdapter(vpa);
    }

    public void verificar() {
        vd = DatosVentanas.getInstance();
        if (vd.getBacklog().isEmpty())
            addFIni();
        else {
            for (ArrayList<Tarea> e : vd.getBacklog()) {
                addFragment();
            }
        }
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
                        vd.agregaInicial();
                        vpa.addFragments(new Main_Content(), url);
                        vpa.notifyDataSetChanged();
                        viewPager.setCurrentItem(vpa.getCount());
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

    private void readFirebase() {
        DatabaseReference mTareas= FirebaseDatabase.getInstance().getReference("tareas");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Tarea tarea = dataSnapshot.getValue(Tarea.class);
                Intent callingIntent = getIntent();
                Integer pos = callingIntent.getIntExtra("pos", 0);
                vd = DatosVentanas.getInstance();
                vd.agregarTareaBacklog(tarea,pos);
                MensajeOK("HOLA");
                //MensajeOK("Nombre: " +tarea.getNombre()+ " \nDescripción: "+ tarea.getDescripcion());
                /*Intent intento = new Intent(getApplicationContext(), Backlog.class);
                startActivity(intento);*/
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                //Tarea tarea = dataSnapshot.getValue(Tarea.class);
                //String tareaKey = dataSnapshot.getKey();
                MensajeOK("Change");
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                //String tareaKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                //Tarea tarea = dataSnapshot.getValue(Tarea.class);
                //String tareaKey = dataSnapshot.getKey();
                MensajeOK("Move");

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                //Toast.makeText(mContext, "Failed to load comments.",
                //        Toast.LENGTH_SHORT).show();
                MensajeOK("Cancelado: "+databaseError.toException());
            }
        };
        mTareas.addChildEventListener(childEventListener);
    }

    public void MensajeOK(String msg){
        View v1 = getWindow().getDecorView().getRootView();
        AlertDialog.Builder builder1 = new AlertDialog.Builder( v1.getContext());
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {} });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        ;};
}
