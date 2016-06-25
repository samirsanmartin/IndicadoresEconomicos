package com.example.android.indicadores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DatabaseActivityFragment extends Fragment {
    private final String LOG_TAG = DatabaseActivityFragment.class.getSimpleName();
    private BundleAdapter databaseAdapter;

    public DatabaseActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_database, container, false);


       List<BundleContent> extras = new ArrayList<>();
        extras =(ArrayList<BundleContent>)getActivity().getIntent().getSerializableExtra("data_to_send");

        Log.v(LOG_TAG, "Size data: " + extras.size());


        ArrayList<BundleContent> data = new ArrayList<>();
         for(BundleContent s: extras){

            data.add(s);
        }


        databaseAdapter= new BundleAdapter(getActivity(), data);

        for(BundleContent s: extras){

            databaseAdapter.add(s);
        }

        ListView database_list = (ListView) rootview.findViewById(R.id.database_listView);
        database_list.setAdapter(databaseAdapter);

        return rootview;
    }
}
