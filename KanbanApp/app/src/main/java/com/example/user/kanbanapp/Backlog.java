package com.example.user.kanbanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Backlog extends AppCompatActivity {

    TareaAdapter adapter;
    EditText editText;
    EditText editText2;
    List<Tarea> itemList;
    DatosVentanas vd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("Backlog");

        itemList=new ArrayList<Tarea>();
        adapter=new TareaAdapter(this,R.layout.item, (ArrayList<Tarea>) itemList);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);



        verificarParaInsertar();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), IngresoItem.class);
                startActivity(intento);
            }
        });
    }

    private void verificarParaInsertar() {
        vd = DatosVentanas.getInstance();
        for(int i = 0; i<vd.getBacklog().size(); i++){
            itemList.add(vd.getBacklog().get(i));
        }

    }


    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};Intent intento = new Intent();

}
