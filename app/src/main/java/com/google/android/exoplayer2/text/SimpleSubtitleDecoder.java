package com.google.android.exoplayer2.text;

import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.decoder.SimpleDecoder;

import java.nio.ByteBuffer;

public abstract class SimpleSubtitleDecoder extends SimpleDecoder<SubtitleInputBuffer, SubtitleOutputBuffer, SubtitleDecoderException> implements SubtitleDecoder {
    private final String name;

    protected SimpleSubtitleDecoder(String name2) {
        super(new SubtitleInputBuffer[2], new SubtitleOutputBuffer[2]);
        this.name = name2;
        setInitialInputBufferSize(1024);
    }

    /* access modifiers changed from: protected */
    public abstract Subtitle decode(byte[] bArr, int i, boolean z) throws SubtitleDecoderException;

    public final String getName() {
        return this.name;
    }

    public void setPositionUs(long timeUs) {
    }

    /* access modifiers changed from: protected */
    public final SubtitleInputBuffer createInputBuffer() {
        return new SubtitleInputBuffer();
    }

    /* access modifiers changed from: protected */
    public final SubtitleOutputBuffer createOutputBuffer() {
        return new SimpleSubtitleOutputBuffer(this);
    }

    /* access modifiers changed from: protected */
    public final SubtitleDecoderException createUnexpectedDecodeException(Throwable error) {
        return new SubtitleDecoderException("Unexpected decode error", error);
    }

    /* access modifiers changed from: protected */
    public final void releaseOutputBuffer(SubtitleOutputBuffer buffer) {
        super.releaseOutputBuffer((OutputBuffer) buffer);
    }

    /* access modifiers changed from: protected */
    public final SubtitleDecoderException decode(SubtitleInputBuffer inputBuffer, SubtitleOutputBuffer outputBuffer, boolean reset) {
        try {
            ByteBuffer inputData = inputBuffer.data;
            SubtitleOutputBuffer subtitleOutputBuffer = outputBuffer;
            subtitleOutputBuffer.setContent(inputBuffer.timeUs, decode(inputData.array(), inputData.limit(), reset), inputBuffer.subsampleOffsetUs);
            outputBuffer.clearFlag(Integer.MIN_VALUE);
            return null;
        } catch (SubtitleDecoderException e) {
            return e;
        }
    }
}
