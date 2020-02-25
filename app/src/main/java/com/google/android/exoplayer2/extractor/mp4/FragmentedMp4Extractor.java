package com.google.android.exoplayer2.extractor.mp4;

import android.support.annotation.Nullable;
import android.util.Pair;
import android.util.SparseArray;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.text.cea.CeaUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FragmentedMp4Extractor implements Extractor {
    public static final ExtractorsFactory FACTORY = FragmentedMp4Extractor$$Lambda$0.$instance;
    public static final int FLAG_ENABLE_EMSG_TRACK = 4;
    public static final int FLAG_WORKAROUND_EVERY_VIDEO_FRAME_IS_SYNC_FRAME = 1;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 16;
    public static final int FLAG_WORKAROUND_IGNORE_TFDT_BOX = 2;
    private static final Format EMSG_FORMAT = Format.createSampleFormat(null, MimeTypes.APPLICATION_EMSG, Long.MAX_VALUE);
    private static final int FLAG_SIDELOADED = 8;
    private static final byte[] PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE = {-94, 57, 79, 82, 90, -101, 79, Ascii.DC4, -94, 68, 108, 66, 124, 100, -115, -12};
    private static final int SAMPLE_GROUP_TYPE_seig = Util.getIntegerCodeForString("seig");
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_ENCRYPTION_DATA = 2;
    private static final int STATE_READING_SAMPLE_CONTINUE = 4;
    private static final int STATE_READING_SAMPLE_START = 3;
    private static final String TAG = "FragmentedMp4Extractor";
    @Nullable
    private final TrackOutput additionalEmsgTrackOutput;
    private final ParsableByteArray atomHeader;
    private final List<Format> closedCaptionFormats;
    private final ArrayDeque<Atom.ContainerAtom> containerAtoms;
    private final int flags;
    private final ParsableByteArray nalBuffer;
    private final ParsableByteArray nalPrefix;
    private final ParsableByteArray nalStartCode;
    private final ArrayDeque<MetadataSampleInfo> pendingMetadataSampleInfos;
    private final ParsableByteArray scratch;
    private final byte[] scratchBytes;
    @Nullable
    private final DrmInitData sideloadedDrmInitData;
    @Nullable
    private final Track sideloadedTrack;
    @Nullable
    private final TimestampAdjuster timestampAdjuster;
    private final SparseArray<TrackBundle> trackBundles;
    private ParsableByteArray atomData;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private TrackOutput[] cea608TrackOutputs;
    private TrackBundle currentTrackBundle;
    private long durationUs;
    private TrackOutput[] emsgTrackOutputs;
    private long endOfMdatPosition;
    private ExtractorOutput extractorOutput;
    private boolean haveOutputSeekMap;
    private boolean isAc4HeaderRequired;
    private int parserState;
    private int pendingMetadataSampleBytes;
    private long pendingSeekTimeUs;
    private boolean processSeiNalUnitPayload;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private int sampleSize;
    private long segmentIndexEarliestPresentationTimeUs;

    public FragmentedMp4Extractor() {
        this(0);
    }

    public FragmentedMp4Extractor(int flags2) {
        this(flags2, null);
    }

    public FragmentedMp4Extractor(int flags2, @Nullable TimestampAdjuster timestampAdjuster2) {
        this(flags2, timestampAdjuster2, null, null);
    }

    public FragmentedMp4Extractor(int flags2, @Nullable TimestampAdjuster timestampAdjuster2, @Nullable Track sideloadedTrack2, @Nullable DrmInitData sideloadedDrmInitData2) {
        this(flags2, timestampAdjuster2, sideloadedTrack2, sideloadedDrmInitData2, Collections.emptyList());
    }

    public FragmentedMp4Extractor(int flags2, @Nullable TimestampAdjuster timestampAdjuster2, @Nullable Track sideloadedTrack2, @Nullable DrmInitData sideloadedDrmInitData2, List<Format> closedCaptionFormats2) {
        this(flags2, timestampAdjuster2, sideloadedTrack2, sideloadedDrmInitData2, closedCaptionFormats2, null);
    }

    public FragmentedMp4Extractor(int flags2, @Nullable TimestampAdjuster timestampAdjuster2, @Nullable Track sideloadedTrack2, @Nullable DrmInitData sideloadedDrmInitData2, List<Format> closedCaptionFormats2, @Nullable TrackOutput additionalEmsgTrackOutput2) {
        this.flags = (sideloadedTrack2 != null ? 8 : 0) | flags2;
        this.timestampAdjuster = timestampAdjuster2;
        this.sideloadedTrack = sideloadedTrack2;
        this.sideloadedDrmInitData = sideloadedDrmInitData2;
        this.closedCaptionFormats = Collections.unmodifiableList(closedCaptionFormats2);
        this.additionalEmsgTrackOutput = additionalEmsgTrackOutput2;
        this.atomHeader = new ParsableByteArray(16);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalPrefix = new ParsableByteArray(5);
        this.nalBuffer = new ParsableByteArray();
        this.scratchBytes = new byte[16];
        this.scratch = new ParsableByteArray(this.scratchBytes);
        this.containerAtoms = new ArrayDeque<>();
        this.pendingMetadataSampleInfos = new ArrayDeque<>();
        this.trackBundles = new SparseArray<>();
        this.durationUs = C0841C.TIME_UNSET;
        this.pendingSeekTimeUs = C0841C.TIME_UNSET;
        this.segmentIndexEarliestPresentationTimeUs = C0841C.TIME_UNSET;
        enterReadingAtomHeaderState();
    }

    static final /* synthetic */ Extractor[] lambda$static$0$FragmentedMp4Extractor() {
        return new Extractor[]{new FragmentedMp4Extractor()};
    }

    private static Pair<Integer, DefaultSampleValues> parseTrex(ParsableByteArray trex) {
        trex.setPosition(12);
        return Pair.create(Integer.valueOf(trex.readInt()), new DefaultSampleValues(trex.readUnsignedIntToInt() - 1, trex.readUnsignedIntToInt(), trex.readUnsignedIntToInt(), trex.readInt()));
    }

    private static long parseMehd(ParsableByteArray mehd) {
        mehd.setPosition(8);
        return Atom.parseFullAtomVersion(mehd.readInt()) == 0 ? mehd.readUnsignedInt() : mehd.readUnsignedLongToLong();
    }

    private static void parseMoof(Atom.ContainerAtom moof, SparseArray<TrackBundle> trackBundleArray, int flags2, byte[] extendedTypeScratch) throws ParserException {
        int moofContainerChildrenSize = moof.containerChildren.size();
        for (int i = 0; i < moofContainerChildrenSize; i++) {
            Atom.ContainerAtom child = moof.containerChildren.get(i);
            if (child.type == Atom.TYPE_traf) {
                parseTraf(child, trackBundleArray, flags2, extendedTypeScratch);
            }
        }
    }

    private static void parseTraf(Atom.ContainerAtom traf, SparseArray<TrackBundle> trackBundleArray, int flags2, byte[] extendedTypeScratch) throws ParserException {
        Atom.ContainerAtom containerAtom = traf;
        int i = flags2;
        TrackBundle trackBundle = parseTfhd(containerAtom.getLeafAtomOfType(Atom.TYPE_tfhd).data, trackBundleArray);
        if (trackBundle != null) {
            TrackFragment fragment = trackBundle.fragment;
            long decodeTime = fragment.nextFragmentDecodeTime;
            trackBundle.reset();
            if (containerAtom.getLeafAtomOfType(Atom.TYPE_tfdt) != null && (i & 2) == 0) {
                decodeTime = parseTfdt(containerAtom.getLeafAtomOfType(Atom.TYPE_tfdt).data);
            }
            parseTruns(containerAtom, trackBundle, decodeTime, i);
            TrackEncryptionBox encryptionBox = trackBundle.track.getSampleDescriptionEncryptionBox(fragment.header.sampleDescriptionIndex);
            Atom.LeafAtom saiz = containerAtom.getLeafAtomOfType(Atom.TYPE_saiz);
            if (saiz != null) {
                parseSaiz(encryptionBox, saiz.data, fragment);
            }
            Atom.LeafAtom saio = containerAtom.getLeafAtomOfType(Atom.TYPE_saio);
            if (saio != null) {
                parseSaio(saio.data, fragment);
            }
            Atom.LeafAtom senc = containerAtom.getLeafAtomOfType(Atom.TYPE_senc);
            if (senc != null) {
                parseSenc(senc.data, fragment);
            }
            Atom.LeafAtom sbgp = containerAtom.getLeafAtomOfType(Atom.TYPE_sbgp);
            Atom.LeafAtom sgpd = containerAtom.getLeafAtomOfType(Atom.TYPE_sgpd);
            if (sbgp != null && sgpd != null) {
                parseSgpd(sbgp.data, sgpd.data, encryptionBox != null ? encryptionBox.schemeType : null, fragment);
            }
            int leafChildrenSize = containerAtom.leafChildren.size();
            int i2 = 0;
            while (i2 < leafChildrenSize) {
                Atom.LeafAtom atom = containerAtom.leafChildren.get(i2);
                int leafChildrenSize2 = leafChildrenSize;
                if (atom.type == Atom.TYPE_uuid) {
                    parseUuid(atom.data, fragment, extendedTypeScratch);
                }
                i2++;
                containerAtom = traf;
                leafChildrenSize = leafChildrenSize2;
            }
        }
    }

    private static void parseTruns(Atom.ContainerAtom traf, TrackBundle trackBundle, long decodeTime, int flags2) {
        TrackBundle trackBundle2 = trackBundle;
        List<Atom.LeafAtom> leafChildren = traf.leafChildren;
        int leafChildrenSize = leafChildren.size();
        int trunCount = 0;
        int totalSampleCount = 0;
        for (int i = 0; i < leafChildrenSize; i++) {
            Atom.LeafAtom atom = leafChildren.get(i);
            if (atom.type == Atom.TYPE_trun) {
                ParsableByteArray trunData = atom.data;
                trunData.setPosition(12);
                int trunSampleCount = trunData.readUnsignedIntToInt();
                if (trunSampleCount > 0) {
                    totalSampleCount += trunSampleCount;
                    trunCount++;
                }
            }
        }
        trackBundle2.currentTrackRunIndex = 0;
        trackBundle2.currentSampleInTrackRun = 0;
        trackBundle2.currentSampleIndex = 0;
        trackBundle2.fragment.initTables(trunCount, totalSampleCount);
        int trunStartPosition = 0;
        int trunIndex = 0;
        for (int i2 = 0; i2 < leafChildrenSize; i2++) {
            Atom.LeafAtom trun = leafChildren.get(i2);
            if (trun.type == Atom.TYPE_trun) {
                trunStartPosition = parseTrun(trackBundle, trunIndex, decodeTime, flags2, trun.data, trunStartPosition);
                trunIndex++;
            }
        }
    }

    private static void parseSaiz(TrackEncryptionBox encryptionBox, ParsableByteArray saiz, TrackFragment out) throws ParserException {
        int vectorSize = encryptionBox.perSampleIvSize;
        saiz.setPosition(8);
        boolean subsampleEncryption = true;
        if ((Atom.parseFullAtomFlags(saiz.readInt()) & 1) == 1) {
            saiz.skipBytes(8);
        }
        int defaultSampleInfoSize = saiz.readUnsignedByte();
        int sampleCount = saiz.readUnsignedIntToInt();
        if (sampleCount == out.sampleCount) {
            int totalSize = 0;
            if (defaultSampleInfoSize == 0) {
                boolean[] sampleHasSubsampleEncryptionTable = out.sampleHasSubsampleEncryptionTable;
                for (int i = 0; i < sampleCount; i++) {
                    int sampleInfoSize = saiz.readUnsignedByte();
                    totalSize += sampleInfoSize;
                    sampleHasSubsampleEncryptionTable[i] = sampleInfoSize > vectorSize;
                }
            } else {
                if (defaultSampleInfoSize <= vectorSize) {
                    subsampleEncryption = false;
                }
                totalSize = 0 + (defaultSampleInfoSize * sampleCount);
                Arrays.fill(out.sampleHasSubsampleEncryptionTable, 0, sampleCount, subsampleEncryption);
            }
            out.initEncryptionData(totalSize);
            return;
        }
        int i2 = out.sampleCount;
        StringBuilder sb = new StringBuilder(41);
        sb.append("Length mismatch: ");
        sb.append(sampleCount);
        sb.append(", ");
        sb.append(i2);
        throw new ParserException(sb.toString());
    }

    private static void parseSaio(ParsableByteArray saio, TrackFragment out) throws ParserException {
        saio.setPosition(8);
        int fullAtom = saio.readInt();
        if ((Atom.parseFullAtomFlags(fullAtom) & 1) == 1) {
            saio.skipBytes(8);
        }
        int entryCount = saio.readUnsignedIntToInt();
        if (entryCount == 1) {
            out.auxiliaryDataPosition += Atom.parseFullAtomVersion(fullAtom) == 0 ? saio.readUnsignedInt() : saio.readUnsignedLongToLong();
            return;
        }
        StringBuilder sb = new StringBuilder(40);
        sb.append("Unexpected saio entry count: ");
        sb.append(entryCount);
        throw new ParserException(sb.toString());
    }

    /* JADX INFO: Multiple debug info for r4v1 com.google.android.exoplayer2.extractor.mp4.DefaultSampleValues: [D('baseDataPosition' long), D('defaultSampleValues' com.google.android.exoplayer2.extractor.mp4.DefaultSampleValues)] */
    private static TrackBundle parseTfhd(ParsableByteArray tfhd, SparseArray<TrackBundle> trackBundles2) {
        tfhd.setPosition(8);
        int atomFlags = Atom.parseFullAtomFlags(tfhd.readInt());
        TrackBundle trackBundle = getTrackBundle(trackBundles2, tfhd.readInt());
        if (trackBundle == null) {
            return null;
        }
        if ((atomFlags & 1) != 0) {
            long baseDataPosition = tfhd.readUnsignedLongToLong();
            trackBundle.fragment.dataPosition = baseDataPosition;
            trackBundle.fragment.auxiliaryDataPosition = baseDataPosition;
        }
        DefaultSampleValues defaultSampleValues = trackBundle.defaultSampleValues;
        trackBundle.fragment.header = new DefaultSampleValues((atomFlags & 2) != 0 ? tfhd.readUnsignedIntToInt() - 1 : defaultSampleValues.sampleDescriptionIndex, (atomFlags & 8) != 0 ? tfhd.readUnsignedIntToInt() : defaultSampleValues.duration, (atomFlags & 16) != 0 ? tfhd.readUnsignedIntToInt() : defaultSampleValues.size, (atomFlags & 32) != 0 ? tfhd.readUnsignedIntToInt() : defaultSampleValues.flags);
        return trackBundle;
    }

    @Nullable
    private static TrackBundle getTrackBundle(SparseArray<TrackBundle> trackBundles2, int trackId) {
        if (trackBundles2.size() == 1) {
            return trackBundles2.valueAt(0);
        }
        return trackBundles2.get(trackId);
    }

    private static long parseTfdt(ParsableByteArray tfdt) {
        tfdt.setPosition(8);
        return Atom.parseFullAtomVersion(tfdt.readInt()) == 1 ? tfdt.readUnsignedLongToLong() : tfdt.readUnsignedInt();
    }

    /* JADX INFO: Multiple debug info for r0v4 long: [D('timescale' long), D('cumulativeTime' long)] */
    /* JADX INFO: Multiple debug info for r0v5 long: [D('timescale' long), D('cumulativeTime' long)] */
    /* JADX INFO: Multiple debug info for r6v8 int: [D('defaultSampleValues' com.google.android.exoplayer2.extractor.mp4.DefaultSampleValues), D('sampleOffset' int)] */
    private static int parseTrun(TrackBundle trackBundle, int index, long decodeTime, int flags2, ParsableByteArray trun, int trackRunStart) {
        int firstSampleFlags;
        int sampleDuration;
        boolean sampleDurationsPresent;
        int i;
        boolean firstSampleFlagsPresent;
        int i2;
        boolean sampleFlagsPresent;
        boolean sampleSizesPresent;
        DefaultSampleValues defaultSampleValues;
        TrackBundle trackBundle2 = trackBundle;
        trun.setPosition(8);
        int atomFlags = Atom.parseFullAtomFlags(trun.readInt());
        Track track = trackBundle2.track;
        TrackFragment fragment = trackBundle2.fragment;
        DefaultSampleValues defaultSampleValues2 = fragment.header;
        fragment.trunLength[index] = trun.readUnsignedIntToInt();
        fragment.trunDataPosition[index] = fragment.dataPosition;
        if ((atomFlags & 1) != 0) {
            long[] jArr = fragment.trunDataPosition;
            jArr[index] = jArr[index] + ((long) trun.readInt());
        }
        boolean firstSampleFlagsPresent2 = (atomFlags & 4) != 0;
        int firstSampleFlags2 = defaultSampleValues2.flags;
        if (firstSampleFlagsPresent2) {
            firstSampleFlags2 = trun.readUnsignedIntToInt();
        }
        boolean sampleDurationsPresent2 = (atomFlags & 256) != 0;
        boolean sampleSizesPresent2 = (atomFlags & 512) != 0;
        boolean sampleFlagsPresent2 = (atomFlags & 1024) != 0;
        boolean sampleCompositionTimeOffsetsPresent = (atomFlags & 2048) != 0;
        long edtsOffset = 0;
        if (track.editListDurations != null && track.editListDurations.length == 1 && track.editListDurations[0] == 0) {
            firstSampleFlags = firstSampleFlags2;
            edtsOffset = Util.scaleLargeTimestamp(track.editListMediaTimes[0], 1000, track.timescale);
        } else {
            firstSampleFlags = firstSampleFlags2;
        }
        int[] sampleSizeTable = fragment.sampleSizeTable;
        int[] sampleCompositionTimeOffsetTable = fragment.sampleCompositionTimeOffsetTable;
        long[] sampleDecodingTimeTable = fragment.sampleDecodingTimeTable;
        boolean[] sampleIsSyncFrameTable = fragment.sampleIsSyncFrameTable;
        boolean workaroundEveryVideoFrameIsSyncFrame = track.type == 2 && (flags2 & 1) != 0;
        int trackRunEnd = trackRunStart + fragment.trunLength[index];
        boolean[] sampleIsSyncFrameTable2 = sampleIsSyncFrameTable;
        boolean workaroundEveryVideoFrameIsSyncFrame2 = workaroundEveryVideoFrameIsSyncFrame;
        long timescale = track.timescale;
        long cumulativeTime = index > 0 ? fragment.nextFragmentDecodeTime : decodeTime;
        int i3 = trackRunStart;
        while (i3 < trackRunEnd) {
            if (sampleDurationsPresent2) {
                sampleDuration = trun.readUnsignedIntToInt();
            } else {
                sampleDuration = defaultSampleValues2.duration;
            }
            if (sampleSizesPresent2) {
                i = trun.readUnsignedIntToInt();
                sampleDurationsPresent = sampleDurationsPresent2;
            } else {
                sampleDurationsPresent = sampleDurationsPresent2;
                i = defaultSampleValues2.size;
            }
            int sampleSize2 = i;
            if (i3 == 0 && firstSampleFlagsPresent2) {
                firstSampleFlagsPresent = firstSampleFlagsPresent2;
                i2 = firstSampleFlags;
            } else if (sampleFlagsPresent2) {
                i2 = trun.readInt();
                firstSampleFlagsPresent = firstSampleFlagsPresent2;
            } else {
                firstSampleFlagsPresent = firstSampleFlagsPresent2;
                i2 = defaultSampleValues2.flags;
            }
            int sampleFlags = i2;
            if (sampleCompositionTimeOffsetsPresent) {
                defaultSampleValues = defaultSampleValues2;
                sampleSizesPresent = sampleSizesPresent2;
                sampleFlagsPresent = sampleFlagsPresent2;
                sampleCompositionTimeOffsetTable[i3] = (int) ((((long) trun.readInt()) * 1000) / timescale);
            } else {
                defaultSampleValues = defaultSampleValues2;
                sampleSizesPresent = sampleSizesPresent2;
                sampleFlagsPresent = sampleFlagsPresent2;
                sampleCompositionTimeOffsetTable[i3] = 0;
            }
            sampleDecodingTimeTable[i3] = Util.scaleLargeTimestamp(cumulativeTime, 1000, timescale) - edtsOffset;
            sampleSizeTable[i3] = sampleSize2;
            sampleIsSyncFrameTable2[i3] = ((sampleFlags >> 16) & 1) == 0 && (!workaroundEveryVideoFrameIsSyncFrame2 || i3 == 0);
            cumulativeTime += (long) sampleDuration;
            i3++;
            timescale = timescale;
            sampleDurationsPresent2 = sampleDurationsPresent;
            firstSampleFlagsPresent2 = firstSampleFlagsPresent;
            defaultSampleValues2 = defaultSampleValues;
            sampleSizesPresent2 = sampleSizesPresent;
            sampleFlagsPresent2 = sampleFlagsPresent;
        }
        fragment.nextFragmentDecodeTime = cumulativeTime;
        return trackRunEnd;
    }

    private static void parseUuid(ParsableByteArray uuid, TrackFragment out, byte[] extendedTypeScratch) throws ParserException {
        uuid.setPosition(8);
        uuid.readBytes(extendedTypeScratch, 0, 16);
        if (Arrays.equals(extendedTypeScratch, PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE)) {
            parseSenc(uuid, 16, out);
        }
    }

    private static void parseSenc(ParsableByteArray senc, TrackFragment out) throws ParserException {
        parseSenc(senc, 0, out);
    }

    private static void parseSenc(ParsableByteArray senc, int offset, TrackFragment out) throws ParserException {
        senc.setPosition(offset + 8);
        int flags2 = Atom.parseFullAtomFlags(senc.readInt());
        if ((flags2 & 1) == 0) {
            boolean subsampleEncryption = (flags2 & 2) != 0;
            int sampleCount = senc.readUnsignedIntToInt();
            if (sampleCount == out.sampleCount) {
                Arrays.fill(out.sampleHasSubsampleEncryptionTable, 0, sampleCount, subsampleEncryption);
                out.initEncryptionData(senc.bytesLeft());
                out.fillEncryptionData(senc);
                return;
            }
            int i = out.sampleCount;
            StringBuilder sb = new StringBuilder(41);
            sb.append("Length mismatch: ");
            sb.append(sampleCount);
            sb.append(", ");
            sb.append(i);
            throw new ParserException(sb.toString());
        }
        throw new ParserException("Overriding TrackEncryptionBox parameters is unsupported.");
    }

    private static void parseSgpd(ParsableByteArray sbgp, ParsableByteArray sgpd, String schemeType, TrackFragment out) throws ParserException {
        byte[] constantIv;
        ParsableByteArray parsableByteArray = sbgp;
        ParsableByteArray parsableByteArray2 = sgpd;
        TrackFragment trackFragment = out;
        parsableByteArray.setPosition(8);
        int sbgpFullAtom = sbgp.readInt();
        if (sbgp.readInt() == SAMPLE_GROUP_TYPE_seig) {
            if (Atom.parseFullAtomVersion(sbgpFullAtom) == 1) {
                parsableByteArray.skipBytes(4);
            }
            if (sbgp.readInt() == 1) {
                parsableByteArray2.setPosition(8);
                int sgpdFullAtom = sgpd.readInt();
                if (sgpd.readInt() == SAMPLE_GROUP_TYPE_seig) {
                    int sgpdVersion = Atom.parseFullAtomVersion(sgpdFullAtom);
                    if (sgpdVersion == 1) {
                        if (sgpd.readUnsignedInt() == 0) {
                            throw new ParserException("Variable length description in sgpd found (unsupported)");
                        }
                    } else if (sgpdVersion >= 2) {
                        parsableByteArray2.skipBytes(4);
                    }
                    if (sgpd.readUnsignedInt() == 1) {
                        parsableByteArray2.skipBytes(1);
                        int patternByte = sgpd.readUnsignedByte();
                        int cryptByteBlock = (patternByte & 240) >> 4;
                        int skipByteBlock = patternByte & 15;
                        boolean isProtected = sgpd.readUnsignedByte() == 1;
                        if (isProtected) {
                            int perSampleIvSize = sgpd.readUnsignedByte();
                            byte[] keyId = new byte[16];
                            parsableByteArray2.readBytes(keyId, 0, keyId.length);
                            if (perSampleIvSize == 0) {
                                int constantIvSize = sgpd.readUnsignedByte();
                                byte[] constantIv2 = new byte[constantIvSize];
                                parsableByteArray2.readBytes(constantIv2, 0, constantIvSize);
                                constantIv = constantIv2;
                            } else {
                                constantIv = null;
                            }
                            trackFragment.definesEncryptionData = true;
                            trackFragment.trackEncryptionBox = new TrackEncryptionBox(isProtected, schemeType, perSampleIvSize, keyId, cryptByteBlock, skipByteBlock, constantIv);
                            return;
                        }
                        return;
                    }
                    throw new ParserException("Entry count in sgpd != 1 (unsupported).");
                }
                return;
            }
            throw new ParserException("Entry count in sbgp != 1 (unsupported).");
        }
    }

    /* JADX INFO: Multiple debug info for r1v3 long[]: [D('offsets' long[]), D('fullAtom' int)] */
    /* JADX INFO: Multiple debug info for r2v2 long[]: [D('version' int), D('durationsUs' long[])] */
    /* JADX INFO: Multiple debug info for r12v2 int[]: [D('sizes' int[]), D('earliestPresentationTime' long)] */
    /* JADX INFO: Multiple debug info for r1v4 long[]: [D('offsets' long[]), D('fullAtom' int)] */
    /* JADX INFO: Multiple debug info for r2v3 long[]: [D('version' int), D('durationsUs' long[])] */
    /* JADX INFO: Multiple debug info for r12v3 int[]: [D('sizes' int[]), D('earliestPresentationTime' long)] */
    private static Pair<Long, ChunkIndex> parseSidx(ParsableByteArray atom, long inputPosition) throws ParserException {
        long offset;
        long earliestPresentationTime;
        ParsableByteArray parsableByteArray = atom;
        parsableByteArray.setPosition(8);
        int fullAtom = atom.readInt();
        int version = Atom.parseFullAtomVersion(fullAtom);
        parsableByteArray.skipBytes(4);
        long timescale = atom.readUnsignedInt();
        long offset2 = inputPosition;
        if (version == 0) {
            long earliestPresentationTime2 = atom.readUnsignedInt();
            offset = offset2 + atom.readUnsignedInt();
            earliestPresentationTime = earliestPresentationTime2;
        } else {
            long earliestPresentationTime3 = atom.readUnsignedLongToLong();
            offset = offset2 + atom.readUnsignedLongToLong();
            earliestPresentationTime = earliestPresentationTime3;
        }
        long earliestPresentationTimeUs = Util.scaleLargeTimestamp(earliestPresentationTime, 1000000, timescale);
        parsableByteArray.skipBytes(2);
        int referenceCount = atom.readUnsignedShort();
        int[] sizes = new int[referenceCount];
        long[] offsets = new long[referenceCount];
        long[] durationsUs = new long[referenceCount];
        long[] timesUs = new long[referenceCount];
        long timeUs = earliestPresentationTimeUs;
        long time = earliestPresentationTime;
        long offset3 = offset;
        int i = 0;
        while (i < referenceCount) {
            int firstInt = atom.readInt();
            if ((firstInt & Integer.MIN_VALUE) == 0) {
                long referenceDuration = atom.readUnsignedInt();
                sizes[i] = Integer.MAX_VALUE & firstInt;
                offsets[i] = offset3;
                timesUs[i] = timeUs;
                time += referenceDuration;
                long[] timesUs2 = timesUs;
                int version2 = version;
                long[] durationsUs2 = durationsUs;
                long earliestPresentationTime4 = earliestPresentationTime;
                int[] sizes2 = sizes;
                timeUs = Util.scaleLargeTimestamp(time, 1000000, timescale);
                durationsUs2[i] = timeUs - timesUs2[i];
                parsableByteArray.skipBytes(4);
                offset3 += (long) sizes2[i];
                i++;
                offsets = offsets;
                durationsUs = durationsUs2;
                timesUs = timesUs2;
                sizes = sizes2;
                referenceCount = referenceCount;
                fullAtom = fullAtom;
                version = version2;
                earliestPresentationTime = earliestPresentationTime4;
            } else {
                throw new ParserException("Unhandled indirect reference");
            }
        }
        return Pair.create(Long.valueOf(earliestPresentationTimeUs), new ChunkIndex(sizes, offsets, durationsUs, timesUs));
    }

    private static TrackBundle getNextFragmentRun(SparseArray<TrackBundle> trackBundles2) {
        TrackBundle nextTrackBundle = null;
        long nextTrackRunOffset = Long.MAX_VALUE;
        int trackBundlesSize = trackBundles2.size();
        for (int i = 0; i < trackBundlesSize; i++) {
            TrackBundle trackBundle = trackBundles2.valueAt(i);
            if (trackBundle.currentTrackRunIndex != trackBundle.fragment.trunCount) {
                long trunOffset = trackBundle.fragment.trunDataPosition[trackBundle.currentTrackRunIndex];
                if (trunOffset < nextTrackRunOffset) {
                    nextTrackBundle = trackBundle;
                    nextTrackRunOffset = trunOffset;
                }
            }
        }
        return nextTrackBundle;
    }

    private static DrmInitData getDrmInitDataFromAtoms(List<Atom.LeafAtom> leafChildren) {
        ArrayList<DrmInitData.SchemeData> schemeDatas = null;
        int leafChildrenSize = leafChildren.size();
        for (int i = 0; i < leafChildrenSize; i++) {
            Atom.LeafAtom child = leafChildren.get(i);
            if (child.type == Atom.TYPE_pssh) {
                if (schemeDatas == null) {
                    schemeDatas = new ArrayList<>();
                }
                byte[] psshData = child.data.data;
                UUID uuid = PsshAtomUtil.parseUuid(psshData);
                if (uuid == null) {
                    Log.m30w(TAG, "Skipped pssh atom (failed to extract uuid)");
                } else {
                    schemeDatas.add(new DrmInitData.SchemeData(uuid, MimeTypes.VIDEO_MP4, psshData));
                }
            }
        }
        if (schemeDatas == null) {
            return null;
        }
        return new DrmInitData(schemeDatas);
    }

    private static boolean shouldParseLeafAtom(int atom) {
        return atom == Atom.TYPE_hdlr || atom == Atom.TYPE_mdhd || atom == Atom.TYPE_mvhd || atom == Atom.TYPE_sidx || atom == Atom.TYPE_stsd || atom == Atom.TYPE_tfdt || atom == Atom.TYPE_tfhd || atom == Atom.TYPE_tkhd || atom == Atom.TYPE_trex || atom == Atom.TYPE_trun || atom == Atom.TYPE_pssh || atom == Atom.TYPE_saiz || atom == Atom.TYPE_saio || atom == Atom.TYPE_senc || atom == Atom.TYPE_uuid || atom == Atom.TYPE_sbgp || atom == Atom.TYPE_sgpd || atom == Atom.TYPE_elst || atom == Atom.TYPE_mehd || atom == Atom.TYPE_emsg;
    }

    private static boolean shouldParseContainerAtom(int atom) {
        return atom == Atom.TYPE_moov || atom == Atom.TYPE_trak || atom == Atom.TYPE_mdia || atom == Atom.TYPE_minf || atom == Atom.TYPE_stbl || atom == Atom.TYPE_moof || atom == Atom.TYPE_traf || atom == Atom.TYPE_mvex || atom == Atom.TYPE_edts;
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        return Sniffer.sniffFragmented(input);
    }

    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
        Track track = this.sideloadedTrack;
        if (track != null) {
            TrackBundle bundle = new TrackBundle(output.track(0, track.type));
            bundle.init(this.sideloadedTrack, new DefaultSampleValues(0, 0, 0, 0));
            this.trackBundles.put(0, bundle);
            maybeInitExtraTracks();
            this.extractorOutput.endTracks();
        }
    }

    public void seek(long position, long timeUs) {
        int trackCount = this.trackBundles.size();
        for (int i = 0; i < trackCount; i++) {
            this.trackBundles.valueAt(i).reset();
        }
        this.pendingMetadataSampleInfos.clear();
        this.pendingMetadataSampleBytes = 0;
        this.pendingSeekTimeUs = timeUs;
        this.containerAtoms.clear();
        this.isAc4HeaderRequired = false;
        enterReadingAtomHeaderState();
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        while (true) {
            int i = this.parserState;
            if (i != 0) {
                if (i == 1) {
                    readAtomPayload(input);
                } else if (i == 2) {
                    readEncryptionData(input);
                } else if (readSample(input)) {
                    return 0;
                }
            } else if (!readAtomHeader(input)) {
                return -1;
            }
        }
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
            long atomPosition = input.getPosition() - ((long) this.atomHeaderBytesRead);
            if (this.atomType == Atom.TYPE_moof) {
                int trackCount = this.trackBundles.size();
                for (int i = 0; i < trackCount; i++) {
                    TrackFragment fragment = this.trackBundles.valueAt(i).fragment;
                    fragment.atomPosition = atomPosition;
                    fragment.auxiliaryDataPosition = atomPosition;
                    fragment.dataPosition = atomPosition;
                }
            }
            if (this.atomType == Atom.TYPE_mdat) {
                this.currentTrackBundle = null;
                this.endOfMdatPosition = this.atomSize + atomPosition;
                if (!this.haveOutputSeekMap) {
                    this.extractorOutput.seekMap(new SeekMap.Unseekable(this.durationUs, atomPosition));
                    this.haveOutputSeekMap = true;
                }
                this.parserState = 2;
                return true;
            }
            if (shouldParseContainerAtom(this.atomType)) {
                long endPosition2 = (input.getPosition() + this.atomSize) - 8;
                this.containerAtoms.push(new Atom.ContainerAtom(this.atomType, endPosition2));
                if (this.atomSize == ((long) this.atomHeaderBytesRead)) {
                    processAtomEnded(endPosition2);
                } else {
                    enterReadingAtomHeaderState();
                }
            } else if (shouldParseLeafAtom(this.atomType)) {
                if (this.atomHeaderBytesRead == 8) {
                    long j2 = this.atomSize;
                    if (j2 <= 2147483647L) {
                        this.atomData = new ParsableByteArray((int) j2);
                        System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
                        this.parserState = 1;
                    } else {
                        throw new ParserException("Leaf atom with length > 2147483647 (unsupported).");
                    }
                } else {
                    throw new ParserException("Leaf atom defines extended atom size (unsupported).");
                }
            } else if (this.atomSize <= 2147483647L) {
                this.atomData = null;
                this.parserState = 1;
            } else {
                throw new ParserException("Skipping atom with length > 2147483647 (unsupported).");
            }
            return true;
        }
        throw new ParserException("Atom size less than header length (unsupported).");
    }

    private void readAtomPayload(ExtractorInput input) throws IOException, InterruptedException {
        int atomPayloadSize = ((int) this.atomSize) - this.atomHeaderBytesRead;
        ParsableByteArray parsableByteArray = this.atomData;
        if (parsableByteArray != null) {
            input.readFully(parsableByteArray.data, 8, atomPayloadSize);
            onLeafAtomRead(new Atom.LeafAtom(this.atomType, this.atomData), input.getPosition());
        } else {
            input.skipFully(atomPayloadSize);
        }
        processAtomEnded(input.getPosition());
    }

    private void processAtomEnded(long atomEndPosition) throws ParserException {
        while (!this.containerAtoms.isEmpty() && this.containerAtoms.peek().endPosition == atomEndPosition) {
            onContainerAtomRead(this.containerAtoms.pop());
        }
        enterReadingAtomHeaderState();
    }

    private void onLeafAtomRead(Atom.LeafAtom leaf, long inputPosition) throws ParserException {
        if (!this.containerAtoms.isEmpty()) {
            this.containerAtoms.peek().add(leaf);
        } else if (leaf.type == Atom.TYPE_sidx) {
            Pair<Long, ChunkIndex> result = parseSidx(leaf.data, inputPosition);
            this.segmentIndexEarliestPresentationTimeUs = ((Long) result.first).longValue();
            this.extractorOutput.seekMap((SeekMap) result.second);
            this.haveOutputSeekMap = true;
        } else if (leaf.type == Atom.TYPE_emsg) {
            onEmsgLeafAtomRead(leaf.data);
        }
    }

    private void onContainerAtomRead(Atom.ContainerAtom container) throws ParserException {
        if (container.type == Atom.TYPE_moov) {
            onMoovContainerAtomRead(container);
        } else if (container.type == Atom.TYPE_moof) {
            onMoofContainerAtomRead(container);
        } else if (!this.containerAtoms.isEmpty()) {
            this.containerAtoms.peek().add(container);
        }
    }

    private void onMoovContainerAtomRead(Atom.ContainerAtom moov) throws ParserException {
        DrmInitData drmInitData;
        int moovContainerChildrenSize;
        int i;
        SparseArray<Track> tracks;
        Atom.ContainerAtom containerAtom = moov;
        Assertions.checkState(this.sideloadedTrack == null, "Unexpected moov box.");
        DrmInitData drmInitData2 = this.sideloadedDrmInitData;
        if (drmInitData2 != null) {
            drmInitData = drmInitData2;
        } else {
            drmInitData = getDrmInitDataFromAtoms(containerAtom.leafChildren);
        }
        Atom.ContainerAtom mvex = containerAtom.getContainerAtomOfType(Atom.TYPE_mvex);
        SparseArray sparseArray = new SparseArray();
        int mvexChildrenSize = mvex.leafChildren.size();
        long duration = -9223372036854775807L;
        for (int i2 = 0; i2 < mvexChildrenSize; i2++) {
            Atom.LeafAtom atom = mvex.leafChildren.get(i2);
            if (atom.type == Atom.TYPE_trex) {
                Pair<Integer, DefaultSampleValues> trexData = parseTrex(atom.data);
                sparseArray.put(((Integer) trexData.first).intValue(), (DefaultSampleValues) trexData.second);
            } else if (atom.type == Atom.TYPE_mehd) {
                duration = parseMehd(atom.data);
            }
        }
        SparseArray<Track> tracks2 = new SparseArray<>();
        int moovContainerChildrenSize2 = containerAtom.containerChildren.size();
        int i3 = 0;
        while (i3 < moovContainerChildrenSize2) {
            Atom.ContainerAtom atom2 = containerAtom.containerChildren.get(i3);
            if (atom2.type == Atom.TYPE_trak) {
                i = i3;
                moovContainerChildrenSize = moovContainerChildrenSize2;
                tracks = tracks2;
                Track track = modifyTrack(AtomParsers.parseTrak(atom2, containerAtom.getLeafAtomOfType(Atom.TYPE_mvhd), duration, drmInitData, (this.flags & 16) != 0, false));
                if (track != null) {
                    tracks.put(track.f77id, track);
                }
            } else {
                i = i3;
                moovContainerChildrenSize = moovContainerChildrenSize2;
                tracks = tracks2;
            }
            i3 = i + 1;
            tracks2 = tracks;
            moovContainerChildrenSize2 = moovContainerChildrenSize;
        }
        SparseArray<Track> tracks3 = tracks2;
        int trackCount = tracks3.size();
        if (this.trackBundles.size() == 0) {
            int i4 = 0;
            while (i4 < trackCount) {
                Track track2 = (Track) tracks3.valueAt(i4);
                TrackBundle trackBundle = new TrackBundle(this.extractorOutput.track(i4, track2.type));
                trackBundle.init(track2, getDefaultSampleValues(sparseArray, track2.f77id));
                this.trackBundles.put(track2.f77id, trackBundle);
                this.durationUs = Math.max(this.durationUs, track2.durationUs);
                i4++;
                mvex = mvex;
            }
            maybeInitExtraTracks();
            this.extractorOutput.endTracks();
            return;
        }
        Assertions.checkState(this.trackBundles.size() == trackCount);
        for (int i5 = 0; i5 < trackCount; i5++) {
            Track track3 = (Track) tracks3.valueAt(i5);
            this.trackBundles.get(track3.f77id).init(track3, getDefaultSampleValues(sparseArray, track3.f77id));
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Track modifyTrack(@Nullable Track track) {
        return track;
    }

    private DefaultSampleValues getDefaultSampleValues(SparseArray<DefaultSampleValues> defaultSampleValuesArray, int trackId) {
        if (defaultSampleValuesArray.size() == 1) {
            return defaultSampleValuesArray.valueAt(0);
        }
        return (DefaultSampleValues) Assertions.checkNotNull(defaultSampleValuesArray.get(trackId));
    }

    private void onMoofContainerAtomRead(Atom.ContainerAtom moof) throws ParserException {
        DrmInitData drmInitData;
        parseMoof(moof, this.trackBundles, this.flags, this.scratchBytes);
        if (this.sideloadedDrmInitData != null) {
            drmInitData = null;
        } else {
            drmInitData = getDrmInitDataFromAtoms(moof.leafChildren);
        }
        if (drmInitData != null) {
            int trackCount = this.trackBundles.size();
            for (int i = 0; i < trackCount; i++) {
                this.trackBundles.valueAt(i).updateDrmInitData(drmInitData);
            }
        }
        if (this.pendingSeekTimeUs != C0841C.TIME_UNSET) {
            int trackCount2 = this.trackBundles.size();
            for (int i2 = 0; i2 < trackCount2; i2++) {
                this.trackBundles.valueAt(i2).seek(this.pendingSeekTimeUs);
            }
            this.pendingSeekTimeUs = C0841C.TIME_UNSET;
        }
    }

    private void maybeInitExtraTracks() {
        if (this.emsgTrackOutputs == null) {
            this.emsgTrackOutputs = new TrackOutput[2];
            int emsgTrackOutputCount = 0;
            TrackOutput trackOutput = this.additionalEmsgTrackOutput;
            if (trackOutput != null) {
                this.emsgTrackOutputs[0] = trackOutput;
                emsgTrackOutputCount = 0 + 1;
            }
            if ((this.flags & 4) != 0) {
                this.emsgTrackOutputs[emsgTrackOutputCount] = this.extractorOutput.track(this.trackBundles.size(), 4);
                emsgTrackOutputCount++;
            }
            this.emsgTrackOutputs = (TrackOutput[]) Arrays.copyOf(this.emsgTrackOutputs, emsgTrackOutputCount);
            for (TrackOutput eventMessageTrackOutput : this.emsgTrackOutputs) {
                eventMessageTrackOutput.format(EMSG_FORMAT);
            }
        }
        if (this.cea608TrackOutputs == null) {
            this.cea608TrackOutputs = new TrackOutput[this.closedCaptionFormats.size()];
            for (int i = 0; i < this.cea608TrackOutputs.length; i++) {
                TrackOutput output = this.extractorOutput.track(this.trackBundles.size() + 1 + i, 3);
                output.format(this.closedCaptionFormats.get(i));
                this.cea608TrackOutputs[i] = output;
            }
        }
    }

    private void onEmsgLeafAtomRead(ParsableByteArray atom) {
        long sampleTimeUs;
        ParsableByteArray parsableByteArray = atom;
        TrackOutput[] trackOutputArr = this.emsgTrackOutputs;
        if (trackOutputArr != null && trackOutputArr.length != 0) {
            parsableByteArray.setPosition(12);
            int sampleSize2 = atom.bytesLeft();
            atom.readNullTerminatedString();
            atom.readNullTerminatedString();
            long presentationTimeDeltaUs = Util.scaleLargeTimestamp(atom.readUnsignedInt(), 1000000, atom.readUnsignedInt());
            int position = atom.getPosition();
            parsableByteArray.data[position - 4] = 0;
            parsableByteArray.data[position - 3] = 0;
            parsableByteArray.data[position - 2] = 0;
            parsableByteArray.data[position - 1] = 0;
            for (TrackOutput emsgTrackOutput : this.emsgTrackOutputs) {
                parsableByteArray.setPosition(12);
                emsgTrackOutput.sampleData(parsableByteArray, sampleSize2);
            }
            long j = this.segmentIndexEarliestPresentationTimeUs;
            if (j != C0841C.TIME_UNSET) {
                long sampleTimeUs2 = j + presentationTimeDeltaUs;
                TimestampAdjuster timestampAdjuster2 = this.timestampAdjuster;
                if (timestampAdjuster2 != null) {
                    sampleTimeUs = timestampAdjuster2.adjustSampleTimestamp(sampleTimeUs2);
                } else {
                    sampleTimeUs = sampleTimeUs2;
                }
                TrackOutput[] trackOutputArr2 = this.emsgTrackOutputs;
                int i = 0;
                for (int length = trackOutputArr2.length; i < length; length = length) {
                    trackOutputArr2[i].sampleMetadata(sampleTimeUs, 1, sampleSize2, 0, null);
                    i++;
                }
                return;
            }
            this.pendingMetadataSampleInfos.addLast(new MetadataSampleInfo(presentationTimeDeltaUs, sampleSize2));
            this.pendingMetadataSampleBytes += sampleSize2;
        }
    }

    private void readEncryptionData(ExtractorInput input) throws IOException, InterruptedException {
        TrackBundle nextTrackBundle = null;
        long nextDataOffset = Long.MAX_VALUE;
        int trackBundlesSize = this.trackBundles.size();
        for (int i = 0; i < trackBundlesSize; i++) {
            TrackFragment trackFragment = this.trackBundles.valueAt(i).fragment;
            if (trackFragment.sampleEncryptionDataNeedsFill && trackFragment.auxiliaryDataPosition < nextDataOffset) {
                nextDataOffset = trackFragment.auxiliaryDataPosition;
                nextTrackBundle = this.trackBundles.valueAt(i);
            }
        }
        if (nextTrackBundle == null) {
            this.parserState = 3;
            return;
        }
        int bytesToSkip = (int) (nextDataOffset - input.getPosition());
        if (bytesToSkip >= 0) {
            input.skipFully(bytesToSkip);
            nextTrackBundle.fragment.fillEncryptionData(input);
            return;
        }
        throw new ParserException("Offset to encryption data was negative.");
    }

    private boolean readSample(ExtractorInput input) throws IOException, InterruptedException {
        long sampleTimeUs;
        boolean z;
        int writtenBytes;
        ExtractorInput extractorInput = input;
        int i = 4;
        int i2 = 1;
        int i3 = 0;
        if (this.parserState == 3) {
            if (this.currentTrackBundle == null) {
                TrackBundle currentTrackBundle2 = getNextFragmentRun(this.trackBundles);
                if (currentTrackBundle2 == null) {
                    int bytesToSkip = (int) (this.endOfMdatPosition - input.getPosition());
                    if (bytesToSkip >= 0) {
                        extractorInput.skipFully(bytesToSkip);
                        enterReadingAtomHeaderState();
                        return false;
                    }
                    throw new ParserException("Offset to end of mdat was negative.");
                }
                int bytesToSkip2 = (int) (currentTrackBundle2.fragment.trunDataPosition[currentTrackBundle2.currentTrackRunIndex] - input.getPosition());
                if (bytesToSkip2 < 0) {
                    Log.m30w(TAG, "Ignoring negative offset to sample data.");
                    bytesToSkip2 = 0;
                }
                extractorInput.skipFully(bytesToSkip2);
                this.currentTrackBundle = currentTrackBundle2;
            }
            this.sampleSize = this.currentTrackBundle.fragment.sampleSizeTable[this.currentTrackBundle.currentSampleIndex];
            if (this.currentTrackBundle.currentSampleIndex < this.currentTrackBundle.firstSampleToOutputIndex) {
                extractorInput.skipFully(this.sampleSize);
                this.currentTrackBundle.skipSampleEncryptionData();
                if (!this.currentTrackBundle.next()) {
                    this.currentTrackBundle = null;
                }
                this.parserState = 3;
                return true;
            }
            if (this.currentTrackBundle.track.sampleTransformation == 1) {
                this.sampleSize -= 8;
                extractorInput.skipFully(8);
            }
            this.sampleBytesWritten = this.currentTrackBundle.outputSampleEncryptionData();
            this.sampleSize += this.sampleBytesWritten;
            this.parserState = 4;
            this.sampleCurrentNalBytesRemaining = 0;
            this.isAc4HeaderRequired = MimeTypes.AUDIO_AC4.equals(this.currentTrackBundle.track.format.sampleMimeType);
        }
        TrackFragment fragment = this.currentTrackBundle.fragment;
        Track track = this.currentTrackBundle.track;
        TrackOutput output = this.currentTrackBundle.output;
        int sampleIndex = this.currentTrackBundle.currentSampleIndex;
        long sampleTimeUs2 = fragment.getSamplePresentationTime(sampleIndex) * 1000;
        TimestampAdjuster timestampAdjuster2 = this.timestampAdjuster;
        if (timestampAdjuster2 != null) {
            sampleTimeUs = timestampAdjuster2.adjustSampleTimestamp(sampleTimeUs2);
        } else {
            sampleTimeUs = sampleTimeUs2;
        }
        if (track.nalUnitLengthFieldLength == 0) {
            if (this.isAc4HeaderRequired) {
                Ac4Util.getAc4SampleHeader(this.sampleSize, this.scratch);
                int length = this.scratch.limit();
                output.sampleData(this.scratch, length);
                this.sampleSize += length;
                this.sampleBytesWritten += length;
                z = false;
                this.isAc4HeaderRequired = false;
            } else {
                z = false;
            }
            while (true) {
                int i4 = this.sampleBytesWritten;
                int i5 = this.sampleSize;
                if (i4 >= i5) {
                    break;
                }
                this.sampleBytesWritten += output.sampleData(extractorInput, i5 - i4, z);
            }
        } else {
            byte[] nalPrefixData = this.nalPrefix.data;
            nalPrefixData[0] = 0;
            nalPrefixData[1] = 0;
            nalPrefixData[2] = 0;
            int nalUnitPrefixLength = track.nalUnitLengthFieldLength + 1;
            int nalUnitLengthFieldLengthDiff = 4 - track.nalUnitLengthFieldLength;
            while (this.sampleBytesWritten < this.sampleSize) {
                int i6 = this.sampleCurrentNalBytesRemaining;
                if (i6 == 0) {
                    extractorInput.readFully(nalPrefixData, nalUnitLengthFieldLengthDiff, nalUnitPrefixLength);
                    this.nalPrefix.setPosition(i3);
                    int nalLengthInt = this.nalPrefix.readInt();
                    if (nalLengthInt >= i2) {
                        this.sampleCurrentNalBytesRemaining = nalLengthInt - 1;
                        this.nalStartCode.setPosition(i3);
                        output.sampleData(this.nalStartCode, i);
                        output.sampleData(this.nalPrefix, i2);
                        this.processSeiNalUnitPayload = this.cea608TrackOutputs.length > 0 && NalUnitUtil.isNalUnitSei(track.format.sampleMimeType, nalPrefixData[i]);
                        this.sampleBytesWritten += 5;
                        this.sampleSize += nalUnitLengthFieldLengthDiff;
                        i2 = 1;
                    } else {
                        throw new ParserException("Invalid NAL length");
                    }
                } else {
                    if (this.processSeiNalUnitPayload) {
                        this.nalBuffer.reset(i6);
                        extractorInput.readFully(this.nalBuffer.data, i3, this.sampleCurrentNalBytesRemaining);
                        output.sampleData(this.nalBuffer, this.sampleCurrentNalBytesRemaining);
                        writtenBytes = this.sampleCurrentNalBytesRemaining;
                        int unescapedLength = NalUnitUtil.unescapeStream(this.nalBuffer.data, this.nalBuffer.limit());
                        this.nalBuffer.setPosition(MimeTypes.VIDEO_H265.equals(track.format.sampleMimeType) ? 1 : 0);
                        this.nalBuffer.setLimit(unescapedLength);
                        CeaUtil.consume(sampleTimeUs, this.nalBuffer, this.cea608TrackOutputs);
                    } else {
                        writtenBytes = output.sampleData(extractorInput, i6, false);
                    }
                    this.sampleBytesWritten += writtenBytes;
                    this.sampleCurrentNalBytesRemaining -= writtenBytes;
                    i = 4;
                    i2 = 1;
                    i3 = 0;
                }
            }
            z = false;
        }
        if (fragment.sampleIsSyncFrameTable[sampleIndex]) {
            z = true;
        }
        int sampleFlags = z;
        TrackOutput.CryptoData cryptoData = null;
        TrackEncryptionBox encryptionBox = this.currentTrackBundle.getEncryptionBoxIfEncrypted();
        if (encryptionBox != null) {
            sampleFlags |= 1073741824;
            cryptoData = encryptionBox.cryptoData;
        }
        output.sampleMetadata(sampleTimeUs, (int) sampleFlags, this.sampleSize, 0, cryptoData);
        outputPendingMetadataSamples(sampleTimeUs);
        if (!this.currentTrackBundle.next()) {
            this.currentTrackBundle = null;
        }
        this.parserState = 3;
        return true;
    }

    private void outputPendingMetadataSamples(long sampleTimeUs) {
        while (!this.pendingMetadataSampleInfos.isEmpty()) {
            MetadataSampleInfo sampleInfo = this.pendingMetadataSampleInfos.removeFirst();
            this.pendingMetadataSampleBytes -= sampleInfo.size;
            long metadataTimeUs = sampleTimeUs + sampleInfo.presentationTimeDeltaUs;
            TimestampAdjuster timestampAdjuster2 = this.timestampAdjuster;
            if (timestampAdjuster2 != null) {
                metadataTimeUs = timestampAdjuster2.adjustSampleTimestamp(metadataTimeUs);
            }
            for (TrackOutput emsgTrackOutput : this.emsgTrackOutputs) {
                emsgTrackOutput.sampleMetadata(metadataTimeUs, 1, sampleInfo.size, this.pendingMetadataSampleBytes, null);
            }
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private static final class MetadataSampleInfo {
        public final long presentationTimeDeltaUs;
        public final int size;

        public MetadataSampleInfo(long presentationTimeDeltaUs2, int size2) {
            this.presentationTimeDeltaUs = presentationTimeDeltaUs2;
            this.size = size2;
        }
    }

    private static final class TrackBundle {
        public final TrackFragment fragment = new TrackFragment();
        public final TrackOutput output;
        private final ParsableByteArray defaultInitializationVector = new ParsableByteArray();
        private final ParsableByteArray encryptionSignalByte = new ParsableByteArray(1);
        public int currentSampleInTrackRun;
        public int currentSampleIndex;
        public int currentTrackRunIndex;
        public DefaultSampleValues defaultSampleValues;
        public int firstSampleToOutputIndex;
        public Track track;

        public TrackBundle(TrackOutput output2) {
            this.output = output2;
        }

        public void init(Track track2, DefaultSampleValues defaultSampleValues2) {
            this.track = (Track) Assertions.checkNotNull(track2);
            this.defaultSampleValues = (DefaultSampleValues) Assertions.checkNotNull(defaultSampleValues2);
            this.output.format(track2.format);
            reset();
        }

        public void updateDrmInitData(DrmInitData drmInitData) {
            TrackEncryptionBox encryptionBox = this.track.getSampleDescriptionEncryptionBox(this.fragment.header.sampleDescriptionIndex);
            this.output.format(this.track.format.copyWithDrmInitData(drmInitData.copyWithSchemeType(encryptionBox != null ? encryptionBox.schemeType : null)));
        }

        public void reset() {
            this.fragment.reset();
            this.currentSampleIndex = 0;
            this.currentTrackRunIndex = 0;
            this.currentSampleInTrackRun = 0;
            this.firstSampleToOutputIndex = 0;
        }

        public void seek(long timeUs) {
            long timeMs = C0841C.usToMs(timeUs);
            int searchIndex = this.currentSampleIndex;
            while (searchIndex < this.fragment.sampleCount && this.fragment.getSamplePresentationTime(searchIndex) < timeMs) {
                if (this.fragment.sampleIsSyncFrameTable[searchIndex]) {
                    this.firstSampleToOutputIndex = searchIndex;
                }
                searchIndex++;
            }
        }

        public boolean next() {
            this.currentSampleIndex++;
            this.currentSampleInTrackRun++;
            int i = this.currentSampleInTrackRun;
            int[] iArr = this.fragment.trunLength;
            int i2 = this.currentTrackRunIndex;
            if (i != iArr[i2]) {
                return true;
            }
            this.currentTrackRunIndex = i2 + 1;
            this.currentSampleInTrackRun = 0;
            return false;
        }

        /* JADX INFO: Multiple debug info for r2v2 byte[]: [D('initializationVectorData' com.google.android.exoplayer2.util.ParsableByteArray), D('initVectorData' byte[])] */
        public int outputSampleEncryptionData() {
            ParsableByteArray initializationVectorData;
            int vectorSize;
            TrackEncryptionBox encryptionBox = getEncryptionBoxIfEncrypted();
            if (encryptionBox == null) {
                return 0;
            }
            if (encryptionBox.perSampleIvSize != 0) {
                initializationVectorData = this.fragment.sampleEncryptionData;
                vectorSize = encryptionBox.perSampleIvSize;
            } else {
                byte[] initVectorData = encryptionBox.defaultInitializationVector;
                this.defaultInitializationVector.reset(initVectorData, initVectorData.length);
                initializationVectorData = this.defaultInitializationVector;
                vectorSize = initVectorData.length;
            }
            boolean subsampleEncryption = this.fragment.sampleHasSubsampleEncryptionTable(this.currentSampleIndex);
            this.encryptionSignalByte.data[0] = (byte) ((subsampleEncryption ? 128 : 0) | vectorSize);
            this.encryptionSignalByte.setPosition(0);
            this.output.sampleData(this.encryptionSignalByte, 1);
            this.output.sampleData(initializationVectorData, vectorSize);
            if (!subsampleEncryption) {
                return vectorSize + 1;
            }
            ParsableByteArray subsampleEncryptionData = this.fragment.sampleEncryptionData;
            int subsampleCount = subsampleEncryptionData.readUnsignedShort();
            subsampleEncryptionData.skipBytes(-2);
            int subsampleDataLength = (subsampleCount * 6) + 2;
            this.output.sampleData(subsampleEncryptionData, subsampleDataLength);
            return vectorSize + 1 + subsampleDataLength;
        }

        /* access modifiers changed from: private */
        public void skipSampleEncryptionData() {
            TrackEncryptionBox encryptionBox = getEncryptionBoxIfEncrypted();
            if (encryptionBox != null) {
                ParsableByteArray sampleEncryptionData = this.fragment.sampleEncryptionData;
                if (encryptionBox.perSampleIvSize != 0) {
                    sampleEncryptionData.skipBytes(encryptionBox.perSampleIvSize);
                }
                if (this.fragment.sampleHasSubsampleEncryptionTable(this.currentSampleIndex)) {
                    sampleEncryptionData.skipBytes(sampleEncryptionData.readUnsignedShort() * 6);
                }
            }
        }

        /* access modifiers changed from: private */
        public TrackEncryptionBox getEncryptionBoxIfEncrypted() {
            TrackEncryptionBox encryptionBox;
            int sampleDescriptionIndex = this.fragment.header.sampleDescriptionIndex;
            if (this.fragment.trackEncryptionBox != null) {
                encryptionBox = this.fragment.trackEncryptionBox;
            } else {
                encryptionBox = this.track.getSampleDescriptionEncryptionBox(sampleDescriptionIndex);
            }
            if (encryptionBox == null || !encryptionBox.isEncrypted) {
                return null;
            }
            return encryptionBox;
        }
    }
}
