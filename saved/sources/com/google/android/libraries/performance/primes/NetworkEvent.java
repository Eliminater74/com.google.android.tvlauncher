package com.google.android.libraries.performance.primes;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.libraries.performance.primes.metriccapture.NetworkCapture;
import com.google.android.libraries.performance.primes.metriccapture.ProcessStatsCapture;
import com.google.android.libraries.performance.primes.metriccapture.TimeCapture;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.Arrays;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.NetworkMetric;
import logs.proto.wireless.performance.mobile.ProcessProto;

public final class NetworkEvent {
    int bytesDownloaded;
    int bytesUploaded;
    int cacheHitCount;
    int cacheLookupCount;
    String contentType;
    final String domainPath;
    int httpStatusCode;
    final boolean isConstantRpcPath;
    ExtensionMetric.MetricExtension metricExtension;
    String negotiationProtocol;
    NetworkMetric.NetworkConnectionInfo.NetworkType networkType;
    private int networkingStackType;
    ProcessProto.AndroidProcessStats processStats;
    int quicDetailedErrorCode;
    int requestFailedReason;
    final String requestPath;
    NetworkMetric.RequestStatus requestStatus;
    int retryCount;
    int rpcStatusCode;
    private String[] scenarios;
    final long startTimeMs;
    String[] subRequests;
    long timeToResponseDataFinishMs;
    long timeToResponseHeaderMs;

    public static NetworkEvent forConstantRpcPath(@Nullable String domainPath2, NoPiiString rpcPath) {
        return new NetworkEvent(domainPath2, NoPiiString.safeToString(rpcPath), true);
    }

    public static NetworkEvent forConstantRpcPath(NoPiiString rpcPath) {
        return forConstantRpcPath(null, rpcPath);
    }

    private NetworkEvent(@Nullable String domainPath2, @Nullable String requestPath2, boolean isConstantRpcPath2) {
        this.requestStatus = NetworkMetric.RequestStatus.REQUEST_STATUS_UNSPECIFIED;
        String str = null;
        this.domainPath = (domainPath2 == null || domainPath2.isEmpty()) ? null : domainPath2;
        if (requestPath2 != null && !requestPath2.isEmpty()) {
            str = requestPath2;
        }
        this.requestPath = str;
        this.isConstantRpcPath = isConstantRpcPath2;
        this.startTimeMs = TimeCapture.getTime();
    }

    public NetworkEvent(String requestPath2) {
        this(null, requestPath2, false);
    }

    public NetworkEvent setTimeToResponseHeader(WhitelistNetworkToken token, long timeToResponseHeaderMs2) {
        Preconditions.checkNotNull(token);
        this.timeToResponseHeaderMs = timeToResponseHeaderMs2;
        return this;
    }

    public NetworkEvent setTimeToResponseFinish(WhitelistNetworkToken token, long timeToResponseDataFinishMs2) {
        Preconditions.checkNotNull(token);
        this.timeToResponseDataFinishMs = timeToResponseDataFinishMs2;
        return this;
    }

    public NetworkEvent setMetricExtension(ExtensionMetric.MetricExtension metricExtension2) {
        this.metricExtension = metricExtension2;
        return this;
    }

    public NetworkEvent onResponseStarted() {
        this.timeToResponseHeaderMs = TimeCapture.getTime() - this.startTimeMs;
        return this;
    }

    public NetworkEvent onResponseCompleted(int bytesDownloaded2, int bytesUploaded2) {
        this.timeToResponseDataFinishMs = TimeCapture.getTime() - this.startTimeMs;
        this.bytesDownloaded = bytesDownloaded2;
        this.bytesUploaded = bytesUploaded2;
        return this;
    }

    public NetworkEvent setContentType(String contentType2) {
        if (contentType2 != null && !contentType2.isEmpty()) {
            this.contentType = contentType2;
        }
        return this;
    }

    public NetworkEvent setNegotiationProtocol(String negotiationProtocol2) {
        this.negotiationProtocol = negotiationProtocol2;
        return this;
    }

    public NetworkEvent setSubRequestPaths(String[] paths) {
        if (paths != null) {
            this.subRequests = (String[]) Arrays.copyOf(paths, paths.length);
        }
        return this;
    }

    public NetworkEvent setErrorStatus(int httpStatusCode2) {
        if (httpStatusCode2 >= 0) {
            this.httpStatusCode = httpStatusCode2;
        }
        return this;
    }

    public NetworkEvent setRpcStatusCode(int rpcStatusCode2) {
        this.rpcStatusCode = rpcStatusCode2;
        return this;
    }

    public NetworkEvent setRequestStatus(NetworkMetric.RequestStatus requestStatus2) {
        this.requestStatus = requestStatus2;
        return this;
    }

    public NetworkEvent setRequestFailedReason(int requestFailedReason2) {
        this.requestFailedReason = requestFailedReason2;
        return this;
    }

    public NetworkEvent setQuicDetailedErrorCode(int quicDetailedErrorCode2) {
        this.quicDetailedErrorCode = quicDetailedErrorCode2;
        return this;
    }

    public NetworkEvent setRetryCount(int retryCount2) {
        this.retryCount = retryCount2;
        return this;
    }

    public NetworkEvent setCacheStats(int lookupCount, int hitCount) {
        boolean z = true;
        Preconditions.checkArgument(lookupCount >= hitCount);
        if (hitCount < 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.cacheLookupCount = lookupCount;
        this.cacheHitCount = hitCount;
        return this;
    }

    /* access modifiers changed from: package-private */
    public NetworkEvent setScenarios(@Nullable String[] scenarios2) {
        if (scenarios2 != null) {
            this.scenarios = (String[]) Arrays.copyOf(scenarios2, scenarios2.length);
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public NetworkEvent setNetworkingStackType(int networkingStack) {
        this.networkingStackType = networkingStack;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void onRecord(Context appContext) {
        this.processStats = ProcessStatsCapture.getAndroidProcessStats(appContext);
        this.networkType = NetworkCapture.getNetworkType(appContext);
    }

    /* access modifiers changed from: package-private */
    public boolean isReadyToRecord() {
        return this.timeToResponseDataFinishMs > 0 || this.bytesDownloaded > 0 || this.bytesUploaded > 0 || this.cacheLookupCount > 0 || this.requestStatus == NetworkMetric.RequestStatus.FAILED || this.requestStatus == NetworkMetric.RequestStatus.CANCELED || this.rpcStatusCode > 0;
    }

    /* access modifiers changed from: package-private */
    public String[] getScenarios() {
        return this.scenarios;
    }

    /* access modifiers changed from: package-private */
    public int getNetworkingStackType() {
        return this.networkingStackType;
    }
}
