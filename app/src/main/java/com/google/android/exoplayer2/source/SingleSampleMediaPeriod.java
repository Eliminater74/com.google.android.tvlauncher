package com.google.android.exoplayer2.source;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class SingleSampleMediaPeriod implements MediaPeriod, Loader.Callback<SourceLoadable> {
    private static final int INITIAL_SAMPLE_SIZE = 1024;
    /* access modifiers changed from: private */
    public final MediaSourceEventListener.EventDispatcher eventDispatcher;
    final Format format;
    final Loader loader = new Loader("Loader:SingleSampleMediaPeriod");
    final boolean treatLoadErrorsAsEndOfStream;
    private final DataSource.Factory dataSourceFactory;
    private final DataSpec dataSpec;
    private final long durationUs;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final ArrayList<SampleStreamImpl> sampleStreams = new ArrayList<>();
    private final TrackGroupArray tracks;
    @Nullable
    private final TransferListener transferListener;
    boolean loadingFinished;
    boolean loadingSucceeded;
    boolean notifiedReadingStarted;
    byte[] sampleData;
    int sampleSize;

    public SingleSampleMediaPeriod(DataSpec dataSpec2, DataSource.Factory dataSourceFactory2, @Nullable TransferListener transferListener2, Format format2, long durationUs2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, MediaSourceEventListener.EventDispatcher eventDispatcher2, boolean treatLoadErrorsAsEndOfStream2) {
        this.dataSpec = dataSpec2;
        this.dataSourceFactory = dataSourceFactory2;
        this.transferListener = transferListener2;
        this.format = format2;
        this.durationUs = durationUs2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.eventDispatcher = eventDispatcher2;
        this.treatLoadErrorsAsEndOfStream = treatLoadErrorsAsEndOfStream2;
        this.tracks = new TrackGroupArray(new TrackGroup(format2));
        eventDispatcher2.mediaPeriodCreated();
    }

    public List getStreamKeys(List list) {
        return MediaPeriod$$CC.getStreamKeys$$dflt$$(this, list);
    }

    public void release() {
        this.loader.release();
        this.eventDispatcher.mediaPeriodReleased();
    }

    public void prepare(MediaPeriod.Callback callback, long positionUs) {
        callback.onPrepared(this);
    }

    public void maybeThrowPrepareError() throws IOException {
    }

    public TrackGroupArray getTrackGroups() {
        return this.tracks;
    }

    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        for (int i = 0; i < selections.length; i++) {
            if (streams[i] != null && (selections[i] == null || !mayRetainStreamFlags[i])) {
                this.sampleStreams.remove(streams[i]);
                streams[i] = null;
            }
            if (streams[i] == null && selections[i] != null) {
                SampleStreamImpl stream = new SampleStreamImpl();
                this.sampleStreams.add(stream);
                streams[i] = stream;
                streamResetFlags[i] = true;
            }
        }
        return positionUs;
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
    }

    public void reevaluateBuffer(long positionUs) {
    }

    public boolean continueLoading(long positionUs) {
        if (this.loadingFinished || this.loader.isLoading()) {
            return false;
        }
        DataSource dataSource = this.dataSourceFactory.createDataSource();
        TransferListener transferListener2 = this.transferListener;
        if (transferListener2 != null) {
            dataSource.addTransferListener(transferListener2);
        }
        this.eventDispatcher.loadStarted(this.dataSpec, 1, -1, this.format, 0, null, 0, this.durationUs, this.loader.startLoading(new SourceLoadable(this.dataSpec, dataSource), this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(1)));
        return true;
    }

    public long readDiscontinuity() {
        if (this.notifiedReadingStarted) {
            return C0841C.TIME_UNSET;
        }
        this.eventDispatcher.readingStarted();
        this.notifiedReadingStarted = true;
        return C0841C.TIME_UNSET;
    }

    public long getNextLoadPositionUs() {
        return (this.loadingFinished || this.loader.isLoading()) ? Long.MIN_VALUE : 0;
    }

    public long getBufferedPositionUs() {
        return this.loadingFinished ? Long.MIN_VALUE : 0;
    }

    public long seekToUs(long positionUs) {
        for (int i = 0; i < this.sampleStreams.size(); i++) {
            this.sampleStreams.get(i).reset();
        }
        return positionUs;
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        return positionUs;
    }

    public void onLoadCompleted(SourceLoadable loadable, long elapsedRealtimeMs, long loadDurationMs) {
        this.sampleSize = (int) loadable.dataSource.getBytesRead();
        this.sampleData = loadable.sampleData;
        this.loadingFinished = true;
        this.loadingSucceeded = true;
        this.eventDispatcher.loadCompleted(loadable.dataSpec, loadable.dataSource.getLastOpenedUri(), loadable.dataSource.getLastResponseHeaders(), 1, -1, this.format, 0, null, 0, this.durationUs, elapsedRealtimeMs, loadDurationMs, (long) this.sampleSize);
    }

    public void onLoadCanceled(SourceLoadable loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        this.eventDispatcher.loadCanceled(loadable.dataSpec, loadable.dataSource.getLastOpenedUri(), loadable.dataSource.getLastResponseHeaders(), 1, -1, null, 0, null, 0, this.durationUs, elapsedRealtimeMs, loadDurationMs, loadable.dataSource.getBytesRead());
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.upstream.Loader.LoadErrorAction onLoadError(com.google.android.exoplayer2.source.SingleSampleMediaPeriod.SourceLoadable r31, long r32, long r34, java.io.IOException r36, int r37) {
        /*
            r30 = this;
            r0 = r30
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r1 = r0.loadErrorHandlingPolicy
            r2 = 1
            r3 = r34
            r5 = r36
            r6 = r37
            long r1 = r1.getRetryDelayMsFor(r2, r3, r5, r6)
            r3 = 0
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r6 = 1
            int r7 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r7 == 0) goto L_0x0027
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r7 = r0.loadErrorHandlingPolicy
            int r7 = r7.getMinimumLoadableRetryCount(r6)
            r8 = r37
            if (r8 < r7) goto L_0x0025
            goto L_0x0029
        L_0x0025:
            r7 = 0
            goto L_0x002a
        L_0x0027:
            r8 = r37
        L_0x0029:
            r7 = 1
        L_0x002a:
            boolean r9 = r0.treatLoadErrorsAsEndOfStream
            if (r9 == 0) goto L_0x0035
            if (r7 == 0) goto L_0x0035
            r0.loadingFinished = r6
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r3 = com.google.android.exoplayer2.upstream.Loader.DONT_RETRY
            goto L_0x0041
        L_0x0035:
            int r9 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x003e
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r3 = com.google.android.exoplayer2.upstream.Loader.createRetryAction(r3, r1)
            goto L_0x0040
        L_0x003e:
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r3 = com.google.android.exoplayer2.upstream.Loader.DONT_RETRY_FATAL
        L_0x0040:
        L_0x0041:
            com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher r9 = r0.eventDispatcher
            r4 = r31
            com.google.android.exoplayer2.upstream.DataSpec r10 = r4.dataSpec
            com.google.android.exoplayer2.upstream.StatsDataSource r5 = r31.dataSource
            android.net.Uri r11 = r5.getLastOpenedUri()
            com.google.android.exoplayer2.upstream.StatsDataSource r5 = r31.dataSource
            java.util.Map r12 = r5.getLastResponseHeaders()
            r13 = 1
            com.google.android.exoplayer2.Format r15 = r0.format
            r16 = 0
            r17 = 0
            r18 = 0
            r20 = r15
            long r14 = r0.durationUs
            com.google.android.exoplayer2.upstream.StatsDataSource r21 = r31.dataSource
            long r26 = r21.getBytesRead()
            boolean r21 = r3.isRetry()
            r29 = r21 ^ 1
            r21 = r14
            r5 = -1
            r14 = r5
            r15 = r20
            r20 = r21
            r22 = r32
            r24 = r34
            r28 = r36
            r9.loadError(r10, r11, r12, r13, r14, r15, r16, r17, r18, r20, r22, r24, r26, r28, r29)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SingleSampleMediaPeriod.onLoadError(com.google.android.exoplayer2.source.SingleSampleMediaPeriod$SourceLoadable, long, long, java.io.IOException, int):com.google.android.exoplayer2.upstream.Loader$LoadErrorAction");
    }

    static final class SourceLoadable implements Loader.Loadable {
        /* access modifiers changed from: private */
        public final StatsDataSource dataSource;
        public final DataSpec dataSpec;
        /* access modifiers changed from: private */
        public byte[] sampleData;

        public SourceLoadable(DataSpec dataSpec2, DataSource dataSource2) {
            this.dataSpec = dataSpec2;
            this.dataSource = new StatsDataSource(dataSource2);
        }

        public void cancelLoad() {
        }

        public void load() throws IOException, InterruptedException {
            this.dataSource.resetBytesRead();
            try {
                this.dataSource.open(this.dataSpec);
                int result = 0;
                while (result != -1) {
                    int sampleSize = (int) this.dataSource.getBytesRead();
                    if (this.sampleData == null) {
                        this.sampleData = new byte[1024];
                    } else if (sampleSize == this.sampleData.length) {
                        this.sampleData = Arrays.copyOf(this.sampleData, this.sampleData.length * 2);
                    }
                    result = this.dataSource.read(this.sampleData, sampleSize, this.sampleData.length - sampleSize);
                }
            } finally {
                Util.closeQuietly(this.dataSource);
            }
        }
    }

    private final class SampleStreamImpl implements SampleStream {
        private static final int STREAM_STATE_END_OF_STREAM = 2;
        private static final int STREAM_STATE_SEND_FORMAT = 0;
        private static final int STREAM_STATE_SEND_SAMPLE = 1;
        private boolean notifiedDownstreamFormat;
        private int streamState;

        private SampleStreamImpl() {
        }

        public void reset() {
            if (this.streamState == 2) {
                this.streamState = 1;
            }
        }

        public boolean isReady() {
            return SingleSampleMediaPeriod.this.loadingFinished;
        }

        public void maybeThrowError() throws IOException {
            if (!SingleSampleMediaPeriod.this.treatLoadErrorsAsEndOfStream) {
                SingleSampleMediaPeriod.this.loader.maybeThrowError();
            }
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean requireFormat) {
            maybeNotifyDownstreamFormat();
            int i = this.streamState;
            if (i == 2) {
                buffer.addFlag(4);
                return -4;
            } else if (requireFormat || i == 0) {
                formatHolder.format = SingleSampleMediaPeriod.this.format;
                this.streamState = 1;
                return -5;
            } else if (!SingleSampleMediaPeriod.this.loadingFinished) {
                return -3;
            } else {
                if (SingleSampleMediaPeriod.this.loadingSucceeded) {
                    buffer.addFlag(1);
                    buffer.timeUs = 0;
                    if (buffer.isFlagsOnly()) {
                        return -4;
                    }
                    buffer.ensureSpaceForWrite(SingleSampleMediaPeriod.this.sampleSize);
                    buffer.data.put(SingleSampleMediaPeriod.this.sampleData, 0, SingleSampleMediaPeriod.this.sampleSize);
                } else {
                    buffer.addFlag(4);
                }
                this.streamState = 2;
                return -4;
            }
        }

        public int skipData(long positionUs) {
            maybeNotifyDownstreamFormat();
            if (positionUs <= 0 || this.streamState == 2) {
                return 0;
            }
            this.streamState = 2;
            return 1;
        }

        private void maybeNotifyDownstreamFormat() {
            if (!this.notifiedDownstreamFormat) {
                SingleSampleMediaPeriod.this.eventDispatcher.downstreamFormatChanged(MimeTypes.getTrackType(SingleSampleMediaPeriod.this.format.sampleMimeType), SingleSampleMediaPeriod.this.format, 0, null, 0);
                this.notifiedDownstreamFormat = true;
            }
        }
    }
}
