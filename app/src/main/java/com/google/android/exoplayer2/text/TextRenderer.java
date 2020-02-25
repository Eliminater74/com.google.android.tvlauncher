package com.google.android.exoplayer2.text;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

public final class TextRenderer extends BaseRenderer implements Handler.Callback {
    private static final int MSG_UPDATE_OUTPUT = 0;
    private static final int REPLACEMENT_STATE_NONE = 0;
    private static final int REPLACEMENT_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REPLACEMENT_STATE_WAIT_END_OF_STREAM = 2;
    private final SubtitleDecoderFactory decoderFactory;
    private final FormatHolder formatHolder;
    private final TextOutput output;
    @Nullable
    private final Handler outputHandler;
    private SubtitleDecoder decoder;
    private int decoderReplacementState;
    private boolean inputStreamEnded;
    private SubtitleInputBuffer nextInputBuffer;
    private SubtitleOutputBuffer nextSubtitle;
    private int nextSubtitleEventIndex;
    private boolean outputStreamEnded;
    private Format streamFormat;
    private SubtitleOutputBuffer subtitle;

    public TextRenderer(TextOutput output2, @Nullable Looper outputLooper) {
        this(output2, outputLooper, SubtitleDecoderFactory.DEFAULT);
    }

    public TextRenderer(TextOutput output2, @Nullable Looper outputLooper, SubtitleDecoderFactory decoderFactory2) {
        super(3);
        this.output = (TextOutput) Assertions.checkNotNull(output2);
        this.outputHandler = outputLooper == null ? null : Util.createHandler(outputLooper, this);
        this.decoderFactory = decoderFactory2;
        this.formatHolder = new FormatHolder();
    }

