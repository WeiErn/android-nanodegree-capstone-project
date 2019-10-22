package com.udacity.findaflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.udacity.findaflight.adapters.AirportAdapter;
import com.udacity.findaflight.data.Airport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.findaflight.utils.DateUtils.getDateFromString;
import static com.udacity.findaflight.utils.DateUtils.getDateInDayMonthFormat;
import static com.udacity.findaflight.utils.DateUtils.getDateInDayMonthYearFormat;

public class MainActivity extends AppCompatActivity implements
        AirportAdapter.AirportAdapterOnClickHandler {
    @BindView(R.id.return_date_group_linear_layout)
    LinearLayout returnDateGroup;
    @BindView(R.id.plane_return_image_view)
    ImageView returnPlaneImage;
    @BindView(R.id.return_date_edit_text)
    EditText returnDate;
    @BindView(R.id.return_day_text_view)
    EditText returnDay;
    @BindView(R.id.depart_date_group_linear_layout)
    LinearLayout departDateGroup;
    @BindView(R.id.plane_depart_image_view)
    ImageView departPlaneImage;
    @BindView(R.id.depart_date_edit_text)
    EditText departDate;
    @BindView(R.id.depart_day_text_view)
    EditText departDay;
    @BindView(R.id.depart_airport_edit_text)
    EditText departAirport;
    @BindView(R.id.return_airport_edit_text)
    EditText returnAirport;
    @BindView(R.id.depart_country_edit_text)
    EditText departCountry;
    @BindView(R.id.return_country_edit_text)
    EditText returnCountry;
    @BindView(R.id.flight_search_options_radio_group)
    RadioGroup flightSearchOptionsRadioGroup;

    Calendar calendar = Calendar.getInstance();

    String mFlightSearchOption;

    Date mDepartureDate;
    Date mReturnDate;
    String mDepartureDateString;
    String mReturnDateString;
    String mDepartureDayString;
    String mReturnDayString;

    Airport mSelectedDepartAirport;
    Airport mSelectedReturnAirport;
    Airport mChosenDepartAirport;
    Airport mChosenReturnAirport;

    int mSelectedDepartAirportPosition;
    int mSelectedReturnAirportPosition;
    int mChosenDepartAirportPosition;
    int mChosenReturnAirportPosition;

    int mDepartClickCount;
    int mReturnClickCount;

    AirportAdapter mDepartAirportAdapter;
    AirportAdapter mReturnAirportAdapter;

    boolean mIsOneWayFlight;
    boolean mIsWideScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mDepartureDateString = savedInstanceState.getString("departureDate");
            mReturnDateString = savedInstanceState.getString("returnDate");
            mFlightSearchOption = savedInstanceState.getString("flightSearchOption");
            mChosenDepartAirport = savedInstanceState.getParcelable("departAirport");
            mChosenReturnAirport = savedInstanceState.getParcelable("returnAirport");
            mIsOneWayFlight = savedInstanceState.getBoolean("isOneWayFlight");
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (findViewById(R.id.main_activity_banner) != null) {
            mIsWideScreen = true;
        }
//        TODO: Figure how to set onClickListener on LinearLayout consisting of ImageView and EditText
//        setOnClickListenerToDate(departDateGroup);
//        setOnClickListenerToDate(returnDateGroup);

        setOnClickListenerToDate(departDate);
        setOnClickListenerToDate(departPlaneImage);
        setOnClickListenerToDate(returnDate);
        setOnClickListenerToDate(returnPlaneImage);

        setupAirportAdapter(R.id.depart_airport_edit_text);
        setupAirportAdapter(R.id.return_airport_edit_text);

        setOnClickListenerToAirport(departAirport);
        setOnClickListenerToAirport(returnAirport);
    }

    private void setupAirportAdapter(int editTextId) {
        String[] airportsIataArray = {"SIN", "JFK", "LAX", "SEA", "BOS", "HND"};
        String[] airportsCountryArray = {"SINGAPORE", "USA", "USA", "USA", "USA", "JAPAN"};
        List<Airport> airportsList = new ArrayList<>();

        for (int i = 0; i < airportsIataArray.length; i++) {
            String iataCode = airportsIataArray[i];
            String country = airportsCountryArray[i];
            airportsList.add(new Airport(iataCode, country));
        }

        switch (editTextId) {
            case R.id.depart_airport_edit_text:
                mDepartAirportAdapter = new AirportAdapter(editTextId, airportsList, this);
                break;
            case R.id.return_airport_edit_text:
                mReturnAirportAdapter = new AirportAdapter(editTextId, airportsList, this);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("departureDate", mDepartureDateString);
        outState.putString("returnDate", mReturnDateString);
        outState.putString("departureDay", mDepartureDayString);
        outState.putString("returnDay", mReturnDayString);
        outState.putString("flightSearchOption", mFlightSearchOption);
        outState.putParcelable("departAirport", mChosenDepartAirport);
        outState.putParcelable("returnAirport", mChosenReturnAirport);
        outState.putBoolean("isOneWayFlight", mIsOneWayFlight);
    }

    private void setOnClickListenerToAirport(EditText airport) {
        airport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAirportOptions(v);
            }
        });
    }

    private void setOnClickListenerToDepartDialogButton(AlertDialog alertDialog, Button button) {
        button
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.positive_button:
                                if (mDepartClickCount != 0) {
                                    mChosenDepartAirport = mSelectedDepartAirport;
                                    mChosenDepartAirportPosition = mSelectedDepartAirportPosition;
                                    mDepartAirportAdapter.setCheckedPosition(mChosenDepartAirportPosition);
                                    departAirport.setText(mChosenDepartAirport.getIataCode());
                                    departCountry.setText(mChosenDepartAirport.getCountry());
                                }
                                break;
                            case R.id.negative_button:
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
                            case R.id.positive_button:
                                if (mReturnClickCount != 0) {
                                    mChosenReturnAirport = mSelectedReturnAirport;
                                    mChosenReturnAirportPosition = mSelectedReturnAirportPosition;
                                    mReturnAirportAdapter.setCheckedPosition(mChosenReturnAirportPosition);
                                    returnAirport.setText(mChosenReturnAirport.getIataCode());
                                    returnCountry.setText(mChosenReturnAirport.getCountry());
                                }
                                break;
                            case R.id.negative_button:
                                mReturnAirportAdapter.setCheckedPosition(mChosenReturnAirportPosition);
                                break;
                        }
                        alertDialog.dismiss();
                    }
                });
    }

    private void displayAirportOptions(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.airports_dialog, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        Button buttonPositive = dialogView.findViewById(R.id.positive_button);
        Button buttonNegative = dialogView.findViewById(R.id.negative_button);

        RecyclerView airportOptions = dialogView.findViewById(R.id.airports_recycler_view);
        airportOptions.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        airportOptions.setLayoutManager(layoutManager);

        switch (v.getId()) {
            case R.id.depart_airport_edit_text:
                mDepartClickCount = 0;
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
            case R.id.return_airport_edit_text:
                mReturnClickCount = 0;
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

        handleAlertDialogInWideScreen(alertDialog);
    }

    private void handleAlertDialogInWideScreen(AlertDialog alertDialog) {
        if (mIsWideScreen) {
            WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
            params.width = 900;
            alertDialog.getWindow().setAttributes(params);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onAirportClick(Airport airport, int adapterPosition, int editTextAirportId) {
        switch (editTextAirportId) {
            case R.id.depart_airport_edit_text:
                mDepartClickCount++;
                mSelectedDepartAirport = airport;
                mSelectedDepartAirportPosition = adapterPosition;
                break;
            case R.id.return_airport_edit_text:
                mReturnClickCount++;
                mSelectedReturnAirport = airport;
                mSelectedReturnAirportPosition = adapterPosition;
                break;
        }
    }

//    TODO: Figure if this is okay so that the two ClickListener handlers below can be replaced
//    private void setOnClickListenerToDate(View view) {
//        view.setOnClickListener(new View.OnClickListener() {

    private void setOnClickListenerToDate(EditText date) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(v);
            }
        });
    }

    private void setOnClickListenerToDate(ImageView image) {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(v);
            }
        });
    }

    private void openDatePickerDialog(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyDatePickerDialogTheme);     // Reference: https://stackoverflow.com/questions/41456444/extra-padding-margin-added-to-datepicker-on-android-7-1-1
        final View dialogView = getLayoutInflater().inflate(R.layout.custom_date_picker_dialog, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        final DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
        CheckBox oneWayFlightCheckBox = dialogView.findViewById(R.id.one_way_flight_checkbox);
        if (mIsOneWayFlight) {
            oneWayFlightCheckBox.setChecked(true);
        }
        Button buttonPositive = dialogView.findViewById(R.id.positive_button);
        Button buttonNegative = dialogView.findViewById(R.id.negative_button);

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        datePicker.setMinDate(calendar.getTimeInMillis());

        switch (v.getId()) {
            case R.id.depart_date_edit_text:
            case R.id.plane_depart_image_view:
                oneWayFlightCheckBox.setVisibility(View.INVISIBLE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int dayOfMonth = datePicker.getDayOfMonth();
                        int monthOfYear = datePicker.getMonth();
                        int year = datePicker.getYear();
                        String selectedDate = getDateInDayMonthFormat(datePicker.getDayOfMonth(),
                                datePicker.getMonth() + 1, datePicker.getYear());
                        String[] dateTokens = selectedDate.split(",");
                        String dayMonth = dateTokens[0];
                        mDepartureDayString = dateTokens[1];
                        departDate.setText(dayMonth);
                        departDate.setTextColor(Color.parseColor("#323232"));
                        departDay.setText(mDepartureDayString);
                        mDepartureDate = getDateFromString(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        mDepartureDateString = getDateInDayMonthYearFormat(dayOfMonth, monthOfYear + 1, year);
                        alertDialog.dismiss();
                    }
                });
                break;
            case R.id.return_date_edit_text:
            case R.id.plane_return_image_view:
                oneWayFlightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
//                            datePicker.setMinDate(System.currentTimeMillis());
//                            datePicker.setMaxDate(System.currentTimeMillis());
                            mIsOneWayFlight = true;
                        } else {
                            mIsOneWayFlight = false;
                        }
                    }
                });
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!oneWayFlightCheckBox.isChecked()) {
                            int dayOfMonth = datePicker.getDayOfMonth();
                            int monthOfYear = datePicker.getMonth();
                            int year = datePicker.getYear();
                            String selectedDate = getDateInDayMonthFormat(datePicker.getDayOfMonth(),
                                    datePicker.getMonth() + 1, datePicker.getYear());
                            String[] dateTokens = selectedDate.split(",");
                            String dayMonth = dateTokens[0];
                            mReturnDayString = dateTokens[1];
                            returnDate.setText(dayMonth);
                            returnDate.setTextColor(Color.parseColor("#323232"));
                            returnDay.setText(mReturnDayString);
                            mReturnDate = getDateFromString(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            mReturnDateString = getDateInDayMonthYearFormat(dayOfMonth, monthOfYear + 1, year);
                            alertDialog.dismiss();
                        } else {
                            returnDate.setText("SELECT");
                            returnDate.setTextColor(Color.parseColor("#1FCBF2"));
                            mReturnDayString = "ONE-WAY";
                            returnDay.setText(mReturnDayString);
                            mReturnDate = null;
                            mReturnDateString = null;
                            alertDialog.dismiss();
                        }
                    }
                });
                break;
        }
        alertDialog.show();
    }

    public void onSearchButtonClick(View v) {
        boolean hasErrors = false;
        List errorMessageList = new ArrayList();

        Intent intent = new Intent(this, SearchResultsActivity.class);


//        TODO: Check if values are null
        if (mDepartureDateString != null) {
            intent.putExtra("departureDate", mDepartureDateString);
        } else {
            hasErrors = true;
            errorMessageList.add("Departure date is missing");
        }

        if (mReturnDate != null && mReturnDate.before(mDepartureDate)) {
            hasErrors = true;
            errorMessageList.add("Departure date falls after return date");
        } else {
            intent.putExtra("returnDate", mReturnDateString);
        }

        if (mChosenDepartAirport != null) {
            intent.putExtra("departureAirport", mChosenDepartAirport.getIataCode());
        } else {
            hasErrors = true;
            errorMessageList.add("Departure airport is missing");
        }

        if (mChosenReturnAirport != null) {
            intent.putExtra("returnAirport", mChosenReturnAirport.getIataCode());
        } else {
            hasErrors = true;
            errorMessageList.add("Arrival airport is missing");
        }

        if (hasErrors) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            final View errorDialogView = getLayoutInflater().inflate(R.layout.error_dialog, null);
            dialogBuilder.setView(errorDialogView);
            AlertDialog errorAlertDialog = dialogBuilder.create();

            ArrayAdapter errorAdapter = new ArrayAdapter<String>(this,
                    R.layout.error_list_item, errorMessageList);
            ListView errorMessageListView = errorDialogView.findViewById(R.id.error_list_view);
            errorMessageListView.setAdapter(errorAdapter);

            Button button = errorDialogView.findViewById(R.id.positive_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    errorAlertDialog.dismiss();
                }
            });
            errorAlertDialog.show();
            handleAlertDialogInWideScreen(errorAlertDialog);
            return;
        }

        if (mReturnDate == null) {
            returnDay.setText("ONE-WAY");
        }

        int selectedFlightSearchOptionId = flightSearchOptionsRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedFlightSearchOptionButton = (RadioButton) findViewById(selectedFlightSearchOptionId);
        mFlightSearchOption = ((String) selectedFlightSearchOptionButton.getText()).toLowerCase();
        intent.putExtra("flightOption", mFlightSearchOption);

        startActivity(intent);
    }
}