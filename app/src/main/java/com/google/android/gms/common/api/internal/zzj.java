package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzau;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: AutoManageLifecycleHelper */
public class zzj extends zzp {
    private final SparseArray<zza> zze = new SparseArray<>();

    private zzj(zzcf zzcf) {
        super(zzcf);
        this.zzd.zza("AutoManageHelper", this);
    }

    public static zzj zza(zzce zzce) {
        zzcf zzb = zzb(zzce);
        zzj zzj = (zzj) zzb.zza("AutoManageHelper", zzj.class);
        if (zzj != null) {
            return zzj;
        }
        return new zzj(zzb);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    public final void zza(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzau.zza(googleApiClient, "GoogleApiClient instance cannot be null");
        boolean z = this.zze.indexOfKey(i) < 0;
        StringBuilder sb = new StringBuilder(54);
        sb.append("Already managing a GoogleApiClient with id ");
        sb.append(i);
        zzau.zza(z, (Object) sb.toString());
        zzq zzq = (zzq) this.zzb.get();
        boolean z2 = this.zza;
        String valueOf = String.valueOf(zzq);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 49);
        sb2.append("starting AutoManage for client ");
        sb2.append(i);
        sb2.append(" ");
        sb2.append(z2);
        sb2.append(" ");
        sb2.append(valueOf);
        Log.d("AutoManageHelper", sb2.toString());
        this.zze.put(i, new zza(i, googleApiClient, onConnectionFailedListener));
        if (this.zza && zzq == null) {
            String valueOf2 = String.valueOf(googleApiClient);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 11);
            sb3.append("connecting ");
            sb3.append(valueOf2);
            Log.d("AutoManageHelper", sb3.toString());
            googleApiClient.connect();
        }
    }

    public final void zza(int i) {
        zza zza2 = this.zze.get(i);
        this.zze.remove(i);
        if (zza2 != null) {
            zza2.zzb.unregisterConnectionFailedListener(zza2);
            zza2.zzb.disconnect();
        }
    }

    public final void zzb() {
        super.zzb();
        boolean z = this.zza;
        String valueOf = String.valueOf(this.zze);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14);
        sb.append("onStart ");
        sb.append(z);
        sb.append(" ");
        sb.append(valueOf);
        Log.d("AutoManageHelper", sb.toString());
        if (this.zzb.get() == null) {
            for (int i = 0; i < this.zze.size(); i++) {
                zza zzb = zzb(i);
                if (zzb != null) {
                    zzb.zzb.connect();
                }
            }
        }
    }

    public final void zza() {
        super.zza();
        for (int i = 0; i < this.zze.size(); i++) {
            zza zzb = zzb(i);
            if (zzb != null) {
                zzb.zzb.disconnect();
            }
        }
    }

    public final void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.zze.size(); i++) {
            zza zzb = zzb(i);
            if (zzb != null) {
                printWriter.append((CharSequence) str).append((CharSequence) "GoogleApiClient #").print(zzb.zza);
                printWriter.println(":");
                zzb.zzb.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza zza2 = this.zze.get(i);
        if (zza2 != null) {
            zza(i);
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = zza2.zzc;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zzc() {
        for (int i = 0; i < this.zze.size(); i++) {
            zza zzb = zzb(i);
            if (zzb != null) {
                zzb.zzb.connect();
            }
        }
    }

    @Nullable
    private final zza zzb(int i) {
        if (this.zze.size() <= i) {
            return null;
        }
        SparseArray<zza> sparseArray = this.zze;
        return sparseArray.get(sparseArray.keyAt(i));
    }

    /* compiled from: AutoManageLifecycleHelper */
    class zza implements GoogleApiClient.OnConnectionFailedListener {
        public final int zza;
        public final GoogleApiClient zzb;
        public final GoogleApiClient.OnConnectionFailedListener zzc;

        public zza(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.zza = i;
            this.zzb = googleApiClient;
            this.zzc = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
            sb.append("beginFailureResolution for ");
            sb.append(valueOf);
            Log.d("AutoManageHelper", sb.toString());
            zzj.this.zzb(connectionResult, this.zza);
        }
    }
}
