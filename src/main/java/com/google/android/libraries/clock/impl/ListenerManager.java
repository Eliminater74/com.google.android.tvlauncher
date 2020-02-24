package com.google.android.libraries.clock.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;

public class ListenerManager<T> {
    private final String mAction;
    private final Context mContext;
    /* access modifiers changed from: private */
    public final Dispatcher<T> mDispatcher;
    /* access modifiers changed from: private */
    public final Set<T> mListeners = Sets.newHashSet();
    private BroadcastReceiver mReceiver = new Receiver();

    public interface Dispatcher<T> {
        void dispatch(T t);
    }

    public ListenerManager(Context context, String action, Dispatcher<T> dispatcher) {
        this.mAction = action;
        this.mContext = context;
        this.mDispatcher = dispatcher;
    }

    public void registerListener(T listener) {
        synchronized (this.mListeners) {
            if (this.mListeners.isEmpty()) {
                IntentFilter filter = new IntentFilter();
                filter.addAction(this.mAction);
                this.mContext.registerReceiver(this.mReceiver, filter);
            }
            this.mListeners.add(listener);
        }
    }

    public void unRegisterListener(T listener) {
        synchronized (this.mListeners) {
            if (this.mListeners.remove(listener) && this.mListeners.isEmpty()) {
                this.mContext.unregisterReceiver(this.mReceiver);
            }
        }
    }

    public boolean isEmpty() {
        return this.mListeners.isEmpty();
    }

    @VisibleForTesting
    public BroadcastReceiver getReceiver() {
        return this.mReceiver;
    }

    class Receiver extends BroadcastReceiver {
        Receiver() {
        }

        public void onReceive(Context context, Intent intent) {
            Set<T> listeners;
            synchronized (ListenerManager.this.mListeners) {
                listeners = ImmutableSet.copyOf((Collection) ListenerManager.this.mListeners);
            }
            for (T listener : listeners) {
                ListenerManager.this.mDispatcher.dispatch(listener);
            }
        }
    }
}
