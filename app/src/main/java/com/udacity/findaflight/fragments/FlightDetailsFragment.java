package com.udacity.findaflight.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.udacity.findaflight.R;
import com.udacity.findaflight.RecyclerViewMargin;
import com.udacity.findaflight.adapters.FlightRouteAdapter;
import com.udacity.findaflight.data.FlightRoute;
import com.udacity.findaflight.data.FlightSearchResult;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.findaflight.utils.DateUtils.getDateMonthYear;
import static com.udacity.findaflight.utils.DateUtils.getDayOfWeekMonthDay;
import static com.udacity.findaflight.utils.DateUtils.getHoursMinutesFromSeconds;

public class FlightDetailsFragment extends Fragment {

    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.collapsing_layout)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.flight_journey)
    TextView mFlightJourneyTextView;
    @BindView(R.id.flight_period)
    TextView mFlightPeriodTextView;
    @BindView(R.id.flight_price)
    TextView mFlightPriceTextView;
    @BindView(R.id.flight_routes_recyclerview_outbound)
    RecyclerView mOutboundRoutesRecyclerView;
    @BindView(R.id.header_flights_outbound)
    TextView mHeaderFlightsOutboundTextView;
    @BindView(R.id.from_to_airports_outbound)
    TextView mFromToAirportsOutboundTextView;
    @BindView(R.id.day_date_outbound)
    TextView mDayDateOutboundTextView;
    @BindView(R.id.duration_direct_outbound)
    TextView mDurationDirectOutboundTextView;
    @BindView(R.id.divider)
    View mDividerView;
    @BindView(R.id.flight_routes_recyclerview_inbound)
    RecyclerView mInboundRoutesRecyclerView;
    @BindView(R.id.header_flights_inbound)
    TextView mHeaderFlightsInboundTextView;
    @BindView(R.id.from_to_airports_inbound)
    TextView mFromToAirportsInboundTextView;
    @BindView(R.id.day_date_inbound)
    TextView mDayDateInboundTextView;
    @BindView(R.id.duration_direct_inbound)
    TextView mDurationDirectInboundTextView;

    private FlightRouteAdapter mOutboundRoutesAdapter;
    private GridLayoutManager mOutboundRoutesLayoutManager;
    private FlightRouteAdapter mInboundRoutesAdapter;
    private GridLayoutManager mInboundRoutesLayoutManager;

    private FlightSearchResult mFlightSearchResult;
    private List<FlightRoute> mOutboundFlightRoutes;
    private List<FlightRoute> mInboundFlightRoutes;
    private boolean mIsDirectOutbound;
    private boolean mIsDirectInbound;
    private boolean mIsReturn;

    private String mFlightJourney;
    private Date mOutboundDepartureDateTime;
    private Date mInboundArrivalDateTime;

    public FlightDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        retrieveAndAssignFlightSearchResultDetails(bundle);
        View view = inflater.inflate(R.layout.fragment_flight_details, container, false);
        ButterKnife.bind(this, view);

        handleOutboundViews();

        if (!mInboundFlightRoutes.isEmpty()) {
            mIsReturn = true;
            showDividerAndInboundTripDetails();
            handleInboundViews();
        }

        handleExpandedToolbarViews();

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShown = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    mCollapsingToolbar.setTitle(mFlightJourney);
                    isShown = true;
                } else if (isShown) {
                    mCollapsingToolbar.setTitle(" ");
                    isShown = false;
                }
            }
        });

        return view;
    }

    private void handleExpandedToolbarViews() {
        mFlightJourneyTextView.setText(mFlightJourney);
        mFlightPeriodTextView.setText(getDateMonthYear(mOutboundDepartureDateTime)
                + (mIsReturn ? " \u2015 " + getDateMonthYear(mInboundArrivalDateTime) : ""));
        mFlightPriceTextView.setText("$" + Integer.toString(mFlightSearchResult.getPrice()));
    }

    private void retrieveAndAssignFlightSearchResultDetails(Bundle bundle) {
        mFlightSearchResult = bundle.getParcelable("flight");
        mOutboundFlightRoutes = mFlightSearchResult.getOutboundFlightRoutes();
        mInboundFlightRoutes = mFlightSearchResult.getInboundFlightRoutes();
    }

    private void handleOutboundViews() {
        // RecyclerView
        mOutboundRoutesLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
        mOutboundRoutesRecyclerView.setLayoutManager(mOutboundRoutesLayoutManager);
        mOutboundRoutesRecyclerView.setHasFixedSize(true);
        mOutboundRoutesAdapter = new FlightRouteAdapter(mOutboundFlightRoutes);
        mOutboundRoutesRecyclerView.setAdapter(mOutboundRoutesAdapter);

        FlightRoute outboundDepartureFlight = mOutboundFlightRoutes.get(0);
        FlightRoute outboundArrivalFlight = mOutboundFlightRoutes.get(mOutboundFlightRoutes.size() - 1);

        if (mOutboundFlightRoutes.size() == 1) {
            mIsDirectOutbound = true;
        }

        String outboundDepartureAirport = outboundDepartureFlight.getDepartureAirport();
        String outboundArrivalAirport = outboundArrivalFlight.getArrivalAirport();
        mFlightJourney = outboundDepartureAirport + " \u2015 " + outboundArrivalAirport;

        String outboundDepartureCity = outboundDepartureFlight.getDepartureCity();
        String outboundArrivalCity = outboundArrivalFlight.getArrivalCity();
        mFromToAirportsOutboundTextView.setText(
                outboundDepartureCity +
                        " \u2015 " +
                        outboundArrivalCity
        );

        mOutboundDepartureDateTime = outboundDepartureFlight.getDepartureDateTime();
        mDayDateOutboundTextView.setText(
                getDayOfWeekMonthDay(mOutboundDepartureDateTime) +
                        " \u2015 " +
                        getDayOfWeekMonthDay(outboundArrivalFlight.getArrivalDateTime())
        );

        Date outboundDepartureDateTimeUTC = outboundDepartureFlight.getDepartureDateTimeUTC();
        Date outboundArrivalDateTimeUTC = outboundArrivalFlight.getArrivalDateTimeUTC();
        long msDifference = outboundArrivalDateTimeUTC.getTime() - outboundDepartureDateTimeUTC.getTime();
        mDurationDirectOutboundTextView.setText(getHoursMinutesFromSeconds(msDifference / 1000) + (mIsDirectOutbound ? ", direct" : ""));
    }

    private void handleInboundViews() {
        mInboundRoutesLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
        mInboundRoutesRecyclerView.setLayoutManager(mInboundRoutesLayoutManager);
        mInboundRoutesRecyclerView.setHasFixedSize(true);
        mInboundRoutesAdapter = new FlightRouteAdapter(mInboundFlightRoutes);
        mInboundRoutesRecyclerView.setAdapter(mInboundRoutesAdapter);

        FlightRoute inboundDepartureFlight = mInboundFlightRoutes.get(0);
        FlightRoute inboundArrivalFlight = mInboundFlightRoutes.get(mInboundFlightRoutes.size() - 1);

        if (mInboundFlightRoutes.size() == 1) {
            mIsDirectInbound = true;
        }

        String inboundDepartureCity = inboundDepartureFlight.getDepartureCity();
        String inboundArrivalCity = inboundArrivalFlight.getArrivalCity();
        mFromToAirportsInboundTextView.setText(
                inboundDepartureCity +
                        " \u2015 " +
                        inboundArrivalCity
        );

        mInboundArrivalDateTime = inboundArrivalFlight.getArrivalDateTime();
        mDayDateInboundTextView.setText(
                getDayOfWeekMonthDay(inboundDepartureFlight.getDepartureDateTime()) +
                        " \u2015 " +
                        getDayOfWeekMonthDay(mInboundArrivalDateTime)
        );

        Date inboundDepartureDateTimeUTC = inboundDepartureFlight.getDepartureDateTimeUTC();
        Date inboundArrivalDateTimeUTC = inboundArrivalFlight.getArrivalDateTimeUTC();
        long msDifference = inboundArrivalDateTimeUTC.getTime() - inboundDepartureDateTimeUTC.getTime();
        mDurationDirectInboundTextView.setText(getHoursMinutesFromSeconds(msDifference / 1000) + (mIsDirectInbound ? ", direct" : ""));
    }

    private void showDividerAndInboundTripDetails() {
        mDividerView.setVisibility(View.VISIBLE);
        mHeaderFlightsInboundTextView.setVisibility(View.VISIBLE);
        mFromToAirportsInboundTextView.setVisibility(View.VISIBLE);
        mDayDateInboundTextView.setVisibility(View.VISIBLE);
        mDurationDirectInboundTextView.setVisibility(View.VISIBLE);
        mInboundRoutesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
