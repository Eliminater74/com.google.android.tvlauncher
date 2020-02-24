package com.google.android.tvlauncher.util;

import android.content.AsyncTaskLoader;
import android.content.Context;

public abstract class LauncherAsyncTaskLoader<T> extends AsyncTaskLoader<T> {
    private T mResult;

    public LauncherAsyncTaskLoader(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onStartLoading() {
        T t = this.mResult;
        if (t != null) {
            deliverResult(t);
        }
        if (takeContentChanged() || this.mResult == null) {
            forceLoad();
        }
    }

    /* access modifiers changed from: protected */
    public void onStopLoading() {
        cancelLoad();
    }

    public void deliverResult(T data) {
        if (!isReset()) {
            this.mResult = data;
            if (isStarted()) {
                super.deliverResult(data);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        super.onReset();
        onStopLoading();
        this.mResult = null;
    }
}
