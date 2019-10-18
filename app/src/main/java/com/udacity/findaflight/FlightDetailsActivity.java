package com.udacity.findaflight;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.udacity.findaflight.fragments.FlightDetailsFragment;

public class FlightDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);

        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FlightDetailsFragment flightDetailsFragment = new FlightDetailsFragment();
        flightDetailsFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.flight_details_fragment_container, flightDetailsFragment);
        fragmentTransaction.commit();
    }
}
