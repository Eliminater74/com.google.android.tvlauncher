package com.google.android.exoplayer2.extractor.mp4;

import android.util.LongSparseArray;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mp4.Atom;
import com.google.android.exoplayer2.extractor.mp4.HeifMetaItem;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.HevcConfig;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class HeifExtractor implements Extractor, SeekMap {
    private static final int BRAND_QUICKTIME = Util.getIntegerCodeForString("qt  ");
    public static final ExtractorsFactory FACTORY = HeifExtractor$$Lambda$0.$instance;
    private static final long RELOAD_MINIMUM_SEEK_DISTANCE = 262144;
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_SAMPLE = 2;
    private static final String TAG = "HeifExtractor";
    private static final int TYPE_Exif = Util.getIntegerCodeForString("Exif");
    private static final int TYPE_irot = Util.getIntegerCodeForString("irot");
    private static final int TYPE_ispe = Util.getIntegerCodeForString("ispe");
    private ParsableByteArray atomData;
    private final ParsableByteArray atomHeader = new ParsableByteArray(16);
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private final ArrayDeque<Atom.ContainerAtom> containerAtoms = new ArrayDeque<>();
    private long durationUs;
    private ExtractorOutput extractorOutput;
    private int firstVideoTrackIndex;
    private boolean isQuickTime;
    private LongSparseArray<HeifMetaItem> items;
    private final ParsableByteArray nalLength = new ParsableByteArray(4);
    private final ParsableByteArray nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
    private int parserState;
    private long primaryItemId;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private Mp4Track[] tracks;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface State {
    }

    static final /* synthetic */ Extractor[] lambda$static$0$HeifExtractor() {
        return new Extractor[]{new HeifExtractor()};
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
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
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

    public LongSparseArray<HeifMetaItem> getMetaItems() {
        return this.items;
    }

    public long getPrimaryItemId() {
        return this.primaryItemId;
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
            if (isFullAtom(this.atomType)) {
                input.readFully(this.atomHeader.data, 8, 4);
                this.atomHeaderBytesRead += 4;
            }
            if (shouldParseContainerAtom(this.atomType)) {
                long endPosition2 = (input.getPosition() + this.atomSize) - ((long) this.atomHeaderBytesRead);
                this.containerAtoms.push(new Atom.ContainerAtom(this.atomType, endPosition2));
                if (this.atomSize == ((long) this.atomHeaderBytesRead)) {
                    processAtomEnded(endPosition2);
                } else {
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
            } else if (containerAtom.type == Atom.TYPE_meta) {
                processMetaAtom(containerAtom);
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
        int firstVideoTrackIndex2 = -1;
        long durationUs2 = C0841C.TIME_UNSET;
        List<Mp4Track> tracks2 = new ArrayList<>();
        Metadata metadata = null;
        GaplessInfoHolder gaplessInfoHolder = new GaplessInfoHolder();
        Atom.LeafAtom udta = containerAtom.getLeafAtomOfType(Atom.TYPE_udta);
        if (!(udta == null || (metadata = AtomParsers.parseUdta(udta, this.isQuickTime)) == null)) {
            gaplessInfoHolder.setFromMetadata(metadata);
        }
        Metadata mdtaMetadata = null;
        Atom.ContainerAtom meta = containerAtom.getContainerAtomOfType(Atom.TYPE_meta);
        if (meta != null) {
            mdtaMetadata = AtomParsers.parseMdtaFromMeta(meta);
        }
        ArrayList<TrackSampleTable> trackSampleTables = getTrackSampleTables(containerAtom, gaplessInfoHolder, false);
        int trackCount = trackSampleTables.size();
        int i = 0;
        while (i < trackCount) {
            TrackSampleTable trackSampleTable = trackSampleTables.get(i);
            Track track = trackSampleTable.track;
            Atom.LeafAtom udta2 = udta;
            Atom.ContainerAtom meta2 = meta;
            Mp4Track mp4Track = new Mp4Track(track, trackSampleTable, this.extractorOutput.track(i, track.type));
            mp4Track.trackOutput.format(MetadataUtil.getFormatWithMetadata(track.type, track.format.copyWithMaxInputSize(trackSampleTable.maximumSize + 30), metadata, mdtaMetadata, gaplessInfoHolder));
            Metadata metadata2 = metadata;
            GaplessInfoHolder gaplessInfoHolder2 = gaplessInfoHolder;
            durationUs2 = Math.max(durationUs2, track.durationUs != C0841C.TIME_UNSET ? track.durationUs : trackSampleTable.durationUs);
            if (track.type == 2 && firstVideoTrackIndex2 == -1) {
                firstVideoTrackIndex2 = tracks2.size();
            }
            tracks2.add(mp4Track);
            i++;
            gaplessInfoHolder = gaplessInfoHolder2;
            udta = udta2;
            meta = meta2;
            metadata = metadata2;
        }
        this.firstVideoTrackIndex = firstVideoTrackIndex2;
        this.durationUs = durationUs2;
        this.tracks = (Mp4Track[]) tracks2.toArray(new Mp4Track[0]);
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

    /* JADX INFO: Multiple debug info for r12v3 com.google.android.exoplayer2.extractor.mp4.Track: [D('item' com.google.android.exoplayer2.extractor.mp4.HeifMetaItem), D('track' com.google.android.exoplayer2.extractor.mp4.Track)] */
    private void processMetaAtom(Atom.ContainerAtom meta) throws ParserException {
        long primaryItemId2;
        HeifMetaItem.HeifMetaItemsBuilder builder;
        Atom.ContainerAtom containerAtom = meta;
        List<Mp4Track> tracks2 = new ArrayList<>();
        HeifMetaItem.HeifMetaItemsBuilder builder2 = new HeifMetaItem.HeifMetaItemsBuilder();
        long primaryItemId3 = -1;
        for (int i = 0; i < containerAtom.leafChildren.size(); i++) {
            Atom.LeafAtom atom = containerAtom.leafChildren.get(i);
            if (atom.type == HeifAtomParsers.TYPE_iloc) {
                HeifAtomParsers.parseIloc(atom, builder2);
            } else if (atom.type == HeifAtomParsers.TYPE_iref) {
                HeifAtomParsers.parseIref(atom, builder2);
            } else if (atom.type == HeifAtomParsers.TYPE_iinf) {
                HeifAtomParsers.parseIinf(atom, builder2);
            } else if (atom.type == HeifAtomParsers.TYPE_iprp) {
                HeifAtomParsers.parseIprp(atom, builder2);
            } else if (atom.type == HeifAtomParsers.TYPE_idat) {
                HeifAtomParsers.parseIdat(atom, builder2);
            } else if (atom.type == HeifAtomParsers.TYPE_pitm) {
                primaryItemId3 = HeifAtomParsers.parsePitm(atom);
            }
        }
        this.items = builder2.build();
        this.primaryItemId = primaryItemId3;
        int i2 = 0;
        while (i2 < this.items.size()) {
            int trackId = (int) this.items.keyAt(i2);
            HeifMetaItem item = this.items.valueAt(i2);
            Track track = getMetaItemTrack(trackId, item);
            if (track == null) {
                Log.m30w(TAG, "Couldn't get track for metadata item");
                builder = builder2;
                primaryItemId2 = primaryItemId3;
            } else {
                int sampleCount = item.offsets.length;
                int[] flags = new int[sampleCount];
                Arrays.fill(flags, 1);
                builder = builder2;
                int maxSize = 0;
                int j = 0;
                while (j < sampleCount) {
                    long primaryItemId4 = primaryItemId3;
                    int size = item.sizes[j];
                    if (size > maxSize) {
                        maxSize = size;
                    }
                    j++;
                    primaryItemId3 = primaryItemId4;
                }
                primaryItemId2 = primaryItemId3;
                long[] jArr = item.offsets;
                int[] iArr = item.sizes;
                Track track2 = track;
                Mp4Track mp4Track = new Mp4Track(track2, new TrackSampleTable(track, jArr, iArr, maxSize, new long[item.offsets.length], flags, 0), this.extractorOutput.track(trackId, track2.type));
                mp4Track.trackOutput.format(track2.format);
                tracks2.add(mp4Track);
            }
            i2++;
            builder2 = builder;
            primaryItemId3 = primaryItemId2;
        }
        this.durationUs = C0841C.TIME_UNSET;
        this.tracks = (Mp4Track[]) tracks2.toArray(new Mp4Track[0]);
        this.extractorOutput.endTracks();
        this.extractorOutput.seekMap(this);
    }

    private int readSample(ExtractorInput input, PositionHolder positionHolder) throws IOException, InterruptedException {
        long position;
        int trackIndex;
        ExtractorInput extractorInput = input;
        int trackIndex2 = getTrackIndexOfEarliestCurrentSample();
        if (trackIndex2 == -1) {
            return -1;
        }
        Mp4Track track = this.tracks[trackIndex2];
        TrackOutput trackOutput = track.trackOutput;
        int sampleIndex = track.sampleIndex;
        long position2 = track.sampleTable.offsets[sampleIndex];
        int sampleSize = track.sampleTable.sizes[sampleIndex];
        if (track.track.sampleTransformation == 1) {
            sampleSize -= 8;
            position = position2 + 8;
        } else {
            position = position2;
        }
        long skipAmount = (position - input.getPosition()) + ((long) this.sampleBytesWritten);
        if (skipAmount >= 0) {
            if (skipAmount < 262144) {
                extractorInput.skipFully((int) skipAmount);
                int i = 0;
                if (track.track.nalUnitLengthFieldLength != 0) {
                    byte[] nalLengthData = this.nalLength.data;
                    nalLengthData[0] = 0;
                    nalLengthData[1] = 0;
                    nalLengthData[2] = 0;
                    int nalUnitLengthFieldLength = track.track.nalUnitLengthFieldLength;
                    int nalUnitLengthFieldLengthDiff = 4 - track.track.nalUnitLengthFieldLength;
                    while (this.sampleBytesWritten < sampleSize) {
                        int i2 = this.sampleCurrentNalBytesRemaining;
                        if (i2 == 0) {
                            extractorInput.readFully(nalLengthData, nalUnitLengthFieldLengthDiff, nalUnitLengthFieldLength);
                            this.nalLength.setPosition(i);
                            int nalLengthInt = this.nalLength.readInt();
                            if (nalLengthInt >= 0) {
                                this.sampleCurrentNalBytesRemaining = nalLengthInt;
                                this.nalStartCode.setPosition(i);
                                trackOutput.sampleData(this.nalStartCode, 4);
                                this.sampleBytesWritten += 4;
                                sampleSize += nalUnitLengthFieldLengthDiff;
                                trackIndex2 = trackIndex2;
                                i = 0;
                            } else {
                                throw new ParserException("Invalid NAL length");
                            }
                        } else {
                            int writtenBytes = trackOutput.sampleData(extractorInput, i2, false);
                            this.sampleBytesWritten += writtenBytes;
                            this.sampleCurrentNalBytesRemaining -= writtenBytes;
                            trackIndex2 = trackIndex2;
                            i = 0;
                        }
                    }
                    trackIndex = sampleSize;
                } else {
                    while (true) {
                        int trackIndex3 = this.sampleBytesWritten;
                        if (trackIndex3 >= sampleSize) {
                            break;
                        }
                        int writtenBytes2 = trackOutput.sampleData(extractorInput, sampleSize - trackIndex3, false);
                        this.sampleBytesWritten += writtenBytes2;
                        this.sampleCurrentNalBytesRemaining -= writtenBytes2;
                    }
                    trackIndex = sampleSize;
                }
                trackOutput.sampleMetadata(track.sampleTable.timestampsUs[sampleIndex], track.sampleTable.flags[sampleIndex], trackIndex, 0, null);
                track.sampleIndex++;
                this.sampleBytesWritten = 0;
                this.sampleCurrentNalBytesRemaining = 0;
                return 0;
            }
        }
        positionHolder.position = position;
        return 1;
    }

    private int getTrackIndexOfEarliestCurrentSample() {
        int earliestSampleTrackIndex = -1;
        long earliestSampleOffset = Long.MAX_VALUE;
        int trackIndex = 0;
        while (true) {
            Mp4Track[] mp4TrackArr = this.tracks;
            if (trackIndex >= mp4TrackArr.length) {
                return earliestSampleTrackIndex;
            }
            Mp4Track track = mp4TrackArr[trackIndex];
            int sampleIndex = track.sampleIndex;
            if (sampleIndex != track.sampleTable.sampleCount) {
                long trackSampleOffset = track.sampleTable.offsets[sampleIndex];
                if (trackSampleOffset < earliestSampleOffset) {
                    earliestSampleOffset = trackSampleOffset;
                    earliestSampleTrackIndex = trackIndex;
                }
            }
            trackIndex++;
        }
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

    private static Track getMetaItemTrack(int trackId, HeifMetaItem item) throws ParserException {
        int trackType;
        Format format;
        HeifMetaItem heifMetaItem = item;
        byte[] initializationData = null;
        float pixelAspectRatio = -1.0f;
        int rotationDegrees = -1;
        int height = -1;
        int width = -1;
        for (HeifMetaItem.Property property : heifMetaItem.properties) {
            if (property.type == TYPE_ispe) {
                ParsableByteArray data = new ParsableByteArray(property.data);
                data.setPosition(4);
                width = data.readUnsignedIntToInt();
                height = data.readUnsignedIntToInt();
            } else if (property.type == Atom.TYPE_hvcC) {
                initializationData = property.data;
            } else if (property.type == TYPE_irot) {
                rotationDegrees = (property.data[0] & 3) * 90;
            } else if (property.type == Atom.TYPE_pasp) {
                ParsableByteArray data2 = new ParsableByteArray(property.data);
                pixelAspectRatio = ((float) data2.readUnsignedIntToInt()) / ((float) data2.readUnsignedIntToInt());
            }
        }
        int nalUnitLengthFieldLength = 0;
        if (heifMetaItem.type == Atom.TYPE_hvc1) {
            HevcConfig hevcConfig = HevcConfig.parse(new ParsableByteArray(initializationData));
            format = Format.createVideoSampleFormat(null, MimeTypes.VIDEO_H265, null, -1, -1, width, height, -1.0f, hevcConfig.initializationData, rotationDegrees, pixelAspectRatio, null);
            trackType = 2;
            nalUnitLengthFieldLength = hevcConfig.nalUnitLengthFieldLength;
        } else if (heifMetaItem.type != TYPE_Exif) {
            return null;
        } else {
            format = Format.createSampleFormat(null, MimeTypes.APPLICATION_EXIF, null, -1, null);
            trackType = 4;
        }
        return new Track(trackId, trackType, 0, 0, 0, format, 0, null, nalUnitLengthFieldLength, new long[0], new long[0]);
    }

    private static boolean shouldParseLeafAtom(int atom) {
        return atom == Atom.TYPE_mdhd || atom == Atom.TYPE_mvhd || atom == Atom.TYPE_hdlr || atom == Atom.TYPE_stsd || atom == Atom.TYPE_stts || atom == Atom.TYPE_stss || atom == Atom.TYPE_ctts || atom == Atom.TYPE_elst || atom == Atom.TYPE_stsc || atom == Atom.TYPE_stsz || atom == Atom.TYPE_stz2 || atom == Atom.TYPE_stco || atom == Atom.TYPE_co64 || atom == Atom.TYPE_tkhd || atom == Atom.TYPE_ftyp || atom == Atom.TYPE_udta || atom == Atom.TYPE_meta || atom == HeifAtomParsers.TYPE_pitm || atom == HeifAtomParsers.TYPE_iinf || atom == HeifAtomParsers.TYPE_iref || atom == HeifAtomParsers.TYPE_iprp || atom == HeifAtomParsers.TYPE_idat || atom == HeifAtomParsers.TYPE_iloc;
    }

    private static boolean shouldParseContainerAtom(int atom) {
        return atom == Atom.TYPE_moov || atom == Atom.TYPE_trak || atom == Atom.TYPE_mdia || atom == Atom.TYPE_minf || atom == Atom.TYPE_stbl || atom == Atom.TYPE_edts || atom == Atom.TYPE_meta;
    }

    private static boolean isFullAtom(int atom) {
        return atom == Atom.TYPE_meta;
    }

    private static final class Mp4Track {
        public int sampleIndex;
        public final TrackSampleTable sampleTable;
        public final Track track;
        public final TrackOutput trackOutput;

        public Mp4Track(Track track2, TrackSampleTable sampleTable2, TrackOutput trackOutput2) {
            this.track = track2;
            this.sampleTable = sampleTable2;
            this.trackOutput = trackOutput2;
        }
    }
}
