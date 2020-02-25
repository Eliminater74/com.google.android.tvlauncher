package com.google.android.exoplayer2.metadata;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;

public final class MetadataRenderer extends BaseRenderer implements Handler.Callback {
    private static final int MAX_PENDING_METADATA_COUNT = 5;
    private static final int MSG_INVOKE_RENDERER = 0;
    private final MetadataInputBuffer buffer;
    private final MetadataDecoderFactory decoderFactory;
    private final FormatHolder formatHolder;
    private final MetadataOutput output;
    @Nullable
    private final Handler outputHandler;
    private final Metadata[] pendingMetadata;
    private final long[] pendingMetadataTimestamps;
    private MetadataDecoder decoder;
    private boolean inputStreamEnded;
    private int pendingMetadataCount;
    private int pendingMetadataIndex;

    public MetadataRenderer(MetadataOutput output2, @Nullable Looper outputLooper) {
        this(output2, outputLooper, MetadataDecoderFactory.DEFAULT);
    }

    public MetadataRenderer(MetadataOutput output2, @Nullable Looper outputLooper, MetadataDecoderFactory decoderFactory2) {
        super(4);
        this.output = (MetadataOutput) Assertions.checkNotNull(output2);
        this.outputHandler = outputLooper == null ? null : Util.createHandler(outputLooper, this);
        this.decoderFactory = (MetadataDecoderFactory) Assertions.checkNotNull(decoderFactory2);
        this.formatHolder = new FormatHolder();
        this.buffer = new MetadataInputBuffer();
        this.pendingMetadata = new Metadata[5];
        this.pendingMetadataTimestamps = new long[5];
    }

    public int supportsFormat(Format format) {
        if (this.decoderFactory.supportsFormat(format)) {
            return supportsFormatDrm(null, format.drmInitData) ? 4 : 2;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onStreamChanged(Format[] formats, long offsetUs) throws ExoPlaybackException {
        this.decoder = this.decoderFactory.createDecoder(formats[0]);
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long positionUs, boolean joining) {
        flushPendingMetadata();
        this.inputStreamEnded = false;
    }

    public void render(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException {
        if (!this.inputStreamEnded && this.pendingMetadataCount < 5) {
            this.buffer.clear();
            if (readSource(this.formatHolder, this.buffer, false) == -4) {
                if (this.buffer.isEndOfStream()) {
                    this.inputStreamEnded = true;
                } else if (!this.buffer.isDecodeOnly()) {
                    this.buffer.subsampleOffsetUs = this.formatHolder.format.subsampleOffsetUs;
                    this.buffer.flip();
                    int index = (this.pendingMetadataIndex + this.pendingMetadataCount) % 5;
                    Metadata metadata = this.decoder.decode(this.buffer);
                    if (metadata != null) {
                        this.pendingMetadata[index] = metadata;
                        this.pendingMetadataTimestamps[index] = this.buffer.timeUs;
                        this.pendingMetadataCount++;
                    }
                }
            }
        }
        if (this.pendingMetadataCount > 0) {
            long[] jArr = this.pendingMetadataTimestamps;
            int i = this.pendingMetadataIndex;
            if (jArr[i] <= positionUs) {
                invokeRenderer(this.pendingMetadata[i]);
                Metadata[] metadataArr = this.pendingMetadata;
                int i2 = this.pendingMetadataIndex;
                metadataArr[i2] = null;
                this.pendingMetadataIndex = (i2 + 1) % 5;
                this.pendingMetadataCount--;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        flushPendingMetadata();
        this.decoder = null;
    }

    public boolean isEnded() {
        return this.inputStreamEnded;
    }

    public boolean isReady() {
        return true;
    }

    private void invokeRenderer(Metadata metadata) {
        Handler handler = this.outputHandler;
        if (handler != null) {
            handler.obtainMessage(0, metadata).sendToTarget();
        } else {
            invokeRendererInternal(metadata);
        }
    }

    private void flushPendingMetadata() {
        Arrays.fill(this.pendingMetadata, (Object) null);
        this.pendingMetadataIndex = 0;
        this.pendingMetadataCount = 0;
    }

    public boolean handleMessage(Message msg) {
        if (msg.what == 0) {
            invokeRendererInternal((Metadata) msg.obj);
            return true;
        }
        throw new IllegalStateException();
    }

    private void invokeRendererInternal(Metadata metadata) {
        this.output.onMetadata(metadata);
    }

    @Deprecated
    public interface Output extends MetadataOutput {
    }
}
