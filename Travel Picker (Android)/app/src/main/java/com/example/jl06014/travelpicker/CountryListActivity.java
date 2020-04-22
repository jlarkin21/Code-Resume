package com.example.jl06014.travelpicker;


import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by jl06014 on 2/28/2017.
 */

public class CountryListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CountryListFragment();
    }
}
