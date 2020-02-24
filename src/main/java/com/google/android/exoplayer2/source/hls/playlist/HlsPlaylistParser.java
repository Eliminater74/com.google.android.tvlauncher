package com.google.android.exoplayer2.source.hls.playlist;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.source.UnrecognizedInputFormatException;
import com.google.android.exoplayer2.source.hls.HlsTrackMetadataEntry;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HlsPlaylistParser implements ParsingLoadable.Parser<HlsPlaylist> {
    private static final String ATTR_CLOSED_CAPTIONS_NONE = "CLOSED-CAPTIONS=NONE";
    private static final String BOOLEAN_FALSE = "NO";
    private static final String BOOLEAN_TRUE = "YES";
    private static final String KEYFORMAT_IDENTITY = "identity";
    private static final String KEYFORMAT_PLAYREADY = "com.microsoft.playready";
    private static final String KEYFORMAT_WIDEVINE_PSSH_BINARY = "urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed";
    private static final String KEYFORMAT_WIDEVINE_PSSH_JSON = "com.widevine";
    private static final String METHOD_AES_128 = "AES-128";
    private static final String METHOD_NONE = "NONE";
    private static final String METHOD_SAMPLE_AES = "SAMPLE-AES";
    private static final String METHOD_SAMPLE_AES_CENC = "SAMPLE-AES-CENC";
    private static final String METHOD_SAMPLE_AES_CTR = "SAMPLE-AES-CTR";
    private static final String PLAYLIST_HEADER = "#EXTM3U";
    private static final Pattern REGEX_ATTR_BYTERANGE = Pattern.compile("BYTERANGE=\"(\\d+(?:@\\d+)?)\\b\"");
    private static final Pattern REGEX_AUDIO = Pattern.compile("AUDIO=\"(.+?)\"");
    private static final Pattern REGEX_AUTOSELECT = compileBooleanAttrPattern("AUTOSELECT");
    private static final Pattern REGEX_AVERAGE_BANDWIDTH = Pattern.compile("AVERAGE-BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_BANDWIDTH = Pattern.compile("[^-]BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_BYTERANGE = Pattern.compile("#EXT-X-BYTERANGE:(\\d+(?:@\\d+)?)\\b");
    private static final Pattern REGEX_CHANNELS = Pattern.compile("CHANNELS=\"(.+?)\"");
    private static final Pattern REGEX_CHARACTERISTICS = Pattern.compile("CHARACTERISTICS=\"(.+?)\"");
    private static final Pattern REGEX_CLOSED_CAPTIONS = Pattern.compile("CLOSED-CAPTIONS=\"(.+?)\"");
    private static final Pattern REGEX_CODECS = Pattern.compile("CODECS=\"(.+?)\"");
    private static final Pattern REGEX_DEFAULT = compileBooleanAttrPattern("DEFAULT");
    private static final Pattern REGEX_FORCED = compileBooleanAttrPattern("FORCED");
    private static final Pattern REGEX_FRAME_RATE = Pattern.compile("FRAME-RATE=([\\d\\.]+)\\b");
    private static final Pattern REGEX_GROUP_ID = Pattern.compile("GROUP-ID=\"(.+?)\"");
    private static final Pattern REGEX_IMPORT = Pattern.compile("IMPORT=\"(.+?)\"");
    private static final Pattern REGEX_INSTREAM_ID = Pattern.compile("INSTREAM-ID=\"((?:CC|SERVICE)\\d+)\"");
    private static final Pattern REGEX_IV = Pattern.compile("IV=([^,.*]+)");
    private static final Pattern REGEX_KEYFORMAT = Pattern.compile("KEYFORMAT=\"(.+?)\"");
    private static final Pattern REGEX_KEYFORMATVERSIONS = Pattern.compile("KEYFORMATVERSIONS=\"(.+?)\"");
    private static final Pattern REGEX_LANGUAGE = Pattern.compile("LANGUAGE=\"(.+?)\"");
    private static final Pattern REGEX_MEDIA_DURATION = Pattern.compile("#EXTINF:([\\d\\.]+)\\b");
    private static final Pattern REGEX_MEDIA_SEQUENCE = Pattern.compile("#EXT-X-MEDIA-SEQUENCE:(\\d+)\\b");
    private static final Pattern REGEX_MEDIA_TITLE = Pattern.compile("#EXTINF:[\\d\\.]+\\b,(.+)");
    private static final Pattern REGEX_METHOD = Pattern.compile("METHOD=(NONE|AES-128|SAMPLE-AES|SAMPLE-AES-CENC|SAMPLE-AES-CTR)\\s*(?:,|$)");
    private static final Pattern REGEX_NAME = Pattern.compile("NAME=\"(.+?)\"");
    private static final Pattern REGEX_PLAYLIST_TYPE = Pattern.compile("#EXT-X-PLAYLIST-TYPE:(.+)\\b");
    private static final Pattern REGEX_RESOLUTION = Pattern.compile("RESOLUTION=(\\d+x\\d+)");
    private static final Pattern REGEX_SUBTITLES = Pattern.compile("SUBTITLES=\"(.+?)\"");
    private static final Pattern REGEX_TARGET_DURATION = Pattern.compile("#EXT-X-TARGETDURATION:(\\d+)\\b");
    private static final Pattern REGEX_TIME_OFFSET = Pattern.compile("TIME-OFFSET=(-?[\\d\\.]+)\\b");
    private static final Pattern REGEX_TYPE = Pattern.compile("TYPE=(AUDIO|VIDEO|SUBTITLES|CLOSED-CAPTIONS)");
    private static final Pattern REGEX_URI = Pattern.compile("URI=\"(.+?)\"");
    private static final Pattern REGEX_VALUE = Pattern.compile("VALUE=\"(.+?)\"");
    private static final Pattern REGEX_VARIABLE_REFERENCE = Pattern.compile("\\{\\$([a-zA-Z0-9\\-_]+)\\}");
    private static final Pattern REGEX_VERSION = Pattern.compile("#EXT-X-VERSION:(\\d+)\\b");
    private static final Pattern REGEX_VIDEO = Pattern.compile("VIDEO=\"(.+?)\"");
    private static final String TAG_BYTERANGE = "#EXT-X-BYTERANGE";
    private static final String TAG_DEFINE = "#EXT-X-DEFINE";
    private static final String TAG_DISCONTINUITY = "#EXT-X-DISCONTINUITY";
    private static final String TAG_DISCONTINUITY_SEQUENCE = "#EXT-X-DISCONTINUITY-SEQUENCE";
    private static final String TAG_ENDLIST = "#EXT-X-ENDLIST";
    private static final String TAG_GAP = "#EXT-X-GAP";
    private static final String TAG_INDEPENDENT_SEGMENTS = "#EXT-X-INDEPENDENT-SEGMENTS";
    private static final String TAG_INIT_SEGMENT = "#EXT-X-MAP";
    private static final String TAG_KEY = "#EXT-X-KEY";
    private static final String TAG_MEDIA = "#EXT-X-MEDIA";
    private static final String TAG_MEDIA_DURATION = "#EXTINF";
    private static final String TAG_MEDIA_SEQUENCE = "#EXT-X-MEDIA-SEQUENCE";
    private static final String TAG_PLAYLIST_TYPE = "#EXT-X-PLAYLIST-TYPE";
    private static final String TAG_PREFIX = "#EXT";
    private static final String TAG_PROGRAM_DATE_TIME = "#EXT-X-PROGRAM-DATE-TIME";
    private static final String TAG_SESSION_KEY = "#EXT-X-SESSION-KEY";
    private static final String TAG_START = "#EXT-X-START";
    private static final String TAG_STREAM_INF = "#EXT-X-STREAM-INF";
    private static final String TAG_TARGET_DURATION = "#EXT-X-TARGETDURATION";
    private static final String TAG_VERSION = "#EXT-X-VERSION";
    private static final String TYPE_AUDIO = "AUDIO";
    private static final String TYPE_CLOSED_CAPTIONS = "CLOSED-CAPTIONS";
    private static final String TYPE_SUBTITLES = "SUBTITLES";
    private static final String TYPE_VIDEO = "VIDEO";
    private final HlsMasterPlaylist masterPlaylist;

    public HlsPlaylistParser() {
        this(HlsMasterPlaylist.EMPTY);
    }

    public HlsPlaylistParser(HlsMasterPlaylist masterPlaylist2) {
        this.masterPlaylist = masterPlaylist2;
    }

    public HlsPlaylist parse(Uri uri, InputStream inputStream) throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Queue<String> extraLines = new ArrayDeque<>();
        try {
            if (checkPlaylistHeader(reader)) {
                while (true) {
                    String readLine = reader.readLine();
                    String line2 = readLine;
                    if (readLine != null) {
                        line = line2.trim();
                        if (!line.isEmpty()) {
                            if (!line.startsWith(TAG_STREAM_INF)) {
                                if (line.startsWith(TAG_TARGET_DURATION) || line.startsWith(TAG_MEDIA_SEQUENCE) || line.startsWith(TAG_MEDIA_DURATION) || line.startsWith(TAG_KEY) || line.startsWith(TAG_BYTERANGE) || line.equals(TAG_DISCONTINUITY) || line.equals(TAG_DISCONTINUITY_SEQUENCE)) {
                                    break;
                                } else if (line.equals(TAG_ENDLIST)) {
                                    break;
                                } else {
                                    extraLines.add(line);
                                }
                            } else {
                                extraLines.add(line);
                                HlsMasterPlaylist parseMasterPlaylist = parseMasterPlaylist(new LineIterator(extraLines, reader), uri.toString());
                                Util.closeQuietly(reader);
                                return parseMasterPlaylist;
                            }
                        }
                    } else {
                        Util.closeQuietly(reader);
                        throw new ParserException("Failed to parse the playlist, could not identify any tags.");
                    }
                }
                extraLines.add(line);
                return parseMediaPlaylist(this.masterPlaylist, new LineIterator(extraLines, reader), uri.toString());
            }
            throw new UnrecognizedInputFormatException("Input does not start with the #EXTM3U header.", uri);
        } finally {
            Util.closeQuietly(reader);
        }
    }

    private static boolean checkPlaylistHeader(BufferedReader reader) throws IOException {
        int last = reader.read();
        if (last == 239) {
            if (reader.read() != 187 || reader.read() != 191) {
                return false;
            }
            last = reader.read();
        }
        int last2 = skipIgnorableWhitespace(reader, true, last);
        int playlistHeaderLength = PLAYLIST_HEADER.length();
        for (int i = 0; i < playlistHeaderLength; i++) {
            if (last2 != PLAYLIST_HEADER.charAt(i)) {
                return false;
            }
            last2 = reader.read();
        }
        return Util.isLinebreak(skipIgnorableWhitespace(reader, false, last2));
    }

    private static int skipIgnorableWhitespace(BufferedReader reader, boolean skipLinebreaks, int c) throws IOException {
        while (c != -1 && Character.isWhitespace(c) && (skipLinebreaks || !Util.isLinebreak(c))) {
            c = reader.read();
        }
        return c;
    }

    /* JADX INFO: Multiple debug info for r8v2 java.util.ArrayList<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Rendition>: [D('mediaTags' java.util.ArrayList<java.lang.String>), D('audios' java.util.ArrayList<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Rendition>)] */
    /* JADX INFO: Multiple debug info for r7v3 java.util.ArrayList<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Rendition>: [D('deduplicatedVariants' java.util.ArrayList<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant>), D('closedCaptions' java.util.ArrayList<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Rendition>)] */
    /* JADX INFO: Multiple debug info for r10v9 int: [D('codecs' java.lang.String), D('height' int)] */
    /* JADX INFO: Multiple debug info for r8v12 com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant: [D('format' com.google.android.exoplayer2.Format), D('variant' com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant)] */
    /* JADX INFO: Multiple debug info for r11v28 android.net.Uri: [D('videos' java.util.ArrayList<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Rendition>), D('uri' android.net.Uri)] */
    /* JADX INFO: Multiple debug info for r1v20 com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant: [D('width' int), D('variant' com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant)] */
    /* JADX INFO: Multiple debug info for r1v24 'variantInfosForUrl'  java.util.ArrayList<com.google.android.exoplayer2.source.hls.HlsTrackMetadataEntry$VariantInfo>: [D('variant' com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant), D('variantInfosForUrl' java.util.ArrayList<com.google.android.exoplayer2.source.hls.HlsTrackMetadataEntry$VariantInfo>)] */
    /* JADX INFO: Multiple debug info for r1v29 java.lang.String: [D('scheme' java.lang.String), D('muxedCaptionFormats' java.util.List<com.google.android.exoplayer2.Format>)] */
    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private static HlsMasterPlaylist parseMasterPlaylist(LineIterator iterator, String baseUri) throws IOException {
        Uri uri;
        char c;
        ArrayList<HlsMasterPlaylist.Rendition> videos;
        ArrayList<HlsMasterPlaylist.Rendition> audios;
        String codecs;
        int accessibilityChannel;
        String mimeType;
        List<Format> muxedCaptionFormats;
        List<Format> muxedCaptionFormats2;
        Format muxedAudioFormat;
        int width;
        int width2;
        ArrayList<HlsTrackMetadataEntry.VariantInfo> variantInfosForUrl;
        String str = baseUri;
        HashMap hashMap = new HashMap();
        HashMap<String, String> variableDefinitions = new HashMap<>();
        ArrayList<HlsMasterPlaylist.Variant> variants = new ArrayList<>();
        ArrayList<HlsMasterPlaylist.Rendition> videos2 = new ArrayList<>();
        List<Format> audios2 = new ArrayList<>();
        ArrayList<HlsMasterPlaylist.Rendition> subtitles = new ArrayList<>();
        ArrayList<HlsMasterPlaylist.Rendition> closedCaptions = new ArrayList<>();
        ArrayList<String> mediaTags = new ArrayList<>();
        ArrayList<DrmInitData> sessionKeyDrmInitData = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        Format muxedAudioFormat2 = null;
        List<Format> muxedCaptionFormats3 = null;
        boolean noClosedCaptions = false;
        boolean hasIndependentSegmentsTag = false;
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.startsWith(TAG_PREFIX)) {
                tags.add(line);
            }
            if (line.startsWith(TAG_DEFINE)) {
                variableDefinitions.put(parseStringAttr(line, REGEX_NAME, variableDefinitions), parseStringAttr(line, REGEX_VALUE, variableDefinitions));
                muxedAudioFormat = muxedAudioFormat2;
                muxedCaptionFormats2 = muxedCaptionFormats3;
            } else if (line.equals(TAG_INDEPENDENT_SEGMENTS)) {
                hasIndependentSegmentsTag = true;
            } else if (line.startsWith(TAG_MEDIA)) {
                mediaTags.add(line);
                muxedAudioFormat = muxedAudioFormat2;
                muxedCaptionFormats2 = muxedCaptionFormats3;
            } else if (line.startsWith(TAG_SESSION_KEY)) {
                DrmInitData.SchemeData schemeData = parseDrmSchemeData(line, parseOptionalStringAttr(line, REGEX_KEYFORMAT, KEYFORMAT_IDENTITY, variableDefinitions), variableDefinitions);
                if (schemeData != null) {
                    muxedAudioFormat = muxedAudioFormat2;
                    muxedCaptionFormats2 = muxedCaptionFormats3;
                    sessionKeyDrmInitData.add(new DrmInitData(parseEncryptionScheme(parseStringAttr(line, REGEX_METHOD, variableDefinitions)), schemeData));
                } else {
                    muxedAudioFormat = muxedAudioFormat2;
                    muxedCaptionFormats2 = muxedCaptionFormats3;
                }
            } else {
                muxedAudioFormat = muxedAudioFormat2;
                muxedCaptionFormats2 = muxedCaptionFormats3;
                if (line.startsWith(TAG_STREAM_INF)) {
                    noClosedCaptions |= line.contains(ATTR_CLOSED_CAPTIONS_NONE);
                    int bitrate = parseIntAttr(line, REGEX_BANDWIDTH);
                    String averageBandwidthString = parseOptionalStringAttr(line, REGEX_AVERAGE_BANDWIDTH, variableDefinitions);
                    if (averageBandwidthString != null) {
                        bitrate = Integer.parseInt(averageBandwidthString);
                    }
                    String codecs2 = parseOptionalStringAttr(line, REGEX_CODECS, variableDefinitions);
                    String resolutionString = parseOptionalStringAttr(line, REGEX_RESOLUTION, variableDefinitions);
                    if (resolutionString != null) {
                        String[] widthAndHeight = resolutionString.split("x");
                        int width3 = Integer.parseInt(widthAndHeight[0]);
                        int height = Integer.parseInt(widthAndHeight[1]);
                        if (width3 <= 0 || height <= 0) {
                            width3 = -1;
                            height = -1;
                        }
                        width2 = width3;
                        width = height;
                    } else {
                        width2 = -1;
                        width = -1;
                    }
                    float frameRate = -1.0f;
                    String frameRateString = parseOptionalStringAttr(line, REGEX_FRAME_RATE, variableDefinitions);
                    if (frameRateString != null) {
                        frameRate = Float.parseFloat(frameRateString);
                    }
                    String videoGroupId = parseOptionalStringAttr(line, REGEX_VIDEO, variableDefinitions);
                    ArrayList<DrmInitData> sessionKeyDrmInitData2 = sessionKeyDrmInitData;
                    String audioGroupId = parseOptionalStringAttr(line, REGEX_AUDIO, variableDefinitions);
                    ArrayList<HlsMasterPlaylist.Rendition> closedCaptions2 = closedCaptions;
                    String subtitlesGroupId = parseOptionalStringAttr(line, REGEX_SUBTITLES, variableDefinitions);
                    ArrayList<String> tags2 = tags;
                    String closedCaptionsGroupId = parseOptionalStringAttr(line, REGEX_CLOSED_CAPTIONS, variableDefinitions);
                    ArrayList<HlsMasterPlaylist.Rendition> videos3 = videos2;
                    Uri uri2 = UriUtil.resolveToUri(str, replaceVariableReferences(iterator.next(), variableDefinitions));
                    variants.add(new HlsMasterPlaylist.Variant(uri2, Format.createVideoContainerFormat(Integer.toString(variants.size()), null, MimeTypes.APPLICATION_M3U8, null, codecs2, bitrate, width2, width, frameRate, null, 0, 0), videoGroupId, audioGroupId, subtitlesGroupId, closedCaptionsGroupId));
                    ArrayList<HlsTrackMetadataEntry.VariantInfo> variantInfosForUrl2 = (ArrayList) hashMap.get(uri2);
                    if (variantInfosForUrl2 == null) {
                        variantInfosForUrl = new ArrayList<>();
                        hashMap.put(uri2, variantInfosForUrl);
                    } else {
                        variantInfosForUrl = variantInfosForUrl2;
                    }
                    variantInfosForUrl.add(new HlsTrackMetadataEntry.VariantInfo((long) bitrate, videoGroupId, audioGroupId, subtitlesGroupId, closedCaptionsGroupId));
                    muxedAudioFormat2 = muxedAudioFormat;
                    muxedCaptionFormats3 = muxedCaptionFormats2;
                    audios2 = audios2;
                    sessionKeyDrmInitData = sessionKeyDrmInitData2;
                    closedCaptions = closedCaptions2;
                    tags = tags2;
                    videos2 = videos3;
                }
            }
            muxedAudioFormat2 = muxedAudioFormat;
            muxedCaptionFormats3 = muxedCaptionFormats2;
            audios2 = audios2;
            sessionKeyDrmInitData = sessionKeyDrmInitData;
            closedCaptions = closedCaptions;
            tags = tags;
            videos2 = videos2;
        }
        Format muxedAudioFormat3 = muxedAudioFormat2;
        List<Format> muxedCaptionFormats4 = muxedCaptionFormats3;
        ArrayList<String> tags3 = tags;
        ArrayList<DrmInitData> sessionKeyDrmInitData3 = sessionKeyDrmInitData;
        ArrayList<HlsMasterPlaylist.Rendition> closedCaptions3 = closedCaptions;
        ArrayList<HlsMasterPlaylist.Rendition> audios3 = audios2;
        ArrayList<HlsMasterPlaylist.Rendition> videos4 = videos2;
        ArrayList<HlsMasterPlaylist.Variant> deduplicatedVariants = new ArrayList<>();
        HashSet<Uri> urlsInDeduplicatedVariants = new HashSet<>();
        int i = 0;
        while (true) {
            uri = null;
            if (i >= variants.size()) {
                break;
            }
            HlsMasterPlaylist.Variant variant = (HlsMasterPlaylist.Variant) variants.get(i);
            if (urlsInDeduplicatedVariants.add(variant.url)) {
                Assertions.checkState(variant.format.metadata == null);
                deduplicatedVariants.add(variant.copyWithFormat(variant.format.copyWithMetadata(new Metadata(new HlsTrackMetadataEntry(null, null, (List) hashMap.get(variant.url))))));
            }
            i++;
        }
        int i2 = 0;
        while (i2 < mediaTags.size()) {
            String line2 = (String) mediaTags.get(i2);
            String groupId = parseStringAttr(line2, REGEX_GROUP_ID, variableDefinitions);
            String name = parseStringAttr(line2, REGEX_NAME, variableDefinitions);
            String referenceUri = parseOptionalStringAttr(line2, REGEX_URI, variableDefinitions);
            Uri uri3 = referenceUri == null ? uri : UriUtil.resolveToUri(str, referenceUri);
            String language = parseOptionalStringAttr(line2, REGEX_LANGUAGE, variableDefinitions);
            int selectionFlags = parseSelectionFlags(line2);
            int roleFlags = parseRoleFlags(line2, variableDefinitions);
            StringBuilder sb = new StringBuilder(String.valueOf(groupId).length() + 1 + String.valueOf(name).length());
            sb.append(groupId);
            sb.append(":");
            sb.append(name);
            String formatId = sb.toString();
            ArrayList<String> mediaTags2 = mediaTags;
            HashSet<Uri> urlsInDeduplicatedVariants2 = urlsInDeduplicatedVariants;
            Metadata metadata = new Metadata(new HlsTrackMetadataEntry(groupId, name, Collections.emptyList()));
            String parseStringAttr = parseStringAttr(line2, REGEX_TYPE, variableDefinitions);
            switch (parseStringAttr.hashCode()) {
                case -959297733:
                    if (parseStringAttr.equals(TYPE_SUBTITLES)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -333210994:
                    if (parseStringAttr.equals(TYPE_CLOSED_CAPTIONS)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 62628790:
                    if (parseStringAttr.equals(TYPE_AUDIO)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 81665115:
                    if (parseStringAttr.equals(TYPE_VIDEO)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                audios = audios3;
                HlsMasterPlaylist.Variant variant2 = getVariantWithVideoGroup(variants, groupId);
                String codecs3 = null;
                int height2 = -1;
                int height3 = -1;
                float frameRate2 = -1.0f;
                if (variant2 != null) {
                    Format variantFormat = variant2.format;
                    String codecs4 = Util.getCodecsOfType(variantFormat.codecs, 2);
                    int width4 = variantFormat.width;
                    codecs3 = codecs4;
                    int height4 = variantFormat.height;
                    frameRate2 = variantFormat.frameRate;
                    height3 = height4;
                    height2 = width4;
                }
                Format format = Format.createVideoContainerFormat(formatId, name, MimeTypes.APPLICATION_M3U8, codecs3 != null ? MimeTypes.getMediaMimeType(codecs3) : null, codecs3, -1, height2, height3, frameRate2, null, selectionFlags, roleFlags).copyWithMetadata(metadata);
                if (uri3 == null) {
                    videos = videos4;
                } else {
                    videos = videos4;
                    videos.add(new HlsMasterPlaylist.Rendition(uri3, format, groupId, name));
                }
            } else if (c == 1) {
                HlsMasterPlaylist.Variant variant3 = getVariantWithAudioGroup(variants, groupId);
                if (variant3 != null) {
                    codecs = Util.getCodecsOfType(variant3.format.codecs, 1);
                } else {
                    codecs = null;
                }
                Format format2 = Format.createAudioContainerFormat(formatId, name, MimeTypes.APPLICATION_M3U8, codecs != null ? MimeTypes.getMediaMimeType(codecs) : null, codecs, -1, parseChannelsAttribute(line2, variableDefinitions), -1, null, selectionFlags, roleFlags, language);
                if (uri3 == null) {
                    muxedAudioFormat3 = format2;
                    audios = audios3;
                    videos = videos4;
                } else {
                    audios = audios3;
                    audios.add(new HlsMasterPlaylist.Rendition(uri3, format2.copyWithMetadata(metadata), groupId, name));
                    videos = videos4;
                }
            } else if (c == 2) {
                subtitles.add(new HlsMasterPlaylist.Rendition(uri3, Format.createTextContainerFormat(formatId, name, MimeTypes.APPLICATION_M3U8, MimeTypes.TEXT_VTT, null, -1, selectionFlags, roleFlags, language).copyWithMetadata(metadata), groupId, name));
                audios = audios3;
                videos = videos4;
            } else if (c != 3) {
                audios = audios3;
                videos = videos4;
            } else {
                String instreamId = parseStringAttr(line2, REGEX_INSTREAM_ID, variableDefinitions);
                if (instreamId.startsWith("CC")) {
                    mimeType = MimeTypes.APPLICATION_CEA608;
                    accessibilityChannel = Integer.parseInt(instreamId.substring(2));
                } else {
                    mimeType = MimeTypes.APPLICATION_CEA708;
                    accessibilityChannel = Integer.parseInt(instreamId.substring(7));
                }
                if (muxedCaptionFormats4 == null) {
                    muxedCaptionFormats = new ArrayList<>();
                } else {
                    muxedCaptionFormats = muxedCaptionFormats4;
                }
                muxedCaptionFormats.add(Format.createTextContainerFormat(formatId, name, null, mimeType, null, -1, selectionFlags, roleFlags, language, accessibilityChannel));
                muxedCaptionFormats4 = muxedCaptionFormats;
                audios = audios3;
                videos = videos4;
            }
            i2++;
            audios3 = audios;
            videos4 = videos;
            mediaTags = mediaTags2;
            urlsInDeduplicatedVariants = urlsInDeduplicatedVariants2;
            uri = null;
            str = baseUri;
        }
        ArrayList<HlsMasterPlaylist.Rendition> audios4 = audios3;
        ArrayList<HlsMasterPlaylist.Rendition> videos5 = videos4;
        if (noClosedCaptions) {
            muxedCaptionFormats4 = Collections.emptyList();
        }
        return new HlsMasterPlaylist(baseUri, tags3, deduplicatedVariants, videos5, audios4, subtitles, closedCaptions3, muxedAudioFormat3, muxedCaptionFormats4, hasIndependentSegmentsTag, variableDefinitions, sessionKeyDrmInitData3);
    }

    private static HlsMasterPlaylist.Variant getVariantWithAudioGroup(ArrayList<HlsMasterPlaylist.Variant> variants, String groupId) {
        for (int i = 0; i < variants.size(); i++) {
            HlsMasterPlaylist.Variant variant = variants.get(i);
            if (groupId.equals(variant.audioGroupId)) {
                return variant;
            }
        }
        return null;
    }

    private static HlsMasterPlaylist.Variant getVariantWithVideoGroup(ArrayList<HlsMasterPlaylist.Variant> variants, String groupId) {
        for (int i = 0; i < variants.size(); i++) {
            HlsMasterPlaylist.Variant variant = variants.get(i);
            if (groupId.equals(variant.videoGroupId)) {
                return variant;
            }
        }
        return null;
    }

    /* JADX INFO: Multiple debug info for r0v28 'currentSchemeDatas'  java.util.TreeMap<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData$SchemeData>: [D('fullSegmentEncryptionIV' java.lang.String), D('currentSchemeDatas' java.util.TreeMap<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData$SchemeData>)] */
    private static HlsMediaPlaylist parseMediaPlaylist(HlsMasterPlaylist masterPlaylist2, LineIterator iterator, String baseUri) throws IOException {
        String segmentEncryptionIV;
        DrmInitData cachedDrmInitData;
        TreeMap<String, DrmInitData.SchemeData> currentSchemeDatas;
        HlsMasterPlaylist hlsMasterPlaylist = masterPlaylist2;
        int playlistType = 0;
        long startOffsetUs = C0841C.TIME_UNSET;
        boolean hasIndependentSegmentsTag = hlsMasterPlaylist.hasIndependentSegments;
        HashMap<String, String> variableDefinitions = new HashMap<>();
        List<HlsMediaPlaylist.Segment> segments = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        TreeMap<String, DrmInitData.SchemeData> currentSchemeDatas2 = new TreeMap<>();
        HlsMediaPlaylist.Segment initializationSegment = null;
        String segmentTitle = "";
        long segmentDurationUs = 0;
        int relativeDiscontinuitySequence = 0;
        long segmentStartTimeUs = 0;
        long segmentByteRangeOffset = 0;
        long segmentByteRangeLength = -1;
        long segmentMediaSequence = 0;
        boolean hasGapTag = false;
        DrmInitData playlistProtectionSchemes = null;
        String fullSegmentEncryptionKeyUri = null;
        String fullSegmentEncryptionIV = null;
        String encryptionScheme = null;
        DrmInitData cachedDrmInitData2 = null;
        long targetDurationUs = -9223372036854775807L;
        boolean hasIndependentSegmentsTag2 = hasIndependentSegmentsTag;
        boolean hasEndTag = false;
        int playlistDiscontinuitySequence = 0;
        long mediaSequence = 0;
        int version = 1;
        boolean hasDiscontinuitySequence = false;
        long playlistStartTimeUs = 0;
        while (true) {
            boolean hasIndependentSegmentsTag3 = hasIndependentSegmentsTag2;
            if (iterator.hasNext()) {
                String line = iterator.next();
                int version2 = version;
                if (line.startsWith(TAG_PREFIX)) {
                    tags.add(line);
                }
                if (line.startsWith(TAG_PLAYLIST_TYPE)) {
                    String playlistTypeString = parseStringAttr(line, REGEX_PLAYLIST_TYPE, variableDefinitions);
                    List<String> tags2 = tags;
                    if ("VOD".equals(playlistTypeString)) {
                        playlistType = 1;
                    } else if ("EVENT".equals(playlistTypeString)) {
                        playlistType = 2;
                    }
                    tags = tags2;
                    hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                    version = version2;
                } else {
                    List<String> tags3 = tags;
                    if (line.startsWith(TAG_START)) {
                        startOffsetUs = (long) (parseDoubleAttr(line, REGEX_TIME_OFFSET) * 1000000.0d);
                        tags = tags3;
                        hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                        version = version2;
                        mediaSequence = mediaSequence;
                    } else {
                        long mediaSequence2 = mediaSequence;
                        if (line.startsWith(TAG_INIT_SEGMENT)) {
                            String uri = parseStringAttr(line, REGEX_URI, variableDefinitions);
                            String byteRange = parseOptionalStringAttr(line, REGEX_ATTR_BYTERANGE, variableDefinitions);
                            if (byteRange != null) {
                                String[] splitByteRange = byteRange.split("@");
                                long segmentByteRangeLength2 = Long.parseLong(splitByteRange[0]);
                                if (splitByteRange.length > 1) {
                                    segmentByteRangeOffset = Long.parseLong(splitByteRange[1]);
                                    segmentByteRangeLength = segmentByteRangeLength2;
                                } else {
                                    segmentByteRangeLength = segmentByteRangeLength2;
                                }
                            }
                            if (fullSegmentEncryptionKeyUri == null || fullSegmentEncryptionIV != null) {
                                initializationSegment = new HlsMediaPlaylist.Segment(uri, segmentByteRangeOffset, segmentByteRangeLength, fullSegmentEncryptionKeyUri, fullSegmentEncryptionIV);
                                segmentByteRangeOffset = 0;
                                segmentByteRangeLength = -1;
                                tags = tags3;
                                hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                version = version2;
                                mediaSequence = mediaSequence2;
                            } else {
                                throw new ParserException("The encryption IV attribute must be present when an initialization segment is encrypted with METHOD=AES-128.");
                            }
                        } else if (line.startsWith(TAG_TARGET_DURATION)) {
                            targetDurationUs = ((long) parseIntAttr(line, REGEX_TARGET_DURATION)) * 1000000;
                            tags = tags3;
                            hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                            version = version2;
                            mediaSequence = mediaSequence2;
                        } else if (line.startsWith(TAG_MEDIA_SEQUENCE)) {
                            mediaSequence = parseLongAttr(line, REGEX_MEDIA_SEQUENCE);
                            segmentMediaSequence = mediaSequence;
                            tags = tags3;
                            hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                            version = version2;
                        } else if (line.startsWith(TAG_VERSION)) {
                            version = parseIntAttr(line, REGEX_VERSION);
                            tags = tags3;
                            hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                            mediaSequence = mediaSequence2;
                        } else {
                            if (line.startsWith(TAG_DEFINE)) {
                                String importName = parseOptionalStringAttr(line, REGEX_IMPORT, variableDefinitions);
                                if (importName != null) {
                                    String value = hlsMasterPlaylist.variableDefinitions.get(importName);
                                    if (value != null) {
                                        variableDefinitions.put(importName, value);
                                    }
                                } else {
                                    variableDefinitions.put(parseStringAttr(line, REGEX_NAME, variableDefinitions), parseStringAttr(line, REGEX_VALUE, variableDefinitions));
                                }
                            } else if (line.startsWith(TAG_MEDIA_DURATION)) {
                                segmentTitle = parseOptionalStringAttr(line, REGEX_MEDIA_TITLE, "", variableDefinitions);
                                segmentDurationUs = (long) (parseDoubleAttr(line, REGEX_MEDIA_DURATION) * 1000000.0d);
                                tags = tags3;
                                hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                version = version2;
                                mediaSequence = mediaSequence2;
                            } else if (line.startsWith(TAG_KEY)) {
                                String method = parseStringAttr(line, REGEX_METHOD, variableDefinitions);
                                String keyFormat = parseOptionalStringAttr(line, REGEX_KEYFORMAT, KEYFORMAT_IDENTITY, variableDefinitions);
                                if (METHOD_NONE.equals(method)) {
                                    currentSchemeDatas2.clear();
                                    cachedDrmInitData2 = null;
                                    fullSegmentEncryptionKeyUri = null;
                                    fullSegmentEncryptionIV = null;
                                    currentSchemeDatas = currentSchemeDatas2;
                                } else {
                                    String fullSegmentEncryptionIV2 = parseOptionalStringAttr(line, REGEX_IV, variableDefinitions);
                                    if (!KEYFORMAT_IDENTITY.equals(keyFormat)) {
                                        if (encryptionScheme == null) {
                                            encryptionScheme = parseEncryptionScheme(method);
                                        }
                                        DrmInitData.SchemeData schemeData = parseDrmSchemeData(line, keyFormat, variableDefinitions);
                                        if (schemeData != null) {
                                            String fullSegmentEncryptionIV3 = fullSegmentEncryptionIV2;
                                            currentSchemeDatas = currentSchemeDatas2;
                                            currentSchemeDatas.put(keyFormat, schemeData);
                                            fullSegmentEncryptionKeyUri = null;
                                            cachedDrmInitData2 = null;
                                            fullSegmentEncryptionIV = fullSegmentEncryptionIV3;
                                        } else {
                                            String fullSegmentEncryptionIV4 = fullSegmentEncryptionIV2;
                                            currentSchemeDatas = currentSchemeDatas2;
                                            fullSegmentEncryptionKeyUri = null;
                                            fullSegmentEncryptionIV = fullSegmentEncryptionIV4;
                                        }
                                    } else if (METHOD_AES_128.equals(method)) {
                                        fullSegmentEncryptionIV = fullSegmentEncryptionIV2;
                                        fullSegmentEncryptionKeyUri = parseStringAttr(line, REGEX_URI, variableDefinitions);
                                        currentSchemeDatas = currentSchemeDatas2;
                                    } else {
                                        fullSegmentEncryptionIV = fullSegmentEncryptionIV2;
                                        fullSegmentEncryptionKeyUri = null;
                                        currentSchemeDatas = currentSchemeDatas2;
                                    }
                                }
                                currentSchemeDatas2 = currentSchemeDatas;
                                tags = tags3;
                                hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                version = version2;
                                mediaSequence = mediaSequence2;
                                hlsMasterPlaylist = masterPlaylist2;
                            } else {
                                TreeMap<String, DrmInitData.SchemeData> currentSchemeDatas3 = currentSchemeDatas2;
                                if (line.startsWith(TAG_BYTERANGE)) {
                                    String[] splitByteRange2 = parseStringAttr(line, REGEX_BYTERANGE, variableDefinitions).split("@");
                                    segmentByteRangeLength = Long.parseLong(splitByteRange2[0]);
                                    if (splitByteRange2.length > 1) {
                                        segmentByteRangeOffset = Long.parseLong(splitByteRange2[1]);
                                    }
                                    currentSchemeDatas2 = currentSchemeDatas3;
                                    tags = tags3;
                                    hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                    version = version2;
                                    mediaSequence = mediaSequence2;
                                    hlsMasterPlaylist = masterPlaylist2;
                                } else if (line.startsWith(TAG_DISCONTINUITY_SEQUENCE)) {
                                    hasDiscontinuitySequence = true;
                                    playlistDiscontinuitySequence = Integer.parseInt(line.substring(line.indexOf(58) + 1));
                                    currentSchemeDatas2 = currentSchemeDatas3;
                                    tags = tags3;
                                    hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                    version = version2;
                                    mediaSequence = mediaSequence2;
                                    hlsMasterPlaylist = masterPlaylist2;
                                } else if (line.equals(TAG_DISCONTINUITY)) {
                                    relativeDiscontinuitySequence++;
                                    currentSchemeDatas2 = currentSchemeDatas3;
                                    tags = tags3;
                                    hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                    version = version2;
                                    mediaSequence = mediaSequence2;
                                    hlsMasterPlaylist = masterPlaylist2;
                                } else if (line.startsWith(TAG_PROGRAM_DATE_TIME)) {
                                    if (playlistStartTimeUs == 0) {
                                        playlistStartTimeUs = C0841C.msToUs(Util.parseXsDateTime(line.substring(line.indexOf(58) + 1))) - segmentStartTimeUs;
                                        currentSchemeDatas2 = currentSchemeDatas3;
                                        tags = tags3;
                                        hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                        version = version2;
                                        mediaSequence = mediaSequence2;
                                        hlsMasterPlaylist = masterPlaylist2;
                                    } else {
                                        currentSchemeDatas2 = currentSchemeDatas3;
                                    }
                                } else if (line.equals(TAG_GAP)) {
                                    hasGapTag = true;
                                    currentSchemeDatas2 = currentSchemeDatas3;
                                    tags = tags3;
                                    hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                    version = version2;
                                    mediaSequence = mediaSequence2;
                                    hlsMasterPlaylist = masterPlaylist2;
                                } else if (line.equals(TAG_INDEPENDENT_SEGMENTS)) {
                                    currentSchemeDatas2 = currentSchemeDatas3;
                                    hasIndependentSegmentsTag2 = true;
                                    tags = tags3;
                                    version = version2;
                                    mediaSequence = mediaSequence2;
                                    hlsMasterPlaylist = masterPlaylist2;
                                } else if (line.equals(TAG_ENDLIST)) {
                                    hasEndTag = true;
                                    currentSchemeDatas2 = currentSchemeDatas3;
                                    tags = tags3;
                                    hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                    version = version2;
                                    mediaSequence = mediaSequence2;
                                    hlsMasterPlaylist = masterPlaylist2;
                                } else if (!line.startsWith("#")) {
                                    if (fullSegmentEncryptionKeyUri == null) {
                                        segmentEncryptionIV = null;
                                    } else if (fullSegmentEncryptionIV != null) {
                                        segmentEncryptionIV = fullSegmentEncryptionIV;
                                    } else {
                                        segmentEncryptionIV = Long.toHexString(segmentMediaSequence);
                                    }
                                    segmentMediaSequence++;
                                    if (segmentByteRangeLength == -1) {
                                        segmentByteRangeOffset = 0;
                                    }
                                    if (cachedDrmInitData2 != null || currentSchemeDatas3.isEmpty()) {
                                        currentSchemeDatas2 = currentSchemeDatas3;
                                    } else {
                                        DrmInitData.SchemeData[] schemeDatas = (DrmInitData.SchemeData[]) currentSchemeDatas3.values().toArray(new DrmInitData.SchemeData[0]);
                                        DrmInitData cachedDrmInitData3 = new DrmInitData(encryptionScheme, schemeDatas);
                                        if (playlistProtectionSchemes == null) {
                                            DrmInitData.SchemeData[] playlistSchemeDatas = new DrmInitData.SchemeData[schemeDatas.length];
                                            currentSchemeDatas2 = currentSchemeDatas3;
                                            int i = 0;
                                            while (true) {
                                                cachedDrmInitData = cachedDrmInitData3;
                                                if (i >= schemeDatas.length) {
                                                    break;
                                                }
                                                playlistSchemeDatas[i] = schemeDatas[i].copyWithData(null);
                                                i++;
                                                cachedDrmInitData3 = cachedDrmInitData;
                                                schemeDatas = schemeDatas;
                                            }
                                            playlistProtectionSchemes = new DrmInitData(encryptionScheme, playlistSchemeDatas);
                                            cachedDrmInitData2 = cachedDrmInitData;
                                        } else {
                                            currentSchemeDatas2 = currentSchemeDatas3;
                                            cachedDrmInitData2 = cachedDrmInitData3;
                                        }
                                    }
                                    segments.add(new HlsMediaPlaylist.Segment(replaceVariableReferences(line, variableDefinitions), initializationSegment, segmentTitle, segmentDurationUs, relativeDiscontinuitySequence, segmentStartTimeUs, cachedDrmInitData2, fullSegmentEncryptionKeyUri, segmentEncryptionIV, segmentByteRangeOffset, segmentByteRangeLength, hasGapTag));
                                    segmentStartTimeUs += segmentDurationUs;
                                    segmentDurationUs = 0;
                                    segmentTitle = "";
                                    if (segmentByteRangeLength != -1) {
                                        segmentByteRangeOffset += segmentByteRangeLength;
                                    }
                                    segmentByteRangeLength = -1;
                                    hasGapTag = false;
                                    hlsMasterPlaylist = masterPlaylist2;
                                    tags = tags3;
                                    hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                                    version = version2;
                                    mediaSequence = mediaSequence2;
                                } else {
                                    currentSchemeDatas2 = currentSchemeDatas3;
                                }
                            }
                            hlsMasterPlaylist = masterPlaylist2;
                            tags = tags3;
                            hasIndependentSegmentsTag2 = hasIndependentSegmentsTag3;
                            version = version2;
                            mediaSequence = mediaSequence2;
                        }
                    }
                }
            } else {
                return new HlsMediaPlaylist(playlistType, baseUri, tags, startOffsetUs, playlistStartTimeUs, hasDiscontinuitySequence, playlistDiscontinuitySequence, mediaSequence, version, targetDurationUs, hasIndependentSegmentsTag3, hasEndTag, playlistStartTimeUs != 0, playlistProtectionSchemes, segments);
            }
        }
    }

    private static int parseSelectionFlags(String line) {
        int flags = 0;
        if (parseOptionalBooleanAttribute(line, REGEX_DEFAULT, false)) {
            flags = 0 | 1;
        }
        if (parseOptionalBooleanAttribute(line, REGEX_FORCED, false)) {
            flags |= 2;
        }
        if (parseOptionalBooleanAttribute(line, REGEX_AUTOSELECT, false)) {
            return flags | 4;
        }
        return flags;
    }

    private static int parseRoleFlags(String line, Map<String, String> variableDefinitions) {
        String concatenatedCharacteristics = parseOptionalStringAttr(line, REGEX_CHARACTERISTICS, variableDefinitions);
        if (TextUtils.isEmpty(concatenatedCharacteristics)) {
            return 0;
        }
        String[] characteristics = Util.split(concatenatedCharacteristics, ",");
        int roleFlags = 0;
        if (Util.contains(characteristics, "public.accessibility.describes-video")) {
            roleFlags = 0 | 512;
        }
        if (Util.contains(characteristics, "public.accessibility.transcribes-spoken-dialog")) {
            roleFlags |= 4096;
        }
        if (Util.contains(characteristics, "public.accessibility.describes-music-and-sound")) {
            roleFlags |= 1024;
        }
        if (Util.contains(characteristics, "public.easy-to-read")) {
            return roleFlags | 8192;
        }
        return roleFlags;
    }

    private static int parseChannelsAttribute(String line, Map<String, String> variableDefinitions) {
        String channelsString = parseOptionalStringAttr(line, REGEX_CHANNELS, variableDefinitions);
        if (channelsString != null) {
            return Integer.parseInt(Util.splitAtFirst(channelsString, "/")[0]);
        }
        return -1;
    }

    @Nullable
    private static DrmInitData.SchemeData parseDrmSchemeData(String line, String keyFormat, Map<String, String> variableDefinitions) throws ParserException {
        String keyFormatVersions = parseOptionalStringAttr(line, REGEX_KEYFORMATVERSIONS, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE, variableDefinitions);
        if (KEYFORMAT_WIDEVINE_PSSH_BINARY.equals(keyFormat)) {
            String uriString = parseStringAttr(line, REGEX_URI, variableDefinitions);
            return new DrmInitData.SchemeData(C0841C.WIDEVINE_UUID, MimeTypes.VIDEO_MP4, Base64.decode(uriString.substring(uriString.indexOf(44)), 0));
        } else if (KEYFORMAT_WIDEVINE_PSSH_JSON.equals(keyFormat)) {
            return new DrmInitData.SchemeData(C0841C.WIDEVINE_UUID, DownloadRequest.TYPE_HLS, Util.getUtf8Bytes(line));
        } else {
            if (!KEYFORMAT_PLAYREADY.equals(keyFormat) || !IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(keyFormatVersions)) {
                return null;
            }
            String uriString2 = parseStringAttr(line, REGEX_URI, variableDefinitions);
            return new DrmInitData.SchemeData(C0841C.PLAYREADY_UUID, MimeTypes.VIDEO_MP4, PsshAtomUtil.buildPsshAtom(C0841C.PLAYREADY_UUID, Base64.decode(uriString2.substring(uriString2.indexOf(44)), 0)));
        }
    }

    private static String parseEncryptionScheme(String method) {
        if (METHOD_SAMPLE_AES_CENC.equals(method) || METHOD_SAMPLE_AES_CTR.equals(method)) {
            return C0841C.CENC_TYPE_cenc;
        }
        return C0841C.CENC_TYPE_cbcs;
    }

    private static int parseIntAttr(String line, Pattern pattern) throws ParserException {
        return Integer.parseInt(parseStringAttr(line, pattern, Collections.emptyMap()));
    }

    private static long parseLongAttr(String line, Pattern pattern) throws ParserException {
        return Long.parseLong(parseStringAttr(line, pattern, Collections.emptyMap()));
    }

    private static double parseDoubleAttr(String line, Pattern pattern) throws ParserException {
        return Double.parseDouble(parseStringAttr(line, pattern, Collections.emptyMap()));
    }

    private static String parseStringAttr(String line, Pattern pattern, Map<String, String> variableDefinitions) throws ParserException {
        String value = parseOptionalStringAttr(line, pattern, variableDefinitions);
        if (value != null) {
            return value;
        }
        String pattern2 = pattern.pattern();
        StringBuilder sb = new StringBuilder(String.valueOf(pattern2).length() + 19 + String.valueOf(line).length());
        sb.append("Couldn't match ");
        sb.append(pattern2);
        sb.append(" in ");
        sb.append(line);
        throw new ParserException(sb.toString());
    }

    @Nullable
    private static String parseOptionalStringAttr(String line, Pattern pattern, Map<String, String> variableDefinitions) {
        return parseOptionalStringAttr(line, pattern, null, variableDefinitions);
    }

    private static String parseOptionalStringAttr(String line, Pattern pattern, String defaultValue, Map<String, String> variableDefinitions) {
        Matcher matcher = pattern.matcher(line);
        String value = matcher.find() ? matcher.group(1) : defaultValue;
        if (variableDefinitions.isEmpty() || value == null) {
            return value;
        }
        return replaceVariableReferences(value, variableDefinitions);
    }

    private static String replaceVariableReferences(String string, Map<String, String> variableDefinitions) {
        Matcher matcher = REGEX_VARIABLE_REFERENCE.matcher(string);
        StringBuffer stringWithReplacements = new StringBuffer();
        while (matcher.find()) {
            String groupName = matcher.group(1);
            if (variableDefinitions.containsKey(groupName)) {
                matcher.appendReplacement(stringWithReplacements, Matcher.quoteReplacement(variableDefinitions.get(groupName)));
            }
        }
        matcher.appendTail(stringWithReplacements);
        return stringWithReplacements.toString();
    }

    private static boolean parseOptionalBooleanAttribute(String line, Pattern pattern, boolean defaultValue) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1).equals(BOOLEAN_TRUE);
        }
        return defaultValue;
    }

    private static Pattern compileBooleanAttrPattern(String attribute) {
        StringBuilder sb = new StringBuilder(String.valueOf(attribute).length() + 9);
        sb.append(attribute);
        sb.append("=(");
        sb.append(BOOLEAN_FALSE);
        sb.append("|");
        sb.append(BOOLEAN_TRUE);
        sb.append(")");
        return Pattern.compile(sb.toString());
    }

    private static class LineIterator {
        private final Queue<String> extraLines;
        private String next;
        private final BufferedReader reader;

        public LineIterator(Queue<String> extraLines2, BufferedReader reader2) {
            this.extraLines = extraLines2;
            this.reader = reader2;
        }

        public boolean hasNext() throws IOException {
            if (this.next != null) {
                return true;
            }
            if (!this.extraLines.isEmpty()) {
                this.next = this.extraLines.poll();
                return true;
            }
            do {
                String readLine = this.reader.readLine();
                this.next = readLine;
                if (readLine == null) {
                    return false;
                }
                this.next = this.next.trim();
            } while (this.next.isEmpty());
            return true;
        }

        public String next() throws IOException {
            if (!hasNext()) {
                return null;
            }
            String result = this.next;
            this.next = null;
            return result;
        }
    }
}
