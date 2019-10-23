package com.udacity.findaflight.widgets;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import com.udacity.findaflight.data.CompactResult;
import com.udacity.findaflight.database.AppDatabase;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ClickIntentService extends IntentService {

    public static final String ACTION_CLICK = "com.udacity.findaflight.widgets.click";

    public ClickIntentService() {
        super("ClickIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CLICK.equals(action)) {
                Bundle bundle = intent.getExtras();
                if (bundle.containsKey(CompactResultsWidgetProvider.COMPACT_RESULT)) {
                    CompactResult compactResult = (CompactResult) bundle.getParcelable(CompactResultsWidgetProvider.COMPACT_RESULT);
                    AppDatabase appDatabase = AppDatabase.getInstance(this);
                    appDatabase.compactResultDao().deleteCompactResult(compactResult);
                    // Issues with this, but not in FlightDetailsFragment
//                    CompactResultsWidgetProvider.sendRefreshBroadcast(this);
                }
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
