package com.example.user.kanbanapp;

/**
 * Created by User on 10/04/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TareaAdapter extends ArrayAdapter<Tarea> {
    private final Context context;
    private  ArrayList<Tarea> data;
    private final int layoutResourceId;
    private Integer posPestana;
    private DatosVentanas dv;

    public TareaAdapter(Context context, int layoutResourceId, ArrayList<Tarea> data, Integer pos) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
        this.posPestana = pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        dv = DatosVentanas.getInstance();

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textView1 = (TextView) row.findViewById(R.id.nombre);
            holder.textView2 = (TextView) row.findViewById(R.id.descripcion);
            holder.borrar = (ImageButton) row.findViewById(R.id.eliminarTarea);
            holder.borrar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    System.out.println("pos en listView: "+position+", posicion en pestanas: "+posPestana);
                    //System.out.println("adap: "+data.size()+", global: "+ dv.getBacklog().get(posPestana).size());
                    data.remove(position);
                    dv.getTab(posPestana).getTareas().remove(position);
                    DatosVentanas dv = DatosVentanas.getInstance();
                    ArrayList<Tab> tbs = dv.getBacklog();
                    FbConnection conn = FbConnection.getInstance();
                    if(tbs.size() > 0)
                        for(Tab t : tbs)
                            conn.addTabs(t);
                    //dv.getBacklog().get(posPestana).remove(position);
                    notifyDataSetChanged();
                }
            });


            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Tarea person = data.get(position);

        holder.textView1.setText(person.getNombre());
        holder.textView2.setText(person.getDescripcion());
        return row;
    }


    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageButton borrar;

    }
}
