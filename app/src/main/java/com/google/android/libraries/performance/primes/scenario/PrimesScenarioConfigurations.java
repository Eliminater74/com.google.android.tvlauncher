package com.google.android.libraries.performance.primes.scenario;

import com.google.android.libraries.stitch.util.Preconditions;

import java.util.concurrent.TimeUnit;

public final class PrimesScenarioConfigurations {
    /* access modifiers changed from: private */
    public static final int DEFAULT_TIMEOUT_MS = ((int) TimeUnit.HOURS.toMillis(1));
    private static final int DEFAULT_MAX_ACTIVE_SCENARIOS = 10;
    private static final float DEFAULT_SAMPLING_PROBABILITY = 1.0f;
    private final boolean isEnabled;
    private final boolean isTotalPssCaptureEnabled;
    private final int maxActiveScenarios;
    private final float samplingProbability;
    private final ScenarioStructureProvider scenarioStructureProvider;
    private final int timeoutMs;

    private PrimesScenarioConfigurations(boolean isEnabled2, float samplingProbability2, int timeoutMs2, int maxActiveScenarios2, ScenarioStructureProvider scenarioStructureProvider2, boolean isTotalPssCaptureEnabled2) {
        this.isEnabled = isEnabled2;
        this.samplingProbability = samplingProbability2;
        this.timeoutMs = timeoutMs2;
        this.maxActiveScenarios = maxActiveScenarios2;
        this.scenarioStructureProvider = scenarioStructureProvider2;
        this.isTotalPssCaptureEnabled = isTotalPssCaptureEnabled2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public float getSamplingProbability() {
        return this.samplingProbability;
    }

    public int getTimeoutMs() {
        return this.timeoutMs;
    }

    public int getMaxActiveScenarios() {
        return this.maxActiveScenarios;
    }

    public ScenarioStructureProvider getScenarioStructureProvider() {
        return this.scenarioStructureProvider;
    }

    public boolean isTotalPssCaptureEnabled() {
        return this.isTotalPssCaptureEnabled;
    }

    public static final class Builder {
        private boolean isEnabled;
        private boolean isTotalPssCaptureEnabled;
        private int maxActiveScenarios;
        private float samplingProbability;
        private ScenarioStructureProvider scenarioStructureProvider;
        private int timeoutMs;

        private Builder() {
            this.samplingProbability = PrimesScenarioConfigurations.DEFAULT_SAMPLING_PROBABILITY;
            this.timeoutMs = PrimesScenarioConfigurations.DEFAULT_TIMEOUT_MS;
            this.maxActiveScenarios = 10;
        }

        public Builder setEnabled(boolean enabled) {
            this.isEnabled = enabled;
            return this;
        }

        public Builder setSamplingProbability(float samplingProbability2) {
            Preconditions.checkState(samplingProbability2 >= 0.0f && samplingProbability2 <= PrimesScenarioConfigurations.DEFAULT_SAMPLING_PROBABILITY, "Probability shall be between 0 and 1.");
            this.samplingProbability = samplingProbability2;
            return this;
        }

        public Builder setTimeoutMs(int timeoutMs2) {
            Preconditions.checkState(timeoutMs2 > 0);
            this.timeoutMs = timeoutMs2;
            return this;
        }

        public Builder setMaxActiveScenarios(int maxActiveScenarios2) {
            this.maxActiveScenarios = maxActiveScenarios2;
            return this;
        }

        public Builder setScenarioStructureProvider(ScenarioStructureProvider scenarioStructureProvider2) {
            this.scenarioStructureProvider = scenarioStructureProvider2;
            return this;
        }

        public Builder setTotalPssCaptureEnabled(boolean enabled) {
            this.isTotalPssCaptureEnabled = enabled;
            return this;
        }

        public PrimesScenarioConfigurations build() {
            return new PrimesScenarioConfigurations(this.isEnabled, this.samplingProbability, this.timeoutMs, this.maxActiveScenarios, this.scenarioStructureProvider, this.isTotalPssCaptureEnabled);
        }
    }
}
