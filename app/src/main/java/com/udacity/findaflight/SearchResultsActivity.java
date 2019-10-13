package com.udacity.findaflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.udacity.findaflight.fragments.FlightListFragment;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FlightListFragment flightListFragment = new FlightListFragment();
        flightListFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.flight_list_fragment_container, flightListFragment);
        fragmentTransaction.commit();
    }
}
