package com.google.android.tvlauncher.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.p001v4.view.GravityCompat;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import androidx.leanback.preference.LeanbackSettingsFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceScreen;
import com.google.android.tvlauncher.analytics.LoggingActivity;
import com.google.android.tvlauncher.util.Util;

public class FavoriteLaunchItemsActivity extends LoggingActivity {
    private static final String TAG_FAVORITES_FRAGMENT = "tag_favorites_fragment";

    public FavoriteLaunchItemsActivity() {
        super("FavoriteLaunchItems");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(16908290, FavoriteLaunchItemsFragment.newInstance(), TAG_FAVORITES_FRAGMENT).commit();
            TransitionManager.go(new Scene((ViewGroup) findViewById(16908290)), new Slide(GravityCompat.END));
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Util.forceLandscapeOrientation(this);
    }

    public static class FavoriteLaunchItemsFragment extends LeanbackSettingsFragment {
        public static FavoriteLaunchItemsFragment newInstance() {
            return new FavoriteLaunchItemsFragment();
        }

        public void onPreferenceStartInitialScreen() {
            startPreferenceFragment(FavoriteLaunchItemsSelectionFragment.newInstance());
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

    public void finish() {
        final Fragment fragment = getFragmentManager().findFragmentByTag(TAG_FAVORITES_FRAGMENT);
        if (fragment == null || !fragment.isResumed()) {
            super.finish();
            return;
        }
        Scene scene = new Scene((ViewGroup) findViewById(16908290));
        scene.setEnterAction(new Runnable() {
            public void run() {
                FavoriteLaunchItemsActivity.this.getFragmentManager().beginTransaction().remove(fragment).commitNow();
            }
        });
        Slide slide = new Slide(GravityCompat.END);
        slide.addListener(new Transition.TransitionListener() {
            public void onTransitionStart(Transition transition) {
                FavoriteLaunchItemsActivity.this.getWindow().setDimAmount(0.0f);
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                FavoriteLaunchItemsActivity.super.finish();
            }

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }
        });
        TransitionManager.go(scene, slide);
    }
}
