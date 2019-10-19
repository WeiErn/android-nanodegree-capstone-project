package com.udacity.findaflight.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.findaflight.R;
import com.udacity.findaflight.adapters.FlightRouteAdapter;
import com.udacity.findaflight.data.FlightRoute;
import com.udacity.findaflight.data.FlightSearchResult;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.findaflight.utils.DateUtils.getDayofweekMonthDay;
import static com.udacity.findaflight.utils.DateUtils.getHoursMinutesFromSeconds;

public class FlightDetailsFragment extends Fragment {

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

        // Inbound
        if (!mInboundFlightRoutes.isEmpty()) {
            showDividerAndInboundTripDetails();
            mInboundRoutesLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
            mInboundRoutesRecyclerView.setLayoutManager(mInboundRoutesLayoutManager);
            mInboundRoutesRecyclerView.setHasFixedSize(true);
            mInboundRoutesAdapter = new FlightRouteAdapter(mInboundFlightRoutes);
            mInboundRoutesRecyclerView.setAdapter(mInboundRoutesAdapter);
        }
        return view;
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
        String outboundDepartureCity = outboundDepartureFlight.getDepartureCity();
        String outboundArrivalAirport = outboundArrivalFlight.getArrivalAirport();
        String outboundArrivalCity = outboundArrivalFlight.getArrivalCity();
        mFromToAirportsOutboundTextView.setText(
                outboundDepartureCity +
                        " \u2015 " +
                        outboundArrivalCity
        );

        mDayDateOutboundTextView.setText(
                getDayofweekMonthDay(outboundDepartureFlight.getDepartureDateTime()) +
                        " \u2015 " +
                        getDayofweekMonthDay(outboundArrivalFlight.getArrivalDateTime())
        );

        Date outboundDepartureDateTimeUTC = outboundDepartureFlight.getDepartureDateTimeUTC();
        Date outboundArrivalDateTimeUTC = outboundArrivalFlight.getArrivalDateTimeUTC();
        long msDifference = outboundArrivalDateTimeUTC.getTime() - outboundDepartureDateTimeUTC.getTime();
        mDurationDirectOutboundTextView.setText(getHoursMinutesFromSeconds(msDifference / 1000) + (mIsDirectOutbound ? ", direct" : ""));
    }

    private void showDividerAndInboundTripDetails() {
        mDividerView.setVisibility(View.VISIBLE);
        mHeaderFlightsInboundTextView.setVisibility(View.VISIBLE);
        mFromToAirportsInboundTextView.setVisibility(View.VISIBLE);
        mDayDateInboundTextView.setVisibility(View.VISIBLE);
        mDurationDirectInboundTextView.setVisibility(View.VISIBLE);
        mInboundRoutesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void retrieveAndAssignFlightSearchResultDetails(Bundle bundle) {
        mFlightSearchResult = bundle.getParcelable("flight");
        mOutboundFlightRoutes = mFlightSearchResult.getOutboundFlightRoutes();
        mInboundFlightRoutes = mFlightSearchResult.getInboundFlightRoutes();
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
