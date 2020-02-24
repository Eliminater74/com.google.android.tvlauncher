package com.google.android.libraries.performance.primes.metriccapture;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.libraries.performance.primes.PrimesLog;
import logs.proto.wireless.performance.mobile.NetworkMetric;

public final class NetworkCapture {
    private static final String TAG = "NetworkCapture";

    /* JADX INFO: Multiple debug info for r1v1 logs.proto.wireless.performance.mobile.NetworkMetric$NetworkConnectionInfo$NetworkType: [D('result' logs.proto.wireless.performance.mobile.NetworkMetric$NetworkConnectionInfo$NetworkType), D('e' java.lang.SecurityException)] */
    public static NetworkMetric.NetworkConnectionInfo.NetworkType getNetworkType(Context appContext) {
        NetworkInfo networkInfo;
        int networkType = -1;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService("connectivity");
            if (!(connectivityManager == null || (networkInfo = connectivityManager.getActiveNetworkInfo()) == null)) {
                networkType = networkInfo.getType();
            }
        } catch (SecurityException e) {
            PrimesLog.m54w(TAG, "Failed to get network type, Please add: android.permission.ACCESS_NETWORK_STATE to AndroidManifest.xml", new Object[0]);
        }
        NetworkMetric.NetworkConnectionInfo.NetworkType result = NetworkMetric.NetworkConnectionInfo.NetworkType.forNumber(networkType);
        return result == null ? NetworkMetric.NetworkConnectionInfo.NetworkType.NONE : result;
    }
}
