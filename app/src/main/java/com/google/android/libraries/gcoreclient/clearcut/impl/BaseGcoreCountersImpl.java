package com.google.android.libraries.gcoreclient.clearcut.impl;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutMessageProducer;
import com.google.android.libraries.gcoreclient.clearcut.GcoreCounters;
import com.google.android.libraries.gcoreclient.clearcut.GcoreCountersAlias;
import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.GcorePendingResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreResultCallback;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;
import com.google.android.libraries.gcoreclient.common.api.support.GcoreStatusImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

abstract class BaseGcoreCountersImpl implements GcoreCounters {
    private static final GcorePendingResult<GcoreStatus> errorResult = new GcorePendingResult<GcoreStatus>() {
        private final GcoreStatus status = new GcoreStatusImpl(new Status(10));

        public GcoreStatus await() {
            return this.status;
        }

        public GcoreStatus await(long time, TimeUnit units) {
            return this.status;
        }

        public void cancel() {
        }

        public void setResultCallback(GcoreResultCallback<GcoreStatus> callback) {
            callback.onResult(this.status);
        }

        public void setResultCallback(GcoreResultCallback<GcoreStatus> callback, long time, TimeUnit units) {
            callback.onResult(this.status);
        }
    };

    BaseGcoreCountersImpl() {
    }

    public GcoreCounters.GcoreBooleanHistogram getBooleanHistogram(String name) {
        return new GcoreNoOpBooleanHistogram(name);
    }

    public GcoreCounters.GcoreCounter getCounter(String name) {
        return new NoOpCounter(name);
    }

    public Collection<byte[]> getDimensionsInstances() {
        return Collections.emptySet();
    }

    public GcoreCounters.GcoreIntegerHistogram getIntegerHistogram(String name) {
        return new GcoreNoOpIntegerHistogram(name);
    }

    public GcoreCounters.GcoreLongHistogram getLongHistogram(String name, GcoreCountersAlias alias) {
        return new GcoreNoOpLongHistogram(name);
    }

    public GcoreCounters.GcoreLongHistogram getLongHistogram(String name) {
        return new GcoreNoOpLongHistogram(name);
    }

    public GcoreCounters.GcoreTimerHistogram getTimerHistogram(String name) {
        return new GcoreNoOpTimerHistogram(name);
    }

    public GcoreCounters.GcoreTimerHistogram getTimerHistogram(String name, GcoreCountersAlias alias) {
        return new GcoreNoOpTimerHistogram(name);
    }

    public GcorePendingResult<GcoreStatus> logAll(GcoreGoogleApiClient apiClient) {
        return errorResult;
    }

    public GcorePendingResult<GcoreStatus> logAll() {
        return errorResult;
    }

    public void logAllAsync() {
    }

    public GcorePendingResult<GcoreStatus> logAllAsync(GcoreGoogleApiClient apiClient) {
        return errorResult;
    }

    public GcoreClearcutMessageProducer makeProducer(byte[] dimensionInstance) {
        return new GcoreClearcutMessageProducer(this) {
            public byte[] toProtoBytes() {
                return new byte[0];
            }
        };
    }

    public GcoreCounters.GcoreBooleanHistogram newBooleanHistogram(String name) {
        return new GcoreNoOpBooleanHistogram(name);
    }

    public GcoreCounters.GcoreCounter newCounter(String name) {
        return new NoOpCounter(name);
    }

    public GcoreCounters.GcoreIntegerHistogram newIntegerHistogram(String name) {
        return new GcoreNoOpIntegerHistogram(name);
    }

    public GcoreCounters.GcoreLongHistogram newLongHistogram(String name, GcoreCountersAlias alias) {
        return new GcoreNoOpLongHistogram(name);
    }

    public GcoreCounters.GcoreLongHistogram newLongHistogram(String name) {
        return new GcoreNoOpLongHistogram(name);
    }

    public GcoreCounters.GcoreTimer newTimer() {
        return new NoOpTimer();
    }

    public GcoreCounters.GcoreTimerHistogram newTimerHistogram(String name) {
        return new GcoreNoOpTimerHistogram(name);
    }

    public GcoreCounters.GcoreTimerHistogram newTimerHistogram(String name, GcoreCountersAlias alias) {
        return new GcoreNoOpTimerHistogram(name);
    }

    public void setAutoLogAsync(GcoreGoogleApiClient apiClient) {
    }

    public void setDimensionsInstance(byte[] serializedDimensionsProto) {
    }

    public GcoreCounters snapshot() {
        return this;
    }

    public GcoreCounters snapshotAndReset() {
        return this;
    }

    private static class GcoreNoOpBooleanHistogram implements GcoreCounters.GcoreBooleanHistogram {
        private final String name;

        private GcoreNoOpBooleanHistogram(String name2) {
            this.name = name2;
        }

        public long getCount(boolean key) {
            return 0;
        }

        public String getName() {
            return this.name;
        }

        public void increment(boolean key) {
        }
    }

    private static class NoOpCounter implements GcoreCounters.GcoreCounter {
        private final String name;

        private NoOpCounter(String name2) {
            this.name = name2;
        }

        public long getCount() {
            return 0;
        }

        public String getName() {
            return this.name;
        }

        public void increment() {
        }

        public void incrementBy(long increment) {
        }
    }

    private static class GcoreNoOpIntegerHistogram implements GcoreCounters.GcoreIntegerHistogram {
        private final String name;

        private GcoreNoOpIntegerHistogram(String name2) {
            this.name = name2;
        }

        public long getCount(int key) {
            return 0;
        }

        public String getName() {
            return this.name;
        }

        public void increment(int key) {
        }
    }

    private static class GcoreNoOpLongHistogram implements GcoreCounters.GcoreLongHistogram {
        private final String name;

        private GcoreNoOpLongHistogram(String name2) {
            this.name = name2;
        }

        public long getCount(long key) {
            return 0;
        }

        public String getName() {
            return this.name;
        }

        public void increment(long key) {
        }

        public void incrementBy(long key, long increment) {
        }
    }

    private static class NoOpTimer implements GcoreCounters.GcoreTimer {
        private NoOpTimer() {
        }

        public long getMilliseconds() {
            return 0;
        }

        public void incrementTo(GcoreCounters.GcoreTimerHistogram timerHistogram) {
        }

        public long reset() {
            return 0;
        }
    }

    private static class GcoreNoOpTimerHistogram implements GcoreCounters.GcoreTimerHistogram {
        private final String name;

        private GcoreNoOpTimerHistogram(String name2) {
            this.name = name2;
        }

        public long getCount(long key) {
            return 0;
        }

        public String getName() {
            return this.name;
        }

        public GcoreCounters.GcoreTimerHistogram.BoundTimer newTimer() {
            return new NoOpBoundTimer();
        }

        private static class NoOpBoundTimer implements GcoreCounters.GcoreTimerHistogram.BoundTimer {
            private NoOpBoundTimer() {
            }

            public long getMilliseconds() {
                return 0;
            }

            public void incrementTo() {
            }

            public void reset() {
            }
        }
    }
}
