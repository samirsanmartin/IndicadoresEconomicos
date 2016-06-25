package com.example.android.indicadores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * Created by samirsanmartin on 5/9/16.
 */
public class SqdbHelper extends SQLiteOpenHelper {

    String sqlcreate = "CREATE TABLE record(_id INTEGER PRIMARY KEY AUTOINCREMENT, RECORD STRING, CURRENCY STRING, RATE STRING, date STRING)";

    public SqdbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        if (BuildConfig.DEBUG)
        {
            new File(context.getDatabasePath("wl.db").getPath()).setReadable(true, false);
        }


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL(sqlcreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS record");


       db.execSQL(sqlcreate);
    }
}
