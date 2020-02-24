package com.google.android.exoplayer2.extractor.mp4;

import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.mp4.Atom;
import com.google.android.exoplayer2.extractor.mp4.FixedSampleSizeRechunker;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class AtomParsers {
    private static final int MAX_GAPLESS_TRIM_SIZE_SAMPLES = 3;
    private static final String TAG = "AtomParsers";
    private static final int TYPE_clcp = Util.getIntegerCodeForString("clcp");
    private static final int TYPE_mdta = Util.getIntegerCodeForString("mdta");
    private static final int TYPE_meta = Util.getIntegerCodeForString("meta");
    private static final int TYPE_sbtl = Util.getIntegerCodeForString("sbtl");
    private static final int TYPE_soun = Util.getIntegerCodeForString("soun");
    private static final int TYPE_subt = Util.getIntegerCodeForString("subt");
    private static final int TYPE_text = Util.getIntegerCodeForString("text");
    private static final int TYPE_vide = Util.getIntegerCodeForString("vide");
    private static final byte[] opusMagic = Util.getUtf8Bytes("OpusHead");

    private interface SampleSizeBox {
        int getSampleCount();

        boolean isFixedSampleSize();

        int readNextSampleSize();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v16, resolved type: long[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: long[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.extractor.mp4.Track parseTrak(com.google.android.exoplayer2.extractor.mp4.Atom.ContainerAtom r31, com.google.android.exoplayer2.extractor.mp4.Atom.LeafAtom r32, long r33, com.google.android.exoplayer2.drm.DrmInitData r35, boolean r36, boolean r37) throws com.google.android.exoplayer2.ParserException {
        /*
            r0 = r31
            int r1 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_mdia
            com.google.android.exoplayer2.extractor.mp4.Atom$ContainerAtom r1 = r0.getContainerAtomOfType(r1)
            int r2 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_hdlr
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r2 = r1.getLeafAtomOfType(r2)
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r2.data
            int r2 = parseHdlr(r2)
            int r2 = getTrackTypeForHdlr(r2)
            r3 = 0
            r4 = -1
            if (r2 != r4) goto L_0x001d
            return r3
        L_0x001d:
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_tkhd
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r4 = r0.getLeafAtomOfType(r4)
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r4.data
            com.google.android.exoplayer2.extractor.mp4.AtomParsers$TkhdData r18 = parseTkhd(r4)
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r6 = (r33 > r4 ? 1 : (r33 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x0039
            long r6 = r18.duration
            r19 = r6
            goto L_0x003b
        L_0x0039:
            r19 = r33
        L_0x003b:
            r15 = r32
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r15.data
            long r21 = parseMvhd(r6)
            int r6 = (r19 > r4 ? 1 : (r19 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x004f
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r23 = r4
            goto L_0x005c
        L_0x004f:
            r10 = 1000000(0xf4240, double:4.940656E-318)
            r8 = r19
            r12 = r21
            long r4 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r8, r10, r12)
            r23 = r4
        L_0x005c:
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_minf
            com.google.android.exoplayer2.extractor.mp4.Atom$ContainerAtom r4 = r1.getContainerAtomOfType(r4)
            int r5 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_stbl
            com.google.android.exoplayer2.extractor.mp4.Atom$ContainerAtom r14 = r4.getContainerAtomOfType(r5)
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_mdhd
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r4 = r1.getLeafAtomOfType(r4)
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r4.data
            android.util.Pair r13 = parseMdhd(r4)
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_stsd
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r4 = r14.getLeafAtomOfType(r4)
            com.google.android.exoplayer2.util.ParsableByteArray r5 = r4.data
            int r6 = r18.f76id
            int r7 = r18.rotationDegrees
            java.lang.Object r4 = r13.second
            r8 = r4
            java.lang.String r8 = (java.lang.String) r8
            r9 = r35
            r10 = r37
            com.google.android.exoplayer2.extractor.mp4.AtomParsers$StsdData r12 = parseStsd(r5, r6, r7, r8, r9, r10)
            r4 = 0
            r5 = 0
            if (r36 != 0) goto L_0x00ae
            int r6 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_edts
            com.google.android.exoplayer2.extractor.mp4.Atom$ContainerAtom r6 = r0.getContainerAtomOfType(r6)
            android.util.Pair r6 = parseEdts(r6)
            java.lang.Object r7 = r6.first
            r4 = r7
            long[] r4 = (long[]) r4
            java.lang.Object r7 = r6.second
            r5 = r7
            long[] r5 = (long[]) r5
            r25 = r4
            r26 = r5
            goto L_0x00b2
        L_0x00ae:
            r25 = r4
            r26 = r5
        L_0x00b2:
            com.google.android.exoplayer2.Format r4 = r12.format
            if (r4 != 0) goto L_0x00bf
            r27 = r3
            r30 = r12
            r28 = r13
            r29 = r14
            goto L_0x00f9
        L_0x00bf:
            com.google.android.exoplayer2.extractor.mp4.Track r27 = new com.google.android.exoplayer2.extractor.mp4.Track
            int r4 = r18.f76id
            java.lang.Object r3 = r13.first
            java.lang.Long r3 = (java.lang.Long) r3
            long r6 = r3.longValue()
            com.google.android.exoplayer2.Format r10 = r12.format
            int r11 = r12.requiredSampleTransformation
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox[] r8 = r12.trackEncryptionBoxes
            int r9 = r12.nalUnitLengthFieldLength
            r3 = r27
            r5 = r2
            r16 = r8
            r17 = r9
            r8 = r21
            r28 = r10
            r29 = r11
            r10 = r23
            r30 = r12
            r12 = r28
            r28 = r13
            r13 = r29
            r29 = r14
            r14 = r16
            r15 = r17
            r16 = r25
            r17 = r26
            r3.<init>(r4, r5, r6, r8, r10, r12, r13, r14, r15, r16, r17)
        L_0x00f9:
            return r27
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.AtomParsers.parseTrak(com.google.android.exoplayer2.extractor.mp4.Atom$ContainerAtom, com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom, long, com.google.android.exoplayer2.drm.DrmInitData, boolean, boolean):com.google.android.exoplayer2.extractor.mp4.Track");
    }

    /* JADX INFO: Multiple debug info for r13v4 int[]: [D('sampleSizeBox' com.google.android.exoplayer2.extractor.mp4.AtomParsers$SampleSizeBox), D('flags' int[])] */
    /* JADX INFO: Multiple debug info for r10v4 int: [D('duration' long), D('sampleCount' int)] */
    /* JADX INFO: Multiple debug info for r10v5 int: [D('sampleIndex' int), D('sampleCount' int)] */
    /* JADX INFO: Multiple debug info for r5v7 int: [D('endIndex' int), D('editedSampleCount' int)] */
    /* JADX INFO: Multiple debug info for r6v10 int: [D('endIndices' int[]), D('count' int)] */
    /* JADX INFO: Multiple debug info for r15v8 long[]: [D('timestampOffset' int), D('offsets' long[])] */
    /* JADX INFO: Multiple debug info for r15v9 'sizes'  int[]: [D('sizes' int[]), D('offsets' long[])] */
    /* JADX INFO: Multiple debug info for r10v10 long: [D('editMediaTime' long), D('duration' long)] */
    /* JADX INFO: Multiple debug info for r38v10 int[]: [D('stts' com.google.android.exoplayer2.util.ParsableByteArray), D('flags' int[])] */
    /* JADX INFO: Multiple debug info for r2v24 int[]: [D('sizes' int[]), D('maximumSize' int)] */
    /* JADX INFO: Multiple debug info for r6v15 long[]: [D('timestamps' long[]), D('stsc' com.google.android.exoplayer2.util.ParsableByteArray)] */
    /* JADX INFO: Multiple debug info for r7v15 int[]: [D('chunkOffsetsAreLongs' boolean), D('flags' int[])] */
    /* JADX INFO: Multiple debug info for r20v3 long[]: [D('timestampOffset' int), D('offsets' long[])] */
    /* JADX INFO: Multiple debug info for r1v67 'remainingSamplesAtTimestampOffset'  int: [D('ctts' com.google.android.exoplayer2.util.ParsableByteArray), D('remainingSamplesAtTimestampOffset' int)] */
    /* JADX INFO: Multiple debug info for r14v29 long: [D('offset' long), D('sampleCount' int)] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchCeil(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int
     arg types: [long[], long, boolean, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchCeil(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int */
    public static TrackSampleTable parseStbl(Track track, Atom.ContainerAtom stblAtom, GaplessInfoHolder gaplessInfoHolder) throws ParserException {
        SampleSizeBox sampleSizeBox;
        Atom.LeafAtom chunkOffsetsAtom;
        boolean chunkOffsetsAreLongs;
        int nextSynchronizationSampleIndex;
        ParsableByteArray stss;
        ParsableByteArray ctts;
        ParsableByteArray stts;
        ParsableByteArray chunkOffsets;
        boolean chunkOffsetsAreLongs2;
        int timestampOffset;
        int timestampOffset2;
        int remainingSamplesAtTimestampDelta;
        long duration;
        int[] sizes;
        long[] offsets;
        long[] offsets2;
        int[] flags;
        long[] offsets3;
        int[] sizes2;
        int[] flags2;
        long[] timestamps;
        int[] sizes3;
        long[] offsets4;
        int[] flags3;
        long[] timestamps2;
        int timestampOffset3;
        int[] endIndices;
        int[] sizes4;
        long[] editedTimestamps;
        int sampleCount;
        SampleSizeBox sampleSizeBox2;
        int[] flags4;
        int remainingTimestampDeltaChanges;
        int[] sizes5;
        int remainingSamplesInChunk;
        long duration2;
        long[] timestamps3;
        int[] flags5;
        int remainingTimestampDeltaChanges2;
        int remainingSamplesAtTimestampOffset;
        int timestampOffset4;
        Track track2 = track;
        Atom.ContainerAtom containerAtom = stblAtom;
        Atom.LeafAtom stszAtom = containerAtom.getLeafAtomOfType(Atom.TYPE_stsz);
        if (stszAtom != null) {
            sampleSizeBox = new StszSampleSizeBox(stszAtom);
        } else {
            Atom.LeafAtom stz2Atom = containerAtom.getLeafAtomOfType(Atom.TYPE_stz2);
            if (stz2Atom != null) {
                sampleSizeBox = new Stz2SampleSizeBox(stz2Atom);
            } else {
                throw new ParserException("Track has no sample table size information");
            }
        }
        int sampleCount2 = sampleSizeBox.getSampleCount();
        if (sampleCount2 == 0) {
            return new TrackSampleTable(track, new long[0], new int[0], 0, new long[0], new int[0], C0841C.TIME_UNSET);
        }
        Atom.LeafAtom chunkOffsetsAtom2 = containerAtom.getLeafAtomOfType(Atom.TYPE_stco);
        if (chunkOffsetsAtom2 == null) {
            chunkOffsetsAreLongs = true;
            chunkOffsetsAtom = containerAtom.getLeafAtomOfType(Atom.TYPE_co64);
        } else {
            chunkOffsetsAreLongs = false;
            chunkOffsetsAtom = chunkOffsetsAtom2;
        }
        ParsableByteArray chunkOffsets2 = chunkOffsetsAtom.data;
        ParsableByteArray stsc = containerAtom.getLeafAtomOfType(Atom.TYPE_stsc).data;
        ParsableByteArray stts2 = containerAtom.getLeafAtomOfType(Atom.TYPE_stts).data;
        Atom.LeafAtom stssAtom = containerAtom.getLeafAtomOfType(Atom.TYPE_stss);
        ParsableByteArray ctts2 = null;
        ParsableByteArray stss2 = stssAtom != null ? stssAtom.data : null;
        Atom.LeafAtom cttsAtom = containerAtom.getLeafAtomOfType(Atom.TYPE_ctts);
        if (cttsAtom != null) {
            ctts2 = cttsAtom.data;
        }
        ChunkIterator chunkIterator = new ChunkIterator(stsc, chunkOffsets2, chunkOffsetsAreLongs);
        stts2.setPosition(12);
        int remainingTimestampDeltaChanges3 = stts2.readUnsignedIntToInt() - 1;
        int remainingSamplesAtTimestampDelta2 = stts2.readUnsignedIntToInt();
        int timestampDeltaInTimeUnits = stts2.readUnsignedIntToInt();
        int remainingTimestampOffsetChanges = 0;
        if (ctts2 != null) {
            ctts2.setPosition(12);
            remainingTimestampOffsetChanges = ctts2.readUnsignedIntToInt();
        }
        int remainingSynchronizationSamples = 0;
        if (stss2 != null) {
            nextSynchronizationSampleIndex = -1;
            stss2.setPosition(12);
            remainingSynchronizationSamples = stss2.readUnsignedIntToInt();
            if (remainingSynchronizationSamples > 0) {
                stss = stss2;
                nextSynchronizationSampleIndex = stss2.readUnsignedIntToInt() - 1;
            } else {
                stss = null;
            }
        } else {
            nextSynchronizationSampleIndex = -1;
            stss = stss2;
        }
        long timestampTimeUnits = 0;
        if (!(sampleSizeBox.isFixedSampleSize() && MimeTypes.AUDIO_RAW.equals(track2.format.sampleMimeType) && remainingTimestampDeltaChanges3 == 0 && remainingTimestampOffsetChanges == 0 && remainingSynchronizationSamples == 0)) {
            long[] offsets5 = new long[sampleCount2];
            int[] sizes6 = new int[sampleCount2];
            long[] timestamps4 = new long[sampleCount2];
            chunkOffsetsAreLongs2 = chunkOffsetsAreLongs;
            int[] flags6 = new int[sampleCount2];
            int remainingSamplesInChunk2 = 0;
            int remainingSynchronizationSamples2 = remainingSynchronizationSamples;
            int nextSynchronizationSampleIndex2 = nextSynchronizationSampleIndex;
            int maximumSize = 0;
            long offset = 0;
            chunkOffsets = chunkOffsets2;
            int remainingSamplesAtTimestampDelta3 = remainingSamplesAtTimestampDelta2;
            int timestampOffset5 = 0;
            int timestampDeltaInTimeUnits2 = timestampDeltaInTimeUnits;
            int i = 0;
            int remainingTimestampDeltaChanges4 = remainingTimestampDeltaChanges3;
            int remainingSamplesAtTimestampOffset2 = 0;
            while (true) {
                stts = stts2;
                if (i >= sampleCount2) {
                    remainingTimestampDeltaChanges = remainingTimestampDeltaChanges4;
                    sizes5 = sizes6;
                    remainingSamplesInChunk = remainingSamplesInChunk2;
                    break;
                }
                boolean chunkDataComplete = true;
                while (remainingSamplesInChunk2 == 0) {
                    boolean moveNext = chunkIterator.moveNext();
                    chunkDataComplete = moveNext;
                    if (!moveNext) {
                        break;
                    }
                    offset = chunkIterator.offset;
                    remainingSamplesInChunk2 = chunkIterator.numSamples;
                    remainingTimestampDeltaChanges4 = remainingTimestampDeltaChanges4;
                    sampleCount2 = sampleCount2;
                }
                int sampleCount3 = sampleCount2;
                remainingTimestampDeltaChanges = remainingTimestampDeltaChanges4;
                if (!chunkDataComplete) {
                    Log.m30w(TAG, "Unexpected end of chunk data");
                    sampleCount2 = i;
                    offsets5 = Arrays.copyOf(offsets5, sampleCount2);
                    int[] sizes7 = Arrays.copyOf(sizes6, sampleCount2);
                    timestamps4 = Arrays.copyOf(timestamps4, sampleCount2);
                    flags6 = Arrays.copyOf(flags6, sampleCount2);
                    sizes5 = sizes7;
                    remainingSamplesInChunk = remainingSamplesInChunk2;
                    break;
                }
                if (ctts2 != null) {
                    while (remainingSamplesAtTimestampOffset2 == 0 && remainingTimestampOffsetChanges > 0) {
                        remainingSamplesAtTimestampOffset2 = ctts2.readUnsignedIntToInt();
                        timestampOffset5 = ctts2.readInt();
                        remainingTimestampOffsetChanges--;
                    }
                    remainingSamplesAtTimestampOffset2--;
                    timestampOffset4 = timestampOffset5;
                } else {
                    timestampOffset4 = timestampOffset5;
                }
                offsets5[i] = offset;
                sizes6[i] = sampleSizeBox.readNextSampleSize();
                if (sizes6[i] > maximumSize) {
                    maximumSize = sizes6[i];
                }
                timestamps4[i] = timestampTimeUnits + ((long) timestampOffset4);
                flags6[i] = stss == null ? 1 : 0;
                if (i == nextSynchronizationSampleIndex2) {
                    flags6[i] = 1;
                    remainingSynchronizationSamples2--;
                    if (remainingSynchronizationSamples2 > 0) {
                        nextSynchronizationSampleIndex2 = stss.readUnsignedIntToInt() - 1;
                    }
                }
                timestampTimeUnits += (long) timestampDeltaInTimeUnits2;
                remainingSamplesAtTimestampDelta3--;
                if (remainingSamplesAtTimestampDelta3 != 0 || remainingTimestampDeltaChanges <= 0) {
                    remainingTimestampDeltaChanges4 = remainingTimestampDeltaChanges;
                } else {
                    remainingSamplesAtTimestampDelta3 = stts.readUnsignedIntToInt();
                    timestampDeltaInTimeUnits2 = stts.readInt();
                    remainingTimestampDeltaChanges4 = remainingTimestampDeltaChanges - 1;
                }
                timestampOffset5 = timestampOffset4;
                offset += (long) sizes6[i];
                remainingSamplesInChunk2--;
                i++;
                stts2 = stts;
                sampleCount2 = sampleCount3;
                offsets5 = offsets5;
            }
            int[] sizes8 = sizes5;
            timestampOffset2 = timestampOffset5;
            long[] offsets6 = offsets5;
            long duration3 = timestampTimeUnits + ((long) timestampOffset2);
            boolean isCttsValid = true;
            while (true) {
                if (remainingTimestampOffsetChanges <= 0) {
                    break;
                } else if (ctts2.readUnsignedIntToInt() != 0) {
                    isCttsValid = false;
                    break;
                } else {
                    ctts2.readInt();
                    remainingTimestampOffsetChanges--;
                }
            }
            if (remainingSynchronizationSamples2 == 0 && remainingSamplesAtTimestampDelta3 == 0 && remainingSamplesInChunk == 0 && remainingTimestampDeltaChanges == 0 && remainingSamplesAtTimestampOffset2 == 0 && isCttsValid) {
                ctts = ctts2;
                duration2 = duration3;
                timestamps3 = timestamps4;
                remainingSamplesAtTimestampOffset = remainingSamplesAtTimestampOffset2;
                remainingTimestampDeltaChanges2 = remainingTimestampDeltaChanges;
                track2 = track;
                flags5 = flags6;
            } else {
                ctts = ctts2;
                duration2 = duration3;
                remainingSamplesAtTimestampOffset = remainingSamplesAtTimestampOffset2;
                track2 = track;
                int i2 = track2.f77id;
                String str = !isCttsValid ? ", ctts invalid" : "";
                timestamps3 = timestamps4;
                flags5 = flags6;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + ClientAnalytics.LogRequest.LogSource.INBOX_IOS_PRIMES_VALUE);
                sb.append("Inconsistent stbl box for track ");
                sb.append(i2);
                sb.append(": remainingSynchronizationSamples ");
                sb.append(remainingSynchronizationSamples2);
                sb.append(", remainingSamplesAtTimestampDelta ");
                sb.append(remainingSamplesAtTimestampDelta3);
                sb.append(", remainingSamplesInChunk ");
                sb.append(remainingSamplesInChunk);
                sb.append(", remainingTimestampDeltaChanges ");
                remainingTimestampDeltaChanges2 = remainingTimestampDeltaChanges;
                sb.append(remainingTimestampDeltaChanges2);
                sb.append(", remainingSamplesAtTimestampOffset ");
                sb.append(remainingSamplesAtTimestampOffset);
                sb.append(str);
                Log.m30w(TAG, sb.toString());
            }
            timestampOffset = maximumSize;
            flags = flags5;
            offsets = offsets6;
            offsets2 = timestamps3;
            duration = duration2;
            remainingSamplesAtTimestampDelta = timestampDeltaInTimeUnits2;
            sizes = sizes8;
        } else {
            ctts = ctts2;
            stts = stts2;
            chunkOffsetsAreLongs2 = chunkOffsetsAreLongs;
            chunkOffsets = chunkOffsets2;
            int sampleCount4 = sampleCount2;
            long[] chunkOffsetsBytes = new long[chunkIterator.length];
            int[] chunkSampleCounts = new int[chunkIterator.length];
            while (chunkIterator.moveNext()) {
                chunkOffsetsBytes[chunkIterator.index] = chunkIterator.offset;
                chunkSampleCounts[chunkIterator.index] = chunkIterator.numSamples;
            }
            FixedSampleSizeRechunker.Results rechunkedResults = FixedSampleSizeRechunker.rechunk(Util.getPcmFrameSize(track2.format.pcmEncoding, track2.format.channelCount), chunkOffsetsBytes, chunkSampleCounts, (long) timestampDeltaInTimeUnits);
            long[] offsets7 = rechunkedResults.offsets;
            int[] sizes9 = rechunkedResults.sizes;
            int maximumSize2 = rechunkedResults.maximumSize;
            long[] timestamps5 = rechunkedResults.timestamps;
            int[] flags7 = rechunkedResults.flags;
            long j = rechunkedResults.duration;
            remainingSamplesAtTimestampDelta = timestampDeltaInTimeUnits;
            flags = flags7;
            timestampOffset2 = 0;
            sampleCount2 = sampleCount4;
            timestampOffset = maximumSize2;
            sizes = sizes9;
            offsets = offsets7;
            offsets2 = timestamps5;
            duration = j;
        }
        long durationUs = Util.scaleLargeTimestamp(duration, 1000000, track2.timescale);
        if (track2.editListDurations == null) {
            offsets3 = offsets;
            flags2 = flags;
            timestamps = offsets2;
            sizes2 = sizes;
        } else if (gaplessInfoHolder.hasGaplessInfo()) {
            offsets3 = offsets;
            flags2 = flags;
            timestamps = offsets2;
            sizes2 = sizes;
        } else {
            if (track2.editListDurations.length == 1 && track2.type == 1 && offsets2.length >= 2) {
                long editStartTime = track2.editListMediaTimes[0];
                long editEndTime = editStartTime + Util.scaleLargeTimestamp(track2.editListDurations[0], track2.timescale, track2.movieTimescale);
                if (canApplyEditWithGaplessInfo(offsets2, duration, editStartTime, editEndTime)) {
                    long encoderDelay = Util.scaleLargeTimestamp(editStartTime - offsets2[0], (long) track2.format.sampleRate, track2.timescale);
                    int[] flags8 = flags;
                    long encoderPadding = Util.scaleLargeTimestamp(duration - editEndTime, (long) track2.format.sampleRate, track2.timescale);
                    if (encoderDelay == 0 && encoderPadding == 0) {
                        offsets4 = offsets;
                        sizes3 = sizes;
                        timestamps2 = offsets2;
                        flags3 = flags8;
                    } else if (encoderDelay > 2147483647L || encoderPadding > 2147483647L) {
                        offsets4 = offsets;
                        sizes3 = sizes;
                        timestamps2 = offsets2;
                        flags3 = flags8;
                    } else {
                        GaplessInfoHolder gaplessInfoHolder2 = gaplessInfoHolder;
                        gaplessInfoHolder2.encoderDelay = (int) encoderDelay;
                        gaplessInfoHolder2.encoderPadding = (int) encoderPadding;
                        Util.scaleLargeTimestampsInPlace(offsets2, 1000000, track2.timescale);
                        return new TrackSampleTable(track, offsets, sizes, timestampOffset, offsets2, flags8, Util.scaleLargeTimestamp(track2.editListDurations[0], 1000000, track2.movieTimescale));
                    }
                } else {
                    offsets4 = offsets;
                    sizes3 = sizes;
                    flags3 = flags;
                    timestamps2 = offsets2;
                }
            } else {
                offsets4 = offsets;
                sizes3 = sizes;
                flags3 = flags;
                timestamps2 = offsets2;
            }
            if (track2.editListDurations.length == 1 && track2.editListDurations[0] == 0) {
                long editStartTime2 = track2.editListMediaTimes[0];
                for (int i3 = 0; i3 < timestamps2.length; i3++) {
                    timestamps2[i3] = Util.scaleLargeTimestamp(timestamps2[i3] - editStartTime2, 1000000, track2.timescale);
                }
                return new TrackSampleTable(track, offsets4, sizes3, timestampOffset, timestamps2, flags3, Util.scaleLargeTimestamp(duration - editStartTime2, 1000000, track2.timescale));
            }
            boolean omitClippedSample = track2.type == 1;
            boolean copyMetadata = false;
            int[] startIndices = new int[track2.editListDurations.length];
            int[] endIndices2 = new int[track2.editListDurations.length];
            int i4 = 0;
            int editedSampleCount = 0;
            int nextSampleIndex = 0;
            while (i4 < track2.editListDurations.length) {
                long duration4 = duration;
                long duration5 = track2.editListMediaTimes[i4];
                if (duration5 != -1) {
                    sampleSizeBox2 = sampleSizeBox;
                    sampleCount = sampleCount2;
                    long editDuration = Util.scaleLargeTimestamp(track2.editListDurations[i4], track2.timescale, track2.movieTimescale);
                    startIndices[i4] = Util.binarySearchCeil(timestamps2, duration5, true, true);
                    endIndices2[i4] = Util.binarySearchCeil(timestamps2, duration5 + editDuration, omitClippedSample, false);
                    while (true) {
                        if (startIndices[i4] >= endIndices2[i4]) {
                            flags4 = flags3;
                            break;
                        }
                        flags4 = flags3;
                        if ((flags4[startIndices[i4]] & 1) != 0) {
                            break;
                        }
                        startIndices[i4] = startIndices[i4] + 1;
                        flags3 = flags4;
                    }
                    editedSampleCount += endIndices2[i4] - startIndices[i4];
                    boolean z = nextSampleIndex != startIndices[i4];
                    nextSampleIndex = endIndices2[i4];
                    copyMetadata = z | copyMetadata;
                } else {
                    sampleSizeBox2 = sampleSizeBox;
                    sampleCount = sampleCount2;
                    flags4 = flags3;
                }
                i4++;
                flags3 = flags4;
                duration = duration4;
                sampleSizeBox = sampleSizeBox2;
                sampleCount2 = sampleCount;
            }
            int sampleCount5 = sampleCount2;
            int[] flags9 = flags3;
            int editedMaximumSize = 0;
            boolean sampleCount6 = true;
            if (editedSampleCount == sampleCount5) {
                sampleCount6 = false;
            }
            boolean copyMetadata2 = copyMetadata | sampleCount6;
            long[] editedOffsets = copyMetadata2 ? new long[editedSampleCount] : offsets4;
            int[] editedSizes = copyMetadata2 ? new int[editedSampleCount] : sizes3;
            if (!copyMetadata2) {
                editedMaximumSize = timestampOffset;
            }
            int[] editedFlags = copyMetadata2 ? new int[editedSampleCount] : flags9;
            long[] editedTimestamps2 = new long[editedSampleCount];
            long pts = 0;
            int i5 = 0;
            int sampleIndex = editedMaximumSize;
            int i6 = 0;
            while (true) {
                int sampleCount7 = i5;
                int nextSampleIndex2 = nextSampleIndex;
                if (i6 < track2.editListDurations.length) {
                    long editMediaTime = track2.editListMediaTimes[i6];
                    int startIndex = startIndices[i6];
                    int editedSampleCount2 = editedSampleCount;
                    int endIndex = endIndices2[i6];
                    if (copyMetadata2) {
                        endIndices = endIndices2;
                        int count = endIndex - startIndex;
                        timestampOffset3 = timestampOffset2;
                        System.arraycopy(offsets4, startIndex, editedOffsets, sampleCount7, count);
                        sizes4 = sizes3;
                        System.arraycopy(sizes4, startIndex, editedSizes, sampleCount7, count);
                        System.arraycopy(flags9, startIndex, editedFlags, sampleCount7, count);
                    } else {
                        endIndices = endIndices2;
                        timestampOffset3 = timestampOffset2;
                        sizes4 = sizes3;
                    }
                    int j2 = startIndex;
                    int i7 = sampleIndex;
                    int sampleIndex2 = sampleCount7;
                    int editedMaximumSize2 = i7;
                    while (j2 < endIndex) {
                        int startIndex2 = startIndex;
                        int endIndex2 = endIndex;
                        boolean omitClippedSample2 = omitClippedSample;
                        int[] startIndices2 = startIndices;
                        editedTimestamps2[sampleIndex2] = Util.scaleLargeTimestamp(pts, 1000000, track2.movieTimescale) + Util.scaleLargeTimestamp(timestamps2[j2] - editMediaTime, 1000000, track2.timescale);
                        if (copyMetadata2) {
                            editedTimestamps = editedTimestamps2;
                            if (editedSizes[sampleIndex2] > editedMaximumSize2) {
                                editedMaximumSize2 = sizes4[j2];
                            }
                        } else {
                            editedTimestamps = editedTimestamps2;
                        }
                        sampleIndex2++;
                        j2++;
                        startIndex = startIndex2;
                        endIndex = endIndex2;
                        editedTimestamps2 = editedTimestamps;
                        omitClippedSample = omitClippedSample2;
                        startIndices = startIndices2;
                    }
                    pts += track2.editListDurations[i6];
                    i6++;
                    sizes3 = sizes4;
                    nextSampleIndex = nextSampleIndex2;
                    editedSampleCount = editedSampleCount2;
                    endIndices2 = endIndices;
                    timestampOffset2 = timestampOffset3;
                    editedTimestamps2 = editedTimestamps2;
                    i5 = sampleIndex2;
                    sampleIndex = editedMaximumSize2;
                } else {
                    return new TrackSampleTable(track, editedOffsets, editedSizes, sampleIndex, editedTimestamps2, editedFlags, Util.scaleLargeTimestamp(pts, 1000000, track2.movieTimescale));
                }
            }
        }
        Util.scaleLargeTimestampsInPlace(timestamps, 1000000, track2.timescale);
        return new TrackSampleTable(track, offsets3, sizes2, timestampOffset, timestamps, flags2, durationUs);
    }

    @Nullable
    public static Metadata parseUdta(Atom.LeafAtom udtaAtom, boolean isQuickTime) {
        if (isQuickTime) {
            return null;
        }
        ParsableByteArray udtaData = udtaAtom.data;
        udtaData.setPosition(8);
        while (udtaData.bytesLeft() >= 8) {
            int atomPosition = udtaData.getPosition();
            int atomSize = udtaData.readInt();
            if (udtaData.readInt() == Atom.TYPE_meta) {
                udtaData.setPosition(atomPosition);
                return parseUdtaMeta(udtaData, atomPosition + atomSize);
            }
            udtaData.setPosition(atomPosition + atomSize);
        }
        return null;
    }

    /* JADX INFO: Multiple debug info for r7v2 com.google.android.exoplayer2.util.ParsableByteArray: [D('ilst' com.google.android.exoplayer2.util.ParsableByteArray), D('i' int)] */
    @Nullable
    public static Metadata parseMdtaFromMeta(Atom.ContainerAtom meta) {
        Atom.LeafAtom hdlrAtom = meta.getLeafAtomOfType(Atom.TYPE_hdlr);
        Atom.LeafAtom keysAtom = meta.getLeafAtomOfType(Atom.TYPE_keys);
        Atom.LeafAtom ilstAtom = meta.getLeafAtomOfType(Atom.TYPE_ilst);
        if (hdlrAtom == null || keysAtom == null || ilstAtom == null || parseHdlr(hdlrAtom.data) != TYPE_mdta) {
            return null;
        }
        ParsableByteArray keys = keysAtom.data;
        keys.setPosition(12);
        int entryCount = keys.readInt();
        String[] keyNames = new String[entryCount];
        for (int i = 0; i < entryCount; i++) {
            int entrySize = keys.readInt();
            keys.skipBytes(4);
            keyNames[i] = keys.readString(entrySize - 8);
        }
        ParsableByteArray ilst = ilstAtom.data;
        ilst.setPosition(8);
        ArrayList<Metadata.Entry> entries = new ArrayList<>();
        while (ilst.bytesLeft() > 8) {
            int atomPosition = ilst.getPosition();
            int atomSize = ilst.readInt();
            int keyIndex = ilst.readInt() - 1;
            if (keyIndex < 0 || keyIndex >= keyNames.length) {
                StringBuilder sb = new StringBuilder(52);
                sb.append("Skipped metadata with unknown key index: ");
                sb.append(keyIndex);
                Log.m30w(TAG, sb.toString());
            } else {
                Metadata.Entry entry = MetadataUtil.parseMdtaMetadataEntryFromIlst(ilst, atomPosition + atomSize, keyNames[keyIndex]);
                if (entry != null) {
                    entries.add(entry);
                }
            }
            ilst.setPosition(atomPosition + atomSize);
        }
        if (entries.isEmpty()) {
            return null;
        }
        return new Metadata(entries);
    }

    @Nullable
    private static Metadata parseUdtaMeta(ParsableByteArray meta, int limit) {
        meta.skipBytes(12);
        while (meta.getPosition() < limit) {
            int atomPosition = meta.getPosition();
            int atomSize = meta.readInt();
            if (meta.readInt() == Atom.TYPE_ilst) {
                meta.setPosition(atomPosition);
                return parseIlst(meta, atomPosition + atomSize);
            }
            meta.setPosition(atomPosition + atomSize);
        }
        return null;
    }

    @Nullable
    private static Metadata parseIlst(ParsableByteArray ilst, int limit) {
        ilst.skipBytes(8);
        ArrayList<Metadata.Entry> entries = new ArrayList<>();
        while (ilst.getPosition() < limit) {
            Metadata.Entry entry = MetadataUtil.parseIlstElement(ilst);
            if (entry != null) {
                entries.add(entry);
            }
        }
        if (entries.isEmpty()) {
            return null;
        }
        return new Metadata(entries);
    }

    private static long parseMvhd(ParsableByteArray mvhd) {
        int i = 8;
        mvhd.setPosition(8);
        if (Atom.parseFullAtomVersion(mvhd.readInt()) != 0) {
            i = 16;
        }
        mvhd.skipBytes(i);
        return mvhd.readUnsignedInt();
    }

    private static TkhdData parseTkhd(ParsableByteArray tkhd) {
        long duration;
        int rotationDegrees;
        int durationByteCount = 8;
        tkhd.setPosition(8);
        int version = Atom.parseFullAtomVersion(tkhd.readInt());
        tkhd.skipBytes(version == 0 ? 8 : 16);
        int trackId = tkhd.readInt();
        tkhd.skipBytes(4);
        boolean durationUnknown = true;
        int durationPosition = tkhd.getPosition();
        if (version == 0) {
            durationByteCount = 4;
        }
        int i = 0;
        while (true) {
            if (i >= durationByteCount) {
                break;
            } else if (tkhd.data[durationPosition + i] != -1) {
                durationUnknown = false;
                break;
            } else {
                i++;
            }
        }
        if (durationUnknown) {
            tkhd.skipBytes(durationByteCount);
            duration = C0841C.TIME_UNSET;
        } else {
            duration = version == 0 ? tkhd.readUnsignedInt() : tkhd.readUnsignedLongToLong();
            if (duration == 0) {
                duration = C0841C.TIME_UNSET;
            }
        }
        tkhd.skipBytes(16);
        int a00 = tkhd.readInt();
        int a01 = tkhd.readInt();
        tkhd.skipBytes(4);
        int a10 = tkhd.readInt();
        int a11 = tkhd.readInt();
        if (a00 == 0 && a01 == 65536 && a10 == (-65536) && a11 == 0) {
            rotationDegrees = 90;
        } else if (a00 == 0 && a01 == (-65536) && a10 == 65536 && a11 == 0) {
            rotationDegrees = ClientAnalytics.LogRequest.LogSource.CLINICAL_STUDIES_VALUE;
        } else if (a00 == (-65536) && a01 == 0 && a10 == 0 && a11 == (-65536)) {
            rotationDegrees = ClientAnalytics.LogRequest.LogSource.INBOX_ANDROID_PRIMES_VALUE;
        } else {
            rotationDegrees = 0;
        }
        return new TkhdData(trackId, duration, rotationDegrees);
    }

    private static int parseHdlr(ParsableByteArray hdlr) {
        hdlr.setPosition(16);
        return hdlr.readInt();
    }

    private static int getTrackTypeForHdlr(int hdlr) {
        if (hdlr == TYPE_soun) {
            return 1;
        }
        if (hdlr == TYPE_vide) {
            return 2;
        }
        if (hdlr == TYPE_text || hdlr == TYPE_sbtl || hdlr == TYPE_subt || hdlr == TYPE_clcp) {
            return 3;
        }
        if (hdlr == TYPE_meta) {
            return 4;
        }
        return -1;
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray mdhd) {
        int i = 8;
        mdhd.setPosition(8);
        int version = Atom.parseFullAtomVersion(mdhd.readInt());
        mdhd.skipBytes(version == 0 ? 8 : 16);
        long timescale = mdhd.readUnsignedInt();
        if (version == 0) {
            i = 4;
        }
        mdhd.skipBytes(i);
        int languageCode = mdhd.readUnsignedShort();
        StringBuilder sb = new StringBuilder(3);
        sb.append((char) (((languageCode >> 10) & 31) + 96));
        sb.append((char) (((languageCode >> 5) & 31) + 96));
        sb.append((char) ((languageCode & 31) + 96));
        return Pair.create(Long.valueOf(timescale), sb.toString());
    }

    private static StsdData parseStsd(ParsableByteArray stsd, int trackId, int rotationDegrees, String language, DrmInitData drmInitData, boolean isQuickTime) throws ParserException {
        int childAtomType;
        ParsableByteArray parsableByteArray = stsd;
        parsableByteArray.setPosition(12);
        int numberOfEntries = stsd.readInt();
        StsdData out = new StsdData(numberOfEntries);
        for (int i = 0; i < numberOfEntries; i++) {
            int childStartPosition = stsd.getPosition();
            int childAtomSize = stsd.readInt();
            Assertions.checkArgument(childAtomSize > 0, "childAtomSize should be positive");
            int childAtomType2 = stsd.readInt();
            if (childAtomType2 == Atom.TYPE_avc1 || childAtomType2 == Atom.TYPE_avc3 || childAtomType2 == Atom.TYPE_encv || childAtomType2 == Atom.TYPE_mp4v || childAtomType2 == Atom.TYPE_hvc1 || childAtomType2 == Atom.TYPE_hev1 || childAtomType2 == Atom.TYPE_s263 || childAtomType2 == Atom.TYPE_vp08 || childAtomType2 == Atom.TYPE_vp09 || childAtomType2 == Atom.TYPE_av01 || childAtomType2 == Atom.TYPE_dvav || childAtomType2 == Atom.TYPE_dva1 || childAtomType2 == Atom.TYPE_dvhe) {
                childAtomType = childAtomType2;
            } else if (childAtomType2 == Atom.TYPE_dvh1) {
                childAtomType = childAtomType2;
            } else if (childAtomType2 == Atom.TYPE_mp4a || childAtomType2 == Atom.TYPE_enca || childAtomType2 == Atom.TYPE_ac_3 || childAtomType2 == Atom.TYPE_ec_3 || childAtomType2 == Atom.TYPE_ac_4 || childAtomType2 == Atom.TYPE_dtsc || childAtomType2 == Atom.TYPE_dtse || childAtomType2 == Atom.TYPE_dtsh || childAtomType2 == Atom.TYPE_dtsl || childAtomType2 == Atom.TYPE_samr || childAtomType2 == Atom.TYPE_sawb || childAtomType2 == Atom.TYPE_lpcm || childAtomType2 == Atom.TYPE_sowt || childAtomType2 == Atom.TYPE__mp3 || childAtomType2 == Atom.TYPE_alac || childAtomType2 == Atom.TYPE_alaw || childAtomType2 == Atom.TYPE_ulaw || childAtomType2 == Atom.TYPE_Opus || childAtomType2 == Atom.TYPE_fLaC) {
                parseAudioSampleEntry(stsd, childAtomType2, childStartPosition, childAtomSize, trackId, language, isQuickTime, drmInitData, out, i);
                parsableByteArray.setPosition(childStartPosition + childAtomSize);
            } else if (childAtomType2 == Atom.TYPE_TTML || childAtomType2 == Atom.TYPE_tx3g || childAtomType2 == Atom.TYPE_wvtt || childAtomType2 == Atom.TYPE_stpp || childAtomType2 == Atom.TYPE_c608) {
                parseTextSampleEntry(stsd, childAtomType2, childStartPosition, childAtomSize, trackId, language, out);
                parsableByteArray.setPosition(childStartPosition + childAtomSize);
            } else {
                if (childAtomType2 == Atom.TYPE_camm) {
                    out.format = Format.createSampleFormat(Integer.toString(trackId), MimeTypes.APPLICATION_CAMERA_MOTION, null, -1, null);
                }
                parsableByteArray.setPosition(childStartPosition + childAtomSize);
            }
            parseVideoSampleEntry(stsd, childAtomType, childStartPosition, childAtomSize, trackId, rotationDegrees, drmInitData, out, i);
            parsableByteArray.setPosition(childStartPosition + childAtomSize);
        }
        return out;
    }

    private static void parseTextSampleEntry(ParsableByteArray parent, int atomType, int position, int atomSize, int trackId, String language, StsdData out) throws ParserException {
        String mimeType;
        ParsableByteArray parsableByteArray = parent;
        int i = atomType;
        StsdData stsdData = out;
        parsableByteArray.setPosition(position + 8 + 8);
        List<byte[]> initializationData = null;
        long subsampleOffsetUs = Long.MAX_VALUE;
        if (i == Atom.TYPE_TTML) {
            mimeType = MimeTypes.APPLICATION_TTML;
        } else if (i == Atom.TYPE_tx3g) {
            mimeType = MimeTypes.APPLICATION_TX3G;
            int sampleDescriptionLength = (atomSize - 8) - 8;
            byte[] sampleDescriptionData = new byte[sampleDescriptionLength];
            parsableByteArray.readBytes(sampleDescriptionData, 0, sampleDescriptionLength);
            initializationData = Collections.singletonList(sampleDescriptionData);
        } else if (i == Atom.TYPE_wvtt) {
            mimeType = MimeTypes.APPLICATION_MP4VTT;
        } else if (i == Atom.TYPE_stpp) {
            mimeType = MimeTypes.APPLICATION_TTML;
            subsampleOffsetUs = 0;
        } else if (i == Atom.TYPE_c608) {
            mimeType = MimeTypes.APPLICATION_MP4CEA608;
            stsdData.requiredSampleTransformation = 1;
        } else {
            throw new IllegalStateException();
        }
        stsdData.format = Format.createTextSampleFormat(Integer.toString(trackId), mimeType, null, -1, 0, language, -1, null, subsampleOffsetUs, initializationData);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r26v4, resolved type: java.lang.String} */
    /* JADX INFO: Multiple debug info for r8v4 com.google.android.exoplayer2.video.DolbyVisionConfig: [D('version' int), D('dolbyVisionConfig' com.google.android.exoplayer2.video.DolbyVisionConfig)] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void parseVideoSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray r31, int r32, int r33, int r34, int r35, int r36, com.google.android.exoplayer2.drm.DrmInitData r37, com.google.android.exoplayer2.extractor.mp4.AtomParsers.StsdData r38, int r39) throws com.google.android.exoplayer2.ParserException {
        /*
            r0 = r31
            r1 = r33
            r2 = r34
            r3 = r37
            r4 = r38
            int r5 = r1 + 8
            int r5 = r5 + 8
            r0.setPosition(r5)
            r5 = 16
            r0.skipBytes(r5)
            int r5 = r31.readUnsignedShort()
            int r21 = r31.readUnsignedShort()
            r6 = 0
            r7 = 1065353216(0x3f800000, float:1.0)
            r8 = 50
            r0.skipBytes(r8)
            int r8 = r31.getPosition()
            int r9 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_encv
            r10 = r32
            if (r10 != r9) goto L_0x005a
            android.util.Pair r9 = parseSampleEntryEncryptionData(r0, r1, r2)
            if (r9 == 0) goto L_0x0055
            java.lang.Object r11 = r9.first
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r10 = r11.intValue()
            if (r3 != 0) goto L_0x0042
            r11 = 0
            goto L_0x004c
        L_0x0042:
            java.lang.Object r11 = r9.second
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r11 = (com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox) r11
            java.lang.String r11 = r11.schemeType
            com.google.android.exoplayer2.drm.DrmInitData r11 = r3.copyWithSchemeType(r11)
        L_0x004c:
            r3 = r11
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox[] r11 = r4.trackEncryptionBoxes
            java.lang.Object r12 = r9.second
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r12 = (com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox) r12
            r11[r39] = r12
        L_0x0055:
            r0.setPosition(r8)
            r15 = r10
            goto L_0x005b
        L_0x005a:
            r15 = r10
        L_0x005b:
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = -1
            r22 = r6
            r23 = r7
            r14 = r8
            r25 = r9
            r26 = r10
            r27 = r11
            r28 = r12
            r24 = r13
        L_0x006f:
            int r6 = r14 - r1
            if (r6 >= r2) goto L_0x018c
            r0.setPosition(r14)
            int r6 = r31.getPosition()
            int r7 = r31.readInt()
            if (r7 != 0) goto L_0x0089
            int r8 = r31.getPosition()
            int r8 = r8 - r1
            if (r8 != r2) goto L_0x0089
            goto L_0x018c
        L_0x0089:
            r8 = 0
            r9 = 1
            if (r7 <= 0) goto L_0x008f
            r10 = 1
            goto L_0x0090
        L_0x008f:
            r10 = 0
        L_0x0090:
            java.lang.String r11 = "childAtomSize should be positive"
            com.google.android.exoplayer2.util.Assertions.checkArgument(r10, r11)
            int r10 = r31.readInt()
            int r11 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_avcC
            if (r10 != r11) goto L_0x00bf
            if (r26 != 0) goto L_0x00a0
            r8 = 1
        L_0x00a0:
            com.google.android.exoplayer2.util.Assertions.checkState(r8)
            java.lang.String r26 = "video/avc"
            int r8 = r6 + 8
            r0.setPosition(r8)
            com.google.android.exoplayer2.video.AvcConfig r8 = com.google.android.exoplayer2.video.AvcConfig.parse(r31)
            java.util.List<byte[]> r9 = r8.initializationData
            int r11 = r8.nalUnitLengthFieldLength
            r4.nalUnitLengthFieldLength = r11
            if (r22 != 0) goto L_0x00bb
            float r11 = r8.pixelWidthAspectRatio
            r23 = r11
        L_0x00bb:
            r25 = r9
            goto L_0x0189
        L_0x00bf:
            int r11 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_hvcC
            if (r10 != r11) goto L_0x00df
            if (r26 != 0) goto L_0x00c6
            r8 = 1
        L_0x00c6:
            com.google.android.exoplayer2.util.Assertions.checkState(r8)
            java.lang.String r26 = "video/hevc"
            int r8 = r6 + 8
            r0.setPosition(r8)
            com.google.android.exoplayer2.video.HevcConfig r8 = com.google.android.exoplayer2.video.HevcConfig.parse(r31)
            java.util.List<byte[]> r9 = r8.initializationData
            int r11 = r8.nalUnitLengthFieldLength
            r4.nalUnitLengthFieldLength = r11
            r25 = r9
            goto L_0x0189
        L_0x00df:
            int r11 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dvcC
            if (r10 == r11) goto L_0x017b
            int r11 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dvvC
            if (r10 != r11) goto L_0x00e9
            goto L_0x017b
        L_0x00e9:
            int r11 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_vpcC
            if (r10 != r11) goto L_0x0102
            if (r26 != 0) goto L_0x00f0
            r8 = 1
        L_0x00f0:
            com.google.android.exoplayer2.util.Assertions.checkState(r8)
            int r8 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_vp08
            if (r15 != r8) goto L_0x00fb
            java.lang.String r8 = "video/x-vnd.on2.vp8"
            goto L_0x00fe
        L_0x00fb:
            java.lang.String r8 = "video/x-vnd.on2.vp9"
        L_0x00fe:
            r26 = r8
            goto L_0x0189
        L_0x0102:
            int r11 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_av1C
            if (r10 != r11) goto L_0x0111
            if (r26 != 0) goto L_0x0109
            r8 = 1
        L_0x0109:
            com.google.android.exoplayer2.util.Assertions.checkState(r8)
            java.lang.String r26 = "video/av01"
            goto L_0x0189
        L_0x0111:
            int r11 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_d263
            if (r10 != r11) goto L_0x0120
            if (r26 != 0) goto L_0x0118
            r8 = 1
        L_0x0118:
            com.google.android.exoplayer2.util.Assertions.checkState(r8)
            java.lang.String r26 = "video/3gpp"
            goto L_0x0189
        L_0x0120:
            int r11 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_esds
            if (r10 != r11) goto L_0x013e
            if (r26 != 0) goto L_0x0127
            r8 = 1
        L_0x0127:
            com.google.android.exoplayer2.util.Assertions.checkState(r8)
            android.util.Pair r8 = parseEsdsFromParent(r0, r6)
            java.lang.Object r9 = r8.first
            r26 = r9
            java.lang.String r26 = (java.lang.String) r26
            java.lang.Object r9 = r8.second
            byte[] r9 = (byte[]) r9
            java.util.List r25 = java.util.Collections.singletonList(r9)
            goto L_0x0189
        L_0x013e:
            int r8 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_pasp
            if (r10 != r8) goto L_0x014a
            float r23 = parsePaspFromParent(r0, r6)
            r8 = 1
            r22 = r8
            goto L_0x0189
        L_0x014a:
            int r8 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_sv3d
            if (r10 != r8) goto L_0x0153
            byte[] r28 = parseProjFromParent(r0, r6, r7)
            goto L_0x0189
        L_0x0153:
            int r8 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_st3d
            if (r10 != r8) goto L_0x0189
            int r8 = r31.readUnsignedByte()
            r11 = 3
            r0.skipBytes(r11)
            if (r8 != 0) goto L_0x0189
            int r12 = r31.readUnsignedByte()
            if (r12 == 0) goto L_0x0178
            if (r12 == r9) goto L_0x0175
            r9 = 2
            if (r12 == r9) goto L_0x0172
            if (r12 == r11) goto L_0x016f
            goto L_0x0189
        L_0x016f:
            r24 = 3
            goto L_0x0189
        L_0x0172:
            r24 = 2
            goto L_0x0189
        L_0x0175:
            r24 = 1
            goto L_0x0189
        L_0x0178:
            r24 = 0
            goto L_0x0189
        L_0x017b:
            com.google.android.exoplayer2.video.DolbyVisionConfig r8 = com.google.android.exoplayer2.video.DolbyVisionConfig.parse(r31)
            if (r8 == 0) goto L_0x0188
            java.lang.String r9 = r8.codecs
            java.lang.String r26 = "video/dolby-vision"
            r27 = r9
        L_0x0188:
        L_0x0189:
            int r14 = r14 + r7
            goto L_0x006f
        L_0x018c:
            if (r26 != 0) goto L_0x018f
            return
        L_0x018f:
            java.lang.String r6 = java.lang.Integer.toString(r35)
            r9 = -1
            r10 = -1
            r13 = -1082130432(0xffffffffbf800000, float:-1.0)
            r19 = 0
            r7 = r26
            r8 = r27
            r11 = r5
            r12 = r21
            r29 = r14
            r14 = r25
            r30 = r15
            r15 = r36
            r16 = r23
            r17 = r28
            r18 = r24
            r20 = r3
            com.google.android.exoplayer2.Format r6 = com.google.android.exoplayer2.Format.createVideoSampleFormat(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            r4.format = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.AtomParsers.parseVideoSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray, int, int, int, int, int, com.google.android.exoplayer2.drm.DrmInitData, com.google.android.exoplayer2.extractor.mp4.AtomParsers$StsdData, int):void");
    }

    private static Pair<long[], long[]> parseEdts(Atom.ContainerAtom edtsAtom) {
        if (edtsAtom != null) {
            Atom.LeafAtom leafAtomOfType = edtsAtom.getLeafAtomOfType(Atom.TYPE_elst);
            Atom.LeafAtom elst = leafAtomOfType;
            if (leafAtomOfType != null) {
                ParsableByteArray elstData = elst.data;
                elstData.setPosition(8);
                int version = Atom.parseFullAtomVersion(elstData.readInt());
                int entryCount = elstData.readUnsignedIntToInt();
                long[] editListDurations = new long[entryCount];
                long[] editListMediaTimes = new long[entryCount];
                int i = 0;
                while (i < entryCount) {
                    editListDurations[i] = version == 1 ? elstData.readUnsignedLongToLong() : elstData.readUnsignedInt();
                    editListMediaTimes[i] = version == 1 ? elstData.readLong() : (long) elstData.readInt();
                    if (elstData.readShort() == 1) {
                        elstData.skipBytes(2);
                        i++;
                    } else {
                        throw new IllegalArgumentException("Unsupported media rate.");
                    }
                }
                return Pair.create(editListDurations, editListMediaTimes);
            }
        }
        return Pair.create(null, null);
    }

    private static float parsePaspFromParent(ParsableByteArray parent, int position) {
        parent.setPosition(position + 8);
        return ((float) parent.readUnsignedIntToInt()) / ((float) parent.readUnsignedIntToInt());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v3, resolved type: byte[]} */
    /* JADX INFO: Multiple debug info for r5v1 int: [D('childPosition' int), D('channelCount' int)] */
    /* JADX INFO: Multiple debug info for r13v2 int: [D('quickTimeSoundDescriptionVersion' int), D('childPosition' int)] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void parseAudioSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray r29, int r30, int r31, int r32, int r33, java.lang.String r34, boolean r35, com.google.android.exoplayer2.drm.DrmInitData r36, com.google.android.exoplayer2.extractor.mp4.AtomParsers.StsdData r37, int r38) throws com.google.android.exoplayer2.ParserException {
        /*
            r0 = r29
            r1 = r31
            r2 = r32
            r15 = r34
            r3 = r36
            r14 = r37
            int r4 = r1 + 8
            r5 = 8
            int r4 = r4 + r5
            r0.setPosition(r4)
            r4 = 0
            r6 = 6
            if (r35 == 0) goto L_0x0021
            int r4 = r29.readUnsignedShort()
            r0.skipBytes(r6)
            r13 = r4
            goto L_0x0025
        L_0x0021:
            r0.skipBytes(r5)
            r13 = r4
        L_0x0025:
            r12 = 2
            r4 = 16
            r11 = 1
            if (r13 == 0) goto L_0x0048
            if (r13 != r11) goto L_0x002e
            goto L_0x0048
        L_0x002e:
            if (r13 != r12) goto L_0x0047
            r0.skipBytes(r4)
            double r4 = r29.readDouble()
            long r4 = java.lang.Math.round(r4)
            int r5 = (int) r4
            int r4 = r29.readUnsignedIntToInt()
            r6 = 20
            r0.skipBytes(r6)
            r6 = r5
            goto L_0x0059
        L_0x0047:
            return
        L_0x0048:
            int r5 = r29.readUnsignedShort()
            r0.skipBytes(r6)
            int r6 = r29.readUnsignedFixedPoint1616()
            if (r13 != r11) goto L_0x0058
            r0.skipBytes(r4)
        L_0x0058:
            r4 = r5
        L_0x0059:
            int r5 = r29.getPosition()
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_enca
            r16 = 0
            r8 = r30
            if (r8 != r7) goto L_0x0091
            android.util.Pair r7 = parseSampleEntryEncryptionData(r0, r1, r2)
            if (r7 == 0) goto L_0x008b
            java.lang.Object r9 = r7.first
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r8 = r9.intValue()
            if (r3 != 0) goto L_0x0078
            r9 = r16
            goto L_0x0082
        L_0x0078:
            java.lang.Object r9 = r7.second
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r9 = (com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox) r9
            java.lang.String r9 = r9.schemeType
            com.google.android.exoplayer2.drm.DrmInitData r9 = r3.copyWithSchemeType(r9)
        L_0x0082:
            r3 = r9
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox[] r9 = r14.trackEncryptionBoxes
            java.lang.Object r10 = r7.second
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r10 = (com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox) r10
            r9[r38] = r10
        L_0x008b:
            r0.setPosition(r5)
            r9 = r3
            r10 = r8
            goto L_0x0093
        L_0x0091:
            r9 = r3
            r10 = r8
        L_0x0093:
            r3 = 0
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_ac_3
            if (r10 != r7) goto L_0x009c
            java.lang.String r3 = "audio/ac3"
            goto L_0x0109
        L_0x009c:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_ec_3
            if (r10 != r7) goto L_0x00a4
            java.lang.String r3 = "audio/eac3"
            goto L_0x0109
        L_0x00a4:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_ac_4
            if (r10 != r7) goto L_0x00ac
            java.lang.String r3 = "audio/ac4"
            goto L_0x0109
        L_0x00ac:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dtsc
            if (r10 != r7) goto L_0x00b3
            java.lang.String r3 = "audio/vnd.dts"
            goto L_0x0109
        L_0x00b3:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dtsh
            if (r10 == r7) goto L_0x0107
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dtsl
            if (r10 != r7) goto L_0x00bc
            goto L_0x0107
        L_0x00bc:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dtse
            if (r10 != r7) goto L_0x00c3
            java.lang.String r3 = "audio/vnd.dts.hd;profile=lbr"
            goto L_0x0109
        L_0x00c3:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_samr
            if (r10 != r7) goto L_0x00ca
            java.lang.String r3 = "audio/3gpp"
            goto L_0x0109
        L_0x00ca:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_sawb
            if (r10 != r7) goto L_0x00d1
            java.lang.String r3 = "audio/amr-wb"
            goto L_0x0109
        L_0x00d1:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_lpcm
            if (r10 == r7) goto L_0x0104
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_sowt
            if (r10 != r7) goto L_0x00da
            goto L_0x0104
        L_0x00da:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE__mp3
            if (r10 != r7) goto L_0x00e1
            java.lang.String r3 = "audio/mpeg"
            goto L_0x0109
        L_0x00e1:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_alac
            if (r10 != r7) goto L_0x00e8
            java.lang.String r3 = "audio/alac"
            goto L_0x0109
        L_0x00e8:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_alaw
            if (r10 != r7) goto L_0x00ef
            java.lang.String r3 = "audio/g711-alaw"
            goto L_0x0109
        L_0x00ef:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_ulaw
            if (r10 != r7) goto L_0x00f6
            java.lang.String r3 = "audio/g711-mlaw"
            goto L_0x0109
        L_0x00f6:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_Opus
            if (r10 != r7) goto L_0x00fd
            java.lang.String r3 = "audio/opus"
            goto L_0x0109
        L_0x00fd:
            int r7 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_fLaC
            if (r10 != r7) goto L_0x0109
            java.lang.String r3 = "audio/flac"
            goto L_0x0109
        L_0x0104:
            java.lang.String r3 = "audio/raw"
            goto L_0x0109
        L_0x0107:
            java.lang.String r3 = "audio/vnd.dts.hd"
        L_0x0109:
            r7 = 0
            r17 = r4
            r8 = r5
            r18 = r6
            r19 = r7
            r7 = r3
        L_0x0112:
            int r3 = r8 - r1
            r4 = -1
            if (r3 >= r2) goto L_0x02b1
            r0.setPosition(r8)
            int r6 = r29.readInt()
            r3 = 0
            if (r6 <= 0) goto L_0x0123
            r5 = 1
            goto L_0x0124
        L_0x0123:
            r5 = 0
        L_0x0124:
            java.lang.String r11 = "childAtomSize should be positive"
            com.google.android.exoplayer2.util.Assertions.checkArgument(r5, r11)
            int r11 = r29.readInt()
            int r5 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_esds
            if (r11 == r5) goto L_0x025a
            if (r35 == 0) goto L_0x0147
            int r5 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_wave
            if (r11 != r5) goto L_0x0147
            r26 = r7
            r21 = r9
            r22 = r10
            r5 = r11
            r24 = r13
            r20 = 1
            r23 = 2
            r13 = r8
            goto L_0x0268
        L_0x0147:
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dac3
            if (r11 != r4) goto L_0x016a
            int r3 = r8 + 8
            r0.setPosition(r3)
            java.lang.String r3 = java.lang.Integer.toString(r33)
            com.google.android.exoplayer2.Format r3 = com.google.android.exoplayer2.audio.Ac3Util.parseAc3AnnexFFormat(r0, r3, r15, r9)
            r14.format = r3
            r26 = r7
            r21 = r9
            r22 = r10
            r5 = r11
            r24 = r13
            r20 = 1
            r23 = 2
            r13 = r8
            goto L_0x0257
        L_0x016a:
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dec3
            if (r11 != r4) goto L_0x018d
            int r3 = r8 + 8
            r0.setPosition(r3)
            java.lang.String r3 = java.lang.Integer.toString(r33)
            com.google.android.exoplayer2.Format r3 = com.google.android.exoplayer2.audio.Ac3Util.parseEAc3AnnexFFormat(r0, r3, r15, r9)
            r14.format = r3
            r26 = r7
            r21 = r9
            r22 = r10
            r5 = r11
            r24 = r13
            r20 = 1
            r23 = 2
            r13 = r8
            goto L_0x0257
        L_0x018d:
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dac4
            if (r11 != r4) goto L_0x01b1
            int r3 = r8 + 8
            r0.setPosition(r3)
            java.lang.String r3 = java.lang.Integer.toString(r33)
            com.google.android.exoplayer2.Format r3 = com.google.android.exoplayer2.audio.Ac4Util.parseAc4AnnexEFormat(r0, r3, r15, r9)
            r14.format = r3
            r26 = r7
            r21 = r9
            r22 = r10
            r5 = r11
            r24 = r13
            r20 = 1
            r23 = 2
            r13 = r8
            goto L_0x0257
        L_0x01b1:
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_ddts
            if (r11 != r4) goto L_0x01f3
            java.lang.String r3 = java.lang.Integer.toString(r33)
            r5 = 0
            r21 = -1
            r22 = -1
            r23 = 0
            r24 = 0
            r4 = r7
            r25 = r6
            r6 = r21
            r26 = r7
            r7 = r22
            r27 = r8
            r8 = r17
            r21 = r9
            r9 = r18
            r22 = r10
            r10 = r23
            r28 = r11
            r20 = 1
            r11 = r21
            r23 = 2
            r12 = r24
            r24 = r13
            r13 = r34
            com.google.android.exoplayer2.Format r3 = com.google.android.exoplayer2.Format.createAudioSampleFormat(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r14.format = r3
            r6 = r25
            r13 = r27
            r5 = r28
            goto L_0x0257
        L_0x01f3:
            r25 = r6
            r26 = r7
            r27 = r8
            r21 = r9
            r22 = r10
            r28 = r11
            r24 = r13
            r20 = 1
            r23 = 2
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_alac
            r5 = r28
            if (r5 != r4) goto L_0x021d
            r6 = r25
            byte[] r4 = new byte[r6]
            r13 = r27
            r0.setPosition(r13)
            r0.readBytes(r4, r3, r6)
            r19 = r4
            r7 = r26
            goto L_0x02a5
        L_0x021d:
            r6 = r25
            r13 = r27
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dOps
            if (r5 != r4) goto L_0x0242
            int r4 = r6 + -8
            byte[] r7 = com.google.android.exoplayer2.extractor.mp4.AtomParsers.opusMagic
            int r8 = r7.length
            int r8 = r8 + r4
            byte[] r8 = new byte[r8]
            int r9 = r7.length
            java.lang.System.arraycopy(r7, r3, r8, r3, r9)
            int r3 = r13 + 8
            r0.setPosition(r3)
            byte[] r3 = com.google.android.exoplayer2.extractor.mp4.AtomParsers.opusMagic
            int r3 = r3.length
            r0.readBytes(r8, r3, r4)
            r19 = r8
            r7 = r26
            goto L_0x02a5
        L_0x0242:
            int r4 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_dfLa
            if (r6 != r4) goto L_0x0257
            int r4 = r6 + -12
            byte[] r7 = new byte[r4]
            int r8 = r13 + 12
            r0.setPosition(r8)
            r0.readBytes(r7, r3, r4)
            r19 = r7
            r7 = r26
            goto L_0x02a5
        L_0x0257:
            r7 = r26
            goto L_0x02a5
        L_0x025a:
            r26 = r7
            r21 = r9
            r22 = r10
            r5 = r11
            r24 = r13
            r20 = 1
            r23 = 2
            r13 = r8
        L_0x0268:
            int r3 = com.google.android.exoplayer2.extractor.mp4.Atom.TYPE_esds
            if (r5 != r3) goto L_0x026e
            r8 = r13
            goto L_0x0272
        L_0x026e:
            int r8 = findEsdsPosition(r0, r13, r6)
        L_0x0272:
            r3 = r8
            if (r3 == r4) goto L_0x02a2
            android.util.Pair r4 = parseEsdsFromParent(r0, r3)
            java.lang.Object r7 = r4.first
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r8 = r4.second
            r19 = r8
            byte[] r19 = (byte[]) r19
            java.lang.String r8 = "audio/mp4a-latm"
            boolean r8 = r8.equals(r7)
            if (r8 == 0) goto L_0x02a4
            android.util.Pair r8 = com.google.android.exoplayer2.util.CodecSpecificDataUtil.parseAacAudioSpecificConfig(r19)
            java.lang.Object r9 = r8.first
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r18 = r9.intValue()
            java.lang.Object r9 = r8.second
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r17 = r9.intValue()
            goto L_0x02a4
        L_0x02a2:
            r7 = r26
        L_0x02a4:
        L_0x02a5:
            int r8 = r13 + r6
            r9 = r21
            r10 = r22
            r13 = r24
            r11 = 1
            r12 = 2
            goto L_0x0112
        L_0x02b1:
            r26 = r7
            r21 = r9
            r22 = r10
            r24 = r13
            r23 = 2
            r13 = r8
            com.google.android.exoplayer2.Format r3 = r14.format
            if (r3 != 0) goto L_0x02ff
            r12 = r26
            if (r12 == 0) goto L_0x02f9
            java.lang.String r3 = "audio/raw"
            boolean r3 = r3.equals(r12)
            if (r3 == 0) goto L_0x02ce
            r10 = 2
            goto L_0x02cf
        L_0x02ce:
            r10 = -1
        L_0x02cf:
            java.lang.String r3 = java.lang.Integer.toString(r33)
            r5 = 0
            r6 = -1
            r7 = -1
            if (r19 != 0) goto L_0x02db
            r11 = r16
            goto L_0x02e0
        L_0x02db:
            java.util.List r4 = java.util.Collections.singletonList(r19)
            r11 = r4
        L_0x02e0:
            r16 = 0
            r4 = r12
            r8 = r17
            r9 = r18
            r20 = r12
            r12 = r21
            r23 = r13
            r13 = r16
            r0 = r14
            r14 = r34
            com.google.android.exoplayer2.Format r3 = com.google.android.exoplayer2.Format.createAudioSampleFormat(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            r0.format = r3
            goto L_0x0304
        L_0x02f9:
            r20 = r12
            r23 = r13
            r0 = r14
            goto L_0x0304
        L_0x02ff:
            r23 = r13
            r0 = r14
            r20 = r26
        L_0x0304:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.AtomParsers.parseAudioSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray, int, int, int, int, java.lang.String, boolean, com.google.android.exoplayer2.drm.DrmInitData, com.google.android.exoplayer2.extractor.mp4.AtomParsers$StsdData, int):void");
    }

    private static int findEsdsPosition(ParsableByteArray parent, int position, int size) {
        int childAtomPosition = parent.getPosition();
        while (childAtomPosition - position < size) {
            parent.setPosition(childAtomPosition);
            int childAtomSize = parent.readInt();
            Assertions.checkArgument(childAtomSize > 0, "childAtomSize should be positive");
            if (parent.readInt() == Atom.TYPE_esds) {
                return childAtomPosition;
            }
            childAtomPosition += childAtomSize;
        }
        return -1;
    }

    private static Pair<String, byte[]> parseEsdsFromParent(ParsableByteArray parent, int position) {
        parent.setPosition(position + 8 + 4);
        parent.skipBytes(1);
        parseExpandableClassSize(parent);
        parent.skipBytes(2);
        int flags = parent.readUnsignedByte();
        if ((flags & 128) != 0) {
            parent.skipBytes(2);
        }
        if ((flags & 64) != 0) {
            parent.skipBytes(parent.readUnsignedShort());
        }
        if ((flags & 32) != 0) {
            parent.skipBytes(2);
        }
        parent.skipBytes(1);
        parseExpandableClassSize(parent);
        String mimeType = MimeTypes.getMimeTypeFromMp4ObjectType(parent.readUnsignedByte());
        if (MimeTypes.AUDIO_MPEG.equals(mimeType) || MimeTypes.AUDIO_DTS.equals(mimeType) || MimeTypes.AUDIO_DTS_HD.equals(mimeType)) {
            return Pair.create(mimeType, null);
        }
        parent.skipBytes(12);
        parent.skipBytes(1);
        int initializationDataSize = parseExpandableClassSize(parent);
        byte[] initializationData = new byte[initializationDataSize];
        parent.readBytes(initializationData, 0, initializationDataSize);
        return Pair.create(mimeType, initializationData);
    }

    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parent, int position, int size) {
        Pair<Integer, TrackEncryptionBox> result;
        int childPosition = parent.getPosition();
        while (childPosition - position < size) {
            parent.setPosition(childPosition);
            int childAtomSize = parent.readInt();
            Assertions.checkArgument(childAtomSize > 0, "childAtomSize should be positive");
            if (parent.readInt() == Atom.TYPE_sinf && (result = parseCommonEncryptionSinfFromParent(parent, childPosition, childAtomSize)) != null) {
                return result;
            }
            childPosition += childAtomSize;
        }
        return null;
    }

    static Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent(ParsableByteArray parent, int position, int size) {
        int childPosition = position + 8;
        int schemeInformationBoxPosition = -1;
        int schemeInformationBoxSize = 0;
        String schemeType = null;
        Integer dataFormat = null;
        while (childPosition - position < size) {
            parent.setPosition(childPosition);
            int childAtomSize = parent.readInt();
            int childAtomType = parent.readInt();
            if (childAtomType == Atom.TYPE_frma) {
                dataFormat = Integer.valueOf(parent.readInt());
            } else if (childAtomType == Atom.TYPE_schm) {
                parent.skipBytes(4);
                schemeType = parent.readString(4);
            } else if (childAtomType == Atom.TYPE_schi) {
                schemeInformationBoxPosition = childPosition;
                schemeInformationBoxSize = childAtomSize;
            }
            childPosition += childAtomSize;
        }
        if (!C0841C.CENC_TYPE_cenc.equals(schemeType) && !C0841C.CENC_TYPE_cbc1.equals(schemeType) && !C0841C.CENC_TYPE_cens.equals(schemeType) && !C0841C.CENC_TYPE_cbcs.equals(schemeType)) {
            return null;
        }
        boolean z = true;
        Assertions.checkArgument(dataFormat != null, "frma atom is mandatory");
        Assertions.checkArgument(schemeInformationBoxPosition != -1, "schi atom is mandatory");
        TrackEncryptionBox encryptionBox = parseSchiFromParent(parent, schemeInformationBoxPosition, schemeInformationBoxSize, schemeType);
        if (encryptionBox == null) {
            z = false;
        }
        Assertions.checkArgument(z, "tenc atom is mandatory");
        return Pair.create(dataFormat, encryptionBox);
    }

    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parent, int position, int size, String schemeType) {
        byte[] constantIv;
        ParsableByteArray parsableByteArray = parent;
        int childPosition = position + 8;
        while (childPosition - position < size) {
            parsableByteArray.setPosition(childPosition);
            int childAtomSize = parent.readInt();
            if (parent.readInt() == Atom.TYPE_tenc) {
                int version = Atom.parseFullAtomVersion(parent.readInt());
                boolean defaultIsProtected = true;
                parsableByteArray.skipBytes(1);
                int defaultCryptByteBlock = 0;
                int defaultSkipByteBlock = 0;
                if (version == 0) {
                    parsableByteArray.skipBytes(1);
                } else {
                    int patternByte = parent.readUnsignedByte();
                    defaultCryptByteBlock = (patternByte & 240) >> 4;
                    defaultSkipByteBlock = patternByte & 15;
                }
                if (parent.readUnsignedByte() != 1) {
                    defaultIsProtected = false;
                }
                int defaultPerSampleIvSize = parent.readUnsignedByte();
                byte[] defaultKeyId = new byte[16];
                parsableByteArray.readBytes(defaultKeyId, 0, defaultKeyId.length);
                if (!defaultIsProtected || defaultPerSampleIvSize != 0) {
                    constantIv = null;
                } else {
                    int constantIvSize = parent.readUnsignedByte();
                    byte[] constantIv2 = new byte[constantIvSize];
                    parsableByteArray.readBytes(constantIv2, 0, constantIvSize);
                    constantIv = constantIv2;
                }
                return new TrackEncryptionBox(defaultIsProtected, schemeType, defaultPerSampleIvSize, defaultKeyId, defaultCryptByteBlock, defaultSkipByteBlock, constantIv);
            }
            childPosition += childAtomSize;
        }
        return null;
    }

    private static byte[] parseProjFromParent(ParsableByteArray parent, int position, int size) {
        int childPosition = position + 8;
        while (childPosition - position < size) {
            parent.setPosition(childPosition);
            int childAtomSize = parent.readInt();
            if (parent.readInt() == Atom.TYPE_proj) {
                return Arrays.copyOfRange(parent.data, childPosition, childPosition + childAtomSize);
            }
            childPosition += childAtomSize;
        }
        return null;
    }

    private static int parseExpandableClassSize(ParsableByteArray data) {
        int currentByte = data.readUnsignedByte();
        int size = currentByte & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
        while ((currentByte & 128) == 128) {
            currentByte = data.readUnsignedByte();
            size = (size << 7) | (currentByte & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE);
        }
        return size;
    }

    private static boolean canApplyEditWithGaplessInfo(long[] timestamps, long duration, long editStartTime, long editEndTime) {
        int lastIndex = timestamps.length - 1;
        int latestDelayIndex = Util.constrainValue(3, 0, lastIndex);
        int earliestPaddingIndex = Util.constrainValue(timestamps.length - 3, 0, lastIndex);
        if (timestamps[0] > editStartTime || editStartTime >= timestamps[latestDelayIndex] || timestamps[earliestPaddingIndex] >= editEndTime || editEndTime > duration) {
            return false;
        }
        return true;
    }

    private AtomParsers() {
    }

    private static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray stsc2, ParsableByteArray chunkOffsets2, boolean chunkOffsetsAreLongs2) {
            this.stsc = stsc2;
            this.chunkOffsets = chunkOffsets2;
            this.chunkOffsetsAreLongs = chunkOffsetsAreLongs2;
            chunkOffsets2.setPosition(12);
            this.length = chunkOffsets2.readUnsignedIntToInt();
            stsc2.setPosition(12);
            this.remainingSamplesPerChunkChanges = stsc2.readUnsignedIntToInt();
            Assertions.checkState(stsc2.readInt() != 1 ? false : true, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            long j;
            int i = this.index + 1;
            this.index = i;
            if (i == this.length) {
                return false;
            }
            if (this.chunkOffsetsAreLongs) {
                j = this.chunkOffsets.readUnsignedLongToLong();
            } else {
                j = this.chunkOffsets.readUnsignedInt();
            }
            this.offset = j;
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                int i2 = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i2;
                this.nextSamplesPerChunkChangeIndex = i2 > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    private static final class TkhdData {
        /* access modifiers changed from: private */
        public final long duration;
        /* access modifiers changed from: private */

        /* renamed from: id */
        public final int f76id;
        /* access modifiers changed from: private */
        public final int rotationDegrees;

        public TkhdData(int id, long duration2, int rotationDegrees2) {
            this.f76id = id;
            this.duration = duration2;
            this.rotationDegrees = rotationDegrees2;
        }
    }

    private static final class StsdData {
        public static final int STSD_HEADER_SIZE = 8;
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int numberOfEntries) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[numberOfEntries];
        }
    }

    static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize = this.data.readUnsignedIntToInt();
        private final int sampleCount = this.data.readUnsignedIntToInt();

        public StszSampleSizeBox(Atom.LeafAtom stszAtom) {
            this.data = stszAtom.data;
            this.data.setPosition(12);
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public int readNextSampleSize() {
            int i = this.fixedSampleSize;
            return i == 0 ? this.data.readUnsignedIntToInt() : i;
        }

        public boolean isFixedSampleSize() {
            return this.fixedSampleSize != 0;
        }
    }

    static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize = (this.data.readUnsignedIntToInt() & 255);
        private final int sampleCount = this.data.readUnsignedIntToInt();
        private int sampleIndex;

        public Stz2SampleSizeBox(Atom.LeafAtom stz2Atom) {
            this.data = stz2Atom.data;
            this.data.setPosition(12);
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public int readNextSampleSize() {
            int i = this.fieldSize;
            if (i == 8) {
                return this.data.readUnsignedByte();
            }
            if (i == 16) {
                return this.data.readUnsignedShort();
            }
            int i2 = this.sampleIndex;
            this.sampleIndex = i2 + 1;
            if (i2 % 2 != 0) {
                return this.currentByte & 15;
            }
            this.currentByte = this.data.readUnsignedByte();
            return (this.currentByte & 240) >> 4;
        }

        public boolean isFixedSampleSize() {
            return false;
        }
    }
}
