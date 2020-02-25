package com.google.android.tvlauncher.inputs;

import android.app.Fragment;
import android.content.Intent;
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

import com.google.android.tvlauncher.BlockForDataLauncherActivity;
import com.google.android.tvlauncher.util.Util;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

public class InputsPanelActivity extends BlockForDataLauncherActivity implements InputsPanelFragment.Callbacks {
    private static final String TAG_INPUTS_FRAGMENT = "inputs_fragment";
    private InputsPanelFragment mInputsPanelFragment;

    public InputsPanelActivity() {
        super("InputsPanel", TvLauncherConstants.INPUTS_PAGE);
    }

    public void onCreateAddContent(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(16908290, AppInputsFragment.newInstance(), TAG_INPUTS_FRAGMENT).commit();
            TransitionManager.go(new Scene((ViewGroup) findViewById(16908290)), new Slide(GravityCompat.END));
        }
    }

    public void onInputClicked(int inputIndex) {
        transitionOut(new InputsPanelActivity$$Lambda$0(this, inputIndex));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onInputClicked$0$InputsPanelActivity(int inputIndex) {
        InputsPanelFragment inputsPanelFragment = this.mInputsPanelFragment;
        if (inputsPanelFragment != null) {
            inputsPanelFragment.launchInputActivity(inputIndex);
        }
        super.finish();
    }

    public void onInputsPanelFragmentAttached(InputsPanelFragment fragment) {
        this.mInputsPanelFragment = fragment;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Util.forceLandscapeOrientation(this);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (!isFinishing()) {
            super.finish();
        }
    }

    public void finish() {
        transitionOut(new InputsPanelActivity$$Lambda$1(this));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$finish$1$InputsPanelActivity() {
        super.finish();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void transitionOut(final Runnable afterTransition) {
        Fragment fragment = getFragmentManager().findFragmentByTag(TAG_INPUTS_FRAGMENT);
        if (fragment == null || !fragment.isResumed()) {
            afterTransition.run();
            return;
        }
        Scene scene = new Scene((ViewGroup) findViewById(16908290));
        scene.setEnterAction(new InputsPanelActivity$$Lambda$2(this, fragment));
        Slide slide = new Slide(GravityCompat.END);
        slide.addListener(new Transition.TransitionListener() {
            public void onTransitionStart(Transition transition) {
                InputsPanelActivity.this.getWindow().setDimAmount(0.0f);
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                afterTransition.run();
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

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$transitionOut$2$InputsPanelActivity(Fragment fragment) {
        getFragmentManager().beginTransaction().remove(fragment).commitNow();
    }

    public static class AppInputsFragment extends LeanbackSettingsFragment {
        public static AppInputsFragment newInstance() {
            return new AppInputsFragment();
        }

        public void onPreferenceStartInitialScreen() {
            startPreferenceFragment(InputsPanelFragment.newInstance());
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
