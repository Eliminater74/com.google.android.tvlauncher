package com.google.android.exoplayer2.source.dash;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.metadata.emsg.EventMessageEncoder;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.dash.manifest.EventStream;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

final class EventSampleStream implements SampleStream {
    private int currentIndex;
    private final EventMessageEncoder eventMessageEncoder = new EventMessageEncoder();
    private EventStream eventStream;
    private boolean eventStreamAppendable;
    private long[] eventTimesUs;
    private boolean isFormatSentDownstream;
    private long pendingSeekPositionUs = C0841C.TIME_UNSET;
    private final Format upstreamFormat;

    public EventSampleStream(EventStream eventStream2, Format upstreamFormat2, boolean eventStreamAppendable2) {
        this.upstreamFormat = upstreamFormat2;
        this.eventStream = eventStream2;
        this.eventTimesUs = eventStream2.presentationTimesUs;
        updateEventStream(eventStream2, eventStreamAppendable2);
    }

    public String eventStreamId() {
        return this.eventStream.mo14759id();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchCeil(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int */
    public void updateEventStream(EventStream eventStream2, boolean eventStreamAppendable2) {
        int i = this.currentIndex;
        long lastReadPositionUs = i == 0 ? -9223372036854775807L : this.eventTimesUs[i - 1];
        this.eventStreamAppendable = eventStreamAppendable2;
        this.eventStream = eventStream2;
        this.eventTimesUs = eventStream2.presentationTimesUs;
        long j = this.pendingSeekPositionUs;
        if (j != C0841C.TIME_UNSET) {
            seekToUs(j);
        } else if (lastReadPositionUs != C0841C.TIME_UNSET) {
            this.currentIndex = Util.binarySearchCeil(this.eventTimesUs, lastReadPositionUs, false, false);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchCeil(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int */
    public void seekToUs(long positionUs) {
        boolean isPendingSeek = false;
        this.currentIndex = Util.binarySearchCeil(this.eventTimesUs, positionUs, true, false);
        if (this.eventStreamAppendable && this.currentIndex == this.eventTimesUs.length) {
            isPendingSeek = true;
        }
        this.pendingSeekPositionUs = isPendingSeek ? positionUs : C0841C.TIME_UNSET;
    }

    public boolean isReady() {
        return true;
    }

    public void maybeThrowError() throws IOException {
    }

    public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired) {
        if (formatRequired || !this.isFormatSentDownstream) {
            formatHolder.format = this.upstreamFormat;
            this.isFormatSentDownstream = true;
            return -5;
        }
        int sampleIndex = this.currentIndex;
        if (sampleIndex != this.eventTimesUs.length) {
            this.currentIndex = sampleIndex + 1;
            byte[] serializedEvent = this.eventMessageEncoder.encode(this.eventStream.events[sampleIndex]);
            if (serializedEvent == null) {
                return -3;
            }
            buffer.ensureSpaceForWrite(serializedEvent.length);
            buffer.setFlags(1);
            buffer.data.put(serializedEvent);
            buffer.timeUs = this.eventTimesUs[sampleIndex];
            return -4;
        } else if (this.eventStreamAppendable) {
            return -3;
        } else {
            buffer.setFlags(4);
            return -4;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchCeil(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int */
    public int skipData(long positionUs) {
        int newIndex = Math.max(this.currentIndex, Util.binarySearchCeil(this.eventTimesUs, positionUs, true, false));
        int skipped = newIndex - this.currentIndex;
        this.currentIndex = newIndex;
        return skipped;
    }
}
