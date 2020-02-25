package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import android.util.Xml;

import androidx.tvprovider.media.p005tv.TvContractCompat;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;

import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DashManifestParser extends DefaultHandler implements ParsingLoadable.Parser<DashManifest> {
    private static final Pattern CEA_608_ACCESSIBILITY_PATTERN = Pattern.compile("CC([1-4])=.*");
    private static final Pattern CEA_708_ACCESSIBILITY_PATTERN = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final String TAG = "MpdParser";
    private final XmlPullParserFactory xmlParserFactory;

    public DashManifestParser() {
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    public static void maybeSkipTag(XmlPullParser xpp) throws IOException, XmlPullParserException {
        if (XmlPullParserUtil.isStartTag(xpp)) {
            int depth = 1;
            while (depth != 0) {
                xpp.next();
                if (XmlPullParserUtil.isStartTag(xpp)) {
                    depth++;
                } else if (XmlPullParserUtil.isEndTag(xpp)) {
                    depth--;
                }
            }
        }
    }

    private static void filterRedundantIncompleteSchemeDatas(ArrayList<DrmInitData.SchemeData> schemeDatas) {
        for (int i = schemeDatas.size() - 1; i >= 0; i--) {
            DrmInitData.SchemeData schemeData = schemeDatas.get(i);
            if (!schemeData.hasData()) {
                int j = 0;
                while (true) {
                    if (j >= schemeDatas.size()) {
                        break;
                    } else if (schemeDatas.get(j).canReplace(schemeData)) {
                        schemeDatas.remove(i);
                        break;
                    } else {
                        j++;
                    }
                }
            }
        }
    }

    private static String getSampleMimeType(String containerMimeType, String codecs) {
        if (MimeTypes.isAudio(containerMimeType)) {
            return MimeTypes.getAudioMediaMimeType(codecs);
        }
        if (MimeTypes.isVideo(containerMimeType)) {
            return MimeTypes.getVideoMediaMimeType(codecs);
        }
        if (mimeTypeIsRawText(containerMimeType)) {
            return containerMimeType;
        }
        if (!MimeTypes.APPLICATION_MP4.equals(containerMimeType)) {
            if (MimeTypes.APPLICATION_RAWCC.equals(containerMimeType) && codecs != null) {
                if (codecs.contains("cea708")) {
                    return MimeTypes.APPLICATION_CEA708;
                }
                if (codecs.contains("eia608") || codecs.contains("cea608")) {
                    return MimeTypes.APPLICATION_CEA608;
                }
            }
            return null;
        } else if (codecs != null) {
            if (codecs.startsWith("stpp")) {
                return MimeTypes.APPLICATION_TTML;
            }
            if (codecs.startsWith("wvtt")) {
                return MimeTypes.APPLICATION_MP4VTT;
            }
        }
        return null;
    }

    private static boolean mimeTypeIsRawText(String mimeType) {
        return MimeTypes.isText(mimeType) || MimeTypes.APPLICATION_TTML.equals(mimeType) || MimeTypes.APPLICATION_MP4VTT.equals(mimeType) || MimeTypes.APPLICATION_CEA708.equals(mimeType) || MimeTypes.APPLICATION_CEA608.equals(mimeType);
    }

    private static String checkLanguageConsistency(String firstLanguage, String secondLanguage) {
        if (firstLanguage == null) {
            return secondLanguage;
        }
        if (secondLanguage == null) {
            return firstLanguage;
        }
        Assertions.checkState(firstLanguage.equals(secondLanguage));
        return firstLanguage;
    }

    private static int checkContentTypeConsistency(int firstType, int secondType) {
        if (firstType == -1) {
            return secondType;
        }
        if (secondType == -1) {
            return firstType;
        }
        Assertions.checkState(firstType == secondType);
        return firstType;
    }

    protected static Descriptor parseDescriptor(XmlPullParser xpp, String tag) throws XmlPullParserException, IOException {
        String schemeIdUri = parseString(xpp, "schemeIdUri", "");
        String value = parseString(xpp, "value", null);
        String id = parseString(xpp, TtmlNode.ATTR_ID, null);
        do {
            xpp.next();
        } while (!XmlPullParserUtil.isEndTag(xpp, tag));
        return new Descriptor(schemeIdUri, value, id);
    }

    protected static int parseCea608AccessibilityChannel(List<Descriptor> accessibilityDescriptors) {
        for (int i = 0; i < accessibilityDescriptors.size(); i++) {
            Descriptor descriptor = accessibilityDescriptors.get(i);
            if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher accessibilityValueMatcher = CEA_608_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (accessibilityValueMatcher.matches()) {
                    return Integer.parseInt(accessibilityValueMatcher.group(1));
                }
                String valueOf = String.valueOf(descriptor.value);
                Log.m30w(TAG, valueOf.length() != 0 ? "Unable to parse CEA-608 channel number from: ".concat(valueOf) : new String("Unable to parse CEA-608 channel number from: "));
            }
        }
        return -1;
    }

    protected static int parseCea708AccessibilityChannel(List<Descriptor> accessibilityDescriptors) {
        for (int i = 0; i < accessibilityDescriptors.size(); i++) {
            Descriptor descriptor = accessibilityDescriptors.get(i);
            if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher accessibilityValueMatcher = CEA_708_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (accessibilityValueMatcher.matches()) {
                    return Integer.parseInt(accessibilityValueMatcher.group(1));
                }
                String valueOf = String.valueOf(descriptor.value);
                Log.m30w(TAG, valueOf.length() != 0 ? "Unable to parse CEA-708 service block number from: ".concat(valueOf) : new String("Unable to parse CEA-708 service block number from: "));
            }
        }
        return -1;
    }

    protected static String parseEac3SupplementalProperties(List<Descriptor> supplementalProperties) {
        for (int i = 0; i < supplementalProperties.size(); i++) {
            Descriptor descriptor = supplementalProperties.get(i);
            if ("tag:dolby.com,2014:dash:DolbyDigitalPlusExtensionType:2014".equals(descriptor.schemeIdUri) && "ec+3".equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
        }
        return MimeTypes.AUDIO_E_AC3;
    }

    protected static float parseFrameRate(XmlPullParser xpp, float defaultValue) {
        float frameRate = defaultValue;
        String frameRateAttribute = xpp.getAttributeValue(null, "frameRate");
        if (frameRateAttribute == null) {
            return frameRate;
        }
        Matcher frameRateMatcher = FRAME_RATE_PATTERN.matcher(frameRateAttribute);
        if (!frameRateMatcher.matches()) {
            return frameRate;
        }
        int numerator = Integer.parseInt(frameRateMatcher.group(1));
        String denominatorString = frameRateMatcher.group(2);
        if (!TextUtils.isEmpty(denominatorString)) {
            return ((float) numerator) / ((float) Integer.parseInt(denominatorString));
        }
        return (float) numerator;
    }

    protected static long parseDuration(XmlPullParser xpp, String name, long defaultValue) {
        String value = xpp.getAttributeValue(null, name);
        if (value == null) {
            return defaultValue;
        }
        return Util.parseXsDuration(value);
    }

    protected static long parseDateTime(XmlPullParser xpp, String name, long defaultValue) throws ParserException {
        String value = xpp.getAttributeValue(null, name);
        if (value == null) {
            return defaultValue;
        }
        return Util.parseXsDateTime(value);
    }

    protected static String parseBaseUrl(XmlPullParser xpp, String parentBaseUrl) throws XmlPullParserException, IOException {
        xpp.next();
        return UriUtil.resolve(parentBaseUrl, xpp.getText());
    }

    protected static int parseInt(XmlPullParser xpp, String name, int defaultValue) {
        String value = xpp.getAttributeValue(null, name);
        return value == null ? defaultValue : Integer.parseInt(value);
    }

    protected static long parseLong(XmlPullParser xpp, String name, long defaultValue) {
        String value = xpp.getAttributeValue(null, name);
        return value == null ? defaultValue : Long.parseLong(value);
    }

    protected static String parseString(XmlPullParser xpp, String name, String defaultValue) {
        String value = xpp.getAttributeValue(null, name);
        return value == null ? defaultValue : value;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    protected static int parseDolbyChannelConfiguration(XmlPullParser xpp) {
        char c;
        String value = Util.toLowerInvariant(xpp.getAttributeValue(null, "value"));
        if (value == null) {
            return -1;
        }
        switch (value.hashCode()) {
            case 1596796:
                if (value.equals("4000")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2937391:
                if (value.equals("a000")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3094035:
                if (value.equals("f801")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3133436:
                if (value.equals("fa01")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return 1;
        }
        if (c == 1) {
            return 2;
        }
        if (c == 2) {
            return 6;
        }
        if (c != 3) {
            return -1;
        }
        return 8;
    }

    public DashManifest parse(Uri uri, InputStream inputStream) throws IOException {
        try {
            XmlPullParser xpp = this.xmlParserFactory.newPullParser();
            xpp.setInput(inputStream, null);
            if (xpp.next() == 2 && "MPD".equals(xpp.getName())) {
                return parseMediaPresentationDescription(xpp, uri.toString());
            }
            throw new ParserException("inputStream does not contain a valid media presentation description");
        } catch (XmlPullParserException e) {
            throw new ParserException(e);
        }
    }

    /* JADX INFO: Multiple debug info for r7v7 android.util.Pair<com.google.android.exoplayer2.source.dash.manifest.Period, java.lang.Long>: [D('periodWithDurationMs' android.util.Pair<com.google.android.exoplayer2.source.dash.manifest.Period, java.lang.Long>), D('utcTiming' com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement)] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x01bd  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01e2 A[LOOP:0: B:21:0x0077->B:69:0x01e2, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0198 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.source.dash.manifest.DashManifest parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser r42, java.lang.String r43) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r41 = this;
            r0 = r42
            r1 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            java.lang.String r3 = "availabilityStartTime"
            long r24 = parseDateTime(r0, r3, r1)
            java.lang.String r3 = "mediaPresentationDuration"
            long r3 = parseDuration(r0, r3, r1)
            java.lang.String r5 = "minBufferTime"
            long r26 = parseDuration(r0, r5, r1)
            r5 = 0
            java.lang.String r6 = "type"
            java.lang.String r11 = r0.getAttributeValue(r5, r6)
            if (r11 == 0) goto L_0x002d
            java.lang.String r5 = "dynamic"
            boolean r5 = r5.equals(r11)
            if (r5 == 0) goto L_0x002d
            r5 = 1
            goto L_0x002e
        L_0x002d:
            r5 = 0
        L_0x002e:
            r28 = r5
            if (r28 == 0) goto L_0x003a
            java.lang.String r5 = "minimumUpdatePeriod"
            long r5 = parseDuration(r0, r5, r1)
            r12 = r5
            goto L_0x003b
        L_0x003a:
            r12 = r1
        L_0x003b:
            if (r28 == 0) goto L_0x0047
            java.lang.String r5 = "timeShiftBufferDepth"
            long r5 = parseDuration(r0, r5, r1)
            r14 = r5
            goto L_0x0048
        L_0x0047:
            r14 = r1
        L_0x0048:
            if (r28 == 0) goto L_0x0054
            java.lang.String r5 = "suggestedPresentationDelay"
            long r5 = parseDuration(r0, r5, r1)
            r16 = r5
            goto L_0x0056
        L_0x0054:
            r16 = r1
        L_0x0056:
            java.lang.String r5 = "publishTime"
            long r29 = parseDateTime(r0, r5, r1)
            r5 = 0
            r6 = 0
            r7 = 0
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r9 = r8
            if (r28 == 0) goto L_0x006b
            r18 = r1
            goto L_0x006d
        L_0x006b:
            r18 = 0
        L_0x006d:
            r8 = 0
            r10 = 0
            r1 = r18
            r18 = r8
            r8 = r7
            r7 = r6
            r6 = r43
        L_0x0077:
            r42.next()
            r43 = r5
            java.lang.String r5 = "BaseURL"
            boolean r5 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r5)
            if (r5 == 0) goto L_0x00a4
            if (r10 != 0) goto L_0x009a
            java.lang.String r5 = parseBaseUrl(r0, r6)
            r6 = 1
            r34 = r1
            r31 = r5
            r32 = r6
            r2 = r7
            r33 = r8
            r36 = r18
            r1 = r43
            goto L_0x0190
        L_0x009a:
            r22 = r1
            r31 = r6
            r21 = r7
            r32 = r8
            goto L_0x0184
        L_0x00a4:
            java.lang.String r5 = "ProgramInformation"
            boolean r5 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r5)
            if (r5 == 0) goto L_0x00be
            com.google.android.exoplayer2.source.dash.manifest.ProgramInformation r5 = r41.parseProgramInformation(r42)
            r34 = r1
            r1 = r5
            r31 = r6
            r2 = r7
            r33 = r8
            r32 = r10
            r36 = r18
            goto L_0x0190
        L_0x00be:
            java.lang.String r5 = "UTCTiming"
            boolean r5 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r5)
            if (r5 == 0) goto L_0x00d9
            com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement r5 = r41.parseUtcTiming(r42)
            r34 = r1
            r2 = r5
            r31 = r6
            r33 = r8
            r32 = r10
            r36 = r18
            r1 = r43
            goto L_0x0190
        L_0x00d9:
            java.lang.String r5 = "Location"
            boolean r5 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r5)
            if (r5 == 0) goto L_0x00f8
            java.lang.String r5 = r42.nextText()
            android.net.Uri r5 = android.net.Uri.parse(r5)
            r34 = r1
            r33 = r5
            r31 = r6
            r2 = r7
            r32 = r10
            r36 = r18
            r1 = r43
            goto L_0x0190
        L_0x00f8:
            java.lang.String r5 = "Period"
            boolean r5 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r5)
            if (r5 == 0) goto L_0x0179
            if (r18 != 0) goto L_0x0179
            r5 = r41
            r21 = r7
            android.util.Pair r7 = r5.parsePeriod(r0, r6, r1)
            r22 = r1
            java.lang.Object r1 = r7.first
            com.google.android.exoplayer2.source.dash.manifest.Period r1 = (com.google.android.exoplayer2.source.dash.manifest.Period) r1
            r2 = r6
            long r5 = r1.startMs
            r19 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r31 = (r5 > r19 ? 1 : (r5 == r19 ? 0 : -1))
            if (r31 != 0) goto L_0x0146
            if (r28 == 0) goto L_0x0125
            r18 = 1
            r31 = r2
            r32 = r8
            goto L_0x016c
        L_0x0125:
            com.google.android.exoplayer2.ParserException r5 = new com.google.android.exoplayer2.ParserException
            int r6 = r9.size()
            r31 = r2
            r2 = 47
            r32 = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r2)
            java.lang.String r2 = "Unable to determine start of period "
            r8.append(r2)
            r8.append(r6)
            java.lang.String r2 = r8.toString()
            r5.<init>(r2)
            throw r5
        L_0x0146:
            r31 = r2
            r32 = r8
            java.lang.Object r2 = r7.second
            java.lang.Long r2 = (java.lang.Long) r2
            long r5 = r2.longValue()
            r19 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r2 = (r5 > r19 ? 1 : (r5 == r19 ? 0 : -1))
            if (r2 != 0) goto L_0x0162
            r2 = r7
            r7 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            goto L_0x0166
        L_0x0162:
            r2 = r7
            long r7 = r1.startMs
            long r7 = r7 + r5
        L_0x0166:
            r9.add(r1)
            r22 = r7
        L_0x016c:
            r1 = r43
            r36 = r18
            r2 = r21
            r34 = r22
            r33 = r32
            r32 = r10
            goto L_0x0190
        L_0x0179:
            r22 = r1
            r31 = r6
            r21 = r7
            r32 = r8
            maybeSkipTag(r42)
        L_0x0184:
            r1 = r43
            r36 = r18
            r2 = r21
            r34 = r22
            r33 = r32
            r32 = r10
        L_0x0190:
            java.lang.String r5 = "MPD"
            boolean r5 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r5)
            if (r5 == 0) goto L_0x01e2
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x01b5
            int r7 = (r34 > r5 ? 1 : (r34 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x01aa
            r3 = r34
            r37 = r3
            goto L_0x01b7
        L_0x01aa:
            if (r28 == 0) goto L_0x01ad
            goto L_0x01b5
        L_0x01ad:
            com.google.android.exoplayer2.ParserException r5 = new com.google.android.exoplayer2.ParserException
            java.lang.String r6 = "Unable to determine duration of static manifest."
            r5.<init>(r6)
            throw r5
        L_0x01b5:
            r37 = r3
        L_0x01b7:
            boolean r3 = r9.isEmpty()
            if (r3 != 0) goto L_0x01da
            r4 = r41
            r5 = r24
            r7 = r37
            r39 = r9
            r9 = r26
            r40 = r11
            r11 = r28
            r18 = r29
            r20 = r1
            r21 = r2
            r22 = r33
            r23 = r39
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r3 = r4.buildMediaPresentationDescription(r5, r7, r9, r11, r12, r14, r16, r18, r20, r21, r22, r23)
            return r3
        L_0x01da:
            com.google.android.exoplayer2.ParserException r3 = new com.google.android.exoplayer2.ParserException
            java.lang.String r4 = "No periods found."
            r3.<init>(r4)
            throw r3
        L_0x01e2:
            r39 = r9
            r40 = r11
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r5 = r1
            r7 = r2
            r6 = r31
            r10 = r32
            r8 = r33
            r1 = r34
            r18 = r36
            goto L_0x0077
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser, java.lang.String):com.google.android.exoplayer2.source.dash.manifest.DashManifest");
    }

    /* access modifiers changed from: protected */
    public DashManifest buildMediaPresentationDescription(long availabilityStartTime, long durationMs, long minBufferTimeMs, boolean dynamic, long minUpdateTimeMs, long timeShiftBufferDepthMs, long suggestedPresentationDelayMs, long publishTimeMs, ProgramInformation programInformation, UtcTimingElement utcTiming, Uri location, List<Period> periods) {
        return new DashManifest(availabilityStartTime, durationMs, minBufferTimeMs, dynamic, minUpdateTimeMs, timeShiftBufferDepthMs, suggestedPresentationDelayMs, publishTimeMs, programInformation, utcTiming, location, periods);
    }

    /* access modifiers changed from: protected */
    public UtcTimingElement parseUtcTiming(XmlPullParser xpp) {
        return buildUtcTimingElement(xpp.getAttributeValue(null, "schemeIdUri"), xpp.getAttributeValue(null, "value"));
    }

    /* access modifiers changed from: protected */
    public UtcTimingElement buildUtcTimingElement(String schemeIdUri, String value) {
        return new UtcTimingElement(schemeIdUri, value);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00c8 A[LOOP:0: B:1:0x0030->B:26:0x00c8, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00b4 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.util.Pair<com.google.android.exoplayer2.source.dash.manifest.Period, java.lang.Long> parsePeriod(org.xmlpull.v1.XmlPullParser r21, java.lang.String r22, long r23) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r20 = this;
            r6 = r20
            r7 = r21
            r0 = 0
            java.lang.String r1 = "id"
            java.lang.String r8 = r7.getAttributeValue(r0, r1)
            java.lang.String r1 = "start"
            r9 = r23
            long r11 = parseDuration(r7, r1, r9)
            java.lang.String r1 = "duration"
            r2 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            long r13 = parseDuration(r7, r1, r2)
            r1 = 0
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r15 = r2
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r5 = r2
            r2 = 0
            r3 = r1
            r1 = r22
        L_0x0030:
            r21.next()
            java.lang.String r4 = "BaseURL"
            boolean r4 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r4)
            if (r4 == 0) goto L_0x004a
            if (r2 != 0) goto L_0x00a6
            java.lang.String r1 = parseBaseUrl(r7, r1)
            r2 = 1
            r16 = r1
            r18 = r2
            r17 = r3
            goto L_0x00ac
        L_0x004a:
            java.lang.String r4 = "AdaptationSet"
            boolean r4 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r4)
            if (r4 == 0) goto L_0x005a
            com.google.android.exoplayer2.source.dash.manifest.AdaptationSet r4 = r6.parseAdaptationSet(r7, r1, r3)
            r15.add(r4)
            goto L_0x00a6
        L_0x005a:
            java.lang.String r4 = "EventStream"
            boolean r4 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r4)
            if (r4 == 0) goto L_0x006a
            com.google.android.exoplayer2.source.dash.manifest.EventStream r4 = r20.parseEventStream(r21)
            r5.add(r4)
            goto L_0x00a6
        L_0x006a:
            java.lang.String r4 = "SegmentBase"
            boolean r4 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r4)
            if (r4 == 0) goto L_0x007d
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r3 = r6.parseSegmentBase(r7, r0)
            r16 = r1
            r18 = r2
            r17 = r3
            goto L_0x00ac
        L_0x007d:
            java.lang.String r4 = "SegmentList"
            boolean r4 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r4)
            if (r4 == 0) goto L_0x0090
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r3 = r6.parseSegmentList(r7, r0)
            r16 = r1
            r18 = r2
            r17 = r3
            goto L_0x00ac
        L_0x0090:
            java.lang.String r4 = "SegmentTemplate"
            boolean r4 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r4)
            if (r4 == 0) goto L_0x00a3
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r3 = r6.parseSegmentTemplate(r7, r0)
            r16 = r1
            r18 = r2
            r17 = r3
            goto L_0x00ac
        L_0x00a3:
            maybeSkipTag(r21)
        L_0x00a6:
            r16 = r1
            r18 = r2
            r17 = r3
        L_0x00ac:
            java.lang.String r1 = "Period"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r7, r1)
            if (r1 == 0) goto L_0x00c8
            r0 = r20
            r1 = r8
            r2 = r11
            r4 = r15
            r19 = r5
            com.google.android.exoplayer2.source.dash.manifest.Period r0 = r0.buildPeriod(r1, r2, r4, r5)
            java.lang.Long r1 = java.lang.Long.valueOf(r13)
            android.util.Pair r0 = android.util.Pair.create(r0, r1)
            return r0
        L_0x00c8:
            r19 = r5
            r1 = r16
            r3 = r17
            r2 = r18
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parsePeriod(org.xmlpull.v1.XmlPullParser, java.lang.String, long):android.util.Pair");
    }

    /* access modifiers changed from: protected */
    public Period buildPeriod(String id, long startMs, List<AdaptationSet> adaptationSets, List<EventStream> eventStreams) {
        return new Period(id, startMs, adaptationSets, eventStreams);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v3, resolved type: com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v4, resolved type: com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v5, resolved type: com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v4, resolved type: java.lang.String} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x02bc A[LOOP:0: B:1:0x0074->B:57:0x02bc, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0289 A[EDGE_INSN: B:58:0x0289->B:51:0x0289 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.source.dash.manifest.AdaptationSet parseAdaptationSet(org.xmlpull.v1.XmlPullParser r40, java.lang.String r41, com.google.android.exoplayer2.source.dash.manifest.SegmentBase r42) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r39 = this;
            r15 = r39
            r14 = r40
            r0 = -1
            java.lang.String r1 = "id"
            int r16 = parseInt(r14, r1, r0)
            int r1 = r39.parseContentType(r40)
            r13 = 0
            java.lang.String r2 = "mimeType"
            java.lang.String r17 = r14.getAttributeValue(r13, r2)
            java.lang.String r2 = "codecs"
            java.lang.String r18 = r14.getAttributeValue(r13, r2)
            java.lang.String r2 = "width"
            int r19 = parseInt(r14, r2, r0)
            java.lang.String r2 = "height"
            int r20 = parseInt(r14, r2, r0)
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r21 = parseFrameRate(r14, r2)
            r2 = -1
            java.lang.String r3 = "audioSamplingRate"
            int r22 = parseInt(r14, r3, r0)
            java.lang.String r12 = "lang"
            java.lang.String r0 = r14.getAttributeValue(r13, r12)
            java.lang.String r3 = "label"
            java.lang.String r23 = r14.getAttributeValue(r13, r3)
            r3 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r11 = r4
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r10 = r4
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r9 = r4
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r8 = r4
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r7 = r4
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r6 = r4
            r4 = 0
            r5 = r41
            r27 = r42
            r26 = r2
            r25 = r3
            r24 = r4
            r4 = r0
            r3 = r1
        L_0x0074:
            r40.next()
            java.lang.String r0 = "BaseURL"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00b4
            if (r24 != 0) goto L_0x009f
            java.lang.String r0 = parseBaseUrl(r14, r5)
            r1 = 1
            r30 = r0
            r24 = r1
            r29 = r4
            r32 = r7
            r33 = r8
            r34 = r9
            r8 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r7 = r14
            r10 = r25
            r9 = r3
            goto L_0x0281
        L_0x009f:
            r2 = r3
            r29 = r4
            r30 = r5
            r32 = r7
            r33 = r8
            r34 = r9
            r8 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r7 = r14
            goto L_0x027e
        L_0x00b4:
            java.lang.String r0 = "ContentProtection"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00ec
            android.util.Pair r0 = r39.parseContentProtection(r40)
            java.lang.Object r1 = r0.first
            if (r1 == 0) goto L_0x00ca
            java.lang.Object r1 = r0.first
            r25 = r1
            java.lang.String r25 = (java.lang.String) r25
        L_0x00ca:
            java.lang.Object r1 = r0.second
            if (r1 == 0) goto L_0x00d5
            java.lang.Object r1 = r0.second
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r1 = (com.google.android.exoplayer2.drm.DrmInitData.SchemeData) r1
            r11.add(r1)
        L_0x00d5:
            r29 = r4
            r30 = r5
            r32 = r7
            r33 = r8
            r34 = r9
            r8 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r7 = r14
            r10 = r25
            r9 = r3
            goto L_0x0281
        L_0x00ec:
            java.lang.String r0 = "ContentComponent"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x011b
            java.lang.String r0 = r14.getAttributeValue(r13, r12)
            java.lang.String r4 = checkLanguageConsistency(r4, r0)
            int r0 = r39.parseContentType(r40)
            int r0 = checkContentTypeConsistency(r3, r0)
            r29 = r4
            r30 = r5
            r32 = r7
            r33 = r8
            r34 = r9
            r8 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r7 = r14
            r10 = r25
            r9 = r0
            goto L_0x0281
        L_0x011b:
            java.lang.String r0 = "Role"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x013f
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r8.add(r0)
            r2 = r3
            r29 = r4
            r30 = r5
            r32 = r7
            r33 = r8
            r34 = r9
            r8 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r7 = r14
            goto L_0x027e
        L_0x013f:
            java.lang.String r0 = "AudioChannelConfiguration"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x0162
            int r26 = r39.parseAudioChannelConfiguration(r40)
            r29 = r4
            r30 = r5
            r32 = r7
            r33 = r8
            r34 = r9
            r8 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r7 = r14
            r10 = r25
            r9 = r3
            goto L_0x0281
        L_0x0162:
            java.lang.String r0 = "Accessibility"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x0186
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r9.add(r0)
            r2 = r3
            r29 = r4
            r30 = r5
            r32 = r7
            r33 = r8
            r34 = r9
            r8 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r7 = r14
            goto L_0x027e
        L_0x0186:
            java.lang.String r0 = "SupplementalProperty"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x01aa
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r7.add(r0)
            r2 = r3
            r29 = r4
            r30 = r5
            r32 = r7
            r33 = r8
            r34 = r9
            r8 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r7 = r14
            goto L_0x027e
        L_0x01aa:
            java.lang.String r0 = "Representation"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x0204
            r0 = r39
            r1 = r40
            r2 = r5
            r28 = r3
            r3 = r23
            r29 = r4
            r4 = r17
            r30 = r5
            r5 = r18
            r31 = r6
            r6 = r19
            r32 = r7
            r7 = r20
            r33 = r8
            r8 = r21
            r34 = r9
            r9 = r26
            r35 = r10
            r10 = r22
            r36 = r11
            r11 = r29
            r37 = r12
            r12 = r33
            r38 = r13
            r13 = r34
            r14 = r27
            com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo r0 = r0.parseRepresentation(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            com.google.android.exoplayer2.Format r1 = r0.format
            int r1 = r15.getContentType(r1)
            r2 = r28
            int r1 = checkContentTypeConsistency(r2, r1)
            r6 = r31
            r6.add(r0)
            r7 = r40
            r9 = r1
            r10 = r25
            r8 = r35
            goto L_0x0281
        L_0x0204:
            r2 = r3
            r29 = r4
            r30 = r5
            r32 = r7
            r33 = r8
            r34 = r9
            r35 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            java.lang.String r0 = "SegmentBase"
            r7 = r40
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r0)
            if (r0 == 0) goto L_0x0231
            r0 = r27
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r0 = r15.parseSegmentBase(r7, r0)
            r27 = r0
            r9 = r2
            r10 = r25
            r8 = r35
            goto L_0x0281
        L_0x0231:
            java.lang.String r0 = "SegmentList"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r0)
            if (r0 == 0) goto L_0x0249
            r0 = r27
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentList) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r0 = r15.parseSegmentList(r7, r0)
            r27 = r0
            r9 = r2
            r10 = r25
            r8 = r35
            goto L_0x0281
        L_0x0249:
            java.lang.String r0 = "SegmentTemplate"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r0)
            if (r0 == 0) goto L_0x0261
            r0 = r27
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTemplate) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r0 = r15.parseSegmentTemplate(r7, r0)
            r27 = r0
            r9 = r2
            r10 = r25
            r8 = r35
            goto L_0x0281
        L_0x0261:
            java.lang.String r0 = "InbandEventStream"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r7, r0)
            if (r1 == 0) goto L_0x0273
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r7, r0)
            r8 = r35
            r8.add(r0)
            goto L_0x027e
        L_0x0273:
            r8 = r35
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r40)
            if (r0 == 0) goto L_0x027e
            r39.parseAdaptationSetChild(r40)
        L_0x027e:
            r9 = r2
            r10 = r25
        L_0x0281:
            java.lang.String r0 = "AdaptationSet"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r7, r0)
            if (r0 == 0) goto L_0x02bc
            java.util.ArrayList r0 = new java.util.ArrayList
            int r1 = r6.size()
            r0.<init>(r1)
            r11 = r0
            r0 = 0
        L_0x0294:
            int r1 = r6.size()
            if (r0 >= r1) goto L_0x02ad
            java.lang.Object r1 = r6.get(r0)
            com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo r1 = (com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.RepresentationInfo) r1
            r12 = r36
            com.google.android.exoplayer2.source.dash.manifest.Representation r1 = r15.buildRepresentation(r1, r10, r12, r8)
            r11.add(r1)
            int r0 = r0 + 1
            goto L_0x0294
        L_0x02ad:
            r0 = r39
            r1 = r16
            r2 = r9
            r3 = r11
            r4 = r34
            r5 = r32
            com.google.android.exoplayer2.source.dash.manifest.AdaptationSet r0 = r0.buildAdaptationSet(r1, r2, r3, r4, r5)
            return r0
        L_0x02bc:
            r12 = r36
            r14 = r7
            r3 = r9
            r25 = r10
            r11 = r12
            r4 = r29
            r5 = r30
            r7 = r32
            r9 = r34
            r12 = r37
            r13 = r38
            r10 = r8
            r8 = r33
            goto L_0x0074
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseAdaptationSet(org.xmlpull.v1.XmlPullParser, java.lang.String, com.google.android.exoplayer2.source.dash.manifest.SegmentBase):com.google.android.exoplayer2.source.dash.manifest.AdaptationSet");
    }

    /* access modifiers changed from: protected */
    public AdaptationSet buildAdaptationSet(int id, int contentType, List<Representation> representations, List<Descriptor> accessibilityDescriptors, List<Descriptor> supplementalProperties) {
        return new AdaptationSet(id, contentType, representations, accessibilityDescriptors, supplementalProperties);
    }

    /* access modifiers changed from: protected */
    public int parseContentType(XmlPullParser xpp) {
        String contentType = xpp.getAttributeValue(null, "contentType");
        if (TextUtils.isEmpty(contentType)) {
            return -1;
        }
        if ("audio".equals(contentType)) {
            return 1;
        }
        if ("video".equals(contentType)) {
            return 2;
        }
        if ("text".equals(contentType)) {
            return 3;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public int getContentType(Format format) {
        String sampleMimeType = format.sampleMimeType;
        if (TextUtils.isEmpty(sampleMimeType)) {
            return -1;
        }
        if (MimeTypes.isVideo(sampleMimeType)) {
            return 2;
        }
        if (MimeTypes.isAudio(sampleMimeType)) {
            return 1;
        }
        if (mimeTypeIsRawText(sampleMimeType)) {
            return 3;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public Pair<String, DrmInitData.SchemeData> parseContentProtection(XmlPullParser xpp) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = xpp;
        String schemeType = null;
        String licenseServerUrl = null;
        byte[] data = null;
        UUID uuid = null;
        boolean requiresSecureDecoder = false;
        DrmInitData.SchemeData schemeData = null;
        String schemeIdUri = xmlPullParser.getAttributeValue(null, "schemeIdUri");
        if (schemeIdUri != null) {
            String lowerInvariant = Util.toLowerInvariant(schemeIdUri);
            char c = 65535;
            int hashCode = lowerInvariant.hashCode();
            if (hashCode != 489446379) {
                if (hashCode != 755418770) {
                    if (hashCode == 1812765994 && lowerInvariant.equals("urn:mpeg:dash:mp4protection:2011")) {
                        c = 0;
                    }
                } else if (lowerInvariant.equals("urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed")) {
                    c = 2;
                }
            } else if (lowerInvariant.equals("urn:uuid:9a04f079-9840-4286-ab92-e65be0885f95")) {
                c = 1;
            }
            if (c == 0) {
                schemeType = xmlPullParser.getAttributeValue(null, "value");
                String defaultKid = XmlPullParserUtil.getAttributeValueIgnorePrefix(xmlPullParser, "default_KID");
                if (!TextUtils.isEmpty(defaultKid) && !"00000000-0000-0000-0000-000000000000".equals(defaultKid)) {
                    String[] defaultKidStrings = defaultKid.split("\\s+");
                    UUID[] defaultKids = new UUID[defaultKidStrings.length];
                    for (int i = 0; i < defaultKidStrings.length; i++) {
                        defaultKids[i] = UUID.fromString(defaultKidStrings[i]);
                    }
                    data = PsshAtomUtil.buildPsshAtom(C0841C.COMMON_PSSH_UUID, defaultKids, null);
                    uuid = C0841C.COMMON_PSSH_UUID;
                }
            } else if (c == 1) {
                uuid = C0841C.PLAYREADY_UUID;
            } else if (c == 2) {
                uuid = C0841C.WIDEVINE_UUID;
            }
        }
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "ms:laurl")) {
                licenseServerUrl = xmlPullParser.getAttributeValue(null, "licenseUrl");
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "widevine:license")) {
                String robustnessLevel = xmlPullParser.getAttributeValue(null, "robustness_level");
                requiresSecureDecoder = robustnessLevel != null && robustnessLevel.startsWith("HW");
            } else if (data == null && XmlPullParserUtil.isStartTagIgnorePrefix(xmlPullParser, "pssh") && xpp.next() == 4) {
                data = Base64.decode(xpp.getText(), 0);
                uuid = PsshAtomUtil.parseUuid(data);
                if (uuid == null) {
                    Log.m30w(TAG, "Skipping malformed cenc:pssh data");
                    data = null;
                }
            } else if (data != null || !C0841C.PLAYREADY_UUID.equals(uuid) || !XmlPullParserUtil.isStartTag(xmlPullParser, "mspr:pro") || xpp.next() != 4) {
                maybeSkipTag(xpp);
            } else {
                data = PsshAtomUtil.buildPsshAtom(C0841C.PLAYREADY_UUID, Base64.decode(xpp.getText(), 0));
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "ContentProtection"));
        if (uuid != null) {
            schemeData = new DrmInitData.SchemeData(uuid, licenseServerUrl, MimeTypes.VIDEO_MP4, data, requiresSecureDecoder);
        }
        return Pair.create(schemeType, schemeData);
    }

    /* access modifiers changed from: protected */
    public void parseAdaptationSetChild(XmlPullParser xpp) throws XmlPullParserException, IOException {
        maybeSkipTag(xpp);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v3, resolved type: com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v4, resolved type: com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v5, resolved type: com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0192 A[LOOP:0: B:1:0x005c->B:43:0x0192, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x014c A[EDGE_INSN: B:44:0x014c->B:37:0x014c ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.RepresentationInfo parseRepresentation(org.xmlpull.v1.XmlPullParser r33, java.lang.String r34, java.lang.String r35, java.lang.String r36, java.lang.String r37, int r38, int r39, float r40, int r41, int r42, java.lang.String r43, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r44, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r45, com.google.android.exoplayer2.source.dash.manifest.SegmentBase r46) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r32 = this;
            r15 = r32
            r14 = r33
            r0 = 0
            java.lang.String r1 = "id"
            java.lang.String r16 = r14.getAttributeValue(r0, r1)
            java.lang.String r0 = "bandwidth"
            r1 = -1
            int r17 = parseInt(r14, r0, r1)
            java.lang.String r0 = "mimeType"
            r13 = r36
            java.lang.String r18 = parseString(r14, r0, r13)
            java.lang.String r0 = "codecs"
            r12 = r37
            java.lang.String r19 = parseString(r14, r0, r12)
            java.lang.String r0 = "width"
            r11 = r38
            int r20 = parseInt(r14, r0, r11)
            java.lang.String r0 = "height"
            r10 = r39
            int r21 = parseInt(r14, r0, r10)
            r9 = r40
            float r22 = parseFrameRate(r14, r9)
            r0 = r41
            java.lang.String r1 = "audioSamplingRate"
            r8 = r42
            int r23 = parseInt(r14, r1, r8)
            r1 = 0
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r7 = r2
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r6 = r2
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r5 = r2
            r2 = 0
            r3 = r46
            r4 = r1
            r1 = r34
        L_0x005c:
            r33.next()
            r34 = r0
            java.lang.String r0 = "BaseURL"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x0080
            if (r2 != 0) goto L_0x007c
            java.lang.String r0 = parseBaseUrl(r14, r1)
            r1 = 1
            r25 = r34
            r24 = r0
            r26 = r1
            r27 = r3
            r28 = r4
            goto L_0x0144
        L_0x007c:
            r46 = r1
            goto L_0x013a
        L_0x0080:
            java.lang.String r0 = "AudioChannelConfiguration"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x0098
            int r0 = r32.parseAudioChannelConfiguration(r33)
            r25 = r0
            r24 = r1
            r26 = r2
            r27 = r3
            r28 = r4
            goto L_0x0144
        L_0x0098:
            java.lang.String r0 = "SegmentBase"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00b3
            r0 = r3
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r0 = r15.parseSegmentBase(r14, r0)
            r25 = r34
            r27 = r0
            r24 = r1
            r26 = r2
            r28 = r4
            goto L_0x0144
        L_0x00b3:
            java.lang.String r0 = "SegmentList"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00ce
            r0 = r3
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentList) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r0 = r15.parseSegmentList(r14, r0)
            r25 = r34
            r27 = r0
            r24 = r1
            r26 = r2
            r28 = r4
            goto L_0x0144
        L_0x00ce:
            java.lang.String r0 = "SegmentTemplate"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00e8
            r0 = r3
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTemplate) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r0 = r15.parseSegmentTemplate(r14, r0)
            r25 = r34
            r27 = r0
            r24 = r1
            r26 = r2
            r28 = r4
            goto L_0x0144
        L_0x00e8:
            java.lang.String r0 = "ContentProtection"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x0115
            android.util.Pair r0 = r32.parseContentProtection(r33)
            r46 = r1
            java.lang.Object r1 = r0.first
            if (r1 == 0) goto L_0x00ff
            java.lang.Object r1 = r0.first
            r4 = r1
            java.lang.String r4 = (java.lang.String) r4
        L_0x00ff:
            java.lang.Object r1 = r0.second
            if (r1 == 0) goto L_0x010a
            java.lang.Object r1 = r0.second
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r1 = (com.google.android.exoplayer2.drm.DrmInitData.SchemeData) r1
            r7.add(r1)
        L_0x010a:
            r25 = r34
            r24 = r46
            r26 = r2
            r27 = r3
            r28 = r4
            goto L_0x0144
        L_0x0115:
            r46 = r1
            java.lang.String r0 = "InbandEventStream"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x0127
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r6.add(r0)
            goto L_0x013a
        L_0x0127:
            java.lang.String r0 = "SupplementalProperty"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x0137
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r5.add(r0)
            goto L_0x013a
        L_0x0137:
            maybeSkipTag(r33)
        L_0x013a:
            r25 = r34
            r24 = r46
            r26 = r2
            r27 = r3
            r28 = r4
        L_0x0144:
            java.lang.String r0 = "Representation"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r14, r0)
            if (r0 == 0) goto L_0x0192
            r0 = r32
            r1 = r16
            r2 = r35
            r3 = r18
            r4 = r20
            r29 = r5
            r5 = r21
            r30 = r6
            r6 = r22
            r31 = r7
            r7 = r25
            r8 = r23
            r9 = r17
            r10 = r43
            r11 = r44
            r12 = r45
            r13 = r19
            r14 = r29
            com.google.android.exoplayer2.Format r0 = r0.buildFormat(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            if (r27 == 0) goto L_0x017a
            r6 = r27
            goto L_0x0180
        L_0x017a:
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r1 = new com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase
            r1.<init>()
            r6 = r1
        L_0x0180:
            com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo r1 = new com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo
            r10 = -1
            r3 = r1
            r4 = r0
            r5 = r24
            r7 = r28
            r8 = r31
            r9 = r30
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)
            return r1
        L_0x0192:
            r29 = r5
            r30 = r6
            r31 = r7
            r14 = r33
            r13 = r36
            r12 = r37
            r11 = r38
            r10 = r39
            r9 = r40
            r8 = r42
            r1 = r24
            r0 = r25
            r2 = r26
            r3 = r27
            r4 = r28
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseRepresentation(org.xmlpull.v1.XmlPullParser, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, float, int, int, java.lang.String, java.util.List, java.util.List, com.google.android.exoplayer2.source.dash.manifest.SegmentBase):com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo");
    }

    /* access modifiers changed from: protected */
    public Format buildFormat(String id, String label, String containerMimeType, int width, int height, float frameRate, int audioChannels, int audioSamplingRate, int bitrate, String language, List<Descriptor> roleDescriptors, List<Descriptor> accessibilityDescriptors, String codecs, List<Descriptor> supplementalProperties) {
        String sampleMimeType;
        String sampleMimeType2;
        int accessibilityChannel;
        List<Descriptor> list = roleDescriptors;
        String sampleMimeType3 = getSampleMimeType(containerMimeType, codecs);
        int selectionFlags = parseSelectionFlagsFromRoleDescriptors(list);
        int roleFlags = parseRoleFlagsFromRoleDescriptors(list) | parseRoleFlagsFromAccessibilityDescriptors(accessibilityDescriptors);
        if (sampleMimeType3 != null) {
            if (MimeTypes.AUDIO_E_AC3.equals(sampleMimeType3)) {
                sampleMimeType2 = parseEac3SupplementalProperties(supplementalProperties);
            } else {
                sampleMimeType2 = sampleMimeType3;
            }
            if (MimeTypes.isVideo(sampleMimeType2)) {
                return Format.createVideoContainerFormat(id, label, containerMimeType, sampleMimeType2, codecs, bitrate, width, height, frameRate, null, selectionFlags, roleFlags);
            }
            String sampleMimeType4 = sampleMimeType2;
            if (MimeTypes.isAudio(sampleMimeType4)) {
                return Format.createAudioContainerFormat(id, label, containerMimeType, sampleMimeType4, codecs, bitrate, audioChannels, audioSamplingRate, null, selectionFlags, roleFlags, language);
            }
            if (mimeTypeIsRawText(sampleMimeType4)) {
                String sampleMimeType5 = sampleMimeType4;
                if (MimeTypes.APPLICATION_CEA608.equals(sampleMimeType5)) {
                    accessibilityChannel = parseCea608AccessibilityChannel(accessibilityDescriptors);
                } else if (MimeTypes.APPLICATION_CEA708.equals(sampleMimeType5)) {
                    accessibilityChannel = parseCea708AccessibilityChannel(accessibilityDescriptors);
                } else {
                    accessibilityChannel = -1;
                }
                return Format.createTextContainerFormat(id, label, containerMimeType, sampleMimeType5, codecs, bitrate, selectionFlags, roleFlags, language, accessibilityChannel);
            }
            sampleMimeType = sampleMimeType4;
        } else {
            sampleMimeType = sampleMimeType3;
        }
        return Format.createContainerFormat(id, label, containerMimeType, sampleMimeType, codecs, bitrate, selectionFlags, roleFlags, language);
    }

    /* JADX INFO: Multiple debug info for r3v1 java.util.ArrayList<com.google.android.exoplayer2.source.dash.manifest.Descriptor>: [D('drmInitData' com.google.android.exoplayer2.drm.DrmInitData), D('inbandEventStreams' java.util.ArrayList<com.google.android.exoplayer2.source.dash.manifest.Descriptor>)] */
    /* access modifiers changed from: protected */
    public Representation buildRepresentation(RepresentationInfo representationInfo, String extraDrmSchemeType, ArrayList<DrmInitData.SchemeData> extraDrmSchemeDatas, ArrayList<Descriptor> extraInbandEventStreams) {
        Format format = representationInfo.format;
        String drmSchemeType = representationInfo.drmSchemeType != null ? representationInfo.drmSchemeType : extraDrmSchemeType;
        ArrayList<DrmInitData.SchemeData> drmSchemeDatas = representationInfo.drmSchemeDatas;
        drmSchemeDatas.addAll(extraDrmSchemeDatas);
        if (!drmSchemeDatas.isEmpty()) {
            filterRedundantIncompleteSchemeDatas(drmSchemeDatas);
            format = format.copyWithDrmInitData(new DrmInitData(drmSchemeType, drmSchemeDatas));
        }
        ArrayList<Descriptor> inbandEventStreams = representationInfo.inbandEventStreams;
        inbandEventStreams.addAll(extraInbandEventStreams);
        return Representation.newInstance(representationInfo.revisionId, format, representationInfo.baseUrl, representationInfo.segmentBase, inbandEventStreams);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SingleSegmentBase parseSegmentBase(XmlPullParser xpp, SegmentBase.SingleSegmentBase parent) throws XmlPullParserException, IOException {
        long indexLength;
        long indexStart;
        XmlPullParser xmlPullParser = xpp;
        SegmentBase.SingleSegmentBase singleSegmentBase = parent;
        long timescale = parseLong(xmlPullParser, "timescale", singleSegmentBase != null ? singleSegmentBase.timescale : 1);
        long indexLength2 = 0;
        long presentationTimeOffset = parseLong(xmlPullParser, "presentationTimeOffset", singleSegmentBase != null ? singleSegmentBase.presentationTimeOffset : 0);
        long indexStart2 = singleSegmentBase != null ? singleSegmentBase.indexStart : 0;
        if (singleSegmentBase != null) {
            indexLength2 = singleSegmentBase.indexLength;
        }
        RangedUri rangedUri = null;
        String indexRangeText = xmlPullParser.getAttributeValue(null, "indexRange");
        if (indexRangeText != null) {
            String[] indexRange = indexRangeText.split("-");
            long indexStart3 = Long.parseLong(indexRange[0]);
            indexStart = indexStart3;
            indexLength = (Long.parseLong(indexRange[1]) - indexStart3) + 1;
        } else {
            indexLength = indexLength2;
            indexStart = indexStart2;
        }
        if (singleSegmentBase != null) {
            rangedUri = singleSegmentBase.initialization;
        }
        RangedUri initialization = rangedUri;
        while (true) {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                initialization = parseInitialization(xpp);
            } else {
                maybeSkipTag(xpp);
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentBase")) {
                return buildSingleSegmentBase(initialization, timescale, presentationTimeOffset, indexStart, indexLength);
            }
        }
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SingleSegmentBase buildSingleSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, long indexStart, long indexLength) {
        return new SegmentBase.SingleSegmentBase(initialization, timescale, presentationTimeOffset, indexStart, indexLength);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentList parseSegmentList(XmlPullParser xpp, SegmentBase.SegmentList parent) throws XmlPullParserException, IOException {
        List<RangedUri> segments;
        List<SegmentBase.SegmentTimelineElement> timeline;
        XmlPullParser xmlPullParser = xpp;
        SegmentBase.SegmentList segmentList = parent;
        long j = 1;
        long timescale = parseLong(xmlPullParser, "timescale", segmentList != null ? segmentList.timescale : 1);
        long presentationTimeOffset = parseLong(xmlPullParser, "presentationTimeOffset", segmentList != null ? segmentList.presentationTimeOffset : 0);
        long duration = parseLong(xmlPullParser, "duration", segmentList != null ? segmentList.duration : C0841C.TIME_UNSET);
        if (segmentList != null) {
            j = segmentList.startNumber;
        }
        long startNumber = parseLong(xmlPullParser, "startNumber", j);
        RangedUri initialization = null;
        List<SegmentBase.SegmentTimelineElement> timeline2 = null;
        List<RangedUri> segments2 = null;
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                initialization = parseInitialization(xpp);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                timeline2 = parseSegmentTimeline(xpp);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentURL")) {
                if (segments2 == null) {
                    segments2 = new ArrayList<>();
                }
                segments2.add(parseSegmentUrl(xpp));
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentList"));
        if (segmentList != null) {
            initialization = initialization != null ? initialization : segmentList.initialization;
            timeline = timeline2 != null ? timeline2 : segmentList.segmentTimeline;
            segments = segments2 != null ? segments2 : segmentList.mediaSegments;
        } else {
            timeline = timeline2;
            segments = segments2;
        }
        return buildSegmentList(initialization, timescale, presentationTimeOffset, startNumber, duration, timeline, segments);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentList buildSegmentList(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber, long duration, List<SegmentBase.SegmentTimelineElement> timeline, List<RangedUri> segments) {
        return new SegmentBase.SegmentList(initialization, timescale, presentationTimeOffset, startNumber, duration, timeline, segments);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentTemplate parseSegmentTemplate(XmlPullParser xpp, SegmentBase.SegmentTemplate parent) throws XmlPullParserException, IOException {
        List<SegmentBase.SegmentTimelineElement> timeline;
        RangedUri initialization;
        XmlPullParser xmlPullParser = xpp;
        SegmentBase.SegmentTemplate segmentTemplate = parent;
        long j = 1;
        long timescale = parseLong(xmlPullParser, "timescale", segmentTemplate != null ? segmentTemplate.timescale : 1);
        long presentationTimeOffset = parseLong(xmlPullParser, "presentationTimeOffset", segmentTemplate != null ? segmentTemplate.presentationTimeOffset : 0);
        long duration = parseLong(xmlPullParser, "duration", segmentTemplate != null ? segmentTemplate.duration : C0841C.TIME_UNSET);
        if (segmentTemplate != null) {
            j = segmentTemplate.startNumber;
        }
        long startNumber = parseLong(xmlPullParser, "startNumber", j);
        UrlTemplate urlTemplate = null;
        UrlTemplate mediaTemplate = parseUrlTemplate(xmlPullParser, "media", segmentTemplate != null ? segmentTemplate.mediaTemplate : null);
        if (segmentTemplate != null) {
            urlTemplate = segmentTemplate.initializationTemplate;
        }
        UrlTemplate initializationTemplate = parseUrlTemplate(xmlPullParser, "initialization", urlTemplate);
        RangedUri initialization2 = null;
        List<SegmentBase.SegmentTimelineElement> timeline2 = null;
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                initialization2 = parseInitialization(xpp);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                timeline2 = parseSegmentTimeline(xpp);
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTemplate"));
        if (segmentTemplate != null) {
            initialization = initialization2 != null ? initialization2 : segmentTemplate.initialization;
            timeline = timeline2 != null ? timeline2 : segmentTemplate.segmentTimeline;
        } else {
            initialization = initialization2;
            timeline = timeline2;
        }
        return buildSegmentTemplate(initialization, timescale, presentationTimeOffset, startNumber, duration, timeline, initializationTemplate, mediaTemplate);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentTemplate buildSegmentTemplate(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber, long duration, List<SegmentBase.SegmentTimelineElement> timeline, UrlTemplate initializationTemplate, UrlTemplate mediaTemplate) {
        return new SegmentBase.SegmentTemplate(initialization, timescale, presentationTimeOffset, startNumber, duration, timeline, initializationTemplate, mediaTemplate);
    }

    /* access modifiers changed from: protected */
    public EventStream parseEventStream(XmlPullParser xpp) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = xpp;
        String schemeIdUri = parseString(xmlPullParser, "schemeIdUri", "");
        String value = parseString(xmlPullParser, "value", "");
        long timescale = parseLong(xmlPullParser, "timescale", 1);
        List<Pair<Long, EventMessage>> arrayList = new ArrayList<>();
        ByteArrayOutputStream scratchOutputStream = new ByteArrayOutputStream(512);
        while (true) {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Event")) {
                arrayList.add(parseEvent(xpp, schemeIdUri, value, timescale, scratchOutputStream));
            } else {
                maybeSkipTag(xpp);
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "EventStream")) {
                break;
            }
        }
        long[] presentationTimesUs = new long[arrayList.size()];
        EventMessage[] events = new EventMessage[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            Pair<Long, EventMessage> event = (Pair) arrayList.get(i);
            presentationTimesUs[i] = ((Long) event.first).longValue();
            events[i] = (EventMessage) event.second;
        }
        return buildEventStream(schemeIdUri, value, timescale, presentationTimesUs, events);
    }

    /* access modifiers changed from: protected */
    public EventStream buildEventStream(String schemeIdUri, String value, long timescale, long[] presentationTimesUs, EventMessage[] events) {
        return new EventStream(schemeIdUri, value, timescale, presentationTimesUs, events);
    }

    /* access modifiers changed from: protected */
    public Pair<Long, EventMessage> parseEvent(XmlPullParser xpp, String schemeIdUri, String value, long timescale, ByteArrayOutputStream scratchOutputStream) throws IOException, XmlPullParserException {
        XmlPullParser xmlPullParser = xpp;
        long id = parseLong(xmlPullParser, TtmlNode.ATTR_ID, 0);
        long duration = parseLong(xmlPullParser, "duration", C0841C.TIME_UNSET);
        long presentationTime = parseLong(xmlPullParser, "presentationTime", 0);
        long durationMs = Util.scaleLargeTimestamp(duration, 1000, timescale);
        long presentationTimesUs = Util.scaleLargeTimestamp(presentationTime, 1000000, timescale);
        String messageData = parseString(xmlPullParser, "messageData", null);
        return Pair.create(Long.valueOf(presentationTimesUs), buildEvent(schemeIdUri, value, id, durationMs, messageData == null ? parseEventObject(xmlPullParser, scratchOutputStream) : Util.getUtf8Bytes(messageData)));
    }

    /* access modifiers changed from: protected */
    public byte[] parseEventObject(XmlPullParser xpp, ByteArrayOutputStream scratchOutputStream) throws XmlPullParserException, IOException {
        scratchOutputStream.reset();
        XmlSerializer xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(scratchOutputStream, "UTF-8");
        xpp.nextToken();
        while (!XmlPullParserUtil.isEndTag(xpp, "Event")) {
            switch (xpp.getEventType()) {
                case 0:
                    xmlSerializer.startDocument(null, false);
                    break;
                case 1:
                    xmlSerializer.endDocument();
                    break;
                case 2:
                    xmlSerializer.startTag(xpp.getNamespace(), xpp.getName());
                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                        xmlSerializer.attribute(xpp.getAttributeNamespace(i), xpp.getAttributeName(i), xpp.getAttributeValue(i));
                    }
                    break;
                case 3:
                    xmlSerializer.endTag(xpp.getNamespace(), xpp.getName());
                    break;
                case 4:
                    xmlSerializer.text(xpp.getText());
                    break;
                case 5:
                    xmlSerializer.cdsect(xpp.getText());
                    break;
                case 6:
                    xmlSerializer.entityRef(xpp.getText());
                    break;
                case 7:
                    xmlSerializer.ignorableWhitespace(xpp.getText());
                    break;
                case 8:
                    xmlSerializer.processingInstruction(xpp.getText());
                    break;
                case 9:
                    xmlSerializer.comment(xpp.getText());
                    break;
                case 10:
                    xmlSerializer.docdecl(xpp.getText());
                    break;
            }
            xpp.nextToken();
        }
        xmlSerializer.flush();
        return scratchOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    public EventMessage buildEvent(String schemeIdUri, String value, long id, long durationMs, byte[] messageData) {
        return new EventMessage(schemeIdUri, value, durationMs, id, messageData);
    }

    /* access modifiers changed from: protected */
    public List<SegmentBase.SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xpp) throws XmlPullParserException, IOException {
        List<SegmentBase.SegmentTimelineElement> segmentTimeline = new ArrayList<>();
        long elapsedTime = 0;
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, "S")) {
                elapsedTime = parseLong(xpp, "t", elapsedTime);
                long duration = parseLong(xpp, "d", C0841C.TIME_UNSET);
                int count = parseInt(xpp, "r", 0) + 1;
                for (int i = 0; i < count; i++) {
                    segmentTimeline.add(buildSegmentTimelineElement(elapsedTime, duration));
                    elapsedTime += duration;
                }
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xpp, "SegmentTimeline"));
        return segmentTimeline;
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentTimelineElement buildSegmentTimelineElement(long elapsedTime, long duration) {
        return new SegmentBase.SegmentTimelineElement(elapsedTime, duration);
    }

    /* access modifiers changed from: protected */
    public UrlTemplate parseUrlTemplate(XmlPullParser xpp, String name, UrlTemplate defaultValue) {
        String valueString = xpp.getAttributeValue(null, name);
        if (valueString != null) {
            return UrlTemplate.compile(valueString);
        }
        return defaultValue;
    }

    /* access modifiers changed from: protected */
    public RangedUri parseInitialization(XmlPullParser xpp) {
        return parseRangedUrl(xpp, "sourceURL", "range");
    }

    /* access modifiers changed from: protected */
    public RangedUri parseSegmentUrl(XmlPullParser xpp) {
        return parseRangedUrl(xpp, "media", "mediaRange");
    }

    /* access modifiers changed from: protected */
    public RangedUri parseRangedUrl(XmlPullParser xpp, String urlAttribute, String rangeAttribute) {
        long rangeLength;
        long rangeStart;
        String urlText = xpp.getAttributeValue(null, urlAttribute);
        String rangeText = xpp.getAttributeValue(null, rangeAttribute);
        if (rangeText != null) {
            String[] rangeTextArray = rangeText.split("-");
            long rangeStart2 = Long.parseLong(rangeTextArray[0]);
            if (rangeTextArray.length == 2) {
                rangeLength = (Long.parseLong(rangeTextArray[1]) - rangeStart2) + 1;
                rangeStart = rangeStart2;
            } else {
                rangeStart = rangeStart2;
                rangeLength = -1;
            }
        } else {
            rangeStart = 0;
            rangeLength = -1;
        }
        return buildRangedUri(urlText, rangeStart, rangeLength);
    }

    /* access modifiers changed from: protected */
    public RangedUri buildRangedUri(String urlText, long rangeStart, long rangeLength) {
        return new RangedUri(urlText, rangeStart, rangeLength);
    }

    /* access modifiers changed from: protected */
    public ProgramInformation parseProgramInformation(XmlPullParser xpp) throws IOException, XmlPullParserException {
        String title = null;
        String source = null;
        String copyright = null;
        String moreInformationURL = parseString(xpp, "moreInformationURL", null);
        String lang = parseString(xpp, "lang", null);
        do {
            xpp.next();
            if (XmlPullParserUtil.isStartTag(xpp, "Title")) {
                title = xpp.nextText();
            } else if (XmlPullParserUtil.isStartTag(xpp, "Source")) {
                source = xpp.nextText();
            } else if (XmlPullParserUtil.isStartTag(xpp, "Copyright")) {
                copyright = xpp.nextText();
            } else {
                maybeSkipTag(xpp);
            }
        } while (!XmlPullParserUtil.isEndTag(xpp, "ProgramInformation"));
        return new ProgramInformation(title, source, copyright, moreInformationURL, lang);
    }

    /* access modifiers changed from: protected */
    public int parseAudioChannelConfiguration(XmlPullParser xpp) throws XmlPullParserException, IOException {
        String schemeIdUri = parseString(xpp, "schemeIdUri", null);
        int i = -1;
        if ("urn:mpeg:dash:23003:3:audio_channel_configuration:2011".equals(schemeIdUri)) {
            i = parseInt(xpp, "value", -1);
        } else if ("tag:dolby.com,2014:dash:audio_channel_configuration:2011".equals(schemeIdUri)) {
            i = parseDolbyChannelConfiguration(xpp);
        }
        int audioChannels = i;
        do {
            xpp.next();
        } while (!XmlPullParserUtil.isEndTag(xpp, "AudioChannelConfiguration"));
        return audioChannels;
    }

    /* access modifiers changed from: protected */
    public int parseSelectionFlagsFromRoleDescriptors(List<Descriptor> roleDescriptors) {
        for (int i = 0; i < roleDescriptors.size(); i++) {
            Descriptor descriptor = roleDescriptors.get(i);
            if ("urn:mpeg:dash:role:2011".equalsIgnoreCase(descriptor.schemeIdUri) && "main".equals(descriptor.value)) {
                return 1;
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int parseRoleFlagsFromRoleDescriptors(List<Descriptor> roleDescriptors) {
        int result = 0;
        for (int i = 0; i < roleDescriptors.size(); i++) {
            Descriptor descriptor = roleDescriptors.get(i);
            if ("urn:mpeg:dash:role:2011".equalsIgnoreCase(descriptor.schemeIdUri)) {
                result |= parseDashRoleSchemeValue(descriptor.value);
            }
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public int parseRoleFlagsFromAccessibilityDescriptors(List<Descriptor> accessibilityDescriptors) {
        int result = 0;
        for (int i = 0; i < accessibilityDescriptors.size(); i++) {
            Descriptor descriptor = accessibilityDescriptors.get(i);
            if ("urn:mpeg:dash:role:2011".equalsIgnoreCase(descriptor.schemeIdUri)) {
                result |= parseDashRoleSchemeValue(descriptor.value);
            } else if ("urn:tva:metadata:cs:AudioPurposeCS:2007".equalsIgnoreCase(descriptor.schemeIdUri)) {
                result |= parseTvaAudioPurposeCsValue(descriptor.value);
            }
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public int parseDashRoleSchemeValue(String value) {
        if (value == null) {
            return 0;
        }
        char c = 65535;
        switch (value.hashCode()) {
            case -2060497896:
                if (value.equals("subtitle")) {
                    c = 7;
                    break;
                }
                break;
            case -1724546052:
                if (value.equals(TvContractCompat.Channels.COLUMN_DESCRIPTION)) {
                    c = 9;
                    break;
                }
                break;
            case -1580883024:
                if (value.equals("enhanced-audio-intelligibility")) {
                    c = 10;
                    break;
                }
                break;
            case -1408024454:
                if (value.equals("alternate")) {
                    c = 1;
                    break;
                }
                break;
            case 99825:
                if (value.equals("dub")) {
                    c = 4;
                    break;
                }
                break;
            case 3343801:
                if (value.equals("main")) {
                    c = 0;
                    break;
                }
                break;
            case 3530173:
                if (value.equals("sign")) {
                    c = 8;
                    break;
                }
                break;
            case 552573414:
                if (value.equals("caption")) {
                    c = 6;
                    break;
                }
                break;
            case 899152809:
                if (value.equals("commentary")) {
                    c = 3;
                    break;
                }
                break;
            case 1629013393:
                if (value.equals("emergency")) {
                    c = 5;
                    break;
                }
                break;
            case 1855372047:
                if (value.equals("supplementary")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 8;
            case 4:
                return 16;
            case 5:
                return 32;
            case 6:
                return 64;
            case 7:
                return 128;
            case 8:
                return 256;
            case 9:
                return 512;
            case 10:
                return 2048;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: protected */
    public int parseTvaAudioPurposeCsValue(String value) {
        if (value == null) {
            return 0;
        }
        char c = 65535;
        switch (value.hashCode()) {
            case 49:
                if (value.equals(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE)) {
                    c = 0;
                    break;
                }
                break;
            case 50:
                if (value.equals("2")) {
                    c = 1;
                    break;
                }
                break;
            case 51:
                if (value.equals("3")) {
                    c = 2;
                    break;
                }
                break;
            case 52:
                if (value.equals("4")) {
                    c = 3;
                    break;
                }
                break;
            case 54:
                if (value.equals("6")) {
                    c = 4;
                    break;
                }
                break;
        }
        if (c == 0) {
            return 512;
        }
        if (c == 1) {
            return 2048;
        }
        if (c == 2) {
            return 4;
        }
        if (c == 3) {
            return 8;
        }
        if (c != 4) {
            return 0;
        }
        return 1;
    }

    protected static final class RepresentationInfo {
        public final String baseUrl;
        public final ArrayList<DrmInitData.SchemeData> drmSchemeDatas;
        public final String drmSchemeType;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final long revisionId;
        public final SegmentBase segmentBase;

        public RepresentationInfo(Format format2, String baseUrl2, SegmentBase segmentBase2, String drmSchemeType2, ArrayList<DrmInitData.SchemeData> drmSchemeDatas2, ArrayList<Descriptor> inbandEventStreams2, long revisionId2) {
            this.format = format2;
            this.baseUrl = baseUrl2;
            this.segmentBase = segmentBase2;
            this.drmSchemeType = drmSchemeType2;
            this.drmSchemeDatas = drmSchemeDatas2;
            this.inbandEventStreams = inbandEventStreams2;
            this.revisionId = revisionId2;
        }
    }
}
