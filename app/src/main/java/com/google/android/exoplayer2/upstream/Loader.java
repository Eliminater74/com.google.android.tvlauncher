package com.google.android.exoplayer2.upstream;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutorService;

public final class Loader implements LoaderErrorThrower {
    private static final int ACTION_TYPE_DONT_RETRY = 2;
    private static final int ACTION_TYPE_DONT_RETRY_FATAL = 3;
    private static final int ACTION_TYPE_RETRY = 0;
    private static final int ACTION_TYPE_RETRY_AND_RESET_ERROR_COUNT = 1;
    public static final LoadErrorAction DONT_RETRY = new LoadErrorAction(2, C0841C.TIME_UNSET);
    public static final LoadErrorAction DONT_RETRY_FATAL = new LoadErrorAction(3, C0841C.TIME_UNSET);
    public static final LoadErrorAction RETRY = createRetryAction(false, C0841C.TIME_UNSET);
    public static final LoadErrorAction RETRY_RESET_ERROR_COUNT = createRetryAction(true, C0841C.TIME_UNSET);
    /* access modifiers changed from: private */
    public LoadTask<? extends Loadable> currentTask;
    /* access modifiers changed from: private */
    public final ExecutorService downloadExecutorService;
    /* access modifiers changed from: private */
    public IOException fatalError;

    public interface Callback<T extends Loadable> {
        void onLoadCanceled(Loadable loadable, long j, long j2, boolean z);

        void onLoadCompleted(Loadable loadable, long j, long j2);

        LoadErrorAction onLoadError(Loadable loadable, long j, long j2, IOException iOException, int i);
    }

    public interface Loadable {
        void cancelLoad();

        void load() throws IOException, InterruptedException;
    }

    public interface ReleaseCallback {
        void onLoaderReleased();
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface RetryActionType {
    }

    public static final class UnexpectedLoaderException extends IOException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public UnexpectedLoaderException(java.lang.Throwable r5) {
            /*
                r4 = this;
                java.lang.Class r0 = r5.getClass()
                java.lang.String r0 = r0.getSimpleName()
                java.lang.String r1 = r5.getMessage()
                java.lang.String r2 = java.lang.String.valueOf(r0)
                int r2 = r2.length()
                int r2 = r2 + 13
                java.lang.String r3 = java.lang.String.valueOf(r1)
                int r3 = r3.length()
                int r2 = r2 + r3
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>(r2)
                java.lang.String r2 = "Unexpected "
                r3.append(r2)
                r3.append(r0)
                java.lang.String r0 = ": "
                r3.append(r0)
                r3.append(r1)
                java.lang.String r0 = r3.toString()
                r4.<init>(r0, r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.Loader.UnexpectedLoaderException.<init>(java.lang.Throwable):void");
        }
    }

    public static final class LoadErrorAction {
        /* access modifiers changed from: private */
        public final long retryDelayMillis;
        /* access modifiers changed from: private */
        public final int type;

        private LoadErrorAction(int type2, long retryDelayMillis2) {
            this.type = type2;
            this.retryDelayMillis = retryDelayMillis2;
        }

        public boolean isRetry() {
            int i = this.type;
            return i == 0 || i == 1;
        }
    }

    public Loader(String threadName) {
        this.downloadExecutorService = Util.newSingleThreadExecutor(threadName);
    }

    public static LoadErrorAction createRetryAction(boolean resetErrorCount, long retryDelayMillis) {
        return new LoadErrorAction(resetErrorCount, retryDelayMillis);
    }

    public <T extends Loadable> long startLoading(T loadable, Callback<T> callback, int defaultMinRetryCount) {
        Looper looper = Looper.myLooper();
        Assertions.checkState(looper != null);
        this.fatalError = null;
        long startTimeMs = SystemClock.elapsedRealtime();
        new LoadTask(looper, loadable, callback, defaultMinRetryCount, startTimeMs).start(0);
        return startTimeMs;
    }

    public boolean isLoading() {
        return this.currentTask != null;
    }

    public void cancelLoading() {
        this.currentTask.cancel(false);
    }

    public void release() {
        release(null);
    }

    public void release(@Nullable ReleaseCallback callback) {
        LoadTask<? extends Loadable> loadTask = this.currentTask;
        if (loadTask != null) {
            loadTask.cancel(true);
        }
        if (callback != null) {
            this.downloadExecutorService.execute(new ReleaseTask(callback));
        }
        this.downloadExecutorService.shutdown();
    }

    public void maybeThrowError() throws IOException {
        maybeThrowError(Integer.MIN_VALUE);
    }

    public void maybeThrowError(int minRetryCount) throws IOException {
        IOException iOException = this.fatalError;
        if (iOException == null) {
            LoadTask<? extends Loadable> loadTask = this.currentTask;
            if (loadTask != null) {
                loadTask.maybeThrowError(minRetryCount == Integer.MIN_VALUE ? loadTask.defaultMinRetryCount : minRetryCount);
                return;
            }
            return;
        }
        throw iOException;
    }

    @SuppressLint({"HandlerLeak"})
    private final class LoadTask<T extends Loadable> extends Handler implements Runnable {
        private static final int MSG_CANCEL = 1;
        private static final int MSG_END_OF_SOURCE = 2;
        private static final int MSG_FATAL_ERROR = 4;
        private static final int MSG_IO_EXCEPTION = 3;
        private static final int MSG_START = 0;
        private static final String TAG = "LoadTask";
        @Nullable
        private Callback<T> callback;
        private volatile boolean canceled;
        private IOException currentError;
        public final int defaultMinRetryCount;
        private int errorCount;
        private volatile Thread executorThread;
        private final T loadable;
        private volatile boolean released;
        private final long startTimeMs;

