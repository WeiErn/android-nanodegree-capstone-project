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

        @BindView(R.id.airlines)
        public TextView mAirline;
        public ImageView airlineIcon;
        @BindView(R.id.outboundStartAirport)
        public TextView mOutboundStartAirport;
        @BindView(R.id.outboundStartTime)
        public TextView mOutboundStartTime;
        @BindView(R.id.outboundEndAirport)
        public TextView mOutboundEndAirport;
        @BindView(R.id.outboundEndTime)
        public TextView mOutboundEndTime;
        @BindView(R.id.inboundStartAirport)
        public TextView mInboundStartAirport;
        @BindView(R.id.inboundStartTime)
        public TextView mInboundStartTime;
        @BindView(R.id.inboundEndAirport)
        public TextView mInboundEndAirport;
        @BindView(R.id.inboundEndTime)
        public TextView mInboundEndTime;
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

        holder.mOutboundStartTime.setText(flightSelected.getDepartureTime());
        holder.mOutboundStartAirport.setText(flightSelected.getDepartureAirport());
        holder.mOutboundEndTime.setText(flightSelected.getArrivalTime());
        holder.mOutboundEndAirport.setText(flightSelected.getArrivalAirport());
        holder.mPrice.setText("$" + Integer.toString(flightSelected.getPrice()));

        String airlines = "";
        List<String> airlinesList = flightSelected.getAirlines();
        for (String airline : airlinesList) {
            airlines += airline + ",";
        }
        airlines = airlines.substring(0, airlines.length() - 1);
        holder.mAirline.setText(airlines);

        List<FlightRoute> inboundFlightRoutes = flightSelected.getInboundFlightRoutes();
        if (!inboundFlightRoutes.isEmpty()) {
            FlightRoute firstInboundFlight = inboundFlightRoutes.get(0);
            FlightRoute lastInboundFlight = inboundFlightRoutes.get(inboundFlightRoutes.size() - 1);

            holder.mInboundStartTime.setText(firstInboundFlight.getDepartureTime());
            holder.mInboundStartAirport.setText(firstInboundFlight.getDepartureAirport());
            holder.mInboundEndTime.setText(lastInboundFlight.getArrivalTime());
            holder.mInboundEndAirport.setText(lastInboundFlight.getArrivalAirport());
        } else {
            // set return arrow to GONE
        }
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
