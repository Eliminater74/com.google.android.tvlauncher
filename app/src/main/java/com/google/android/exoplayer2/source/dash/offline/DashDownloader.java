package com.google.android.exoplayer2.source.dash.offline;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.dash.DashSegmentIndex;
import com.google.android.exoplayer2.source.dash.DashUtil;
import com.google.android.exoplayer2.source.dash.DashWrappingSegmentIndex;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class DashDownloader extends SegmentDownloader<DashManifest> {
    public DashDownloader(Uri manifestUri, List<StreamKey> streamKeys, DownloaderConstructorHelper constructorHelper) {
        super(manifestUri, streamKeys, constructorHelper);
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0090 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x008a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void addSegmentsForAdaptationSet(com.google.android.exoplayer2.upstream.DataSource r23, com.google.android.exoplayer2.source.dash.manifest.AdaptationSet r24, long r25, long r27, boolean r29, java.util.ArrayList<com.google.android.exoplayer2.offline.SegmentDownloader.Segment> r30) throws java.io.IOException, java.lang.InterruptedException {
        /*
            r1 = r24
            r2 = r25
            r4 = r30
            r0 = 0
            r5 = r0
        L_0x0008:
            java.util.List<com.google.android.exoplayer2.source.dash.manifest.Representation> r0 = r1.representations
            int r0 = r0.size()
            if (r5 >= r0) goto L_0x0091
            java.util.List<com.google.android.exoplayer2.source.dash.manifest.Representation> r0 = r1.representations
            java.lang.Object r0 = r0.get(r5)
            r6 = r0
            com.google.android.exoplayer2.source.dash.manifest.Representation r6 = (com.google.android.exoplayer2.source.dash.manifest.Representation) r6
            int r0 = r1.type     // Catch:{ IOException -> 0x0084 }
            r7 = r23
            com.google.android.exoplayer2.source.dash.DashSegmentIndex r0 = getSegmentIndex(r7, r0, r6)     // Catch:{ IOException -> 0x0084 }
            if (r0 == 0) goto L_0x0078
            r8 = r27
            int r10 = r0.getSegmentCount(r8)
            r11 = -1
            if (r10 == r11) goto L_0x006e
            java.lang.String r11 = r6.baseUrl
            com.google.android.exoplayer2.source.dash.manifest.RangedUri r12 = r6.getInitializationUri()
            if (r12 == 0) goto L_0x0038
            addSegment(r2, r11, r12, r4)
        L_0x0038:
            com.google.android.exoplayer2.source.dash.manifest.RangedUri r13 = r6.getIndexUri()
            if (r13 == 0) goto L_0x0041
            addSegment(r2, r11, r13, r4)
        L_0x0041:
            long r14 = r0.getFirstSegmentNum()
            r16 = r6
            long r6 = (long) r10
            long r6 = r6 + r14
            r17 = 1
            long r6 = r6 - r17
            r19 = r14
            r8 = r19
        L_0x0051:
            int r19 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r19 > 0) goto L_0x006b
            long r19 = r0.getTimeUs(r8)
            r21 = r6
            long r6 = r2 + r19
            com.google.android.exoplayer2.source.dash.manifest.RangedUri r1 = r0.getSegmentUrl(r8)
            addSegment(r6, r11, r1, r4)
            long r8 = r8 + r17
            r1 = r24
            r6 = r21
            goto L_0x0051
        L_0x006b:
            r21 = r6
            goto L_0x008a
        L_0x006e:
            r16 = r6
            com.google.android.exoplayer2.offline.DownloadException r1 = new com.google.android.exoplayer2.offline.DownloadException
            java.lang.String r6 = "Unbounded segment index"
            r1.<init>(r6)
            throw r1
        L_0x0078:
            r16 = r6
            com.google.android.exoplayer2.offline.DownloadException r1 = new com.google.android.exoplayer2.offline.DownloadException     // Catch:{ IOException -> 0x0082 }
            java.lang.String r6 = "Missing segment index"
            r1.<init>(r6)     // Catch:{ IOException -> 0x0082 }
            throw r1     // Catch:{ IOException -> 0x0082 }
        L_0x0082:
            r0 = move-exception
            goto L_0x0087
        L_0x0084:
            r0 = move-exception
            r16 = r6
        L_0x0087:
            if (r29 == 0) goto L_0x0090
        L_0x008a:
            int r5 = r5 + 1
            r1 = r24
            goto L_0x0008
        L_0x0090:
            throw r0
        L_0x0091:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.offline.DashDownloader.addSegmentsForAdaptationSet(com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.source.dash.manifest.AdaptationSet, long, long, boolean, java.util.ArrayList):void");
    }

    private static void addSegment(long startTimeUs, String baseUrl, RangedUri rangedUri, ArrayList<SegmentDownloader.Segment> out) {
        out.add(new SegmentDownloader.Segment(startTimeUs, new DataSpec(rangedUri.resolveUri(baseUrl), rangedUri.start, rangedUri.length, null)));
    }

    @Nullable
    private static DashSegmentIndex getSegmentIndex(DataSource dataSource, int trackType, Representation representation) throws IOException, InterruptedException {
        DashSegmentIndex index = representation.getIndex();
        if (index != null) {
            return index;
        }
        ChunkIndex seekMap = DashUtil.loadChunkIndex(dataSource, trackType, representation);
        if (seekMap == null) {
            return null;
        }
        return new DashWrappingSegmentIndex(seekMap, representation.presentationTimeOffsetUs);
    }

    /* access modifiers changed from: protected */
    public DashManifest getManifest(DataSource dataSource, DataSpec dataSpec) throws IOException {
        return (DashManifest) ParsingLoadable.load(dataSource, new DashManifestParser(), dataSpec, 4);
    }

    /* access modifiers changed from: protected */
    public List<SegmentDownloader.Segment> getSegments(DataSource dataSource, DashManifest manifest, boolean allowIncompleteList) throws InterruptedException, IOException {
        DashManifest dashManifest = manifest;
        ArrayList<SegmentDownloader.Segment> segments = new ArrayList<>();
        for (int i = 0; i < manifest.getPeriodCount(); i++) {
            Period period = dashManifest.getPeriod(i);
            long periodStartUs = C0841C.msToUs(period.startMs);
            long periodDurationUs = dashManifest.getPeriodDurationUs(i);
            int j = 0;
            for (List<AdaptationSet> adaptationSets = period.adaptationSets; j < adaptationSets.size(); adaptationSets = adaptationSets) {
                addSegmentsForAdaptationSet(dataSource, adaptationSets.get(j), periodStartUs, periodDurationUs, allowIncompleteList, segments);
                j++;
            }
        }
        return segments;
    }
}
