package com.udacity.findaflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.udacity.findaflight.data.FlightSearchResult;
import com.udacity.findaflight.fragments.FlightListFragment;

public class SearchResultsActivity extends AppCompatActivity implements FlightListFragment.OnFlightResultClickListener {

    private boolean mIsTwoPane;
    private Fragment mFlightListFragment;
    private static final String FRAGMENT_TAG = "flightListFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        if (findViewById(R.id.flight_details_fragment_container) != null) {
            mIsTwoPane = true;
        }

        if (savedInstanceState == null) {
            setupFragment();
        } else {
            FlightListFragment fragment = (FlightListFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG);
            changeFragment(fragment);
//            mFlightListFragment = (FlightListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }
    }

    private void changeFragment(FlightListFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flight_list_fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    private void setupFragment() {
        Bundle bundle = getIntent().getExtras();

        FlightListFragment flightListFragment = new FlightListFragment();
        flightListFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flight_list_fragment_container, flightListFragment, FRAGMENT_TAG)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FlightListFragment fragment = (FlightListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment != null) {
            getSupportFragmentManager().putFragment(outState, FRAGMENT_TAG, fragment);
        }

    }

    @Override
    public void onFlightResultClick(FlightSearchResult flight) {
        if (mIsTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            Bundle bundle = new Bundle();
            bundle.putParcelable("flight", flight);

            FlightListFragment flightListFragment = new FlightListFragment();
            flightListFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.flight_details_fragment_container, flightListFragment)
                    .commit();
        } else {
            Class destinationClass = FlightDetailsActivity.class;
            Intent intentToStartFlightDetailsActivity = new Intent(this, destinationClass);
            intentToStartFlightDetailsActivity.putExtra("flight", flight);
            startActivity(intentToStartFlightDetailsActivity);
        }
    }
}
