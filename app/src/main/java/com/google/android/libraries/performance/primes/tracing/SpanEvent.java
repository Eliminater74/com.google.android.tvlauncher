package com.google.android.libraries.performance.primes.tracing;

import com.google.android.libraries.performance.primes.PrimesToken;
import com.google.android.libraries.performance.primes.metriccapture.TimeCapture;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SpanEvent {
    static final SpanEvent EMPTY_SPAN = newSpan("");
    private volatile List<SpanEvent> children;
    long endMs = -1;
    final EventNameType eventNameType;
    String spanName;
    SpanType spanType;
    long startMs;
    final long threadId;

    public enum EventNameType {
        CONSTANT,
        CUSTOM
    }

    public enum SpanType {
        THREAD_ROOT_SPAN,
        ROOT_SPAN,
        CHILD_SPAN,
        TIMER_SPAN
    }

    static SpanEvent newSpan(String spanName2) {
        return new SpanEvent(spanName2, EventNameType.CONSTANT, TimeCapture.getTime(), -1, Thread.currentThread().getId(), SpanType.CHILD_SPAN);
    }

    static SpanEvent newSpan(String spanName2, EventNameType eventNameType2, long threadId2) {
        return new SpanEvent(spanName2, eventNameType2, TimeCapture.getTime(), -1, threadId2, SpanType.CHILD_SPAN);
    }

    static SpanEvent newSpan(String spanName2, EventNameType eventNameType2, long threadId2, SpanType spanType2) {
        return new SpanEvent(spanName2, eventNameType2, TimeCapture.getTime(), -1, threadId2, spanType2);
    }

    public static SpanEvent newSpan(PrimesToken token, String spanName2, EventNameType eventNameType2, long startMs2, long endMs2, long threadId2, SpanType spanType2) {
        Preconditions.checkNotNull(token);
        return new SpanEvent(spanName2, eventNameType2, startMs2, endMs2, threadId2, spanType2);
    }

    private SpanEvent(String spanName2, EventNameType eventNameType2, long startMs2, long endMs2, long threadId2, SpanType spanType2) {
        this.spanName = spanName2;
        this.eventNameType = eventNameType2;
        this.startMs = startMs2;
        this.endMs = endMs2;
        this.threadId = threadId2;
        this.spanType = spanType2;
        if (this.spanType == SpanType.THREAD_ROOT_SPAN) {
            this.children = Collections.synchronizedList(new ArrayList());
        } else {
            this.children = Collections.emptyList();
        }
    }

    public void addChildSpans(PrimesToken token, List<SpanEvent> spans) {
        Preconditions.checkNotNull(token);
        addChildSpans(spans);
    }

    /* access modifiers changed from: package-private */
    public void addChildSpans(List<SpanEvent> spans) {
        if (this.children == Collections.EMPTY_LIST) {
            this.children = new ArrayList();
        }
        this.children.addAll(spans);
    }

    public void addChildSpan(PrimesToken token, SpanEvent span) {
        Preconditions.checkNotNull(token);
        addChildSpan(span);
    }

    /* access modifiers changed from: package-private */
    public void addChildSpan(SpanEvent span) {
        if (this.children == Collections.EMPTY_LIST) {
            this.children = new ArrayList();
        }
        this.children.add(span);
    }

    public void setSpanType(PrimesToken token, SpanType spanType2) {
        Preconditions.checkNotNull(token);
        this.spanType = spanType2;
    }

    public void setEndMs(PrimesToken token, long endMs2) {
        Preconditions.checkNotNull(token);
        this.endMs = endMs2;
    }

    /* access modifiers changed from: package-private */
    public List<SpanEvent> getAndResetChildren() {
        List<SpanEvent> childrenRef = this.children;
        this.children = NoopList.getInstance();
        return childrenRef;
    }

    public boolean hasValidChildren(PrimesToken token) {
        Preconditions.checkNotNull(token);
        boolean hasValidChildren = false;
        for (SpanEvent spanEvent : this.children) {
            hasValidChildren |= !spanEvent.isThreadRootSpan() || !spanEvent.children.isEmpty();
        }
        return hasValidChildren;
    }

    /* access modifiers changed from: package-private */
    public boolean isThreadRootSpan() {
        return this.spanType == SpanType.THREAD_ROOT_SPAN;
    }

    /* access modifiers changed from: package-private */
    public SpanEvent closeSpan() {
        ensureEndTimeSet();
        return this;
    }

    private void ensureEndTimeSet() {
        if (this.endMs < 0) {
            this.endMs = TimeCapture.getTime();
        }
    }

    public long getDurationMs() {
        long j = this.endMs;
        if (j == -1) {
            return -1;
        }
        return j - this.startMs;
    }

    public long getStartMs() {
        return this.startMs;
    }
}
