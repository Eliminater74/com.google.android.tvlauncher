package com.google.android.tvlauncher.data;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

public abstract class DataLoader<T> extends AsyncTaskLoader<T> {
    private static final String TAG = "DataLoader";
    private ContentObserver mContentObserver;
    T mData;
    private Uri mUri;

    public abstract T loadData();

    DataLoader(Context context, @NonNull Uri contentUri) {
        super(context);
        this.mUri = contentUri;
    }

    public T loadInBackground() {
        return loadData();
    }

    /* access modifiers changed from: protected */
    public void onStartLoading() {
        T t = this.mData;
        if (t != null) {
            deliverResult(t);
        }
        if (this.mContentObserver == null && this.mUri != null) {
            this.mContentObserver = new ContentObserver(new Handler()) {
                public void onChange(boolean selfChange) {
                    DataLoader.this.onContentChanged();
                }

                public void onChange(boolean selfChange, Uri uri) {
                    onChange(selfChange);
                }
            };
            try {
                getContext().getContentResolver().registerContentObserver(this.mUri, true, this.mContentObserver);
            } catch (SecurityException e) {
                String valueOf = String.valueOf(this.mUri);
                String message = e.getMessage();
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 55 + String.valueOf(message).length());
                sb.append("Failed to register content observer for URI: ");
                sb.append(valueOf);
                sb.append(".\nReason: ");
                sb.append(message);
                Log.i(TAG, sb.toString());
                this.mContentObserver = null;
            }
        }
        if (takeContentChanged() || this.mData == null) {
            forceLoad();
        }
    }

    /* access modifiers changed from: protected */
    public void onStopLoading() {
        cancelLoad();
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        onStopLoading();
        this.mData = null;
        if (this.mContentObserver != null) {
            getContext().getContentResolver().unregisterContentObserver(this.mContentObserver);
            this.mContentObserver = null;
        }
    }
}
