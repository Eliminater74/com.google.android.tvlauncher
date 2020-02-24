package com.google.android.libraries.performance.primes;

import android.support.annotation.GuardedBy;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.scenario.PrimesScenarioConfigurations;
import com.google.android.libraries.performance.primes.scenario.ScenarioEvent;
import com.google.android.libraries.performance.primes.scenario.ScenarioStructureProvider;
import com.google.common.base.Optional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.concurrent.ThreadSafe;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;

@ThreadSafe
final class ScenarioMetricService {
    @VisibleForTesting
    static final String EVENT_SCENARIOS_KEY = "eventScenarios";
    private static final String TAG = "ScenarioMetricService";
    @VisibleForTesting
    final ConcurrentHashMap<String, ScenarioData> activeScenarios = new ConcurrentHashMap<>();
    @VisibleForTesting
    final int maxActiveScenarios;
    private final PrimesApi primesApi;
    @VisibleForTesting
    final ScenarioStructureProvider scenarioStructureProvider;
    @VisibleForTesting
    final int timeoutMs;

    static ScenarioMetricService createService(PrimesApi primesApi2, PrimesScenarioConfigurations configs) {
        return new ScenarioMetricService(primesApi2, configs.getMaxActiveScenarios(), configs.getTimeoutMs(), configs.getScenarioStructureProvider());
    }

    static PrimesTraceOuterClass.PrimesTrace prepareScenarioMessage(String scenarioName, List<ScenarioEvent> events) {
        PrimesTraceOuterClass.Span[] spans = new PrimesTraceOuterClass.Span[(events.size() + 1)];
        spans[0] = (PrimesTraceOuterClass.Span) PrimesTraceOuterClass.Span.newBuilder().setConstantName(scenarioName).setStartTimeMs(events.get(0).getTimestampMs()).setDurationMs(events.get(events.size() - 1).getTimestampMs() - events.get(0).getTimestampMs()).setId(1).setParentId(0).build();
        for (int i = 0; i < events.size(); i++) {
            ScenarioEvent event = events.get(i);
            spans[i + 1] = (PrimesTraceOuterClass.Span) PrimesTraceOuterClass.Span.newBuilder().setConstantName(event.getEventName()).setStartTimeMs(event.getTimestampMs()).setId((long) (i + 2)).setParentId(1).build();
        }
        return (PrimesTraceOuterClass.PrimesTrace) PrimesTraceOuterClass.PrimesTrace.newBuilder().addAllSpans(Arrays.asList(spans)).build();
    }

    @VisibleForTesting
    ScenarioMetricService(PrimesApi primesApi2, int maxActiveScenarios2, int timeoutMs2, ScenarioStructureProvider scenarioStructureProvider2) {
        this.primesApi = primesApi2;
        this.maxActiveScenarios = maxActiveScenarios2;
        this.timeoutMs = timeoutMs2;
        this.scenarioStructureProvider = scenarioStructureProvider2;
    }

