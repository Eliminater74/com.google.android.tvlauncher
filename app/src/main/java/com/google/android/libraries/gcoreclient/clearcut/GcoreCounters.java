package com.google.android.libraries.gcoreclient.clearcut;

import com.google.android.libraries.gcoreclient.common.api.GcoreGoogleApiClient;
import com.google.android.libraries.gcoreclient.common.api.GcorePendingResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreStatus;

import java.util.Collection;

public interface GcoreCounters {

    GcoreBooleanHistogram getBooleanHistogram(String str);

    GcoreCounter getCounter(String str);

    Collection<byte[]> getDimensionsInstances();

    GcoreIntegerHistogram getIntegerHistogram(String str);

    GcoreLongHistogram getLongHistogram(String str);

    GcoreLongHistogram getLongHistogram(String str, GcoreCountersAlias gcoreCountersAlias);

    GcoreTimerHistogram getTimerHistogram(String str);

    GcoreTimerHistogram getTimerHistogram(String str, GcoreCountersAlias gcoreCountersAlias);

    GcorePendingResult<GcoreStatus> logAll();

    @Deprecated
    GcorePendingResult<GcoreStatus> logAll(GcoreGoogleApiClient gcoreGoogleApiClient);

    GcorePendingResult<GcoreStatus> logAllAsync(GcoreGoogleApiClient gcoreGoogleApiClient);

    void logAllAsync();

    GcoreClearcutMessageProducer makeProducer(byte[] bArr);

    GcoreBooleanHistogram newBooleanHistogram(String str);

    GcoreCounter newCounter(String str);

    GcoreIntegerHistogram newIntegerHistogram(String str);

    GcoreLongHistogram newLongHistogram(String str);

    GcoreLongHistogram newLongHistogram(String str, GcoreCountersAlias gcoreCountersAlias);

    GcoreTimer newTimer();

    GcoreTimerHistogram newTimerHistogram(String str);

    GcoreTimerHistogram newTimerHistogram(String str, GcoreCountersAlias gcoreCountersAlias);

    void setAutoLogAsync(GcoreGoogleApiClient gcoreGoogleApiClient);

    void setDimensionsInstance(byte[] bArr);

    GcoreCounters snapshot();

    GcoreCounters snapshotAndReset();

    public interface GcoreBooleanHistogram {
        long getCount(boolean z);

        String getName();

        void increment(boolean z);
    }

    public interface GcoreCounter {
        long getCount();

        String getName();

        void increment();

        void incrementBy(long j);
    }

    public interface GcoreIntegerHistogram {
        long getCount(int i);

        String getName();

        void increment(int i);
    }

    public interface GcoreLongHistogram {
        long getCount(long j);

        String getName();

        void increment(long j);

        void incrementBy(long j, long j2);
    }

    public interface GcoreTimer {
        long getMilliseconds();

        void incrementTo(GcoreTimerHistogram gcoreTimerHistogram);

        long reset();
    }

    public interface GcoreTimerHistogram {

        long getCount(long j);

        String getName();

        BoundTimer newTimer();

        public interface BoundTimer {
            long getMilliseconds();

            void incrementTo();

            void reset();
        }
    }
}
