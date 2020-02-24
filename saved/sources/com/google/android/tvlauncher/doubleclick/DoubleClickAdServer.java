package com.google.android.tvlauncher.doubleclick;

import android.content.Context;
import android.os.Build;
import android.support.annotation.GuardedBy;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.tvlauncher.util.PackageUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class DoubleClickAdServer {
    private static final int CONNECTION_RETRY_WAIT_MILLIS = 2000;
    private static final boolean DEBUG = true;
    private static final int MAX_CONNECTION_RETRIES = 2;
    private static final String TAG = "DoubleClickAdServer";
    private Clock mConnectionClock;
    private DoubleClickAdRequestFactory mDoubleClickAdRequestFactory;
    private UrlConnectionFactory mDoubleClickConnectionFactory;
    private final Object mPingedImpressionsLock;
    @VisibleForTesting
    @GuardedBy("mPingedImpressionsLock")
    Set<String> mRequestedImpressionUrls;

    public DoubleClickAdServer(Context context) {
        this(new DoubleClickAdRequestFactory(), new DoubleClickConnectionFactory(getUserAgent(context)), new DoubleClickConnectionClock());
    }

    @VisibleForTesting
    DoubleClickAdServer(DoubleClickAdRequestFactory doubleClickAdRequestFactory, UrlConnectionFactory doubleClickConnectionFactory, Clock connectionClock) {
        this.mPingedImpressionsLock = new Object();
        this.mRequestedImpressionUrls = new HashSet();
        this.mDoubleClickAdRequestFactory = doubleClickAdRequestFactory;
        this.mDoubleClickConnectionFactory = doubleClickConnectionFactory;
        this.mConnectionClock = connectionClock;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    @WorkerThread
    public InputStream getDoubleClickAdFromServer(String adUnitId) {
        return getDoubleClickAdFromServer(this.mDoubleClickAdRequestFactory.createVastVideoAdRequest(adUnitId));
    }

    @Nullable
    @WorkerThread
    private InputStream getDoubleClickAdFromServer(DoubleClickAdRequest doubleClickAdRequest) {
        String doubleClickUrl = doubleClickAdRequest.getDfpRequestUri().toString();
        int i = 0;
        while (i < 2) {
            try {
                return this.mDoubleClickConnectionFactory.createGetRequestConnection(doubleClickUrl).getInputStream();
            } catch (NetworkException | IOException e) {
                if (i < 1) {
                    try {
                        this.mConnectionClock.sleep(AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
                    } catch (InterruptedException ex2) {
                        Log.e(TAG, "getDoubleClickAdFromServer interrupted: abort remaining connection retries.", ex2);
                        return null;
                    }
                }
                i++;
            }
        }
        String valueOf = String.valueOf(doubleClickUrl);
        Log.d(TAG, valueOf.length() != 0 ? "Could not successfully resolve ad after retires, url: ".concat(valueOf) : new String("Could not successfully resolve ad after retires, url: "));
        return null;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
        if (android.text.TextUtils.equals(r8, r9) != false) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        r0 = r7.mPingedImpressionsLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r7.mRequestedImpressionUrls.remove(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0023, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0028, code lost:
        r0 = null;
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
        if (android.text.TextUtils.isEmpty(r8) != false) goto L_0x0028;
     */
    @android.support.annotation.Nullable
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean pingImpressionTrackingUrl(java.lang.String r8, java.lang.String r9) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mPingedImpressionsLock
            monitor-enter(r0)
            java.util.Set<java.lang.String> r1 = r7.mRequestedImpressionUrls     // Catch:{ all -> 0x0093 }
            boolean r1 = r1.contains(r9)     // Catch:{ all -> 0x0093 }
            r2 = 0
            if (r1 == 0) goto L_0x000e
            monitor-exit(r0)     // Catch:{ all -> 0x0093 }
            return r2
        L_0x000e:
            monitor-exit(r0)     // Catch:{ all -> 0x0093 }
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 != 0) goto L_0x0028
            boolean r0 = android.text.TextUtils.equals(r8, r9)
            if (r0 != 0) goto L_0x0028
            java.lang.Object r0 = r7.mPingedImpressionsLock
            monitor-enter(r0)
            java.util.Set<java.lang.String> r1 = r7.mRequestedImpressionUrls     // Catch:{ all -> 0x0025 }
            r1.remove(r8)     // Catch:{ all -> 0x0025 }
            monitor-exit(r0)     // Catch:{ all -> 0x0025 }
            goto L_0x0028
        L_0x0025:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0025 }
            throw r1
        L_0x0028:
            r0 = 0
            r1 = 0
        L_0x002a:
            r3 = 2
            if (r1 >= r3) goto L_0x0076
            r3 = 1
            com.google.android.tvlauncher.doubleclick.UrlConnectionFactory r4 = r7.mDoubleClickConnectionFactory     // Catch:{ IOException -> 0x004c, NetworkException -> 0x004a }
            java.net.HttpURLConnection r4 = r4.createGetRequestConnection(r9)     // Catch:{ IOException -> 0x004c, NetworkException -> 0x004a }
            r0 = r4
            java.lang.Object r4 = r7.mPingedImpressionsLock     // Catch:{ IOException -> 0x004c, NetworkException -> 0x004a }
            monitor-enter(r4)     // Catch:{ IOException -> 0x004c, NetworkException -> 0x004a }
            java.util.Set<java.lang.String> r5 = r7.mRequestedImpressionUrls     // Catch:{ all -> 0x0045 }
            r5.add(r9)     // Catch:{ all -> 0x0045 }
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
            if (r0 == 0) goto L_0x0044
            r0.disconnect()
        L_0x0044:
            return r3
        L_0x0045:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
            throw r5     // Catch:{ IOException -> 0x004c, NetworkException -> 0x004a }
        L_0x0048:
            r2 = move-exception
            goto L_0x0067
        L_0x004a:
            r4 = move-exception
            goto L_0x004d
        L_0x004c:
            r4 = move-exception
        L_0x004d:
            if (r1 >= r3) goto L_0x006d
            com.google.android.tvlauncher.doubleclick.Clock r3 = r7.mConnectionClock     // Catch:{ InterruptedException -> 0x0057 }
            r5 = 2000(0x7d0, double:9.88E-321)
            r3.sleep(r5)     // Catch:{ InterruptedException -> 0x0057 }
            goto L_0x006d
        L_0x0057:
            r3 = move-exception
            java.lang.String r5 = "DoubleClickAdServer"
            java.lang.String r6 = "pingImpressionTrackingUrl interrupted: abort remaining connection retries."
            android.util.Log.e(r5, r6, r3)     // Catch:{ all -> 0x0048 }
            if (r0 == 0) goto L_0x0066
            r0.disconnect()
        L_0x0066:
            return r2
        L_0x0067:
            if (r0 == 0) goto L_0x006c
            r0.disconnect()
        L_0x006c:
            throw r2
        L_0x006d:
            if (r0 == 0) goto L_0x0073
            r0.disconnect()
        L_0x0073:
            int r1 = r1 + 1
            goto L_0x002a
        L_0x0076:
            java.lang.String r1 = "Could not successfully request impression after retires, url: "
            java.lang.String r3 = java.lang.String.valueOf(r9)
            int r4 = r3.length()
            if (r4 == 0) goto L_0x0087
            java.lang.String r1 = r1.concat(r3)
            goto L_0x008d
        L_0x0087:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r1)
            r1 = r3
        L_0x008d:
            java.lang.String r3 = "DoubleClickAdServer"
            android.util.Log.d(r3, r1)
            return r2
        L_0x0093:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0093 }
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.doubleclick.DoubleClickAdServer.pingImpressionTrackingUrl(java.lang.String, java.lang.String):boolean");
    }

    /* access modifiers changed from: package-private */
    public void removeRequestedTrackingUrls(Set<String> requestedTrackingUrls) {
        synchronized (this.mPingedImpressionsLock) {
            this.mRequestedImpressionUrls.removeAll(requestedTrackingUrls);
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    @WorkerThread
    public boolean pingImpressionTrackingUrlsInBatch(List<String> newImpressionTrackingUrls) {
        boolean success = true;
        for (String impressionTrackingUrl : newImpressionTrackingUrls) {
            success = success && pingImpressionTrackingUrl(null, impressionTrackingUrl);
        }
        return success;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    @WorkerThread
    public boolean pingClickTrackingUrl(String clickTrackingUrl) {
        HttpURLConnection connection = null;
        int i = 0;
        while (i < 2) {
            try {
                HttpURLConnection connection2 = this.mDoubleClickConnectionFactory.createGetRequestConnection(clickTrackingUrl);
                if (connection2 != null) {
                    connection2.disconnect();
                }
                return true;
            } catch (NetworkException | IOException e) {
                if (i < 1) {
                    try {
                        this.mConnectionClock.sleep(AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
                    } catch (InterruptedException ex2) {
                        Log.e(TAG, "pingClickTrackingUrl interrupted: abort remaining connection retries.", ex2);
                        if (connection != null) {
                            connection.disconnect();
                        }
                        return false;
                    } catch (Throwable th) {
                        if (connection != null) {
                            connection.disconnect();
                        }
                        throw th;
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
                i++;
            }
        }
        String valueOf = String.valueOf(clickTrackingUrl);
        Log.d(TAG, valueOf.length() != 0 ? "Could not successfully request click URL after retires, url: ".concat(valueOf) : new String("Could not successfully request click URL after retires, url: "));
        return false;
    }

    private static String getUserAgent(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append(context.getPackageName());
        builder.append('/');
        builder.append(PackageUtils.getApplicationVersionName(context, context.getPackageName()));
        builder.append("(Linux; U; Android ");
        builder.append(Build.VERSION.RELEASE);
        builder.append("; ");
        builder.append(Locale.getDefault().toString());
        String model = Build.MODEL;
        if (model.length() > 0) {
            builder.append("; ");
            builder.append(model);
        }
        String id = Build.ID;
        if (id.length() > 0) {
            builder.append(" Build/");
            builder.append(id);
        }
        builder.append(')');
        return builder.toString();
    }

    private JSONObject parseJsonFromStream(InputStream inputStream) throws IOException, JSONException {
        byte[] bytes = new byte[4096];
        StringBuilder builder = new StringBuilder();
        while (true) {
            int read = inputStream.read(bytes);
            int numReads = read;
            if (read <= 0) {
                return new JSONObject(builder.toString());
            }
            builder.append(new String(bytes, 0, numReads));
        }
    }

    private static class DoubleClickConnectionFactory implements UrlConnectionFactory {
        private String mUserAgent;

        DoubleClickConnectionFactory(String userAgent) {
            this.mUserAgent = userAgent;
        }

        public HttpURLConnection createGetRequestConnection(String serverUrl) throws IOException, NetworkException {
            HttpURLConnection connection = (HttpURLConnection) new URL(serverUrl).openConnection();
            connection.setRequestProperty("User-Agent", this.mUserAgent);
            connection.setRequestProperty("Content-Type", "application/json");
            int httpStatus = connection.getResponseCode();
            if (httpStatus == 200) {
                return connection;
            }
            String valueOf = String.valueOf(serverUrl);
            throw new NetworkException(valueOf.length() != 0 ? "Error http status when connecting to DoubleClick for server Url: ".concat(valueOf) : new String("Error http status when connecting to DoubleClick for server Url: "), httpStatus);
        }
    }

    private static class DoubleClickConnectionClock implements Clock {
        private DoubleClickConnectionClock() {
        }

        public long getCurrentTimeMillis() {
            return 0;
        }

        public void sleep(long durationMillis) throws InterruptedException {
            Thread.sleep(durationMillis);
        }
    }
}
