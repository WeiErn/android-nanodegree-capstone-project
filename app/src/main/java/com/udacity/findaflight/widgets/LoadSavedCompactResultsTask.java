package com.udacity.findaflight.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.udacity.findaflight.R;
import com.udacity.findaflight.data.CompactResult;
import com.udacity.findaflight.database.AppDatabase;

import java.util.List;

public class LoadSavedCompactResultsTask extends AsyncTask<Void, Void, List<CompactResult>> {
    private int mWidgetId;
    private AppWidgetManager mWidgetManager;
    private Context mContext;

    public LoadSavedCompactResultsTask(Context context, int appWidgetId, AppWidgetManager appWidgetManager) {
        this.mContext = context;
        this.mWidgetId = appWidgetId;
        this.mWidgetManager = appWidgetManager;
    }

    @Override
    protected List<CompactResult> doInBackground(Void... voids) {
        AppDatabase appDatabase = AppDatabase.getInstance(mContext);
        List<CompactResult> compactResults = appDatabase.compactResultDao().loadAllCompactResults();
        return compactResults;
    }

    @Override
    protected void onPostExecute(List<CompactResult> compactResults) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_compact_results_provider);


        mWidgetManager.updateAppWidget(mWidgetId, views);
    }
}
