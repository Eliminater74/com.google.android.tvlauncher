package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.p001v4.app.FragmentActivity;
import com.google.android.gms.common.api.internal.zzce;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzdk;
import com.google.android.gms.common.internal.Hide;
import java.util.Map;
import java.util.WeakHashMap;

@Hide
public abstract class ResultStore {
    private static final Map<Object, ResultStore> zza = new WeakHashMap();
    private static final Object zzb = new Object();

    public abstract boolean hasPendingResult(int i);

    public abstract void remove(int i);

    public abstract void setResultCallbacks(int i, @NonNull ResultCallbacks<?> resultCallbacks);

    @Hide
    public <R extends Result> void zza(int i, @NonNull PendingResult pendingResult) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public static ResultStore getInstance(@NonNull Activity activity, @NonNull GoogleApiClient googleApiClient) {
        return zza(new zzce(activity), googleApiClient);
    }

    private static ResultStore zza(@NonNull zzce zzce, @NonNull GoogleApiClient googleApiClient) {
        ResultStore resultStore;
        synchronized (zzb) {
            resultStore = zza.get(zzce.zze());
            if (resultStore == null) {
                if (zzce.zza()) {
                    resultStore = zza(zzce.zzd());
                } else {
                    resultStore = zza(zzce.zzc());
                }
                zza.put(zzce.zze(), resultStore);
            }
            googleApiClient.zza(resultStore);
        }
        return resultStore;
    }

    private static ResultStore zza(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        try {
            zzci zzci = (zzci) fragmentManager.findFragmentByTag("GmsResultStoreFragment");
            if (zzci == null) {
                zzci = new zzci();
                fragmentManager.beginTransaction().add(zzci, "GmsResultStoreFragment").commit();
            }
            return zzci.zza();
        } catch (ClassCastException e) {
            StringBuilder sb = new StringBuilder(String.valueOf("GmsResultStoreFragment").length() + 42);
            sb.append("Fragment tag ");
            sb.append("GmsResultStoreFragment");
            sb.append(" is reserved for ResultStore.");
            throw new IllegalStateException(sb.toString());
        }
    }

    private static ResultStore zza(FragmentActivity fragmentActivity) {
        android.support.p001v4.app.FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        try {
            zzdk zzdk = (zzdk) supportFragmentManager.findFragmentByTag("GmsResultStoreFragment");
            if (zzdk == null) {
                zzdk = new zzdk();
                supportFragmentManager.beginTransaction().add(zzdk, "GmsResultStoreFragment").commit();
            }
            return zzdk.zza();
        } catch (ClassCastException e) {
            StringBuilder sb = new StringBuilder(String.valueOf("GmsResultStoreFragment").length() + 42);
            sb.append("Fragment tag ");
            sb.append("GmsResultStoreFragment");
            sb.append(" is reserved for ResultStore.");
            throw new IllegalStateException(sb.toString());
        }
    }

    @Hide
    protected static void zza(Object obj) {
        synchronized (zzb) {
            zza.remove(obj);
        }
    }
}
