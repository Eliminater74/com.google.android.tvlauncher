package com.google.android.exoplayer2.source.dash;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.emsg.EventMessageDecoder;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public final class PlayerEmsgHandler implements Handler.Callback {
    private static final int EMSG_MANIFEST_EXPIRED = 1;
    /* access modifiers changed from: private */
    public final EventMessageDecoder decoder = new EventMessageDecoder();
    /* access modifiers changed from: private */
    public final Handler handler = Util.createHandler(this);
    private final Allocator allocator;
    private final TreeMap<Long, Long> manifestPublishTimeToExpiryTimeUs = new TreeMap<>();
    private final PlayerEmsgCallback playerEmsgCallback;
    private long expiredManifestPublishTimeUs;
    private boolean isWaitingForManifestRefresh;
    private long lastLoadedChunkEndTimeBeforeRefreshUs = C0841C.TIME_UNSET;
    private long lastLoadedChunkEndTimeUs = C0841C.TIME_UNSET;
    private DashManifest manifest;
    private boolean released;

    public PlayerEmsgHandler(DashManifest manifest2, PlayerEmsgCallback playerEmsgCallback2, Allocator allocator2) {
        this.manifest = manifest2;
        this.playerEmsgCallback = playerEmsgCallback2;
        this.allocator = allocator2;
    }

    public static boolean isPlayerEmsgEvent(String schemeIdUri, String value) {
        return "urn:mpeg:dash:event:2012".equals(schemeIdUri) && (IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(value) || "2".equals(value) || "3".equals(value));
    }

    /* access modifiers changed from: private */
    public static long getManifestPublishTimeMsInEmsg(EventMessage eventMessage) {
        try {
            return Util.parseXsDateTime(Util.fromUtf8Bytes(eventMessage.messageData));
        } catch (ParserException e) {
            return C0841C.TIME_UNSET;
        }
    }

    public void updateManifest(DashManifest newManifest) {
        this.isWaitingForManifestRefresh = false;
        this.expiredManifestPublishTimeUs = C0841C.TIME_UNSET;
        this.manifest = newManifest;
        removePreviouslyExpiredManifestPublishTimeValues();
    }

    /* access modifiers changed from: package-private */
    public boolean maybeRefreshManifestBeforeLoadingNextChunk(long presentationPositionUs) {
        if (!this.manifest.dynamic) {
            return false;
        }
        if (this.isWaitingForManifestRefresh) {
            return true;
        }
        boolean manifestRefreshNeeded = false;
        Map.Entry<Long, Long> expiredEntry = ceilingExpiryEntryForPublishTime(this.manifest.publishTimeMs);
        if (expiredEntry != null && expiredEntry.getValue().longValue() < presentationPositionUs) {
            this.expiredManifestPublishTimeUs = expiredEntry.getKey().longValue();
            notifyManifestPublishTimeExpired();
            manifestRefreshNeeded = true;
        }
        if (manifestRefreshNeeded) {
            maybeNotifyDashManifestRefreshNeeded();
        }
        return manifestRefreshNeeded;
    }

    /* access modifiers changed from: package-private */
    public boolean maybeRefreshManifestOnLoadingError(Chunk chunk) {
        if (!this.manifest.dynamic) {
            return false;
        }
        if (this.isWaitingForManifestRefresh) {
            return true;
        }
        long j = this.lastLoadedChunkEndTimeUs;
        if (!(j != C0841C.TIME_UNSET && j < chunk.startTimeUs)) {
            return false;
        }
        maybeNotifyDashManifestRefreshNeeded();
        return true;
    }

    /* access modifiers changed from: package-private */
    public void onChunkLoadCompleted(Chunk chunk) {
        if (this.lastLoadedChunkEndTimeUs != C0841C.TIME_UNSET || chunk.endTimeUs > this.lastLoadedChunkEndTimeUs) {
            this.lastLoadedChunkEndTimeUs = chunk.endTimeUs;
        }
    }

    public PlayerTrackEmsgHandler newPlayerTrackEmsgHandler() {
        return new PlayerTrackEmsgHandler(new SampleQueue(this.allocator));
    }

    public void release() {
        this.released = true;
        this.handler.removeCallbacksAndMessages(null);
    }

    public boolean handleMessage(Message message) {
        if (this.released) {
            return true;
        }
        if (message.what != 1) {
            return false;
        }
        ManifestExpiryEventInfo messageObj = (ManifestExpiryEventInfo) message.obj;
        handleManifestExpiredMessage(messageObj.eventTimeUs, messageObj.manifestPublishTimeMsInEmsg);
        return true;
    }

    private void handleManifestExpiredMessage(long eventTimeUs, long manifestPublishTimeMsInEmsg) {
        Long previousExpiryTimeUs = this.manifestPublishTimeToExpiryTimeUs.get(Long.valueOf(manifestPublishTimeMsInEmsg));
        if (previousExpiryTimeUs == null) {
            this.manifestPublishTimeToExpiryTimeUs.put(Long.valueOf(manifestPublishTimeMsInEmsg), Long.valueOf(eventTimeUs));
        } else if (previousExpiryTimeUs.longValue() > eventTimeUs) {
            this.manifestPublishTimeToExpiryTimeUs.put(Long.valueOf(manifestPublishTimeMsInEmsg), Long.valueOf(eventTimeUs));
        }
    }

    @Nullable
    private Map.Entry<Long, Long> ceilingExpiryEntryForPublishTime(long publishTimeMs) {
        return this.manifestPublishTimeToExpiryTimeUs.ceilingEntry(Long.valueOf(publishTimeMs));
    }

    private void removePreviouslyExpiredManifestPublishTimeValues() {
        Iterator<Map.Entry<Long, Long>> it = this.manifestPublishTimeToExpiryTimeUs.entrySet().iterator();
        while (it.hasNext()) {
            if (((Long) it.next().getKey()).longValue() < this.manifest.publishTimeMs) {
                it.remove();
            }
        }
    }

    private void notifyManifestPublishTimeExpired() {
        this.playerEmsgCallback.onDashManifestPublishTimeExpired(this.expiredManifestPublishTimeUs);
    }

    private void maybeNotifyDashManifestRefreshNeeded() {
        long j = this.lastLoadedChunkEndTimeBeforeRefreshUs;
        if (j == C0841C.TIME_UNSET || j != this.lastLoadedChunkEndTimeUs) {
            this.isWaitingForManifestRefresh = true;
            this.lastLoadedChunkEndTimeBeforeRefreshUs = this.lastLoadedChunkEndTimeUs;
            this.playerEmsgCallback.onDashManifestRefreshRequested();
        }
    }

    public interface PlayerEmsgCallback {
        void onDashManifestPublishTimeExpired(long j);

        void onDashManifestRefreshRequested();
    }

    private static final class ManifestExpiryEventInfo {
        public final long eventTimeUs;
        public final long manifestPublishTimeMsInEmsg;

        public ManifestExpiryEventInfo(long eventTimeUs2, long manifestPublishTimeMsInEmsg2) {
            this.eventTimeUs = eventTimeUs2;
            this.manifestPublishTimeMsInEmsg = manifestPublishTimeMsInEmsg2;
        }
    }

    public final class PlayerTrackEmsgHandler implements TrackOutput {
        private final MetadataInputBuffer buffer = new MetadataInputBuffer();
        private final FormatHolder formatHolder = new FormatHolder();
        private final SampleQueue sampleQueue;

        PlayerTrackEmsgHandler(SampleQueue sampleQueue2) {
            this.sampleQueue = sampleQueue2;
        }

        public void format(Format format) {
            this.sampleQueue.format(format);
        }

        public int sampleData(ExtractorInput input, int length, boolean allowEndOfInput) throws IOException, InterruptedException {
            return this.sampleQueue.sampleData(input, length, allowEndOfInput);
        }

        public void sampleData(ParsableByteArray data, int length) {
            this.sampleQueue.sampleData(data, length);
        }

        public void sampleMetadata(long timeUs, int flags, int size, int offset, @Nullable TrackOutput.CryptoData encryptionData) {
            this.sampleQueue.sampleMetadata(timeUs, flags, size, offset, encryptionData);
            parseAndDiscardSamples();
        }

        public boolean maybeRefreshManifestBeforeLoadingNextChunk(long presentationPositionUs) {
            return PlayerEmsgHandler.this.maybeRefreshManifestBeforeLoadingNextChunk(presentationPositionUs);
        }

        public void onChunkLoadCompleted(Chunk chunk) {
            PlayerEmsgHandler.this.onChunkLoadCompleted(chunk);
        }

        public boolean maybeRefreshManifestOnLoadingError(Chunk chunk) {
            return PlayerEmsgHandler.this.maybeRefreshManifestOnLoadingError(chunk);
        }

        public void release() {
            this.sampleQueue.reset();
        }

        private void parseAndDiscardSamples() {
            while (this.sampleQueue.hasNextSample()) {
                MetadataInputBuffer inputBuffer = dequeueSample();
                if (inputBuffer != null) {
                    long eventTimeUs = inputBuffer.timeUs;
                    EventMessage eventMessage = (EventMessage) PlayerEmsgHandler.this.decoder.decode(inputBuffer).get(0);
                    if (PlayerEmsgHandler.isPlayerEmsgEvent(eventMessage.schemeIdUri, eventMessage.value)) {
                        parsePlayerEmsgEvent(eventTimeUs, eventMessage);
                    }
                }
            }
            this.sampleQueue.discardToRead();
        }

        @Nullable
        private MetadataInputBuffer dequeueSample() {
            this.buffer.clear();
            if (this.sampleQueue.read(this.formatHolder, this.buffer, false, false, 0) != -4) {
                return null;
            }
            this.buffer.flip();
            return this.buffer;
        }

        private void parsePlayerEmsgEvent(long eventTimeUs, EventMessage eventMessage) {
            long manifestPublishTimeMsInEmsg = PlayerEmsgHandler.getManifestPublishTimeMsInEmsg(eventMessage);
            if (manifestPublishTimeMsInEmsg != C0841C.TIME_UNSET) {
                onManifestExpiredMessageEncountered(eventTimeUs, manifestPublishTimeMsInEmsg);
            }
        }

        private void onManifestExpiredMessageEncountered(long eventTimeUs, long manifestPublishTimeMsInEmsg) {
            PlayerEmsgHandler.this.handler.sendMessage(PlayerEmsgHandler.this.handler.obtainMessage(1, new ManifestExpiryEventInfo(eventTimeUs, manifestPublishTimeMsInEmsg)));
        }
    }
}
