package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.CompositeMediaSource;
import com.google.android.exoplayer2.source.DeferredMediaPeriod;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AdsMediaSource extends CompositeMediaSource<MediaSource.MediaPeriodId> {
    private static final MediaSource.MediaPeriodId DUMMY_CONTENT_MEDIA_PERIOD_ID = new MediaSource.MediaPeriodId(new Object());
    /* access modifiers changed from: private */
    public final AdsLoader adsLoader;
    /* access modifiers changed from: private */
    public final Handler mainHandler;
    private final MediaSourceFactory adMediaSourceFactory;
    private final AdsLoader.AdViewProvider adViewProvider;
    private final MediaSource contentMediaSource;
    private final Map<MediaSource, List<DeferredMediaPeriod>> deferredMediaPeriodByAdMediaSource;
    private final Timeline.Period period;
    private MediaSource[][] adGroupMediaSources;
    private Timeline[][] adGroupTimelines;
    private AdPlaybackState adPlaybackState;
    private ComponentListener componentListener;
    private Object contentManifest;
    private Timeline contentTimeline;

    public AdsMediaSource(MediaSource contentMediaSource2, DataSource.Factory dataSourceFactory, AdsLoader adsLoader2, AdsLoader.AdViewProvider adViewProvider2) {
        this(contentMediaSource2, new ProgressiveMediaSource.Factory(dataSourceFactory), adsLoader2, adViewProvider2);
    }

    public AdsMediaSource(MediaSource contentMediaSource2, MediaSourceFactory adMediaSourceFactory2, AdsLoader adsLoader2, AdsLoader.AdViewProvider adViewProvider2) {
        this.contentMediaSource = contentMediaSource2;
        this.adMediaSourceFactory = adMediaSourceFactory2;
        this.adsLoader = adsLoader2;
        this.adViewProvider = adViewProvider2;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.deferredMediaPeriodByAdMediaSource = new HashMap();
        this.period = new Timeline.Period();
        this.adGroupMediaSources = new MediaSource[0][];
        this.adGroupTimelines = new Timeline[0][];
        adsLoader2.setSupportedContentTypes(adMediaSourceFactory2.getSupportedTypes());
    }

    private static long[][] getAdDurations(Timeline[][] adTimelines, Timeline.Period period2) {
        long j;
        long[][] adDurations = new long[adTimelines.length][];
        for (int i = 0; i < adTimelines.length; i++) {
            adDurations[i] = new long[adTimelines[i].length];
            for (int j2 = 0; j2 < adTimelines[i].length; j2++) {
                long[] jArr = adDurations[i];
                if (adTimelines[i][j2] == null) {
                    j = C0841C.TIME_UNSET;
                } else {
                    j = adTimelines[i][j2].getPeriod(0, period2).getDurationUs();
                }
                jArr[j2] = j;
            }
        }
        return adDurations;
    }

    @Nullable
    public Object getTag() {
        return this.contentMediaSource.getTag();
    }

    public void prepareSourceInternal(@Nullable TransferListener mediaTransferListener) {
        super.prepareSourceInternal(mediaTransferListener);
        ComponentListener componentListener2 = new ComponentListener();
        this.componentListener = componentListener2;
        prepareChildSource(DUMMY_CONTENT_MEDIA_PERIOD_ID, this.contentMediaSource);
        this.mainHandler.post(new AdsMediaSource$$Lambda$0(this, componentListener2));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$prepareSourceInternal$0$AdsMediaSource(ComponentListener componentListener2) {
        this.adsLoader.start(componentListener2, this.adViewProvider);
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        MediaSource.MediaPeriodId mediaPeriodId = id;
        if (this.adPlaybackState.adGroupCount <= 0 || !id.isAd()) {
            DeferredMediaPeriod mediaPeriod = new DeferredMediaPeriod(this.contentMediaSource, id, allocator, startPositionUs);
            mediaPeriod.createPeriod(id);
            return mediaPeriod;
        }
        int adGroupIndex = mediaPeriodId.adGroupIndex;
        int adIndexInAdGroup = mediaPeriodId.adIndexInAdGroup;
        Uri adUri = this.adPlaybackState.adGroups[adGroupIndex].uris[adIndexInAdGroup];
        if (this.adGroupMediaSources[adGroupIndex].length <= adIndexInAdGroup) {
            MediaSource adMediaSource = this.adMediaSourceFactory.createMediaSource(adUri);
            MediaSource[][] mediaSourceArr = this.adGroupMediaSources;
            if (adIndexInAdGroup >= mediaSourceArr[adGroupIndex].length) {
                int adCount = adIndexInAdGroup + 1;
                mediaSourceArr[adGroupIndex] = (MediaSource[]) Arrays.copyOf(mediaSourceArr[adGroupIndex], adCount);
                Timeline[][] timelineArr = this.adGroupTimelines;
                timelineArr[adGroupIndex] = (Timeline[]) Arrays.copyOf(timelineArr[adGroupIndex], adCount);
            }
            this.adGroupMediaSources[adGroupIndex][adIndexInAdGroup] = adMediaSource;
            this.deferredMediaPeriodByAdMediaSource.put(adMediaSource, new ArrayList());
            prepareChildSource(id, adMediaSource);
        }
        MediaSource mediaSource = this.adGroupMediaSources[adGroupIndex][adIndexInAdGroup];
        DeferredMediaPeriod deferredMediaPeriod = new DeferredMediaPeriod(mediaSource, id, allocator, startPositionUs);
        deferredMediaPeriod.setPrepareErrorListener(new AdPrepareErrorListener(adUri, adGroupIndex, adIndexInAdGroup));
        List<DeferredMediaPeriod> mediaPeriods = this.deferredMediaPeriodByAdMediaSource.get(mediaSource);
        if (mediaPeriods == null) {
            deferredMediaPeriod.createPeriod(new MediaSource.MediaPeriodId(this.adGroupTimelines[adGroupIndex][adIndexInAdGroup].getUidOfPeriod(0), mediaPeriodId.windowSequenceNumber));
        } else {
            mediaPeriods.add(deferredMediaPeriod);
        }
        return deferredMediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        DeferredMediaPeriod deferredMediaPeriod = (DeferredMediaPeriod) mediaPeriod;
        List<DeferredMediaPeriod> mediaPeriods = this.deferredMediaPeriodByAdMediaSource.get(deferredMediaPeriod.mediaSource);
        if (mediaPeriods != null) {
            mediaPeriods.remove(deferredMediaPeriod);
        }
        deferredMediaPeriod.releasePeriod();
    }

    public void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.componentListener.release();
        this.componentListener = null;
        this.deferredMediaPeriodByAdMediaSource.clear();
        this.contentTimeline = null;
        this.contentManifest = null;
        this.adPlaybackState = null;
        this.adGroupMediaSources = new MediaSource[0][];
        this.adGroupTimelines = new Timeline[0][];
        Handler handler = this.mainHandler;
        AdsLoader adsLoader2 = this.adsLoader;
        adsLoader2.getClass();
        handler.post(AdsMediaSource$$Lambda$1.get$Lambda(adsLoader2));
    }

    /* access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(MediaSource.MediaPeriodId mediaPeriodId, MediaSource mediaSource, Timeline timeline, @Nullable Object manifest) {
        if (mediaPeriodId.isAd()) {
            onAdSourceInfoRefreshed(mediaSource, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, timeline);
        } else {
            onContentSourceInfoRefreshed(timeline, manifest);
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(MediaSource.MediaPeriodId childId, MediaSource.MediaPeriodId mediaPeriodId) {
        return childId.isAd() ? childId : mediaPeriodId;
    }

    /* access modifiers changed from: private */
    public void onAdPlaybackState(AdPlaybackState adPlaybackState2) {
        if (this.adPlaybackState == null) {
            this.adGroupMediaSources = new MediaSource[adPlaybackState2.adGroupCount][];
            Arrays.fill(this.adGroupMediaSources, new MediaSource[0]);
            this.adGroupTimelines = new Timeline[adPlaybackState2.adGroupCount][];
            Arrays.fill(this.adGroupTimelines, new Timeline[0]);
        }
        this.adPlaybackState = adPlaybackState2;
        maybeUpdateSourceInfo();
    }

    private void onContentSourceInfoRefreshed(Timeline timeline, Object manifest) {
        boolean z = true;
        if (timeline.getPeriodCount() != 1) {
            z = false;
        }
        Assertions.checkArgument(z);
        this.contentTimeline = timeline;
        this.contentManifest = manifest;
        maybeUpdateSourceInfo();
    }

    private void onAdSourceInfoRefreshed(MediaSource mediaSource, int adGroupIndex, int adIndexInAdGroup, Timeline timeline) {
        boolean z = true;
        if (timeline.getPeriodCount() != 1) {
            z = false;
        }
        Assertions.checkArgument(z);
        this.adGroupTimelines[adGroupIndex][adIndexInAdGroup] = timeline;
        List<DeferredMediaPeriod> mediaPeriods = this.deferredMediaPeriodByAdMediaSource.remove(mediaSource);
        if (mediaPeriods != null) {
            Object periodUid = timeline.getUidOfPeriod(0);
            for (int i = 0; i < mediaPeriods.size(); i++) {
                DeferredMediaPeriod mediaPeriod = (DeferredMediaPeriod) mediaPeriods.get(i);
                mediaPeriod.createPeriod(new MediaSource.MediaPeriodId(periodUid, mediaPeriod.f91id.windowSequenceNumber));
            }
        }
        maybeUpdateSourceInfo();
    }

    private void maybeUpdateSourceInfo() {
        Timeline timeline;
        AdPlaybackState adPlaybackState2 = this.adPlaybackState;
        if (adPlaybackState2 != null && this.contentTimeline != null) {
            this.adPlaybackState = adPlaybackState2.withAdDurationsUs(getAdDurations(this.adGroupTimelines, this.period));
            if (this.adPlaybackState.adGroupCount == 0) {
                timeline = this.contentTimeline;
            } else {
                timeline = new SinglePeriodAdTimeline(this.contentTimeline, this.adPlaybackState);
            }
            refreshSourceInfo(timeline, this.contentManifest);
        }
    }

    public interface MediaSourceFactory {
        MediaSource createMediaSource(Uri uri);

        int[] getSupportedTypes();
    }

    public static final class AdLoadException extends IOException {
        public static final int TYPE_AD = 0;
        public static final int TYPE_AD_GROUP = 1;
        public static final int TYPE_ALL_ADS = 2;
        public static final int TYPE_UNEXPECTED = 3;
        public final int type;

        private AdLoadException(int type2, Exception cause) {
            super(cause);
            this.type = type2;
        }

        public static AdLoadException createForAd(Exception error) {
            return new AdLoadException(0, error);
        }

        public static AdLoadException createForAdGroup(Exception error, int adGroupIndex) {
            StringBuilder sb = new StringBuilder(35);
            sb.append("Failed to load ad group ");
            sb.append(adGroupIndex);
            return new AdLoadException(1, new IOException(sb.toString(), error));
        }

        public static AdLoadException createForAllAds(Exception error) {
            return new AdLoadException(2, error);
        }

        public static AdLoadException createForUnexpected(RuntimeException error) {
            return new AdLoadException(3, error);
        }

        public RuntimeException getRuntimeExceptionForUnexpected() {
            Assertions.checkState(this.type == 3);
            return (RuntimeException) getCause();
        }

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }
    }

    private final class ComponentListener implements AdsLoader.EventListener {
        private final Handler playerHandler = new Handler();
        private volatile boolean released;

        public ComponentListener() {
        }

        public void onAdClicked() {
            AdsLoader$EventListener$$CC.onAdClicked$$dflt$$(this);
        }

        public void onAdTapped() {
            AdsLoader$EventListener$$CC.onAdTapped$$dflt$$(this);
        }

        public void release() {
            this.released = true;
            this.playerHandler.removeCallbacksAndMessages(null);
        }

        public void onAdPlaybackState(AdPlaybackState adPlaybackState) {
            if (!this.released) {
                this.playerHandler.post(new AdsMediaSource$ComponentListener$$Lambda$0(this, adPlaybackState));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$onAdPlaybackState$0$AdsMediaSource$ComponentListener(AdPlaybackState adPlaybackState) {
            if (!this.released) {
                AdsMediaSource.this.onAdPlaybackState(adPlaybackState);
            }
        }

        public void onAdLoadError(AdLoadException error, DataSpec dataSpec) {
            if (!this.released) {
                AdsMediaSource.this.createEventDispatcher(null).loadError(dataSpec, dataSpec.uri, Collections.emptyMap(), 6, -1, 0, 0, error, true);
            }
        }
    }

    private final class AdPrepareErrorListener implements DeferredMediaPeriod.PrepareErrorListener {
        private final int adGroupIndex;
        private final int adIndexInAdGroup;
        private final Uri adUri;

        public AdPrepareErrorListener(Uri adUri2, int adGroupIndex2, int adIndexInAdGroup2) {
            this.adUri = adUri2;
            this.adGroupIndex = adGroupIndex2;
            this.adIndexInAdGroup = adIndexInAdGroup2;
        }

        public void onPrepareError(MediaSource.MediaPeriodId mediaPeriodId, IOException exception) {
            AdsMediaSource.this.createEventDispatcher(mediaPeriodId).loadError(new DataSpec(this.adUri), this.adUri, Collections.emptyMap(), 6, -1, 0, 0, AdLoadException.createForAd(exception), true);
            AdsMediaSource.this.mainHandler.post(new AdsMediaSource$AdPrepareErrorListener$$Lambda$0(this, exception));
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$onPrepareError$0$AdsMediaSource$AdPrepareErrorListener(IOException exception) {
            AdsMediaSource.this.adsLoader.handlePrepareError(this.adGroupIndex, this.adIndexInAdGroup, exception);
        }
    }
}
