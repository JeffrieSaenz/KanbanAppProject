package com.example.user.kanbanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IngresoItem extends AppCompatActivity {

    EditText nombre;
    EditText descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_item);

        nombre = (EditText)findViewById(R.id.nombreTarea);
        descripcion = (EditText)findViewById(R.id.descripcionTarea);

        OnclickDelButton(R.id.btnAgregar);
    }

    public void OnclickDelButton(int ref) {
        View view =findViewById(ref);
        Button btnAgregar = (Button) view;

        btnAgregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatosVentanas dv = DatosVentanas.getInstance();
                switch (v.getId()) {

                    case R.id.btnAgregar:
                        //Se agrega la tarea al Backlog
                        dv.agregarTareaBacklog(
                                new Tarea(
                                        nombre.getText().toString(),
                                        descripcion.getText().toString())
                        );
                        Intent intento = new Intent(getApplicationContext(), ActividadPrincipal.class);
                        startActivity(intento);


                        break;
                    default:break; }// fin de casos
            }// fin del onclick
        });
    }// fin de OnclickDelButton



    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};Intent intento = new Intent();

}
