package android.support.p004v7.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: android.support.v7.util.MessageThreadUtil */
class MessageThreadUtil<T> implements ThreadUtil<T> {
    MessageThreadUtil() {
    }

    public ThreadUtil.MainThreadCallback<T> getMainThreadProxy(final ThreadUtil.MainThreadCallback<T> callback) {
        return new ThreadUtil.MainThreadCallback<T>() {
            static final int ADD_TILE = 2;
            static final int REMOVE_TILE = 3;
            static final int UPDATE_ITEM_COUNT = 1;
            final MessageQueue mQueue = new MessageQueue();
            private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
            private Runnable mMainThreadRunnable = new Runnable() {
                public void run() {
                    SyncQueueItem msg = C02511.this.mQueue.next();
                    while (msg != null) {
                        int i = msg.what;
                        if (i == 1) {
                            callback.updateItemCount(msg.arg1, msg.arg2);
                        } else if (i == 2) {
                            callback.addTile(msg.arg1, (TileList.Tile) msg.data);
                        } else if (i != 3) {
                            Log.e("ThreadUtil", "Unsupported message, what=" + msg.what);
                        } else {
                            callback.removeTile(msg.arg1, msg.arg2);
                        }
                        msg = C02511.this.mQueue.next();
                    }
                }
            };

            public void updateItemCount(int generation, int itemCount) {
                sendMessage(SyncQueueItem.obtainMessage(1, generation, itemCount));
            }

            public void addTile(int generation, TileList.Tile<T> tile) {
                sendMessage(SyncQueueItem.obtainMessage(2, generation, tile));
            }

            public void removeTile(int generation, int position) {
                sendMessage(SyncQueueItem.obtainMessage(3, generation, position));
            }

            private void sendMessage(SyncQueueItem msg) {
                this.mQueue.sendMessage(msg);
                this.mMainThreadHandler.post(this.mMainThreadRunnable);
            }
        };
    }

    public ThreadUtil.BackgroundCallback<T> getBackgroundProxy(final ThreadUtil.BackgroundCallback<T> callback) {
        return new ThreadUtil.BackgroundCallback<T>() {
            static final int LOAD_TILE = 3;
            static final int RECYCLE_TILE = 4;
            static final int REFRESH = 1;
            static final int UPDATE_RANGE = 2;
            final MessageQueue mQueue = new MessageQueue();
            private final Executor mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
            AtomicBoolean mBackgroundRunning = new AtomicBoolean(false);
            private Runnable mBackgroundRunnable = new Runnable() {
                public void run() {
                    while (true) {
                        SyncQueueItem msg = C02532.this.mQueue.next();
                        if (msg == null) {
                            C02532.this.mBackgroundRunning.set(false);
                            return;
                        }
                        int i = msg.what;
                        if (i == 1) {
                            C02532.this.mQueue.removeMessages(1);
                            callback.refresh(msg.arg1);
                        } else if (i == 2) {
                            C02532.this.mQueue.removeMessages(2);
                            C02532.this.mQueue.removeMessages(3);
                            callback.updateRange(msg.arg1, msg.arg2, msg.arg3, msg.arg4, msg.arg5);
                        } else if (i == 3) {
                            callback.loadTile(msg.arg1, msg.arg2);
                        } else if (i != 4) {
                            Log.e("ThreadUtil", "Unsupported message, what=" + msg.what);
                        } else {
                            callback.recycleTile((TileList.Tile) msg.data);
                        }
                    }
                }
            };

            public void refresh(int generation) {
                sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(1, generation, (Object) null));
            }

            public void updateRange(int rangeStart, int rangeEnd, int extRangeStart, int extRangeEnd, int scrollHint) {
                sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(2, rangeStart, rangeEnd, extRangeStart, extRangeEnd, scrollHint, null));
            }

            public void loadTile(int position, int scrollHint) {
                sendMessage(SyncQueueItem.obtainMessage(3, position, scrollHint));
            }

            public void recycleTile(TileList.Tile<T> tile) {
                sendMessage(SyncQueueItem.obtainMessage(4, 0, tile));
            }

