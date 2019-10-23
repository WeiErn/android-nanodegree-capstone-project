package com.udacity.findaflight.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.udacity.findaflight.MainActivity;
import com.udacity.findaflight.R;

public class CompactResultsWidgetProvider extends AppWidgetProvider {

    public static final String COMPACT_RESULT = "compactResult";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.widget_compact_results_provider
            );

            // click event handler for the title, launches the app when the user clicks on title
            Intent titleIntent = new Intent(context, MainActivity.class);
            PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, titleIntent, 0);
            views.setOnClickPendingIntent(R.id.saved_flights_header_text_view, titlePendingIntent);


            Intent intent = new Intent(context, CompactResultRemoteViewsService.class);
            views.setRemoteAdapter(R.id.compact_results_list, intent);


//            Reference: https://stackoverflow.com/questions/32741454/how-to-start-a-service-from-app-widget-in-android
            Intent clickIntentTemplate = new Intent(context, ClickIntentService.class);
//            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
//                    .addNextIntentWithParentStack(clickIntentTemplate)
//                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//            views.setPendingIntentTemplate(R.id.compact_results_list, clickPendingIntentTemplate);
            clickIntentTemplate.setAction(ClickIntentService.ACTION_CLICK);
            PendingIntent clickPendingIntentTemplate = PendingIntent.getService(context, 0, clickIntentTemplate, 0);
            views.setPendingIntentTemplate(R.id.compact_results_list, clickPendingIntentTemplate);


            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, CompactResultsWidgetProvider.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, CompactResultsWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.compact_results_list);
        }
        super.onReceive(context, intent);
    }
}
