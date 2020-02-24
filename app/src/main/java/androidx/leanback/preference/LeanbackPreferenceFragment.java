package androidx.leanback.preference;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@Deprecated
public abstract class LeanbackPreferenceFragment extends BaseLeanbackPreferenceFragment {
    public LeanbackPreferenceFragment() {
        if (Build.VERSION.SDK_INT >= 21) {
            LeanbackPreferenceFragmentTransitionHelperApi21.addTransitions(this);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View innerView = super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(innerView.getContext()).inflate(C0572R.layout.leanback_preference_fragment, container, false);
        ((ViewGroup) view.findViewById(C0572R.C0574id.main_frame)).addView(innerView);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(getPreferenceScreen().getTitle());
    }

    public void setTitle(CharSequence title) {
        TextView decorTitle;
        View view = getView();
        if (view == null) {
            decorTitle = null;
        } else {
            decorTitle = (TextView) view.findViewById(C0572R.C0574id.decor_title);
        }
        if (decorTitle != null) {
            decorTitle.setText(title);
        }
    }
}