    private static Set<String> validEvents(Set<NoPiiString> noPiiEvents) {
        Set<String> events = new HashSet<>();
        for (NoPiiString noPiiEvent : noPiiEvents) {
            if (!NoPiiString.isNullOrEmpty(noPiiEvent)) {
                events.add(noPiiEvent.toString());
            }
        }
        return events;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void addScenarioEvent(com.google.android.libraries.performance.primes.scenario.ScenarioEvent r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            com.google.android.libraries.performance.primes.scenario.ScenarioStructureProvider r0 = r3.scenarioStructureProvider     // Catch:{ all -> 0x005d }
            r1 = 0
            if (r0 != 0) goto L_0x0011
            java.lang.String r0 = "ScenarioMetricService"
            java.lang.String r2 = "Logging ScenarioEvent without structure provider. Ignoring.."
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x005d }
            com.google.android.libraries.performance.primes.PrimesLog.m54w(r0, r2, r1)     // Catch:{ all -> 0x005d }
            monitor-exit(r3)
            return
        L_0x0011:
            java.lang.String r0 = r4.getEventName()     // Catch:{ all -> 0x005d }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0026
            java.lang.String r0 = "ScenarioMetricService"
            java.lang.String r2 = "Log empty ScenarioEvent. Ignoring.."
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x005d }
            com.google.android.libraries.performance.primes.PrimesLog.m54w(r0, r2, r1)     // Catch:{ all -> 0x005d }
            monitor-exit(r3)
            return
        L_0x0026:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.google.android.libraries.performance.primes.ScenarioData> r0 = r3.activeScenarios     // Catch:{ all -> 0x005d }
            java.lang.String r1 = "eventScenarios"
            boolean r0 = r0.containsKey(r1)     // Catch:{ all -> 0x005d }
            if (r0 != 0) goto L_0x003f
            com.google.android.libraries.performance.primes.ScenarioData r0 = new com.google.android.libraries.performance.primes.ScenarioData     // Catch:{ all -> 0x005d }
            java.lang.String r1 = "eventScenarios"
            r0.<init>(r1)     // Catch:{ all -> 0x005d }
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.google.android.libraries.performance.primes.ScenarioData> r1 = r3.activeScenarios     // Catch:{ all -> 0x005d }
            java.lang.String r2 = "eventScenarios"
            r1.put(r2, r0)     // Catch:{ all -> 0x005d }
            goto L_0x0049
        L_0x003f:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.google.android.libraries.performance.primes.ScenarioData> r0 = r3.activeScenarios     // Catch:{ all -> 0x005d }
            java.lang.String r1 = "eventScenarios"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x005d }
            com.google.android.libraries.performance.primes.ScenarioData r0 = (com.google.android.libraries.performance.primes.ScenarioData) r0     // Catch:{ all -> 0x005d }
        L_0x0049:
            r0.addEvent(r4)     // Catch:{ all -> 0x005d }
            com.google.android.libraries.performance.primes.scenario.ScenarioStructureProvider r1 = r3.scenarioStructureProvider     // Catch:{ all -> 0x005d }
            java.lang.String r2 = r4.getEventName()     // Catch:{ all -> 0x005d }
            boolean r1 = r1.isEndEvent(r2)     // Catch:{ all -> 0x005d }
            if (r1 == 0) goto L_0x005b
            r3.recordScenarios(r0, r4)     // Catch:{ all -> 0x005d }
        L_0x005b:
            monitor-exit(r3)
            return
        L_0x005d:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.ScenarioMetricService.addScenarioEvent(com.google.android.libraries.performance.primes.scenario.ScenarioEvent):void");
    }

    @GuardedBy("this")
    private void recordScenarios(ScenarioData scenarioData, ScenarioEvent end) {
        String endEventName = end.getEventName();
        Set<NoPiiString> startEvents = this.scenarioStructureProvider.getStartEventSetForEndEvent(endEventName);
        if (startEvents != null) {
            if (!startEvents.isEmpty()) {
                Optional<List<ScenarioEvent>> events = scenarioData.getEventsSinceMostRecentOf(validEvents(startEvents));
                if (!events.isPresent()) {
                    PrimesLog.m54w(TAG, "Possible start of Scenario not found.", new Object[0]);
                    return;
                }
                ScenarioEvent start = (ScenarioEvent) events.get().get(0);
                NoPiiString noPiiScenario = this.scenarioStructureProvider.getScenarioName(start.getEventName(), endEventName);
                if (NoPiiString.isNullOrEmpty(noPiiScenario)) {
                    PrimesLog.m54w(TAG, "Empty Scenario name.", new Object[0]);
                    return;
                }
                this.primesApi.recordDuration(WhitelistToken.getInstance(), noPiiScenario.toString(), true, start.getTimestampMs(), end.getTimestampMs(), null);
                this.primesApi.recordScenario(prepareScenarioMessage(noPiiScenario.toString(), events.get()));
                return;
            }
        }
        PrimesLog.m54w(TAG, "No Scenario start events are configured for end event: %s", endEventName);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public synchronized void cancelScenario(String scenarioName) {
        if (this.activeScenarios.remove(scenarioName) != null) {
            this.primesApi.cancelGlobalTimer(scenarioName);
            this.primesApi.cancelScenarioSampling(scenarioName);
        }
    }

    /* access modifiers changed from: package-private */
    public String[] getActiveScenarios() {
        return (String[]) this.activeScenarios.keySet().toArray(new String[0]);
    }
}