    public int supportsFormat(Format format) {
        if (this.decoderFactory.supportsFormat(format)) {
            return supportsFormatDrm(null, format.drmInitData) ? 4 : 2;
        }
        if (MimeTypes.isText(format.sampleMimeType)) {
            return 1;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onStreamChanged(Format[] formats, long offsetUs) throws ExoPlaybackException {
        this.streamFormat = formats[0];
        if (this.decoder != null) {
            this.decoderReplacementState = 1;
        } else {
            this.decoder = this.decoderFactory.createDecoder(this.streamFormat);
        }
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long positionUs, boolean joining) {
        clearOutput();
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        if (this.decoderReplacementState != 0) {
            replaceDecoder();
            return;
        }
        releaseBuffers();
        this.decoder.flush();
    }

    public void render(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException {
        if (!this.outputStreamEnded) {
            if (this.nextSubtitle == null) {
                this.decoder.setPositionUs(positionUs);
                try {
                    this.nextSubtitle = (SubtitleOutputBuffer) this.decoder.dequeueOutputBuffer();
                } catch (SubtitleDecoderException e) {
                    throw ExoPlaybackException.createForRenderer(e, getIndex());
                }
            }
            if (getState() == 2) {
                boolean textRendererNeedsUpdate = false;
                if (this.subtitle != null) {
                    long subtitleNextEventTimeUs = getNextEventTime();
                    while (subtitleNextEventTimeUs <= positionUs) {
                        this.nextSubtitleEventIndex++;
                        subtitleNextEventTimeUs = getNextEventTime();
                        textRendererNeedsUpdate = true;
                    }
                }
                SubtitleOutputBuffer subtitleOutputBuffer = this.nextSubtitle;
                if (subtitleOutputBuffer != null) {
                    if (subtitleOutputBuffer.isEndOfStream()) {
                        if (!textRendererNeedsUpdate && getNextEventTime() == Long.MAX_VALUE) {
                            if (this.decoderReplacementState == 2) {
                                replaceDecoder();
                            } else {
                                releaseBuffers();
                                this.outputStreamEnded = true;
                            }
                        }
                    } else if (this.nextSubtitle.timeUs <= positionUs) {
                        SubtitleOutputBuffer subtitleOutputBuffer2 = this.subtitle;
                        if (subtitleOutputBuffer2 != null) {
                            subtitleOutputBuffer2.release();
                        }
                        this.subtitle = this.nextSubtitle;
                        this.nextSubtitle = null;
                        this.nextSubtitleEventIndex = this.subtitle.getNextEventTimeIndex(positionUs);
                        textRendererNeedsUpdate = true;
                    }
                }
                if (textRendererNeedsUpdate) {
                    updateOutput(this.subtitle.getCues(positionUs));
                }
                if (this.decoderReplacementState != 2) {
                    while (!this.inputStreamEnded) {
                        try {
                            if (this.nextInputBuffer == null) {
                                this.nextInputBuffer = (SubtitleInputBuffer) this.decoder.dequeueInputBuffer();
                                if (this.nextInputBuffer == null) {
                                    return;
                                }
                            }
                            if (this.decoderReplacementState == 1) {
                                this.nextInputBuffer.setFlags(4);
                                this.decoder.queueInputBuffer(this.nextInputBuffer);
                                this.nextInputBuffer = null;
                                this.decoderReplacementState = 2;
                                return;
                            }
                            int result = readSource(this.formatHolder, this.nextInputBuffer, false);
                            if (result == -4) {
                                if (this.nextInputBuffer.isEndOfStream()) {
                                    this.inputStreamEnded = true;
                                } else {
                                    this.nextInputBuffer.subsampleOffsetUs = this.formatHolder.format.subsampleOffsetUs;
                                    this.nextInputBuffer.flip();
                                }
                                this.decoder.queueInputBuffer(this.nextInputBuffer);
                                this.nextInputBuffer = null;
                            } else if (result == -3) {
                                return;
                            }
                        } catch (SubtitleDecoderException e2) {
                            throw ExoPlaybackException.createForRenderer(e2, getIndex());
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        this.streamFormat = null;
        clearOutput();
        releaseDecoder();
    }

    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    public boolean isReady() {
        return true;
    }

    private void releaseBuffers() {
        this.nextInputBuffer = null;
        this.nextSubtitleEventIndex = -1;
        SubtitleOutputBuffer subtitleOutputBuffer = this.subtitle;
        if (subtitleOutputBuffer != null) {
            subtitleOutputBuffer.release();
            this.subtitle = null;
        }
        SubtitleOutputBuffer subtitleOutputBuffer2 = this.nextSubtitle;
        if (subtitleOutputBuffer2 != null) {
            subtitleOutputBuffer2.release();
            this.nextSubtitle = null;
        }
    }

    private void releaseDecoder() {
        releaseBuffers();
        this.decoder.release();
        this.decoder = null;
        this.decoderReplacementState = 0;
    }

    private void replaceDecoder() {
        releaseDecoder();
        this.decoder = this.decoderFactory.createDecoder(this.streamFormat);
    }

    private long getNextEventTime() {
        int i = this.nextSubtitleEventIndex;
        if (i == -1 || i >= this.subtitle.getEventTimeCount()) {
            return Long.MAX_VALUE;
        }
        return this.subtitle.getEventTime(this.nextSubtitleEventIndex);
    }

    private void updateOutput(List<Cue> cues) {
        Handler handler = this.outputHandler;
        if (handler != null) {
            handler.obtainMessage(0, cues).sendToTarget();
        } else {
            invokeUpdateOutputInternal(cues);
        }
    }

    private void clearOutput() {
        updateOutput(Collections.emptyList());
    }

    public boolean handleMessage(Message msg) {
        if (msg.what == 0) {
            invokeUpdateOutputInternal((List) msg.obj);
            return true;
        }
        throw new IllegalStateException();
    }

    private void invokeUpdateOutputInternal(List<Cue> cues) {
        this.output.onCues(cues);
    }

    @Deprecated
    public interface Output extends TextOutput {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface ReplacementState {
    }
}
