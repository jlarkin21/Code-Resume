package com.example.jl06014.travelpicker;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;;import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by jl06014 on 2/28/2017.
 */

public class CountryListFragment extends Fragment {

    private RecyclerView countryRecyclerView;
    private CountryAdapter countryAdapter;
    private int countryID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.country_list_fragment, container, false);

        countryRecyclerView = (RecyclerView) view.findViewById(R.id.country_recycler_view);
        countryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        updateUI();
        return view;
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");

        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CountryLab countryLab = CountryLab.get(getActivity());
        List<Country> countries = countryLab.getCountries();

        if (countryAdapter == null) {
            countryAdapter = new CountryAdapter(getActivity(), countries);
            countryRecyclerView.setAdapter(countryAdapter);
        } else {
            countryAdapter.notifyDataSetChanged();
        }

        //mAdapter = new CountryAdapter(Countrys);
        //mCountryRecyclerView.setAdapter(mAdapter);
    }

    public class CountryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTextView;
        public ImageView flagImageView;
        private Country mCountry;


        public CountryHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            nameTextView = (TextView) itemView.findViewById(R.id.country_name);
            flagImageView = (ImageView) itemView.findViewById(R.id.country_photo);
        }

        //from crime
        public void bindCountry(Country country) {
            mCountry = country;
            Log.i(TAG, "bindCountry --> Name: "+mCountry.getName());
            nameTextView.setText(mCountry.getName());
            flagImageView.setImageResource(mCountry.getPicture());
        }

        @Override
        public void onClick(View v) {
            Intent intent = CountryActivity.newIntent(getActivity(), mCountry.getId());
            startActivity(intent);
        }
    }

    public class CountryAdapter extends RecyclerView.Adapter<CountryHolder> {
        private List<Country> mCountries;
        private Context context;

        public CountryAdapter(Context context, List<Country> countries) {
            this.context = context;
            mCountries = countries;
        }

        @Override
        public CountryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item, null);
            CountryHolder rv = new CountryHolder(layoutView);
            return rv;
        }

        @Override
        public void onBindViewHolder(CountryHolder holder, int position) {
            Country country = mCountries.get(position);
            holder.bindCountry(country);
        }

        @Override
        public int getItemCount() {
            return mCountries.size();
        }
    }

//    private List<Country> getAllItemList() {
//        List<Country> allItems = new ArrayList<Country>();
//        allItems.add(new Country("United States", R.drawable.flag_uk));
//        allItems.add(new Country("Canada", R.drawable.flag_uk));
//        allItems.add(new Country("United Kingdom", R.drawable.flag_uk));
//        allItems.add(new Country("United States", R.drawable.flag_uk));
//        allItems.add(new Country("United States", R.drawable.flag_uk));
//        allItems.add(new Country("United States", R.drawable.flag_uk));
//        allItems.add(new Country("United States", R.drawable.flag_uk));
//
//        return allItems;
//
//    }

//    private Country getCountry(int i) {
//        return getAllItemList().get(i);
//    }
}
