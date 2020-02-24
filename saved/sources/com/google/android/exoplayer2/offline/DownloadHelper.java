package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.util.SparseIntArray;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection$Factory$$CC;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public final class DownloadHelper {
    @Nullable
    private static final Constructor<?> DASH_FACTORY_CONSTRUCTOR;
    @Nullable
    private static final Method DASH_FACTORY_CREATE_METHOD;
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS = new DefaultTrackSelector.ParametersBuilder().setForceHighestSupportedBitrate(true).build();
    @Nullable
    private static final Constructor<?> HLS_FACTORY_CONSTRUCTOR;
    @Nullable
    private static final Method HLS_FACTORY_CREATE_METHOD;
    @Nullable
    private static final Constructor<?> SS_FACTORY_CONSTRUCTOR;
    @Nullable
    private static final Method SS_FACTORY_CREATE_METHOD;
    @Nullable
    private final String cacheKey;
    private Callback callback;
    private final Handler callbackHandler;
    private final String downloadType;
    private List<TrackSelection>[][] immutableTrackSelectionsByPeriodAndRenderer;
    private boolean isPreparedWithMedia;
    private MappingTrackSelector.MappedTrackInfo[] mappedTrackInfos;
    private MediaPreparer mediaPreparer;
    @Nullable
    private final MediaSource mediaSource;
    private final RendererCapabilities[] rendererCapabilities;
    private final SparseIntArray scratchSet;
    private TrackGroupArray[] trackGroupArrays;
    private List<TrackSelection>[][] trackSelectionsByPeriodAndRenderer;
    private final DefaultTrackSelector trackSelector = new DefaultTrackSelector(new DownloadTrackSelection.Factory());
    private final Uri uri;

    public interface Callback {
        void onPrepareError(DownloadHelper downloadHelper, IOException iOException);

        void onPrepared(DownloadHelper downloadHelper);
    }

    static {
        Pair<Constructor<?>, Method> dashFactoryMethods = getMediaSourceFactoryMethods("com.google.android.exoplayer2.source.dash.DashMediaSource$Factory");
        DASH_FACTORY_CONSTRUCTOR = (Constructor) dashFactoryMethods.first;
        DASH_FACTORY_CREATE_METHOD = (Method) dashFactoryMethods.second;
        Pair<Constructor<?>, Method> hlsFactoryMethods = getMediaSourceFactoryMethods("com.google.android.exoplayer2.source.hls.HlsMediaSource$Factory");
        HLS_FACTORY_CONSTRUCTOR = (Constructor) hlsFactoryMethods.first;
        HLS_FACTORY_CREATE_METHOD = (Method) hlsFactoryMethods.second;
        Pair<Constructor<?>, Method> ssFactoryMethods = getMediaSourceFactoryMethods("com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource$Factory");
        SS_FACTORY_CONSTRUCTOR = (Constructor) ssFactoryMethods.first;
        SS_FACTORY_CREATE_METHOD = (Method) ssFactoryMethods.second;
    }

    public static DownloadHelper forProgressive(Uri uri2) {
        return forProgressive(uri2, null);
    }

    public static DownloadHelper forProgressive(Uri uri2, @Nullable String cacheKey2) {
        return new DownloadHelper(DownloadRequest.TYPE_PROGRESSIVE, uri2, cacheKey2, null, DEFAULT_TRACK_SELECTOR_PARAMETERS, new RendererCapabilities[0]);
    }

    public static DownloadHelper forDash(Uri uri2, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory) {
        return forDash(uri2, dataSourceFactory, renderersFactory, null, DEFAULT_TRACK_SELECTOR_PARAMETERS);
    }

    public static DownloadHelper forDash(Uri uri2, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, DefaultTrackSelector.Parameters trackSelectorParameters) {
        return new DownloadHelper(DownloadRequest.TYPE_DASH, uri2, null, createMediaSource(uri2, dataSourceFactory, DASH_FACTORY_CONSTRUCTOR, DASH_FACTORY_CREATE_METHOD), trackSelectorParameters, Util.getRendererCapabilities(renderersFactory, drmSessionManager));
    }

    public static DownloadHelper forHls(Uri uri2, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory) {
        return forHls(uri2, dataSourceFactory, renderersFactory, null, DEFAULT_TRACK_SELECTOR_PARAMETERS);
    }

    public static DownloadHelper forHls(Uri uri2, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, DefaultTrackSelector.Parameters trackSelectorParameters) {
        return new DownloadHelper(DownloadRequest.TYPE_HLS, uri2, null, createMediaSource(uri2, dataSourceFactory, HLS_FACTORY_CONSTRUCTOR, HLS_FACTORY_CREATE_METHOD), trackSelectorParameters, Util.getRendererCapabilities(renderersFactory, drmSessionManager));
    }

    public static DownloadHelper forSmoothStreaming(Uri uri2, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory) {
        return forSmoothStreaming(uri2, dataSourceFactory, renderersFactory, null, DEFAULT_TRACK_SELECTOR_PARAMETERS);
    }

    public static DownloadHelper forSmoothStreaming(Uri uri2, DataSource.Factory dataSourceFactory, RenderersFactory renderersFactory, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, DefaultTrackSelector.Parameters trackSelectorParameters) {
        return new DownloadHelper(DownloadRequest.TYPE_SS, uri2, null, createMediaSource(uri2, dataSourceFactory, SS_FACTORY_CONSTRUCTOR, SS_FACTORY_CREATE_METHOD), trackSelectorParameters, Util.getRendererCapabilities(renderersFactory, drmSessionManager));
    }

    public DownloadHelper(String downloadType2, Uri uri2, @Nullable String cacheKey2, @Nullable MediaSource mediaSource2, DefaultTrackSelector.Parameters trackSelectorParameters, RendererCapabilities[] rendererCapabilities2) {
        this.downloadType = downloadType2;
        this.uri = uri2;
        this.cacheKey = cacheKey2;
        this.mediaSource = mediaSource2;
        this.rendererCapabilities = rendererCapabilities2;
        this.scratchSet = new SparseIntArray();
        this.trackSelector.setParameters(trackSelectorParameters);
        this.trackSelector.init(DownloadHelper$$Lambda$0.$instance, new DummyBandwidthMeter());
        this.callbackHandler = new Handler(Util.getLooper());
    }

    static final /* synthetic */ void lambda$new$0$DownloadHelper() {
    }

    public void prepare(Callback callback2) {
        Assertions.checkState(this.callback == null);
        this.callback = callback2;
        MediaSource mediaSource2 = this.mediaSource;
        if (mediaSource2 != null) {
            this.mediaPreparer = new MediaPreparer(mediaSource2, this);
        } else {
            this.callbackHandler.post(new DownloadHelper$$Lambda$1(this, callback2));
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$prepare$1$DownloadHelper(Callback callback2) {
        callback2.onPrepared(this);
    }

    public void release() {
        MediaPreparer mediaPreparer2 = this.mediaPreparer;
        if (mediaPreparer2 != null) {
            mediaPreparer2.release();
        }
    }

    @Nullable
    public Object getManifest() {
        if (this.mediaSource == null) {
            return null;
        }
        assertPreparedWithMedia();
        return this.mediaPreparer.manifest;
    }

    public int getPeriodCount() {
        if (this.mediaSource == null) {
            return 0;
        }
        assertPreparedWithMedia();
        return this.trackGroupArrays.length;
    }

    public TrackGroupArray getTrackGroups(int periodIndex) {
        assertPreparedWithMedia();
        return this.trackGroupArrays[periodIndex];
    }

    public MappingTrackSelector.MappedTrackInfo getMappedTrackInfo(int periodIndex) {
        assertPreparedWithMedia();
        return this.mappedTrackInfos[periodIndex];
    }

    public List<TrackSelection> getTrackSelections(int periodIndex, int rendererIndex) {
        assertPreparedWithMedia();
        return this.immutableTrackSelectionsByPeriodAndRenderer[periodIndex][rendererIndex];
    }

    public void clearTrackSelections(int periodIndex) {
        assertPreparedWithMedia();
        for (int i = 0; i < this.rendererCapabilities.length; i++) {
            this.trackSelectionsByPeriodAndRenderer[periodIndex][i].clear();
        }
    }

    public void replaceTrackSelections(int periodIndex, DefaultTrackSelector.Parameters trackSelectorParameters) {
        clearTrackSelections(periodIndex);
        addTrackSelection(periodIndex, trackSelectorParameters);
    }

    public void addTrackSelection(int periodIndex, DefaultTrackSelector.Parameters trackSelectorParameters) {
        assertPreparedWithMedia();
        this.trackSelector.setParameters(trackSelectorParameters);
        runTrackSelection(periodIndex);
    }

    public void addAudioLanguagesToSelection(String... languages) {
        assertPreparedWithMedia();
        for (int periodIndex = 0; periodIndex < this.mappedTrackInfos.length; periodIndex++) {
            DefaultTrackSelector.ParametersBuilder parametersBuilder = DEFAULT_TRACK_SELECTOR_PARAMETERS.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.mappedTrackInfos[periodIndex];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int rendererIndex = 0; rendererIndex < rendererCount; rendererIndex++) {
                if (mappedTrackInfo.getRendererType(rendererIndex) != 1) {
                    parametersBuilder.setRendererDisabled(rendererIndex, true);
                }
            }
            for (String language : languages) {
                parametersBuilder.setPreferredAudioLanguage(language);
                addTrackSelection(periodIndex, parametersBuilder.build());
            }
        }
    }

    public void addTextLanguagesToSelection(boolean selectUndeterminedTextLanguage, String... languages) {
        assertPreparedWithMedia();
        for (int periodIndex = 0; periodIndex < this.mappedTrackInfos.length; periodIndex++) {
            DefaultTrackSelector.ParametersBuilder parametersBuilder = DEFAULT_TRACK_SELECTOR_PARAMETERS.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.mappedTrackInfos[periodIndex];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int rendererIndex = 0; rendererIndex < rendererCount; rendererIndex++) {
                if (mappedTrackInfo.getRendererType(rendererIndex) != 3) {
                    parametersBuilder.setRendererDisabled(rendererIndex, true);
                }
            }
            parametersBuilder.setSelectUndeterminedTextLanguage(selectUndeterminedTextLanguage);
            for (String language : languages) {
                parametersBuilder.setPreferredTextLanguage(language);
                addTrackSelection(periodIndex, parametersBuilder.build());
            }
        }
    }

    public void addTrackSelectionForSingleRenderer(int periodIndex, int rendererIndex, DefaultTrackSelector.Parameters trackSelectorParameters, List<DefaultTrackSelector.SelectionOverride> overrides) {
        assertPreparedWithMedia();
        DefaultTrackSelector.ParametersBuilder builder = trackSelectorParameters.buildUpon();
        int i = 0;
        while (i < this.mappedTrackInfos[periodIndex].getRendererCount()) {
            builder.setRendererDisabled(i, i != rendererIndex);
            i++;
        }
        if (overrides.isEmpty() != 0) {
            addTrackSelection(periodIndex, builder.build());
            return;
        }
        TrackGroupArray trackGroupArray = this.mappedTrackInfos[periodIndex].getTrackGroups(rendererIndex);
        for (int i2 = 0; i2 < overrides.size(); i2++) {
            builder.setSelectionOverride(rendererIndex, trackGroupArray, overrides.get(i2));
            addTrackSelection(periodIndex, builder.build());
        }
    }

    public DownloadRequest getDownloadRequest(@Nullable byte[] data) {
        String downloadId = this.uri.toString();
        if (this.mediaSource == null) {
            return new DownloadRequest(downloadId, this.downloadType, this.uri, Collections.emptyList(), this.cacheKey, data);
        }
        assertPreparedWithMedia();
        List<StreamKey> streamKeys = new ArrayList<>();
        List<TrackSelection> allSelections = new ArrayList<>();
        int periodCount = this.trackSelectionsByPeriodAndRenderer.length;
        for (int periodIndex = 0; periodIndex < periodCount; periodIndex++) {
            allSelections.clear();
            for (List<TrackSelection> addAll : this.trackSelectionsByPeriodAndRenderer[periodIndex]) {
                allSelections.addAll(addAll);
            }
            streamKeys.addAll(this.mediaPreparer.mediaPeriods[periodIndex].getStreamKeys(allSelections));
        }
        return new DownloadRequest(downloadId, this.downloadType, this.uri, streamKeys, this.cacheKey, data);
    }

    /* access modifiers changed from: private */
    public void onMediaPrepared() {
        Assertions.checkNotNull(this.mediaPreparer);
        Assertions.checkNotNull(this.mediaPreparer.mediaPeriods);
        Assertions.checkNotNull(this.mediaPreparer.timeline);
        int periodCount = this.mediaPreparer.mediaPeriods.length;
        int rendererCount = this.rendererCapabilities.length;
        this.trackSelectionsByPeriodAndRenderer = (List[][]) Array.newInstance(List.class, periodCount, rendererCount);
        this.immutableTrackSelectionsByPeriodAndRenderer = (List[][]) Array.newInstance(List.class, periodCount, rendererCount);
        for (int i = 0; i < periodCount; i++) {
            for (int j = 0; j < rendererCount; j++) {
                this.trackSelectionsByPeriodAndRenderer[i][j] = new ArrayList();
                this.immutableTrackSelectionsByPeriodAndRenderer[i][j] = Collections.unmodifiableList(this.trackSelectionsByPeriodAndRenderer[i][j]);
            }
        }
        this.trackGroupArrays = new TrackGroupArray[periodCount];
        this.mappedTrackInfos = new MappingTrackSelector.MappedTrackInfo[periodCount];
        for (int i2 = 0; i2 < periodCount; i2++) {
            this.trackGroupArrays[i2] = this.mediaPreparer.mediaPeriods[i2].getTrackGroups();
            this.trackSelector.onSelectionActivated(runTrackSelection(i2).info);
            this.mappedTrackInfos[i2] = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(this.trackSelector.getCurrentMappedTrackInfo());
        }
        setPreparedWithMedia();
        ((Handler) Assertions.checkNotNull(this.callbackHandler)).post(new DownloadHelper$$Lambda$2(this));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onMediaPrepared$2$DownloadHelper() {
        ((Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
    }

    /* access modifiers changed from: private */
    public void onMediaPreparationFailed(IOException error) {
        ((Handler) Assertions.checkNotNull(this.callbackHandler)).post(new DownloadHelper$$Lambda$3(this, error));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onMediaPreparationFailed$3$DownloadHelper(IOException error) {
        ((Callback) Assertions.checkNotNull(this.callback)).onPrepareError(this, error);
    }

    @RequiresNonNull({"trackGroupArrays", "mappedTrackInfos", "trackSelectionsByPeriodAndRenderer", "immutableTrackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline", "mediaPreparer.mediaPeriods"})
    private void setPreparedWithMedia() {
        this.isPreparedWithMedia = true;
    }

    @EnsuresNonNull({"trackGroupArrays", "mappedTrackInfos", "trackSelectionsByPeriodAndRenderer", "immutableTrackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline", "mediaPreparer.mediaPeriods"})
    private void assertPreparedWithMedia() {
        Assertions.checkState(this.isPreparedWithMedia);
    }

    @RequiresNonNull({"trackGroupArrays", "trackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline"})
    private TrackSelectorResult runTrackSelection(int periodIndex) {
        try {
            TrackSelectorResult trackSelectorResult = this.trackSelector.selectTracks(this.rendererCapabilities, this.trackGroupArrays[periodIndex], new MediaSource.MediaPeriodId(this.mediaPreparer.timeline.getUidOfPeriod(periodIndex)), this.mediaPreparer.timeline);
            for (int i = 0; i < trackSelectorResult.length; i++) {
                TrackSelection newSelection = trackSelectorResult.selections.get(i);
                if (newSelection != null) {
                    List<TrackSelection> existingSelectionList = this.trackSelectionsByPeriodAndRenderer[periodIndex][i];
                    boolean mergedWithExistingSelection = false;
                    int j = 0;
                    while (true) {
                        if (j >= existingSelectionList.size()) {
                            break;
                        }
                        TrackSelection existingSelection = existingSelectionList.get(j);
                        if (existingSelection.getTrackGroup() == newSelection.getTrackGroup()) {
                            this.scratchSet.clear();
                            for (int k = 0; k < existingSelection.length(); k++) {
                                this.scratchSet.put(existingSelection.getIndexInTrackGroup(k), 0);
                            }
                            for (int k2 = 0; k2 < newSelection.length(); k2++) {
                                this.scratchSet.put(newSelection.getIndexInTrackGroup(k2), 0);
                            }
                            int[] mergedTracks = new int[this.scratchSet.size()];
                            for (int k3 = 0; k3 < this.scratchSet.size(); k3++) {
                                mergedTracks[k3] = this.scratchSet.keyAt(k3);
                            }
                            existingSelectionList.set(j, new DownloadTrackSelection(existingSelection.getTrackGroup(), mergedTracks));
                            mergedWithExistingSelection = true;
                        } else {
                            j++;
                        }
                    }
                    if (!mergedWithExistingSelection) {
                        existingSelectionList.add(newSelection);
                    }
                }
            }
            return trackSelectorResult;
        } catch (ExoPlaybackException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private static Pair<Constructor<?>, Method> getMediaSourceFactoryMethods(String className) {
        Constructor<?> constructor = null;
        Method createMethod = null;
        try {
            Class<?> factoryClazz = Class.forName(className);
            constructor = factoryClazz.getConstructor(DataSource.Factory.class);
            createMethod = factoryClazz.getMethod("createMediaSource", Uri.class);
        } catch (Exception e) {
        }
        return Pair.create(constructor, createMethod);
    }

    private static MediaSource createMediaSource(Uri uri2, DataSource.Factory dataSourceFactory, @Nullable Constructor<?> factoryConstructor, @Nullable Method createMediaSourceMethod) {
        if (factoryConstructor == null || createMediaSourceMethod == null) {
            throw new IllegalStateException("Module missing to create media source.");
        }
        try {
            return (MediaSource) Assertions.checkNotNull(createMediaSourceMethod.invoke(factoryConstructor.newInstance(dataSourceFactory), uri2));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to instantiate media source.", e);
        }
    }

    private static final class MediaPreparer implements MediaSource.SourceInfoRefreshListener, MediaPeriod.Callback, Handler.Callback {
        private static final int DOWNLOAD_HELPER_CALLBACK_MESSAGE_FAILED = 1;
        private static final int DOWNLOAD_HELPER_CALLBACK_MESSAGE_PREPARED = 0;
        private static final int MESSAGE_CHECK_FOR_FAILURE = 1;
        private static final int MESSAGE_CONTINUE_LOADING = 2;
        private static final int MESSAGE_PREPARE_SOURCE = 0;
        private static final int MESSAGE_RELEASE = 3;
        private final Allocator allocator = new DefaultAllocator(true, 65536);
        private final DownloadHelper downloadHelper;
        private final Handler downloadHelperHandler = Util.createHandler(new DownloadHelper$MediaPreparer$$Lambda$0(this));
        @Nullable
        public Object manifest;
        public MediaPeriod[] mediaPeriods;
        private final MediaSource mediaSource;
        private final Handler mediaSourceHandler;
        private final HandlerThread mediaSourceThread = new HandlerThread("DownloadHelper");
        private final ArrayList<MediaPeriod> pendingMediaPeriods;
        private boolean released;
        public Timeline timeline;

        public MediaPreparer(MediaSource mediaSource2, DownloadHelper downloadHelper2) {
            this.mediaSource = mediaSource2;
            this.downloadHelper = downloadHelper2;
            this.mediaSourceThread.start();
            this.mediaSourceHandler = Util.createHandler(this.mediaSourceThread.getLooper(), this);
            this.mediaSourceHandler.sendEmptyMessage(0);
            this.pendingMediaPeriods = new ArrayList<>();
        }

        public void release() {
            if (!this.released) {
                this.released = true;
                this.mediaSourceHandler.sendEmptyMessage(3);
            }
        }

        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i != 0) {
                if (i == 1) {
                    try {
                        if (this.mediaPeriods == null) {
                            this.mediaSource.maybeThrowSourceInfoRefreshError();
                        } else {
                            for (int i2 = 0; i2 < this.pendingMediaPeriods.size(); i2++) {
                                this.pendingMediaPeriods.get(i2).maybeThrowPrepareError();
                            }
                        }
                        this.mediaSourceHandler.sendEmptyMessageDelayed(1, 100);
                    } catch (IOException e) {
                        this.downloadHelperHandler.obtainMessage(1, e).sendToTarget();
                    }
                    return true;
                } else if (i == 2) {
                    MediaPeriod mediaPeriod = (MediaPeriod) msg.obj;
                    if (this.pendingMediaPeriods.contains(mediaPeriod)) {
                        mediaPeriod.continueLoading(0);
                    }
                    return true;
                } else if (i != 3) {
                    return false;
                } else {
                    MediaPeriod[] mediaPeriodArr = this.mediaPeriods;
                    if (mediaPeriodArr != null) {
                        for (MediaPeriod period : mediaPeriodArr) {
                            this.mediaSource.releasePeriod(period);
                        }
                    }
                    this.mediaSource.releaseSource(this);
                    this.mediaSourceHandler.removeCallbacksAndMessages(null);
                    this.mediaSourceThread.quit();
                    return true;
                }
            } else {
                this.mediaSource.prepareSource(this, null);
                this.mediaSourceHandler.sendEmptyMessage(1);
                return true;
            }
        }

        public void onSourceInfoRefreshed(MediaSource source, Timeline timeline2, @Nullable Object manifest2) {
            MediaPeriod[] mediaPeriodArr;
            if (this.timeline == null) {
                this.timeline = timeline2;
                this.manifest = manifest2;
                this.mediaPeriods = new MediaPeriod[timeline2.getPeriodCount()];
                int i = 0;
                while (true) {
                    mediaPeriodArr = this.mediaPeriods;
                    if (i >= mediaPeriodArr.length) {
                        break;
                    }
                    MediaPeriod mediaPeriod = this.mediaSource.createPeriod(new MediaSource.MediaPeriodId(timeline2.getUidOfPeriod(i)), this.allocator, 0);
                    this.mediaPeriods[i] = mediaPeriod;
                    this.pendingMediaPeriods.add(mediaPeriod);
                    i++;
                }
                for (MediaPeriod mediaPeriod2 : mediaPeriodArr) {
                    mediaPeriod2.prepare(this, 0);
                }
            }
        }

        public void onPrepared(MediaPeriod mediaPeriod) {
            this.pendingMediaPeriods.remove(mediaPeriod);
            if (this.pendingMediaPeriods.isEmpty()) {
                this.mediaSourceHandler.removeMessages(1);
                this.downloadHelperHandler.sendEmptyMessage(0);
            }
        }

        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            if (this.pendingMediaPeriods.contains(mediaPeriod)) {
                this.mediaSourceHandler.obtainMessage(2, mediaPeriod).sendToTarget();
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: handleDownloadHelperCallbackMessage */
        public boolean bridge$lambda$0$DownloadHelper$MediaPreparer(Message msg) {
            if (this.released) {
                return false;
            }
            int i = msg.what;
            if (i == 0) {
                this.downloadHelper.onMediaPrepared();
                return true;
            } else if (i != 1) {
                return false;
            } else {
                this.downloadHelper.onMediaPreparationFailed((IOException) Util.castNonNull(msg.obj));
                return true;
            }
        }
    }

    private static final class DownloadTrackSelection extends BaseTrackSelection {

        private static final class Factory implements TrackSelection.Factory {
            public TrackSelection createTrackSelection(TrackGroup trackGroup, BandwidthMeter bandwidthMeter, int... iArr) {
                return TrackSelection$Factory$$CC.createTrackSelection$$dflt$$(this, trackGroup, bandwidthMeter, iArr);
            }

            private Factory() {
            }

            public TrackSelection[] createTrackSelections(TrackSelection.Definition[] definitions, BandwidthMeter bandwidthMeter) {
                DownloadTrackSelection downloadTrackSelection;
                TrackSelection[] selections = new TrackSelection[definitions.length];
                for (int i = 0; i < definitions.length; i++) {
                    if (definitions[i] == null) {
                        downloadTrackSelection = null;
                    } else {
                        downloadTrackSelection = new DownloadTrackSelection(definitions[i].group, definitions[i].tracks);
                    }
                    selections[i] = downloadTrackSelection;
                }
                return selections;
            }
        }

        public DownloadTrackSelection(TrackGroup trackGroup, int[] tracks) {
            super(trackGroup, tracks);
        }

        public int getSelectedIndex() {
            return 0;
        }

        public int getSelectionReason() {
            return 0;
        }

        @Nullable
        public Object getSelectionData() {
            return null;
        }
    }

    private static final class DummyBandwidthMeter implements BandwidthMeter {
        private DummyBandwidthMeter() {
        }

        public long getBitrateEstimate() {
            return 0;
        }

        @Nullable
        public TransferListener getTransferListener() {
            return null;
        }

        public void addEventListener(Handler eventHandler, BandwidthMeter.EventListener eventListener) {
        }

        public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        }
    }
}
