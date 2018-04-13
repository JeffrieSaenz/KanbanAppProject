package com.example.user.kanbanapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main_Content extends android.support.v4.app.Fragment {

    TareaAdapter adapter;
    EditText editText;
    EditText editText2;
    List<Tarea> itemList;
    DatosVentanas vd;

    Integer posicion;

    private ArrayList<Tarea> backlog = new ArrayList<>();

    public Main_Content() {
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Mensaje("Holaaaaaa");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.content_main, container, false);
        itemList=new ArrayList<Tarea>();
        adapter=new TareaAdapter(getContext(),R.layout.item, (ArrayList<Tarea>) itemList);
        ListView lv = (ListView) view.findViewById(R.id.listView);
        lv.setAdapter(adapter);
        // vd = DatosVentanas.getInstance();
        verificarParaInsertar();


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getContext(), IngresoItem.class);
                //Log.i ("Mi App", String.valueOf(getId()));
                Mensaje(String.valueOf(posicion));
                intento.putExtra("pos",posicion);
                startActivity(intento);
            }
        });


        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        Mensaje("resume...");
    }

    @Override
    public void onStart() {
        super.onStart();
        Mensaje("Onstart Fragment");
       // (Backlog) getActivity().setTitle((Backlog) getActivity().getPageTitle(posicion));

        verificarParaInsertar();
        //Backlog b = (Backlog) getActivity();
        //getActivity().setTitle(b.vpa.getPageTitle(b.viewPager.getCurrentItem()));
    }

    private void verificarParaInsertar() {
        vd = DatosVentanas.getInstance();
        itemList.clear();
        ArrayList<Tarea> at = vd.getBacklog().get(posicion);
        for(int i = 0; i< at.size(); i++){
            if(!at.isEmpty())
                itemList.add(at.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    public void Mensaje(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();};


}
