package com.google.android.gms.usagereporting;

import android.content.Context;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzffc;
import com.google.android.gms.internal.zzffg;

@Hide
public class UsageReporting {
    public static final UsageReportingApi UsageReportingApi = new zzffc();
    private static final Api.ClientKey<zzffg> zza = new Api.ClientKey<>();
    private static final Api.zza<zzffg, Api.ApiOptions.NoOptions> zzb = new zza();
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("UsageReporting.API", zzb, zza);

    private UsageReporting() {
    }

    public static boolean isUsageReportingServiceAvailable(Context context) {
        return zzffg.zza(context);
    }

    @Hide
    public static abstract class zza<R extends Result> extends zzn<R, zzffg> {
        public zza(GoogleApiClient googleApiClient) {
            super(UsageReporting.API, googleApiClient);
        }

        @Hide
        public final /* bridge */ /* synthetic */ void zza(Object obj) {
            super.zza((Result) obj);
        }
    }
}
