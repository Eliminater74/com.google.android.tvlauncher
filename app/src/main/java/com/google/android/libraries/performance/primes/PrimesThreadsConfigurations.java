package com.google.android.libraries.performance.primes;

import android.app.Activity;

import com.google.android.libraries.stitch.util.Preconditions;

import java.util.concurrent.ScheduledExecutorService;

public final class PrimesThreadsConfigurations {
    static final int DEFAULT_THREAD_POOL_SIZE = 2;
    static final int DEFAULT_THREAD_PRIORITY = 0;
    /* access modifiers changed from: private */
    public final ActivityResumedCallback activityResumedCallback;
    /* access modifiers changed from: private */
    public final boolean enableEarlyTimers;
    /* access modifiers changed from: private */
    public final InitAfterResumedFlag initAfterResumed;
    /* access modifiers changed from: private */
    public final ScheduledExecutorService primesExecutorService;
    /* access modifiers changed from: private */
    public final int primesInitializationPriority;
    /* access modifiers changed from: private */
    public final int primesMetricExecutorPoolSize;
    /* access modifiers changed from: private */
    public final int primesMetricExecutorPriority;

    private PrimesThreadsConfigurations(ScheduledExecutorService primesExecutorService2, int primesInitializationPriority2, int primesMetricExecutorPriority2, int primesMetricExecutorPoolSize2, InitAfterResumedFlag initAfterResumed2, ActivityResumedCallback activityResumedCallback2, boolean enableEarlyTimers2) {
        this.primesExecutorService = primesExecutorService2;
        this.primesInitializationPriority = primesInitializationPriority2;
        this.primesMetricExecutorPriority = primesMetricExecutorPriority2;
        this.primesMetricExecutorPoolSize = primesMetricExecutorPoolSize2;
        this.initAfterResumed = initAfterResumed2;
        this.activityResumedCallback = activityResumedCallback2;
        this.enableEarlyTimers = enableEarlyTimers2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public ScheduledExecutorService getPrimesExecutorService() {
        return this.primesExecutorService;
    }

    public int getPrimesInitializationPriority() {
        return this.primesInitializationPriority;
    }

    public int getPrimesMetricExecutorPriority() {
        return this.primesMetricExecutorPriority;
    }

    public int getPrimesMetricExecutorPoolSize() {
        return this.primesMetricExecutorPoolSize;
    }

    public InitAfterResumedFlag getInitAfterResumed() {
        return this.initAfterResumed;
    }

    public ActivityResumedCallback getActivityResumedCallback() {
        return this.activityResumedCallback;
    }

    public boolean enableEarlyTimers() {
        return this.enableEarlyTimers;
    }

    public interface ActivityResumedCallback {
        void onActivityResumed(Activity activity, Runnable runnable);
    }

    public interface InitAfterResumedFlag {
        boolean isEnabled();
    }

    public static final class Builder {
        private ActivityResumedCallback activityResumedCallback;
        private boolean enableEarlyTimers;
        private InitAfterResumedFlag initAfterResumed;
        private ScheduledExecutorService primesExecutorService;
        private int primesInitializationPriority;
        private int primesMetricExecutorPoolSize;
        private int primesMetricExecutorPriority;

        private Builder() {
            this.primesInitializationPriority = 0;
            this.primesMetricExecutorPriority = 0;
            this.primesMetricExecutorPoolSize = 2;
        }

        public Builder cloneFrom(PrimesThreadsConfigurations configurations) {
            this.primesExecutorService = configurations.primesExecutorService;
            this.primesInitializationPriority = configurations.primesInitializationPriority;
            this.primesMetricExecutorPriority = configurations.primesMetricExecutorPriority;
            this.primesMetricExecutorPoolSize = configurations.primesMetricExecutorPoolSize;
            this.initAfterResumed = configurations.initAfterResumed;
            this.activityResumedCallback = configurations.activityResumedCallback;
            this.enableEarlyTimers = configurations.enableEarlyTimers;
            return this;
        }

        public Builder setPrimesExecutorService(CustomExecutorToken token, ScheduledExecutorService service) {
            if (token != null) {
                this.primesExecutorService = service;
                return this;
            }
            throw new NullPointerException("token should not be null");
        }

        public Builder setPrimesInitializationPriority(int primesInitializationPriority2) {
            this.primesInitializationPriority = primesInitializationPriority2;
            return this;
        }

        public Builder setPrimesMetricExecutorPriority(int priority) {
            this.primesMetricExecutorPriority = priority;
            return this;
        }

        public Builder setPrimesMetricExecutorPoolSize(int poolSize) {
            Preconditions.checkState(poolSize > 0 && poolSize <= 2, "Thread pool size must be less than or equal to %s", 2);
            this.primesMetricExecutorPoolSize = poolSize;
            return this;
        }

        public Builder setInitAfterActivityResumed(InitAfterResumedFlag initAfterResumed2) {
            this.initAfterResumed = initAfterResumed2;
            return this;
        }

        public Builder setActivityResumedCallback(ActivityResumedCallback activityResumedCallback2) {
            this.activityResumedCallback = activityResumedCallback2;
            return this;
        }

        public Builder setEnableEarlyTimers(boolean enabled) {
            this.enableEarlyTimers = enabled;
            return this;
        }

        public PrimesThreadsConfigurations build() {
            return new PrimesThreadsConfigurations(this.primesExecutorService, this.primesInitializationPriority, this.primesMetricExecutorPriority, this.primesMetricExecutorPoolSize, this.initAfterResumed, this.activityResumedCallback, this.enableEarlyTimers);
        }
    }
}
