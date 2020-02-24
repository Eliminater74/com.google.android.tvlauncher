package com.google.android.exoplayer2.text.webvtt;

import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import com.google.android.exoplayer2.text.webvtt.WebvttCue;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WebvttCueParser {
    private static final char CHAR_AMPERSAND = '&';
    private static final char CHAR_GREATER_THAN = '>';
    private static final char CHAR_LESS_THAN = '<';
    private static final char CHAR_SEMI_COLON = ';';
    private static final char CHAR_SLASH = '/';
    private static final char CHAR_SPACE = ' ';
    public static final Pattern CUE_HEADER_PATTERN = Pattern.compile("^(\\S+)\\s+-->\\s+(\\S+)(.*)?$");
    private static final Pattern CUE_SETTING_PATTERN = Pattern.compile("(\\S+?):(\\S+)");
    private static final String ENTITY_AMPERSAND = "amp";
    private static final String ENTITY_GREATER_THAN = "gt";
    private static final String ENTITY_LESS_THAN = "lt";
    private static final String ENTITY_NON_BREAK_SPACE = "nbsp";
    private static final int STYLE_BOLD = 1;
    private static final int STYLE_ITALIC = 2;
    private static final String TAG = "WebvttCueParser";
    private static final String TAG_BOLD = "b";
    private static final String TAG_CLASS = "c";
    private static final String TAG_ITALIC = "i";
    private static final String TAG_LANG = "lang";
    private static final String TAG_UNDERLINE = "u";
    private static final String TAG_VOICE = "v";
    private final StringBuilder textBuilder = new StringBuilder();

    public boolean parseCue(ParsableByteArray webvttData, WebvttCue.Builder builder, List<WebvttCssStyle> styles) {
        String firstLine = webvttData.readLine();
        if (firstLine == null) {
            return false;
        }
        Matcher cueHeaderMatcher = CUE_HEADER_PATTERN.matcher(firstLine);
        if (cueHeaderMatcher.matches()) {
            return parseCue(null, cueHeaderMatcher, webvttData, builder, this.textBuilder, styles);
        }
        String secondLine = webvttData.readLine();
        if (secondLine == null) {
            return false;
        }
        Matcher cueHeaderMatcher2 = CUE_HEADER_PATTERN.matcher(secondLine);
        if (!cueHeaderMatcher2.matches()) {
            return false;
        }
        return parseCue(firstLine.trim(), cueHeaderMatcher2, webvttData, builder, this.textBuilder, styles);
    }

    static void parseCueSettingsList(String cueSettingsList, WebvttCue.Builder builder) {
        Matcher cueSettingMatcher = CUE_SETTING_PATTERN.matcher(cueSettingsList);
        while (cueSettingMatcher.find()) {
            String name = cueSettingMatcher.group(1);
            String value = cueSettingMatcher.group(2);
            try {
                if ("line".equals(name)) {
                    parseLineAttribute(value, builder);
                } else if ("align".equals(name)) {
                    builder.setTextAlignment(parseTextAlignment(value));
                } else if ("position".equals(name)) {
                    parsePositionAttribute(value, builder);
                } else if ("size".equals(name)) {
                    builder.setWidth(WebvttParserUtil.parsePercentage(value));
                } else {
                    StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 21 + String.valueOf(value).length());
                    sb.append("Unknown cue setting ");
                    sb.append(name);
                    sb.append(":");
                    sb.append(value);
                    Log.m30w(TAG, sb.toString());
                }
            } catch (NumberFormatException e) {
                String valueOf = String.valueOf(cueSettingMatcher.group());
                Log.m30w(TAG, valueOf.length() != 0 ? "Skipping bad cue setting: ".concat(valueOf) : new String("Skipping bad cue setting: "));
            }
        }
    }

    static void parseCueText(String id, String markup, WebvttCue.Builder builder, List<WebvttCssStyle> styles) {
        int entityEndIndex;
        SpannableStringBuilder spannedText = new SpannableStringBuilder();
        ArrayDeque<StartTag> startTagStack = new ArrayDeque<>();
        List<StyleMatch> scratchStyleMatches = new ArrayList<>();
        int pos = 0;
        while (pos < markup.length()) {
            char curr = markup.charAt(pos);
            if (curr == '&') {
                int semiColonEndIndex = markup.indexOf(59, pos + 1);
                int spaceEndIndex = markup.indexOf(32, pos + 1);
                if (semiColonEndIndex == -1) {
                    entityEndIndex = spaceEndIndex;
                } else if (spaceEndIndex == -1) {
                    entityEndIndex = semiColonEndIndex;
                } else {
                    entityEndIndex = Math.min(semiColonEndIndex, spaceEndIndex);
                }
                if (entityEndIndex != -1) {
                    applyEntity(markup.substring(pos + 1, entityEndIndex), spannedText);
                    if (entityEndIndex == spaceEndIndex) {
                        spannedText.append((CharSequence) " ");
                    }
                    pos = entityEndIndex + 1;
                } else {
                    spannedText.append(curr);
                    pos++;
                }
            } else if (curr != '<') {
                spannedText.append(curr);
                pos++;
            } else if (pos + 1 >= markup.length()) {
                pos++;
            } else {
                int ltPos = pos;
                boolean isVoidTag = false;
                int i = 1;
                boolean isClosingTag = markup.charAt(ltPos + 1) == '/';
                pos = findEndOfTag(markup, ltPos + 1);
                if (markup.charAt(pos - 2) == '/') {
                    isVoidTag = true;
                }
                if (isClosingTag) {
                    i = 2;
                }
                String fullTagExpression = markup.substring(i + ltPos, isVoidTag ? pos - 2 : pos - 1);
                String tagName = getTagName(fullTagExpression);
                if (tagName != null && isSupportedTag(tagName)) {
                    if (isClosingTag) {
                        while (!startTagStack.isEmpty()) {
                            StartTag startTag = (StartTag) startTagStack.pop();
                            applySpansForTag(id, startTag, spannedText, styles, scratchStyleMatches);
                            if (startTag.name.equals(tagName)) {
                                break;
                            }
                        }
                    } else if (!isVoidTag) {
                        startTagStack.push(StartTag.buildStartTag(fullTagExpression, spannedText.length()));
                    }
                }
            }
        }
        while (!startTagStack.isEmpty()) {
            applySpansForTag(id, (StartTag) startTagStack.pop(), spannedText, styles, scratchStyleMatches);
        }
        applySpansForTag(id, StartTag.buildWholeCueVirtualTag(), spannedText, styles, scratchStyleMatches);
        builder.setText(spannedText);
    }

    private static boolean parseCue(String id, Matcher cueHeaderMatcher, ParsableByteArray webvttData, WebvttCue.Builder builder, StringBuilder textBuilder2, List<WebvttCssStyle> styles) {
        try {
            builder.setStartTime(WebvttParserUtil.parseTimestampUs(cueHeaderMatcher.group(1))).setEndTime(WebvttParserUtil.parseTimestampUs(cueHeaderMatcher.group(2)));
            parseCueSettingsList(cueHeaderMatcher.group(3), builder);
            textBuilder2.setLength(0);
            while (true) {
                String readLine = webvttData.readLine();
                String line = readLine;
                if (!TextUtils.isEmpty(readLine)) {
                    if (textBuilder2.length() > 0) {
                        textBuilder2.append("\n");
                    }
                    textBuilder2.append(line.trim());
                } else {
                    parseCueText(id, textBuilder2.toString(), builder, styles);
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            String valueOf = String.valueOf(cueHeaderMatcher.group());
            Log.m30w(TAG, valueOf.length() != 0 ? "Skipping cue with bad header: ".concat(valueOf) : new String("Skipping cue with bad header: "));
            return false;
        }
    }

    private static void parseLineAttribute(String s, WebvttCue.Builder builder) throws NumberFormatException {
        int commaIndex = s.indexOf(44);
        if (commaIndex != -1) {
            builder.setLineAnchor(parsePositionAnchor(s.substring(commaIndex + 1)));
            s = s.substring(0, commaIndex);
        } else {
            builder.setLineAnchor(Integer.MIN_VALUE);
        }
        if (s.endsWith("%")) {
            builder.setLine(WebvttParserUtil.parsePercentage(s)).setLineType(0);
            return;
        }
        int lineNumber = Integer.parseInt(s);
        if (lineNumber < 0) {
            lineNumber--;
        }
        builder.setLine((float) lineNumber).setLineType(1);
    }

    private static void parsePositionAttribute(String s, WebvttCue.Builder builder) throws NumberFormatException {
        int commaIndex = s.indexOf(44);
        if (commaIndex != -1) {
            builder.setPositionAnchor(parsePositionAnchor(s.substring(commaIndex + 1)));
            s = s.substring(0, commaIndex);
        } else {
            builder.setPositionAnchor(Integer.MIN_VALUE);
        }
        builder.setPosition(WebvttParserUtil.parsePercentage(s));
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private static int parsePositionAnchor(String s) {
        char c;
        switch (s.hashCode()) {
            case -1364013995:
                if (s.equals(TtmlNode.CENTER)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1074341483:
                if (s.equals("middle")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 100571:
                if (s.equals(TtmlNode.END)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (s.equals(TtmlNode.START)) {
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
            return 0;
        }
        if (c == 1 || c == 2) {
            return 1;
        }
        if (c == 3) {
            return 2;
        }
        String valueOf = String.valueOf(s);
        Log.m30w(TAG, valueOf.length() != 0 ? "Invalid anchor value: ".concat(valueOf) : new String("Invalid anchor value: "));
        return Integer.MIN_VALUE;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private static Layout.Alignment parseTextAlignment(String s) {
        char c;
        switch (s.hashCode()) {
            case -1364013995:
                if (s.equals(TtmlNode.CENTER)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1074341483:
                if (s.equals("middle")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 100571:
                if (s.equals(TtmlNode.END)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3317767:
                if (s.equals(TtmlNode.LEFT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 108511772:
                if (s.equals(TtmlNode.RIGHT)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (s.equals(TtmlNode.START)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0 || c == 1) {
            return Layout.Alignment.ALIGN_NORMAL;
        }
        if (c == 2 || c == 3) {
            return Layout.Alignment.ALIGN_CENTER;
        }
        if (c == 4 || c == 5) {
            return Layout.Alignment.ALIGN_OPPOSITE;
        }
        String valueOf = String.valueOf(s);
        Log.m30w(TAG, valueOf.length() != 0 ? "Invalid alignment value: ".concat(valueOf) : new String("Invalid alignment value: "));
        return null;
    }

    private static int findEndOfTag(String markup, int startPos) {
        int index = markup.indexOf(62, startPos);
        return index == -1 ? markup.length() : index + 1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0083  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void applyEntity(java.lang.String r5, android.text.SpannableStringBuilder r6) {
        /*
            int r0 = r5.hashCode()
            r1 = 3309(0xced, float:4.637E-42)
            r2 = 3
            r3 = 2
            r4 = 1
            if (r0 == r1) goto L_0x0038
            r1 = 3464(0xd88, float:4.854E-42)
            if (r0 == r1) goto L_0x002e
            r1 = 96708(0x179c4, float:1.35517E-40)
            if (r0 == r1) goto L_0x0024
            r1 = 3374865(0x337f11, float:4.729193E-39)
            if (r0 == r1) goto L_0x001a
        L_0x0019:
            goto L_0x0042
        L_0x001a:
            java.lang.String r0 = "nbsp"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0019
            r0 = 2
            goto L_0x0043
        L_0x0024:
            java.lang.String r0 = "amp"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0019
            r0 = 3
            goto L_0x0043
        L_0x002e:
            java.lang.String r0 = "lt"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0019
            r0 = 0
            goto L_0x0043
        L_0x0038:
            java.lang.String r0 = "gt"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0019
            r0 = 1
            goto L_0x0043
        L_0x0042:
            r0 = -1
        L_0x0043:
            if (r0 == 0) goto L_0x0083
            if (r0 == r4) goto L_0x007d
            if (r0 == r3) goto L_0x0077
            if (r0 == r2) goto L_0x0071
            java.lang.String r0 = java.lang.String.valueOf(r5)
            int r0 = r0.length()
            int r0 = r0 + 33
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r0)
            java.lang.String r0 = "ignoring unsupported entity: '&"
            r1.append(r0)
            r1.append(r5)
            java.lang.String r0 = ";'"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "WebvttCueParser"
            com.google.android.exoplayer2.util.Log.m30w(r1, r0)
            goto L_0x0089
        L_0x0071:
            r0 = 38
            r6.append(r0)
            goto L_0x0089
        L_0x0077:
            r0 = 32
            r6.append(r0)
            goto L_0x0089
        L_0x007d:
            r0 = 62
            r6.append(r0)
            goto L_0x0089
        L_0x0083:
            r0 = 60
            r6.append(r0)
        L_0x0089:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.webvtt.WebvttCueParser.applyEntity(java.lang.String, android.text.SpannableStringBuilder):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0065 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isSupportedTag(java.lang.String r8) {
        /*
            int r0 = r8.hashCode()
            r1 = 98
            r2 = 0
            r3 = 5
            r4 = 4
            r5 = 3
            r6 = 2
            r7 = 1
            if (r0 == r1) goto L_0x0058
            r1 = 99
            if (r0 == r1) goto L_0x004e
            r1 = 105(0x69, float:1.47E-43)
            if (r0 == r1) goto L_0x0044
            r1 = 3314158(0x3291ee, float:4.644125E-39)
            if (r0 == r1) goto L_0x003a
            r1 = 117(0x75, float:1.64E-43)
            if (r0 == r1) goto L_0x002f
            r1 = 118(0x76, float:1.65E-43)
            if (r0 == r1) goto L_0x0024
        L_0x0023:
            goto L_0x0062
        L_0x0024:
            java.lang.String r0 = "v"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x0023
            r0 = 5
            goto L_0x0063
        L_0x002f:
            java.lang.String r0 = "u"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x0023
            r0 = 4
            goto L_0x0063
        L_0x003a:
            java.lang.String r0 = "lang"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x0023
            r0 = 3
            goto L_0x0063
        L_0x0044:
            java.lang.String r0 = "i"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x0023
            r0 = 2
            goto L_0x0063
        L_0x004e:
            java.lang.String r0 = "c"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x0023
            r0 = 1
            goto L_0x0063
        L_0x0058:
            java.lang.String r0 = "b"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x0023
            r0 = 0
            goto L_0x0063
        L_0x0062:
            r0 = -1
        L_0x0063:
            if (r0 == 0) goto L_0x0070
            if (r0 == r7) goto L_0x0070
            if (r0 == r6) goto L_0x0070
            if (r0 == r5) goto L_0x0070
            if (r0 == r4) goto L_0x0070
            if (r0 == r3) goto L_0x0070
            return r2
        L_0x0070:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.webvtt.WebvttCueParser.isSupportedTag(java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0078 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00a2 A[LOOP:0: B:43:0x00a0->B:44:0x00a2, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void applySpansForTag(java.lang.String r7, com.google.android.exoplayer2.text.webvtt.WebvttCueParser.StartTag r8, android.text.SpannableStringBuilder r9, java.util.List<com.google.android.exoplayer2.text.webvtt.WebvttCssStyle> r10, java.util.List<com.google.android.exoplayer2.text.webvtt.WebvttCueParser.StyleMatch> r11) {
        /*
            int r0 = r8.position
            int r1 = r9.length()
            java.lang.String r2 = r8.name
            int r3 = r2.hashCode()
            r4 = 2
            r5 = 1
            if (r3 == 0) goto L_0x0068
            r6 = 105(0x69, float:1.47E-43)
            if (r3 == r6) goto L_0x005e
            r6 = 3314158(0x3291ee, float:4.644125E-39)
            if (r3 == r6) goto L_0x0054
            r6 = 98
            if (r3 == r6) goto L_0x004a
            r6 = 99
            if (r3 == r6) goto L_0x0040
            r6 = 117(0x75, float:1.64E-43)
            if (r3 == r6) goto L_0x0035
            r6 = 118(0x76, float:1.65E-43)
            if (r3 == r6) goto L_0x002a
        L_0x0029:
            goto L_0x0072
        L_0x002a:
            java.lang.String r3 = "v"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0029
            r2 = 5
            goto L_0x0073
        L_0x0035:
            java.lang.String r3 = "u"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0029
            r2 = 2
            goto L_0x0073
        L_0x0040:
            java.lang.String r3 = "c"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0029
            r2 = 3
            goto L_0x0073
        L_0x004a:
            java.lang.String r3 = "b"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0029
            r2 = 0
            goto L_0x0073
        L_0x0054:
            java.lang.String r3 = "lang"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0029
            r2 = 4
            goto L_0x0073
        L_0x005e:
            java.lang.String r3 = "i"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0029
            r2 = 1
            goto L_0x0073
        L_0x0068:
            java.lang.String r3 = ""
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0029
            r2 = 6
            goto L_0x0073
        L_0x0072:
            r2 = -1
        L_0x0073:
            r3 = 33
            switch(r2) {
                case 0: goto L_0x008c;
                case 1: goto L_0x0083;
                case 2: goto L_0x007a;
                case 3: goto L_0x0079;
                case 4: goto L_0x0079;
                case 5: goto L_0x0079;
                case 6: goto L_0x0079;
                default: goto L_0x0078;
            }
        L_0x0078:
            return
        L_0x0079:
            goto L_0x0095
        L_0x007a:
            android.text.style.UnderlineSpan r2 = new android.text.style.UnderlineSpan
            r2.<init>()
            r9.setSpan(r2, r0, r1, r3)
            goto L_0x0095
        L_0x0083:
            android.text.style.StyleSpan r2 = new android.text.style.StyleSpan
            r2.<init>(r4)
            r9.setSpan(r2, r0, r1, r3)
            goto L_0x0095
        L_0x008c:
            android.text.style.StyleSpan r2 = new android.text.style.StyleSpan
            r2.<init>(r5)
            r9.setSpan(r2, r0, r1, r3)
        L_0x0095:
            r11.clear()
            getApplicableStyles(r10, r7, r8, r11)
            int r2 = r11.size()
            r3 = 0
        L_0x00a0:
            if (r3 >= r2) goto L_0x00b0
            java.lang.Object r4 = r11.get(r3)
            com.google.android.exoplayer2.text.webvtt.WebvttCueParser$StyleMatch r4 = (com.google.android.exoplayer2.text.webvtt.WebvttCueParser.StyleMatch) r4
            com.google.android.exoplayer2.text.webvtt.WebvttCssStyle r4 = r4.style
            applyStyleToText(r9, r4, r0, r1)
            int r3 = r3 + 1
            goto L_0x00a0
        L_0x00b0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.webvtt.WebvttCueParser.applySpansForTag(java.lang.String, com.google.android.exoplayer2.text.webvtt.WebvttCueParser$StartTag, android.text.SpannableStringBuilder, java.util.List, java.util.List):void");
    }

    private static void applyStyleToText(SpannableStringBuilder spannedText, WebvttCssStyle style, int start, int end) {
        if (style != null) {
            if (style.getStyle() != -1) {
                spannedText.setSpan(new StyleSpan(style.getStyle()), start, end, 33);
            }
            if (style.isLinethrough()) {
                spannedText.setSpan(new StrikethroughSpan(), start, end, 33);
            }
            if (style.isUnderline()) {
                spannedText.setSpan(new UnderlineSpan(), start, end, 33);
            }
            if (style.hasFontColor()) {
                spannedText.setSpan(new ForegroundColorSpan(style.getFontColor()), start, end, 33);
            }
            if (style.hasBackgroundColor()) {
                spannedText.setSpan(new BackgroundColorSpan(style.getBackgroundColor()), start, end, 33);
            }
            if (style.getFontFamily() != null) {
                spannedText.setSpan(new TypefaceSpan(style.getFontFamily()), start, end, 33);
            }
            if (style.getTextAlign() != null) {
                spannedText.setSpan(new AlignmentSpan.Standard(style.getTextAlign()), start, end, 33);
            }
            int fontSizeUnit = style.getFontSizeUnit();
            if (fontSizeUnit == 1) {
                spannedText.setSpan(new AbsoluteSizeSpan((int) style.getFontSize(), true), start, end, 33);
            } else if (fontSizeUnit == 2) {
                spannedText.setSpan(new RelativeSizeSpan(style.getFontSize()), start, end, 33);
            } else if (fontSizeUnit == 3) {
                spannedText.setSpan(new RelativeSizeSpan(style.getFontSize() / 100.0f), start, end, 33);
            }
        }
    }

    private static String getTagName(String tagExpression) {
        String tagExpression2 = tagExpression.trim();
        if (tagExpression2.isEmpty()) {
            return null;
        }
        return Util.splitAtFirst(tagExpression2, "[ \\.]")[0];
    }

    private static void getApplicableStyles(List<WebvttCssStyle> declaredStyles, String id, StartTag tag, List<StyleMatch> output) {
        int styleCount = declaredStyles.size();
        for (int i = 0; i < styleCount; i++) {
            WebvttCssStyle style = declaredStyles.get(i);
            int score = style.getSpecificityScore(id, tag.name, tag.classes, tag.voice);
            if (score > 0) {
                output.add(new StyleMatch(score, style));
            }
        }
        Collections.sort(output);
    }

    private static final class StyleMatch implements Comparable<StyleMatch> {
        public final int score;
        public final WebvttCssStyle style;

        public StyleMatch(int score2, WebvttCssStyle style2) {
            this.score = score2;
            this.style = style2;
        }

        public int compareTo(@NonNull StyleMatch another) {
            return this.score - another.score;
        }
    }

    private static final class StartTag {
        private static final String[] NO_CLASSES = new String[0];
        public final String[] classes;
        public final String name;
        public final int position;
        public final String voice;

        private StartTag(String name2, int position2, String voice2, String[] classes2) {
            this.position = position2;
            this.name = name2;
            this.voice = voice2;
            this.classes = classes2;
        }

        public static StartTag buildStartTag(String fullTagExpression, int position2) {
            String voice2;
            String[] classes2;
            String fullTagExpression2 = fullTagExpression.trim();
            if (fullTagExpression2.isEmpty()) {
                return null;
            }
            int voiceStartIndex = fullTagExpression2.indexOf(" ");
            if (voiceStartIndex == -1) {
                voice2 = "";
            } else {
                voice2 = fullTagExpression2.substring(voiceStartIndex).trim();
                fullTagExpression2 = fullTagExpression2.substring(0, voiceStartIndex);
            }
            String[] nameAndClasses = Util.split(fullTagExpression2, "\\.");
            String name2 = nameAndClasses[0];
            if (nameAndClasses.length > 1) {
                classes2 = (String[]) Arrays.copyOfRange(nameAndClasses, 1, nameAndClasses.length);
            } else {
                classes2 = NO_CLASSES;
            }
            return new StartTag(name2, position2, voice2, classes2);
        }

        public static StartTag buildWholeCueVirtualTag() {
            return new StartTag("", 0, "", new String[0]);
        }
    }
}
