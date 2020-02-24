package com.google.android.libraries.performance.primes;

import android.os.Looper;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.PrimesStartupMeasure;
import com.google.android.libraries.performance.primes.tracing.SpanEvent;
import com.google.android.libraries.performance.primes.tracing.SpanProtoGenerator;
import com.google.android.libraries.performance.primes.tracing.TraceData;
import java.util.ArrayList;
import java.util.List;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;

final class PrimesStartupTraceGenerator {
    @VisibleForTesting
    static final String SPAN_ACTIVITY_DRAW_SUFFIX = ": onDraw";
    @VisibleForTesting
    static final String SPAN_ACTIVITY_INIT_SUFFIX = ": init";
    @VisibleForTesting
    static final String SPAN_ACTIVITY_ONCREATE_SUFFIX = ": onCreate";
    @VisibleForTesting
    static final String SPAN_ACTIVITY_ONRESUME_SUFFIX = ": onResume";
    @VisibleForTesting
    static final String SPAN_ACTIVITY_ONSTART_SUFFIX = ": onStart";
    @VisibleForTesting
    static final String SPAN_APP_CREATE = "App create";

    PrimesStartupTraceGenerator() {
    }

    static SpanEvent prepareMiniTrace(PrimesStartupMeasure startupMeasure) {
        String str;
        long mainThreadId = Looper.getMainLooper().getThread().getId();
        List<SpanEvent> spans = getChildSpans(startupMeasure, mainThreadId);
        PrimesToken primesToken = PrimesToken.PRIMES_TOKEN;
        if (startupMeasure.isColdStartup()) {
            str = PrimesStartupEvents.COLD_STARTUP_EVENT_NAME;
        } else {
            str = PrimesStartupEvents.WARM_STARTUP_EVENT_NAME;
        }
        SpanEvent rootSpan = SpanEvent.newSpan(primesToken, str, SpanEvent.EventNameType.CONSTANT, spans.get(0).getStartMs(), -1, mainThreadId, SpanEvent.SpanType.ROOT_SPAN);
        rootSpan.addChildSpans(PrimesToken.PRIMES_TOKEN, spans);
        return rootSpan;
    }

