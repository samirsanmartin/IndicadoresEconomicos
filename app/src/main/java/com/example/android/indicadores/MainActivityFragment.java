package com.example.android.indicadores;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements onFetchListener {

    private CurrencyAdapter currencyAdapter;
    private boolean finished = false;
    private ArrayList<Moneda> dataSave;

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        SqdbHelper currencydb = new SqdbHelper(getActivity(), "DBcurrency" , null ,4);
        final SQLiteDatabase db = currencydb.getWritableDatabase();

        //Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
       /* Cursor d =db.rawQuery("SELECT _id FROM record WHERE _id IS NOT NULL",null);


        if (d.moveToFirst()) {
            while ( !d.isAfterLast() ) {
               Toast.makeText(getActivity(), "query  Result=> "+d.getString(0), Toast.LENGTH_LONG).show();
                d.moveToNext();
            }
        } */

        ArrayList<Moneda> currency_data = new ArrayList<>();

        currencyAdapter = new CurrencyAdapter(getActivity(), currency_data);
        FetchIndicadores fetchtask = new FetchIndicadores(getActivity(), currencyAdapter,this);
        fetchtask.execute("");



        ListView currency_list = (ListView) rootView.findViewById(R.id.currency_listView);
        currency_list.setAdapter(currencyAdapter);



        Log.v(LOG_TAG, "Currency JSON String: " + fetchtask.getData().size());

        currency_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<BundleContent>  dataSwitch= new ArrayList<>();
                Intent dB = new Intent(getActivity(),DatabaseActivity.class);
                Cursor c = db.rawQuery("SELECT _id, RECORD, CURRENCY, RATE, date FROM record WHERE RECORD='" +
                        dataSave.get(position).getFrom() + "' AND RATE='" + dataSave.get(position).getTo() + "'" , null );
                int i=1;
                if(c.moveToFirst()){
                    do{
                        BundleContent data = new BundleContent(String.valueOf(i),c.getString(1),c.getString(2),
                                          c.getString(3),c.getString(4));
                         dataSwitch.add(data);

                        Log.v(LOG_TAG, "Currency JSON String: " + c.getString(0) + c.getString(1) +c.getString(2) +
                                c.getString(3)+c.getString(4));
                        i++;
                    }while(c.moveToNext());
                }
                dB.putExtra("data_to_send",dataSwitch);
                startActivity(dB);
                                                 }
                                             }

        );

        Button dbCurrency = (Button) rootView.findViewById(R.id.dbRecord);

        dbCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (db != null && finished == true){
                  for(int i=0;i<4;i++) {
                     db.execSQL("INSERT INTO record (RECORD, CURRENCY, RATE, date) " +
                              "VALUES ('" + dataSave.get(i).getFrom() +
                              "', '" + dataSave.get(i).getRate() + "', '" +
                              dataSave.get(i).getTo() + "', '" +
                              getCurrentTimeStamp() + "')");


                  }
                }

            }
        });

        return rootView;
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }


    @Override
    public void onFetchCompleted(ArrayList<Moneda> data) {
        dataSave=data;
        finished = true;
        Log.v(LOG_TAG, "Size: " + data.size());

    }

    @Override
    public void onFetchError(String error) {

    }

}

 interface onFetchListener{
        void onFetchCompleted(ArrayList<Moneda> data);
        void onFetchError(String error);
        }
