package com.google.android.libraries.clock;

public interface ListenableClock extends Clock {

    void registerTimeResetListener(TimeResetListener timeResetListener);

    void registerTimeTickListener(TimeTickListener timeTickListener);

    void unregisterTimeResetListener(TimeResetListener timeResetListener);

    void unregisterTimeTickListener(TimeTickListener timeTickListener);

    public interface TimeResetListener {
        void onTimeReset();
    }

    public interface TimeTickListener {
        void onTimeTick();
    }
}
