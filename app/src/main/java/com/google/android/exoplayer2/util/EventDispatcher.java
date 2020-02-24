package com.google.android.exoplayer2.util;

import android.os.Handler;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventDispatcher<T> {
    private final CopyOnWriteArrayList<HandlerAndListener<T>> listeners = new CopyOnWriteArrayList<>();

    public interface Event<T> {
        void sendTo(T t);
    }

    public void addListener(Handler handler, T eventListener) {
        Assertions.checkArgument((handler == null || eventListener == null) ? false : true);
        removeListener(eventListener);
        this.listeners.add(new HandlerAndListener(handler, eventListener));
    }

    public void removeListener(T eventListener) {
        Iterator<HandlerAndListener<T>> it = this.listeners.iterator();
        while (it.hasNext()) {
            HandlerAndListener<T> handlerAndListener = it.next();
            if (handlerAndListener.listener == eventListener) {
                handlerAndListener.release();
                this.listeners.remove(handlerAndListener);
            }
        }
    }

    public void dispatch(Event<T> event) {
        Iterator<HandlerAndListener<T>> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().dispatch(event);
        }
    }

    private static final class HandlerAndListener<T> {
        private final Handler handler;
        /* access modifiers changed from: private */
        public final T listener;
        private boolean released;

        public HandlerAndListener(Handler handler2, T eventListener) {
            this.handler = handler2;
            this.listener = eventListener;
        }

        public void release() {
            this.released = true;
        }

        public void dispatch(Event<T> event) {
            this.handler.post(new EventDispatcher$HandlerAndListener$$Lambda$0(this, event));
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$dispatch$0$EventDispatcher$HandlerAndListener(Event event) {
            if (!this.released) {
                event.sendTo(this.listener);
            }
        }
    }
}
