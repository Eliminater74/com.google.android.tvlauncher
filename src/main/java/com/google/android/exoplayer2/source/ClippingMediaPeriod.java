package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

public final class ClippingMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
    private MediaPeriod.Callback callback;
    long endUs;
    public final MediaPeriod mediaPeriod;
    private long pendingInitialDiscontinuityPositionUs;
    private ClippingSampleStream[] sampleStreams = new ClippingSampleStream[0];
    long startUs;

    public List getStreamKeys(List list) {
        return MediaPeriod$$CC.getStreamKeys$$dflt$$(this, list);
    }

    public ClippingMediaPeriod(MediaPeriod mediaPeriod2, boolean enableInitialDiscontinuity, long startUs2, long endUs2) {
        this.mediaPeriod = mediaPeriod2;
        this.pendingInitialDiscontinuityPositionUs = enableInitialDiscontinuity ? startUs2 : C0841C.TIME_UNSET;
        this.startUs = startUs2;
        this.endUs = endUs2;
    }

    public void updateClipping(long startUs2, long endUs2) {
        this.startUs = startUs2;
        this.endUs = endUs2;
    }

    public void prepare(MediaPeriod.Callback callback2, long positionUs) {
        this.callback = callback2;
        this.mediaPeriod.prepare(this, positionUs);
    }

    public void maybeThrowPrepareError() throws IOException {
        this.mediaPeriod.maybeThrowPrepareError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.mediaPeriod.getTrackGroups();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005e, code lost:
        if (r1 > r3) goto L_0x0061;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x006b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[] r10, boolean[] r11, com.google.android.exoplayer2.source.SampleStream[] r12, boolean[] r13, long r14) {
        /*
            r9 = this;
            int r0 = r12.length
            com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream[] r0 = new com.google.android.exoplayer2.source.ClippingMediaPeriod.ClippingSampleStream[r0]
            r9.sampleStreams = r0
            int r0 = r12.length
            com.google.android.exoplayer2.source.SampleStream[] r0 = new com.google.android.exoplayer2.source.SampleStream[r0]
            r1 = 0
        L_0x0009:
            int r2 = r12.length
            r8 = 0
            if (r1 >= r2) goto L_0x0022
            com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream[] r2 = r9.sampleStreams
            r3 = r12[r1]
            com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream r3 = (com.google.android.exoplayer2.source.ClippingMediaPeriod.ClippingSampleStream) r3
            r2[r1] = r3
            r3 = r2[r1]
            if (r3 == 0) goto L_0x001d
            r2 = r2[r1]
            com.google.android.exoplayer2.source.SampleStream r8 = r2.childStream
        L_0x001d:
            r0[r1] = r8
            int r1 = r1 + 1
            goto L_0x0009
        L_0x0022:
            com.google.android.exoplayer2.source.MediaPeriod r1 = r9.mediaPeriod
            r2 = r10
            r3 = r11
            r4 = r0
            r5 = r13
            r6 = r14
            long r1 = r1.selectTracks(r2, r3, r4, r5, r6)
            boolean r3 = r9.isPendingInitialDiscontinuity()
            if (r3 == 0) goto L_0x0043
            long r3 = r9.startUs
            int r5 = (r14 > r3 ? 1 : (r14 == r3 ? 0 : -1))
            if (r5 != 0) goto L_0x0043
            boolean r3 = shouldKeepInitialDiscontinuity(r3, r10)
            if (r3 == 0) goto L_0x0043
            r3 = r1
            goto L_0x0048
        L_0x0043:
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x0048:
            r9.pendingInitialDiscontinuityPositionUs = r3
            int r3 = (r1 > r14 ? 1 : (r1 == r14 ? 0 : -1))
            if (r3 == 0) goto L_0x0063
            long r3 = r9.startUs
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 < 0) goto L_0x0061
            long r3 = r9.endUs
            r5 = -9223372036854775808
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x0063
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x0061
            goto L_0x0063
        L_0x0061:
            r3 = 0
            goto L_0x0064
        L_0x0063:
            r3 = 1
        L_0x0064:
            com.google.android.exoplayer2.util.Assertions.checkState(r3)
            r3 = 0
        L_0x0068:
            int r4 = r12.length
            if (r3 >= r4) goto L_0x0096
            r4 = r0[r3]
            if (r4 != 0) goto L_0x0074
            com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream[] r4 = r9.sampleStreams
            r4[r3] = r8
            goto L_0x008d
        L_0x0074:
            r4 = r12[r3]
            if (r4 == 0) goto L_0x0082
            com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream[] r4 = r9.sampleStreams
            r4 = r4[r3]
            com.google.android.exoplayer2.source.SampleStream r4 = r4.childStream
            r5 = r0[r3]
            if (r4 == r5) goto L_0x008d
        L_0x0082:
            com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream[] r4 = r9.sampleStreams
            com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream r5 = new com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream
            r6 = r0[r3]
            r5.<init>(r6)
            r4[r3] = r5
        L_0x008d:
            com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream[] r4 = r9.sampleStreams
            r4 = r4[r3]
            r12[r3] = r4
            int r3 = r3 + 1
            goto L_0x0068
        L_0x0096:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ClippingMediaPeriod.selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long):long");
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        this.mediaPeriod.discardBuffer(positionUs, toKeyframe);
    }

    public void reevaluateBuffer(long positionUs) {
        this.mediaPeriod.reevaluateBuffer(positionUs);
    }

    public long readDiscontinuity() {
        if (isPendingInitialDiscontinuity()) {
            long initialDiscontinuityUs = this.pendingInitialDiscontinuityPositionUs;
            this.pendingInitialDiscontinuityPositionUs = C0841C.TIME_UNSET;
            long childDiscontinuityUs = readDiscontinuity();
            return childDiscontinuityUs != C0841C.TIME_UNSET ? childDiscontinuityUs : initialDiscontinuityUs;
        }
        long discontinuityUs = this.mediaPeriod.readDiscontinuity();
        if (discontinuityUs == C0841C.TIME_UNSET) {
            return C0841C.TIME_UNSET;
        }
        boolean z = true;
        Assertions.checkState(discontinuityUs >= this.startUs);
        long j = this.endUs;
        if (j != Long.MIN_VALUE && discontinuityUs > j) {
            z = false;
        }
        Assertions.checkState(z);
        return discontinuityUs;
    }

    public long getBufferedPositionUs() {
        long bufferedPositionUs = this.mediaPeriod.getBufferedPositionUs();
        if (bufferedPositionUs != Long.MIN_VALUE) {
            long j = this.endUs;
            if (j == Long.MIN_VALUE || bufferedPositionUs < j) {
                return bufferedPositionUs;
            }
        }
        return Long.MIN_VALUE;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0032, code lost:
        if (r0 > r3) goto L_0x0035;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long seekToUs(long r9) {
        /*
            r8 = this;
            r0 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r8.pendingInitialDiscontinuityPositionUs = r0
            com.google.android.exoplayer2.source.ClippingMediaPeriod$ClippingSampleStream[] r0 = r8.sampleStreams
            int r1 = r0.length
            r2 = 0
            r3 = 0
        L_0x000c:
            if (r3 >= r1) goto L_0x0018
            r4 = r0[r3]
            if (r4 == 0) goto L_0x0015
            r4.clearSentEos()
        L_0x0015:
            int r3 = r3 + 1
            goto L_0x000c
        L_0x0018:
            com.google.android.exoplayer2.source.MediaPeriod r0 = r8.mediaPeriod
            long r0 = r0.seekToUs(r9)
            int r3 = (r0 > r9 ? 1 : (r0 == r9 ? 0 : -1))
            if (r3 == 0) goto L_0x0034
            long r3 = r8.startUs
            int r5 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r5 < 0) goto L_0x0035
            long r3 = r8.endUs
            r5 = -9223372036854775808
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x0034
            int r5 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x0035
        L_0x0034:
            r2 = 1
        L_0x0035:
            com.google.android.exoplayer2.util.Assertions.checkState(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ClippingMediaPeriod.seekToUs(long):long");
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        long j = this.startUs;
        if (positionUs == j) {
            return j;
        }
        return this.mediaPeriod.getAdjustedSeekPositionUs(positionUs, clipSeekParameters(positionUs, seekParameters));
    }

    public long getNextLoadPositionUs() {
        long nextLoadPositionUs = this.mediaPeriod.getNextLoadPositionUs();
        if (nextLoadPositionUs != Long.MIN_VALUE) {
            long j = this.endUs;
            if (j == Long.MIN_VALUE || nextLoadPositionUs < j) {
                return nextLoadPositionUs;
            }
        }
        return Long.MIN_VALUE;
    }

    public boolean continueLoading(long positionUs) {
        return this.mediaPeriod.continueLoading(positionUs);
    }

    public void onPrepared(MediaPeriod mediaPeriod2) {
        this.callback.onPrepared(this);
    }

    public void onContinueLoadingRequested(MediaPeriod source) {
        this.callback.onContinueLoadingRequested(this);
    }

    /* access modifiers changed from: package-private */
    public boolean isPendingInitialDiscontinuity() {
        return this.pendingInitialDiscontinuityPositionUs != C0841C.TIME_UNSET;
    }

    private SeekParameters clipSeekParameters(long positionUs, SeekParameters seekParameters) {
        long toleranceBeforeUs = Util.constrainValue(seekParameters.toleranceBeforeUs, 0, positionUs - this.startUs);
        long j = seekParameters.toleranceAfterUs;
        long j2 = this.endUs;
        long toleranceAfterUs = Util.constrainValue(j, 0, j2 == Long.MIN_VALUE ? Long.MAX_VALUE : j2 - positionUs);
        if (toleranceBeforeUs == seekParameters.toleranceBeforeUs && toleranceAfterUs == seekParameters.toleranceAfterUs) {
            return seekParameters;
        }
        return new SeekParameters(toleranceBeforeUs, toleranceAfterUs);
    }

    private static boolean shouldKeepInitialDiscontinuity(long startUs2, TrackSelection[] selections) {
        if (startUs2 != 0) {
            for (TrackSelection trackSelection : selections) {
                if (trackSelection != null && !MimeTypes.isAudio(trackSelection.getSelectedFormat().sampleMimeType)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final class ClippingSampleStream implements SampleStream {
        public final SampleStream childStream;
        private boolean sentEos;

        public ClippingSampleStream(SampleStream childStream2) {
            this.childStream = childStream2;
        }

        public void clearSentEos() {
            this.sentEos = false;
        }

        public boolean isReady() {
            return !ClippingMediaPeriod.this.isPendingInitialDiscontinuity() && this.childStream.isReady();
        }

        public void maybeThrowError() throws IOException {
            this.childStream.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean requireFormat) {
            if (ClippingMediaPeriod.this.isPendingInitialDiscontinuity()) {
                return -3;
            }
            if (this.sentEos) {
                buffer.setFlags(4);
                return -4;
            }
            int result = this.childStream.readData(formatHolder, buffer, requireFormat);
            if (result == -5) {
                Format format = formatHolder.format;
                if (!(format.encoderDelay == 0 && format.encoderPadding == 0)) {
                    int encoderPadding = 0;
                    int encoderDelay = ClippingMediaPeriod.this.startUs != 0 ? 0 : format.encoderDelay;
                    if (ClippingMediaPeriod.this.endUs == Long.MIN_VALUE) {
                        encoderPadding = format.encoderPadding;
                    }
                    formatHolder.format = format.copyWithGaplessInfo(encoderDelay, encoderPadding);
                }
                return -5;
            } else if (ClippingMediaPeriod.this.endUs == Long.MIN_VALUE || ((result != -4 || buffer.timeUs < ClippingMediaPeriod.this.endUs) && (result != -3 || ClippingMediaPeriod.this.getBufferedPositionUs() != Long.MIN_VALUE))) {
                return result;
            } else {
                buffer.clear();
                buffer.setFlags(4);
                this.sentEos = true;
                return -4;
            }
        }

        public int skipData(long positionUs) {
            if (ClippingMediaPeriod.this.isPendingInitialDiscontinuity()) {
                return -3;
            }
            return this.childStream.skipData(positionUs);
        }
    }
}
