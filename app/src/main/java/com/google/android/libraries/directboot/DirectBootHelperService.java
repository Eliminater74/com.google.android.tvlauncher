package com.google.android.libraries.directboot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public final class DirectBootHelperService extends Service {
    private static final String TAG = "DirectBootHelperService";

    private DirectBootHelperService() {
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