        public LoadTask(Looper looper, T loadable2, Callback<T> callback2, int defaultMinRetryCount2, long startTimeMs2) {
            super(looper);
            this.loadable = loadable2;
            this.callback = callback2;
            this.defaultMinRetryCount = defaultMinRetryCount2;
            this.startTimeMs = startTimeMs2;
        }

        public void maybeThrowError(int minRetryCount) throws IOException {
            IOException iOException = this.currentError;
            if (iOException != null && this.errorCount > minRetryCount) {
                throw iOException;
            }
        }

        public void start(long delayMillis) {
            Assertions.checkState(Loader.this.currentTask == null);
            LoadTask unused = Loader.this.currentTask = this;
            if (delayMillis > 0) {
                sendEmptyMessageDelayed(0, delayMillis);
            } else {
                execute();
            }
        }

        public void cancel(boolean released2) {
            this.released = released2;
            this.currentError = null;
            if (hasMessages(0)) {
                removeMessages(0);
                if (!released2) {
                    sendEmptyMessage(1);
                }
            } else {
                this.canceled = true;
                this.loadable.cancelLoad();
                if (this.executorThread != null) {
                    this.executorThread.interrupt();
                }
            }
            if (released2) {
                finish();
                long nowMs = SystemClock.elapsedRealtime();
                this.callback.onLoadCanceled(this.loadable, nowMs, nowMs - this.startTimeMs, true);
                this.callback = null;
            }
        }

        public void run() {
            try {
                this.executorThread = Thread.currentThread();
                if (!this.canceled) {
                    String valueOf = String.valueOf(this.loadable.getClass().getSimpleName());
                    TraceUtil.beginSection(valueOf.length() != 0 ? "load:".concat(valueOf) : new String("load:"));
                    this.loadable.load();
                    TraceUtil.endSection();
                }
                if (!this.released) {
                    sendEmptyMessage(2);
                }
            } catch (IOException e) {
                if (!this.released) {
                    obtainMessage(3, e).sendToTarget();
                }
            } catch (InterruptedException e2) {
                Assertions.checkState(this.canceled);
                if (!this.released) {
                    sendEmptyMessage(2);
                }
            } catch (Exception e3) {
                Log.m27e(TAG, "Unexpected exception loading stream", e3);
                if (!this.released) {
                    obtainMessage(3, new UnexpectedLoaderException(e3)).sendToTarget();
                }
            } catch (OutOfMemoryError e4) {
                Log.m27e(TAG, "OutOfMemory error loading stream", e4);
                if (!this.released) {
                    obtainMessage(3, new UnexpectedLoaderException(e4)).sendToTarget();
                }
            } catch (Error e5) {
                Log.m27e(TAG, "Unexpected error loading stream", e5);
                if (!this.released) {
                    obtainMessage(4, e5).sendToTarget();
                }
                throw e5;
            } catch (Throwable th) {
                TraceUtil.endSection();
                throw th;
            }
        }

        public void handleMessage(Message msg) {
            long j;
            Message message = msg;
            if (!this.released) {
                if (message.what == 0) {
                    execute();
                } else if (message.what != 4) {
                    finish();
                    long nowMs = SystemClock.elapsedRealtime();
                    long durationMs = nowMs - this.startTimeMs;
                    if (this.canceled) {
                        this.callback.onLoadCanceled(this.loadable, nowMs, durationMs, false);
                        return;
                    }
                    int i = message.what;
                    if (i == 1) {
                        this.callback.onLoadCanceled(this.loadable, nowMs, durationMs, false);
                    } else if (i == 2) {
                        try {
                            this.callback.onLoadCompleted(this.loadable, nowMs, durationMs);
                        } catch (RuntimeException e) {
                            Log.m27e(TAG, "Unexpected exception handling load completed", e);
                            IOException unused = Loader.this.fatalError = new UnexpectedLoaderException(e);
                        }
                    } else if (i == 3) {
                        this.currentError = (IOException) message.obj;
                        this.errorCount++;
                        LoadErrorAction action = this.callback.onLoadError(this.loadable, nowMs, durationMs, this.currentError, this.errorCount);
                        if (action.type == 3) {
                            IOException unused2 = Loader.this.fatalError = this.currentError;
                        } else if (action.type != 2) {
                            if (action.type == 1) {
                                this.errorCount = 1;
                            }
                            if (action.retryDelayMillis != C0841C.TIME_UNSET) {
                                j = action.retryDelayMillis;
                            } else {
                                j = getRetryDelayMillis();
                            }
                            start(j);
                        }
                    }
                } else {
                    throw ((Error) message.obj);
                }
            }
        }

        private void execute() {
            this.currentError = null;
            Loader.this.downloadExecutorService.execute(Loader.this.currentTask);
        }

        private void finish() {
            LoadTask unused = Loader.this.currentTask = null;
        }

        private long getRetryDelayMillis() {
            return (long) Math.min((this.errorCount - 1) * 1000, 5000);
        }
    }

    private static final class ReleaseTask implements Runnable {
        private final ReleaseCallback callback;

        public ReleaseTask(ReleaseCallback callback2) {
            this.callback = callback2;
        }

        public void run() {
            this.callback.onLoaderReleased();
        }
    }
}
