package com.google.android.gms.usagereporting;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import java.util.List;

@Hide
public interface UsageReportingApi {

    public interface OptInOptions {
        boolean canUpload();

        List<String> getAccountsToUpload();

        @Deprecated
        boolean isOptedInForUsageReporting();
    }

    public interface OptInOptionsChangedListener {
        void onOptInOptionsChanged();
    }

    public interface OptInOptionsResult extends Result, OptInOptions {
    }

    PendingResult<OptInOptionsResult> getOptInOptions(GoogleApiClient googleApiClient);

    PendingResult<Status> setOptInOptions(GoogleApiClient googleApiClient, UsageReportingOptInOptions usageReportingOptInOptions);

    PendingResult<Status> setOptInOptionsChangedListener(GoogleApiClient googleApiClient, OptInOptionsChangedListener optInOptionsChangedListener);
}
