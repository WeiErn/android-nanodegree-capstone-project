package com.udacity.findaflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.udacity.findaflight.data.FlightSearchResult;
import com.udacity.findaflight.fragments.FlightListFragment;

public class SearchResultsActivity extends AppCompatActivity implements FlightListFragment.OnFlightResultClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FlightListFragment flightListFragment = new FlightListFragment();
        flightListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.flight_list_fragment_container, flightListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFlightResultClick(FlightSearchResult flight) {
        Class destinationClass = FlightDetailsActivity.class;
        Intent intentToStartFlightDetailsActivity = new Intent(this, destinationClass);
        intentToStartFlightDetailsActivity.putExtra("flight", flight);
        startActivity(intentToStartFlightDetailsActivity);
    }
}
