package com.google.android.gms.common.api;

import android.os.Looper;

import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzcp;
import com.google.android.gms.common.api.internal.zzdh;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;

public final class PendingResults {
    private PendingResults() {
    }

    public static PendingResult<Status> immediatePendingResult(Status status) {
        zzau.zza(status, "Result must not be null");
        zzdh zzdh = new zzdh(Looper.getMainLooper());
        zzdh.zza((Result) status);
        return zzdh;
    }

    @Hide
    public static PendingResult<Status> zza(Status status, GoogleApiClient googleApiClient) {
        zzau.zza(status, "Result must not be null");
        zzdh zzdh = new zzdh(googleApiClient);
        zzdh.zza((Result) status);
        return zzdh;
    }

    @Hide
    public static <R extends Result> PendingResult<R> zza(Result result, GoogleApiClient googleApiClient) {
        zzau.zza(result, "Result must not be null");
        zzau.zzb(!result.getStatus().isSuccess(), "Status code must not be SUCCESS");
        zzb zzb2 = new zzb(googleApiClient, result);
        zzb2.zza(result);
        return zzb2;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [R, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r) {
        zzau.zza((Object) r, (Object) "Result must not be null");
        zzc zzc2 = new zzc(null);
        zzc2.zza((Result) r);
        return new zzcp(zzc2);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [R, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    @Hide
    public static <R extends Result> OptionalPendingResult<R> zzb(R r, GoogleApiClient googleApiClient) {
        zzau.zza((Object) r, (Object) "Result must not be null");
        zzc zzc2 = new zzc(googleApiClient);
        zzc2.zza((Result) r);
        return new zzcp(zzc2);
    }

    public static PendingResult<Status> canceledPendingResult() {
        zzdh zzdh = new zzdh(Looper.getMainLooper());
        zzdh.cancel();
        return zzdh;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
     arg types: [R, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
    public static <R extends Result> PendingResult<R> canceledPendingResult(R r) {
        zzau.zza((Object) r, (Object) "Result must not be null");
        zzau.zzb(r.getStatus().getStatusCode() == 16, "Status code must be CommonStatusCodes.CANCELED");
        zza zza2 = new zza(r);
        zza2.cancel();
        return zza2;
    }

    static final class zzc<R extends Result> extends BasePendingResult<R> {
        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final R zza(Status status) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    static final class zza<R extends Result> extends BasePendingResult<R> {
        private final R zza;

        public zza(R r) {
            super(Looper.getMainLooper());
            this.zza = r;
        }

        /* access modifiers changed from: protected */
        public final R zza(Status status) {
            if (status.getStatusCode() == this.zza.getStatus().getStatusCode()) {
                return this.zza;
            }
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    static final class zzb<R extends Result> extends BasePendingResult<R> {
        private final R zza;

        public zzb(GoogleApiClient googleApiClient, R r) {
            super(googleApiClient);
            this.zza = r;
        }

        /* access modifiers changed from: protected */
        public final R zza(Status status) {
            return this.zza;
        }
    }
}
