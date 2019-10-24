package com.udacity.findaflight.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.findaflight.data.CompactResult;
import com.udacity.findaflight.database.AppDatabase;

import java.util.List;

public class WidgetViewModel extends AndroidViewModel {

    private LiveData<List<CompactResult>> mCompactResults;

    public WidgetViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
//        mCompactResults = database.compactResultDao().loadAllCompactResults();
    }

    public LiveData<List<CompactResult>> getmCompactResults() {
        return mCompactResults;
    }
}
