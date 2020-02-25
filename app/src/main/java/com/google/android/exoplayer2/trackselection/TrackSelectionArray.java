package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;

import java.util.Arrays;

public final class TrackSelectionArray {
    public final int length;
    private final TrackSelection[] trackSelections;
    private int hashCode;

    public TrackSelectionArray(TrackSelection... trackSelections2) {
        this.trackSelections = trackSelections2;
        this.length = trackSelections2.length;
    }

    @Nullable
    public TrackSelection get(int index) {
        return this.trackSelections[index];
    }

    public TrackSelection[] getAll() {
        return (TrackSelection[]) this.trackSelections.clone();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = (17 * 31) + Arrays.hashCode(this.trackSelections);
        }
        return this.hashCode;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.trackSelections, ((TrackSelectionArray) obj).trackSelections);
    }
}
