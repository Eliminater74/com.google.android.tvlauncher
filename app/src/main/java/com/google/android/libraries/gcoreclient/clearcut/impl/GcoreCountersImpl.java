package com.google.android.libraries.gcoreclient.clearcut.impl;

import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.clearcut.Counters;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogger;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutMessageProducer;
import com.google.android.libraries.gcoreclient.clearcut.GcoreCounters;
import com.google.android.libraries.gcoreclient.clearcut.GcoreCountersAlias;
import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.GcorePendingResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;
import com.google.android.libraries.gcoreclient.common.api.support.GcorePendingResultImpl;
import com.google.android.libraries.gcoreclient.common.api.support.GcoreStatusImpl;
import com.google.android.libraries.gcoreclient.common.api.support.GcoreWrapper;

import java.util.Collection;

final class GcoreCountersImpl extends BaseGcoreCountersImpl implements GcoreCounters {
    /* access modifiers changed from: private */
    public final Counters counters;
    private final GcoreWrapper wrapper;

    GcoreCountersImpl(GcoreClearcutLogger logger, String logSourceName, int maxSamplesPerCounter, GcoreWrapper wrapper2) {
        this(new Counters(BaseClearcutLoggerImpl.unwrap(logger), logSourceName, maxSamplesPerCounter), wrapper2);
    }

    private GcoreCountersImpl(Counters counters2, GcoreWrapper wrapper2) {
        this.counters = counters2;
        this.wrapper = wrapper2;
    }

    public GcoreCounters.GcoreBooleanHistogram getBooleanHistogram(String name) {
        return new GcoreBooleanHistogramImpl(this.counters.getBooleanHistogram(name), name);
    }

    public GcoreCounters.GcoreCounter getCounter(String name) {
        return new GcoreCounterImpl(this.counters.getCounter(name), name);
    }

    public Collection<byte[]> getDimensionsInstances() {
        return this.counters.getDimensionsInstances();
    }

    public GcoreCounters.GcoreIntegerHistogram getIntegerHistogram(String name) {
        return new GcoreIntegerHistogramImpl(this.counters.getIntegerHistogram(name), name);
    }

    public GcoreCounters.GcoreLongHistogram getLongHistogram(String name, final GcoreCountersAlias alias) {
        return new GcoreLongHistogramImpl(this.counters.getLongHistogram(name, new Counters.Alias(this) {
            public long alias(long rawKey) {
                return alias.alias(rawKey);
            }
        }), name);
    }

    public GcoreCounters.GcoreLongHistogram getLongHistogram(String name) {
        return new GcoreLongHistogramImpl(this.counters.getLongHistogram(name), name);
    }

    public GcoreCounters.GcoreTimerHistogram getTimerHistogram(String name) {
        return new GcoreTimerHistogramImpl(this.counters.getTimerHistogram(name), name);
    }

    public GcoreCounters.GcoreTimerHistogram getTimerHistogram(String name, final GcoreCountersAlias alias) {
        return new GcoreTimerHistogramImpl(this.counters.getTimerHistogram(name, new Counters.Alias(this) {
            public long alias(long rawKey) {
                return alias.alias(rawKey);
            }
        }), name);
    }

    public GcorePendingResult<GcoreStatus> logAll(GcoreGoogleApiClient apiClient) {
        return new GcorePendingResultImpl(this.counters.logAll(this.wrapper.unwrap(apiClient)), GcoreStatusImpl.STATUS_RESULT_WRAPPER);
    }

    public GcorePendingResult<GcoreStatus> logAll() {
        return new GcorePendingResultImpl(this.counters.logAll(), GcoreStatusImpl.STATUS_RESULT_WRAPPER);
    }

    public void logAllAsync() {
        this.counters.logAllAsync();
    }

    public GcorePendingResult<GcoreStatus> logAllAsync(GcoreGoogleApiClient apiClient) {
        return new GcorePendingResultImpl(this.counters.logAllAsync(this.wrapper.unwrap(apiClient)), GcoreStatusImpl.STATUS_RESULT_WRAPPER);
    }

    public GcoreClearcutMessageProducer makeProducer(final byte[] dimensionInstance) {
        return new GcoreClearcutMessageProducer() {
            final ClearcutLogger.MessageProducer producer = GcoreCountersImpl.this.counters.makeProducer(dimensionInstance);

            public byte[] toProtoBytes() {
                return this.producer.toProtoBytes();
            }
        };
    }

    public GcoreCounters.GcoreBooleanHistogram newBooleanHistogram(String name) {
        return new GcoreBooleanHistogramImpl(this.counters.newBooleanHistogram(name), name);
    }

    public GcoreCounters.GcoreCounter newCounter(String name) {
        return new GcoreCounterImpl(this.counters.newCounter(name), name);
    }

    public GcoreCounters.GcoreIntegerHistogram newIntegerHistogram(String name) {
        return new GcoreIntegerHistogramImpl(this.counters.newIntegerHistogram(name), name);
    }

    public GcoreCounters.GcoreLongHistogram newLongHistogram(String name, final GcoreCountersAlias alias) {
        return new GcoreLongHistogramImpl(this.counters.newLongHistogram(name, new Counters.Alias(this) {
            public long alias(long rawKey) {
                return alias.alias(rawKey);
            }
        }), name);
    }

    public GcoreCounters.GcoreLongHistogram newLongHistogram(String name) {
        return new GcoreLongHistogramImpl(this.counters.newLongHistogram(name), name);
    }

