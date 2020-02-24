package androidx.preference;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.p001v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import androidx.preference.PreferenceManager;

public final class PreferenceScreen extends PreferenceGroup {
    private boolean mShouldUseGeneratedIds = true;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PreferenceScreen(Context context, AttributeSet attrs) {
        super(context, attrs, TypedArrayUtils.getAttr(context, C0731R.attr.preferenceScreenStyle, 16842891));
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        PreferenceManager.OnNavigateToScreenListener listener;
        if (getIntent() == null && getFragment() == null && getPreferenceCount() != 0 && (listener = getPreferenceManager().getOnNavigateToScreenListener()) != null) {
            listener.onNavigateToScreen(this);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isOnSameScreenAsChildren() {
        return false;
    }

    public boolean shouldUseGeneratedIds() {
        return this.mShouldUseGeneratedIds;
    }

    public void setShouldUseGeneratedIds(boolean shouldUseGeneratedIds) {
        if (!isAttached()) {
            this.mShouldUseGeneratedIds = shouldUseGeneratedIds;
            return;
        }
        throw new IllegalStateException("Cannot change the usage of generated IDs while attached to the preference hierarchy");
    }
}
