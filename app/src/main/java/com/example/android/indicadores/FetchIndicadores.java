package com.example.android.indicadores;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by samirsanmartin on 5/7/16.
 */
public class FetchIndicadores extends AsyncTask<String,Integer,ArrayList<Moneda>>{

    private final String LOG_TAG = FetchIndicadores.class.getSimpleName();

    private ArrayList<Moneda> getDataFromJson(ArrayList<String> currencyJsonStr)
            throws JSONException {

        ArrayList<Moneda> currency = new ArrayList<>();
        final String LOG_TAG = FetchIndicadores.class.getSimpleName();



        for(int j=0; j<currencyJsonStr.size();j++) {

            JSONObject currencyJSON = new JSONObject(currencyJsonStr.get(j));
            // JSONArray currencyArray = currencyJSON.getJSONArray("List");


            Moneda value = new Moneda((String) currencyJSON.get("from"),
                    String.valueOf(currencyJSON.get("rate")), (String) currencyJSON.get("to"));

            currency.add(value);

        }


        for (Moneda s : currency) {
            Log.v(LOG_TAG, " class test entry: " + s.getFrom() + " " + s.getRate());
        }

        return currency;
    }





    //bar.setProgress(0);
    ProgressBar progres;
    private  Context mContext;
    private CurrencyAdapter currencyAdapter;
    private ArrayList<Moneda> datas = new ArrayList<>();
    private final onFetchListener mListener;

    public FetchIndicadores(Context context, CurrencyAdapter currencyadapter, onFetchListener listener) {
        mContext = context;
        currencyAdapter=currencyadapter;
        mListener=listener;
    }




    String[] monedaOrigen = {"USD", "EUR", "VEF"};
    static final String[] MONEDA_DESTINO = {"COP", "USD"};
    Uri builtUri;
    @Override
    protected ArrayList<Moneda> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        ArrayList<String> IndicadoresJsonStr = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 3; k++) {
                try {
                    final String FETCH_BASE_URL =
                            "http://rate-exchange-1.appspot.com/currency?";
                    //final String QUERY_PARAM = "a";
                    final String ORIGEN_PARAM = "from";
                    final String DESTINO_PARAM = "to";

                   if (i==0) {
                       builtUri = Uri.parse(FETCH_BASE_URL).buildUpon()
                               // .appendQueryParameter(QUERY_PARAM, params[0])
                               .appendQueryParameter(ORIGEN_PARAM, monedaOrigen[k])
                               .appendQueryParameter(DESTINO_PARAM, MONEDA_DESTINO[0])
                               .build();
                   }
                    else if (i==1 && k==1){
                       builtUri = Uri.parse(FETCH_BASE_URL).buildUpon()
                               // .appendQueryParameter(QUERY_PARAM, params[0])
                               .appendQueryParameter(ORIGEN_PARAM, monedaOrigen[k])
                               .appendQueryParameter(DESTINO_PARAM, MONEDA_DESTINO[k])
                               .build();
                   }


                       URL url = new URL(builtUri.toString());
                       Log.v(LOG_TAG, "Built URI" + builtUri.toString());

                       urlConnection = (HttpURLConnection) url.openConnection();
                       urlConnection.setRequestMethod("GET");
                       urlConnection.connect();

                       // Read the input stream into a String
                       InputStream inputStream = urlConnection.getInputStream();
                       StringBuffer buffer = new StringBuffer();
                       if (inputStream == null) {

                           return null;
                       }
                       reader = new BufferedReader(new InputStreamReader(inputStream));

                       String line;
                       while ((line = reader.readLine()) != null) {

                           buffer.append(line + "\n");
                       }

                       if (buffer.length() == 0) {

                           return null;
                       }
                       if (i==0) {
                           IndicadoresJsonStr.add(buffer.toString());
                       }
                       else if (i==1 && k==1){
                           IndicadoresJsonStr.add(buffer.toString());
                       }


                    Log.v(LOG_TAG, "Currency JSON String: " + IndicadoresJsonStr.get(i));
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error ", e);

                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }
            }
        }
            try {
                return getDataFromJson(IndicadoresJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;

        }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);


    }

    @Override
    protected void onPostExecute(ArrayList<Moneda> strings) {
       currencyAdapter.clear();

       if(strings != null){


           for(Moneda s : strings){
               currencyAdapter.add(s);
               datas.add(s);
           }

       }

        if(mListener != null){
            mListener.onFetchCompleted(strings);
        }




        super.onPostExecute(strings);
    }


    public ArrayList<Moneda> getData(){
        return  datas;
    }
}
