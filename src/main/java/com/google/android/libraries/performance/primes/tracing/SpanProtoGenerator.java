package com.google.android.libraries.performance.primes.tracing;

import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.PrimesToken;
import com.google.android.libraries.performance.primes.tracing.SpanEvent;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;

public final class SpanProtoGenerator {
    private static final int ROOT_SPAN_ID = 1;
    private static final int ROOT_SPAN_PARENT_ID = 0;
    private static final String TAG = "TraceDataToProto";
    private long nextId;
    private final List<PrimesTraceOuterClass.Span> result = new ArrayList();
    private final SpanEvent rootSpan;

    static SpanProtoGenerator create(SpanEvent rootSpan2) {
        return new SpanProtoGenerator(rootSpan2);
    }

    public static SpanProtoGenerator create(PrimesToken token, SpanEvent rootSpan2) {
        Preconditions.checkNotNull(token);
        return new SpanProtoGenerator(rootSpan2);
    }

    private SpanProtoGenerator(SpanEvent rootSpan2) {
        this.rootSpan = rootSpan2;
        this.nextId = 1;
    }

    private static PrimesTraceOuterClass.Span createSpanProto(SpanEvent spanEvent, long id, long parentId) {
        PrimesTraceOuterClass.Span.Builder span = PrimesTraceOuterClass.Span.newBuilder();
        if (spanEvent.eventNameType == SpanEvent.EventNameType.CONSTANT) {
            span.setConstantName(spanEvent.spanName);
        } else {
            span.setName(spanEvent.spanName);
        }
        span.setStartTimeMs(spanEvent.startMs).setDurationMs(spanEvent.getDurationMs()).setThreadId(spanEvent.threadId).setId(id).setParentId(parentId);
        int i = C11481.f121x6154420e[spanEvent.spanType.ordinal()];
        if (i == 1) {
            span.setSpanType(PrimesTraceOuterClass.Span.SpanType.TRACE);
        } else if (i != 2) {
            span.setSpanType(PrimesTraceOuterClass.Span.SpanType.NONE);
        } else {
            span.setSpanType(PrimesTraceOuterClass.Span.SpanType.TIMER);
        }
        return (PrimesTraceOuterClass.Span) span.build();
    }

    /* renamed from: com.google.android.libraries.performance.primes.tracing.SpanProtoGenerator$1 */
    static /* synthetic */ class C11481 {

        /* renamed from: $SwitchMap$com$google$android$libraries$performance$primes$tracing$SpanEvent$SpanType */
        static final /* synthetic */ int[] f121x6154420e = new int[SpanEvent.SpanType.values().length];

        static {
            try {
                f121x6154420e[SpanEvent.SpanType.ROOT_SPAN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f121x6154420e[SpanEvent.SpanType.TIMER_SPAN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private void traverse(SpanEvent span, long parentId) {
        List<SpanEvent> children = span.getAndResetChildren();
        if (!span.isThreadRootSpan() || !children.isEmpty()) {
            long j = this.nextId;
            this.nextId = 1 + j;
            PrimesTraceOuterClass.Span.Builder spanProto = (PrimesTraceOuterClass.Span.Builder) createSpanProto(span, j, parentId).toBuilder();
            if (span.isThreadRootSpan()) {
                spanProto.setDurationMs(children.get(children.size() - 1).endMs - span.startMs);
            }
            this.result.add((PrimesTraceOuterClass.Span) spanProto.build());
            for (SpanEvent childSpan : children) {
                traverse(childSpan, spanProto.getId());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public PrimesTraceOuterClass.Span[] generate() {
        traverse(this.rootSpan, 0);
        if (this.result.size() == 1) {
            PrimesLog.m46d(TAG, "No other span except for root span. Dropping trace...", new Object[0]);
            return null;
        }
        List<PrimesTraceOuterClass.Span> list = this.result;
        return (PrimesTraceOuterClass.Span[]) list.toArray(new PrimesTraceOuterClass.Span[list.size()]);
    }

    public PrimesTraceOuterClass.Span[] generate(PrimesToken token) {
        Preconditions.checkNotNull(token);
        return generate();
    }
}
