package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.scenario.ScenarioEvent;
import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

final class ScenarioData {
    @VisibleForTesting
    static final int DEFAULT_MAX_SCENARIO_EVENTS = 200;
    private static final String TAG = "ScenarioData";
    ScheduledFuture<?> cancelFuture;
    @Nullable
    private String end;
    private final String name;
    @VisibleForTesting
    ScenarioEventStore scenarioEventStore;
    @Nullable
    private String start;

    static final class ScenarioEventStore {
        @VisibleForTesting
        final ScenarioEvent[] events;
        private int nextEventIndex;
        private int recentEventIndex;

        private ScenarioEventStore() {
            this.recentEventIndex = -1;
            this.nextEventIndex = 0;
            this.events = new ScenarioEvent[200];
        }

        /* access modifiers changed from: package-private */
        public void addEvent(ScenarioEvent event) {
            ScenarioEvent[] scenarioEventArr = this.events;
            int i = this.nextEventIndex;
            scenarioEventArr[i] = event;
            this.recentEventIndex = i;
            this.nextEventIndex = nextIndex(i);
        }

        /* access modifiers changed from: package-private */
        public Optional<List<ScenarioEvent>> getEventsAfter(Set<String> startCandidates) {
            List<ScenarioEvent> subset = new ArrayList<>();
            int startIndex = startIndexForRecentEvent(startCandidates);
            if (startIndex == -1) {
                return Optional.absent();
            }
            while (true) {
                int i = this.recentEventIndex;
                if (startIndex != i) {
                    subset.add(this.events[startIndex]);
                    startIndex = nextIndex(startIndex);
                } else {
                    subset.add(this.events[i]);
                    return Optional.m80of(subset);
                }
            }
        }

        private int startIndexForRecentEvent(Set<String> startCandidates) {
            ScenarioEvent[] scenarioEventArr = this.events;
            int i = this.recentEventIndex;
            ScenarioEvent endEvent = scenarioEventArr[i];
            int i2 = previousIndex(i);
            while (true) {
                ScenarioEvent[] scenarioEventArr2 = this.events;
                if (scenarioEventArr2[i2] == null || i2 == this.recentEventIndex) {
                    return -1;
                }
                ScenarioEvent curEvent = scenarioEventArr2[i2];
                if (curEvent.getEventName().equals(endEvent.getEventName()) && endEvent.tagMatch(curEvent)) {
                    PrimesLog.m54w(ScenarioData.TAG, "Found duplicated identical end event [%s] before any starting event. Current end event tags: %s. Duplicated end event tags: %s", endEvent, endEvent.getTags(), curEvent.getTags());
                    return -1;
                } else if (startCandidates.contains(curEvent.getEventName()) && endEvent.tagMatch(curEvent)) {
                    return i2;
                } else {
                    i2 = previousIndex(i2);
                }
            }
            return -1;
        }

        private int previousIndex(int index) {
            ScenarioEvent[] scenarioEventArr = this.events;
            return ((scenarioEventArr.length + index) - 1) % scenarioEventArr.length;
        }

        private int nextIndex(int index) {
            return (index + 1) % this.events.length;
        }
    }

    ScenarioData(String scenarioName) {
        this.name = scenarioName;
    }

    /* access modifiers changed from: package-private */
    public void addEvent(ScenarioEvent scenarioEvent) {
        if (this.scenarioEventStore == null) {
            this.scenarioEventStore = new ScenarioEventStore();
        }
        this.scenarioEventStore.addEvent(scenarioEvent);
    }

    /* access modifiers changed from: package-private */
    public Optional<List<ScenarioEvent>> getEventsSinceMostRecentOf(Set<String> startEvents) {
        ScenarioEventStore scenarioEventStore2 = this.scenarioEventStore;
        if (scenarioEventStore2 != null) {
            return scenarioEventStore2.getEventsAfter(startEvents);
        }
        PrimesLog.m54w(TAG, "Get event subset on null event store...", new Object[0]);
        return Optional.absent();
    }

    /* access modifiers changed from: package-private */
    public void setStart(@Nullable String start2) {
        this.start = start2;
    }

    /* access modifiers changed from: package-private */
    public void setEnd(@Nullable String end2) {
        this.end = end2;
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return this.name;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String getStart() {
        return this.start;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String getEnd() {
        return this.end;
    }
}
