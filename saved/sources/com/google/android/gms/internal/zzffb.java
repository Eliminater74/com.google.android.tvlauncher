package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.usagereporting.UsageReportingApi;
import com.google.android.gms.usagereporting.UsageReportingOptInOptions;
import java.util.List;

@Hide
/* compiled from: OptInOptionsResultImpl */
final class zzffb implements UsageReportingApi.OptInOptionsResult {
    private final Status zza;
    private final UsageReportingOptInOptions zzb;

    zzffb(Status status, UsageReportingOptInOptions usageReportingOptInOptions) {
        this.zza = status;
        this.zzb = usageReportingOptInOptions;
    }

    public final Status getStatus() {
        return this.zza;
    }

    public final boolean isOptedInForUsageReporting() {
        zzau.zza(this.zzb);
        return this.zzb.zza() == 1;
    }

    public final boolean canUpload() {
        return false;
    }

    public final List<String> getAccountsToUpload() {
        zzau.zza(this.zzb);
        return this.zzb.zzb();
    }

    public final String toString() {
        boolean z = true;
        if (this.zzb.zza() != 1) {
            z = false;
        }
        String bool = Boolean.toString(z);
        StringBuilder sb = new StringBuilder(String.valueOf(bool).length() + 24);
        sb.append("OptInOptionsResultImpl[");
        sb.append(bool);
        sb.append("]");
        return sb.toString();
    }
}