    private static List<SpanEvent> getChildSpans(PrimesStartupMeasure startupMeasure, long mainThreadId) {
        List<SpanEvent> spanEvents = new ArrayList<>();
        boolean isColdStartup = startupMeasure.isColdStartup();
        PrimesStartupMeasure.StartupActivityInfo[] startupActivityInfos = startupMeasure.getStartupActivityInfos();
        if (isColdStartup) {
            spanEvents.add(SpanEvent.newSpan(PrimesToken.PRIMES_TOKEN, SPAN_APP_CREATE, SpanEvent.EventNameType.CONSTANT, startupMeasure.getAppClassLoadedAt(), startupMeasure.getAppOnCreateAt(), mainThreadId, SpanEvent.SpanType.CHILD_SPAN));
            PrimesToken primesToken = PrimesToken.PRIMES_TOKEN;
            String valueOf = String.valueOf(startupActivityInfos[0].activityName);
            String valueOf2 = String.valueOf(SPAN_ACTIVITY_ONCREATE_SUFFIX);
            SpanEvent spanActivityOnCreate = SpanEvent.newSpan(primesToken, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), SpanEvent.EventNameType.CONSTANT, startupMeasure.getAppOnCreateAt(), startupActivityInfos[0].onActivityCreatedAt, mainThreadId, SpanEvent.SpanType.CHILD_SPAN);
            spanEvents.add(spanActivityOnCreate);
            if (startupMeasure.getFirstOnActivityInitAt() > 0) {
                PrimesToken primesToken2 = PrimesToken.PRIMES_TOKEN;
                PrimesToken primesToken3 = PrimesToken.PRIMES_TOKEN;
                String valueOf3 = String.valueOf(startupActivityInfos[0].activityName);
                String valueOf4 = String.valueOf(SPAN_ACTIVITY_INIT_SUFFIX);
                spanActivityOnCreate.addChildSpan(primesToken2, SpanEvent.newSpan(primesToken3, valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3), SpanEvent.EventNameType.CONSTANT, startupMeasure.getAppOnCreateAt(), startupMeasure.getFirstOnActivityInitAt(), mainThreadId, SpanEvent.SpanType.CHILD_SPAN));
            }
        } else {
            PrimesToken primesToken4 = PrimesToken.PRIMES_TOKEN;
            String valueOf5 = String.valueOf(startupActivityInfos[0].activityName);
            String valueOf6 = String.valueOf(SPAN_ACTIVITY_ONCREATE_SUFFIX);
            spanEvents.add(SpanEvent.newSpan(primesToken4, valueOf6.length() != 0 ? valueOf5.concat(valueOf6) : new String(valueOf5), SpanEvent.EventNameType.CONSTANT, startupMeasure.getFirstOnActivityInitAt(), startupActivityInfos[0].onActivityCreatedAt, mainThreadId, SpanEvent.SpanType.CHILD_SPAN));
        }
        long startTimeMs = startupActivityInfos[0].onActivityCreatedAt;
        for (int index = 1; index < startupActivityInfos.length; index++) {
            long endTimeMs = startupActivityInfos[index].onActivityCreatedAt;
            PrimesToken primesToken5 = PrimesToken.PRIMES_TOKEN;
            String valueOf7 = String.valueOf(startupActivityInfos[index].activityName);
            String valueOf8 = String.valueOf(SPAN_ACTIVITY_ONCREATE_SUFFIX);
            spanEvents.add(SpanEvent.newSpan(primesToken5, valueOf8.length() != 0 ? valueOf7.concat(valueOf8) : new String(valueOf7), SpanEvent.EventNameType.CONSTANT, startTimeMs, endTimeMs, mainThreadId, SpanEvent.SpanType.CHILD_SPAN));
            startTimeMs = endTimeMs;
        }
        long startTimeMs2 = startupActivityInfos[startupActivityInfos.length - 1].onActivityCreatedAt;
        String drawActivityName = startupActivityInfos[startupActivityInfos.length - 1].activityName;
        PrimesToken primesToken6 = PrimesToken.PRIMES_TOKEN;
        String valueOf9 = String.valueOf(drawActivityName);
        String valueOf10 = String.valueOf(SPAN_ACTIVITY_ONSTART_SUFFIX);
        spanEvents.add(SpanEvent.newSpan(primesToken6, valueOf10.length() != 0 ? valueOf9.concat(valueOf10) : new String(valueOf9), SpanEvent.EventNameType.CONSTANT, startTimeMs2, startupMeasure.getFirstOnActivityStartedAt(), mainThreadId, SpanEvent.SpanType.CHILD_SPAN));
        PrimesToken primesToken7 = PrimesToken.PRIMES_TOKEN;
        String valueOf11 = String.valueOf(drawActivityName);
        String valueOf12 = String.valueOf(SPAN_ACTIVITY_ONRESUME_SUFFIX);
        spanEvents.add(SpanEvent.newSpan(primesToken7, valueOf12.length() != 0 ? valueOf11.concat(valueOf12) : new String(valueOf11), SpanEvent.EventNameType.CONSTANT, startupMeasure.getFirstOnActivityStartedAt(), startupMeasure.getFirstOnActivityResumedAt(), mainThreadId, SpanEvent.SpanType.CHILD_SPAN));
        PrimesToken primesToken8 = PrimesToken.PRIMES_TOKEN;
        String valueOf13 = String.valueOf(drawActivityName);
        String valueOf14 = String.valueOf(SPAN_ACTIVITY_DRAW_SUFFIX);
        spanEvents.add(SpanEvent.newSpan(primesToken8, valueOf14.length() != 0 ? valueOf13.concat(valueOf14) : new String(valueOf13), SpanEvent.EventNameType.CONSTANT, startupMeasure.getFirstOnActivityResumedAt(), startupMeasure.getFirstDrawnAt(), mainThreadId, SpanEvent.SpanType.CHILD_SPAN));
        return spanEvents;
    }

    static PrimesTraceOuterClass.Span[] createStartupTraceProto(SpanEvent miniTraceRootSpan, TraceData traceData) {
        if (traceData != null) {
            SpanEvent detailedStartupTrace = convertToSubTrace(traceData);
            if (detailedStartupTrace.hasValidChildren(PrimesToken.PRIMES_TOKEN)) {
                miniTraceRootSpan.addChildSpan(PrimesToken.PRIMES_TOKEN, detailedStartupTrace);
            }
        }
        return SpanProtoGenerator.create(PrimesToken.PRIMES_TOKEN, miniTraceRootSpan).generate(PrimesToken.PRIMES_TOKEN);
    }

    private static SpanEvent convertToSubTrace(TraceData traceData) {
        SpanEvent subTrace = traceData.linkTraceAndGetRootSpan(PrimesToken.PRIMES_TOKEN);
        subTrace.setSpanType(PrimesToken.PRIMES_TOKEN, SpanEvent.SpanType.CHILD_SPAN);
        return subTrace;
    }
}
