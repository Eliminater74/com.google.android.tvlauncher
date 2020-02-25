package com.google.android.exoplayer2.text.webvtt;

import android.text.SpannableStringBuilder;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class WebvttSubtitle implements Subtitle {
    private final List<WebvttCue> cues;
    private final int numCues;
    private final long[] cueTimesUs = new long[(this.numCues * 2)];
    private final long[] sortedCueTimesUs;

    public WebvttSubtitle(List<WebvttCue> cues2) {
        this.cues = cues2;
        this.numCues = cues2.size();
        for (int cueIndex = 0; cueIndex < this.numCues; cueIndex++) {
            WebvttCue cue = cues2.get(cueIndex);
            int arrayIndex = cueIndex * 2;
            this.cueTimesUs[arrayIndex] = cue.startTime;
            this.cueTimesUs[arrayIndex + 1] = cue.endTime;
        }
        long[] jArr = this.cueTimesUs;
        this.sortedCueTimesUs = Arrays.copyOf(jArr, jArr.length);
        Arrays.sort(this.sortedCueTimesUs);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int
     arg types: [long[], long, int, int]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchCeil(java.util.List, java.lang.Comparable, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchCeil(long[], long, boolean, boolean):int */
    public int getNextEventTimeIndex(long timeUs) {
        int index = Util.binarySearchCeil(this.sortedCueTimesUs, timeUs, false, false);
        if (index < this.sortedCueTimesUs.length) {
            return index;
        }
        return -1;
    }

    public int getEventTimeCount() {
        return this.sortedCueTimesUs.length;
    }

    public long getEventTime(int index) {
        boolean z = true;
        Assertions.checkArgument(index >= 0);
        if (index >= this.sortedCueTimesUs.length) {
            z = false;
        }
        Assertions.checkArgument(z);
        return this.sortedCueTimesUs[index];
    }

    public List<Cue> getCues(long timeUs) {
        ArrayList<Cue> list = null;
        WebvttCue firstNormalCue = null;
        SpannableStringBuilder normalCueTextBuilder = null;
        for (int i = 0; i < this.numCues; i++) {
            long[] jArr = this.cueTimesUs;
            if (jArr[i * 2] <= timeUs && timeUs < jArr[(i * 2) + 1]) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                WebvttCue cue = this.cues.get(i);
                if (!cue.isNormalCue()) {
                    list.add(cue);
                } else if (firstNormalCue == null) {
                    firstNormalCue = cue;
                } else if (normalCueTextBuilder == null) {
                    normalCueTextBuilder = new SpannableStringBuilder();
                    normalCueTextBuilder.append(firstNormalCue.text).append((CharSequence) "\n").append(cue.text);
                } else {
                    normalCueTextBuilder.append((CharSequence) "\n").append(cue.text);
                }
            }
        }
        if (normalCueTextBuilder != null) {
            list.add(new WebvttCue(normalCueTextBuilder));
        } else if (firstNormalCue != null) {
            list.add(firstNormalCue);
        }
        if (list != null) {
            return list;
        }
        return Collections.emptyList();
    }
}
