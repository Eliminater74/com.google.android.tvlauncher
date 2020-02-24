package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import com.google.android.libraries.performance.primes.scenario.PrimesScenarioConfigurations;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.common.base.Optional;

public abstract class PrimesConfigurations {
    public MetricTransmitter metricTransmitter() {
        return MetricTransmitter.NOOP_TRANSMITTER;
    }

    public Optional<PrimesGlobalConfigurations> globalConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesMemoryConfigurations> memoryConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesTimerConfigurations> timerConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesCrashConfigurations> crashConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesNetworkConfigurations> networkConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesPackageConfigurations> packageConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesJankConfigurations> jankConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesTikTokTraceConfigurations> tiktokTraceConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesTraceConfigurations> primesTraceConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesBatteryConfigurations> batteryConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesMemoryLeakConfigurations> memoryLeakConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesExperimentalConfigurations> experimentalConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesCpuConfigurations> cpuConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesMiniHeapDumpConfigurations> miniHeapDumpConfigurations() {
        return Optional.absent();
    }

    public Optional<PrimesScenarioConfigurations> scenarioConfigurations() {
        return Optional.absent();
    }

    static PrimesConfigurations lazyValid(PrimesConfigurations configs) {
        return configs instanceof LazyValid ? configs : new LazyValid(configs);
    }

    static final class LazyValid extends PrimesConfigurations {
        private volatile Optional<PrimesBatteryConfigurations> batteryConfigurations;
        private final PrimesConfigurations configs;
        private volatile Optional<PrimesCpuConfigurations> cpuConfigurations;
        private volatile Optional<PrimesCrashConfigurations> crashConfigurations;
        private volatile Optional<PrimesExperimentalConfigurations> experimentalConfigurations;
        private volatile Optional<PrimesGlobalConfigurations> globalConfigurations;
        private volatile Optional<PrimesJankConfigurations> jankConfigurations;
        private volatile Optional<PrimesMemoryConfigurations> memoryConfigurations;
        private volatile Optional<PrimesMemoryLeakConfigurations> memoryLeakConfigurations;
        private volatile MetricTransmitter metricTransmitter;
        private volatile Optional<PrimesMiniHeapDumpConfigurations> miniHeapDumpConfigurations;
        private final Object mutex = new Object();
        private volatile Optional<PrimesNetworkConfigurations> networkConfigurations;
        private volatile Optional<PrimesPackageConfigurations> packageConfigurations;
        private volatile Optional<PrimesTraceConfigurations> primesTraceConfigurations;
        private volatile Optional<PrimesScenarioConfigurations> scenarioConfigurations;
        private volatile Optional<PrimesTikTokTraceConfigurations> tiktokTraceConfigurations;
        private volatile Optional<PrimesTimerConfigurations> timerConfigurations;

        LazyValid(PrimesConfigurations configs2) {
            this.configs = configs2;
        }

        public MetricTransmitter metricTransmitter() {
            if (this.metricTransmitter == null) {
                synchronized (this.mutex) {
                    if (this.metricTransmitter == null) {
                        MetricTransmitter transmitter = this.configs.metricTransmitter();
                        this.metricTransmitter = transmitter != null ? transmitter : MetricTransmitter.NOOP_TRANSMITTER;
                    }
                }
            }
            return this.metricTransmitter;
        }

