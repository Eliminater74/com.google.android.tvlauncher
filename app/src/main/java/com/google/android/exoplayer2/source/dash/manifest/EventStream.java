package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.metadata.emsg.EventMessage;

public final class EventStream {
    public final EventMessage[] events;
    public final long[] presentationTimesUs;
    public final String schemeIdUri;
    public final long timescale;
    public final String value;

    public EventStream(String schemeIdUri2, String value2, long timescale2, long[] presentationTimesUs2, EventMessage[] events2) {
        this.schemeIdUri = schemeIdUri2;
        this.value = value2;
        this.timescale = timescale2;
        this.presentationTimesUs = presentationTimesUs2;
        this.events = events2;
    }

    /* renamed from: id */
    public String mo14759id() {
        String str = this.schemeIdUri;
        String str2 = this.value;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append("/");
        sb.append(str2);
        return sb.toString();
    }
}
