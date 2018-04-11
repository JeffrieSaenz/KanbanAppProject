package com.example.user.kanbanapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<String> {
    String item;

    public ItemAdapter(Context context, ArrayList<String> array) {
        super(context, 0, array);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        // Lookup view for data population
        TextView text = (TextView) convertView.findViewById(R.id.textView);
        // Populate the data into the template view using the data object
        text.setText(item);
        // Return the completed view to render on screen
        return convertView;
    }

    public void addItem(String i){
        item = i;
        this.add(i);
    }
}