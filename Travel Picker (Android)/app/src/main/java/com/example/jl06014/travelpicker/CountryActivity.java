package com.example.jl06014.travelpicker;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class CountryActivity extends SingleFragmentActivity {

    public static final String EXTRA_COUNTRY_ID = "countryID";

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_country);
//
//        FragmentManager fm = getFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//
//        if(fragment == null) {
//            fragment = new CountryFragment();
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, fragment)
//                    .commit();
//        }
//    }

    @Override
    protected Fragment createFragment() {
        UUID countryID  = (UUID) getIntent().getSerializableExtra(EXTRA_COUNTRY_ID);
        return CountryFragment.newInstance(countryID);
    }

    public static Intent newIntent(Context packageContext, UUID countryId) {
        Intent intent = new Intent(packageContext, CountryActivity.class);
        intent.putExtra(EXTRA_COUNTRY_ID, countryId);
        return intent;
    }
}
