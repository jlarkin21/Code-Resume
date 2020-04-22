package com.example.jl06014.travelpicker;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.util.Date;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by jl06014 on 3/10/2017.
 */

public class CountryCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CountryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Country getCountry() {
        Log.i(TAG, "CountryCursorWrapper --> getCountry");

        String uuidString = getString(getColumnIndex(CountryDBSchema.CountryTable.Cols.UUID));
        String name = getString(getColumnIndex(CountryDBSchema.CountryTable.Cols.NAME));
        String description = getString(getColumnIndex(CountryDBSchema.CountryTable.Cols.DESCRIPTION));
        int picture = getInt(getColumnIndex(CountryDBSchema.CountryTable.Cols.PICTURE));

        Country country = new Country(UUID.fromString(uuidString));
        country.setName(name);
        country.setDescription(description);
        country.setPicture(picture);
        return country;
    }
}
