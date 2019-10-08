package com.udacity.findaflight;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.udacity.findaflight.adapters.AirportAdapter;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.findaflight.utils.DateUtils.getDateInDayMonthFormat;

public class MainActivity extends AppCompatActivity implements
        AirportAdapter.AirportAdapterOnClickHandler {
    @BindView(R.id.editTextReturnDate)
    EditText returnDate;
    @BindView(R.id.textViewReturnDay)
    TextView returnDay;
    @BindView(R.id.editTextDepartDate)
    EditText departDate;
    @BindView(R.id.textViewDepartDay)
    TextView departDay;
    @BindView(R.id.editTextDepartAirport)
    EditText departAirport;
    @BindView(R.id.editTextReturnAirport)
    EditText returnAirport;


    Calendar calendar = Calendar.getInstance();

    String mDepartureDate;
    String mReturnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setOnClickListenerToDate(departDate);
        setOnClickListenerToDate(returnDate);
        setOnClickListenerToAirport(departAirport);
        setOnClickListenerToAirport(returnAirport);
    }

    private void setOnClickListenerToAirport(EditText airport) {
        airport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAirportOptions(v);
            }
        });
    }

    private void setOnClickListenerToDate(EditText date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(v);
            }
        });
    }

    private void openDatePickerDialog(View v) {
        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate = null;
                    String dayMonth = null;
                    String dayInWeek = null;

                    try {
                        selectedDate = getDateInDayMonthFormat(dayOfMonth, monthOfYear + 1, year);
                        String[] dateTokens = selectedDate.split(",");
                        dayMonth = dateTokens[0];
                        dayInWeek = dateTokens[1];
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    switch (v.getId()) {
                        case R.id.editTextDepartDate:
                            ((EditText) v).setText(dayMonth);
                            ((EditText) v).setTextColor(Color.parseColor("#323232"));
                            departDay.setText(dayInWeek);
                            mDepartureDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            break;
                        case R.id.editTextReturnDate:
                            ((EditText) v).setText(dayMonth);
                            ((EditText) v).setTextColor(Color.parseColor("#323232"));
                            returnDay.setText(dayInWeek);
                            mReturnDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            break;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void displayAirportOptions(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View myView = getLayoutInflater().inflate(R.layout.airports_dialog, null);
        dialogBuilder.setView(myView);

        RecyclerView airportOptions = myView.findViewById(R.id.recyclerview_airports);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        airportOptions.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        airportOptions.setLayoutManager(layoutManager);

        String[] airportsArray = {"SIN", "JFK", "LAX", "SEA", "BOS"};
        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new AirportAdapter(airportsArray, this);
        airportOptions.setAdapter(mAdapter);
        airportOptions.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

//        Spinner airportOptions = (Spinner) myView .findViewById(R.id.airportOptions);

//        List airportsList = Arrays.asList(airportsArray);

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, airportsList);

//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        airportOptions.setAdapter(dataAdapter);

        dialogBuilder.show();
    }

    @Override
    public void onAirportClick(String airport) {

    }
}
