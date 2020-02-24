package com.google.android.exoplayer2.source.smoothstreaming.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class SsManifestParser implements ParsingLoadable.Parser<SsManifest> {
    private final XmlPullParserFactory xmlParserFactory;

    public SsManifestParser() {
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    public SsManifest parse(Uri uri, InputStream inputStream) throws IOException {
        try {
            XmlPullParser xmlParser = this.xmlParserFactory.newPullParser();
            xmlParser.setInput(inputStream, null);
            return (SsManifest) new SmoothStreamingMediaParser(null, uri.toString()).parse(xmlParser);
        } catch (XmlPullParserException e) {
            throw new ParserException(e);
        }
    }

    public static class MissingFieldException extends ParserException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public MissingFieldException(java.lang.String r4) {
            /*
                r3 = this;
                java.lang.String r0 = java.lang.String.valueOf(r4)
                int r1 = r0.length()
                java.lang.String r2 = "Missing required field: "
                if (r1 == 0) goto L_0x0011
                java.lang.String r0 = r2.concat(r0)
                goto L_0x0016
            L_0x0011:
                java.lang.String r0 = new java.lang.String
                r0.<init>(r2)
            L_0x0016:
                r3.<init>(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.MissingFieldException.<init>(java.lang.String):void");
        }
    }

    private static abstract class ElementParser {
        private final String baseUri;
        private final List<Pair<String, Object>> normalizedAttributes = new LinkedList();
        private final ElementParser parent;
        private final String tag;

        /* access modifiers changed from: protected */
        public abstract Object build();

        public ElementParser(ElementParser parent2, String baseUri2, String tag2) {
            this.parent = parent2;
            this.baseUri = baseUri2;
            this.tag = tag2;
        }

        public final Object parse(XmlPullParser xmlParser) throws XmlPullParserException, IOException {
            boolean foundStartTag = false;
            int skippingElementDepth = 0;
            while (true) {
                int eventType = xmlParser.getEventType();
                if (eventType == 1) {
                    return null;
                }
                if (eventType == 2) {
                    String tagName = xmlParser.getName();
                    if (this.tag.equals(tagName)) {
                        foundStartTag = true;
                        parseStartTag(xmlParser);
                    } else if (foundStartTag) {
                        if (skippingElementDepth > 0) {
                            skippingElementDepth++;
                        } else if (handleChildInline(tagName)) {
                            parseStartTag(xmlParser);
                        } else {
                            ElementParser childElementParser = newChildParser(this, tagName, this.baseUri);
                            if (childElementParser == null) {
                                skippingElementDepth = 1;
                            } else {
                                addChild(childElementParser.parse(xmlParser));
                            }
                        }
                    }
                } else if (eventType != 3) {
                    if (eventType == 4 && foundStartTag && skippingElementDepth == 0) {
                        parseText(xmlParser);
                    }
                } else if (!foundStartTag) {
                    continue;
                } else if (skippingElementDepth > 0) {
                    skippingElementDepth--;
                } else {
                    String tagName2 = xmlParser.getName();
                    parseEndTag(xmlParser);
                    if (!handleChildInline(tagName2)) {
                        return build();
                    }
                }
                xmlParser.next();
            }
        }

        private ElementParser newChildParser(ElementParser parent2, String name, String baseUri2) {
            if (QualityLevelParser.TAG.equals(name)) {
                return new QualityLevelParser(parent2, baseUri2);
            }
            if (ProtectionParser.TAG.equals(name)) {
                return new ProtectionParser(parent2, baseUri2);
            }
            if (StreamIndexParser.TAG.equals(name)) {
                return new StreamIndexParser(parent2, baseUri2);
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public final void putNormalizedAttribute(String key, Object value) {
            this.normalizedAttributes.add(Pair.create(key, value));
        }

        /* access modifiers changed from: protected */
        public final Object getNormalizedAttribute(String key) {
            for (int i = 0; i < this.normalizedAttributes.size(); i++) {
                Pair<String, Object> pair = this.normalizedAttributes.get(i);
                if (((String) pair.first).equals(key)) {
                    return pair.second;
                }
            }
            ElementParser elementParser = this.parent;
            if (elementParser == null) {
                return null;
            }
            return elementParser.getNormalizedAttribute(key);
        }

        /* access modifiers changed from: protected */
        public boolean handleChildInline(String tagName) {
            return false;
        }

        /* access modifiers changed from: protected */
        public void parseStartTag(XmlPullParser xmlParser) throws ParserException {
        }

        /* access modifiers changed from: protected */
        public void parseText(XmlPullParser xmlParser) {
        }

        /* access modifiers changed from: protected */
        public void parseEndTag(XmlPullParser xmlParser) {
        }

        /* access modifiers changed from: protected */
        public void addChild(Object parsedChild) {
        }

        /* access modifiers changed from: protected */
        public final String parseRequiredString(XmlPullParser parser, String key) throws MissingFieldException {
            String value = parser.getAttributeValue(null, key);
            if (value != null) {
                return value;
            }
            throw new MissingFieldException(key);
        }

        /* access modifiers changed from: protected */
        public final int parseInt(XmlPullParser parser, String key, int defaultValue) throws ParserException {
            String value = parser.getAttributeValue(null, key);
            if (value == null) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new ParserException(e);
            }
        }

        /* access modifiers changed from: protected */
        public final int parseRequiredInt(XmlPullParser parser, String key) throws ParserException {
            String value = parser.getAttributeValue(null, key);
            if (value != null) {
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new ParserException(e);
                }
            } else {
                throw new MissingFieldException(key);
            }
        }

        /* access modifiers changed from: protected */
        public final long parseLong(XmlPullParser parser, String key, long defaultValue) throws ParserException {
            String value = parser.getAttributeValue(null, key);
            if (value == null) {
                return defaultValue;
            }
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                throw new ParserException(e);
            }
        }

        /* access modifiers changed from: protected */
        public final long parseRequiredLong(XmlPullParser parser, String key) throws ParserException {
            String value = parser.getAttributeValue(null, key);
            if (value != null) {
                try {
                    return Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new ParserException(e);
                }
            } else {
                throw new MissingFieldException(key);
            }
        }

        /* access modifiers changed from: protected */
        public final boolean parseBoolean(XmlPullParser parser, String key, boolean defaultValue) {
            String value = parser.getAttributeValue(null, key);
            if (value != null) {
                return Boolean.parseBoolean(value);
            }
            return defaultValue;
        }
    }

    private static class SmoothStreamingMediaParser extends ElementParser {
        private static final String KEY_DURATION = "Duration";
        private static final String KEY_DVR_WINDOW_LENGTH = "DVRWindowLength";
        private static final String KEY_IS_LIVE = "IsLive";
        private static final String KEY_LOOKAHEAD_COUNT = "LookaheadCount";
        private static final String KEY_MAJOR_VERSION = "MajorVersion";
        private static final String KEY_MINOR_VERSION = "MinorVersion";
        private static final String KEY_TIME_SCALE = "TimeScale";
        public static final String TAG = "SmoothStreamingMedia";
        private long duration;
        private long dvrWindowLength;
        private boolean isLive;
        private int lookAheadCount = -1;
        private int majorVersion;
        private int minorVersion;
        private SsManifest.ProtectionElement protectionElement = null;
        private final List<SsManifest.StreamElement> streamElements = new LinkedList();
        private long timescale;

        public SmoothStreamingMediaParser(ElementParser parent, String baseUri) {
            super(parent, baseUri, TAG);
        }

        public void parseStartTag(XmlPullParser parser) throws ParserException {
            this.majorVersion = parseRequiredInt(parser, KEY_MAJOR_VERSION);
            this.minorVersion = parseRequiredInt(parser, KEY_MINOR_VERSION);
            this.timescale = parseLong(parser, KEY_TIME_SCALE, 10000000);
            this.duration = parseRequiredLong(parser, KEY_DURATION);
            this.dvrWindowLength = parseLong(parser, KEY_DVR_WINDOW_LENGTH, 0);
            this.lookAheadCount = parseInt(parser, KEY_LOOKAHEAD_COUNT, -1);
            this.isLive = parseBoolean(parser, KEY_IS_LIVE, false);
            putNormalizedAttribute(KEY_TIME_SCALE, Long.valueOf(this.timescale));
        }

        public void addChild(Object child) {
            if (child instanceof SsManifest.StreamElement) {
                this.streamElements.add((SsManifest.StreamElement) child);
            } else if (child instanceof SsManifest.ProtectionElement) {
                Assertions.checkState(this.protectionElement == null);
                this.protectionElement = (SsManifest.ProtectionElement) child;
            }
        }

        public Object build() {
            SsManifest.StreamElement[] streamElementArray = new SsManifest.StreamElement[this.streamElements.size()];
            this.streamElements.toArray(streamElementArray);
            SsManifest.ProtectionElement protectionElement2 = this.protectionElement;
            if (protectionElement2 != null) {
                DrmInitData.SchemeData schemeData = new DrmInitData.SchemeData(protectionElement2.uuid, MimeTypes.VIDEO_MP4, this.protectionElement.data);
                DrmInitData drmInitData = new DrmInitData(schemeData);
                for (SsManifest.StreamElement streamElement : streamElementArray) {
                    int type = streamElement.type;
                    if (type == 2 || type == 1) {
                        Format[] formats = streamElement.formats;
                        for (int i = 0; i < formats.length; i++) {
                            formats[i] = formats[i].copyWithDrmInitData(drmInitData);
                        }
                    }
                }
            }
            return new SsManifest(this.majorVersion, this.minorVersion, this.timescale, this.duration, this.dvrWindowLength, this.lookAheadCount, this.isLive, this.protectionElement, streamElementArray);
        }
    }

    private static class ProtectionParser extends ElementParser {
        private static final int INITIALIZATION_VECTOR_SIZE = 8;
        public static final String KEY_SYSTEM_ID = "SystemID";
        public static final String TAG = "Protection";
        public static final String TAG_PROTECTION_HEADER = "ProtectionHeader";
        private boolean inProtectionHeader;
        private byte[] initData;
        private UUID uuid;

        public ProtectionParser(ElementParser parent, String baseUri) {
            super(parent, baseUri, TAG);
        }

        public boolean handleChildInline(String tag) {
            return TAG_PROTECTION_HEADER.equals(tag);
        }

        public void parseStartTag(XmlPullParser parser) {
            if (TAG_PROTECTION_HEADER.equals(parser.getName())) {
                this.inProtectionHeader = true;
                this.uuid = UUID.fromString(stripCurlyBraces(parser.getAttributeValue(null, KEY_SYSTEM_ID)));
            }
        }

        public void parseText(XmlPullParser parser) {
            if (this.inProtectionHeader) {
                this.initData = Base64.decode(parser.getText(), 0);
            }
        }

        public void parseEndTag(XmlPullParser parser) {
            if (TAG_PROTECTION_HEADER.equals(parser.getName())) {
                this.inProtectionHeader = false;
            }
        }

        public Object build() {
            UUID uuid2 = this.uuid;
            return new SsManifest.ProtectionElement(uuid2, PsshAtomUtil.buildPsshAtom(uuid2, this.initData), buildTrackEncryptionBoxes(this.initData));
        }

        private static TrackEncryptionBox[] buildTrackEncryptionBoxes(byte[] initData2) {
            return new TrackEncryptionBox[]{new TrackEncryptionBox(true, null, 8, getProtectionElementKeyId(initData2), 0, 0, null)};
        }

        /* JADX INFO: Multiple debug info for r1v2 java.lang.String: [D('i' int), D('initDataString' java.lang.String)] */
        private static byte[] getProtectionElementKeyId(byte[] initData2) {
            StringBuilder initDataStringBuilder = new StringBuilder();
            for (int i = 0; i < initData2.length; i += 2) {
                initDataStringBuilder.append((char) initData2[i]);
            }
            String initDataString = initDataStringBuilder.toString();
            byte[] keyId = Base64.decode(initDataString.substring(initDataString.indexOf("<KID>") + 5, initDataString.indexOf("</KID>")), 0);
            swap(keyId, 0, 3);
            swap(keyId, 1, 2);
            swap(keyId, 4, 5);
            swap(keyId, 6, 7);
            return keyId;
        }

        private static void swap(byte[] data, int firstPosition, int secondPosition) {
            byte temp = data[firstPosition];
            data[firstPosition] = data[secondPosition];
            data[secondPosition] = temp;
        }

        private static String stripCurlyBraces(String uuidString) {
            if (uuidString.charAt(0) == '{' && uuidString.charAt(uuidString.length() - 1) == '}') {
                return uuidString.substring(1, uuidString.length() - 1);
            }
            return uuidString;
        }
    }

    private static class StreamIndexParser extends ElementParser {
        private static final String KEY_DISPLAY_HEIGHT = "DisplayHeight";
        private static final String KEY_DISPLAY_WIDTH = "DisplayWidth";
        private static final String KEY_FRAGMENT_DURATION = "d";
        private static final String KEY_FRAGMENT_REPEAT_COUNT = "r";
        private static final String KEY_FRAGMENT_START_TIME = "t";
        private static final String KEY_LANGUAGE = "Language";
        private static final String KEY_MAX_HEIGHT = "MaxHeight";
        private static final String KEY_MAX_WIDTH = "MaxWidth";
        private static final String KEY_NAME = "Name";
        private static final String KEY_SUB_TYPE = "Subtype";
        private static final String KEY_TIME_SCALE = "TimeScale";
        private static final String KEY_TYPE = "Type";
        private static final String KEY_TYPE_AUDIO = "audio";
        private static final String KEY_TYPE_TEXT = "text";
        private static final String KEY_TYPE_VIDEO = "video";
        private static final String KEY_URL = "Url";
        public static final String TAG = "StreamIndex";
        private static final String TAG_STREAM_FRAGMENT = "c";
        private final String baseUri;
        private int displayHeight;
        private int displayWidth;
        private final List<Format> formats = new LinkedList();
        private String language;
        private long lastChunkDuration;
        private int maxHeight;
        private int maxWidth;
        private String name;
        private ArrayList<Long> startTimes;
        private String subType;
        private long timescale;
        private int type;
        private String url;

        public StreamIndexParser(ElementParser parent, String baseUri2) {
            super(parent, baseUri2, TAG);
            this.baseUri = baseUri2;
        }

        public boolean handleChildInline(String tag) {
            return TAG_STREAM_FRAGMENT.equals(tag);
        }

        public void parseStartTag(XmlPullParser parser) throws ParserException {
            if (TAG_STREAM_FRAGMENT.equals(parser.getName())) {
                parseStreamFragmentStartTag(parser);
            } else {
                parseStreamElementStartTag(parser);
            }
        }

        private void parseStreamFragmentStartTag(XmlPullParser parser) throws ParserException {
            int chunkIndex = this.startTimes.size();
            long startTime = parseLong(parser, KEY_FRAGMENT_START_TIME, C0841C.TIME_UNSET);
            if (startTime == C0841C.TIME_UNSET) {
                if (chunkIndex == 0) {
                    startTime = 0;
                } else if (this.lastChunkDuration != -1) {
                    startTime = this.startTimes.get(chunkIndex - 1).longValue() + this.lastChunkDuration;
                } else {
                    throw new ParserException("Unable to infer start time");
                }
            }
            int chunkIndex2 = chunkIndex + 1;
            this.startTimes.add(Long.valueOf(startTime));
            this.lastChunkDuration = parseLong(parser, KEY_FRAGMENT_DURATION, C0841C.TIME_UNSET);
            long repeatCount = parseLong(parser, KEY_FRAGMENT_REPEAT_COUNT, 1);
            if (repeatCount <= 1 || this.lastChunkDuration != C0841C.TIME_UNSET) {
                for (int i = 1; ((long) i) < repeatCount; i++) {
                    chunkIndex2++;
                    this.startTimes.add(Long.valueOf((this.lastChunkDuration * ((long) i)) + startTime));
                }
                return;
            }
            throw new ParserException("Repeated chunk with unspecified duration");
        }

        private void parseStreamElementStartTag(XmlPullParser parser) throws ParserException {
            this.type = parseType(parser);
            putNormalizedAttribute(KEY_TYPE, Integer.valueOf(this.type));
            if (this.type == 3) {
                this.subType = parseRequiredString(parser, KEY_SUB_TYPE);
            } else {
                this.subType = parser.getAttributeValue(null, KEY_SUB_TYPE);
            }
            this.name = parser.getAttributeValue(null, KEY_NAME);
            this.url = parseRequiredString(parser, KEY_URL);
            this.maxWidth = parseInt(parser, KEY_MAX_WIDTH, -1);
            this.maxHeight = parseInt(parser, KEY_MAX_HEIGHT, -1);
            this.displayWidth = parseInt(parser, KEY_DISPLAY_WIDTH, -1);
            this.displayHeight = parseInt(parser, KEY_DISPLAY_HEIGHT, -1);
            this.language = parser.getAttributeValue(null, KEY_LANGUAGE);
            putNormalizedAttribute(KEY_LANGUAGE, this.language);
            this.timescale = (long) parseInt(parser, KEY_TIME_SCALE, -1);
            if (this.timescale == -1) {
                this.timescale = ((Long) getNormalizedAttribute(KEY_TIME_SCALE)).longValue();
            }
            this.startTimes = new ArrayList<>();
        }

        private int parseType(XmlPullParser parser) throws ParserException {
            String value = parser.getAttributeValue(null, KEY_TYPE);
            if (value == null) {
                throw new MissingFieldException(KEY_TYPE);
            } else if ("audio".equalsIgnoreCase(value)) {
                return 1;
            } else {
                if ("video".equalsIgnoreCase(value)) {
                    return 2;
                }
                if ("text".equalsIgnoreCase(value)) {
                    return 3;
                }
                StringBuilder sb = new StringBuilder(String.valueOf(value).length() + 19);
                sb.append("Invalid key value[");
                sb.append(value);
                sb.append("]");
                throw new ParserException(sb.toString());
            }
        }

        public void addChild(Object child) {
            if (child instanceof Format) {
                this.formats.add((Format) child);
            }
        }

        public Object build() {
            Format[] formatArray = new Format[this.formats.size()];
            this.formats.toArray(formatArray);
            return new SsManifest.StreamElement(this.baseUri, this.url, this.type, this.subType, this.timescale, this.name, this.maxWidth, this.maxHeight, this.displayWidth, this.displayHeight, this.language, formatArray, this.startTimes, this.lastChunkDuration);
        }
    }

    private static class QualityLevelParser extends ElementParser {
        private static final String KEY_BITRATE = "Bitrate";
        private static final String KEY_CHANNELS = "Channels";
        private static final String KEY_CODEC_PRIVATE_DATA = "CodecPrivateData";
        private static final String KEY_FOUR_CC = "FourCC";
        private static final String KEY_INDEX = "Index";
        private static final String KEY_LANGUAGE = "Language";
        private static final String KEY_MAX_HEIGHT = "MaxHeight";
        private static final String KEY_MAX_WIDTH = "MaxWidth";
        private static final String KEY_NAME = "Name";
        private static final String KEY_SAMPLING_RATE = "SamplingRate";
        private static final String KEY_TYPE = "Type";
        public static final String TAG = "QualityLevel";
        private Format format;

        public QualityLevelParser(ElementParser parent, String baseUri) {
            super(parent, baseUri, TAG);
        }

        public void parseStartTag(XmlPullParser parser) throws ParserException {
            XmlPullParser xmlPullParser = parser;
            int type = ((Integer) getNormalizedAttribute(KEY_TYPE)).intValue();
            String id = xmlPullParser.getAttributeValue(null, KEY_INDEX);
            String name = (String) getNormalizedAttribute(KEY_NAME);
            int bitrate = parseRequiredInt(xmlPullParser, KEY_BITRATE);
            String sampleMimeType = fourCCToMimeType(parseRequiredString(xmlPullParser, KEY_FOUR_CC));
            if (type == 2) {
                this.format = Format.createVideoContainerFormat(id, name, MimeTypes.VIDEO_MP4, sampleMimeType, null, bitrate, parseRequiredInt(xmlPullParser, KEY_MAX_WIDTH), parseRequiredInt(xmlPullParser, KEY_MAX_HEIGHT), -1.0f, buildCodecSpecificData(xmlPullParser.getAttributeValue(null, KEY_CODEC_PRIVATE_DATA)), 0, 0);
            } else if (type == 1) {
                String sampleMimeType2 = sampleMimeType == null ? MimeTypes.AUDIO_AAC : sampleMimeType;
                int channels = parseRequiredInt(xmlPullParser, KEY_CHANNELS);
                int samplingRate = parseRequiredInt(xmlPullParser, KEY_SAMPLING_RATE);
                List<byte[]> codecSpecificData = buildCodecSpecificData(xmlPullParser.getAttributeValue(null, KEY_CODEC_PRIVATE_DATA));
                if (codecSpecificData.isEmpty() && MimeTypes.AUDIO_AAC.equals(sampleMimeType2)) {
                    codecSpecificData = Collections.singletonList(CodecSpecificDataUtil.buildAacLcAudioSpecificConfig(samplingRate, channels));
                }
                this.format = Format.createAudioContainerFormat(id, name, MimeTypes.AUDIO_MP4, sampleMimeType2, null, bitrate, channels, samplingRate, codecSpecificData, 0, 0, (String) getNormalizedAttribute(KEY_LANGUAGE));
            } else if (type == 3) {
                this.format = Format.createTextContainerFormat(id, name, MimeTypes.APPLICATION_MP4, sampleMimeType, null, bitrate, 0, 0, (String) getNormalizedAttribute(KEY_LANGUAGE));
            } else {
                this.format = Format.createContainerFormat(id, name, MimeTypes.APPLICATION_MP4, sampleMimeType, null, bitrate, 0, 0, null);
            }
        }

        public Object build() {
            return this.format;
        }

        private static List<byte[]> buildCodecSpecificData(String codecSpecificDataString) {
            ArrayList<byte[]> csd = new ArrayList<>();
            if (!TextUtils.isEmpty(codecSpecificDataString)) {
                byte[] codecPrivateData = Util.getBytesFromHexString(codecSpecificDataString);
                byte[][] split = CodecSpecificDataUtil.splitNalUnits(codecPrivateData);
                if (split == null) {
                    csd.add(codecPrivateData);
                } else {
                    Collections.addAll(csd, split);
                }
            }
            return csd;
        }

        private static String fourCCToMimeType(String fourCC) {
            if (fourCC.equalsIgnoreCase("H264") || fourCC.equalsIgnoreCase("X264") || fourCC.equalsIgnoreCase("AVC1") || fourCC.equalsIgnoreCase("DAVC")) {
                return MimeTypes.VIDEO_H264;
            }
            if (fourCC.equalsIgnoreCase("AAC") || fourCC.equalsIgnoreCase("AACL") || fourCC.equalsIgnoreCase("AACH") || fourCC.equalsIgnoreCase("AACP")) {
                return MimeTypes.AUDIO_AAC;
            }
            if (fourCC.equalsIgnoreCase("TTML") || fourCC.equalsIgnoreCase("DFXP")) {
                return MimeTypes.APPLICATION_TTML;
            }
            if (fourCC.equalsIgnoreCase("ac-3") || fourCC.equalsIgnoreCase("dac3")) {
                return MimeTypes.AUDIO_AC3;
            }
            if (fourCC.equalsIgnoreCase("ec-3") || fourCC.equalsIgnoreCase("dec3")) {
                return MimeTypes.AUDIO_E_AC3;
            }
            if (fourCC.equalsIgnoreCase("dtsc")) {
                return MimeTypes.AUDIO_DTS;
            }
            if (fourCC.equalsIgnoreCase("dtsh") || fourCC.equalsIgnoreCase("dtsl")) {
                return MimeTypes.AUDIO_DTS_HD;
            }
            if (fourCC.equalsIgnoreCase("dtse")) {
                return MimeTypes.AUDIO_DTS_EXPRESS;
            }
            if (fourCC.equalsIgnoreCase("opus")) {
                return MimeTypes.AUDIO_OPUS;
            }
            return null;
        }
    }
}
