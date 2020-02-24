package android.support.p001v4.content;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* renamed from: android.support.v4.content.Loader */
public class Loader<D> {
    private boolean mAbandoned = false;
    private boolean mContentChanged = false;
    private Context mContext;
    private int mId;
    private OnLoadCompleteListener<D> mListener;
    private OnLoadCanceledListener<D> mOnLoadCanceledListener;
    private boolean mProcessingChange = false;
    private boolean mReset = true;
    private boolean mStarted = false;

    /* renamed from: android.support.v4.content.Loader$OnLoadCanceledListener */
    public interface OnLoadCanceledListener<D> {
        void onLoadCanceled(@NonNull Loader<D> loader);
    }

    /* renamed from: android.support.v4.content.Loader$OnLoadCompleteListener */
    public interface OnLoadCompleteListener<D> {
        void onLoadComplete(@NonNull Loader<D> loader, @Nullable D d);
    }

    /* renamed from: android.support.v4.content.Loader$ForceLoadContentObserver */
    public final class ForceLoadContentObserver extends ContentObserver {
        public ForceLoadContentObserver() {
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean selfChange) {
            Loader.this.onContentChanged();
        }
    }

    public Loader(@NonNull Context context) {
        this.mContext = context.getApplicationContext();
    }

    @MainThread
    public void deliverResult(@Nullable D data) {
        OnLoadCompleteListener<D> onLoadCompleteListener = this.mListener;
        if (onLoadCompleteListener != null) {
            onLoadCompleteListener.onLoadComplete(this, data);
        }
    }

    @MainThread
    public void deliverCancellation() {
        OnLoadCanceledListener<D> onLoadCanceledListener = this.mOnLoadCanceledListener;
        if (onLoadCanceledListener != null) {
            onLoadCanceledListener.onLoadCanceled(this);
        }
    }

    @NonNull
    public Context getContext() {
        return this.mContext;
    }

    public int getId() {
        return this.mId;
    }

    @MainThread
    public void registerListener(int id, @NonNull OnLoadCompleteListener<D> listener) {
        if (this.mListener == null) {
            this.mListener = listener;
            this.mId = id;
            return;
        }
        throw new IllegalStateException("There is already a listener registered");
    }

    @MainThread
    public void unregisterListener(@NonNull OnLoadCompleteListener<D> listener) {
        OnLoadCompleteListener<D> onLoadCompleteListener = this.mListener;
        if (onLoadCompleteListener == null) {
            throw new IllegalStateException("No listener register");
        } else if (onLoadCompleteListener == listener) {
            this.mListener = null;
        } else {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
    }

    @MainThread
    public void registerOnLoadCanceledListener(@NonNull OnLoadCanceledListener<D> listener) {
        if (this.mOnLoadCanceledListener == null) {
            this.mOnLoadCanceledListener = listener;
            return;
        }
        throw new IllegalStateException("There is already a listener registered");
    }

    @MainThread
    public void unregisterOnLoadCanceledListener(@NonNull OnLoadCanceledListener<D> listener) {
        OnLoadCanceledListener<D> onLoadCanceledListener = this.mOnLoadCanceledListener;
        if (onLoadCanceledListener == null) {
            throw new IllegalStateException("No listener register");
        } else if (onLoadCanceledListener == listener) {
            this.mOnLoadCanceledListener = null;
        } else {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
    }

    public boolean isStarted() {
        return this.mStarted;
    }

    public boolean isAbandoned() {
        return this.mAbandoned;
    }

    public boolean isReset() {
        return this.mReset;
    }

    @MainThread
    public final void startLoading() {
        this.mStarted = true;
        this.mReset = false;
        this.mAbandoned = false;
        onStartLoading();
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void onStartLoading() {
    }

    @MainThread
    public boolean cancelLoad() {
        return onCancelLoad();
    }

    /* access modifiers changed from: protected */
    @MainThread
    public boolean onCancelLoad() {
        return false;
    }

    @MainThread
    public void forceLoad() {
        onForceLoad();
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void onForceLoad() {
    }

    @MainThread
    public void stopLoading() {
        this.mStarted = false;
        onStopLoading();
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void onStopLoading() {
    }

    @MainThread
    public void abandon() {
        this.mAbandoned = true;
        onAbandon();
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void onAbandon() {
    }

    @MainThread
    public void reset() {
        onReset();
        this.mReset = true;
        this.mStarted = false;
        this.mAbandoned = false;
        this.mContentChanged = false;
        this.mProcessingChange = false;
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void onReset() {
    }

    public boolean takeContentChanged() {
        boolean res = this.mContentChanged;
        this.mContentChanged = false;
        this.mProcessingChange |= res;
        return res;
    }

    public void commitContentChanged() {
        this.mProcessingChange = false;
    }

    public void rollbackContentChanged() {
        if (this.mProcessingChange) {
            onContentChanged();
        }
    }

    @MainThread
    public void onContentChanged() {
        if (this.mStarted) {
            forceLoad();
        } else {
            this.mContentChanged = true;
        }
    }

    @NonNull
    public String dataToString(@Nullable D data) {
        StringBuilder sb = new StringBuilder(64);
        if (data == null) {
            sb.append("null");
        } else {
            Class cls = data.getClass();
            sb.append(cls.getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(cls)));
            sb.append("}");
        }
        return sb.toString();
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        Class cls = getClass();
        sb.append(cls.getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(cls)));
        sb.append(" id=");
        sb.append(this.mId);
        sb.append("}");
        return sb.toString();
    }

    @Deprecated
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        writer.print(prefix);
        writer.print("mId=");
        writer.print(this.mId);
        writer.print(" mListener=");
        writer.println(this.mListener);
        if (this.mStarted || this.mContentChanged || this.mProcessingChange) {
            writer.print(prefix);
            writer.print("mStarted=");
            writer.print(this.mStarted);
            writer.print(" mContentChanged=");
            writer.print(this.mContentChanged);
            writer.print(" mProcessingChange=");
            writer.println(this.mProcessingChange);
        }
        if (this.mAbandoned || this.mReset) {
            writer.print(prefix);
            writer.print("mAbandoned=");
            writer.print(this.mAbandoned);
            writer.print(" mReset=");
            writer.println(this.mReset);
        }
    }
}