            private void sendMessage(SyncQueueItem msg) {
                this.mQueue.sendMessage(msg);
                maybeExecuteBackgroundRunnable();
            }

            private void sendMessageAtFrontOfQueue(SyncQueueItem msg) {
                this.mQueue.sendMessageAtFrontOfQueue(msg);
                maybeExecuteBackgroundRunnable();
            }

            private void maybeExecuteBackgroundRunnable() {
                if (this.mBackgroundRunning.compareAndSet(false, true)) {
                    this.mExecutor.execute(this.mBackgroundRunnable);
                }
            }
        };
    }

    /* renamed from: android.support.v7.util.MessageThreadUtil$SyncQueueItem */
    static class SyncQueueItem {
        private static final Object sPoolLock = new Object();
        private static SyncQueueItem sPool;
        public int arg1;
        public int arg2;
        public int arg3;
        public int arg4;
        public int arg5;
        public Object data;
        public int what;
        SyncQueueItem next;

        SyncQueueItem() {
        }

        static SyncQueueItem obtainMessage(int what2, int arg12, int arg22, int arg32, int arg42, int arg52, Object data2) {
            SyncQueueItem item;
            synchronized (sPoolLock) {
                if (sPool == null) {
                    item = new SyncQueueItem();
                } else {
                    item = sPool;
                    sPool = sPool.next;
                    item.next = null;
                }
                item.what = what2;
                item.arg1 = arg12;
                item.arg2 = arg22;
                item.arg3 = arg32;
                item.arg4 = arg42;
                item.arg5 = arg52;
                item.data = data2;
            }
            return item;
        }

        static SyncQueueItem obtainMessage(int what2, int arg12, int arg22) {
            return obtainMessage(what2, arg12, arg22, 0, 0, 0, null);
        }

        static SyncQueueItem obtainMessage(int what2, int arg12, Object data2) {
            return obtainMessage(what2, arg12, 0, 0, 0, 0, data2);
        }

        /* access modifiers changed from: package-private */
        public void recycle() {
            this.next = null;
            this.arg5 = 0;
            this.arg4 = 0;
            this.arg3 = 0;
            this.arg2 = 0;
            this.arg1 = 0;
            this.what = 0;
            this.data = null;
            synchronized (sPoolLock) {
                if (sPool != null) {
                    this.next = sPool;
                }
                sPool = this;
            }
        }
    }

    /* renamed from: android.support.v7.util.MessageThreadUtil$MessageQueue */
    static class MessageQueue {
        private SyncQueueItem mRoot;

        MessageQueue() {
        }

        /* access modifiers changed from: package-private */
        public synchronized SyncQueueItem next() {
            if (this.mRoot == null) {
                return null;
            }
            SyncQueueItem next = this.mRoot;
            this.mRoot = this.mRoot.next;
            return next;
        }

        /* access modifiers changed from: package-private */
        public synchronized void sendMessageAtFrontOfQueue(SyncQueueItem item) {
            item.next = this.mRoot;
            this.mRoot = item;
        }

        /* access modifiers changed from: package-private */
        public synchronized void sendMessage(SyncQueueItem item) {
            if (this.mRoot == null) {
                this.mRoot = item;
                return;
            }
            SyncQueueItem last = this.mRoot;
            while (last.next != null) {
                last = last.next;
            }
            last.next = item;
        }

        /* access modifiers changed from: package-private */
        public synchronized void removeMessages(int what) {
            while (this.mRoot != null && this.mRoot.what == what) {
                SyncQueueItem item = this.mRoot;
                this.mRoot = this.mRoot.next;
                item.recycle();
            }
            if (this.mRoot != null) {
                SyncQueueItem prev = this.mRoot;
                SyncQueueItem item2 = prev.next;
                while (item2 != null) {
                    SyncQueueItem next = item2.next;
                    if (item2.what == what) {
                        prev.next = next;
                        item2.recycle();
                    } else {
                        prev = item2;
                    }
                    item2 = next;
                }
            }
        }
    }
}
