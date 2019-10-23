package com.udacity.findaflight.widgets;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import com.udacity.findaflight.data.CompactResult;

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
        System.out.println("HANDLED");
        if (intent != null) {
            final String action = intent.getAction();
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey(CompactResultsWidgetProvider.COMPACT_RESULT)) {
                int id = bundle.getInt("id");
                String string = bundle.getString("STRING");
                CompactResult compactResult = (CompactResult) bundle.getParcelable(CompactResultsWidgetProvider.COMPACT_RESULT);
//                System.out.println(compactResult.getOutboundEndAirport());
                System.out.println("TEST");
            }
            if (ACTION_CLICK.equals(action)) {
                System.out.println("CROSS CLICKED ON WIDGET LIST");
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

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
