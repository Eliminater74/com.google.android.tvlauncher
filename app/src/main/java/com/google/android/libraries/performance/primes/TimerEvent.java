package com.google.android.libraries.performance.primes;

import com.google.android.libraries.performance.primes.metriccapture.TimeCapture;
import com.google.android.libraries.stitch.util.Preconditions;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;

public final class TimerEvent {
    static final TimerEvent EMPTY_TIMER = new TimerEvent();
    private long endMs;
    boolean hasTrace;
    private final long startMs;
    private TimerStatus timerStatus;

    /* renamed from: com.google.android.libraries.performance.primes.TimerEvent$1 */
    static /* synthetic */ class C11221 {

        /* renamed from: $SwitchMap$com$google$android$libraries$performance$primes$TimerEvent$TimerStatus */
        static final /* synthetic */ int[] f119xd4658a1c = new int[TimerStatus.values().length];

        static {
            try {
                f119xd4658a1c[TimerStatus.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f119xd4658a1c[TimerStatus.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f119xd4658a1c[TimerStatus.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f119xd4658a1c[TimerStatus.CANCEL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public enum TimerStatus {
        UNKNOWN,
        SUCCESS,
        ERROR,
        CANCEL;

        /* access modifiers changed from: package-private */
        public PrimesTraceOuterClass.EndStatus toProto() {
            int i = C11221.f119xd4658a1c[ordinal()];
            if (i == 1) {
                return PrimesTraceOuterClass.EndStatus.UNKNOWN_STATUS;
            }
            if (i == 2) {
                return PrimesTraceOuterClass.EndStatus.SUCCESS;
            }
            if (i == 3) {
                return PrimesTraceOuterClass.EndStatus.ERROR;
            }
            if (i != 4) {
                return PrimesTraceOuterClass.EndStatus.UNKNOWN_STATUS;
            }
            return PrimesTraceOuterClass.EndStatus.CANCEL;
        }
    }

    public static TimerEvent newTimer() {
        return new TimerEvent();
    }

    TimerEvent() {
        this(TimeCapture.getTime());
    }

    public static long getTime() {
        return TimeCapture.getTime();
    }

    public static boolean isEmpty(TimerEvent event) {
        return event == null || event == EMPTY_TIMER;
    }

    private TimerEvent(long startMs2) {
        this.endMs = -1;
        this.timerStatus = TimerStatus.UNKNOWN;
        this.hasTrace = false;
        this.startMs = startMs2;
    }

    TimerEvent(long startMs2, long endMs2) {
        this.endMs = -1;
        this.timerStatus = TimerStatus.UNKNOWN;
        this.hasTrace = false;
        Preconditions.checkArgument(endMs2 >= startMs2, "End time %s is before start time %s.", Long.valueOf(endMs2), Long.valueOf(startMs2));
        this.startMs = startMs2;
        this.endMs = endMs2;
    }

    public TimerEvent copyStartTime() {
        return new TimerEvent(this.startMs);
    }

    /* access modifiers changed from: package-private */
    public TimerEvent stop() {
        ensureEndTimeSet();
        return this;
    }

    /* access modifiers changed from: package-private */
    public long getDuration() {
        return this.endMs - this.startMs;
    }

    /* access modifiers changed from: package-private */
    public long getStartTimeMs() {
        return this.startMs;
    }

    private void ensureEndTimeSet() {
        this.endMs = TimeCapture.getTime();
    }

    /* access modifiers changed from: package-private */
    public TimerStatus getTimerStatus() {
        return this.timerStatus;
    }

    /* access modifiers changed from: package-private */
    public void setTimerStatus(TimerStatus timerStatus2) {
        this.timerStatus = timerStatus2;
    }
}
