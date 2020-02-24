package com.google.android.exoplayer2.text.cea;

import android.support.annotation.NonNull;
import android.text.Layout;
import com.google.android.exoplayer2.text.Cue;

final class Cea708Cue extends Cue implements Comparable<Cea708Cue> {
    public static final int PRIORITY_UNSET = -1;
    public final int priority;

    public Cea708Cue(CharSequence text, Layout.Alignment textAlignment, float line, int lineType, int lineAnchor, float position, int positionAnchor, float size, boolean windowColorSet, int windowColor, int priority2) {
        super(text, textAlignment, line, lineType, lineAnchor, position, positionAnchor, size, windowColorSet, windowColor);
        this.priority = priority2;
    }

    public int compareTo(@NonNull Cea708Cue other) {
        int i = other.priority;
        int i2 = this.priority;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        return 0;
    }
}