        public Optional<PrimesMemoryConfigurations> memoryConfigurations() {
            if (this.memoryConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.memoryConfigurations == null) {
                        this.memoryConfigurations = this.configs.memoryConfigurations();
                    }
                }
            }
            return this.memoryConfigurations;
        }

        public Optional<PrimesGlobalConfigurations> globalConfigurations() {
            if (this.globalConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.globalConfigurations == null) {
                        this.globalConfigurations = this.configs.globalConfigurations();
                    }
                }
            }
            return this.globalConfigurations;
        }

        public Optional<PrimesTimerConfigurations> timerConfigurations() {
            Optional<PrimesTimerConfigurations> optional;
            if (this.timerConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.timerConfigurations == null) {
                        Optional<PrimesTimerConfigurations> timerConfigs = this.configs.timerConfigurations();
                        if (!timerConfigs.isPresent() || timerConfigs.get().getSampleRatePerSecond() <= 0) {
                            optional = Optional.absent();
                        } else {
                            optional = timerConfigs;
                        }
                        this.timerConfigurations = optional;
                    }
                }
            }
            return this.timerConfigurations;
        }

        public Optional<PrimesCrashConfigurations> crashConfigurations() {
            if (this.crashConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.crashConfigurations == null) {
                        this.crashConfigurations = this.configs.crashConfigurations();
                    }
                }
            }
            return this.crashConfigurations;
        }

        public Optional<PrimesNetworkConfigurations> networkConfigurations() {
            if (this.networkConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.networkConfigurations == null) {
                        this.networkConfigurations = this.configs.networkConfigurations();
                    }
                }
            }
            return this.networkConfigurations;
        }

        public Optional<PrimesPackageConfigurations> packageConfigurations() {
            if (this.packageConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.packageConfigurations == null) {
                        this.packageConfigurations = this.configs.packageConfigurations();
                    }
                }
            }
            return this.packageConfigurations;
        }

        public Optional<PrimesJankConfigurations> jankConfigurations() {
            if (this.jankConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.jankConfigurations == null) {
                        this.jankConfigurations = this.configs.jankConfigurations();
                    }
                }
            }
            return this.jankConfigurations;
        }

        public Optional<PrimesTikTokTraceConfigurations> tiktokTraceConfigurations() {
            if (this.tiktokTraceConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.tiktokTraceConfigurations == null) {
                        this.tiktokTraceConfigurations = this.configs.tiktokTraceConfigurations();
                    }
                }
            }
            return this.tiktokTraceConfigurations;
        }

        public Optional<PrimesTraceConfigurations> primesTraceConfigurations() {
            if (this.primesTraceConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.primesTraceConfigurations == null) {
                        this.primesTraceConfigurations = this.configs.primesTraceConfigurations();
                    }
                }
            }
            return this.primesTraceConfigurations;
        }

        public Optional<PrimesBatteryConfigurations> batteryConfigurations() {
            if (this.batteryConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.batteryConfigurations == null) {
                        this.batteryConfigurations = this.configs.batteryConfigurations();
                    }
                }
            }
            return this.batteryConfigurations;
        }

        public Optional<PrimesMemoryLeakConfigurations> memoryLeakConfigurations() {
            if (this.memoryLeakConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.memoryLeakConfigurations == null) {
                        this.memoryLeakConfigurations = this.configs.memoryLeakConfigurations();
                    }
                }
            }
            return this.memoryLeakConfigurations;
        }

        public Optional<PrimesExperimentalConfigurations> experimentalConfigurations() {
            if (this.experimentalConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.experimentalConfigurations == null) {
                        this.experimentalConfigurations = this.configs.experimentalConfigurations();
                    }
                }
            }
            return this.experimentalConfigurations;
        }

        public Optional<PrimesCpuConfigurations> cpuConfigurations() {
            Optional<PrimesCpuConfigurations> optional;
            if (this.cpuConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.cpuConfigurations == null) {
                        Optional<PrimesCpuConfigurations> cpuConfigs = this.configs.cpuConfigurations();
                        if (!cpuConfigs.isPresent() || cpuConfigs.get().getInitialDelay() <= 0 || cpuConfigs.get().getNumSamples() <= 0 || cpuConfigs.get().getTimeBetweenSamples() < 100) {
                            optional = Optional.absent();
                        } else {
                            optional = cpuConfigs;
                        }
                        this.cpuConfigurations = optional;
                    }
                }
            }
            return this.cpuConfigurations;
        }

        public Optional<PrimesMiniHeapDumpConfigurations> miniHeapDumpConfigurations() {
            if (this.miniHeapDumpConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.miniHeapDumpConfigurations == null) {
                        this.miniHeapDumpConfigurations = this.configs.miniHeapDumpConfigurations();
                    }
                }
            }
            return this.miniHeapDumpConfigurations;
        }

        public Optional<PrimesScenarioConfigurations> scenarioConfigurations() {
            if (this.scenarioConfigurations == null) {
                synchronized (this.mutex) {
                    if (this.scenarioConfigurations == null) {
                        this.scenarioConfigurations = this.configs.scenarioConfigurations();
                    }
                }
            }
            return this.scenarioConfigurations;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Optional<PrimesBatteryConfigurations> batteryConfigs;
        private Optional<PrimesCpuConfigurations> cpuConfigs;
        private Optional<PrimesCrashConfigurations> crashConfigs;
        private Optional<PrimesExperimentalConfigurations> experimentalConfigs;
        private Optional<PrimesGlobalConfigurations> globalConfigs;
        private Optional<PrimesJankConfigurations> jankConfigs;
        private Optional<PrimesMemoryConfigurations> memoryConfigs;
        private Optional<PrimesMemoryLeakConfigurations> memoryLeakConfigs;
        private MetricTransmitter metricTransmitter;
        private Optional<PrimesMiniHeapDumpConfigurations> miniHeapDumpConfigs;
        private Optional<PrimesNetworkConfigurations> networkConfigs;
        private Optional<PrimesPackageConfigurations> packageConfigs;
        private Optional<PrimesTraceConfigurations> primesTraceConfigs;
        private Optional<PrimesScenarioConfigurations> scenarioConfigs;
        private Optional<PrimesTikTokTraceConfigurations> tiktokTraceConfigs;
        private Optional<PrimesTimerConfigurations> timerConfigs;

        private Builder() {
            this.metricTransmitter = MetricTransmitter.NOOP_TRANSMITTER;
            this.globalConfigs = Optional.absent();
            this.memoryConfigs = Optional.absent();
            this.timerConfigs = Optional.absent();
            this.crashConfigs = Optional.absent();
            this.networkConfigs = Optional.absent();
            this.packageConfigs = Optional.absent();
            this.jankConfigs = Optional.absent();
            this.tiktokTraceConfigs = Optional.absent();
            this.primesTraceConfigs = Optional.absent();
            this.batteryConfigs = Optional.absent();
            this.memoryLeakConfigs = Optional.absent();
            this.experimentalConfigs = Optional.absent();
            this.cpuConfigs = Optional.absent();
            this.miniHeapDumpConfigs = Optional.absent();
            this.scenarioConfigs = Optional.absent();
        }

        public Builder cloneFrom(PrimesConfigurations configurations) {
            this.metricTransmitter = configurations.metricTransmitter();
            this.globalConfigs = configurations.globalConfigurations();
            this.memoryConfigs = configurations.memoryConfigurations();
            this.timerConfigs = configurations.timerConfigurations();
            this.crashConfigs = configurations.crashConfigurations();
            this.networkConfigs = configurations.networkConfigurations();
            this.packageConfigs = configurations.packageConfigurations();
            this.jankConfigs = configurations.jankConfigurations();
            this.tiktokTraceConfigs = configurations.tiktokTraceConfigurations();
            this.primesTraceConfigs = configurations.primesTraceConfigurations();
            this.batteryConfigs = configurations.batteryConfigurations();
            this.memoryLeakConfigs = configurations.memoryLeakConfigurations();
            this.experimentalConfigs = configurations.experimentalConfigurations();
            this.cpuConfigs = configurations.cpuConfigurations();
            this.miniHeapDumpConfigs = configurations.miniHeapDumpConfigurations();
            this.scenarioConfigs = configurations.scenarioConfigurations();
            return this;
        }

        public Builder setMetricTransmitter(MetricTransmitter metricTransmitter2) {
            this.metricTransmitter = metricTransmitter2;
            return this;
        }

        public Builder setMemoryConfigurations(@Nullable PrimesMemoryConfigurations memoryConfigs2) {
            this.memoryConfigs = Optional.fromNullable(memoryConfigs2);
            return this;
        }

        public Builder setGlobalConfigurations(@Nullable PrimesGlobalConfigurations globalConfigs2) {
            this.globalConfigs = Optional.fromNullable(globalConfigs2);
            return this;
        }

        public Builder setTimerConfigurations(@Nullable PrimesTimerConfigurations timerConfigs2) {
            this.timerConfigs = Optional.fromNullable(timerConfigs2);
            return this;
        }

        public Builder setCrashConfigurations(@Nullable PrimesCrashConfigurations crashConfigs2) {
            this.crashConfigs = Optional.fromNullable(crashConfigs2);
            return this;
        }

        public Builder setNetworkConfigurations(@Nullable PrimesNetworkConfigurations networkConfigs2) {
            this.networkConfigs = Optional.fromNullable(networkConfigs2);
            return this;
        }

        public Builder setPackageConfigurations(@Nullable PrimesPackageConfigurations packageConfigs2) {
            this.packageConfigs = Optional.fromNullable(packageConfigs2);
            return this;
        }

        public Builder setJankConfigurations(@Nullable PrimesJankConfigurations jankConfigs2) {
            this.jankConfigs = Optional.fromNullable(jankConfigs2);
            return this;
        }

        @Deprecated
        public Builder setTraceConfigurations(@Nullable PrimesTikTokTraceConfigurations tiktokTraceConfigs2) {
            this.tiktokTraceConfigs = Optional.fromNullable(tiktokTraceConfigs2);
            return this;
        }

        public Builder setTikTokTraceConfigurations(@Nullable PrimesTikTokTraceConfigurations tiktokTraceConfigs2) {
            this.tiktokTraceConfigs = Optional.fromNullable(tiktokTraceConfigs2);
            return this;
        }

        public Builder setPrimesTraceConfigurations(@Nullable PrimesTraceConfigurations primesTraceConfigs2) {
            this.primesTraceConfigs = Optional.fromNullable(primesTraceConfigs2);
            return this;
        }

        public Builder setBatteryConfigurations(@Nullable PrimesBatteryConfigurations batteryConfigs2) {
            this.batteryConfigs = Optional.fromNullable(batteryConfigs2);
            return this;
        }

        public Builder setMemoryLeakConfigurations(@Nullable PrimesMemoryLeakConfigurations memoryLeakConfigs2) {
            this.memoryLeakConfigs = Optional.fromNullable(memoryLeakConfigs2);
            return this;
        }

        public Builder setExperimentalConfigurations(@Nullable PrimesExperimentalConfigurations experimentalConfigs2) {
            this.experimentalConfigs = Optional.fromNullable(experimentalConfigs2);
            return this;
        }

        public Builder setCpuConfigurations(@Nullable PrimesCpuConfigurations cpuConfigs2) {
            this.cpuConfigs = Optional.fromNullable(cpuConfigs2);
            return this;
        }

        public Builder setMiniHeapDumpConfigurations(@Nullable PrimesMiniHeapDumpConfigurations miniHeapDumpConfigurations) {
            this.miniHeapDumpConfigs = Optional.fromNullable(miniHeapDumpConfigurations);
            return this;
        }

        public Builder setScenarioConfigurations(@Nullable PrimesScenarioConfigurations scenarioConfigurations) {
            this.scenarioConfigs = Optional.fromNullable(scenarioConfigurations);
            return this;
        }

        public PrimesConfigurations build() {
            return PrimesConfigurations.lazyValid(new FromBuilder(this.metricTransmitter, this.globalConfigs, this.memoryConfigs, this.timerConfigs, this.crashConfigs, this.networkConfigs, this.packageConfigs, this.jankConfigs, this.tiktokTraceConfigs, this.primesTraceConfigs, this.batteryConfigs, this.memoryLeakConfigs, this.experimentalConfigs, this.cpuConfigs, this.miniHeapDumpConfigs, this.scenarioConfigs));
        }
    }

    static final class FromBuilder extends PrimesConfigurations {
        private final Optional<PrimesBatteryConfigurations> batteryConfigurations;
        private final Optional<PrimesCpuConfigurations> cpuConfigurations;
        private final Optional<PrimesCrashConfigurations> crashConfigurations;
        private final Optional<PrimesExperimentalConfigurations> experimentalConfigurations;
        private final Optional<PrimesGlobalConfigurations> globalConfigurations;
        private final Optional<PrimesJankConfigurations> jankConfigurations;
        private final Optional<PrimesMemoryConfigurations> memoryConfigurations;
        private final Optional<PrimesMemoryLeakConfigurations> memoryLeakConfigurations;
        private final MetricTransmitter metricTransmitter;
        private final Optional<PrimesMiniHeapDumpConfigurations> miniHeapDumpConfigurations;
        private final Optional<PrimesNetworkConfigurations> networkConfigurations;
        private final Optional<PrimesPackageConfigurations> packageConfigurations;
        private final Optional<PrimesTraceConfigurations> primesTraceConfigurations;
        private final Optional<PrimesScenarioConfigurations> scenarioConfigurations;
        private final Optional<PrimesTikTokTraceConfigurations> tiktokTraceConfigurations;
        private final Optional<PrimesTimerConfigurations> timerConfigurations;

        private FromBuilder(MetricTransmitter metricTransmitter2, Optional<PrimesGlobalConfigurations> globalConfigurations2, Optional<PrimesMemoryConfigurations> memoryConfigurations2, Optional<PrimesTimerConfigurations> timerConfigurations2, Optional<PrimesCrashConfigurations> crashConfigurations2, Optional<PrimesNetworkConfigurations> networkConfigurations2, Optional<PrimesPackageConfigurations> packageConfigurations2, Optional<PrimesJankConfigurations> jankConfigurations2, Optional<PrimesTikTokTraceConfigurations> tiktokTraceConfigurations2, Optional<PrimesTraceConfigurations> primesTraceConfigurations2, Optional<PrimesBatteryConfigurations> batteryConfigurations2, Optional<PrimesMemoryLeakConfigurations> memoryLeakConfigurations2, Optional<PrimesExperimentalConfigurations> experimentalConfigurations2, Optional<PrimesCpuConfigurations> cpuConfigurations2, Optional<PrimesMiniHeapDumpConfigurations> miniHeapDumpConfigurations2, Optional<PrimesScenarioConfigurations> scenarioConfigurations2) {
            this.metricTransmitter = metricTransmitter2;
            this.globalConfigurations = globalConfigurations2;
            this.memoryConfigurations = memoryConfigurations2;
            this.timerConfigurations = timerConfigurations2;
            this.crashConfigurations = crashConfigurations2;
            this.networkConfigurations = networkConfigurations2;
            this.packageConfigurations = packageConfigurations2;
            this.jankConfigurations = jankConfigurations2;
            this.tiktokTraceConfigurations = tiktokTraceConfigurations2;
            this.primesTraceConfigurations = primesTraceConfigurations2;
            this.batteryConfigurations = batteryConfigurations2;
            this.memoryLeakConfigurations = memoryLeakConfigurations2;
            this.experimentalConfigurations = experimentalConfigurations2;
            this.cpuConfigurations = cpuConfigurations2;
            this.miniHeapDumpConfigurations = miniHeapDumpConfigurations2;
            this.scenarioConfigurations = scenarioConfigurations2;
        }

        public MetricTransmitter metricTransmitter() {
            return this.metricTransmitter;
        }

        public Optional<PrimesGlobalConfigurations> globalConfigurations() {
            return this.globalConfigurations;
        }

        public Optional<PrimesMemoryConfigurations> memoryConfigurations() {
            return this.memoryConfigurations;
        }

        public Optional<PrimesTimerConfigurations> timerConfigurations() {
            return this.timerConfigurations;
        }

        public Optional<PrimesCrashConfigurations> crashConfigurations() {
            return this.crashConfigurations;
        }

        public Optional<PrimesNetworkConfigurations> networkConfigurations() {
            return this.networkConfigurations;
        }

        public Optional<PrimesPackageConfigurations> packageConfigurations() {
            return this.packageConfigurations;
        }

        public Optional<PrimesJankConfigurations> jankConfigurations() {
            return this.jankConfigurations;
        }

        public Optional<PrimesTikTokTraceConfigurations> tiktokTraceConfigurations() {
            return this.tiktokTraceConfigurations;
        }

        public Optional<PrimesTraceConfigurations> primesTraceConfigurations() {
            return this.primesTraceConfigurations;
        }

        public Optional<PrimesBatteryConfigurations> batteryConfigurations() {
            return this.batteryConfigurations;
        }

        public Optional<PrimesMemoryLeakConfigurations> memoryLeakConfigurations() {
            return this.memoryLeakConfigurations;
        }

        public Optional<PrimesExperimentalConfigurations> experimentalConfigurations() {
            return this.experimentalConfigurations;
        }

        public Optional<PrimesCpuConfigurations> cpuConfigurations() {
            return this.cpuConfigurations;
        }

        public Optional<PrimesMiniHeapDumpConfigurations> miniHeapDumpConfigurations() {
            return this.miniHeapDumpConfigurations;
        }

        public Optional<PrimesScenarioConfigurations> scenarioConfigurations() {
            return this.scenarioConfigurations;
        }
    }
}
