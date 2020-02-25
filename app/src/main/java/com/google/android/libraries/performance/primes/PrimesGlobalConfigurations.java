package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;

public final class PrimesGlobalConfigurations {
    @Nullable
    private final ComponentNameSupplier componentNameSupplier;

    private PrimesGlobalConfigurations(@Nullable ComponentNameSupplier componentNameSupplier2) {
        this.componentNameSupplier = componentNameSupplier2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ComponentNameSupplier getComponentNameSupplier() {
        return this.componentNameSupplier;
    }

    public interface ComponentNameSupplier extends Supplier<NoPiiString> {
        NoPiiString get();
    }

    public static final class Builder {
        private ComponentNameSupplier componentNameSupplier;

        private Builder() {
        }

        public Builder setComponentName(final NoPiiString componentName) {
            this.componentNameSupplier = new ComponentNameSupplier(this) {
                public NoPiiString get() {
                    return componentName;
                }
            };
            return this;
        }

        public Builder setComponentNameSupplier(ComponentNameSupplier componentNameSupplier2) {
            this.componentNameSupplier = componentNameSupplier2;
            return this;
        }

        public PrimesGlobalConfigurations build() {
            return new PrimesGlobalConfigurations(this.componentNameSupplier);
        }
    }
}
