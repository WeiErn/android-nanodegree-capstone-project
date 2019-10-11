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
import com.udacity.findaflight.data.Airport;

import java.util.List;

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.AirportViewHolder> {

    private int mEditTextAirportId;
    private List<Airport> mAirports;
    private final AirportAdapterOnClickHandler mClickHandler;
    private int mCheckedPosition = -1;

    public interface AirportAdapterOnClickHandler {
        void onAirportClick(Airport airport, int adapterPosition, int editTextAirportId);
    }

    public class AirportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView airportTextView;
        public final ImageView tickImageView;

        public AirportViewHolder(View view) {
            super(view);
            airportTextView = view.findViewById(R.id.airport_name);
            tickImageView = view.findViewById(R.id.imageview_tick);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            tickImageView.setVisibility(View.VISIBLE);
            if (mCheckedPosition != getAdapterPosition()) {
                notifyItemChanged(mCheckedPosition);
                mCheckedPosition = getAdapterPosition();
            }
            int adapterPosition = getAdapterPosition();
            mClickHandler.onAirportClick(mAirports.get(adapterPosition), adapterPosition, mEditTextAirportId);
        }

        void bind(final Airport airport) {
            if (mCheckedPosition == -1) {
                tickImageView.setVisibility(View.GONE);
            } else {
                if (mCheckedPosition == getAdapterPosition()) {
                    tickImageView.setVisibility(View.VISIBLE);
                } else {
                    tickImageView.setVisibility(View.GONE);
                }
            }
            airportTextView.setText(airport.getIataCode());
        }
    }

    public AirportAdapter(int id, List airports, AirportAdapterOnClickHandler clickHandler) {
        mEditTextAirportId = id;
        mAirports = airports;
        mClickHandler = clickHandler;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AirportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.airport_recyclerview_row, viewGroup, false);

        return new AirportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirportAdapter.AirportViewHolder holder, int position) {
//        holder.airportTextView.setText(mAirports[position]);
        holder.bind(mAirports.get(position));
    }

    @Override
    public int getItemCount() {
        if (null == mAirports) return 0;
        return mAirports.size();
    }


    public int getCheckPosition() {
        return mCheckedPosition;
    }

    public void setCheckedPosition(int position) {
        mCheckedPosition = position;
        notifyItemChanged(mCheckedPosition);
    }

    public Airport getAirport(int position) {
        return mAirports.get(position);
    }
}
