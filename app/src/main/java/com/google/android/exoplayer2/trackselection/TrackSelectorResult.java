package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.util.Util;

public final class TrackSelectorResult {
    public final Object info;
    public final int length;
    public final RendererConfiguration[] rendererConfigurations;
    public final TrackSelectionArray selections;

    public TrackSelectorResult(RendererConfiguration[] rendererConfigurations2, TrackSelection[] selections2, Object info2) {
        this.rendererConfigurations = rendererConfigurations2;
        this.selections = new TrackSelectionArray(selections2);
        this.info = info2;
        this.length = rendererConfigurations2.length;
    }

    public boolean isRendererEnabled(int index) {
        return this.rendererConfigurations[index] != null;
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult other) {
        if (other == null || other.selections.length != this.selections.length) {
            return false;
        }
        for (int i = 0; i < this.selections.length; i++) {
            if (!isEquivalent(other, i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult other, int index) {
        if (other != null && Util.areEqual(this.rendererConfigurations[index], other.rendererConfigurations[index]) && Util.areEqual(this.selections.get(index), other.selections.get(index))) {
            return true;
        }
        return false;
    }
}
