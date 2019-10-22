package com.udacity.findaflight.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class CompactResultRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CompactResultRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
