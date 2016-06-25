package com.example.android.indicadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by samirsanmartin on 5/8/16.
 */
public class CurrencyAdapter extends ArrayAdapter<Moneda> {

    private ArrayList<Moneda>datos=new ArrayList<>();


    public CurrencyAdapter(Context context, ArrayList<Moneda> datos){
        super(context,R.layout.dolar_textview, datos);
        this.datos=datos;


    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.dolar_textview, null);

        TextView origen = (TextView) item.findViewById(R.id.currency_origen_textview);
        origen.setText(datos.get(position).getFrom());

        TextView curre = (TextView) item.findViewById(R.id.dolar_currency_textview);
        curre.setText(datos.get(position).getRate());

        TextView to = (TextView) item.findViewById(R.id.currency_destiny_textview);
        to.setText(datos.get(position).getTo());


     return(item);
    }


}
