package com.example.jl06014.travelpicker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.example.jl06014.travelpicker.CountryDBSchema.*;

/**
 * Created by jl06014 on 2/28/2017.
 */

public class CountryLab {
    private static final String TAG = "countryLab";

    private static CountryLab sCountryLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CountryLab get(Context context) {
        if (sCountryLab == null) {
            sCountryLab = new CountryLab(context);
        }
        return sCountryLab;
    }

    /* this constructor can only be accessed through the get method.
    If the class instance exists, then new instance is not created.
    There is always a single instance for an application
     */
    private CountryLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CountryBaseHelper(mContext)
                .getWritableDatabase();

       /* Populate the DB with dummy Country objects */
        Log.i(TAG, "Populating DB with dummy data");
//        for (int i = 0; i < 100; i++) {
//            Country Country = new Country();
//            Country.setName("Country #" + i);
//            Country.setDescription("A country."); // Every other one
//            Country.setPicture(R.drawable.flag_uk);
//            addCountry(Country);
        Country us = new Country();
        us.setName("US");
        us.setDescription("The United States of America, commonly referred to as the United States (U.S.) or America, is a constitutional federal republic composed of 50 states, a federal district, five major self-governing territories, and various possessions.");
        us.setPicture(R.drawable.us_flag);
        addCountry(us);

        Country argentina = new Country();
        argentina.setName("Argentina");
        argentina.setDescription(" a federal republic in the southern half of South America.");
        argentina.setPicture(R.drawable.argentina_flag);
        addCountry(argentina);

        Country brazil = new Country();
        brazil.setName("Brazil");
        brazil.setDescription(" the largest country in both South America and Latin America.");
        brazil.setPicture(R.drawable.brazil_flag);
        addCountry(brazil);

        Country canada = new Country();
        canada.setName("Canada");
        canada.setDescription("is a country in the northern half of North America. Its ten provinces and three territories extend from the Atlantic to the Pacific and northward into the Arctic Ocean");
        canada.setPicture(R.drawable.canada_flag);
        addCountry(canada);

        Country china = new Country();
        china.setName("China");
        china.setDescription("  a unitary sovereign state in East Asia. With a population of over 1.381 billion, it is the world's most populous country.");
        china.setPicture(R.drawable.china_flag);
        addCountry(china);

        Country columbia = new Country();
        columbia.setName("Columbia");
        columbia.setDescription("  a transcontinental country largely situated in the northwest of South America, with territories in Central America.");
        columbia.setPicture(R.drawable.columbia_flag);
        addCountry(columbia);

        Country cuba = new Country();
        cuba.setName("cuba");
        cuba.setDescription("  a country comprising the island of Cuba as well as Isla de la Juventud and several minor archipelagos. Cuba is located in the northern Caribbean where the Caribbean Sea, the Gulf of Mexico, and the Atlantic Ocean meet.");
        cuba.setPicture(R.drawable.cuba_flag);
        addCountry(cuba);

        Country dr = new Country();
        dr.setName("Dominican Republic");
        dr.setDescription(" is a sovereign state occupying the eastern two-thirds of the island of Hispaniola, in the Greater Antilles archipelago in the Caribbean region.");
        dr.setPicture(R.drawable.dr_flag);
        addCountry(dr);

        Country egypt = new Country();
        egypt.setName("egypt");
        egypt.setDescription(" officially the Arab Republic of Egypt, is a transcontinental country spanning the northeast corner of Africa and southwest corner of Asia by a land bridge formed by the Sinai Peninsula.");
        egypt.setPicture(R.drawable.egypt_flag);
        addCountry(egypt);

        Country elsalvador = new Country();
        elsalvador.setName("El Salvador");
        elsalvador.setDescription(" the smallest and the most densely populated country in Central America.");
        elsalvador.setPicture(R.drawable.elsalvador_flag);
        addCountry(elsalvador);

        Country france = new Country();
        france.setName("france");
        france.setDescription(" a country with territory in western Europe and several overseas regions and territories.");
        france.setPicture(R.drawable.france_flag);
        addCountry(france);

        Country greece = new Country();
        greece.setName("Greece");
        greece.setDescription("Greece's population is approximately 10.955 million as of 2015. ");
        greece.setPicture(R.drawable.greece_flag);
        addCountry(greece);

        Country india = new Country();
        india.setName("india");
        india.setDescription("a country in South Asia. It is the seventh-largest country by area, the second-most populous country (with over 1.2 billion people), and the most populous democracy in the world.");
        india.setPicture(R.drawable.india_flag);
        addCountry(india);

