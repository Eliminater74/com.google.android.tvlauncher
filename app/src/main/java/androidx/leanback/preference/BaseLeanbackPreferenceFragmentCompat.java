package androidx.leanback.preference;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.app.Fragment;
import android.support.p004v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.leanback.widget.VerticalGridView;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceRecyclerViewAccessibilityDelegate;

public abstract class BaseLeanbackPreferenceFragmentCompat extends PreferenceFragmentCompat {
    private Context mThemedContext;

    @Nullable
    public Context getContext() {
        if (this.mThemedContext == null && getActivity() != null) {
            TypedValue tv = new TypedValue();
            getActivity().getTheme().resolveAttribute(C0572R.attr.preferenceTheme, tv, true);
            int theme = tv.resourceId;
            if (theme == 0) {
                theme = C0572R.style.PreferenceThemeOverlayLeanback;
            }
            this.mThemedContext = new ContextThemeWrapper(super.getContext(), theme);
        }
        return this.mThemedContext;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public RecyclerView onCreateRecyclerView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        VerticalGridView verticalGridView = (VerticalGridView) inflater.inflate(C0572R.layout.leanback_preferences_list, parent, false);
        verticalGridView.setWindowAlignment(3);
        verticalGridView.setFocusScrollStrategy(0);
        verticalGridView.setAccessibilityDelegateCompat(new PreferenceRecyclerViewAccessibilityDelegate(verticalGridView));
        return verticalGridView;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Fragment getCallbackFragment() {
        return getParentFragment();
    }
}
