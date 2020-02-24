package androidx.concurrent.futures;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import com.google.common.util.concurrent.ListenableFuture;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class ResolvableFuture<V> extends AbstractResolvableFuture<V> {
    public static <V> ResolvableFuture<V> create() {
        return new ResolvableFuture<>();
    }

    public boolean set(@Nullable V value) {
        return super.set(value);
    }

    public boolean setException(Throwable throwable) {
        return super.setException(throwable);
    }

    public boolean setFuture(ListenableFuture<? extends V> future) {
        return super.setFuture(future);
    }

    private ResolvableFuture() {
    }
}
