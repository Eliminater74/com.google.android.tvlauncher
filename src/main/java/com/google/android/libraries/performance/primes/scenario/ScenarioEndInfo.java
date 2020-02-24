package com.google.android.libraries.performance.primes.scenario;

import android.support.annotation.Nullable;
import com.google.android.libraries.performance.primes.NoPiiString;
import com.google.android.libraries.stitch.util.Preconditions;

public final class ScenarioEndInfo {
    private final String end;
    private final String scenarioName;

    private ScenarioEndInfo(String scenarioName2, @Nullable String end2) {
        this.scenarioName = scenarioName2;
        this.end = end2;
    }

    public String getScenarioName() {
        return this.scenarioName;
    }

    public String getEnd() {
        return this.end;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String end;
        private String scenarioName;

        private Builder() {
        }

        public Builder setScenarioName(NoPiiString scenarioName2) {
            Preconditions.checkNotNull(scenarioName2);
            this.scenarioName = NoPiiString.safeToString(scenarioName2);
            return this;
        }

        public Builder setEnd(NoPiiString end2) {
            Preconditions.checkNotNull(end2);
            this.end = NoPiiString.safeToString(end2);
            return this;
        }

        public ScenarioEndInfo build() {
            Preconditions.checkNotNull(this.scenarioName);
            return new ScenarioEndInfo(this.scenarioName, this.end);
        }
    }
}
