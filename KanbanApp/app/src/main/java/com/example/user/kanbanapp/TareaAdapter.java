package com.example.user.kanbanapp;

/**
 * Created by User on 10/04/2018.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class TareaAdapter extends ArrayAdapter<Tarea> {
    private final Context context;
    private final ArrayList<Tarea> data;
    private final int layoutResourceId;

    public TareaAdapter(Context context, int layoutResourceId, ArrayList<Tarea> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.textView1 = (TextView)row.findViewById(R.id.nombre);
            holder.textView2 = (TextView)row.findViewById(R.id.descripcion);
            //holder.textView3 = (TextView)row.findViewById(R.id.text3);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }

        Tarea person = data.get(position);

        holder.textView1.setText(person.getNombre());
        holder.textView2.setText(person.getDescripcion());
        return row;
    }

    static class ViewHolder
    {
        TextView textView1;
        TextView textView2;

    }
}
