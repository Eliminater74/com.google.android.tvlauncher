package com.google.android.tvlauncher.doubleclick;

import android.content.ContentValues;
import android.content.Context;
import android.os.Process;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;

import androidx.tvprovider.media.p005tv.TvContractCompat;

import com.google.android.tvlauncher.application.TvLauncherApplication;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class AdsManager {
    private static final int CORE_POOL_SIZE = 3;
    private static final boolean DEBUG = true;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final int MAXIMUM_POOL_SIZE = 3;
    private static final String TAG = "AdsManager";
    private static final int WAITING_QUEUE_CAPACITY = 128;
    private final TvLauncherApplication mAppContext;
    private final DoubleClickAdConfigSerializer mDoubleClickAdConfigSerializer;
    private final DoubleClickAdServer mDoubleClickAdServer;
    private final DoubleClickThreadPoolExecutor mExecutor;
    private final OutstreamVideoAdFactory mOutstreamVideoAdFactory;

    public AdsManager(Context context) {
        this(context, ((TvLauncherApplication) context.getApplicationContext()).getDoubleClickAdServer(), ((TvLauncherApplication) context.getApplicationContext()).getDoubleClickAdConfigSerializer(), ((TvLauncherApplication) context.getApplicationContext()).getOutstreamVideoAdFactory(), new DoubleClickThreadPoolExecutor(3, 3, 30, TimeUnit.SECONDS, new LinkedBlockingQueue(128)));
    }

    @VisibleForTesting
    AdsManager(Context context, DoubleClickAdServer doubleClickAdServer, DoubleClickAdConfigSerializer doubleClickAdConfigSerializer, OutstreamVideoAdFactory outstreamVideoAdFactory, DoubleClickThreadPoolExecutor executor) {
        this.mAppContext = (TvLauncherApplication) context.getApplicationContext();
        this.mDoubleClickAdServer = doubleClickAdServer;
        this.mDoubleClickAdConfigSerializer = doubleClickAdConfigSerializer;
        this.mOutstreamVideoAdFactory = outstreamVideoAdFactory;
        this.mExecutor = executor;
    }

    public void processAdRequest(long programId, String adId) {
        this.mExecutor.addTaskAndExecuteIfNeeded(new AdLoaderTask(this.mAppContext, programId, adId, this.mDoubleClickAdServer, this.mDoubleClickAdConfigSerializer, this.mOutstreamVideoAdFactory));
    }

    public void recordImpression(String oldImpressionTrackingUrl, String newImpressionTrackingUrl) {
        this.mExecutor.addTaskAndExecuteIfNeeded(new ImpressionTrackerTask(this.mDoubleClickAdServer, oldImpressionTrackingUrl, newImpressionTrackingUrl));
    }

    public void recordImpressionsInBatch(List<String> newImpressionTrackingUrls) {
        this.mExecutor.addTaskAndExecuteIfNeeded(new ImpressionBatchTrackerTask(this.mDoubleClickAdServer, newImpressionTrackingUrls));
    }

    public void recordClick(String clickTrackingUrl) {
        this.mExecutor.addTaskAndExecuteIfNeeded(new ClickTrackerTask(this.mDoubleClickAdServer, clickTrackingUrl));
    }

    public void removeRequestedTrackingUrls(Set<String> requestedTrackingUrls) {
        this.mDoubleClickAdServer.removeRequestedTrackingUrls(requestedTrackingUrls);
    }

    private static class AdLoaderTask implements Runnable {
        private final String mAdId;
        private final TvLauncherApplication mAppContext;
        private final DoubleClickAdConfigSerializer mDoubleClickAdConfigSerializer;
        private final DoubleClickAdServer mDoubleClickAdServer;
        private final OutstreamVideoAdFactory mOutstreamVideoAdFactory;
        private final long mProgramId;

        AdLoaderTask(TvLauncherApplication context, long programId, String adId, DoubleClickAdServer doubleClickAdServer, DoubleClickAdConfigSerializer doubleClickAdConfigSerializer, OutstreamVideoAdFactory outstreamVideoAdFactory) {
            this.mAppContext = context;
            this.mProgramId = programId;
            this.mAdId = adId;
            this.mDoubleClickAdServer = doubleClickAdServer;
            this.mDoubleClickAdConfigSerializer = doubleClickAdConfigSerializer;
            this.mOutstreamVideoAdFactory = outstreamVideoAdFactory;
        }

        public int hashCode() {
            return Objects.hash(this.mAdId, Long.valueOf(this.mProgramId));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof AdLoaderTask)) {
                return false;
            }
            AdLoaderTask adLoaderTask = (AdLoaderTask) obj;
            if (!TextUtils.equals(this.mAdId, adLoaderTask.mAdId) || this.mProgramId != adLoaderTask.mProgramId) {
                return false;
            }
            return true;
        }

        public void run() {
            Process.setThreadPriority(10);
            InputStream inputStream = this.mDoubleClickAdServer.getDoubleClickAdFromServer(this.mAdId);
            if (inputStream == null) {
                Log.d(AdsManager.TAG, "AdLoaderTask: could not resolve ad.");
                return;
            }
            OutstreamVideoAd outstreamVideoAd = this.mOutstreamVideoAdFactory.createOutstreamVideoAdFromAdResponse(this.mAdId, inputStream);
            if (outstreamVideoAd == null) {
                String valueOf = String.valueOf(this.mAdId);
                Log.e(AdsManager.TAG, valueOf.length() != 0 ? "AdLoaderTask: failed to create outstream video ad for ad id: ".concat(valueOf) : new String("AdLoaderTask: failed to create outstream video ad for ad id: "));
                return;
            }
            byte[] adConfigBlob = this.mDoubleClickAdConfigSerializer.serialize(outstreamVideoAd.getAdAsset());
            ContentValues contentValues = new ContentValues();
            contentValues.put(TvContractCompat.ProgramColumns.COLUMN_POSTER_ART_URI, outstreamVideoAd.getImageUri());
            if (!TextUtils.isEmpty(outstreamVideoAd.getVideoUri())) {
                contentValues.put(TvContractCompat.PreviewProgramColumns.COLUMN_PREVIEW_VIDEO_URI, outstreamVideoAd.getVideoUri());
            } else {
                contentValues.putNull(TvContractCompat.PreviewProgramColumns.COLUMN_PREVIEW_VIDEO_URI);
            }
            contentValues.put("internal_provider_data", adConfigBlob);
            this.mAppContext.getContentResolver().update(TvContractCompat.buildPreviewProgramUri(this.mProgramId), contentValues, null, null);
        }
    }

    private static class ImpressionTrackerTask implements Runnable {
        private final DoubleClickAdServer mDoubleClickAdServer;
        private final String mNewImpressionTrackingUrl;
        private final String mOldImpressionTrackingUrl;

        ImpressionTrackerTask(DoubleClickAdServer doubleClickAdServer, String oldImpressionTrackingUrl, String newImpressionTrackingUrl) {
            this.mDoubleClickAdServer = doubleClickAdServer;
            this.mOldImpressionTrackingUrl = oldImpressionTrackingUrl;
            this.mNewImpressionTrackingUrl = newImpressionTrackingUrl;
        }

        public int hashCode() {
            return this.mNewImpressionTrackingUrl.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ImpressionTrackerTask)) {
                return false;
            }
            return TextUtils.equals(this.mNewImpressionTrackingUrl, ((ImpressionTrackerTask) obj).mNewImpressionTrackingUrl);
        }

        public void run() {
            Process.setThreadPriority(10);
            this.mDoubleClickAdServer.pingImpressionTrackingUrl(this.mOldImpressionTrackingUrl, this.mNewImpressionTrackingUrl);
        }
    }

    private static class ImpressionBatchTrackerTask implements Runnable {
        private final DoubleClickAdServer mDoubleClickAdServer;
        private final List<String> mNewImpressionTrackingUrls;

        ImpressionBatchTrackerTask(DoubleClickAdServer doubleClickAdServer, List<String> newImpressionTrackingUrls) {
            this.mDoubleClickAdServer = doubleClickAdServer;
            this.mNewImpressionTrackingUrls = newImpressionTrackingUrls;
        }

        public int hashCode() {
            return this.mNewImpressionTrackingUrls.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ImpressionBatchTrackerTask)) {
                return false;
            }
            return this.mNewImpressionTrackingUrls.equals(((ImpressionBatchTrackerTask) obj).mNewImpressionTrackingUrls);
        }

        public void run() {
            Process.setThreadPriority(10);
            this.mDoubleClickAdServer.pingImpressionTrackingUrlsInBatch(this.mNewImpressionTrackingUrls);
        }
    }

    private static class ClickTrackerTask implements Runnable {
        private final String mClickTrackingUrl;
        private final DoubleClickAdServer mDoubleClickAdServer;

        ClickTrackerTask(DoubleClickAdServer doubleClickAdServer, String clickTrackingUrl) {
            this.mDoubleClickAdServer = doubleClickAdServer;
            this.mClickTrackingUrl = clickTrackingUrl;
        }

        public int hashCode() {
            return this.mClickTrackingUrl.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ClickTrackerTask)) {
                return false;
            }
            return TextUtils.equals(this.mClickTrackingUrl, ((ClickTrackerTask) obj).mClickTrackingUrl);
        }

        public void run() {
            Process.setThreadPriority(10);
            this.mDoubleClickAdServer.pingClickTrackingUrl(this.mClickTrackingUrl);
        }
    }
}
