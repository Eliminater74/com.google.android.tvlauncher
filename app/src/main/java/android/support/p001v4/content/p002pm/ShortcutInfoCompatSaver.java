package android.support.p001v4.content.p002pm;

import android.support.annotation.AnyThread;
import android.support.annotation.RestrictTo;
import android.support.annotation.WorkerThread;

import androidx.concurrent.futures.ResolvableFuture;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v4.content.pm.ShortcutInfoCompatSaver */
public class ShortcutInfoCompatSaver {
    @AnyThread
    public ListenableFuture<Void> addShortcuts(List<ShortcutInfoCompat> list) {
        ResolvableFuture<Void> result = ResolvableFuture.create();
        result.set(null);
        return result;
    }

    @AnyThread
    public ListenableFuture<Void> removeShortcuts(List<String> list) {
        ResolvableFuture<Void> result = ResolvableFuture.create();
        result.set(null);
        return result;
    }

    @AnyThread
    public ListenableFuture<Void> removeAllShortcuts() {
        ResolvableFuture<Void> result = ResolvableFuture.create();
        result.set(null);
        return result;
    }

    @WorkerThread
    public List<ShortcutInfoCompat> getShortcuts() throws Exception {
        return new ArrayList();
    }
}
