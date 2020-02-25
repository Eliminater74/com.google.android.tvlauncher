package androidx.activity;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class OnBackPressedCallback {
    private ArrayList<Cancellable> mCancellables = new ArrayList<>();
    private boolean mEnabled;

    public OnBackPressedCallback(boolean enabled) {
        this.mEnabled = enabled;
    }

    @MainThread
    public abstract void handleOnBackPressed();

    @MainThread
    public boolean isEnabled() {
        return this.mEnabled;
    }

    @MainThread
    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    @MainThread
    public void remove() {
        Iterator<Cancellable> it = this.mCancellables.iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public void addCancellable(@NonNull Cancellable cancellable) {
        this.mCancellables.add(cancellable);
    }

    /* access modifiers changed from: package-private */
    public void removeCancellable(@NonNull Cancellable cancellable) {
        this.mCancellables.remove(cancellable);
    }
}
