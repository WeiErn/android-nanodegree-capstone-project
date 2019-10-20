package com.udacity.findaflight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.findaflight.R;
import com.udacity.findaflight.data.FlightRoute;
import com.udacity.findaflight.data.FlightSearchResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.findaflight.utils.DateUtils.getDateMonthYear;

public class FlightListAdapter extends RecyclerView.Adapter<FlightListAdapter.FlightListAdapterViewHolder> {

    public static final String TAG = FlightListAdapter.class.getSimpleName();
    private List<FlightSearchResult> mFlightData;
    private final FlightListAdapterOnClickHandler mClickHandler;

    public interface FlightListAdapterOnClickHandler {
        void onFlightClick(FlightSearchResult flight);
    }

    public FlightListAdapter(FlightListAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class FlightListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.travel_period)
        public TextView mTravelPeriod;
        @BindView(R.id.airlines)
        public TextView mAirline;
        @BindView(R.id.outbound_start_airport)
        public TextView mOutboundStartAirport;
        @BindView(R.id.outbound_start_time)
        public TextView mOutboundStartTime;
        @BindView(R.id.outbound_end_airport)
        public TextView mOutboundEndAirport;
        @BindView(R.id.outbound_end_time)
        public TextView mOutboundEndTime;
        @BindView(R.id.inbound_start_airport)
        public TextView mInboundStartAirport;
        @BindView(R.id.inbound_start_time)
        public TextView mInboundStartTime;
        @BindView(R.id.inbound_end_airport)
        public TextView mInboundEndAirport;
        @BindView(R.id.inbound_end_time)
        public TextView mInboundEndTime;
        @BindView(R.id.inbound_arrow)
        public ImageView mInboundArrow;
        @BindView(R.id.price)
        public TextView mPrice;

        public FlightListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onFlightClick(mFlightData.get(adapterPosition));
        }
    }

    @NonNull
    @Override
    public FlightListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.fragment_flight_on_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FlightListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightListAdapterViewHolder holder, int position) {
        FlightSearchResult flightSelected = mFlightData.get(position);

        String travelPeriod = getDateMonthYear(flightSelected.getDepartureDateTime());

        // Set airlines
        setAirlinesText(holder, flightSelected);

        // Set outbound details
        holder.mOutboundStartTime.setText(flightSelected.getDepartureTime());
        holder.mOutboundStartAirport.setText(flightSelected.getDepartureAirport());
        holder.mOutboundEndTime.setText(flightSelected.getArrivalTime());
        holder.mOutboundEndAirport.setText(flightSelected.getArrivalAirport());

        holder.mPrice.setText("$" + Integer.toString(flightSelected.getPrice()));

        // Set inbound details
        List<FlightRoute> inboundFlightRoutes = flightSelected.getInboundFlightRoutes();
        if (!inboundFlightRoutes.isEmpty()) {
            FlightRoute firstInboundFlight = inboundFlightRoutes.get(0);
            FlightRoute lastInboundFlight = inboundFlightRoutes.get(inboundFlightRoutes.size() - 1);
            travelPeriod += " \u2015 " + getDateMonthYear(lastInboundFlight.getArrivalDateTime());
            holder.mTravelPeriod.setText(travelPeriod);

            makeInboundDetailsVisible(holder);
            holder.mInboundStartTime.setText(firstInboundFlight.getDepartureTime());
            holder.mInboundStartAirport.setText(firstInboundFlight.getDepartureAirport());
            holder.mInboundEndTime.setText(lastInboundFlight.getArrivalTime());
            holder.mInboundEndAirport.setText(lastInboundFlight.getArrivalAirport());
        }
    }

    private void setAirlinesText(@NonNull FlightListAdapterViewHolder holder, FlightSearchResult flightSelected) {
        String airlines = "";
        List<String> airlinesList = flightSelected.getAirlines();
        for (String airline : airlinesList) {
            airlines += airline + ",";
        }
        airlines = airlines.substring(0, airlines.length() - 1);
        holder.mAirline.setText(airlines);
    }

    private void makeInboundDetailsVisible(FlightListAdapterViewHolder holder) {
        holder.mInboundStartTime.setVisibility(View.VISIBLE);
        holder.mInboundStartAirport.setVisibility(View.VISIBLE);
        holder.mInboundEndTime.setVisibility(View.VISIBLE);
        holder.mInboundEndAirport.setVisibility(View.VISIBLE);
        holder.mInboundArrow.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if (null == mFlightData) return 0;
        return mFlightData.size();
    }

    public void setFlightData(List<FlightSearchResult> flightData) {
        mFlightData = flightData;
        notifyDataSetChanged();
    }
}
