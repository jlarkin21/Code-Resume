package com.example.jl06014.travelpicker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by jl06014 on 3/10/2017.
 */

public class CountryBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "countryBaseHelper";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "countryBase.db";
    public CountryBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate --> creating DB");
        db.execSQL("create table " + CountryDBSchema.CountryTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CountryDBSchema.CountryTable.Cols.UUID + ", " +
                CountryDBSchema.CountryTable.Cols.NAME + ", " +
                CountryDBSchema.CountryTable.Cols.DESCRIPTION + ", " +
                CountryDBSchema.CountryTable.Cols.PICTURE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
