package com.google.android.exoplayer2.text.ttml;

import android.text.Layout;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ColorParser;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TtmlDecoder extends SimpleSubtitleDecoder {
    private static final String ATTR_BEGIN = "begin";
    private static final String ATTR_DURATION = "dur";
    private static final String ATTR_END = "end";
    private static final String ATTR_IMAGE = "backgroundImage";
    private static final String ATTR_REGION = "region";
    private static final String ATTR_STYLE = "style";
    private static final Pattern CELL_RESOLUTION = Pattern.compile("^(\\d+) (\\d+)$");
    private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    private static final CellResolution DEFAULT_CELL_RESOLUTION = new CellResolution(32, 15);
    private static final FrameAndTickRate DEFAULT_FRAME_AND_TICK_RATE = new FrameAndTickRate(30.0f, 1, 1);
    private static final int DEFAULT_FRAME_RATE = 30;
    private static final Pattern FONT_SIZE = Pattern.compile("^(([0-9]*.)?[0-9]+)(px|em|%)$");
    private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    private static final Pattern PERCENTAGE_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)% (\\d+\\.?\\d*?)%$");
    private static final Pattern PIXEL_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)px (\\d+\\.?\\d*?)px$");
    private static final String TAG = "TtmlDecoder";
    private static final String TTP = "http://www.w3.org/ns/ttml#parameter";
    private final XmlPullParserFactory xmlParserFactory;

    public TtmlDecoder() {
        super(TAG);
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
            this.xmlParserFactory.setNamespaceAware(true);
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    private static boolean isSupportedTag(String tag) {
        return tag.equals(TtmlNode.TAG_TT) || tag.equals(TtmlNode.TAG_HEAD) || tag.equals("body") || tag.equals(TtmlNode.TAG_DIV) || tag.equals(TtmlNode.TAG_P) || tag.equals(TtmlNode.TAG_SPAN) || tag.equals(TtmlNode.TAG_BR) || tag.equals("style") || tag.equals(TtmlNode.TAG_STYLING) || tag.equals(TtmlNode.TAG_LAYOUT) || tag.equals("region") || tag.equals(TtmlNode.TAG_METADATA) || tag.equals(TtmlNode.TAG_IMAGE) || tag.equals("data") || tag.equals(TtmlNode.TAG_INFORMATION);
    }

    private static void parseFontSize(String expression, TtmlStyle out) throws SubtitleDecoderException {
        Matcher matcher;
        String[] expressions = Util.split(expression, "\\s+");
        if (expressions.length == 1) {
            matcher = FONT_SIZE.matcher(expression);
        } else if (expressions.length == 2) {
            matcher = FONT_SIZE.matcher(expressions[1]);
            Log.m30w(TAG, "Multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
        } else {
            int length = expressions.length;
            StringBuilder sb = new StringBuilder(52);
            sb.append("Invalid number of entries for fontSize: ");
            sb.append(length);
            sb.append(".");
            throw new SubtitleDecoderException(sb.toString());
        }
        if (matcher.matches()) {
            String unit = matcher.group(3);
            char c = 65535;
            int hashCode = unit.hashCode();
            if (hashCode != 37) {
                if (hashCode != 3240) {
                    if (hashCode == 3592 && unit.equals("px")) {
                        c = 0;
                    }
                } else if (unit.equals("em")) {
                    c = 1;
                }
            } else if (unit.equals("%")) {
                c = 2;
            }
            if (c == 0) {
                out.setFontSizeUnit(1);
            } else if (c == 1) {
                out.setFontSizeUnit(2);
            } else if (c == 2) {
                out.setFontSizeUnit(3);
            } else {
                StringBuilder sb2 = new StringBuilder(String.valueOf(unit).length() + 30);
                sb2.append("Invalid unit for fontSize: '");
                sb2.append(unit);
                sb2.append("'.");
                throw new SubtitleDecoderException(sb2.toString());
            }
            out.setFontSize(Float.valueOf(matcher.group(1)).floatValue());
            return;
        }
        StringBuilder sb3 = new StringBuilder(String.valueOf(expression).length() + 36);
        sb3.append("Invalid expression for fontSize: '");
        sb3.append(expression);
        sb3.append("'.");
        throw new SubtitleDecoderException(sb3.toString());
    }

    private static long parseTimeExpression(String time, FrameAndTickRate frameAndTickRate) throws SubtitleDecoderException {
        String str = time;
        FrameAndTickRate frameAndTickRate2 = frameAndTickRate;
        Matcher matcher = CLOCK_TIME.matcher(str);
        if (matcher.matches()) {
            double durationSeconds = (double) (Long.parseLong(matcher.group(1)) * 3600);
            double parseLong = (double) (Long.parseLong(matcher.group(2)) * 60);
            Double.isNaN(durationSeconds);
            Double.isNaN(parseLong);
            double durationSeconds2 = durationSeconds + parseLong;
            double parseLong2 = (double) Long.parseLong(matcher.group(3));
            Double.isNaN(parseLong2);
            double durationSeconds3 = durationSeconds2 + parseLong2;
            String fraction = matcher.group(4);
            double d = 0.0d;
            double durationSeconds4 = durationSeconds3 + (fraction != null ? Double.parseDouble(fraction) : 0.0d);
            String frames = matcher.group(5);
            double durationSeconds5 = durationSeconds4 + (frames != null ? (double) (((float) Long.parseLong(frames)) / frameAndTickRate2.effectiveFrameRate) : 0.0d);
            String subframes = matcher.group(6);
            if (subframes != null) {
                double parseLong3 = (double) Long.parseLong(subframes);
                double d2 = (double) frameAndTickRate2.subFrameRate;
                Double.isNaN(parseLong3);
                Double.isNaN(d2);
                double d3 = parseLong3 / d2;
                double d4 = (double) frameAndTickRate2.effectiveFrameRate;
                Double.isNaN(d4);
                d = d3 / d4;
            }
            return (long) (1000000.0d * (durationSeconds5 + d));
        }
        Matcher matcher2 = OFFSET_TIME.matcher(str);
        if (matcher2.matches()) {
            double offsetSeconds = Double.parseDouble(matcher2.group(1));
            String unit = matcher2.group(2);
            char c = 65535;
            int hashCode = unit.hashCode();
            if (hashCode != 102) {
                if (hashCode != 104) {
                    if (hashCode != 109) {
                        if (hashCode != 3494) {
                            if (hashCode != 115) {
                                if (hashCode == 116 && unit.equals("t")) {
                                    c = 5;
                                }
                            } else if (unit.equals("s")) {
                                c = 2;
                            }
                        } else if (unit.equals("ms")) {
                            c = 3;
                        }
                    } else if (unit.equals("m")) {
                        c = 1;
                    }
                } else if (unit.equals("h")) {
                    c = 0;
                }
            } else if (unit.equals("f")) {
                c = 4;
            }
            if (c == 0) {
                offsetSeconds *= 3600.0d;
            } else if (c == 1) {
                offsetSeconds *= 60.0d;
            } else if (c != 2) {
                if (c == 3) {
                    offsetSeconds /= 1000.0d;
                } else if (c == 4) {
                    double d5 = (double) frameAndTickRate2.effectiveFrameRate;
                    Double.isNaN(d5);
                    offsetSeconds /= d5;
                } else if (c == 5) {
                    double d6 = (double) frameAndTickRate2.tickRate;
                    Double.isNaN(d6);
                    offsetSeconds /= d6;
                }
            }
            return (long) (1000000.0d * offsetSeconds);
        }
        String valueOf = String.valueOf(time);
        throw new SubtitleDecoderException(valueOf.length() != 0 ? "Malformed time expression: ".concat(valueOf) : new String("Malformed time expression: "));
    }

    /* JADX INFO: Multiple debug info for r15v3 int: [D('eventType' int), D('inputStream' java.io.ByteArrayInputStream)] */
    /* JADX INFO: Multiple debug info for r15v4 int: [D('eventType' int), D('inputStream' java.io.ByteArrayInputStream)] */
    /* JADX INFO: Multiple debug info for r10v6 'nodeStack'  java.util.ArrayDeque<com.google.android.exoplayer2.text.ttml.TtmlNode>: [D('nodeStack' java.util.ArrayDeque<com.google.android.exoplayer2.text.ttml.TtmlNode>), D('globalStyles' java.util.Map<java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlStyle>)] */
    /* JADX INFO: Multiple debug info for r10v8 'nodeStack'  java.util.ArrayDeque<com.google.android.exoplayer2.text.ttml.TtmlNode>: [D('nodeStack' java.util.ArrayDeque<com.google.android.exoplayer2.text.ttml.TtmlNode>), D('globalStyles' java.util.Map<java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlStyle>)] */
    /* access modifiers changed from: protected */
    public TtmlSubtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        ByteArrayInputStream inputStream;
        ArrayDeque<TtmlNode> nodeStack;
        Map<String, TtmlStyle> globalStyles;
        TtsExtent ttsExtent;
        CellResolution cellResolution;
        FrameAndTickRate frameAndTickRate;
        Map<String, TtmlStyle> globalStyles2;
        FrameAndTickRate frameAndTickRate2;
        Map<String, TtmlStyle> globalStyles3;
        try {
            XmlPullParser xmlParser = this.xmlParserFactory.newPullParser();
            Map<String, TtmlStyle> globalStyles4 = new HashMap<>();
            Map<String, TtmlRegion> regionMap = new HashMap<>();
            Map<String, String> imageMap = new HashMap<>();
            regionMap.put("", new TtmlRegion(null));
            ByteArrayInputStream inputStream2 = new ByteArrayInputStream(bytes, 0, length);
            xmlParser.setInput(inputStream2, null);
            ArrayDeque<TtmlNode> nodeStack2 = new ArrayDeque<>();
            int eventType = xmlParser.getEventType();
            FrameAndTickRate frameAndTickRate3 = DEFAULT_FRAME_AND_TICK_RATE;
            CellResolution cellResolution2 = DEFAULT_CELL_RESOLUTION;
            TtsExtent ttsExtent2 = null;
            TtmlSubtitle ttmlSubtitle = null;
            int unsupportedNodeDepth = 0;
            int eventType2 = eventType;
            while (eventType2 != 1) {
                TtmlNode parent = (TtmlNode) nodeStack2.peek();
                if (unsupportedNodeDepth == 0) {
                    String name = xmlParser.getName();
                    if (eventType2 == 2) {
                        String name2 = name;
                        if (TtmlNode.TAG_TT.equals(name2)) {
                            FrameAndTickRate frameAndTickRate4 = parseFrameAndTickRates(xmlParser);
                            cellResolution = parseCellResolution(xmlParser, DEFAULT_CELL_RESOLUTION);
                            frameAndTickRate2 = frameAndTickRate4;
                            ttsExtent = parseTtsExtent(xmlParser);
                        } else {
                            cellResolution = cellResolution2;
                            ttsExtent = ttsExtent2;
                            frameAndTickRate2 = frameAndTickRate3;
                        }
                        if (!isSupportedTag(name2)) {
                            String valueOf = String.valueOf(xmlParser.getName());
                            Log.m28i(TAG, valueOf.length() != 0 ? "Ignoring unsupported tag: ".concat(valueOf) : new String("Ignoring unsupported tag: "));
                            unsupportedNodeDepth++;
                            TtmlNode ttmlNode = parent;
                            frameAndTickRate = frameAndTickRate2;
                            globalStyles = globalStyles4;
                            inputStream = inputStream2;
                            int i = eventType2;
                            nodeStack = nodeStack2;
                        } else {
                            if (TtmlNode.TAG_HEAD.equals(name2)) {
                                String str = name2;
                                TtmlNode ttmlNode2 = parent;
                                frameAndTickRate = frameAndTickRate2;
                                inputStream = inputStream2;
                                int i2 = eventType2;
                                globalStyles3 = globalStyles4;
                                nodeStack = nodeStack2;
                                parseHeader(xmlParser, globalStyles4, cellResolution, ttsExtent, regionMap, imageMap);
                            } else {
                                TtmlNode parent2 = parent;
                                frameAndTickRate = frameAndTickRate2;
                                globalStyles3 = globalStyles4;
                                inputStream = inputStream2;
                                nodeStack = nodeStack2;
                                try {
                                    TtmlNode node = parseNode(xmlParser, parent2, regionMap, frameAndTickRate);
                                    nodeStack.push(node);
                                    if (parent2 != null) {
                                        parent2.addChild(node);
                                    }
                                } catch (SubtitleDecoderException e) {
                                    Log.m31w(TAG, "Suppressing parser error", e);
                                    unsupportedNodeDepth++;
                                    globalStyles = globalStyles3;
                                }
                            }
                            globalStyles = globalStyles3;
                        }
                    } else {
                        TtmlNode parent3 = parent;
                        Map<String, TtmlStyle> globalStyles5 = globalStyles4;
                        inputStream = inputStream2;
                        String str2 = name;
                        int eventType3 = eventType2;
                        nodeStack = nodeStack2;
                        if (eventType3 == 4) {
                            parent3.addChild(TtmlNode.buildTextNode(xmlParser.getText()));
                            globalStyles2 = globalStyles5;
                        } else if (eventType3 == 3) {
                            if (xmlParser.getName().equals(TtmlNode.TAG_TT)) {
                                globalStyles = globalStyles5;
                                ttmlSubtitle = new TtmlSubtitle((TtmlNode) nodeStack.peek(), globalStyles, regionMap, imageMap);
                            } else {
                                globalStyles = globalStyles5;
                            }
                            nodeStack.pop();
                            frameAndTickRate = frameAndTickRate3;
                            cellResolution = cellResolution2;
                            ttsExtent = ttsExtent2;
                        } else {
                            globalStyles2 = globalStyles5;
                        }
                        frameAndTickRate = frameAndTickRate3;
                        cellResolution = cellResolution2;
                        ttsExtent = ttsExtent2;
                    }
                    frameAndTickRate3 = frameAndTickRate;
                    cellResolution2 = cellResolution;
                    ttsExtent2 = ttsExtent;
                } else {
                    globalStyles = globalStyles4;
                    inputStream = inputStream2;
                    int eventType4 = eventType2;
                    nodeStack = nodeStack2;
                    if (eventType4 == 2) {
                        unsupportedNodeDepth++;
                    } else if (eventType4 == 3) {
                        unsupportedNodeDepth--;
                    }
                }
                xmlParser.next();
                eventType2 = xmlParser.getEventType();
                nodeStack2 = nodeStack;
                inputStream2 = inputStream;
                globalStyles4 = globalStyles;
            }
            return ttmlSubtitle;
        } catch (XmlPullParserException xppe) {
            throw new SubtitleDecoderException("Unable to decode source", xppe);
        } catch (IOException e2) {
            throw new IllegalStateException("Unexpected error when reading input.", e2);
        }
    }

    private FrameAndTickRate parseFrameAndTickRates(XmlPullParser xmlParser) throws SubtitleDecoderException {
        int frameRate = 30;
        String frameRateString = xmlParser.getAttributeValue(TTP, "frameRate");
        if (frameRateString != null) {
            frameRate = Integer.parseInt(frameRateString);
        }
        float frameRateMultiplier = 1.0f;
        String frameRateMultiplierString = xmlParser.getAttributeValue(TTP, "frameRateMultiplier");
        if (frameRateMultiplierString != null) {
            String[] parts = Util.split(frameRateMultiplierString, " ");
            if (parts.length == 2) {
                frameRateMultiplier = ((float) Integer.parseInt(parts[0])) / ((float) Integer.parseInt(parts[1]));
            } else {
                throw new SubtitleDecoderException("frameRateMultiplier doesn't have 2 parts");
            }
        }
        int subFrameRate = DEFAULT_FRAME_AND_TICK_RATE.subFrameRate;
        String subFrameRateString = xmlParser.getAttributeValue(TTP, "subFrameRate");
        if (subFrameRateString != null) {
            subFrameRate = Integer.parseInt(subFrameRateString);
        }
        int tickRate = DEFAULT_FRAME_AND_TICK_RATE.tickRate;
        String tickRateString = xmlParser.getAttributeValue(TTP, "tickRate");
        if (tickRateString != null) {
            tickRate = Integer.parseInt(tickRateString);
        }
        return new FrameAndTickRate(((float) frameRate) * frameRateMultiplier, subFrameRate, tickRate);
    }

    private CellResolution parseCellResolution(XmlPullParser xmlParser, CellResolution defaultValue) throws SubtitleDecoderException {
        String cellResolution = xmlParser.getAttributeValue(TTP, "cellResolution");
        if (cellResolution == null) {
            return defaultValue;
        }
        Matcher cellResolutionMatcher = CELL_RESOLUTION.matcher(cellResolution);
        if (!cellResolutionMatcher.matches()) {
            String valueOf = String.valueOf(cellResolution);
            Log.m30w(TAG, valueOf.length() != 0 ? "Ignoring malformed cell resolution: ".concat(valueOf) : new String("Ignoring malformed cell resolution: "));
            return defaultValue;
        }
        try {
            int columns = Integer.parseInt(cellResolutionMatcher.group(1));
            int rows = Integer.parseInt(cellResolutionMatcher.group(2));
            if (columns != 0 && rows != 0) {
                return new CellResolution(columns, rows);
            }
            StringBuilder sb = new StringBuilder(47);
            sb.append("Invalid cell resolution ");
            sb.append(columns);
            sb.append(" ");
            sb.append(rows);
            throw new SubtitleDecoderException(sb.toString());
        } catch (NumberFormatException e) {
            String valueOf2 = String.valueOf(cellResolution);
            Log.m30w(TAG, valueOf2.length() != 0 ? "Ignoring malformed cell resolution: ".concat(valueOf2) : new String("Ignoring malformed cell resolution: "));
            return defaultValue;
        }
    }

    private TtsExtent parseTtsExtent(XmlPullParser xmlParser) {
        String ttsExtent = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_TTS_EXTENT);
        if (ttsExtent == null) {
            return null;
        }
        Matcher extentMatcher = PIXEL_COORDINATES.matcher(ttsExtent);
        if (!extentMatcher.matches()) {
            String valueOf = String.valueOf(ttsExtent);
            Log.m30w(TAG, valueOf.length() != 0 ? "Ignoring non-pixel tts extent: ".concat(valueOf) : new String("Ignoring non-pixel tts extent: "));
            return null;
        }
        try {
            return new TtsExtent(Integer.parseInt(extentMatcher.group(1)), Integer.parseInt(extentMatcher.group(2)));
        } catch (NumberFormatException e) {
            String valueOf2 = String.valueOf(ttsExtent);
            Log.m30w(TAG, valueOf2.length() != 0 ? "Ignoring malformed tts extent: ".concat(valueOf2) : new String("Ignoring malformed tts extent: "));
            return null;
        }
    }

    private Map<String, TtmlStyle> parseHeader(XmlPullParser xmlParser, Map<String, TtmlStyle> globalStyles, CellResolution cellResolution, TtsExtent ttsExtent, Map<String, TtmlRegion> globalRegions, Map<String, String> imageMap) throws IOException, XmlPullParserException {
        do {
            xmlParser.next();
            if (XmlPullParserUtil.isStartTag(xmlParser, "style")) {
                String parentStyleId = XmlPullParserUtil.getAttributeValue(xmlParser, "style");
                TtmlStyle style = parseStyleAttributes(xmlParser, new TtmlStyle());
                if (parentStyleId != null) {
                    for (String id : parseStyleIds(parentStyleId)) {
                        style.chain(globalStyles.get(id));
                    }
                }
                if (style.getId() != null) {
                    globalStyles.put(style.getId(), style);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlParser, "region")) {
                TtmlRegion ttmlRegion = parseRegionAttributes(xmlParser, cellResolution, ttsExtent);
                if (ttmlRegion != null) {
                    globalRegions.put(ttmlRegion.f101id, ttmlRegion);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlParser, TtmlNode.TAG_METADATA)) {
                parseMetadata(xmlParser, imageMap);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlParser, TtmlNode.TAG_HEAD));
        return globalStyles;
    }

    private void parseMetadata(XmlPullParser xmlParser, Map<String, String> imageMap) throws IOException, XmlPullParserException {
        String id;
        do {
            xmlParser.next();
            if (XmlPullParserUtil.isStartTag(xmlParser, TtmlNode.TAG_IMAGE) && (id = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_ID)) != null) {
                imageMap.put(id, xmlParser.nextText());
            }
        } while (!XmlPullParserUtil.isEndTag(xmlParser, TtmlNode.TAG_METADATA));
    }

    /* JADX WARNING: Removed duplicated region for block: B:69:0x015d  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0168  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.exoplayer2.text.ttml.TtmlRegion parseRegionAttributes(org.xmlpull.v1.XmlPullParser r23, com.google.android.exoplayer2.text.ttml.TtmlDecoder.CellResolution r24, com.google.android.exoplayer2.text.ttml.TtmlDecoder.TtsExtent r25) {
        /*
            r22 = this;
            r1 = r23
            r2 = r25
            java.lang.String r0 = "id"
            java.lang.String r12 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r1, r0)
            r3 = 0
            if (r12 != 0) goto L_0x000e
            return r3
        L_0x000e:
            java.lang.String r0 = "origin"
            java.lang.String r13 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r1, r0)
            java.lang.String r4 = "TtmlDecoder"
            if (r13 == 0) goto L_0x0207
            java.util.regex.Pattern r0 = com.google.android.exoplayer2.text.ttml.TtmlDecoder.PERCENTAGE_COORDINATES
            java.util.regex.Matcher r5 = r0.matcher(r13)
            java.util.regex.Pattern r0 = com.google.android.exoplayer2.text.ttml.TtmlDecoder.PIXEL_COORDINATES
            java.util.regex.Matcher r6 = r0.matcher(r13)
            boolean r0 = r5.matches()
            java.lang.String r7 = "Ignoring region with malformed origin: "
            java.lang.String r8 = "Ignoring region with missing tts:extent: "
            r9 = 2
            r10 = 1120403456(0x42c80000, float:100.0)
            r11 = 1
            if (r0 == 0) goto L_0x0060
            java.lang.String r0 = r5.group(r11)     // Catch:{ NumberFormatException -> 0x0046 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ NumberFormatException -> 0x0046 }
            float r0 = r0 / r10
            java.lang.String r14 = r5.group(r9)     // Catch:{ NumberFormatException -> 0x0046 }
            float r7 = java.lang.Float.parseFloat(r14)     // Catch:{ NumberFormatException -> 0x0046 }
            float r7 = r7 / r10
            r15 = r0
            goto L_0x009c
        L_0x0046:
            r0 = move-exception
            java.lang.String r8 = java.lang.String.valueOf(r13)
            int r9 = r8.length()
            if (r9 == 0) goto L_0x0056
            java.lang.String r7 = r7.concat(r8)
            goto L_0x005c
        L_0x0056:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r7)
            r7 = r8
        L_0x005c:
            com.google.android.exoplayer2.util.Log.m30w(r4, r7)
            return r3
        L_0x0060:
            boolean r0 = r6.matches()
            if (r0 == 0) goto L_0x01eb
            if (r2 != 0) goto L_0x0080
            java.lang.String r0 = java.lang.String.valueOf(r13)
            int r7 = r0.length()
            if (r7 == 0) goto L_0x0077
            java.lang.String r0 = r8.concat(r0)
            goto L_0x007c
        L_0x0077:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r8)
        L_0x007c:
            com.google.android.exoplayer2.util.Log.m30w(r4, r0)
            return r3
        L_0x0080:
            java.lang.String r0 = r6.group(r11)     // Catch:{ NumberFormatException -> 0x01d1 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x01d1 }
            java.lang.String r14 = r6.group(r9)     // Catch:{ NumberFormatException -> 0x01d1 }
            int r14 = java.lang.Integer.parseInt(r14)     // Catch:{ NumberFormatException -> 0x01d1 }
            float r15 = (float) r0     // Catch:{ NumberFormatException -> 0x01d1 }
            int r3 = r2.width     // Catch:{ NumberFormatException -> 0x01d1 }
            float r3 = (float) r3     // Catch:{ NumberFormatException -> 0x01d1 }
            float r15 = r15 / r3
            float r3 = (float) r14     // Catch:{ NumberFormatException -> 0x01d1 }
            int r7 = r2.height     // Catch:{ NumberFormatException -> 0x01d1 }
            float r7 = (float) r7
            float r7 = r3 / r7
        L_0x009c:
            java.lang.String r0 = "extent"
            java.lang.String r14 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r1, r0)
            if (r14 == 0) goto L_0x01ca
            java.util.regex.Pattern r0 = com.google.android.exoplayer2.text.ttml.TtmlDecoder.PERCENTAGE_COORDINATES
            java.util.regex.Matcher r3 = r0.matcher(r14)
            java.util.regex.Pattern r0 = com.google.android.exoplayer2.text.ttml.TtmlDecoder.PIXEL_COORDINATES
            java.util.regex.Matcher r5 = r0.matcher(r14)
            boolean r0 = r3.matches()
            java.lang.String r6 = "Ignoring region with malformed extent: "
            if (r0 == 0) goto L_0x00e9
            java.lang.String r0 = r3.group(r11)     // Catch:{ NumberFormatException -> 0x00ce }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ NumberFormatException -> 0x00ce }
            float r0 = r0 / r10
            java.lang.String r8 = r3.group(r9)     // Catch:{ NumberFormatException -> 0x00ce }
            float r4 = java.lang.Float.parseFloat(r8)     // Catch:{ NumberFormatException -> 0x00ce }
            float r4 = r4 / r10
            r16 = r4
            goto L_0x0128
        L_0x00ce:
            r0 = move-exception
            java.lang.String r8 = java.lang.String.valueOf(r13)
            int r9 = r8.length()
            if (r9 == 0) goto L_0x00de
            java.lang.String r6 = r6.concat(r8)
            goto L_0x00e4
        L_0x00de:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r6)
            r6 = r8
        L_0x00e4:
            com.google.android.exoplayer2.util.Log.m30w(r4, r6)
            r4 = 0
            return r4
        L_0x00e9:
            boolean r0 = r5.matches()
            if (r0 == 0) goto L_0x01ae
            if (r2 != 0) goto L_0x010a
            java.lang.String r0 = java.lang.String.valueOf(r13)
            int r6 = r0.length()
            if (r6 == 0) goto L_0x0100
            java.lang.String r0 = r8.concat(r0)
            goto L_0x0105
        L_0x0100:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r8)
        L_0x0105:
            com.google.android.exoplayer2.util.Log.m30w(r4, r0)
            r4 = 0
            return r4
        L_0x010a:
            java.lang.String r0 = r5.group(r11)     // Catch:{ NumberFormatException -> 0x0193 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0193 }
            java.lang.String r8 = r5.group(r9)     // Catch:{ NumberFormatException -> 0x0193 }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ NumberFormatException -> 0x0193 }
            float r9 = (float) r0     // Catch:{ NumberFormatException -> 0x0193 }
            int r10 = r2.width     // Catch:{ NumberFormatException -> 0x0193 }
            float r10 = (float) r10     // Catch:{ NumberFormatException -> 0x0193 }
            float r9 = r9 / r10
            float r10 = (float) r8     // Catch:{ NumberFormatException -> 0x0193 }
            int r4 = r2.height     // Catch:{ NumberFormatException -> 0x0193 }
            float r4 = (float) r4
            float r4 = r10 / r4
            r16 = r4
            r0 = r9
        L_0x0128:
            r3 = 0
            java.lang.String r4 = "displayAlign"
            java.lang.String r17 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValue(r1, r4)
            if (r17 == 0) goto L_0x0173
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r17)
            r5 = -1
            int r6 = r4.hashCode()
            r8 = -1364013995(0xffffffffaeb2cc55, float:-8.1307995E-11)
            if (r6 == r8) goto L_0x0150
            r8 = 92734940(0x58705dc, float:1.2697491E-35)
            if (r6 == r8) goto L_0x0146
        L_0x0145:
            goto L_0x015a
        L_0x0146:
            java.lang.String r6 = "after"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x0145
            r4 = 1
            goto L_0x015b
        L_0x0150:
            java.lang.String r6 = "center"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x0145
            r4 = 0
            goto L_0x015b
        L_0x015a:
            r4 = -1
        L_0x015b:
            if (r4 == 0) goto L_0x0168
            if (r4 == r11) goto L_0x0160
            goto L_0x0173
        L_0x0160:
            r3 = 2
            float r7 = r7 + r16
            r19 = r3
            r18 = r7
            goto L_0x0177
        L_0x0168:
            r3 = 1
            r4 = 1073741824(0x40000000, float:2.0)
            float r4 = r16 / r4
            float r7 = r7 + r4
            r19 = r3
            r18 = r7
            goto L_0x0177
        L_0x0173:
            r19 = r3
            r18 = r7
        L_0x0177:
            r3 = 1065353216(0x3f800000, float:1.0)
            r11 = r24
            int r4 = r11.rows
            float r4 = (float) r4
            float r20 = r3 / r4
            com.google.android.exoplayer2.text.ttml.TtmlRegion r21 = new com.google.android.exoplayer2.text.ttml.TtmlRegion
            r7 = 0
            r10 = 1
            r3 = r21
            r4 = r12
            r5 = r15
            r6 = r18
            r8 = r19
            r9 = r0
            r11 = r20
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11)
            return r21
        L_0x0193:
            r0 = move-exception
            java.lang.String r8 = java.lang.String.valueOf(r13)
            int r9 = r8.length()
            if (r9 == 0) goto L_0x01a3
            java.lang.String r6 = r6.concat(r8)
            goto L_0x01a9
        L_0x01a3:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r6)
            r6 = r8
        L_0x01a9:
            com.google.android.exoplayer2.util.Log.m30w(r4, r6)
            r4 = 0
            return r4
        L_0x01ae:
            java.lang.String r0 = "Ignoring region with unsupported extent: "
            java.lang.String r6 = java.lang.String.valueOf(r13)
            int r8 = r6.length()
            if (r8 == 0) goto L_0x01bf
            java.lang.String r0 = r0.concat(r6)
            goto L_0x01c5
        L_0x01bf:
            java.lang.String r6 = new java.lang.String
            r6.<init>(r0)
            r0 = r6
        L_0x01c5:
            com.google.android.exoplayer2.util.Log.m30w(r4, r0)
            r6 = 0
            return r6
        L_0x01ca:
            r6 = 0
            java.lang.String r0 = "Ignoring region without an extent"
            com.google.android.exoplayer2.util.Log.m30w(r4, r0)
            return r6
        L_0x01d1:
            r0 = move-exception
            java.lang.String r3 = java.lang.String.valueOf(r13)
            int r8 = r3.length()
            if (r8 == 0) goto L_0x01e1
            java.lang.String r3 = r7.concat(r3)
            goto L_0x01e6
        L_0x01e1:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r7)
        L_0x01e6:
            com.google.android.exoplayer2.util.Log.m30w(r4, r3)
            r3 = 0
            return r3
        L_0x01eb:
            java.lang.String r0 = "Ignoring region with unsupported origin: "
            java.lang.String r3 = java.lang.String.valueOf(r13)
            int r7 = r3.length()
            if (r7 == 0) goto L_0x01fc
            java.lang.String r0 = r0.concat(r3)
            goto L_0x0202
        L_0x01fc:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r0)
            r0 = r3
        L_0x0202:
            com.google.android.exoplayer2.util.Log.m30w(r4, r0)
            r3 = 0
            return r3
        L_0x0207:
            java.lang.String r0 = "Ignoring region without an origin"
            com.google.android.exoplayer2.util.Log.m30w(r4, r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseRegionAttributes(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlDecoder$CellResolution, com.google.android.exoplayer2.text.ttml.TtmlDecoder$TtsExtent):com.google.android.exoplayer2.text.ttml.TtmlRegion");
    }

    private String[] parseStyleIds(String parentStyleIds) {
        String parentStyleIds2 = parentStyleIds.trim();
        return parentStyleIds2.isEmpty() ? new String[0] : Util.split(parentStyleIds2, "\\s+");
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private TtmlStyle parseStyleAttributes(XmlPullParser parser, TtmlStyle style) {
        char c;
        int attributeCount = parser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeValue = parser.getAttributeValue(i);
            String attributeName = parser.getAttributeName(i);
            char c2 = 65535;
            switch (attributeName.hashCode()) {
                case -1550943582:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_FONT_STYLE)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1224696685:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_FONT_FAMILY)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1065511464:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_TEXT_ALIGN)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -879295043:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_TEXT_DECORATION)) {
                        c = 8;
                        break;
                    }
                    c = 65535;
                    break;
                case -734428249:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_FONT_WEIGHT)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 3355:
                    if (attributeName.equals(TtmlNode.ATTR_ID)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 94842723:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_COLOR)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 365601008:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_FONT_SIZE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1287124693:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_BACKGROUND_COLOR)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (!"style".equals(parser.getName())) {
                        break;
                    } else {
                        style = createIfNull(style).setId(attributeValue);
                        break;
                    }
                case 1:
                    style = createIfNull(style);
                    try {
                        style.setBackgroundColor(ColorParser.parseTtmlColor(attributeValue));
                        break;
                    } catch (IllegalArgumentException e) {
                        String valueOf = String.valueOf(attributeValue);
                        Log.m30w(TAG, valueOf.length() != 0 ? "Failed parsing background value: ".concat(valueOf) : new String("Failed parsing background value: "));
                        break;
                    }
                case 2:
                    style = createIfNull(style);
                    try {
                        style.setFontColor(ColorParser.parseTtmlColor(attributeValue));
                        break;
                    } catch (IllegalArgumentException e2) {
                        String valueOf2 = String.valueOf(attributeValue);
                        Log.m30w(TAG, valueOf2.length() != 0 ? "Failed parsing color value: ".concat(valueOf2) : new String("Failed parsing color value: "));
                        break;
                    }
                case 3:
                    style = createIfNull(style).setFontFamily(attributeValue);
                    break;
                case 4:
                    try {
                        style = createIfNull(style);
                        parseFontSize(attributeValue, style);
                        break;
                    } catch (SubtitleDecoderException e3) {
                        String valueOf3 = String.valueOf(attributeValue);
                        Log.m30w(TAG, valueOf3.length() != 0 ? "Failed parsing fontSize value: ".concat(valueOf3) : new String("Failed parsing fontSize value: "));
                        break;
                    }
                case 5:
                    style = createIfNull(style).setBold(TtmlNode.BOLD.equalsIgnoreCase(attributeValue));
                    break;
                case 6:
                    style = createIfNull(style).setItalic(TtmlNode.ITALIC.equalsIgnoreCase(attributeValue));
                    break;
                case 7:
                    String lowerInvariant = Util.toLowerInvariant(attributeValue);
                    switch (lowerInvariant.hashCode()) {
                        case -1364013995:
                            if (lowerInvariant.equals(TtmlNode.CENTER)) {
                                c2 = 4;
                                break;
                            }
                            break;
                        case 100571:
                            if (lowerInvariant.equals("end")) {
                                c2 = 3;
                                break;
                            }
                            break;
                        case 3317767:
                            if (lowerInvariant.equals(TtmlNode.LEFT)) {
                                c2 = 0;
                                break;
                            }
                            break;
                        case 108511772:
                            if (lowerInvariant.equals(TtmlNode.RIGHT)) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 109757538:
                            if (lowerInvariant.equals(TtmlNode.START)) {
                                c2 = 1;
                                break;
                            }
                            break;
                    }
                    if (c2 != 0) {
                        if (c2 != 1) {
                            if (c2 != 2) {
                                if (c2 != 3) {
                                    if (c2 == 4) {
                                        style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_CENTER);
                                        break;
                                    } else {
                                        break;
                                    }
                                } else {
                                    style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_OPPOSITE);
                                    break;
                                }
                            } else {
                                style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_OPPOSITE);
                                break;
                            }
                        } else {
                            style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_NORMAL);
                            break;
                        }
                    } else {
                        style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_NORMAL);
                        break;
                    }
                case 8:
                    String lowerInvariant2 = Util.toLowerInvariant(attributeValue);
                    switch (lowerInvariant2.hashCode()) {
                        case -1461280213:
                            if (lowerInvariant2.equals(TtmlNode.NO_UNDERLINE)) {
                                c2 = 3;
                                break;
                            }
                            break;
                        case -1026963764:
                            if (lowerInvariant2.equals(TtmlNode.UNDERLINE)) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 913457136:
                            if (lowerInvariant2.equals(TtmlNode.NO_LINETHROUGH)) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 1679736913:
                            if (lowerInvariant2.equals(TtmlNode.LINETHROUGH)) {
                                c2 = 0;
                                break;
                            }
                            break;
                    }
                    if (c2 != 0) {
                        if (c2 != 1) {
                            if (c2 != 2) {
                                if (c2 == 3) {
                                    style = createIfNull(style).setUnderline(false);
                                    break;
                                } else {
                                    break;
                                }
                            } else {
                                style = createIfNull(style).setUnderline(true);
                                break;
                            }
                        } else {
                            style = createIfNull(style).setLinethrough(false);
                            break;
                        }
                    } else {
                        style = createIfNull(style).setLinethrough(true);
                        break;
                    }
            }
        }
        return style;
    }

    private TtmlStyle createIfNull(TtmlStyle style) {
        return style == null ? new TtmlStyle() : style;
    }

    /* JADX INFO: Multiple debug info for r13v2 java.lang.String: [D('attr' java.lang.String), D('attributeCount' int)] */
    /* JADX INFO: Multiple debug info for r11v4 java.lang.String: [D('value' java.lang.String), D('imageId' java.lang.String)] */
    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private TtmlNode parseNode(XmlPullParser parser, TtmlNode parent, Map<String, TtmlRegion> regionMap, FrameAndTickRate frameAndTickRate) throws SubtitleDecoderException {
        long endTime;
        char c;
        TtmlDecoder ttmlDecoder = this;
        XmlPullParser xmlPullParser = parser;
        TtmlNode ttmlNode = parent;
        FrameAndTickRate frameAndTickRate2 = frameAndTickRate;
        long duration = C0841C.TIME_UNSET;
        long startTime = C0841C.TIME_UNSET;
        long endTime2 = C0841C.TIME_UNSET;
        String regionId = "";
        String value = null;
        String[] styleIds = null;
        int attributeCount = parser.getAttributeCount();
        TtmlStyle style = ttmlDecoder.parseStyleAttributes(xmlPullParser, null);
        int i = 0;
        while (i < attributeCount) {
            int attributeCount2 = attributeCount;
            String attr = xmlPullParser.getAttributeName(i);
            String imageId = value;
            String imageId2 = xmlPullParser.getAttributeValue(i);
            switch (attr.hashCode()) {
                case -934795532:
                    if (attr.equals("region")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 99841:
                    if (attr.equals(ATTR_DURATION)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 100571:
                    if (attr.equals("end")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 93616297:
                    if (attr.equals(ATTR_BEGIN)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 109780401:
                    if (attr.equals("style")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1292595405:
                    if (attr.equals(ATTR_IMAGE)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                startTime = parseTimeExpression(imageId2, frameAndTickRate2);
                value = imageId;
            } else if (c == 1) {
                endTime2 = parseTimeExpression(imageId2, frameAndTickRate2);
                value = imageId;
            } else if (c != 2) {
                if (c == 3) {
                    String[] ids = ttmlDecoder.parseStyleIds(imageId2);
                    if (ids.length > 0) {
                        styleIds = ids;
                        value = imageId;
                    }
                } else if (c != 4) {
                    if (c == 5) {
                        if (imageId2.startsWith("#")) {
                            value = imageId2.substring(1);
                        }
                    }
                } else if (regionMap.containsKey(imageId2)) {
                    regionId = imageId2;
                    value = imageId;
                }
                value = imageId;
            } else {
                duration = parseTimeExpression(imageId2, frameAndTickRate2);
                value = imageId;
            }
            i++;
            ttmlDecoder = this;
            xmlPullParser = parser;
            attributeCount = attributeCount2;
        }
        String imageId3 = value;
        if (!(ttmlNode == null || ttmlNode.startTimeUs == C0841C.TIME_UNSET)) {
            if (startTime != C0841C.TIME_UNSET) {
                startTime += ttmlNode.startTimeUs;
            }
            if (endTime2 != C0841C.TIME_UNSET) {
                endTime2 += ttmlNode.startTimeUs;
            }
        }
        if (endTime2 == C0841C.TIME_UNSET) {
            if (duration != C0841C.TIME_UNSET) {
                endTime = startTime + duration;
            } else if (!(ttmlNode == null || ttmlNode.endTimeUs == C0841C.TIME_UNSET)) {
                endTime = ttmlNode.endTimeUs;
            }
            return TtmlNode.buildNode(parser.getName(), startTime, endTime, style, styleIds, regionId, imageId3);
        }
        endTime = endTime2;
        return TtmlNode.buildNode(parser.getName(), startTime, endTime, style, styleIds, regionId, imageId3);
    }

    private static final class FrameAndTickRate {
        final float effectiveFrameRate;
        final int subFrameRate;
        final int tickRate;

        FrameAndTickRate(float effectiveFrameRate2, int subFrameRate2, int tickRate2) {
            this.effectiveFrameRate = effectiveFrameRate2;
            this.subFrameRate = subFrameRate2;
            this.tickRate = tickRate2;
        }
    }

    private static final class CellResolution {
        final int columns;
        final int rows;

        CellResolution(int columns2, int rows2) {
            this.columns = columns2;
            this.rows = rows2;
        }
    }

    private static final class TtsExtent {
        final int height;
        final int width;

        TtsExtent(int width2, int height2) {
            this.width = width2;
            this.height = height2;
        }
    }
}
