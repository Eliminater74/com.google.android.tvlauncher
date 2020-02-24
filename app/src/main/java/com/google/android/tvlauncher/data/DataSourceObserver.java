package com.google.android.tvlauncher.data;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.media.tv.TvContract;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;
import android.util.Log;
import androidx.tvprovider.media.p005tv.TvContractCompat;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.tvlauncher.data.DataLoadingBackgroundTask;
import com.google.android.tvlauncher.util.ExtendableTimer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class DataSourceObserver {
    private static final int ALL_CHANNELS_INVALIDATION_DELAY_MS = 3000;
    private static final int ALL_CHANNELS_INVALIDATION_MAXIMUM_DELAY_MS = 10000;
    private static final int ALL_CHANNELS_INVALIDATION_TIMER_ID = -1000;
    private static final int ALL_PROGRAMS_INVALIDATION_DELAY_MS = 3000;
    private static final int ALL_PROGRAMS_INVALIDATION_MAXIMUM_DELAY_MS = 15000;
    private static final int ALL_PROGRAMS_INVALIDATION_TIMER_ID = -2000;
    private static final int CHANNEL_ID_COLUMN_INDEX = 1;
    private static final boolean DEBUG = false;
    private static final int HOME_CHANNEL_PROGRAMS_INVALIDATION_DELAY_MS = 3000;
    private static final int HOME_CHANNEL_PROGRAMS_INVALIDATION_MAXIMUM_DELAY_MS = 10000;
    private static final int ID_COLUMN_INDEX = 0;
    private static final int MATCH_CHANNEL = 1;
    private static final int MATCH_CHANNEL_ID = 2;
    private static final int MATCH_CHANNEL_ID_LOGO = 3;
    private static final int MATCH_PROGRAM = 4;
    private static final int MATCH_PROGRAM_ID = 5;
    private static final int MATCH_WATCH_NEXT_PROGRAM = 6;
    private static final int MATCH_WATCH_NEXT_PROGRAM_ID = 7;
    private static final int PACKAGE_CHANNELS_INVALIDATION_DELAY_MS = 3000;
    private static final int PACKAGE_CHANNELS_INVALIDATION_MAXIMUM_DELAY_MS = 10000;
    private static final int PACKAGE_CHANNELS_INVALIDATION_TIMER_ID = -5000;
    private static final int PROGRAMS_COUNT_DELAY_MS = 3000;
    private static final int PROGRAMS_COUNT_MAXIMUM_DELAY_MS = 10000;
    private static final int PROGRAMS_COUNT_TIMER_ID = -6000;
    private static final int PROGRAMS_DATA_LOAD_BATCH = 100;
    private static final int PROGRAMS_DATA_LOAD_DELAY_MS = 1000;
    private static final int PROGRAMS_DATA_LOAD_MAXIMUM_DELAY_MS = 5000;
    private static final int PROGRAMS_DATA_LOAD_TIMER_ID = -4000;
    private static final String[] PROJECTION = {"_id", "channel_id"};
    private static final String TAG = "DataSourceObserver";
    private static final int WATCH_NEXT_INVALIDATION_TIMER_ID = -3000;
    private static final int WATCH_NEXT_PROGRAMS_INVALIDATION_DELAY_MS = 3000;
    private static final int WATCH_NEXT_PROGRAMS_INVALIDATION_MAXIMUM_DELAY_MS = 10000;
    /* access modifiers changed from: private */
    public static UriMatcher sUriMatcher = new UriMatcher(-1);
    private ExtendableTimer mAllChannelsInvalidationTimer;
    /* access modifiers changed from: private */
    public ExtendableTimer mAllProgramsInvalidationTimer;
    private BackgroundTaskCallbacks mBackgroundTaskCallbacks;
    /* access modifiers changed from: private */
    public Callbacks mCallbacks;
    /* access modifiers changed from: private */
    public Set<Long> mChannelsToCountPrograms = new HashSet(100);
    private final ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        /* JADX INFO: Multiple debug info for r0v3 java.util.List<java.lang.String>: [D('channelIdParam' java.lang.String), D('pathSegments' java.util.List<java.lang.String>)] */
        public void onChange(boolean selfChange, Uri uri) {
            switch (DataSourceObserver.sUriMatcher.match(uri)) {
                case 1:
                case 2:
                    DataSourceObserver.this.invalidateAllChannels();
                    return;
                case 3:
                    List<String> pathSegments = uri.getPathSegments();
                    if (pathSegments.size() >= 2) {
                        try {
                            DataSourceObserver.this.invalidateChannelLogo(Long.parseLong(pathSegments.get(1)));
                            DataSourceObserver.this.invalidateAllChannels();
                            return;
                        } catch (NumberFormatException e) {
                            String valueOf = String.valueOf(uri);
                            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
                            sb.append("Invalid channel ID in URI: ");
                            sb.append(valueOf);
                            Log.e(DataSourceObserver.TAG, sb.toString());
                            return;
                        }
                    } else {
                        String valueOf2 = String.valueOf(uri);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 26);
                        sb2.append("Invalid channel logo URI: ");
                        sb2.append(valueOf2);
                        Log.e(DataSourceObserver.TAG, sb2.toString());
                        return;
                    }
                case 4:
                    String channelIdParam = uri.getQueryParameter("channel");
                    if (channelIdParam != null) {
                        try {
                            long channelId = Long.parseLong(channelIdParam);
                            DataSourceObserver.this.invalidateHomeChannelProgramsIfCached(channelId);
                            if (DataSourceObserver.this.mCallbacks.isChannelBrowsable(channelId)) {
                                DataSourceObserver.this.scheduleProgramsCount(channelId);
                            }
                            DataSourceObserver.this.invalidatePackageChannels();
                            return;
                        } catch (NumberFormatException e2) {
                            String valueOf3 = String.valueOf(uri);
                            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 27);
                            sb3.append("Invalid channel ID in URI: ");
                            sb3.append(valueOf3);
                            Log.e(DataSourceObserver.TAG, sb3.toString());
                            return;
                        }
                    } else {
                        DataSourceObserver.this.invalidateAllPrograms();
                        DataSourceObserver.this.invalidatePackageChannels();
                        return;
                    }
                case 5:
                    try {
                        DataSourceObserver.this.invalidateProgram(Long.parseLong(uri.getLastPathSegment()));
                        DataSourceObserver.this.invalidatePackageChannels();
                        return;
                    } catch (NumberFormatException e3) {
                        String valueOf4 = String.valueOf(uri);
                        StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf4).length() + 27);
                        sb4.append("Invalid program ID in URI: ");
                        sb4.append(valueOf4);
                        Log.e(DataSourceObserver.TAG, sb4.toString());
                        return;
                    }
                case 6:
                case 7:
                    DataSourceObserver.this.invalidateWatchNextPrograms();
                    return;
                default:
                    return;
            }
        }
    };
    private final Context mContext;
    private ExtendableTimerListener mExtendableTimerListener;
    /* access modifiers changed from: private */
    @SuppressLint({"UseSparseArrays"})
    public Map<Long, ExtendableTimer> mHomeChannelProgramsInvalidationTimers = new HashMap(10);
    private ExtendableTimer mPackageChannelsInvalidationTimer;
    /* access modifiers changed from: private */
    public ExtendableTimer mProgramsCountTimer;
    private ExtendableTimer mProgramsDataLoadTimer;
    private Set<Long> mProgramsToLoadData = new HashSet(100);
    private boolean mRegistered;
    private ExtendableTimer mWatchNextInvalidationTimer;

    interface Callbacks {
        boolean areProgramsCached(long j);

        Long findCachedChannelId(long j);

        Set<Long> getAllHomeChannelIds();

        void invalidateAllChannels();

        void invalidateAllPrograms();

        void invalidateChannelLogo(long j);

        void invalidateHomeChannelPrograms(long j);

        void invalidatePackageChannels();

        void invalidateProgramsCountForBrowsableChannels(Set<Long> set);

        void invalidateWatchNextPrograms();

        boolean isChannelBrowsable(long j);
    }

    static {
        sUriMatcher.addURI(TvContractCompat.AUTHORITY, "channel", 1);
        sUriMatcher.addURI(TvContractCompat.AUTHORITY, "channel/#", 2);
        sUriMatcher.addURI(TvContractCompat.AUTHORITY, "channel/#/logo", 3);
        sUriMatcher.addURI(TvContractCompat.AUTHORITY, "preview_program", 4);
        sUriMatcher.addURI(TvContractCompat.AUTHORITY, "preview_program/#", 5);
        sUriMatcher.addURI(TvContractCompat.AUTHORITY, "watch_next_program", 6);
        sUriMatcher.addURI(TvContractCompat.AUTHORITY, "watch_next_program/#", 7);
    }

    DataSourceObserver(Context context, Callbacks callbacks) {
        this.mContext = context.getApplicationContext();
        this.mCallbacks = callbacks;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public ContentObserver getContentObserver() {
        return this.mContentObserver;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Callbacks getCallbacks() {
        return this.mCallbacks;
    }

    /* access modifiers changed from: package-private */
    public void register() {
        if (!this.mRegistered) {
            ContentResolver resolver = this.mContext.getContentResolver();
            resolver.registerContentObserver(TvContract.Channels.CONTENT_URI, true, this.mContentObserver);
            resolver.registerContentObserver(TvContract.PreviewPrograms.CONTENT_URI, true, this.mContentObserver);
            resolver.registerContentObserver(TvContract.WatchNextPrograms.CONTENT_URI, true, this.mContentObserver);
            this.mRegistered = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void unregister() {
        if (this.mRegistered) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
            this.mRegistered = false;
        }
    }

    private ExtendableTimerListener getTimerListener() {
        if (this.mExtendableTimerListener == null) {
            this.mExtendableTimerListener = new ExtendableTimerListener();
        }
        return this.mExtendableTimerListener;
    }

    /* access modifiers changed from: private */
    public void invalidateAllChannels() {
        if (this.mAllChannelsInvalidationTimer == null) {
            this.mAllChannelsInvalidationTimer = new ExtendableTimer();
            this.mAllChannelsInvalidationTimer.setTimeout(3000);
            this.mAllChannelsInvalidationTimer.setMaximumTimeout(10000);
            this.mAllChannelsInvalidationTimer.setId(-1000);
            this.mAllChannelsInvalidationTimer.setListener(getTimerListener());
        }
        this.mAllChannelsInvalidationTimer.start();
    }

    /* access modifiers changed from: private */
    public void invalidatePackageChannels() {
        if (this.mPackageChannelsInvalidationTimer == null) {
            this.mPackageChannelsInvalidationTimer = new ExtendableTimer();
            this.mPackageChannelsInvalidationTimer.setTimeout(3000);
            this.mPackageChannelsInvalidationTimer.setMaximumTimeout(10000);
            this.mPackageChannelsInvalidationTimer.setId(-5000);
            this.mPackageChannelsInvalidationTimer.setListener(getTimerListener());
        }
        this.mPackageChannelsInvalidationTimer.start();
    }

    /* access modifiers changed from: private */
    public void invalidateChannelLogo(long channelId) {
        this.mCallbacks.invalidateChannelLogo(channelId);
    }

    /* access modifiers changed from: private */
    public void invalidateWatchNextPrograms() {
        if (this.mWatchNextInvalidationTimer == null) {
            this.mWatchNextInvalidationTimer = ExtendableTimer.obtain();
            this.mWatchNextInvalidationTimer.setTimeout(3000);
            this.mWatchNextInvalidationTimer.setMaximumTimeout(10000);
            this.mWatchNextInvalidationTimer.setId(-3000);
            this.mWatchNextInvalidationTimer.setListener(getTimerListener());
        }
        this.mWatchNextInvalidationTimer.start();
    }

    /* access modifiers changed from: private */
    public void invalidateAllPrograms() {
        if (this.mAllProgramsInvalidationTimer == null) {
            this.mAllProgramsInvalidationTimer = new ExtendableTimer();
            this.mAllProgramsInvalidationTimer.setTimeout(3000);
            this.mAllProgramsInvalidationTimer.setMaximumTimeout(15000);
            this.mAllProgramsInvalidationTimer.setId(-2000);
            this.mAllProgramsInvalidationTimer.setListener(getTimerListener());
        }
        ExtendableTimer extendableTimer = this.mProgramsDataLoadTimer;
        if (extendableTimer != null && extendableTimer.isStarted()) {
            this.mProgramsDataLoadTimer.cancel();
        }
        this.mProgramsToLoadData.clear();
        ExtendableTimer extendableTimer2 = this.mProgramsCountTimer;
        if (extendableTimer2 != null && extendableTimer2.isStarted()) {
            this.mProgramsCountTimer.cancel();
        }
        this.mChannelsToCountPrograms.clear();
        for (ExtendableTimer timer : this.mHomeChannelProgramsInvalidationTimers.values()) {
            timer.cancel();
            timer.recycle();
        }
        this.mHomeChannelProgramsInvalidationTimers.clear();
        this.mAllProgramsInvalidationTimer.start();
    }

    /* access modifiers changed from: private */
    public void invalidateHomeChannelProgramsIfCached(long channelId) {
        ExtendableTimer extendableTimer = this.mAllProgramsInvalidationTimer;
        if ((extendableTimer == null || !extendableTimer.isStarted()) && this.mCallbacks.areProgramsCached(channelId)) {
            invalidateHomeChannelPrograms(channelId);
        }
    }

    private void invalidateHomeChannelPrograms(long channelId) {
        ExtendableTimer extendableTimer = this.mAllProgramsInvalidationTimer;
        if (extendableTimer == null || !extendableTimer.isStarted()) {
            ExtendableTimer timer = this.mHomeChannelProgramsInvalidationTimers.get(Long.valueOf(channelId));
            if (timer == null) {
                timer = ExtendableTimer.obtain();
                timer.setTimeout(3000);
                timer.setMaximumTimeout(10000);
                timer.setId(channelId);
                timer.setListener(getTimerListener());
                this.mHomeChannelProgramsInvalidationTimers.put(Long.valueOf(channelId), timer);
            }
            timer.start();
        }
    }

    /* access modifiers changed from: private */
    public void invalidateProgram(long programId) {
        ExtendableTimer extendableTimer = this.mAllProgramsInvalidationTimer;
        if (extendableTimer == null || !extendableTimer.isStarted()) {
            Long channelId = this.mCallbacks.findCachedChannelId(programId);
            if (channelId != null) {
                invalidateHomeChannelPrograms(channelId.longValue());
            }
            scheduleProgramsDataLoad(programId);
        }
    }

    private void scheduleProgramsDataLoad(long programId) {
        this.mProgramsToLoadData.add(Long.valueOf(programId));
        if (this.mProgramsToLoadData.size() >= 100) {
            loadProgramData();
            return;
        }
        if (this.mProgramsDataLoadTimer == null) {
            this.mProgramsDataLoadTimer = new ExtendableTimer();
            this.mProgramsDataLoadTimer.setTimeout(1000);
            this.mProgramsDataLoadTimer.setMaximumTimeout(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            this.mProgramsDataLoadTimer.setId(-4000);
            this.mProgramsDataLoadTimer.setListener(getTimerListener());
        }
        this.mProgramsDataLoadTimer.start();
    }

    /* access modifiers changed from: private */
    public void loadProgramData() {
        ExtendableTimer extendableTimer = this.mProgramsDataLoadTimer;
        if (extendableTimer != null) {
            extendableTimer.cancel();
        }
        if (!this.mProgramsToLoadData.isEmpty()) {
            String selection = buildProgramSelection(this.mProgramsToLoadData);
            if (this.mBackgroundTaskCallbacks == null) {
                this.mBackgroundTaskCallbacks = new BackgroundTaskCallbacks();
            }
            DataLoadingBackgroundTask.obtain(this.mContext).setUri(TvContract.PreviewPrograms.CONTENT_URI).setProjection(PROJECTION).setSelection(selection).setCallbacks(this.mBackgroundTaskCallbacks).setExtraParam(this.mProgramsToLoadData).execute();
            this.mProgramsToLoadData = new HashSet(100);
        }
    }

    private void startProgramsCountTimer() {
        if (this.mProgramsCountTimer == null) {
            this.mProgramsCountTimer = new ExtendableTimer();
            this.mProgramsCountTimer.setTimeout(3000);
            this.mProgramsCountTimer.setMaximumTimeout(10000);
            this.mProgramsCountTimer.setId(-6000);
            this.mProgramsCountTimer.setListener(getTimerListener());
        }
        this.mProgramsCountTimer.start();
    }

    /* access modifiers changed from: private */
    public void scheduleProgramsCount(long channelId) {
        ExtendableTimer extendableTimer = this.mAllProgramsInvalidationTimer;
        if (extendableTimer == null || !extendableTimer.isStarted()) {
            this.mChannelsToCountPrograms.add(Long.valueOf(channelId));
            startProgramsCountTimer();
        }
    }

    /* access modifiers changed from: private */
    public void scheduleProgramsCount(Set<Long> channelIds) {
        ExtendableTimer extendableTimer = this.mAllProgramsInvalidationTimer;
        if (extendableTimer == null || !extendableTimer.isStarted()) {
            this.mChannelsToCountPrograms.addAll(channelIds);
            startProgramsCountTimer();
        }
    }

    @NonNull
    private String buildProgramSelection(Set<Long> programsToLoad) {
        StringBuilder sb = new StringBuilder("_id IN (");
        for (Long programId : programsToLoad) {
            sb.append(programId.toString());
            sb.append(',');
        }
        sb.setLength(sb.length() - 1);
        sb.append(')');
        return sb.toString();
    }

    private class BackgroundTaskCallbacks implements DataLoadingBackgroundTask.Callbacks {
        private BackgroundTaskCallbacks() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ed, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ee, code lost:
            if (r0 != null) goto L_0x00f0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
            r0.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f4, code lost:
            r3 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x00f5, code lost:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x00f8, code lost:
            throw r2;
         */
        @android.support.annotation.MainThread
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onTaskCompleted(com.google.android.tvlauncher.data.DataLoadingBackgroundTask r12) {
            /*
                r11 = this;
                android.database.Cursor r0 = r12.getResult()
                if (r0 != 0) goto L_0x0030
                java.lang.String r1 = "DataSourceObserver"
                java.lang.String r2 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x00eb }
                java.lang.String r3 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x00eb }
                int r3 = r3.length()     // Catch:{ all -> 0x00eb }
                int r3 = r3 + 38
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00eb }
                r4.<init>(r3)     // Catch:{ all -> 0x00eb }
                java.lang.String r3 = "Error loading program data with task: "
                r4.append(r3)     // Catch:{ all -> 0x00eb }
                r4.append(r2)     // Catch:{ all -> 0x00eb }
                java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x00eb }
                android.util.Log.e(r1, r2)     // Catch:{ all -> 0x00eb }
                if (r0 == 0) goto L_0x002f
                r0.close()
            L_0x002f:
                return
            L_0x0030:
                com.google.android.tvlauncher.data.DataSourceObserver r1 = com.google.android.tvlauncher.data.DataSourceObserver.this     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.util.ExtendableTimer r1 = r1.mAllProgramsInvalidationTimer     // Catch:{ all -> 0x00eb }
                if (r1 == 0) goto L_0x0048
                com.google.android.tvlauncher.data.DataSourceObserver r1 = com.google.android.tvlauncher.data.DataSourceObserver.this     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.util.ExtendableTimer r1 = r1.mAllProgramsInvalidationTimer     // Catch:{ all -> 0x00eb }
                boolean r1 = r1.isStarted()     // Catch:{ all -> 0x00eb }
                if (r1 == 0) goto L_0x0048
                r0.close()
                return
            L_0x0048:
                java.util.HashSet r1 = new java.util.HashSet     // Catch:{ all -> 0x00eb }
                r1.<init>()     // Catch:{ all -> 0x00eb }
                java.lang.Object r2 = r12.getExtraParam()     // Catch:{ all -> 0x00eb }
                java.util.Set r2 = (java.util.Set) r2     // Catch:{ all -> 0x00eb }
            L_0x0053:
                boolean r3 = r0.moveToNext()     // Catch:{ all -> 0x00eb }
                if (r3 == 0) goto L_0x0097
                r3 = 0
                long r3 = r0.getLong(r3)     // Catch:{ all -> 0x00eb }
                java.lang.Long r5 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x00eb }
                r2.remove(r5)     // Catch:{ all -> 0x00eb }
                r5 = 1
                long r5 = r0.getLong(r5)     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.data.DataSourceObserver r7 = com.google.android.tvlauncher.data.DataSourceObserver.this     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.data.DataSourceObserver$Callbacks r7 = r7.mCallbacks     // Catch:{ all -> 0x00eb }
                java.lang.Long r7 = r7.findCachedChannelId(r3)     // Catch:{ all -> 0x00eb }
                if (r7 == 0) goto L_0x007e
                long r8 = r7.longValue()     // Catch:{ all -> 0x00eb }
                int r10 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
                if (r10 == 0) goto L_0x0096
            L_0x007e:
                com.google.android.tvlauncher.data.DataSourceObserver r8 = com.google.android.tvlauncher.data.DataSourceObserver.this     // Catch:{ all -> 0x00eb }
                r8.invalidateHomeChannelProgramsIfCached(r5)     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.data.DataSourceObserver r8 = com.google.android.tvlauncher.data.DataSourceObserver.this     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.data.DataSourceObserver$Callbacks r8 = r8.mCallbacks     // Catch:{ all -> 0x00eb }
                boolean r8 = r8.isChannelBrowsable(r5)     // Catch:{ all -> 0x00eb }
                if (r8 == 0) goto L_0x0096
                java.lang.Long r8 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x00eb }
                r1.add(r8)     // Catch:{ all -> 0x00eb }
            L_0x0096:
                goto L_0x0053
            L_0x0097:
                java.util.Iterator r3 = r2.iterator()     // Catch:{ all -> 0x00eb }
            L_0x009b:
                boolean r4 = r3.hasNext()     // Catch:{ all -> 0x00eb }
                if (r4 == 0) goto L_0x00dc
                java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x00eb }
                java.lang.Long r4 = (java.lang.Long) r4     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.data.DataSourceObserver r5 = com.google.android.tvlauncher.data.DataSourceObserver.this     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.data.DataSourceObserver$Callbacks r5 = r5.mCallbacks     // Catch:{ all -> 0x00eb }
                long r6 = r4.longValue()     // Catch:{ all -> 0x00eb }
                java.lang.Long r5 = r5.findCachedChannelId(r6)     // Catch:{ all -> 0x00eb }
                if (r5 == 0) goto L_0x00cb
                com.google.android.tvlauncher.data.DataSourceObserver r6 = com.google.android.tvlauncher.data.DataSourceObserver.this     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.data.DataSourceObserver$Callbacks r6 = r6.mCallbacks     // Catch:{ all -> 0x00eb }
                java.util.Set r6 = r6.getAllHomeChannelIds()     // Catch:{ all -> 0x00eb }
                boolean r6 = r6.contains(r5)     // Catch:{ all -> 0x00eb }
                if (r6 == 0) goto L_0x00cb
                r1.add(r5)     // Catch:{ all -> 0x00eb }
                goto L_0x00db
            L_0x00cb:
                if (r5 != 0) goto L_0x00db
                com.google.android.tvlauncher.data.DataSourceObserver r3 = com.google.android.tvlauncher.data.DataSourceObserver.this     // Catch:{ all -> 0x00eb }
                com.google.android.tvlauncher.data.DataSourceObserver$Callbacks r3 = r3.mCallbacks     // Catch:{ all -> 0x00eb }
                java.util.Set r3 = r3.getAllHomeChannelIds()     // Catch:{ all -> 0x00eb }
                r1.addAll(r3)     // Catch:{ all -> 0x00eb }
                goto L_0x00dc
            L_0x00db:
                goto L_0x009b
            L_0x00dc:
                boolean r3 = r1.isEmpty()     // Catch:{ all -> 0x00eb }
                if (r3 != 0) goto L_0x00e7
                com.google.android.tvlauncher.data.DataSourceObserver r3 = com.google.android.tvlauncher.data.DataSourceObserver.this     // Catch:{ all -> 0x00eb }
                r3.scheduleProgramsCount(r1)     // Catch:{ all -> 0x00eb }
            L_0x00e7:
                r0.close()
                return
            L_0x00eb:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x00ed }
            L_0x00ed:
                r2 = move-exception
                if (r0 == 0) goto L_0x00f8
                r0.close()     // Catch:{ all -> 0x00f4 }
                goto L_0x00f8
            L_0x00f4:
                r3 = move-exception
                com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r3)
            L_0x00f8:
                throw r2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.data.DataSourceObserver.BackgroundTaskCallbacks.onTaskCompleted(com.google.android.tvlauncher.data.DataLoadingBackgroundTask):void");
        }

        @WorkerThread
        public void onTaskPostProcess(DataLoadingBackgroundTask task) {
        }

        @MainThread
        public void onTaskCanceled(DataLoadingBackgroundTask task) {
        }

        @MainThread
        public void onTaskFailed(DataLoadingBackgroundTask task, Throwable throwable) {
            String valueOf = String.valueOf(task);
            String valueOf2 = String.valueOf(throwable);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20 + String.valueOf(valueOf2).length());
            sb.append("onTaskFailed: ");
            sb.append(valueOf);
            sb.append(", ex: ");
            sb.append(valueOf2);
            Log.e(DataSourceObserver.TAG, sb.toString());
        }
    }

    private class ExtendableTimerListener implements ExtendableTimer.Listener {
        private ExtendableTimerListener() {
        }

        public void onTimerFired(ExtendableTimer timer) {
            long timerId = timer.getId();
            if (timerId == -1000) {
                DataSourceObserver.this.mCallbacks.invalidateAllChannels();
            } else if (timerId == -5000) {
                DataSourceObserver.this.mCallbacks.invalidatePackageChannels();
            } else if (timerId == -2000) {
                DataSourceObserver.this.mCallbacks.invalidateAllPrograms();
            } else if (timerId == -3000) {
                DataSourceObserver.this.mCallbacks.invalidateWatchNextPrograms();
            } else if (timerId == -4000) {
                DataSourceObserver.this.loadProgramData();
            } else if (timerId == -6000) {
                if (DataSourceObserver.this.mProgramsCountTimer != null) {
                    DataSourceObserver.this.mProgramsCountTimer.cancel();
                }
                DataSourceObserver.this.mCallbacks.invalidateProgramsCountForBrowsableChannels(DataSourceObserver.this.mChannelsToCountPrograms);
                DataSourceObserver.this.mChannelsToCountPrograms.clear();
            } else if (DataSourceObserver.this.mHomeChannelProgramsInvalidationTimers.containsKey(Long.valueOf(timerId))) {
                DataSourceObserver.this.mHomeChannelProgramsInvalidationTimers.remove(Long.valueOf(timerId));
                timer.recycle();
                DataSourceObserver.this.mCallbacks.invalidateHomeChannelPrograms(timerId);
            } else {
                StringBuilder sb = new StringBuilder(38);
                sb.append("Unknown timer ID: ");
                sb.append(timerId);
                Log.w(DataSourceObserver.TAG, sb.toString());
            }
        }
    }
}
