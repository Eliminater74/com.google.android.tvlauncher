package android.support.p001v4.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/* renamed from: android.support.v4.app.FragmentContainer */
public abstract class FragmentContainer {
    @Nullable
    public abstract View onFindViewById(@IdRes int i);

    public abstract boolean onHasView();

    @Deprecated
    @NonNull
    public Fragment instantiate(@NonNull Context context, @NonNull String className, @Nullable Bundle arguments) {
        return Fragment.instantiate(context, className, arguments);
    }
}
