package com.udacity.findaflight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.findaflight.R;
import com.udacity.findaflight.data.FlightRoute;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.findaflight.utils.DateUtils.getHoursMinutesFromSeconds;

public class FlightRouteAdapter extends RecyclerView.Adapter<FlightRouteAdapter.FlightRouteAdapterViewHolder> {

    public static final String TAG = FlightRouteAdapter.class.getSimpleName();
    private List<FlightRoute> mFlightRouteData;

    public FlightRouteAdapter(List<FlightRoute> flightRoutes) {
        mFlightRouteData = flightRoutes;
    }

    public class FlightRouteAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.departure_date)
        public TextView mDepartureDate;
        @BindView(R.id.departure_time)
        public TextView mDepartureTime;
        @BindView(R.id.flight_duration)
        public TextView mFlightDuration;
        @BindView(R.id.arrival_date)
        public TextView mArrivalDate;
        @BindView(R.id.arrival_time)
        public TextView mArrivalTime;
        @BindView(R.id.departure_airport)
        public TextView mDepartureAirport;
        @BindView(R.id.flight_operating_number)
        public TextView mFlightOperatingNumber;
        @BindView(R.id.arrival_airport)
        public TextView mArrivalAirport;

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

        holder.mDepartureDate.setText(flightRoute.getDepartureDate());
        holder.mDepartureTime.setText(flightRoute.getDepartureTime());
        holder.mDepartureAirport.setText(flightRoute.getDepartureAirport());

        holder.mArrivalDate.setText(flightRoute.getArrivalDate());
        holder.mArrivalTime.setText(flightRoute.getArrivalTime());
        holder.mArrivalAirport.setText(flightRoute.getArrivalAirport());

        String operatingCarrier = flightRoute.getOperatingCarrier();
        String operatingFlightNum = flightRoute.getOperatingFlightNum();
        holder.mFlightOperatingNumber.setText(operatingCarrier + " " + operatingFlightNum);

        long msDifference = flightRoute.getArrivalDateTimeUTC().getTime() - flightRoute.getDepartureDateTimeUTC().getTime();
        holder.mFlightDuration.setText(getHoursMinutesFromSeconds(msDifference/1000));
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
