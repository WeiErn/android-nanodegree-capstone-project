package com.udacity.findaflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.udacity.findaflight.data.FlightSearchResult;
import com.udacity.findaflight.fragments.FlightDetailsFragment;
import com.udacity.findaflight.fragments.FlightListFragment;

public class SearchResultsActivity extends AppCompatActivity implements FlightListFragment.OnFlightResultClickListener {

    private boolean mIsTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        if (findViewById(R.id.flight_details_fragment_container) != null) {
            mIsTwoPane = true;
        }

        setupFragment();
    }

    private void setupFragment() {
        Bundle bundle = getIntent().getExtras();

        FlightListFragment flightListFragment = new FlightListFragment();
        flightListFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flight_list_fragment_container, flightListFragment)
                .commit();
    }

    @Override
    public void onFlightResultClick(FlightSearchResult flight) {
        if (mIsTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            Bundle bundle = new Bundle();
            bundle.putParcelable("flight", flight);
            bundle.putBoolean("twoPane", mIsTwoPane);

            FlightDetailsFragment flightDetailsFragment = new FlightDetailsFragment();
            flightDetailsFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.flight_details_fragment_container, flightDetailsFragment)
                    .commit();
        } else {
            Class destinationClass = FlightDetailsActivity.class;
            Intent intentToStartFlightDetailsActivity = new Intent(this, destinationClass);
            intentToStartFlightDetailsActivity.putExtra("flight", flight);
            startActivity(intentToStartFlightDetailsActivity);
        }
    }
}
