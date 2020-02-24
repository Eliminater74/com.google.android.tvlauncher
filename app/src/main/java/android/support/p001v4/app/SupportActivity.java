package android.support.p001v4.app;

import android.app.Activity;
import android.support.annotation.RestrictTo;
import android.support.p001v4.util.SimpleArrayMap;
import android.support.p001v4.view.KeyEventDispatcher;
import android.view.KeyEvent;
import android.view.View;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v4.app.SupportActivity */
/* compiled from: ComponentActivity */
public class SupportActivity extends Activity implements KeyEventDispatcher.Component {
    private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> mExtraDataMap = new SimpleArrayMap<>();

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* renamed from: android.support.v4.app.SupportActivity$ExtraData */
    /* compiled from: ComponentActivity */
    public static class ExtraData {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void putExtraData(ExtraData extraData) {
        this.mExtraDataMap.put(extraData.getClass(), extraData);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public <T extends ExtraData> T getExtraData(Class<T> extraDataClass) {
        return (ExtraData) this.mExtraDataMap.get(extraDataClass);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean superDispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        View decor = getWindow().getDecorView();
        if (decor == null || !KeyEventDispatcher.dispatchBeforeHierarchy(decor, event)) {
            return super.dispatchKeyShortcutEvent(event);
        }
        return true;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        View decor = getWindow().getDecorView();
        if (decor == null || !KeyEventDispatcher.dispatchBeforeHierarchy(decor, event)) {
            return KeyEventDispatcher.dispatchKeyEvent(this, decor, this, event);
        }
        return true;
    }
}
