package com.google.android.exoplayer2.text.subrip;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SubripDecoder extends SimpleSubtitleDecoder {
    private static final String ALIGN_BOTTOM_LEFT = "{\\an1}";
    private static final String ALIGN_BOTTOM_MID = "{\\an2}";
    private static final String ALIGN_BOTTOM_RIGHT = "{\\an3}";
    private static final String ALIGN_MID_LEFT = "{\\an4}";
    private static final String ALIGN_MID_MID = "{\\an5}";
    private static final String ALIGN_MID_RIGHT = "{\\an6}";
    private static final String ALIGN_TOP_LEFT = "{\\an7}";
    private static final String ALIGN_TOP_MID = "{\\an8}";
    private static final String ALIGN_TOP_RIGHT = "{\\an9}";
    static final float END_FRACTION = 0.92f;
    static final float MID_FRACTION = 0.5f;
    static final float START_FRACTION = 0.08f;
    private static final String SUBRIP_ALIGNMENT_TAG = "\\{\\\\an[1-9]\\}";
    private static final Pattern SUBRIP_TAG_PATTERN = Pattern.compile("\\{\\\\.*?\\}");
    private static final String SUBRIP_TIMECODE = "(?:(\\d+):)?(\\d+):(\\d+),(\\d+)";
    private static final Pattern SUBRIP_TIMING_LINE = Pattern.compile("\\s*((?:(\\d+):)?(\\d+):(\\d+),(\\d+))\\s*-->\\s*((?:(\\d+):)?(\\d+):(\\d+),(\\d+))?\\s*");
    private static final String TAG = "SubripDecoder";
    private final ArrayList<String> tags = new ArrayList<>();
    private final StringBuilder textBuilder = new StringBuilder();

    public SubripDecoder() {
        super(TAG);
    }

    /* access modifiers changed from: protected */
    public SubripSubtitle decode(byte[] bytes, int length, boolean reset) {
        ArrayList<Cue> cues = new ArrayList<>();
        LongArray cueTimesUs = new LongArray();
        ParsableByteArray subripData = new ParsableByteArray(bytes, length);
        while (true) {
            String readLine = subripData.readLine();
            String currentLine = readLine;
            if (readLine == null) {
                break;
            } else if (currentLine.length() != 0) {
                try {
                    Integer.parseInt(currentLine);
                    boolean haveEndTimecode = false;
                    String currentLine2 = subripData.readLine();
                    if (currentLine2 == null) {
                        Log.m30w(TAG, "Unexpected end");
                        break;
                    }
                    Matcher matcher = SUBRIP_TIMING_LINE.matcher(currentLine2);
                    if (matcher.matches()) {
                        cueTimesUs.add(parseTimecode(matcher, 1));
                        if (!TextUtils.isEmpty(matcher.group(6))) {
                            haveEndTimecode = true;
                            cueTimesUs.add(parseTimecode(matcher, 6));
                        }
                        this.textBuilder.setLength(0);
                        this.tags.clear();
                        while (true) {
                            String readLine2 = subripData.readLine();
                            String currentLine3 = readLine2;
                            if (TextUtils.isEmpty(readLine2)) {
                                break;
                            }
                            if (this.textBuilder.length() > 0) {
                                this.textBuilder.append("<br>");
                            }
                            this.textBuilder.append(processLine(currentLine3, this.tags));
                        }
                        Spanned text = Html.fromHtml(this.textBuilder.toString());
                        String alignmentTag = null;
                        int i = 0;
                        while (true) {
                            if (i >= this.tags.size()) {
                                break;
                            }
                            String tag = this.tags.get(i);
                            if (tag.matches(SUBRIP_ALIGNMENT_TAG)) {
                                alignmentTag = tag;
                                break;
                            }
                            i++;
                        }
                        cues.add(buildCue(text, alignmentTag));
                        if (haveEndTimecode) {
                            cues.add(null);
                        }
                    } else {
                        String valueOf = String.valueOf(currentLine2);
                        Log.m30w(TAG, valueOf.length() != 0 ? "Skipping invalid timing: ".concat(valueOf) : new String("Skipping invalid timing: "));
                    }
                } catch (NumberFormatException e) {
                    String valueOf2 = String.valueOf(currentLine);
                    Log.m30w(TAG, valueOf2.length() != 0 ? "Skipping invalid index: ".concat(valueOf2) : new String("Skipping invalid index: "));
                }
            }
        }
        Cue[] cuesArray = new Cue[cues.size()];
        cues.toArray(cuesArray);
        return new SubripSubtitle(cuesArray, cueTimesUs.toArray());
    }

    private String processLine(String line, ArrayList<String> tags2) {
        String line2 = line.trim();
        int removedCharacterCount = 0;
        StringBuilder processedLine = new StringBuilder(line2);
        Matcher matcher = SUBRIP_TAG_PATTERN.matcher(line2);
        while (matcher.find()) {
            String tag = matcher.group();
            tags2.add(tag);
            int start = matcher.start() - removedCharacterCount;
            int tagLength = tag.length();
            processedLine.replace(start, start + tagLength, "");
            removedCharacterCount += tagLength;
        }
        return processedLine.toString();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private Cue buildCue(Spanned text, @Nullable String alignmentTag) {
        char c;
        int positionAnchor;
        char c2;
        int lineAnchor;
        String str = alignmentTag;
        if (str == null) {
            return new Cue(text);
        }
        switch (alignmentTag.hashCode()) {
            case -685620710:
                if (str.equals(ALIGN_BOTTOM_LEFT)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -685620679:
                if (str.equals(ALIGN_BOTTOM_MID)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -685620648:
                if (str.equals(ALIGN_BOTTOM_RIGHT)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -685620617:
                if (str.equals(ALIGN_MID_LEFT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -685620586:
                if (str.equals(ALIGN_MID_MID)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -685620555:
                if (str.equals(ALIGN_MID_RIGHT)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -685620524:
                if (str.equals(ALIGN_TOP_LEFT)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -685620493:
                if (str.equals(ALIGN_TOP_MID)) {
                    c = 8;
                    break;
                }
                c = 65535;
                break;
            case -685620462:
                if (str.equals(ALIGN_TOP_RIGHT)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0 || c == 1 || c == 2) {
            positionAnchor = 0;
        } else if (c == 3 || c == 4 || c == 5) {
            positionAnchor = 2;
        } else {
            positionAnchor = 1;
        }
        switch (alignmentTag.hashCode()) {
            case -685620710:
                if (str.equals(ALIGN_BOTTOM_LEFT)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -685620679:
                if (str.equals(ALIGN_BOTTOM_MID)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -685620648:
                if (str.equals(ALIGN_BOTTOM_RIGHT)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -685620617:
                if (str.equals(ALIGN_MID_LEFT)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -685620586:
                if (str.equals(ALIGN_MID_MID)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -685620555:
                if (str.equals(ALIGN_MID_RIGHT)) {
                    c2 = 8;
                    break;
                }
                c2 = 65535;
                break;
            case -685620524:
                if (str.equals(ALIGN_TOP_LEFT)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -685620493:
                if (str.equals(ALIGN_TOP_MID)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -685620462:
                if (str.equals(ALIGN_TOP_RIGHT)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        if (c2 == 0 || c2 == 1 || c2 == 2) {
            lineAnchor = 2;
        } else if (c2 == 3 || c2 == 4 || c2 == 5) {
            lineAnchor = 0;
        } else {
            lineAnchor = 1;
        }
        return new Cue(text, null, getFractionalPositionForAnchorType(lineAnchor), 0, lineAnchor, getFractionalPositionForAnchorType(positionAnchor), positionAnchor, Float.MIN_VALUE);
    }

    private static long parseTimecode(Matcher matcher, int groupOffset) {
        return 1000 * ((Long.parseLong(matcher.group(groupOffset + 1)) * 60 * 60 * 1000) + (Long.parseLong(matcher.group(groupOffset + 2)) * 60 * 1000) + (Long.parseLong(matcher.group(groupOffset + 3)) * 1000) + Long.parseLong(matcher.group(groupOffset + 4)));
    }

    static float getFractionalPositionForAnchorType(int anchorType) {
        if (anchorType == 0) {
            return 0.08f;
        }
        if (anchorType == 1) {
            return MID_FRACTION;
        }
        if (anchorType == 2) {
            return END_FRACTION;
        }
        throw new IllegalArgumentException();
    }
}
