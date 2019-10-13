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
import com.udacity.findaflight.data.Flight;

import java.util.List;

public class FlightListAdapter extends RecyclerView.Adapter<FlightListAdapter.FlightListAdapterViewHolder> {

    public static final String TAG = FlightListAdapter.class.getSimpleName();
    private List<Flight> mFlightData;
    private final FlightListAdapterOnClickHandler mClickHandler;

    public interface FlightListAdapterOnClickHandler {
        void onFlightClick(Flight flight);
    }

    public FlightListAdapter(FlightListAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class FlightListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mAirline;
        public ImageView airlineIcon;
        public TextView mDepartureStart;
        public TextView mDepartureEnd;
        public TextView mReturnStart;
        public TextView mReturnEnd;

        public FlightListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mAirline = itemView.findViewById(R.id.airline);

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
        Flight flightSelected = mFlightData.get(position);
        holder.mAirline.setText(flightSelected.getAirline());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setFlightData(List<Flight> flightData) {
        mFlightData = flightData;
        notifyDataSetChanged();
    }
}
