package com.google.android.exoplayer2.source.dash.manifest;

public final class UtcTimingElement {
    public final String schemeIdUri;
    public final String value;

    public UtcTimingElement(String schemeIdUri2, String value2) {
        this.schemeIdUri = schemeIdUri2;
        this.value = value2;
    }

    public String toString() {
        String str = this.schemeIdUri;
        String str2 = this.value;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(", ");
        sb.append(str2);
        return sb.toString();
    }
}
