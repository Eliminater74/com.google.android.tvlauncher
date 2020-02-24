package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.usagereporting.UsageReportingApi;
import com.google.android.gms.usagereporting.UsageReportingOptInOptions;

@Hide
/* compiled from: UsageReportingApiImpl */
public final class zzffc implements UsageReportingApi {
    public final PendingResult<UsageReportingApi.OptInOptionsResult> getOptInOptions(GoogleApiClient googleApiClient) {
        return googleApiClient.zza((zzn) new zzffd(this, googleApiClient));
    }

    public final PendingResult<Status> setOptInOptions(GoogleApiClient googleApiClient, UsageReportingOptInOptions usageReportingOptInOptions) {
        return googleApiClient.zzb(new zzffe(this, googleApiClient, usageReportingOptInOptions));
    }

    public final PendingResult<Status> setOptInOptionsChangedListener(GoogleApiClient googleApiClient, UsageReportingApi.OptInOptionsChangedListener optInOptionsChangedListener) {
        return googleApiClient.zza((zzn) new zzfff(this, googleApiClient, optInOptionsChangedListener == null ? null : googleApiClient.zza(optInOptionsChangedListener)));
    }
}
