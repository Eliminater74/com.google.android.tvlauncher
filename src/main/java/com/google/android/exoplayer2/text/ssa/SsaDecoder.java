package com.google.android.exoplayer2.text.ssa;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SsaDecoder extends SimpleSubtitleDecoder {
    private static final String DIALOGUE_LINE_PREFIX = "Dialogue: ";
    private static final String FORMAT_LINE_PREFIX = "Format: ";
    private static final Pattern SSA_TIMECODE_PATTERN = Pattern.compile("(?:(\\d+):)?(\\d+):(\\d+)(?::|\\.)(\\d+)");
    private static final String TAG = "SsaDecoder";
    private int formatEndIndex;
    private int formatKeyCount;
    private int formatStartIndex;
    private int formatTextIndex;
    private final boolean haveInitializationData;

    public SsaDecoder() {
        this(null);
    }

    public SsaDecoder(List<byte[]> initializationData) {
        super(TAG);
        if (initializationData == null || initializationData.isEmpty()) {
            this.haveInitializationData = false;
            return;
        }
        this.haveInitializationData = true;
        String formatLine = Util.fromUtf8Bytes(initializationData.get(0));
        Assertions.checkArgument(formatLine.startsWith(FORMAT_LINE_PREFIX));
        parseFormatLine(formatLine);
        parseHeader(new ParsableByteArray(initializationData.get(1)));
    }

    /* access modifiers changed from: protected */
    public SsaSubtitle decode(byte[] bytes, int length, boolean reset) {
        ArrayList<Cue> cues = new ArrayList<>();
        LongArray cueTimesUs = new LongArray();
        ParsableByteArray data = new ParsableByteArray(bytes, length);
        if (!this.haveInitializationData) {
            parseHeader(data);
        }
        parseEventBody(data, cues, cueTimesUs);
        Cue[] cuesArray = new Cue[cues.size()];
        cues.toArray(cuesArray);
        return new SsaSubtitle(cuesArray, cueTimesUs.toArray());
    }

    private void parseHeader(ParsableByteArray data) {
        String currentLine;
        do {
            String readLine = data.readLine();
            currentLine = readLine;
            if (readLine == null) {
                return;
            }
        } while (!currentLine.startsWith("[Events]"));
    }

    private void parseEventBody(ParsableByteArray data, List<Cue> cues, LongArray cueTimesUs) {
        while (true) {
            String readLine = data.readLine();
            String currentLine = readLine;
            if (readLine == null) {
                return;
            }
            if (!this.haveInitializationData && currentLine.startsWith(FORMAT_LINE_PREFIX)) {
                parseFormatLine(currentLine);
            } else if (currentLine.startsWith(DIALOGUE_LINE_PREFIX)) {
                parseDialogueLine(currentLine, cues, cueTimesUs);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0047, code lost:
        if (r3.equals(com.google.android.exoplayer2.text.ttml.TtmlNode.START) == false) goto L_0x005f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseFormatLine(java.lang.String r10) {
        /*
            r9 = this;
            java.lang.String r0 = "Format: "
            int r0 = r0.length()
            java.lang.String r0 = r10.substring(r0)
            java.lang.String r1 = ","
            java.lang.String[] r0 = android.text.TextUtils.split(r0, r1)
            int r1 = r0.length
            r9.formatKeyCount = r1
            r1 = -1
            r9.formatStartIndex = r1
            r9.formatEndIndex = r1
            r9.formatTextIndex = r1
            r2 = 0
        L_0x001b:
            int r3 = r9.formatKeyCount
            r4 = 0
            if (r2 >= r3) goto L_0x0073
            r3 = r0[r2]
            java.lang.String r3 = r3.trim()
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r3)
            int r5 = r3.hashCode()
            r6 = 100571(0x188db, float:1.4093E-40)
            r7 = 2
            r8 = 1
            if (r5 == r6) goto L_0x0055
            r6 = 3556653(0x36452d, float:4.983932E-39)
            if (r5 == r6) goto L_0x004a
            r6 = 109757538(0x68ac462, float:5.219839E-35)
            if (r5 == r6) goto L_0x0040
        L_0x003f:
            goto L_0x005f
        L_0x0040:
            java.lang.String r5 = "start"
            boolean r5 = r3.equals(r5)
            if (r5 == 0) goto L_0x003f
            goto L_0x0060
        L_0x004a:
            java.lang.String r4 = "text"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x003f
            r4 = 2
            goto L_0x0060
        L_0x0055:
            java.lang.String r4 = "end"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x003f
            r4 = 1
            goto L_0x0060
        L_0x005f:
            r4 = -1
        L_0x0060:
            if (r4 == 0) goto L_0x006d
            if (r4 == r8) goto L_0x006a
            if (r4 == r7) goto L_0x0067
            goto L_0x0070
        L_0x0067:
            r9.formatTextIndex = r2
            goto L_0x0070
        L_0x006a:
            r9.formatEndIndex = r2
            goto L_0x0070
        L_0x006d:
            r9.formatStartIndex = r2
        L_0x0070:
            int r2 = r2 + 1
            goto L_0x001b
        L_0x0073:
            int r2 = r9.formatStartIndex
            if (r2 == r1) goto L_0x007f
            int r2 = r9.formatEndIndex
            if (r2 == r1) goto L_0x007f
            int r2 = r9.formatTextIndex
            if (r2 != r1) goto L_0x0081
        L_0x007f:
            r9.formatKeyCount = r4
        L_0x0081:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaDecoder.parseFormatLine(java.lang.String):void");
    }

    private void parseDialogueLine(String dialogueLine, List<Cue> cues, LongArray cueTimesUs) {
        if (this.formatKeyCount == 0) {
            String valueOf = String.valueOf(dialogueLine);
            Log.m30w(TAG, valueOf.length() != 0 ? "Skipping dialogue line before complete format: ".concat(valueOf) : new String("Skipping dialogue line before complete format: "));
            return;
        }
        String[] lineValues = dialogueLine.substring(DIALOGUE_LINE_PREFIX.length()).split(",", this.formatKeyCount);
        if (lineValues.length != this.formatKeyCount) {
            String valueOf2 = String.valueOf(dialogueLine);
            Log.m30w(TAG, valueOf2.length() != 0 ? "Skipping dialogue line with fewer columns than format: ".concat(valueOf2) : new String("Skipping dialogue line with fewer columns than format: "));
            return;
        }
        long startTimeUs = parseTimecodeUs(lineValues[this.formatStartIndex]);
        if (startTimeUs == C0841C.TIME_UNSET) {
            String valueOf3 = String.valueOf(dialogueLine);
            Log.m30w(TAG, valueOf3.length() != 0 ? "Skipping invalid timing: ".concat(valueOf3) : new String("Skipping invalid timing: "));
            return;
        }
        long endTimeUs = C0841C.TIME_UNSET;
        String endTimeString = lineValues[this.formatEndIndex];
        if (!endTimeString.trim().isEmpty()) {
            endTimeUs = parseTimecodeUs(endTimeString);
            if (endTimeUs == C0841C.TIME_UNSET) {
                String valueOf4 = String.valueOf(dialogueLine);
                Log.m30w(TAG, valueOf4.length() != 0 ? "Skipping invalid timing: ".concat(valueOf4) : new String("Skipping invalid timing: "));
                return;
            }
        }
        cues.add(new Cue(lineValues[this.formatTextIndex].replaceAll("\\{.*?\\}", "").replaceAll("\\\\N", "\n").replaceAll("\\\\n", "\n")));
        cueTimesUs.add(startTimeUs);
        if (endTimeUs != C0841C.TIME_UNSET) {
            cues.add(null);
            cueTimesUs.add(endTimeUs);
        }
    }

    public static long parseTimecodeUs(String timeString) {
        Matcher matcher = SSA_TIMECODE_PATTERN.matcher(timeString);
        if (!matcher.matches()) {
            return C0841C.TIME_UNSET;
        }
        return (Long.parseLong(matcher.group(1)) * 60 * 60 * 1000000) + (Long.parseLong(matcher.group(2)) * 60 * 1000000) + (Long.parseLong(matcher.group(3)) * 1000000) + (Long.parseLong(matcher.group(4)) * 10000);
    }
}
