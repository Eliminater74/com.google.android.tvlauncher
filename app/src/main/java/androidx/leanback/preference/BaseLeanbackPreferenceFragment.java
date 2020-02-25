package androidx.leanback.preference;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.p004v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.leanback.widget.VerticalGridView;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceRecyclerViewAccessibilityDelegate;

@Deprecated
public abstract class BaseLeanbackPreferenceFragment extends PreferenceFragment {
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
