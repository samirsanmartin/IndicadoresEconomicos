package com.example.android.indicadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by samirsanmartin on 5/17/16.
 */
public class BundleAdapter extends ArrayAdapter<BundleContent> {

    ArrayList<BundleContent> datos;
    public BundleAdapter(Context context, ArrayList<BundleContent> datos){
        super(context, R.layout.database_layout);
        this.datos=datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.database_layout, null);

        TextView id = (TextView) item.findViewById(R.id.id_textView);
        TextView from = (TextView) item.findViewById(R.id.from_textView);
        TextView rate = (TextView) item.findViewById(R.id.rate_textView);
        TextView to = (TextView) item.findViewById(R.id.to_textView);
        TextView date = (TextView) item.findViewById(R.id.date_textView);

        id.setText(String.valueOf(datos.get(position).getId()));
        from.setText(datos.get(position).getFrom());
        rate.setText(datos.get(position).getRate());
        to.setText(datos.get(position).getTo());
        date.setText(datos.get(position).getDate());
        return item;
    }
}
