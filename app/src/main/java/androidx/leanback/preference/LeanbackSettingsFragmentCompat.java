package androidx.leanback.preference;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public abstract class LeanbackSettingsFragmentCompat extends Fragment implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback, PreferenceFragmentCompat.OnPreferenceStartScreenCallback, PreferenceFragmentCompat.OnPreferenceDisplayDialogCallback {
    private static final String PREFERENCE_FRAGMENT_TAG = "androidx.leanback.preference.LeanbackSettingsFragment.PREFERENCE_FRAGMENT";
    private final RootViewOnKeyListener mRootViewOnKeyListener = new RootViewOnKeyListener();

    public abstract void onPreferenceStartInitialScreen();

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C0572R.layout.leanback_settings_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            onPreferenceStartInitialScreen();
        }
    }

    public void onResume() {
        super.onResume();
        LeanbackSettingsRootView rootView = (LeanbackSettingsRootView) getView();
        if (rootView != null) {
            rootView.setOnBackKeyListener(this.mRootViewOnKeyListener);
        }
    }

    public void onPause() {
        super.onPause();
        LeanbackSettingsRootView rootView = (LeanbackSettingsRootView) getView();
        if (rootView != null) {
            rootView.setOnBackKeyListener(null);
        }
    }

    public boolean onPreferenceDisplayDialog(@NonNull PreferenceFragmentCompat caller, Preference pref) {
        if (caller == null) {
            throw new IllegalArgumentException("Cannot display dialog for preference " + pref + ", Caller must not be null!");
        } else if (pref instanceof ListPreference) {
            Fragment f = LeanbackListPreferenceDialogFragmentCompat.newInstanceSingle(((ListPreference) pref).getKey());
            f.setTargetFragment(caller, 0);
            startPreferenceFragment(f);
            return true;
        } else if (pref instanceof MultiSelectListPreference) {
            Fragment f2 = LeanbackListPreferenceDialogFragmentCompat.newInstanceMulti(((MultiSelectListPreference) pref).getKey());
            f2.setTargetFragment(caller, 0);
            startPreferenceFragment(f2);
            return true;
        } else if (!(pref instanceof EditTextPreference)) {
            return false;
        } else {
            Fragment f3 = LeanbackEditTextPreferenceDialogFragmentCompat.newInstance(pref.getKey());
            f3.setTargetFragment(caller, 0);
            startPreferenceFragment(f3);
            return true;
        }
    }

    public void startPreferenceFragment(@NonNull Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (getChildFragmentManager().findFragmentByTag(PREFERENCE_FRAGMENT_TAG) != null) {
            transaction.addToBackStack(null).replace(C0572R.C0574id.settings_preference_fragment_container, fragment, PREFERENCE_FRAGMENT_TAG);
        } else {
            transaction.add(C0572R.C0574id.settings_preference_fragment_container, fragment, PREFERENCE_FRAGMENT_TAG);
        }
        transaction.commit();
    }

    public void startImmersiveFragment(@NonNull Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment preferenceFragment = getChildFragmentManager().findFragmentByTag(PREFERENCE_FRAGMENT_TAG);
        if (preferenceFragment != null && !preferenceFragment.isHidden()) {
            transaction.remove(preferenceFragment);
        }
        transaction.add(C0572R.C0574id.settings_dialog_container, fragment).addToBackStack(null).commit();
    }

    private class RootViewOnKeyListener implements View.OnKeyListener {
        RootViewOnKeyListener() {
        }

        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == 4) {
                return LeanbackSettingsFragmentCompat.this.getChildFragmentManager().popBackStackImmediate();
            }
            return false;
        }
    }
}
