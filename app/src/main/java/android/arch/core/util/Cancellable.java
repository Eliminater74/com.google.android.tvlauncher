package android.arch.core.util;

import android.support.annotation.NonNull;

@Deprecated
public interface Cancellable {
    @Deprecated
    @NonNull
    public static final Cancellable CANCELLED = new Cancellable() {
        public void cancel() {
        }

        public boolean isCancelled() {
            return true;
        }
    };

    @Deprecated
    void cancel();

    @Deprecated
    boolean isCancelled();
}
