package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.common.base.Optional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.NetworkMetric;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class NetworkMetricCollector {
    private static final Pattern ALLOWED_SANITIZED_PATH_PATTERN = Pattern.compile("([a-zA-Z0-9-_]+)");
    private static final Pattern CONTENT_TYPE_PATTERN = Pattern.compile("(?:[^\\/]*\\/)([^;]*)");
    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("\\b([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})(:\\d{1,5})?\\b");
    private static final String IP_ADDRESS_REGEX = "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})";
    private static final String IP_ADDRESS_REPLACEMENT = "<ip>";
    private static final String OPTIONAL_PORT_NUMBER_REGEX = "(:\\d{1,5})?";
    private static final Pattern PARAMETERS_PATTERN = Pattern.compile("([^\\?]+)(\\?+)");
    private static final Pattern PATH_WITH_SUBDOMAIN_PATTERN = Pattern.compile("((?:https?:\\/\\/|)[a-zA-Z0-9-_\\.]+(?::\\d+)?)(.*)?");
    private static final String TAG = "NetworkCollector";
    private static final Set<String> WHITELISTED_DOMAINS = new HashSet(Arrays.asList("googleapis.com", "adwords.google.com", "m.google.com", "sandbox.google.com"));
    private final boolean enableAutoSanitization;
    private final Optional<NetworkMetricExtensionProvider> metricExtensionProvider;
    private final UrlSanitizer urlSanitizer;

    NetworkMetricCollector(boolean enableAutoSanitization2, UrlSanitizer urlSanitizer2, Optional<NetworkMetricExtensionProvider> metricExtensionProvider2) {
        this.enableAutoSanitization = enableAutoSanitization2;
        this.urlSanitizer = urlSanitizer2;
        this.metricExtensionProvider = metricExtensionProvider2;
    }

    private static boolean isWhitelistedDomain(String requestPath) {
        for (String domain : WHITELISTED_DOMAINS) {
            if (requestPath.contains(domain)) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    static String getDomainFromUrl(String sanitizedUrl) {
        Matcher matcher = PATH_WITH_SUBDOMAIN_PATTERN.matcher(sanitizedUrl);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    @Nullable
    static String trimIpAddress(@Nullable String path) {
        if (path == null) {
            return null;
        }
        Matcher matcher = IP_ADDRESS_PATTERN.matcher(path);
        if (matcher.find()) {
            return matcher.replaceFirst(IP_ADDRESS_REPLACEMENT);
        }
        return path;
    }

    @VisibleForTesting
    static String getRelativePathFromUrl(String sanitizedUrl) {
        Matcher matcher = PATH_WITH_SUBDOMAIN_PATTERN.matcher(sanitizedUrl);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public SystemHealthProto.SystemHealthMetric getMetric(NetworkEvent... events) {
        NetworkMetric.NetworkUsageMetric.Builder networkMetric = NetworkMetric.NetworkUsageMetric.newBuilder();
        for (int i = 0; i < events.length; i++) {
            NetworkMetric.NetworkEventUsage.Builder networkEventUsage = NetworkMetric.NetworkEventUsage.newBuilder();
            if (events[i].bytesUploaded > 0) {
                networkEventUsage.setRequestSizeBytes(events[i].bytesUploaded);
            }
            if (events[i].bytesDownloaded > 0) {
                networkEventUsage.setResponseSizeBytes(events[i].bytesDownloaded);
            }
            if (events[i].timeToResponseDataFinishMs > 0) {
                networkEventUsage.setTimeToResponseDataFinishMs((int) events[i].timeToResponseDataFinishMs);
            }
            if (events[i].timeToResponseHeaderMs > 0) {
                networkEventUsage.setTimeToResponseHeaderMs((int) events[i].timeToResponseHeaderMs);
            }
            if (events[i].httpStatusCode >= 0) {
                networkEventUsage.setHttpStatusCode(events[i].httpStatusCode);
            }
            if (events[i].rpcStatusCode > 0) {
                networkEventUsage.setRpcStats((NetworkMetric.RpcStats) NetworkMetric.RpcStats.newBuilder().setRpcStatusCode(events[i].rpcStatusCode).build());
            }
            if (events[i].contentType != null) {
                networkEventUsage.setContentType(extractContentType(events[i].contentType));
            }
            networkEventUsage.setRequestNegotiatedProtocol(getNegotiationProtocol(events[i].negotiationProtocol));
            String requestPath = events[i].requestPath;
            String domainPath = null;
            if (requestPath != null) {
                if (events[i].isConstantRpcPath) {
                    domainPath = events[i].domainPath;
                    networkEventUsage.setConstantRpcPath(requestPath);
                } else {
                    domainPath = getDomainFromUrl(requestPath);
                    if (!this.enableAutoSanitization || !isWhitelistedDomain(requestPath)) {
                        networkEventUsage.setRequestPath(sanitizeUrl(requestPath, this.urlSanitizer, false));
                    } else {
                        networkEventUsage.setRpcPath(getRelativePathFromUrl(sanitizeUrl(requestPath, this.urlSanitizer, true)));
                    }
                }
            }
            if (domainPath != null) {
                networkEventUsage.setDomainPath(trimIpAddress(domainPath));
            }
            if (events[i].subRequests != null) {
                for (int j = 0; j < networkEventUsage.getSubRequestCount(); j++) {
                    NetworkMetric.SubRequestData.Builder subRequestData = (NetworkMetric.SubRequestData.Builder) networkEventUsage.getSubRequest(j).toBuilder();
                    subRequestData.setRequestPath(sanitizeUrl(subRequestData.getRequestPath(), this.urlSanitizer, false));
                    networkEventUsage.setSubRequest(j, subRequestData);
                }
            }
            if (events[i].processStats != null) {
                networkEventUsage.setProcessStats(events[i].processStats);
            }
            networkEventUsage.setNetworkingStack((NetworkMetric.NetworkEventUsage.NetworkingStack) Optional.fromNullable(NetworkMetric.NetworkEventUsage.NetworkingStack.forNumber(events[i].getNetworkingStackType())).mo22987or((PrimesBatteryConfigurations) NetworkMetric.NetworkEventUsage.NetworkingStack.UNKNOWN));
            NetworkMetric.NetworkConnectionInfo.Builder networkConnectionInfo = NetworkMetric.NetworkConnectionInfo.newBuilder();
            if (events[i].networkType != null) {
                networkConnectionInfo.setNetworkType(events[i].networkType);
            }
            networkEventUsage.setNetworkConnectionInfo(networkConnectionInfo);
            if (events[i].metricExtension != null) {
                networkEventUsage.setMetricExtension(events[i].metricExtension);
            }
            if (events[i].startTimeMs > 0) {
                networkEventUsage.setStartTimeMs(events[i].startTimeMs);
            }
            if (events[i].cacheLookupCount > 0) {
                NetworkMetric.CacheStats.Builder cacheStats = NetworkMetric.CacheStats.newBuilder().setLookupCount(events[i].cacheLookupCount);
                if (events[i].cacheHitCount > 0) {
                    cacheStats.setHitCount(events[i].cacheHitCount);
                }
                networkEventUsage.setCacheStats((NetworkMetric.CacheStats) cacheStats.build());
            }
            networkEventUsage.setRequestStatus(events[i].requestStatus).setRequestFailedReason(NetworkMetric.RequestFailedReason.forNumber(events[i].requestFailedReason)).setQuicDetailedErrorCode(events[i].quicDetailedErrorCode).setRetryCount(events[i].retryCount);
            networkMetric.addNetworkEventUsage(networkEventUsage);
        }
        SystemHealthProto.SystemHealthMetric.Builder systemHealthMetric = SystemHealthProto.SystemHealthMetric.newBuilder().setNetworkUsageMetric(networkMetric);
        try {
            if (this.metricExtensionProvider.isPresent()) {
                Optional<ExtensionMetric.MetricExtension> metricExtension = this.metricExtensionProvider.get().getMetricExtension();
                if (metricExtension.isPresent()) {
                    systemHealthMetric.setMetricExtension(metricExtension.get());
                }
            }
        } catch (Exception e) {
            PrimesLog.m53w(TAG, "Exception while getting network metric extension!", e, new Object[0]);
        }
        return (SystemHealthProto.SystemHealthMetric) systemHealthMetric.build();
    }

    private static NetworkMetric.RequestNegotiatedProtocol getNegotiationProtocol(String negotiatedProtocol) {
        if (negotiatedProtocol == null || negotiatedProtocol.isEmpty()) {
            return NetworkMetric.RequestNegotiatedProtocol.REQUEST_NEGOTIATED_PROTOCOL_UNKNOWN;
        }
        if (negotiatedProtocol.equals("http/1.1")) {
            return NetworkMetric.RequestNegotiatedProtocol.REQUEST_NEGOTIATED_PROTOCOL_HTTP11;
        }
        if (negotiatedProtocol.equals("spdy/2")) {
            return NetworkMetric.RequestNegotiatedProtocol.REQUEST_NEGOTIATED_PROTOCOL_SPDY2;
        }
        if (negotiatedProtocol.equals("spdy/3")) {
            return NetworkMetric.RequestNegotiatedProtocol.REQUEST_NEGOTIATED_PROTOCOL_SPDY3;
        }
        if (negotiatedProtocol.equals("spdy/3.1")) {
            return NetworkMetric.RequestNegotiatedProtocol.REQUEST_NEGOTIATED_PROTOCOL_SPDY31;
        }
        if (negotiatedProtocol.startsWith("h2")) {
            return NetworkMetric.RequestNegotiatedProtocol.REQUEST_NEGOTIATED_PROTOCOL_SPDY4;
        }
        if (negotiatedProtocol.equals("quic/1+spdy/3")) {
            return NetworkMetric.RequestNegotiatedProtocol.REQUEST_NEGOTIATED_PROTOCOL_QUIC1_SPDY3;
        }
        return NetworkMetric.RequestNegotiatedProtocol.REQUEST_NEGOTIATED_PROTOCOL_UNKNOWN;
    }

    @VisibleForTesting
    static String sanitizeUrl(String path, UrlSanitizer sanitizer, boolean sanitizeAtServer) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        boolean matched = false;
        String sanitizedPath = path;
        if (sanitizer != null && !sanitizeAtServer) {
            sanitizedPath = sanitizer.sanitizeUrl(path);
        }
        if (sanitizeAtServer) {
            matched = true;
        } else {
            String urlDomain = getDomainFromUrl(sanitizedPath);
            if (urlDomain != null) {
                matched = true;
                sanitizedPath = urlDomain;
            }
        }
        Matcher matcher = PARAMETERS_PATTERN.matcher(sanitizedPath);
        if (matcher.find()) {
            matched = true;
            sanitizedPath = matcher.group(1);
        }
        String tmpSanitizedPath = trimIpAddress(sanitizedPath);
        if (tmpSanitizedPath != null && !tmpSanitizedPath.equals(sanitizedPath)) {
            matched = true;
        }
        String sanitizedPath2 = tmpSanitizedPath;
        Matcher matcher2 = IP_ADDRESS_PATTERN.matcher(sanitizedPath2);
        if (matcher2.find()) {
            matched = true;
            sanitizedPath2 = matcher2.replaceFirst(IP_ADDRESS_REPLACEMENT);
        }
        if (matched) {
            return sanitizedPath2;
        }
        Matcher matcher3 = ALLOWED_SANITIZED_PATH_PATTERN.matcher(sanitizedPath2);
        if (matcher3.find()) {
            return matcher3.group(1);
        }
        return null;
    }

    @VisibleForTesting
    static String extractContentType(String contentType) {
        if (contentType == null || contentType.isEmpty()) {
            return null;
        }
        Matcher matcher = CONTENT_TYPE_PATTERN.matcher(contentType);
        if (matcher.find()) {
            return matcher.group(0);
        }
        PrimesLog.m54w(TAG, "contentType extraction failed for %s, skipping logging path", contentType);
        return null;
    }
}
