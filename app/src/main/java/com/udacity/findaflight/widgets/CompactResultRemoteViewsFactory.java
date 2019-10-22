package com.udacity.findaflight.widgets;

import android.content.Context;
import android.content.Intent;
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
        rv.setTextViewText(text1, mCompactResults.get(position));
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