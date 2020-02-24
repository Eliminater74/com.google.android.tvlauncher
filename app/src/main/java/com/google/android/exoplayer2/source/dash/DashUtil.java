package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.List;

public final class DashUtil {
    public static DashManifest loadManifest(DataSource dataSource, Uri uri) throws IOException {
        return (DashManifest) ParsingLoadable.load(dataSource, new DashManifestParser(), uri, 4);
    }

    @Nullable
    public static DrmInitData loadDrmInitData(DataSource dataSource, Period period) throws IOException, InterruptedException {
        int primaryTrackType = 2;
        Representation representation = getFirstRepresentation(period, 2);
        if (representation == null) {
            primaryTrackType = 1;
            representation = getFirstRepresentation(period, 1);
            if (representation == null) {
                return null;
            }
        }
        Format manifestFormat = representation.format;
        Format sampleFormat = loadSampleFormat(dataSource, primaryTrackType, representation);
        if (sampleFormat == null) {
            return manifestFormat.drmInitData;
        }
        return sampleFormat.copyWithManifestFormatInfo(manifestFormat).drmInitData;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.source.dash.DashUtil.loadInitializationData(com.google.android.exoplayer2.upstream.DataSource, int, com.google.android.exoplayer2.source.dash.manifest.Representation, boolean):com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper
     arg types: [com.google.android.exoplayer2.upstream.DataSource, int, com.google.android.exoplayer2.source.dash.manifest.Representation, int]
     candidates:
      com.google.android.exoplayer2.source.dash.DashUtil.loadInitializationData(com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.source.dash.manifest.Representation, com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper, com.google.android.exoplayer2.source.dash.manifest.RangedUri):void
      com.google.android.exoplayer2.source.dash.DashUtil.loadInitializationData(com.google.android.exoplayer2.upstream.DataSource, int, com.google.android.exoplayer2.source.dash.manifest.Representation, boolean):com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper */
    @Nullable
    public static Format loadSampleFormat(DataSource dataSource, int trackType, Representation representation) throws IOException, InterruptedException {
        ChunkExtractorWrapper extractorWrapper = loadInitializationData(dataSource, trackType, representation, false);
        if (extractorWrapper == null) {
            return null;
        }
        return extractorWrapper.getSampleFormats()[0];
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.source.dash.DashUtil.loadInitializationData(com.google.android.exoplayer2.upstream.DataSource, int, com.google.android.exoplayer2.source.dash.manifest.Representation, boolean):com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper
     arg types: [com.google.android.exoplayer2.upstream.DataSource, int, com.google.android.exoplayer2.source.dash.manifest.Representation, int]
     candidates:
      com.google.android.exoplayer2.source.dash.DashUtil.loadInitializationData(com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.source.dash.manifest.Representation, com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper, com.google.android.exoplayer2.source.dash.manifest.RangedUri):void
      com.google.android.exoplayer2.source.dash.DashUtil.loadInitializationData(com.google.android.exoplayer2.upstream.DataSource, int, com.google.android.exoplayer2.source.dash.manifest.Representation, boolean):com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper */
    @Nullable
    public static ChunkIndex loadChunkIndex(DataSource dataSource, int trackType, Representation representation) throws IOException, InterruptedException {
        ChunkExtractorWrapper extractorWrapper = loadInitializationData(dataSource, trackType, representation, true);
        if (extractorWrapper == null) {
            return null;
        }
        return (ChunkIndex) extractorWrapper.getSeekMap();
    }

    @Nullable
    private static ChunkExtractorWrapper loadInitializationData(DataSource dataSource, int trackType, Representation representation, boolean loadIndex) throws IOException, InterruptedException {
        RangedUri requestUri;
        RangedUri initializationUri = representation.getInitializationUri();
        if (initializationUri == null) {
            return null;
        }
        ChunkExtractorWrapper extractorWrapper = newWrappedExtractor(trackType, representation.format);
        if (loadIndex) {
            RangedUri indexUri = representation.getIndexUri();
            if (indexUri == null) {
                return null;
            }
            requestUri = initializationUri.attemptMerge(indexUri, representation.baseUrl);
            if (requestUri == null) {
                loadInitializationData(dataSource, representation, extractorWrapper, initializationUri);
                requestUri = indexUri;
            }
        } else {
            requestUri = initializationUri;
        }
        loadInitializationData(dataSource, representation, extractorWrapper, requestUri);
        return extractorWrapper;
    }

    private static void loadInitializationData(DataSource dataSource, Representation representation, ChunkExtractorWrapper extractorWrapper, RangedUri requestUri) throws IOException, InterruptedException {
        new InitializationChunk(dataSource, new DataSpec(requestUri.resolveUri(representation.baseUrl), requestUri.start, requestUri.length, representation.getCacheKey()), representation.format, 0, null, extractorWrapper).load();
    }

    private static ChunkExtractorWrapper newWrappedExtractor(int trackType, Format format) {
        String mimeType = format.containerMimeType;
        return new ChunkExtractorWrapper(mimeType != null && (mimeType.startsWith(MimeTypes.VIDEO_WEBM) || mimeType.startsWith(MimeTypes.AUDIO_WEBM)) ? new MatroskaExtractor() : new FragmentedMp4Extractor(), trackType, format);
    }

    @Nullable
    private static Representation getFirstRepresentation(Period period, int type) {
        int index = period.getAdaptationSetIndex(type);
        if (index == -1) {
            return null;
        }
        List<Representation> representations = period.adaptationSets.get(index).representations;
        if (representations.isEmpty()) {
            return null;
        }
        return representations.get(0);
    }

    private DashUtil() {
    }
}
