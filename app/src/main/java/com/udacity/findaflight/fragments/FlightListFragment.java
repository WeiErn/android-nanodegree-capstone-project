package com.udacity.findaflight.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.findaflight.FlightDetailsActivity;
import com.udacity.findaflight.R;
import com.udacity.findaflight.adapters.FlightListAdapter;
import com.udacity.findaflight.data.FlightSearchResult;
import com.udacity.findaflight.utils.JsonUtils;
import com.udacity.findaflight.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.findaflight.utils.NetworkUtils.isOnline;

public class FlightListFragment extends Fragment implements
        FlightListAdapter.FlightListAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<ArrayList<FlightSearchResult>> {

    @BindView(R.id.flight_list_toolbar)
    Toolbar mFlightListToolbar;
    @BindView(R.id.list_flights)
    RecyclerView mFlightListRecyclerView;
    @BindView(R.id.no_internet_connection_message_display)
    TextView mNoInternetMessageDisplay;
    @BindView(R.id.error_message_display)
    TextView mErrorMessageDisplay;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    private FlightListAdapter mFlightListAdapter;
    private GridLayoutManager mLayoutManager;

    private String mDepartureDate;
    private String mReturnDate;
    private String mDepartureAirport;
    private String mReturnAirport;
    private String mFlightOption;

    public static final int FLIGHT_LOADER_ID = 0;
    public static final String ARG_FLIGHT_LAYOUT_ID = "flight_layout";
    private int mFlightLayoutId;
    private ArrayList<FlightSearchResult> mFlights;
    OnFlightResultClickListener mCallback;

    public interface OnFlightResultClickListener {
        void onFlightResultClick(FlightSearchResult flight);
    }

    public FlightListFragment() {
    }

    public static FlightListFragment newInstance(String flightLayoutId) {
        FlightListFragment fragment = new FlightListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FLIGHT_LAYOUT_ID, flightLayoutId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's state here
            mFlights = savedInstanceState.getParcelableArrayList("flights");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFlightLayoutId = getArguments().getInt(ARG_FLIGHT_LAYOUT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mFlightLayoutId = getArguments().getInt(ARG_FLIGHT_LAYOUT_ID);
        Bundle bundle = getArguments();
        retrieveAndAssignFlightInfo(bundle);

        View view = inflater.inflate(R.layout.fragment_flight_list, container, false);
        ButterKnife.bind(this, view);

        setupActionBar();

        mLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);

        mFlightListRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mFlightListRecyclerView.setLayoutManager(mLayoutManager);
        mFlightListRecyclerView.setHasFixedSize(true);
        mFlightListAdapter = new FlightListAdapter(this);
        mFlightListRecyclerView.setAdapter(mFlightListAdapter);


        if (!isOnline(getActivity())) {
            showNoInternetConnectionMessage();
        } else {
            initLoader();
        }

        return view;
    }

    private void setupActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mFlightListToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mDepartureAirport + " \u2015 " + mReturnAirport);
        mFlightListToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mFlightListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void retrieveAndAssignFlightInfo(Bundle bundle) {
        mDepartureDate = bundle.getString("departureDate");
        mDepartureAirport = bundle.getString("departureAirport");
        mReturnDate = bundle.getString("returnDate");
        mReturnAirport = bundle.getString("returnAirport");
        mFlightOption = bundle.getString("flightOption");
    }

    private void initLoader() {
        int loaderId = FLIGHT_LOADER_ID;
        LoaderManager.LoaderCallbacks<ArrayList<FlightSearchResult>> callback = this;
        Bundle bundleForLoader = new Bundle();
        getLoaderManager().initLoader(loaderId, bundleForLoader, callback);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnFlightResultClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // TODO: implement save instance state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("flights", mFlights);
    }

    @Override
    public void onFlightClick(FlightSearchResult flight) {
        mCallback.onFlightResultClick(flight);
    }

    @NonNull
    @Override
    public Loader<ArrayList<FlightSearchResult>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<FlightSearchResult>>(getActivity()) {
            ArrayList<FlightSearchResult> mFlightData = null;

            @Override
            protected void onStartLoading() {
                if (mFlightData != null) {
                    deliverResult(mFlightData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public ArrayList<FlightSearchResult> loadInBackground() {
//                String[] arguments = new String[]{"US", "USD", "en-US", "LAX-sky", "SFO-sky", "2019-09-01"};
//                URL flightsRequestUrl = NetworkUtils.buildUrl("", mReturnDate, arguments);
                URL flightsRequestUrl = NetworkUtils.buildUrl(mDepartureAirport, mReturnAirport, mDepartureDate, mReturnDate, mFlightOption);

                try {
                    String jsonFlightsResponse = NetworkUtils.getResponseFromHttpUrl(flightsRequestUrl);
                    ArrayList<FlightSearchResult> listFlightData = JsonUtils.getFlightsByPriceFromJson(jsonFlightsResponse);

                    return listFlightData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(ArrayList<FlightSearchResult> data) {
                mFlightData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<FlightSearchResult>> loader, ArrayList<FlightSearchResult> flights) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mFlights = flights;
        mFlightListAdapter.setFlightData(flights);
        if (!isOnline(getActivity())) {
            showNoInternetConnectionMessage();
        } else if (flights == null) {
            showErrorMessage();
        } else {
            showFlightListView();
        }
        getLoaderManager().destroyLoader(FLIGHT_LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<FlightSearchResult>> loader) {

    }


    private void showFlightListView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mFlightListRecyclerView.setVisibility(View.VISIBLE);
        mNoInternetMessageDisplay.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mFlightListRecyclerView.setVisibility(View.INVISIBLE);
        mNoInternetMessageDisplay.setVisibility(View.INVISIBLE);
    }

    private void showNoInternetConnectionMessage() {
        mNoInternetMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mFlightListRecyclerView.setVisibility(View.INVISIBLE);
    }
}
