package com.udacity.findaflight.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.findaflight.R;
import com.udacity.findaflight.adapters.FlightListAdapter;
import com.udacity.findaflight.data.FlightSearchResult;
import com.udacity.findaflight.utils.JsonUtils;
import com.udacity.findaflight.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.findaflight.utils.NetworkUtils.isOnline;

public class FlightListFragment extends Fragment implements
        FlightListAdapter.FlightListAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<FlightSearchResult>> {

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
    private List<FlightSearchResult> mFlights;

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

    private void retrieveAndAssignFlightInfo(Bundle bundle) {
        mDepartureDate = bundle.getString("departureDate");
        mDepartureAirport = bundle.getString("departureAirport");
        mReturnDate = bundle.getString("returnDate");
        mReturnAirport = bundle.getString("returnAirport");
        mFlightOption = bundle.getString("flightOption");
    }

    private void initLoader() {
        int loaderId = FLIGHT_LOADER_ID;
        LoaderManager.LoaderCallbacks<List<FlightSearchResult>> callback = this;
        Bundle bundleForLoader = new Bundle();
        getLoaderManager().initLoader(loaderId, bundleForLoader, callback);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // TODO: implement save instance state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onFlightClick(FlightSearchResult flight) {
        System.out.println("CLICKED");
    }

    @NonNull
    @Override
    public Loader<List<FlightSearchResult>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<FlightSearchResult>>(getActivity()) {
            List<FlightSearchResult> mFlightData = null;

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
            public List<FlightSearchResult> loadInBackground() {
                String[] arguments = new String[]{"US", "USD", "en-US", "LAX-sky", "SFO-sky", "2019-09-01"};
                URL flightsRequestUrl = NetworkUtils.buildUrl("", "2019-12-01", arguments);
//                URL flightsRequestUrl = NetworkUtils.buildUrl("", mReturnDate, arguments);

                try {
                    String jsonFlightsResponse = NetworkUtils.getResponseFromHttpUrl(flightsRequestUrl);
                    List<FlightSearchResult> listFlightData = JsonUtils.getFlightsByPriceFromJson(jsonFlightsResponse);

                    return listFlightData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(List<FlightSearchResult> data) {
                mFlightData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<FlightSearchResult>> loader, List<FlightSearchResult> flights) {
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
    public void onLoaderReset(@NonNull Loader<List<FlightSearchResult>> loader) {

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