    public GcoreCounters.GcoreTimer newTimer() {
        return new GcoreTimerImpl(this.counters.newTimer());
    }

    public GcoreCounters.GcoreTimerHistogram newTimerHistogram(String name) {
        return new GcoreTimerHistogramImpl(this.counters.newTimerHistogram(name), name);
    }

    public GcoreCounters.GcoreTimerHistogram newTimerHistogram(String name, final GcoreCountersAlias alias) {
        return new GcoreTimerHistogramImpl(this.counters.newTimerHistogram(name, new Counters.Alias(this) {
            public long alias(long rawKey) {
                return alias.alias(rawKey);
            }
        }), name);
    }

    public void setAutoLogAsync(GcoreGoogleApiClient apiClient) {
        this.counters.setAutoLogAsync(this.wrapper.unwrap(apiClient));
    }

    public void setDimensionsInstance(byte[] serializedDimensionsProto) {
        this.counters.setDimensionsInstance(serializedDimensionsProto);
    }

    public GcoreCounters snapshot() {
        return new GcoreCountersImpl(this.counters.snapshot(), this.wrapper);
    }

    public GcoreCounters snapshotAndReset() {
        return new GcoreCountersImpl(this.counters.snapshotAndReset(), this.wrapper);
    }

    private static class GcoreBooleanHistogramImpl implements GcoreCounters.GcoreBooleanHistogram {
        private final Counters.BooleanHistogram histogram;
        private final String name;

        private GcoreBooleanHistogramImpl(Counters.BooleanHistogram histogram2, String name2) {
            this.histogram = histogram2;
            this.name = name2;
        }

        public long getCount(boolean key) {
            return this.histogram.getCount(key);
        }

        public String getName() {
            return this.name;
        }

        public void increment(boolean key) {
            this.histogram.increment(key);
        }
    }

    private static class GcoreCounterImpl implements GcoreCounters.GcoreCounter {
        private final Counters.Counter counter;
        private final String name;

        private GcoreCounterImpl(Counters.Counter counter2, String name2) {
            this.counter = counter2;
            this.name = name2;
        }

        public long getCount() {
            return this.counter.getCount();
        }

        public String getName() {
            return this.name;
        }

        public void increment() {
            this.counter.increment();
        }

        public void incrementBy(long increment) {
            this.counter.incrementBy(increment);
        }
    }

    private static class GcoreIntegerHistogramImpl implements GcoreCounters.GcoreIntegerHistogram {
        private final Counters.IntegerHistogram histogram;
        private final String name;

        private GcoreIntegerHistogramImpl(Counters.IntegerHistogram histogram2, String name2) {
            this.histogram = histogram2;
            this.name = name2;
        }

        public long getCount(int key) {
            return this.histogram.getCount(key);
        }

        public String getName() {
            return this.name;
        }

        public void increment(int key) {
            this.histogram.increment(key);
        }
    }

    private static class GcoreLongHistogramImpl implements GcoreCounters.GcoreLongHistogram {
        private final Counters.LongHistogram histogram;
        private final String name;

        private GcoreLongHistogramImpl(Counters.LongHistogram histogram2, String name2) {
            this.histogram = histogram2;
            this.name = name2;
        }

        public long getCount(long key) {
            return this.histogram.getCount(key);
        }

        public String getName() {
            return this.name;
        }

        public void increment(long key) {
            this.histogram.increment(key);
        }

        public void incrementBy(long key, long increment) {
            this.histogram.incrementBy(key, increment);
        }
    }

    private static class GcoreTimerImpl implements GcoreCounters.GcoreTimer {
        private final Counters.Timer timer;

        private GcoreTimerImpl(Counters.Timer timer2) {
            this.timer = timer2;
        }

        public long getMilliseconds() {
            return this.timer.getMilliseconds();
        }

        public void incrementTo(GcoreCounters.GcoreTimerHistogram timerHistogram) {
            if (timerHistogram instanceof GcoreTimerHistogramImpl) {
                this.timer.incrementTo(((GcoreTimerHistogramImpl) timerHistogram).unwrap());
            }
        }

        public long reset() {
            return this.timer.reset();
        }
    }

    private static class GcoreTimerHistogramImpl implements GcoreCounters.GcoreTimerHistogram {
        private final Counters.TimerHistogram histogram;
        private final String name;

        private GcoreTimerHistogramImpl(Counters.TimerHistogram histogram2, String name2) {
            this.histogram = histogram2;
            this.name = name2;
        }

        /* access modifiers changed from: package-private */
        public Counters.TimerHistogram unwrap() {
            return this.histogram;
        }

        public long getCount(long key) {
            return this.histogram.getCount(key);
        }

        public String getName() {
            return this.name;
        }

        public GcoreCounters.GcoreTimerHistogram.BoundTimer newTimer() {
            return new GcoreBoundTimerImpl(this.histogram.newTimer());
        }

        private static class GcoreBoundTimerImpl implements GcoreCounters.GcoreTimerHistogram.BoundTimer {
            private Counters.TimerHistogram.BoundTimer timer;

            private GcoreBoundTimerImpl(Counters.TimerHistogram.BoundTimer timer2) {
                this.timer = timer2;
            }

            public long getMilliseconds() {
                return this.timer.getMilliseconds();
            }

            public void incrementTo() {
                this.timer.incrementTo();
            }

            public void reset() {
                this.timer.reset();
            }
        }
    }
}
