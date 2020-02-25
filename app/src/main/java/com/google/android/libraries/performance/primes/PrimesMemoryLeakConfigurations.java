package com.google.android.libraries.performance.primes;

public final class PrimesMemoryLeakConfigurations {
    private static final String TAG = "PrimesMemoryLeakConfig";
    private final boolean enabled;
    private final boolean heapDumpEnabled;
    private final boolean quantifyLeakSizeEnabled;

    public PrimesMemoryLeakConfigurations(boolean leakCountEnabled) {
        this(leakCountEnabled, false);
    }

    public PrimesMemoryLeakConfigurations(boolean leakCountEnabled, boolean heapDumpEnabled2) {
        this(leakCountEnabled, heapDumpEnabled2, false);
    }

    private PrimesMemoryLeakConfigurations(boolean leakCountEnabled, boolean heapDumpEnabled2, boolean quantifyLeakSizeEnabled2) {
        this.enabled = leakCountEnabled || heapDumpEnabled2;
        this.heapDumpEnabled = heapDumpEnabled2;
        this.quantifyLeakSizeEnabled = quantifyLeakSizeEnabled2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isHeapDumpEnabled() {
        return this.heapDumpEnabled;
    }

    public boolean isQuantifyLeakSizeEnabled() {
        return this.quantifyLeakSizeEnabled;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.enabled = this.enabled;
        boolean unused2 = builder.heapDumpEnabled = this.heapDumpEnabled;
        boolean unused3 = builder.quantifyLeakSizeEnabled = this.quantifyLeakSizeEnabled;
        return builder;
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean enabled;
        /* access modifiers changed from: private */
        public boolean heapDumpEnabled;
        /* access modifiers changed from: private */
        public boolean quantifyLeakSizeEnabled;

        private Builder() {
        }

        public Builder setEnabled(boolean enabled2) {
            this.enabled = enabled2;
            return this;
        }

        public Builder setHeapDumpEnabled(boolean heapDumpEnabled2) {
            this.heapDumpEnabled = heapDumpEnabled2;
            return this;
        }

        public Builder setQuantifyLeakSizeEnabled(boolean quantifyLeakSizeEnabled2) {
            this.quantifyLeakSizeEnabled = quantifyLeakSizeEnabled2;
            return this;
        }

        public PrimesMemoryLeakConfigurations build() {
            if (this.quantifyLeakSizeEnabled && !this.heapDumpEnabled) {
                PrimesLog.m54w(PrimesMemoryLeakConfigurations.TAG, "Can not quantify leak size without heapdump", new Object[0]);
            }
            return new PrimesMemoryLeakConfigurations(this.enabled, this.heapDumpEnabled, this.quantifyLeakSizeEnabled);
        }
    }
}
