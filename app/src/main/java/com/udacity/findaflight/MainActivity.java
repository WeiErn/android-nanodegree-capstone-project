package com.udacity.findaflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.findaflight.adapters.AirportAdapter;

import java.text.ParseException;
import java.util.Calendar;

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

    String mSelectedDepartAirport;
    String mSelectedReturnAirport;
    String mChosenDepartAirport;
    String mChosenReturnAirport;

    int mSelectedDepartAirportPosition;
    int mSelectedReturnAirportPosition;
    int mChosenDepartAirportPosition;
    int mChosenReturnAirportPosition;

    AirportAdapter mDepartAirportAdapter;
    AirportAdapter mReturnAirportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setOnClickListenerToDate(departDate);
        setOnClickListenerToDate(returnDate);

        setupAirportAdapter(R.id.editTextDepartAirport);
        setupAirportAdapter(R.id.editTextReturnAirport);

        setOnClickListenerToAirport(departAirport);
        setOnClickListenerToAirport(returnAirport);
    }

    private void setupAirportAdapter(int editTextId) {
        String[] airportsArray = {"SIN", "JFK", "LAX", "SEA", "BOS"};

        switch (editTextId) {
            case R.id.editTextDepartAirport:
                mDepartAirportAdapter = new AirportAdapter(editTextId, airportsArray, this);
                break;
            case R.id.editTextReturnAirport:
                mReturnAirportAdapter = new AirportAdapter(editTextId, airportsArray, this);
                break;
        }
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

    private void setOnClickListenerToDepartDialogButton(AlertDialog alertDialog, Button button) {
        button
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btn_positive:
                                mChosenDepartAirport = mSelectedDepartAirport;
                                mChosenDepartAirportPosition = mSelectedDepartAirportPosition;
                                mDepartAirportAdapter.setCheckedPosition(mChosenDepartAirportPosition);
                                break;
                            case R.id.btn_negative:
                                mDepartAirportAdapter.setCheckedPosition(mChosenDepartAirportPosition);
                                break;
                        }
                        alertDialog.dismiss();
                    }
                });
    }

    private void setOnClickListenerToReturnDialogButton(AlertDialog alertDialog, Button button) {
        button
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btn_positive:
                                mChosenReturnAirport = mSelectedReturnAirport;
                                mChosenReturnAirportPosition = mSelectedReturnAirportPosition;
                                mReturnAirportAdapter.setCheckedPosition(mChosenReturnAirportPosition);
                                break;
                            case R.id.btn_negative:
                                mReturnAirportAdapter.setCheckedPosition(mChosenReturnAirportPosition);
                                break;
                        }
                        alertDialog.dismiss();
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
        final View dialogView = getLayoutInflater().inflate(R.layout.airports_dialog, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        Button buttonPositive = dialogView.findViewById(R.id.btn_positive);
        Button buttonNegative = dialogView.findViewById(R.id.btn_negative);

        RecyclerView airportOptions = dialogView.findViewById(R.id.recyclerview_airports);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        airportOptions.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        airportOptions.setLayoutManager(layoutManager);

        switch (v.getId()) {
            case R.id.editTextDepartAirport:
                mChosenDepartAirportPosition = mDepartAirportAdapter.getCheckPosition();
                airportOptions.setAdapter(mDepartAirportAdapter);
                setOnClickListenerToDepartDialogButton(alertDialog, buttonPositive);
                setOnClickListenerToDepartDialogButton(alertDialog, buttonNegative);

                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mDepartAirportAdapter.setCheckedPosition(mChosenDepartAirportPosition);
                        dialog.dismiss();
                    }
                });

                break;
            case R.id.editTextReturnAirport:
                mChosenReturnAirportPosition = mReturnAirportAdapter.getCheckPosition();
                airportOptions.setAdapter(mReturnAirportAdapter);
                setOnClickListenerToReturnDialogButton(alertDialog, buttonPositive);
                setOnClickListenerToReturnDialogButton(alertDialog, buttonNegative);

                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mReturnAirportAdapter.setCheckedPosition(mChosenReturnAirportPosition);
                        dialog.dismiss();
                    }
                });
                break;
        }

        airportOptions.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        alertDialog.show();
    }


    @SuppressLint("ResourceType")
    @Override
    public void onAirportClick(String airport, int adapterPosition, int editTextAirportId) {
        switch (editTextAirportId) {
            case R.id.editTextDepartAirport:
                mSelectedDepartAirport = airport;
                mSelectedDepartAirportPosition = adapterPosition;
                break;
            case R.id.editTextReturnAirport:
                mSelectedReturnAirport = airport;
                mSelectedReturnAirportPosition = adapterPosition;
                break;
        }
    }
}
