package com.google.android.exoplayer2.source;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.Allocation;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.primitives.UnsignedBytes;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SampleQueue implements TrackOutput {
    public static final int ADVANCE_FAILED = -1;
    private static final int INITIAL_SCRATCH_SIZE = 32;
    private final int allocationLength;
    private final Allocator allocator;
    private final SampleMetadataQueue.SampleExtrasHolder extrasHolder = new SampleMetadataQueue.SampleExtrasHolder();
    private final SampleMetadataQueue metadataQueue = new SampleMetadataQueue();
    private final ParsableByteArray scratch = new ParsableByteArray(32);
    private Format downstreamFormat;
    private AllocationNode firstAllocationNode = new AllocationNode(0, this.allocationLength);
    private Format lastUnadjustedFormat;
    private boolean pendingFormatAdjustment;
    private boolean pendingSplice;
    private AllocationNode readAllocationNode;
    private long sampleOffsetUs;
    private long totalBytesWritten;
    private UpstreamFormatChangedListener upstreamFormatChangeListener;
    private AllocationNode writeAllocationNode;

    public SampleQueue(Allocator allocator2) {
        this.allocator = allocator2;
        this.allocationLength = allocator2.getIndividualAllocationLength();
        AllocationNode allocationNode = this.firstAllocationNode;
        this.readAllocationNode = allocationNode;
        this.writeAllocationNode = allocationNode;
    }

    private static Format getAdjustedSampleFormat(Format format, long sampleOffsetUs2) {
        if (format == null) {
            return null;
        }
        if (sampleOffsetUs2 == 0 || format.subsampleOffsetUs == Long.MAX_VALUE) {
            return format;
        }
        return format.copyWithSubsampleOffsetUs(format.subsampleOffsetUs + sampleOffsetUs2);
    }

    public void reset() {
        reset(false);
    }

    public void reset(boolean resetUpstreamFormat) {
        this.metadataQueue.reset(resetUpstreamFormat);
        clearAllocationNodes(this.firstAllocationNode);
        this.firstAllocationNode = new AllocationNode(0, this.allocationLength);
        AllocationNode allocationNode = this.firstAllocationNode;
        this.readAllocationNode = allocationNode;
        this.writeAllocationNode = allocationNode;
        this.totalBytesWritten = 0;
        this.allocator.trim();
    }

    public void sourceId(int sourceId) {
        this.metadataQueue.sourceId(sourceId);
    }

    public void splice() {
        this.pendingSplice = true;
    }

    public int getWriteIndex() {
        return this.metadataQueue.getWriteIndex();
    }

    public void discardUpstreamSamples(int discardFromIndex) {
        AllocationNode allocationNode;
        this.totalBytesWritten = this.metadataQueue.discardUpstreamSamples(discardFromIndex);
        long j = this.totalBytesWritten;
        if (j == 0 || j == this.firstAllocationNode.startPosition) {
            clearAllocationNodes(this.firstAllocationNode);
            this.firstAllocationNode = new AllocationNode(this.totalBytesWritten, this.allocationLength);
            AllocationNode allocationNode2 = this.firstAllocationNode;
            this.readAllocationNode = allocationNode2;
            this.writeAllocationNode = allocationNode2;
            return;
        }
        AllocationNode lastNodeToKeep = this.firstAllocationNode;
        while (this.totalBytesWritten > lastNodeToKeep.endPosition) {
            lastNodeToKeep = lastNodeToKeep.next;
        }
        AllocationNode firstNodeToDiscard = lastNodeToKeep.next;
        clearAllocationNodes(firstNodeToDiscard);
        lastNodeToKeep.next = new AllocationNode(lastNodeToKeep.endPosition, this.allocationLength);
        if (this.totalBytesWritten == lastNodeToKeep.endPosition) {
            allocationNode = lastNodeToKeep.next;
        } else {
            allocationNode = lastNodeToKeep;
        }
        this.writeAllocationNode = allocationNode;
        if (this.readAllocationNode == firstNodeToDiscard) {
            this.readAllocationNode = lastNodeToKeep.next;
        }
    }

    public boolean hasNextSample() {
        return this.metadataQueue.hasNextSample();
    }

    public int getFirstIndex() {
        return this.metadataQueue.getFirstIndex();
    }

    public int getReadIndex() {
        return this.metadataQueue.getReadIndex();
    }

    public int peekSourceId() {
        return this.metadataQueue.peekSourceId();
    }

    public Format getUpstreamFormat() {
        return this.metadataQueue.getUpstreamFormat();
    }

    public long getLargestQueuedTimestampUs() {
        return this.metadataQueue.getLargestQueuedTimestampUs();
    }

    public boolean isLastSampleQueued() {
        return this.metadataQueue.isLastSampleQueued();
    }

    public long getFirstTimestampUs() {
        return this.metadataQueue.getFirstTimestampUs();
    }

    public void rewind() {
        this.metadataQueue.rewind();
        this.readAllocationNode = this.firstAllocationNode;
    }

    public void discardTo(long timeUs, boolean toKeyframe, boolean stopAtReadPosition) {
        discardDownstreamTo(this.metadataQueue.discardTo(timeUs, toKeyframe, stopAtReadPosition));
    }

    public void discardToRead() {
        discardDownstreamTo(this.metadataQueue.discardToRead());
    }

    public void discardToEnd() {
        discardDownstreamTo(this.metadataQueue.discardToEnd());
    }

    public int advanceToEnd() {
        return this.metadataQueue.advanceToEnd();
    }

    public int advanceTo(long timeUs, boolean toKeyframe, boolean allowTimeBeyondBuffer) {
        return this.metadataQueue.advanceTo(timeUs, toKeyframe, allowTimeBeyondBuffer);
    }

    public boolean setReadPosition(int sampleIndex) {
        return this.metadataQueue.setReadPosition(sampleIndex);
    }

    public int read(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired, boolean loadingFinished, long decodeOnlyUntilUs) {
        int result = this.metadataQueue.read(formatHolder, buffer, formatRequired, loadingFinished, this.downstreamFormat, this.extrasHolder);
        if (result == -5) {
            this.downstreamFormat = formatHolder.format;
            return -5;
        } else if (result == -4) {
            if (!buffer.isEndOfStream()) {
                if (buffer.timeUs < decodeOnlyUntilUs) {
                    buffer.addFlag(Integer.MIN_VALUE);
                }
                if (!buffer.isFlagsOnly()) {
                    if (buffer.isEncrypted()) {
                        readEncryptionData(buffer, this.extrasHolder);
                    }
                    buffer.ensureSpaceForWrite(this.extrasHolder.size);
                    readData(this.extrasHolder.offset, buffer.data, this.extrasHolder.size);
                }
            }
            return -4;
        } else if (result == -3) {
            return -3;
        } else {
            throw new IllegalStateException();
        }
    }

    private void readEncryptionData(DecoderInputBuffer buffer, SampleMetadataQueue.SampleExtrasHolder extrasHolder2) {
        int subsampleCount;
        int[] clearDataSizes;
        int[] encryptedDataSizes;
        DecoderInputBuffer decoderInputBuffer = buffer;
        SampleMetadataQueue.SampleExtrasHolder sampleExtrasHolder = extrasHolder2;
        long offset = sampleExtrasHolder.offset;
        boolean subsampleEncryption = true;
        this.scratch.reset(1);
        readData(offset, this.scratch.data, 1);
        long offset2 = offset + 1;
        byte signalByte = this.scratch.data[0];
        if ((signalByte & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
            subsampleEncryption = false;
        }
        int ivSize = signalByte & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
        if (decoderInputBuffer.cryptoInfo.f75iv == null) {
            decoderInputBuffer.cryptoInfo.f75iv = new byte[16];
        }
        readData(offset2, decoderInputBuffer.cryptoInfo.f75iv, ivSize);
        long offset3 = offset2 + ((long) ivSize);
        if (subsampleEncryption) {
            this.scratch.reset(2);
            readData(offset3, this.scratch.data, 2);
            offset3 += 2;
            subsampleCount = this.scratch.readUnsignedShort();
        } else {
            subsampleCount = 1;
        }
        int[] clearDataSizes2 = decoderInputBuffer.cryptoInfo.numBytesOfClearData;
        if (clearDataSizes2 == null || clearDataSizes2.length < subsampleCount) {
            clearDataSizes = new int[subsampleCount];
        } else {
            clearDataSizes = clearDataSizes2;
        }
        int[] encryptedDataSizes2 = decoderInputBuffer.cryptoInfo.numBytesOfEncryptedData;
        if (encryptedDataSizes2 == null || encryptedDataSizes2.length < subsampleCount) {
            encryptedDataSizes = new int[subsampleCount];
        } else {
            encryptedDataSizes = encryptedDataSizes2;
        }
        if (subsampleEncryption) {
            int subsampleDataLength = subsampleCount * 6;
            this.scratch.reset(subsampleDataLength);
            readData(offset3, this.scratch.data, subsampleDataLength);
            offset3 += (long) subsampleDataLength;
            this.scratch.setPosition(0);
            for (int i = 0; i < subsampleCount; i++) {
                clearDataSizes[i] = this.scratch.readUnsignedShort();
                encryptedDataSizes[i] = this.scratch.readUnsignedIntToInt();
            }
        } else {
            clearDataSizes[0] = 0;
            encryptedDataSizes[0] = sampleExtrasHolder.size - ((int) (offset3 - sampleExtrasHolder.offset));
        }
        TrackOutput.CryptoData cryptoData = sampleExtrasHolder.cryptoData;
        CryptoInfo cryptoInfo = decoderInputBuffer.cryptoInfo;
        byte[] bArr = cryptoData.encryptionKey;
        byte[] bArr2 = decoderInputBuffer.cryptoInfo.f75iv;
        int i2 = cryptoData.cryptoMode;
        int i3 = i2;
        cryptoInfo.set(subsampleCount, clearDataSizes, encryptedDataSizes, bArr, bArr2, i3, cryptoData.encryptedBlocks, cryptoData.clearBlocks);
        int bytesRead = (int) (offset3 - sampleExtrasHolder.offset);
        sampleExtrasHolder.offset += (long) bytesRead;
        sampleExtrasHolder.size -= bytesRead;
    }

    private void readData(long absolutePosition, ByteBuffer target, int length) {
        advanceReadTo(absolutePosition);
        int remaining = length;
        while (remaining > 0) {
            int toCopy = Math.min(remaining, (int) (this.readAllocationNode.endPosition - absolutePosition));
            target.put(this.readAllocationNode.allocation.data, this.readAllocationNode.translateOffset(absolutePosition), toCopy);
            remaining -= toCopy;
            absolutePosition += (long) toCopy;
            if (absolutePosition == this.readAllocationNode.endPosition) {
                this.readAllocationNode = this.readAllocationNode.next;
            }
        }
    }

    private void readData(long absolutePosition, byte[] target, int length) {
        advanceReadTo(absolutePosition);
        int remaining = length;
        while (remaining > 0) {
            int toCopy = Math.min(remaining, (int) (this.readAllocationNode.endPosition - absolutePosition));
            System.arraycopy(this.readAllocationNode.allocation.data, this.readAllocationNode.translateOffset(absolutePosition), target, length - remaining, toCopy);
            remaining -= toCopy;
            absolutePosition += (long) toCopy;
            if (absolutePosition == this.readAllocationNode.endPosition) {
                this.readAllocationNode = this.readAllocationNode.next;
            }
        }
    }

    private void advanceReadTo(long absolutePosition) {
        while (absolutePosition >= this.readAllocationNode.endPosition) {
            this.readAllocationNode = this.readAllocationNode.next;
        }
    }

    private void discardDownstreamTo(long absolutePosition) {
        if (absolutePosition != -1) {
            while (absolutePosition >= this.firstAllocationNode.endPosition) {
                this.allocator.release(this.firstAllocationNode.allocation);
                this.firstAllocationNode = this.firstAllocationNode.clear();
            }
            if (this.readAllocationNode.startPosition < this.firstAllocationNode.startPosition) {
                this.readAllocationNode = this.firstAllocationNode;
            }
        }
    }

    public void setUpstreamFormatChangeListener(UpstreamFormatChangedListener listener) {
        this.upstreamFormatChangeListener = listener;
    }

    public void setSampleOffsetUs(long sampleOffsetUs2) {
        if (this.sampleOffsetUs != sampleOffsetUs2) {
            this.sampleOffsetUs = sampleOffsetUs2;
            this.pendingFormatAdjustment = true;
        }
    }

    public void format(Format format) {
        Format adjustedFormat = getAdjustedSampleFormat(format, this.sampleOffsetUs);
        boolean formatChanged = this.metadataQueue.format(adjustedFormat);
        this.lastUnadjustedFormat = format;
        this.pendingFormatAdjustment = false;
        UpstreamFormatChangedListener upstreamFormatChangedListener = this.upstreamFormatChangeListener;
        if (upstreamFormatChangedListener != null && formatChanged) {
            upstreamFormatChangedListener.onUpstreamFormatChanged(adjustedFormat);
        }
    }

    public int sampleData(ExtractorInput input, int length, boolean allowEndOfInput) throws IOException, InterruptedException {
        int bytesAppended = input.read(this.writeAllocationNode.allocation.data, this.writeAllocationNode.translateOffset(this.totalBytesWritten), preAppend(length));
        if (bytesAppended != -1) {
            postAppend(bytesAppended);
            return bytesAppended;
        } else if (allowEndOfInput) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    public void sampleData(ParsableByteArray buffer, int length) {
        while (length > 0) {
            int bytesAppended = preAppend(length);
            buffer.readBytes(this.writeAllocationNode.allocation.data, this.writeAllocationNode.translateOffset(this.totalBytesWritten), bytesAppended);
            length -= bytesAppended;
            postAppend(bytesAppended);
        }
    }

    public void sampleMetadata(long timeUs, int flags, int size, int offset, @Nullable TrackOutput.CryptoData cryptoData) {
        if (this.pendingFormatAdjustment) {
            format(this.lastUnadjustedFormat);
        }
        long timeUs2 = timeUs + this.sampleOffsetUs;
        if (this.pendingSplice) {
            if ((flags & 1) != 0 && this.metadataQueue.attemptSplice(timeUs2)) {
                this.pendingSplice = false;
            } else {
                return;
            }
        }
        this.metadataQueue.commitSample(timeUs2, flags, (this.totalBytesWritten - ((long) size)) - ((long) offset), size, cryptoData);
    }

    private void clearAllocationNodes(AllocationNode fromNode) {
        if (fromNode.wasInitialized) {
            Allocation[] allocationsToRelease = new Allocation[((this.writeAllocationNode.wasInitialized ? 1 : 0) + (((int) (this.writeAllocationNode.startPosition - fromNode.startPosition)) / this.allocationLength))];
            AllocationNode currentNode = fromNode;
            for (int i = 0; i < allocationsToRelease.length; i++) {
                allocationsToRelease[i] = currentNode.allocation;
                currentNode = currentNode.clear();
            }
            this.allocator.release(allocationsToRelease);
        }
    }

    private int preAppend(int length) {
        if (!this.writeAllocationNode.wasInitialized) {
            this.writeAllocationNode.initialize(this.allocator.allocate(), new AllocationNode(this.writeAllocationNode.endPosition, this.allocationLength));
        }
        return Math.min(length, (int) (this.writeAllocationNode.endPosition - this.totalBytesWritten));
    }

    private void postAppend(int length) {
        this.totalBytesWritten += (long) length;
        if (this.totalBytesWritten == this.writeAllocationNode.endPosition) {
            this.writeAllocationNode = this.writeAllocationNode.next;
        }
    }

    public interface UpstreamFormatChangedListener {
        void onUpstreamFormatChanged(Format format);
    }

    private static final class AllocationNode {
        public final long endPosition;
        public final long startPosition;
        @Nullable
        public Allocation allocation;
        @Nullable
        public AllocationNode next;
        public boolean wasInitialized;

        public AllocationNode(long startPosition2, int allocationLength) {
            this.startPosition = startPosition2;
            this.endPosition = ((long) allocationLength) + startPosition2;
        }

        public void initialize(Allocation allocation2, AllocationNode next2) {
            this.allocation = allocation2;
            this.next = next2;
            this.wasInitialized = true;
        }

        public int translateOffset(long absolutePosition) {
            return ((int) (absolutePosition - this.startPosition)) + this.allocation.offset;
        }

        public AllocationNode clear() {
            this.allocation = null;
            AllocationNode temp = this.next;
            this.next = null;
            return temp;
        }
    }
}
