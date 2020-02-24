package com.google.android.libraries.performance.primes.scenario;

import android.support.annotation.Nullable;
import com.google.android.libraries.performance.primes.NoPiiString;
import com.google.android.libraries.stitch.util.Preconditions;

public final class ScenarioStartInfo {
    private final String scenarioName;
    private final String start;

    private ScenarioStartInfo(String scenarioName2, @Nullable String start2) {
        this.scenarioName = scenarioName2;
        this.start = start2;
    }

    public String getScenarioName() {
        return this.scenarioName;
    }

    public String getStart() {
        return this.start;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String scenarioName;
        private String start;

        private Builder() {
        }

        public Builder setScenarioName(NoPiiString scenarioName2) {
            Preconditions.checkNotNull(scenarioName2);
            this.scenarioName = NoPiiString.safeToString(scenarioName2);
            return this;
        }

        public Builder setStart(NoPiiString start2) {
            Preconditions.checkNotNull(start2);
            this.start = NoPiiString.safeToString(start2);
            return this;
        }

        public ScenarioStartInfo build() {
            Preconditions.checkNotNull(this.scenarioName);
            return new ScenarioStartInfo(this.scenarioName, this.start);
        }
    }
}
