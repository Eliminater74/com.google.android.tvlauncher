package com.google.android.libraries.clock.impl;

import android.content.Context;

import com.google.android.libraries.clock.ListenableClock;

import javax.annotation.Nullable;

public class SystemListenableClock extends SystemClockImpl implements ListenableClock {
    private final Context mContext;
    @Nullable
    private ListenerManager<ListenableClock.TimeResetListener> mListenManagerTimeReset;
    @Nullable
    private ListenerManager<ListenableClock.TimeTickListener> mListenManagerTimeTick;

    public SystemListenableClock(Context context) {
        this.mContext = context;
    }

    public void registerTimeResetListener(ListenableClock.TimeResetListener listener) {
        if (this.mListenManagerTimeReset == null) {
            this.mListenManagerTimeReset = new ListenerManager<>(this.mContext, "android.intent.action.TIME_SET", new ListenerManager.Dispatcher<ListenableClock.TimeResetListener>(this) {
                public void dispatch(ListenableClock.TimeResetListener listener) {
                    listener.onTimeReset();
                }
            });
        }
        this.mListenManagerTimeReset.registerListener(listener);
    }

    public void unregisterTimeResetListener(ListenableClock.TimeResetListener listener) {
        ListenerManager<ListenableClock.TimeResetListener> listenerManager = this.mListenManagerTimeReset;
        if (listenerManager != null) {
            listenerManager.unRegisterListener(listener);
            if (this.mListenManagerTimeReset.isEmpty()) {
                this.mListenManagerTimeReset = null;
            }
        }
    }

    public void registerTimeTickListener(ListenableClock.TimeTickListener listener) {
        if (this.mListenManagerTimeTick == null) {
            this.mListenManagerTimeTick = new ListenerManager<>(this.mContext, "android.intent.action.TIME_TICK", new ListenerManager.Dispatcher<ListenableClock.TimeTickListener>(this) {
                public void dispatch(ListenableClock.TimeTickListener listener) {
                    listener.onTimeTick();
                }
            });
        }
        this.mListenManagerTimeTick.registerListener(listener);
    }

    public void unregisterTimeTickListener(ListenableClock.TimeTickListener listener) {
        ListenerManager<ListenableClock.TimeTickListener> listenerManager = this.mListenManagerTimeTick;
        if (listenerManager != null) {
            listenerManager.unRegisterListener(listener);
            if (this.mListenManagerTimeTick.isEmpty()) {
                this.mListenManagerTimeTick = null;
            }
        }
    }
}
