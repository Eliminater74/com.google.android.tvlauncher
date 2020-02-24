package com.google.android.exoplayer2.audio;

import android.support.annotation.Nullable;

public final class AuxEffectInfo {
    public static final int NO_AUX_EFFECT_ID = 0;
    public final int effectId;
    public final float sendLevel;

    public AuxEffectInfo(int effectId2, float sendLevel2) {
        this.effectId = effectId2;
        this.sendLevel = sendLevel2;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuxEffectInfo auxEffectInfo = (AuxEffectInfo) o;
        if (this.effectId == auxEffectInfo.effectId && Float.compare(auxEffectInfo.sendLevel, this.sendLevel) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((17 * 31) + this.effectId) * 31) + Float.floatToIntBits(this.sendLevel);
    }
}