        Country ireland = new Country();
        ireland.setName("ireland");
        ireland.setDescription(" Ireland is the second-largest island of the British Isles, the third-largest in Europe, and the twentieth-largest on Earth.");
        ireland.setPicture(R.drawable.ireland_flag);
        addCountry(ireland);

        Country italy = new Country();
        italy.setName("italy");
        italy.setDescription(" Located in the heart of the Mediterranean Sea, Italy shares open land borders with France, Switzerland, Austria, Slovenia, San Marino and Vatican City.");
        italy.setPicture(R.drawable.italy_flag);
        addCountry(italy);

        Country japan = new Country();
        japan.setName("japan");
        japan.setDescription("Located in the Pacific Ocean, it lies off the eastern coast of the Asia Mainland (east of China, Korea, Russia) and stretches from the Sea of Okhotsk in the north to the East China Sea and Taiwan in the southwest.");
        japan.setPicture(R.drawable.japan_flag);
        addCountry(japan);

        Country madagascar = new Country();
        madagascar.setName("madagascar");
        madagascar.setDescription(" Madagascar split from the Indian peninsula around 88 million years ago, allowing native plants and animals to evolve in relative isolation.");
        madagascar.setPicture(R.drawable.madagascar_flag);
        addCountry(madagascar);

        Country mexico = new Country();
        mexico.setName("mexico");
        mexico.setDescription(" Mexico is the sixth largest country in the Americas by total area and the 13th largest independent nation in the world.");
        mexico.setPicture(R.drawable.mx_flag);
        addCountry(mexico);

        Country nigeria = new Country();
        nigeria.setName("nigeria");
        nigeria.setDescription("It comprises 36 states and the Federal Capital Territory, where the capital, Abuja is located.");
        nigeria.setPicture(R.drawable.nigeria_flag);
        addCountry(nigeria);

        Country phillipines = new Country();
        phillipines.setName("phillipines");
        phillipines.setDescription("a sovereign island country in Southeast Asia situated in the western Pacific Ocean.");
        phillipines.setPicture(R.drawable.phillipines_flag);
        addCountry(phillipines);

        Country safrica = new Country();
        safrica.setName("South Africa");
        safrica.setDescription("South Africa is the 25th-largest country in the world by land area, and with close to 56 million people, is the world's 24th-most populous nation.");
        safrica.setPicture(R.drawable.safrica_flag);
        addCountry(safrica);

        Country saudiarabia = new Country();
        saudiarabia.setName("Saudi Arabia");
        saudiarabia.setDescription(" It is the only nation with both a Red Sea coast and a Persian Gulf coast, and most of its terrain consists of arid desert or barren landforms.");
        saudiarabia.setPicture(R.drawable.saudiarabia_flag);
        addCountry(saudiarabia);

        Country sudan = new Country();
        sudan.setName("sudan");
        sudan.setDescription(" It is the third largest country in Africa. The River Nile divides the country into eastern and western halves. ");
        sudan.setPicture(R.drawable.sudan_flag);
        addCountry(sudan);

        Country uk = new Country();
        uk.setName("United Kingdom");
        uk.setDescription(" a sovereign country in western Europe. Lying off the north-western coast of the European mainland, it includes the island of Great Britain, the north-eastern part of the island of Ireland, and many smaller islands.");
        uk.setPicture(R.drawable.uk_flag);
        addCountry(uk);

        Country venezuela = new Country();
        venezuela.setName("venezuela");
        venezuela.setDescription("Venezuela is considered a state with extremely high biodiversity (currently ranks 7th in the world's list of nations with the most number of species)");
        venezuela.setPicture(R.drawable.venezuela_flag);
        addCountry(venezuela);

    }
    
    

    public void addCountry(Country c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CountryDBSchema.CountryTable.NAME, null, values);
    }

    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();
        CountryCursorWrapper cursor = queryCountries(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                countries.add(cursor.getCountry());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return countries;
    }

    public Country getCountry(UUID id) {
        CountryCursorWrapper cursor = queryCountries(
                CountryDBSchema.CountryTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCountry();
        } finally {
            cursor.close();
        }
    }

    public void updateCountry(Country Country) {
        String uuidString = Country.getId().toString();
        ContentValues values = getContentValues(Country);
        mDatabase.update(CountryDBSchema.CountryTable.NAME, values,
                CountryDBSchema.CountryTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Country Country) {
        ContentValues values = new ContentValues();
        values.put(CountryTable.Cols.UUID, Country.getId().toString());
        values.put(CountryTable.Cols.NAME, Country.getName());
        values.put(CountryTable.Cols.DESCRIPTION, Country.getDescription());
        values.put(CountryTable.Cols.PICTURE, Country.getPicture());
        return values;
    }

    private CountryCursorWrapper queryCountries(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CountryTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new CountryCursorWrapper(cursor);
    }
}
