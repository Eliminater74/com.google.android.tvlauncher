package com.bumptech.glide.manager;

import android.content.Context;
import android.support.annotation.NonNull;

public interface ConnectivityMonitorFactory {
    @NonNull
    ConnectivityMonitor build(@NonNull Context context, @NonNull ConnectivityMonitor.ConnectivityListener connectivityListener);
}
