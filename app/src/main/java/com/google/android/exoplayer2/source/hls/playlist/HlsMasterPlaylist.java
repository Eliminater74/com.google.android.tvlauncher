package com.google.android.exoplayer2.source.hls.playlist;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.util.MimeTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class HlsMasterPlaylist extends HlsPlaylist {
    public static final HlsMasterPlaylist EMPTY = new HlsMasterPlaylist("", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null, Collections.emptyList(), false, Collections.emptyMap(), Collections.emptyList());
    public static final int GROUP_INDEX_AUDIO = 1;
    public static final int GROUP_INDEX_SUBTITLE = 2;
    public static final int GROUP_INDEX_VARIANT = 0;
    public final List<Rendition> audios;
    public final List<Rendition> closedCaptions;
    public final List<Uri> mediaPlaylistUrls;
    public final Format muxedAudioFormat;
    public final List<Format> muxedCaptionFormats;
    public final List<DrmInitData> sessionKeyDrmInitData;
    public final List<Rendition> subtitles;
    public final Map<String, String> variableDefinitions;
    public final List<Variant> variants;
    public final List<Rendition> videos;

    public HlsMasterPlaylist(String baseUri, List<String> tags, List<Variant> variants2, List<Rendition> videos2, List<Rendition> audios2, List<Rendition> subtitles2, List<Rendition> closedCaptions2, Format muxedAudioFormat2, List<Format> muxedCaptionFormats2, boolean hasIndependentSegments, Map<String, String> variableDefinitions2, List<DrmInitData> sessionKeyDrmInitData2) {
        super(baseUri, tags, hasIndependentSegments);
        this.mediaPlaylistUrls = Collections.unmodifiableList(getMediaPlaylistUrls(variants2, videos2, audios2, subtitles2, closedCaptions2));
        this.variants = Collections.unmodifiableList(variants2);
        this.videos = Collections.unmodifiableList(videos2);
        this.audios = Collections.unmodifiableList(audios2);
        this.subtitles = Collections.unmodifiableList(subtitles2);
        this.closedCaptions = Collections.unmodifiableList(closedCaptions2);
        this.muxedAudioFormat = muxedAudioFormat2;
        this.muxedCaptionFormats = muxedCaptionFormats2 != null ? Collections.unmodifiableList(muxedCaptionFormats2) : null;
        this.variableDefinitions = Collections.unmodifiableMap(variableDefinitions2);
        this.sessionKeyDrmInitData = Collections.unmodifiableList(sessionKeyDrmInitData2);
    }

    public static HlsMasterPlaylist createSingleVariantMasterPlaylist(String variantUrl) {
        return new HlsMasterPlaylist(null, Collections.emptyList(), Collections.singletonList(Variant.createMediaPlaylistVariantUrl(Uri.parse(variantUrl))), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null, null, false, Collections.emptyMap(), Collections.emptyList());
    }

    private static List<Uri> getMediaPlaylistUrls(List<Variant> variants2, List<Rendition> videos2, List<Rendition> audios2, List<Rendition> subtitles2, List<Rendition> closedCaptions2) {
        ArrayList<Uri> mediaPlaylistUrls2 = new ArrayList<>();
        for (int i = 0; i < variants2.size(); i++) {
            Uri uri = variants2.get(i).url;
            if (!mediaPlaylistUrls2.contains(uri)) {
                mediaPlaylistUrls2.add(uri);
            }
        }
        addMediaPlaylistUrls(videos2, mediaPlaylistUrls2);
        addMediaPlaylistUrls(audios2, mediaPlaylistUrls2);
        addMediaPlaylistUrls(subtitles2, mediaPlaylistUrls2);
        addMediaPlaylistUrls(closedCaptions2, mediaPlaylistUrls2);
        return mediaPlaylistUrls2;
    }

    private static void addMediaPlaylistUrls(List<Rendition> renditions, List<Uri> out) {
        for (int i = 0; i < renditions.size(); i++) {
            Uri uri = renditions.get(i).url;
            if (uri != null && !out.contains(uri)) {
                out.add(uri);
            }
        }
    }

    private static <T> List<T> copyStreams(List<T> streams, int groupIndex, List<StreamKey> streamKeys) {
        List<T> copiedStreams = new ArrayList<>(streamKeys.size());
        for (int i = 0; i < streams.size(); i++) {
            T stream = streams.get(i);
            int j = 0;
            while (true) {
                if (j >= streamKeys.size()) {
                    break;
                }
                StreamKey streamKey = streamKeys.get(j);
                if (streamKey.groupIndex == groupIndex && streamKey.trackIndex == i) {
                    copiedStreams.add(stream);
                    break;
                }
                j++;
            }
        }
        return copiedStreams;
    }

    public HlsMasterPlaylist copy(List<StreamKey> streamKeys) {
        return new HlsMasterPlaylist(this.baseUri, this.tags, copyStreams(this.variants, 0, streamKeys), Collections.emptyList(), copyStreams(this.audios, 1, streamKeys), copyStreams(this.subtitles, 2, streamKeys), Collections.emptyList(), this.muxedAudioFormat, this.muxedCaptionFormats, this.hasIndependentSegments, this.variableDefinitions, this.sessionKeyDrmInitData);
    }

    public static final class Variant {
        @Nullable
        public final String audioGroupId;
        @Nullable
        public final String captionGroupId;
        public final Format format;
        @Nullable
        public final String subtitleGroupId;
        public final Uri url;
        @Nullable
        public final String videoGroupId;

        public Variant(Uri url2, Format format2, @Nullable String videoGroupId2, @Nullable String audioGroupId2, @Nullable String subtitleGroupId2, @Nullable String captionGroupId2) {
            this.url = url2;
            this.format = format2;
            this.videoGroupId = videoGroupId2;
            this.audioGroupId = audioGroupId2;
            this.subtitleGroupId = subtitleGroupId2;
            this.captionGroupId = captionGroupId2;
        }

        public static Variant createMediaPlaylistVariantUrl(Uri url2) {
            return new Variant(url2, Format.createContainerFormat("0", null, MimeTypes.APPLICATION_M3U8, null, null, -1, 0, 0, null), null, null, null, null);
        }

        public Variant copyWithFormat(Format format2) {
            return new Variant(this.url, format2, this.videoGroupId, this.audioGroupId, this.subtitleGroupId, this.captionGroupId);
        }
    }

    public static final class Rendition {
        public final Format format;
        public final String groupId;
        public final String name;
        @Nullable
        public final Uri url;

        public Rendition(@Nullable Uri url2, Format format2, String groupId2, String name2) {
            this.url = url2;
            this.format = format2;
            this.groupId = groupId2;
            this.name = name2;
        }
    }
}
