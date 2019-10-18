package com.udacity.findaflight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.findaflight.R;
import com.udacity.findaflight.data.FlightRoute;

import java.util.List;

import butterknife.ButterKnife;

public class FlightRouteAdapter extends RecyclerView.Adapter<FlightRouteAdapter.FlightRouteAdapterViewHolder> {

    public static final String TAG = FlightRouteAdapter.class.getSimpleName();
    private List<FlightRoute> mFlightRouteData;

    public FlightRouteAdapter(List<FlightRoute> flightRoutes) {
        mFlightRouteData = flightRoutes;
    }

    public class FlightRouteAdapterViewHolder extends RecyclerView.ViewHolder {

        public FlightRouteAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public FlightRouteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.flight_route_on_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FlightRouteAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlightRouteAdapterViewHolder holder, int position) {
        FlightRoute flightRoute = mFlightRouteData.get(position);

        String departureDate = flightRoute.getDepartureDate();
        String departureTime = flightRoute.getDepartureTime();
        String departureAirport = flightRoute.getDepartureAirport();

        String arrivalDate = flightRoute.getArrivalDate();
        String arrivalTime = flightRoute.getArrivalTime();
        String arrivalAirport = flightRoute.getArrivalAirport();

        String operatingCarrier = flightRoute.getOperatingCarrier();
        String operatingFlightNum = flightRoute.getOperatingFlightNum();
    }

    @Override
    public int getItemCount() {
        if (null == mFlightRouteData) return 0;
        return mFlightRouteData.size();
    }

    public void setFlightRouteDate(List<FlightRoute> flightRouteData) {
        mFlightRouteData = flightRouteData;
        notifyDataSetChanged();
    }
}
