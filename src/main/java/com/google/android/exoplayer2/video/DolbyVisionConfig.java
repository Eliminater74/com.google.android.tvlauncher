package com.google.android.exoplayer2.video;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.ParsableByteArray;

public final class DolbyVisionConfig {
    public final String codecs;
    public final int level;
    public final int profile;

    @Nullable
    public static DolbyVisionConfig parse(ParsableByteArray data) {
        String codecsPrefix;
        data.skipBytes(2);
        int profileData = data.readUnsignedByte();
        int dvProfile = profileData >> 1;
        int dvLevel = ((profileData & 1) << 5) | ((data.readUnsignedByte() >> 3) & 31);
        if (dvProfile == 4 || dvProfile == 5) {
            codecsPrefix = "dvhe";
        } else if (dvProfile == 8) {
            codecsPrefix = "hev1";
        } else if (dvProfile != 9) {
            return null;
        } else {
            codecsPrefix = "avc3";
        }
        StringBuilder sb = new StringBuilder(String.valueOf(codecsPrefix).length() + 26);
        sb.append(codecsPrefix);
        sb.append(".0");
        sb.append(dvProfile);
        sb.append(".0");
        sb.append(dvLevel);
        return new DolbyVisionConfig(dvProfile, dvLevel, sb.toString());
    }

    private DolbyVisionConfig(int profile2, int level2, String codecs2) {
        this.profile = profile2;
        this.level = level2;
        this.codecs = codecs2;
    }
}
