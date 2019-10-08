package com.udacity.findaflight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.findaflight.R;

import butterknife.BindView;

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.AirportViewHolder> {

    private String[] mAirports;
    private final AirportAdapterOnClickHandler mClickHandler;

    public interface AirportAdapterOnClickHandler {
        void onAirportClick(String airport);
//        void onAirportClick(Airport airport);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class AirportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public final TextView airportTextView;

        public AirportViewHolder(View v) {
            super(v);
            airportTextView = v.findViewById(R.id.airport_name);
            airportTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onAirportClick(mAirports[adapterPosition]);
        }
    }

    public AirportAdapter(String[] airports, AirportAdapterOnClickHandler clickHandler) {
        mAirports = airports;
        mClickHandler = clickHandler;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AirportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        // Create a new view
        View v = LayoutInflater.from(context)
                .inflate(R.layout.airport_recyclerview_row, viewGroup, false);

        return new AirportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AirportAdapter.AirportViewHolder holder, int position) {
        holder.airportTextView.setText(mAirports[position]);
    }

    @Override
    public int getItemCount() {
        return mAirports.length;
    }
}
