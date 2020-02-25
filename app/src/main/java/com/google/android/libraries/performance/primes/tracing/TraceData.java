package com.google.android.libraries.performance.primes.tracing;

import android.os.Looper;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.PrimesToken;
import com.google.android.libraries.stitch.util.Preconditions;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class TraceData {
    @VisibleForTesting
    public static final String THREAD_PREFIX = "Thread: ";
    @VisibleForTesting
    public static final String UI_THREAD = "UI Thread";
    private static final String TAG = "TraceData";
    /* access modifiers changed from: private */
    public final AtomicInteger numOfSpans = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public final Map<SpanEvent, ThreadData> parentSpanToThreadData = new ConcurrentHashMap();
    private final ThreadLocal<WeakReference<ThreadData>> activeNode = new ThreadLocal<WeakReference<ThreadData>>() {
        /* access modifiers changed from: protected */
        public WeakReference<ThreadData> initialValue() {
            String str;
            long threadId = Thread.currentThread().getId();
            if (Looper.myLooper() == Looper.getMainLooper()) {
                str = TraceData.UI_THREAD;
            } else {
                String valueOf = String.valueOf(Thread.currentThread().getName());
                str = valueOf.length() != 0 ? TraceData.THREAD_PREFIX.concat(valueOf) : new String(TraceData.THREAD_PREFIX);
            }
            SpanEvent threadRootSpan = SpanEvent.newSpan(str, SpanEvent.EventNameType.CONSTANT, threadId, SpanEvent.SpanType.THREAD_ROOT_SPAN);
            ThreadData threadData = new ThreadData(threadId, threadRootSpan);
            threadData.stack.push(threadRootSpan);
            TraceData.this.numOfSpans.incrementAndGet();
            TraceData.this.parentSpanToThreadData.put(threadRootSpan, threadData);
            return new WeakReference<>(threadData);
        }
    };
    private final SpanEvent rootSpan;
    private final List<SpanEvent> timerSpans = new ArrayList();

    TraceData(String rootSpanName) {
        this.rootSpan = SpanEvent.newSpan(rootSpanName, SpanEvent.EventNameType.CONSTANT, Thread.currentThread().getId(), SpanEvent.SpanType.ROOT_SPAN);
    }

    static final /* synthetic */ int lambda$linkTraceAndGetRootSpan$0$TraceData(SpanEvent span1, SpanEvent span2) {
        return (int) (span1.startMs - span2.startMs);
    }

    private ArrayDeque<SpanEvent> activeNodeStack() {
        return ((ThreadData) this.activeNode.get().get()).stack;
    }

    /* access modifiers changed from: package-private */
    public SpanEvent pushThreadLocalSpan(String spanName, SpanEvent.EventNameType eventNameType) {
        SpanEvent currSpan = SpanEvent.newSpan(spanName, eventNameType, Thread.currentThread().getId());
        activeNodeStack().push(currSpan);
        return currSpan;
    }

    /* access modifiers changed from: package-private */
    public SpanEvent popThreadLocalSpan() {
        return activeNodeStack().poll();
    }

    /* access modifiers changed from: package-private */
    public void linkToParent(SpanEvent spanEvent) {
        SpanEvent parent = activeNodeStack().peek();
        if (parent != null) {
            parent.addChildSpan(spanEvent);
            return;
        }
        PrimesLog.m54w(TAG, "null Parent for Span: %s", spanEvent.spanName);
    }

    /* access modifiers changed from: package-private */
    public void sideLoadSpan(String spanName, long startMs, long durationMs) {
        if (this.rootSpan.startMs <= startMs) {
            PrimesLog.m46d(TAG, "Sideload span: %s. startMs: %d, durationMs: %d", spanName, Long.valueOf(startMs), Long.valueOf(durationMs));
            SpanEvent currSpan = SpanEvent.newSpan(PrimesToken.PRIMES_TOKEN, spanName, SpanEvent.EventNameType.CONSTANT, startMs, startMs + durationMs, Thread.currentThread().getId(), SpanEvent.SpanType.TIMER_SPAN);
            synchronized (this.timerSpans) {
                this.timerSpans.add(currSpan);
            }
            incrementAndGetSpanCount();
        }
    }

    public SpanEvent linkTraceAndGetRootSpan(PrimesToken token) {
        Preconditions.checkNotNull(token);
        return linkTraceAndGetRootSpan();
    }

    /* access modifiers changed from: package-private */
    public SpanEvent linkTraceAndGetRootSpan() {
        Comparator<SpanEvent> spanStartTimeComparator = TraceData$$Lambda$0.$instance;
        synchronized (this.timerSpans) {
            Collections.sort(this.timerSpans, spanStartTimeComparator);
            this.rootSpan.addChildSpans(this.timerSpans);
        }
        List<SpanEvent> threadSpans = new ArrayList<>(this.parentSpanToThreadData.keySet());
        Collections.sort(threadSpans, spanStartTimeComparator);
        this.rootSpan.addChildSpans(threadSpans);
        return this.rootSpan;
    }

    /* access modifiers changed from: package-private */
    public int incrementAndGetSpanCount() {
        return this.numOfSpans.incrementAndGet();
    }

    /* access modifiers changed from: package-private */
    public int getSpanCount() {
        return this.numOfSpans.get();
    }

    /* access modifiers changed from: package-private */
    public void updateRootSpanName(String spanName) {
        this.rootSpan.spanName = spanName;
    }

    /* access modifiers changed from: package-private */
    public SpanEvent getRootSpan() {
        return this.rootSpan;
    }

    private static final class ThreadData {
        /* access modifiers changed from: private */
        public final ArrayDeque<SpanEvent> stack = new ArrayDeque<>();
        private final long threadId;
        private final SpanEvent threadSpan;

        ThreadData(long threadId2, SpanEvent threadSpan2) {
            this.threadId = threadId2;
            this.threadSpan = threadSpan2;
            PrimesLog.m46d(TraceData.TAG, "Instantiate thread-data, thread:%d name:%s", Long.valueOf(this.threadId), this.threadSpan.spanName);
        }
    }
}
