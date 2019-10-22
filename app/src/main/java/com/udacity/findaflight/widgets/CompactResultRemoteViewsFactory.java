package com.udacity.findaflight.widgets;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.findaflight.R;
import com.udacity.findaflight.data.CompactResult;

import java.util.ArrayList;
import java.util.List;

public class CompactResultRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    List<CompactResult> mCompactResults;
    Context mContext = null;

    public CompactResultRemoteViewsFactory(Context context, Intent intent, List<CompactResult> compactResults) {
        mContext = context;
        mCompactResults = compactResults;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
        if (mCompactResults != null) {
            mCompactResults.clear();
        }
    }

    @Override
    public int getCount() {
        return mCompactResults == null ? 0 : mCompactResults.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCompactResults == null) return null;

        RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_compact_result_on_list);
        CompactResult compactResult = mCompactResults.get(position);

        List<String> airlines = compactResult.getAirlines();
        String airlinesString = "";
        for (String airline : airlines) {
            airlinesString += airline + ", ";
        }
        rv.setTextViewText(R.id.airlines, airlinesString.substring(0, airlinesString.length() - 2));
        rv.setTextViewText(R.id.travel_period, compactResult.getTravelPeriod());
        rv.setTextViewText(R.id.price, Integer.toString(compactResult.getPrice()));
        rv.setTextViewText(R.id.outbound_start_time, compactResult.getOutboundStartTime());
        rv.setTextViewText(R.id.outbound_start_airport, compactResult.getOutboundStartAirport());
        rv.setTextViewText(R.id.outbound_end_time, compactResult.getOutboundEndTime());
        rv.setTextViewText(R.id.outbound_end_airport, compactResult.getOutboundEndAirport());

        if (!compactResult.getInboundEndTime().isEmpty()) {
            rv.setViewVisibility(R.id.fourth_row_relative_layout, View.VISIBLE);
            rv.setTextViewText(R.id.inbound_end_time, compactResult.getInboundEndTime());
            rv.setTextViewText(R.id.inbound_end_airport, compactResult.getInboundEndAirport());
            rv.setTextViewText(R.id.inbound_start_time, compactResult.getInboundStartTime());
            rv.setTextViewText(R.id.inbound_start_airport, compactResult.getInboundStartAirport());
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}