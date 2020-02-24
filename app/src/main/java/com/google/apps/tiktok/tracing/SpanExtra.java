package com.google.apps.tiktok.tracing;

import com.google.common.base.Preconditions;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
public final class SpanExtra<T> {
    private static final SpanExtra<?> NO_TRACE_OBJECT = new SpanExtra<>(State.NO_TRACE, null, true);
    private static final SpanExtra<?> UNSET_OBJECT = new SpanExtra<>(State.UNSET, null, true);
    private final State state;
    private boolean stateObserved = false;
    private final T value;

    public enum State {
        PRESENT,
        NO_TRACE,
        UNSET
    }

    static <V> SpanExtra<V> create(V value2) {
        return new SpanExtra<>(State.PRESENT, Preconditions.checkNotNull(value2), false);
    }

    /* renamed from: com.google.apps.tiktok.tracing.SpanExtra$1 */
    static /* synthetic */ class C14721 {
        static final /* synthetic */ int[] $SwitchMap$com$google$apps$tiktok$tracing$SpanExtra$State = new int[State.values().length];

        static {
            try {
                $SwitchMap$com$google$apps$tiktok$tracing$SpanExtra$State[State.UNSET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$apps$tiktok$tracing$SpanExtra$State[State.NO_TRACE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static <V> SpanExtra<V> create(State state2) {
        int i = C14721.$SwitchMap$com$google$apps$tiktok$tracing$SpanExtra$State[state2.ordinal()];
        if (i == 1) {
            return UNSET_OBJECT;
        }
        if (i == 2) {
            return NO_TRACE_OBJECT;
        }
        throw new IllegalArgumentException();
    }

    private SpanExtra(State state2, T value2, boolean observed) {
        this.state = state2;
        this.value = value2;
        this.stateObserved = observed;
    }

    public State getState() {
        this.stateObserved = true;
        return this.state;
    }

    public T get() {
        if (this.stateObserved && this.state == State.PRESENT) {
            return this.value;
        }
        throw new IllegalStateException("Calling get() without checking if the extra is present is an error");
    }

    public boolean isPresent() {
        this.stateObserved = true;
        if (this.state == State.PRESENT) {
            return true;
        }
        return false;
    }
}
