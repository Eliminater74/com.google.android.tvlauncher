package com.google.android.tvlauncher.settings;

import android.app.Fragment;
import android.os.Bundle;
import androidx.leanback.preference.LeanbackSettingsFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceScreen;
import com.google.android.tvlauncher.analytics.LoggingActivity;
import com.google.android.tvlauncher.util.Util;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

public class HomeScreenSettingsActivity extends LoggingActivity {
    public HomeScreenSettingsActivity() {
        super("HomeScreenSettings", TvLauncherConstants.HOME_SCREEN_SETTINGS_PAGE);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(16908290, SettingsFragment.newInstance()).commit();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Util.forceLandscapeOrientation(this);
    }

    public static class SettingsFragment extends LeanbackSettingsFragment {
        public static SettingsFragment newInstance() {
            return new SettingsFragment();
        }

        public void onPreferenceStartInitialScreen() {
            startPreferenceFragment(HomeScreenPreferenceFragment.newInstance());
        }

        public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
            Fragment f = Fragment.instantiate(getActivity(), pref.getFragment(), pref.getExtras());
            f.setTargetFragment(caller, 0);
            startPreferenceFragment(f);
            return true;
        }

        public boolean onPreferenceStartScreen(PreferenceFragment preferenceFragment, PreferenceScreen preferenceScreen) {
            return false;
        }
    }
}
