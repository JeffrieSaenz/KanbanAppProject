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
        // addFIni();
        verificar();
        viewPager.setAdapter(vpa);
        Mensaje("Regrese");

        this.setTitle(vpa.getPageTitle(viewPager.getCurrentItem()));
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
}
