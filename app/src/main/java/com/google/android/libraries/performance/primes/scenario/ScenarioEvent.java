package com.google.android.libraries.performance.primes.scenario;

import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.NoPiiString;
import com.google.android.libraries.performance.primes.metriccapture.TimeCapture;
import com.google.android.libraries.stitch.util.Preconditions;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ScenarioEvent {
    private final String eventName;
    private final Map<String, String> tags;
    private final long timestampMs;

    private ScenarioEvent(String eventName2, Map<String, String> tags2, long timestampMs2) {
        this.eventName = eventName2;
        this.tags = tags2;
        this.timestampMs = timestampMs2;
    }

    private static boolean objectEquals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getEventName() {
        return this.eventName;
    }

    public long getTimestampMs() {
        return this.timestampMs;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public boolean tagMatch(ScenarioEvent event) {
        Map<String, String> otherTags = event.getTags();
        if (this.tags.isEmpty() || otherTags.isEmpty()) {
            return true;
        }
        Set<String> commonKeys = new HashSet<>(this.tags.keySet());
        commonKeys.retainAll(otherTags.keySet());
        for (String key : commonKeys) {
            if (!objectEquals(this.tags.get(key), otherTags.get(key))) {
                return false;
            }
        }
        return true;
    }

    public static final class Builder {
        private static final long UNSET_TIMESTAMP_MS = -1;
        private String eventName;
        private Map<String, String> tags;
        private long timestampMs;

        private Builder() {
            this.tags = Collections.emptyMap();
            this.timestampMs = -1;
        }

        public Builder setEventName(NoPiiString eventName2) {
            this.eventName = NoPiiString.safeToString(eventName2);
            return this;
        }

        public Builder addTag(String key, String value) {
            if (this.tags.isEmpty()) {
                this.tags = new HashMap();
            }
            this.tags.put(key, value);
            return this;
        }

        @VisibleForTesting
        public Builder setTimestampMs(long timestampMs2) {
            this.timestampMs = timestampMs2;
            return this;
        }

        public ScenarioEvent build() {
            Preconditions.checkNotNull(this.eventName);
            if (this.timestampMs == -1) {
                this.timestampMs = TimeCapture.getTime();
            }
            return new ScenarioEvent(this.eventName, this.tags, this.timestampMs);
        }
    }
}
