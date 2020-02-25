package com.google.android.exoplayer2.source.dash.manifest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

public final class Descriptor {
    @Nullable

    /* renamed from: id */
    public final String f96id;
    @NonNull
    public final String schemeIdUri;
    @Nullable
    public final String value;

    public Descriptor(@NonNull String schemeIdUri2, @Nullable String value2, @Nullable String id) {
        this.schemeIdUri = schemeIdUri2;
        this.value = value2;
        this.f96id = id;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Descriptor other = (Descriptor) obj;
        if (!Util.areEqual(this.schemeIdUri, other.schemeIdUri) || !Util.areEqual(this.value, other.value) || !Util.areEqual(this.f96id, other.f96id)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.schemeIdUri;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.value;
        int result = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.f96id;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return result + i;
    }
}
