package com.example.user.kanbanapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ActividadPrincipal extends AppCompatActivity {

    TareaAdapter adapter;
    EditText editText;
    EditText editText2;
    List<Tarea> itemList;
    DatosVentanas vd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_actividad_principal);

        itemList=new ArrayList<Tarea>();
        adapter=new TareaAdapter(this,R.layout.item, (ArrayList<Tarea>) itemList);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        editText=(EditText)findViewById(R.id.nombreTarea);
        editText2=(EditText)findViewById(R.id.descripcionTarea);

        verificarParaInsertar();

        Button MiButton = (Button) findViewById(R.id.button);

        //Programamos el evento onclick

        MiButton.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {
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