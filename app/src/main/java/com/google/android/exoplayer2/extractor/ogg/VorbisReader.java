package com.google.android.exoplayer2.extractor.ogg;

import android.support.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ogg.StreamReader;
import com.google.android.exoplayer2.extractor.ogg.VorbisUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.util.ArrayList;

final class VorbisReader extends StreamReader {
    private VorbisUtil.CommentHeader commentHeader;
    private int previousPacketBlockSize;
    private boolean seenFirstAudioPacket;
    private VorbisUtil.VorbisIdHeader vorbisIdHeader;
    private VorbisSetup vorbisSetup;

    VorbisReader() {
    }

    public static boolean verifyBitstreamType(ParsableByteArray data) {
        try {
            return VorbisUtil.verifyVorbisHeaderCapturePattern(1, data, true);
        } catch (ParserException e) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void reset(boolean headerData) {
        super.reset(headerData);
        if (headerData) {
            this.vorbisSetup = null;
            this.vorbisIdHeader = null;
            this.commentHeader = null;
        }
        this.previousPacketBlockSize = 0;
        this.seenFirstAudioPacket = false;
    }

    /* access modifiers changed from: protected */
    public void onSeekEnd(long currentGranule) {
        super.onSeekEnd(currentGranule);
        int i = 0;
        this.seenFirstAudioPacket = currentGranule != 0;
        VorbisUtil.VorbisIdHeader vorbisIdHeader2 = this.vorbisIdHeader;
        if (vorbisIdHeader2 != null) {
            i = vorbisIdHeader2.blockSize0;
        }
        this.previousPacketBlockSize = i;
    }

    /* access modifiers changed from: protected */
    public long preparePayload(ParsableByteArray packet) {
        int samplesInPacket = 0;
        if ((packet.data[0] & 1) == 1) {
            return -1;
        }
        int packetBlockSize = decodeBlockSize(packet.data[0], this.vorbisSetup);
        if (this.seenFirstAudioPacket) {
            samplesInPacket = (this.previousPacketBlockSize + packetBlockSize) / 4;
        }
        appendNumberOfSamples(packet, (long) samplesInPacket);
        this.seenFirstAudioPacket = true;
        this.previousPacketBlockSize = packetBlockSize;
        return (long) samplesInPacket;
    }

    /* access modifiers changed from: protected */
    public boolean readHeaders(ParsableByteArray packet, long position, StreamReader.SetupData setupData) throws IOException, InterruptedException {
        if (this.vorbisSetup != null) {
            return false;
        }
        this.vorbisSetup = readSetupHeaders(packet);
        if (this.vorbisSetup == null) {
            return true;
        }
        ArrayList<byte[]> codecInitialisationData = new ArrayList<>();
        codecInitialisationData.add(this.vorbisSetup.idHeader.data);
        codecInitialisationData.add(this.vorbisSetup.setupHeaderData);
        setupData.format = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_VORBIS, null, this.vorbisSetup.idHeader.bitrateNominal, -1, this.vorbisSetup.idHeader.channels, (int) this.vorbisSetup.idHeader.sampleRate, codecInitialisationData, null, 0, null);
        return true;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public VorbisSetup readSetupHeaders(ParsableByteArray scratch) throws IOException {
        if (this.vorbisIdHeader == null) {
            this.vorbisIdHeader = VorbisUtil.readVorbisIdentificationHeader(scratch);
            return null;
        } else if (this.commentHeader == null) {
            this.commentHeader = VorbisUtil.readVorbisCommentHeader(scratch);
            return null;
        } else {
            byte[] setupHeaderData = new byte[scratch.limit()];
            System.arraycopy(scratch.data, 0, setupHeaderData, 0, scratch.limit());
            VorbisUtil.Mode[] modes = VorbisUtil.readVorbisModes(scratch, this.vorbisIdHeader.channels);
            return new VorbisSetup(this.vorbisIdHeader, this.commentHeader, setupHeaderData, modes, VorbisUtil.iLog(modes.length - 1));
        }
    }

    @VisibleForTesting
    static int readBits(byte src, int length, int leastSignificantBitIndex) {
        return (src >> leastSignificantBitIndex) & (255 >>> (8 - length));
    }

    @VisibleForTesting
    static void appendNumberOfSamples(ParsableByteArray buffer, long packetSampleCount) {
        buffer.setLimit(buffer.limit() + 4);
        buffer.data[buffer.limit() - 4] = (byte) ((int) (packetSampleCount & 255));
        buffer.data[buffer.limit() - 3] = (byte) ((int) ((packetSampleCount >>> 8) & 255));
        buffer.data[buffer.limit() - 2] = (byte) ((int) ((packetSampleCount >>> 16) & 255));
        buffer.data[buffer.limit() - 1] = (byte) ((int) (255 & (packetSampleCount >>> 24)));
    }

    private static int decodeBlockSize(byte firstByteOfAudioPacket, VorbisSetup vorbisSetup2) {
        if (!vorbisSetup2.modes[readBits(firstByteOfAudioPacket, vorbisSetup2.iLogModes, 1)].blockFlag) {
            return vorbisSetup2.idHeader.blockSize0;
        }
        return vorbisSetup2.idHeader.blockSize1;
    }

    static final class VorbisSetup {
        public final VorbisUtil.CommentHeader commentHeader;
        public final int iLogModes;
        public final VorbisUtil.VorbisIdHeader idHeader;
        public final VorbisUtil.Mode[] modes;
        public final byte[] setupHeaderData;

        public VorbisSetup(VorbisUtil.VorbisIdHeader idHeader2, VorbisUtil.CommentHeader commentHeader2, byte[] setupHeaderData2, VorbisUtil.Mode[] modes2, int iLogModes2) {
            this.idHeader = idHeader2;
            this.commentHeader = commentHeader2;
            this.setupHeaderData = setupHeaderData2;
            this.modes = modes2;
            this.iLogModes = iLogModes2;
        }
    }
}
