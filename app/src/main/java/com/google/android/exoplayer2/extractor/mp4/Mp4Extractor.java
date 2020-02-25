package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public final class Mp4Extractor implements Extractor, SeekMap {
    public static final ExtractorsFactory FACTORY = Mp4Extractor$$Lambda$0.$instance;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 1;
    private static final int BRAND_QUICKTIME = Util.getIntegerCodeForString("qt  ");
    private static final long MAXIMUM_READ_AHEAD_BYTES_STREAM = 10485760;
    private static final long RELOAD_MINIMUM_SEEK_DISTANCE = 262144;
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_SAMPLE = 2;
    private final ParsableByteArray atomHeader;
    private final ArrayDeque<Atom.ContainerAtom> containerAtoms;
    private final int flags;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private final ParsableByteArray scratch;
    private long[][] accumulatedSampleSizes;
    private ParsableByteArray atomData;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private long durationUs;
    private ExtractorOutput extractorOutput;
    private int firstVideoTrackIndex;
    private boolean isAc4HeaderRequired;
    private boolean isQuickTime;
    private int parserState;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private int sampleTrackIndex;
    private Mp4Track[] tracks;

    public Mp4Extractor() {
        this(0);
    }

    public Mp4Extractor(int flags2) {
        this.flags = flags2;
        this.atomHeader = new ParsableByteArray(16);
        this.containerAtoms = new ArrayDeque<>();
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
        this.scratch = new ParsableByteArray();
        this.sampleTrackIndex = -1;
    }

    static final /* synthetic */ Extractor[] lambda$static$0$Mp4Extractor() {
        return new Extractor[]{new Mp4Extractor()};
    }

    /* JADX INFO: Multiple debug info for r10v2 int: [D('i' int), D('trackSampleIndex' int)] */
    private static long[][] calculateAccumulatedSampleSizes(Mp4Track[] tracks2) {
        long[][] accumulatedSampleSizes2 = new long[tracks2.length][];
        int[] nextSampleIndex = new int[tracks2.length];
        long[] nextSampleTimesUs = new long[tracks2.length];
        boolean[] tracksFinished = new boolean[tracks2.length];
        for (int i = 0; i < tracks2.length; i++) {
            accumulatedSampleSizes2[i] = new long[tracks2[i].sampleTable.sampleCount];
            nextSampleTimesUs[i] = tracks2[i].sampleTable.timestampsUs[0];
        }
        long accumulatedSampleSize = 0;
        int finishedTracks = 0;
        while (finishedTracks < tracks2.length) {
            long minTimeUs = Long.MAX_VALUE;
            int minTimeTrackIndex = -1;
            for (int i2 = 0; i2 < tracks2.length; i2++) {
                if (!tracksFinished[i2] && nextSampleTimesUs[i2] <= minTimeUs) {
                    minTimeTrackIndex = i2;
                    minTimeUs = nextSampleTimesUs[i2];
                }
            }
            int trackSampleIndex = nextSampleIndex[minTimeTrackIndex];
            accumulatedSampleSizes2[minTimeTrackIndex][trackSampleIndex] = accumulatedSampleSize;
            accumulatedSampleSize += (long) tracks2[minTimeTrackIndex].sampleTable.sizes[trackSampleIndex];
            int trackSampleIndex2 = trackSampleIndex + 1;
            nextSampleIndex[minTimeTrackIndex] = trackSampleIndex2;
            if (trackSampleIndex2 < accumulatedSampleSizes2[minTimeTrackIndex].length) {
                nextSampleTimesUs[minTimeTrackIndex] = tracks2[minTimeTrackIndex].sampleTable.timestampsUs[trackSampleIndex2];
            } else {
                tracksFinished[minTimeTrackIndex] = true;
                finishedTracks++;
            }
        }
        return accumulatedSampleSizes2;
    }

    private static long maybeAdjustSeekOffset(TrackSampleTable sampleTable, long seekTimeUs, long offset) {
        int sampleIndex = getSynchronizationSampleIndex(sampleTable, seekTimeUs);
        if (sampleIndex == -1) {
            return offset;
        }
        return Math.min(sampleTable.offsets[sampleIndex], offset);
    }

    private static int getSynchronizationSampleIndex(TrackSampleTable sampleTable, long timeUs) {
        int sampleIndex = sampleTable.getIndexOfEarlierOrEqualSynchronizationSample(timeUs);
        if (sampleIndex == -1) {
            return sampleTable.getIndexOfLaterOrEqualSynchronizationSample(timeUs);
        }
        return sampleIndex;
    }

    private static boolean processFtypAtom(ParsableByteArray atomData2) {
        atomData2.setPosition(8);
        if (atomData2.readInt() == BRAND_QUICKTIME) {
            return true;
        }
        atomData2.skipBytes(4);
        while (atomData2.bytesLeft() > 0) {
            if (atomData2.readInt() == BRAND_QUICKTIME) {
                return true;
            }
        }
        return false;
    }

    private static boolean shouldParseLeafAtom(int atom) {
        return atom == Atom.TYPE_mdhd || atom == Atom.TYPE_mvhd || atom == Atom.TYPE_hdlr || atom == Atom.TYPE_stsd || atom == Atom.TYPE_stts || atom == Atom.TYPE_stss || atom == Atom.TYPE_ctts || atom == Atom.TYPE_elst || atom == Atom.TYPE_stsc || atom == Atom.TYPE_stsz || atom == Atom.TYPE_stz2 || atom == Atom.TYPE_stco || atom == Atom.TYPE_co64 || atom == Atom.TYPE_tkhd || atom == Atom.TYPE_ftyp || atom == Atom.TYPE_udta || atom == Atom.TYPE_keys || atom == Atom.TYPE_ilst;
    }

    private static boolean shouldParseContainerAtom(int atom) {
        return atom == Atom.TYPE_moov || atom == Atom.TYPE_trak || atom == Atom.TYPE_mdia || atom == Atom.TYPE_minf || atom == Atom.TYPE_stbl || atom == Atom.TYPE_edts || atom == Atom.TYPE_meta;
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        return Sniffer.sniffUnfragmented(input);
    }

    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
    }

    public void seek(long position, long timeUs) {
        this.containerAtoms.clear();
        this.atomHeaderBytesRead = 0;
        this.sampleTrackIndex = -1;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        this.isAc4HeaderRequired = false;
        if (position == 0) {
            enterReadingAtomHeaderState();
        } else if (this.tracks != null) {
            updateSampleIndices(timeUs);
        }
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        while (true) {
            int i = this.parserState;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        return readSample(input, seekPosition);
                    }
                    throw new IllegalStateException();
                } else if (readAtomPayload(input, seekPosition)) {
                    return 1;
                }
            } else if (!readAtomHeader(input)) {
                return -1;
            }
        }
    }

    public boolean isSeekable() {
        return true;
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        long firstOffset;
        long firstTimeUs;
        int secondSampleIndex;
        long j = timeUs;
        Mp4Track[] mp4TrackArr = this.tracks;
        if (mp4TrackArr.length == 0) {
            return new SeekMap.SeekPoints(SeekPoint.START);
        }
        long secondTimeUs = C0841C.TIME_UNSET;
        long secondOffset = -1;
        int i = this.firstVideoTrackIndex;
        if (i != -1) {
            TrackSampleTable sampleTable = mp4TrackArr[i].sampleTable;
            int sampleIndex = getSynchronizationSampleIndex(sampleTable, j);
            if (sampleIndex == -1) {
                return new SeekMap.SeekPoints(SeekPoint.START);
            }
            long sampleTimeUs = sampleTable.timestampsUs[sampleIndex];
            firstTimeUs = sampleTimeUs;
            firstOffset = sampleTable.offsets[sampleIndex];
            if (sampleTimeUs < j && sampleIndex < sampleTable.sampleCount - 1 && (secondSampleIndex = sampleTable.getIndexOfLaterOrEqualSynchronizationSample(j)) != -1 && secondSampleIndex != sampleIndex) {
                secondTimeUs = sampleTable.timestampsUs[secondSampleIndex];
                secondOffset = sampleTable.offsets[secondSampleIndex];
            }
        } else {
            firstTimeUs = timeUs;
            firstOffset = Long.MAX_VALUE;
        }
        int i2 = 0;
        long secondOffset2 = secondOffset;
        long firstOffset2 = firstOffset;
        while (true) {
            Mp4Track[] mp4TrackArr2 = this.tracks;
            if (i2 >= mp4TrackArr2.length) {
                break;
            }
            if (i2 != this.firstVideoTrackIndex) {
                TrackSampleTable sampleTable2 = mp4TrackArr2[i2].sampleTable;
                firstOffset2 = maybeAdjustSeekOffset(sampleTable2, firstTimeUs, firstOffset2);
                if (secondTimeUs != C0841C.TIME_UNSET) {
                    secondOffset2 = maybeAdjustSeekOffset(sampleTable2, secondTimeUs, secondOffset2);
                }
            }
            i2++;
        }
        SeekPoint firstSeekPoint = new SeekPoint(firstTimeUs, firstOffset2);
        if (secondTimeUs == C0841C.TIME_UNSET) {
            return new SeekMap.SeekPoints(firstSeekPoint);
        }
        return new SeekMap.SeekPoints(firstSeekPoint, new SeekPoint(secondTimeUs, secondOffset2));
    }

    private void enterReadingAtomHeaderState() {
        this.parserState = 0;
        this.atomHeaderBytesRead = 0;
    }

    private boolean readAtomHeader(ExtractorInput input) throws IOException, InterruptedException {
        if (this.atomHeaderBytesRead == 0) {
            if (!input.readFully(this.atomHeader.data, 0, 8, true)) {
                return false;
            }
            this.atomHeaderBytesRead = 8;
            this.atomHeader.setPosition(0);
            this.atomSize = this.atomHeader.readUnsignedInt();
            this.atomType = this.atomHeader.readInt();
        }
        long j = this.atomSize;
        if (j == 1) {
            input.readFully(this.atomHeader.data, 8, 8);
            this.atomHeaderBytesRead += 8;
            this.atomSize = this.atomHeader.readUnsignedLongToLong();
        } else if (j == 0) {
            long endPosition = input.getLength();
            if (endPosition == -1 && !this.containerAtoms.isEmpty()) {
                endPosition = this.containerAtoms.peek().endPosition;
            }
            if (endPosition != -1) {
                this.atomSize = (endPosition - input.getPosition()) + ((long) this.atomHeaderBytesRead);
            }
        }
        if (this.atomSize >= ((long) this.atomHeaderBytesRead)) {
            if (shouldParseContainerAtom(this.atomType)) {
                long endPosition2 = (input.getPosition() + this.atomSize) - ((long) this.atomHeaderBytesRead);
                this.containerAtoms.push(new Atom.ContainerAtom(this.atomType, endPosition2));
                if (this.atomSize == ((long) this.atomHeaderBytesRead)) {
                    processAtomEnded(endPosition2);
                } else {
                    if (this.atomType == Atom.TYPE_meta) {
                        maybeSkipRemainingMetaAtomHeaderBytes(input);
                    }
                    enterReadingAtomHeaderState();
                }
            } else if (shouldParseLeafAtom(this.atomType)) {
                Assertions.checkState(this.atomHeaderBytesRead == 8);
                Assertions.checkState(this.atomSize <= 2147483647L);
                this.atomData = new ParsableByteArray((int) this.atomSize);
                System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
                this.parserState = 1;
            } else {
                this.atomData = null;
                this.parserState = 1;
            }
            return true;
        }
        throw new ParserException("Atom size less than header length (unsupported).");
    }

    private boolean readAtomPayload(ExtractorInput input, PositionHolder positionHolder) throws IOException, InterruptedException {
        long atomPayloadSize = this.atomSize - ((long) this.atomHeaderBytesRead);
        long atomEndPosition = input.getPosition() + atomPayloadSize;
        boolean seekRequired = false;
        ParsableByteArray parsableByteArray = this.atomData;
        if (parsableByteArray != null) {
            input.readFully(parsableByteArray.data, this.atomHeaderBytesRead, (int) atomPayloadSize);
            if (this.atomType == Atom.TYPE_ftyp) {
                this.isQuickTime = processFtypAtom(this.atomData);
            } else if (!this.containerAtoms.isEmpty()) {
                this.containerAtoms.peek().add(new Atom.LeafAtom(this.atomType, this.atomData));
            }
        } else if (atomPayloadSize < 262144) {
            input.skipFully((int) atomPayloadSize);
        } else {
            positionHolder.position = input.getPosition() + atomPayloadSize;
            seekRequired = true;
        }
        processAtomEnded(atomEndPosition);
        return seekRequired && this.parserState != 2;
    }

    private void processAtomEnded(long atomEndPosition) throws ParserException {
        while (!this.containerAtoms.isEmpty() && this.containerAtoms.peek().endPosition == atomEndPosition) {
            Atom.ContainerAtom containerAtom = this.containerAtoms.pop();
            if (containerAtom.type == Atom.TYPE_moov) {
                processMoovAtom(containerAtom);
                this.containerAtoms.clear();
                this.parserState = 2;
            } else if (!this.containerAtoms.isEmpty()) {
                this.containerAtoms.peek().add(containerAtom);
            }
        }
        if (this.parserState != 2) {
            enterReadingAtomHeaderState();
        }
    }

    private void processMoovAtom(Atom.ContainerAtom moov) throws ParserException {
        Atom.ContainerAtom containerAtom = moov;
        List<Mp4Track> tracks2 = new ArrayList<>();
        Metadata udtaMetadata = null;
        GaplessInfoHolder gaplessInfoHolder = new GaplessInfoHolder();
        Atom.LeafAtom udta = containerAtom.getLeafAtomOfType(Atom.TYPE_udta);
        if (!(udta == null || (udtaMetadata = AtomParsers.parseUdta(udta, this.isQuickTime)) == null)) {
            gaplessInfoHolder.setFromMetadata(udtaMetadata);
        }
        Metadata mdtaMetadata = null;
        Atom.ContainerAtom meta = containerAtom.getContainerAtomOfType(Atom.TYPE_meta);
        if (meta != null) {
            mdtaMetadata = AtomParsers.parseMdtaFromMeta(meta);
        }
        boolean ignoreEditLists = (this.flags & 1) != 0;
        ArrayList<TrackSampleTable> trackSampleTables = getTrackSampleTables(containerAtom, gaplessInfoHolder, ignoreEditLists);
        int trackCount = trackSampleTables.size();
        long durationUs2 = -9223372036854775807L;
        int firstVideoTrackIndex2 = -1;
        int i = 0;
        while (i < trackCount) {
            TrackSampleTable trackSampleTable = trackSampleTables.get(i);
            Track track = trackSampleTable.track;
            Atom.ContainerAtom meta2 = meta;
            boolean ignoreEditLists2 = ignoreEditLists;
            long trackDurationUs = track.durationUs != C0841C.TIME_UNSET ? track.durationUs : trackSampleTable.durationUs;
            Atom.LeafAtom udta2 = udta;
            long durationUs3 = Math.max(durationUs2, trackDurationUs);
            Mp4Track mp4Track = new Mp4Track(track, trackSampleTable, this.extractorOutput.track(i, track.type));
            Format format = track.format.copyWithMaxInputSize(trackSampleTable.maximumSize + 30);
            ArrayList<TrackSampleTable> trackSampleTables2 = trackSampleTables;
            if (track.type == 2 && trackDurationUs > 0 && trackSampleTable.sampleCount > 1) {
                format = format.copyWithFrameRate(((float) trackSampleTable.sampleCount) / (((float) trackDurationUs) / 1000000.0f));
            }
            mp4Track.trackOutput.format(MetadataUtil.getFormatWithMetadata(track.type, format, udtaMetadata, mdtaMetadata, gaplessInfoHolder));
            if (track.type == 2 && firstVideoTrackIndex2 == -1) {
                firstVideoTrackIndex2 = tracks2.size();
            }
            tracks2.add(mp4Track);
            i++;
            meta = meta2;
            ignoreEditLists = ignoreEditLists2;
            udta = udta2;
            durationUs2 = durationUs3;
            trackSampleTables = trackSampleTables2;
        }
        this.firstVideoTrackIndex = firstVideoTrackIndex2;
        this.durationUs = durationUs2;
        this.tracks = (Mp4Track[]) tracks2.toArray(new Mp4Track[0]);
        this.accumulatedSampleSizes = calculateAccumulatedSampleSizes(this.tracks);
        this.extractorOutput.endTracks();
        this.extractorOutput.seekMap(this);
    }

    private ArrayList<TrackSampleTable> getTrackSampleTables(Atom.ContainerAtom moov, GaplessInfoHolder gaplessInfoHolder, boolean ignoreEditLists) throws ParserException {
        Track track;
        ArrayList<TrackSampleTable> trackSampleTables = new ArrayList<>();
        for (int i = 0; i < moov.containerChildren.size(); i++) {
            Atom.ContainerAtom atom = moov.containerChildren.get(i);
            if (atom.type == Atom.TYPE_trak && (track = AtomParsers.parseTrak(atom, moov.getLeafAtomOfType(Atom.TYPE_mvhd), C0841C.TIME_UNSET, null, ignoreEditLists, this.isQuickTime)) != null) {
                TrackSampleTable trackSampleTable = AtomParsers.parseStbl(track, atom.getContainerAtomOfType(Atom.TYPE_mdia).getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl), gaplessInfoHolder);
                if (trackSampleTable.sampleCount != 0) {
                    trackSampleTables.add(trackSampleTable);
                }
            }
        }
        return trackSampleTables;
    }

    private int readSample(ExtractorInput input, PositionHolder positionHolder) throws IOException, InterruptedException {
        long position;
        long skipAmount;
        boolean z;
        int sampleSize;
        ExtractorInput extractorInput = input;
        long inputPosition = input.getPosition();
        if (this.sampleTrackIndex == -1) {
            this.sampleTrackIndex = getTrackIndexOfNextReadSample(inputPosition);
            int i = this.sampleTrackIndex;
            if (i == -1) {
                return -1;
            }
            this.isAc4HeaderRequired = MimeTypes.AUDIO_AC4.equals(this.tracks[i].track.format.sampleMimeType);
        }
        Mp4Track track = this.tracks[this.sampleTrackIndex];
        TrackOutput trackOutput = track.trackOutput;
        int sampleIndex = track.sampleIndex;
        long position2 = track.sampleTable.offsets[sampleIndex];
        int sampleSize2 = track.sampleTable.sizes[sampleIndex];
        long skipAmount2 = (position2 - inputPosition) + ((long) this.sampleBytesWritten);
        if (skipAmount2 < 0) {
            position = position2;
        } else if (skipAmount2 >= 262144) {
            position = position2;
        } else {
            if (track.track.sampleTransformation == 1) {
                sampleSize2 -= 8;
                skipAmount = skipAmount2 + 8;
            } else {
                skipAmount = skipAmount2;
            }
            extractorInput.skipFully((int) skipAmount);
            if (track.track.nalUnitLengthFieldLength != 0) {
                byte[] nalLengthData = this.nalLength.data;
                nalLengthData[0] = 0;
                nalLengthData[1] = 0;
                nalLengthData[2] = 0;
                int nalUnitLengthFieldLength = track.track.nalUnitLengthFieldLength;
                int nalUnitLengthFieldLengthDiff = 4 - track.track.nalUnitLengthFieldLength;
                while (this.sampleBytesWritten < sampleSize2) {
                    int i2 = this.sampleCurrentNalBytesRemaining;
                    if (i2 == 0) {
                        extractorInput.readFully(nalLengthData, nalUnitLengthFieldLengthDiff, nalUnitLengthFieldLength);
                        long inputPosition2 = inputPosition;
                        this.nalLength.setPosition(0);
                        int nalLengthInt = this.nalLength.readInt();
                        if (nalLengthInt >= 0) {
                            this.sampleCurrentNalBytesRemaining = nalLengthInt;
                            this.nalStartCode.setPosition(0);
                            trackOutput.sampleData(this.nalStartCode, 4);
                            this.sampleBytesWritten += 4;
                            sampleSize2 += nalUnitLengthFieldLengthDiff;
                            inputPosition = inputPosition2;
                        } else {
                            throw new ParserException("Invalid NAL length");
                        }
                    } else {
                        int writtenBytes = trackOutput.sampleData(extractorInput, i2, false);
                        this.sampleBytesWritten += writtenBytes;
                        this.sampleCurrentNalBytesRemaining -= writtenBytes;
                        inputPosition = inputPosition;
                    }
                }
                sampleSize = sampleSize2;
                z = false;
            } else {
                if (this.isAc4HeaderRequired) {
                    Ac4Util.getAc4SampleHeader(sampleSize2, this.scratch);
                    int length = this.scratch.limit();
                    trackOutput.sampleData(this.scratch, length);
                    sampleSize2 += length;
                    this.sampleBytesWritten += length;
                    z = false;
                    this.isAc4HeaderRequired = false;
                } else {
                    z = false;
                }
                while (true) {
                    int i3 = this.sampleBytesWritten;
                    if (i3 >= sampleSize2) {
                        break;
                    }
                    int writtenBytes2 = trackOutput.sampleData(extractorInput, sampleSize2 - i3, z);
                    this.sampleBytesWritten += writtenBytes2;
                    this.sampleCurrentNalBytesRemaining -= writtenBytes2;
                }
                sampleSize = sampleSize2;
            }
            trackOutput.sampleMetadata(track.sampleTable.timestampsUs[sampleIndex], track.sampleTable.flags[sampleIndex], sampleSize, 0, null);
            track.sampleIndex++;
            this.sampleTrackIndex = -1;
            this.sampleBytesWritten = z ? 1 : 0;
            this.sampleCurrentNalBytesRemaining = z;
            return z;
        }
        positionHolder.position = position;
        return 1;
    }

    private int getTrackIndexOfNextReadSample(long inputPosition) {
        long preferredSkipAmount = Long.MAX_VALUE;
        boolean preferredRequiresReload = true;
        int preferredTrackIndex = -1;
        long preferredAccumulatedBytes = Long.MAX_VALUE;
        long minAccumulatedBytes = Long.MAX_VALUE;
        boolean minAccumulatedBytesRequiresReload = true;
        int minAccumulatedBytesTrackIndex = -1;
        int trackIndex = 0;
        while (true) {
            Mp4Track[] mp4TrackArr = this.tracks;
            if (trackIndex >= mp4TrackArr.length) {
                break;
            }
            Mp4Track track = mp4TrackArr[trackIndex];
            int sampleIndex = track.sampleIndex;
            if (sampleIndex != track.sampleTable.sampleCount) {
                long sampleOffset = track.sampleTable.offsets[sampleIndex];
                long sampleAccumulatedBytes = this.accumulatedSampleSizes[trackIndex][sampleIndex];
                long skipAmount = sampleOffset - inputPosition;
                boolean requiresReload = skipAmount < 0 || skipAmount >= 262144;
                if ((!requiresReload && preferredRequiresReload) || (requiresReload == preferredRequiresReload && skipAmount < preferredSkipAmount)) {
                    preferredRequiresReload = requiresReload;
                    preferredSkipAmount = skipAmount;
                    preferredTrackIndex = trackIndex;
                    preferredAccumulatedBytes = sampleAccumulatedBytes;
                }
                if (sampleAccumulatedBytes < minAccumulatedBytes) {
                    minAccumulatedBytes = sampleAccumulatedBytes;
                    minAccumulatedBytesRequiresReload = requiresReload;
                    minAccumulatedBytesTrackIndex = trackIndex;
                }
            }
            trackIndex++;
        }
        if (minAccumulatedBytes == Long.MAX_VALUE || !minAccumulatedBytesRequiresReload || preferredAccumulatedBytes < MAXIMUM_READ_AHEAD_BYTES_STREAM + minAccumulatedBytes) {
            return preferredTrackIndex;
        }
        return minAccumulatedBytesTrackIndex;
    }

    private void updateSampleIndices(long timeUs) {
        for (Mp4Track track : this.tracks) {
            TrackSampleTable sampleTable = track.sampleTable;
            int sampleIndex = sampleTable.getIndexOfEarlierOrEqualSynchronizationSample(timeUs);
            if (sampleIndex == -1) {
                sampleIndex = sampleTable.getIndexOfLaterOrEqualSynchronizationSample(timeUs);
            }
            track.sampleIndex = sampleIndex;
        }
    }

    private void maybeSkipRemainingMetaAtomHeaderBytes(ExtractorInput input) throws IOException, InterruptedException {
        this.scratch.reset(8);
        input.peekFully(this.scratch.data, 0, 8);
        this.scratch.skipBytes(4);
        if (this.scratch.readInt() == Atom.TYPE_hdlr) {
            input.resetPeekPosition();
        } else {
            input.skipFully(4);
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface State {
    }

    private static final class Mp4Track {
        public final TrackSampleTable sampleTable;
        public final Track track;
        public final TrackOutput trackOutput;
        public int sampleIndex;

        public Mp4Track(Track track2, TrackSampleTable sampleTable2, TrackOutput trackOutput2) {
            this.track = track2;
            this.sampleTable = sampleTable2;
            this.trackOutput = trackOutput2;
        }
    }
}
