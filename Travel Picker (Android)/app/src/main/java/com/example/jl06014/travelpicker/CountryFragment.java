package com.example.jl06014.travelpicker;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by jl06014 on 2/28/2017.
 */

public class CountryFragment extends Fragment{

    private Country country;
    private EditText nameField;
    public static final String ARG_COUNTRYID = "country_id";

    public static CountryFragment newInstance(UUID countryID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_COUNTRYID, countryID);
        CountryFragment fragment = new CountryFragment();
        fragment.setArguments(args);

        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID countryId = (UUID) getArguments().getSerializable(ARG_COUNTRYID);
        country = CountryLab.get(getActivity()).getCountry(countryId);
    }

    @Override
    public void onPause() {
        super.onPause();
        CountryLab.get(getActivity())
                .updateCountry(country);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.country_fragment, container, false);


        nameField = (EditText) v.findViewById(R.id.country_title);
        nameField.setText(country.getName());
//        nameField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                country.setName(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        return v;
    }
}
