package com.google.android.tvlauncher.appsview;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p001v4.view.GravityCompat;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.ViewGroup;

import com.google.android.tvlauncher.BlockForDataLauncherActivity;
import com.google.android.tvlauncher.settings.SettingsIntents;
import com.google.android.tvlauncher.util.Util;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

public class AppsViewActivity extends BlockForDataLauncherActivity {
    static final String TAG_APPS_VIEW_FRAGMENT = "apps_view_fragment";
    static final String TAG_EDIT_MODE_FRAGMENT = "edit_mode_fragment";
    private static final String CHECK_FOR_EDIT_MODE = "check_for_edit_mode";
    private static final String TAG = "AppsViewActivity";

    public AppsViewActivity() {
        super("Apps", TvLauncherConstants.APPS_PAGE);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent}
     arg types: [java.lang.String, int]
     candidates:
      ClspMth{android.content.Intent.putExtra(java.lang.String, int):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, int[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Bundle):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.io.Serializable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent} */
    public static void startAppsViewActivity(@Nullable Integer editModeType, Context context) {
        Intent intent = new Intent("android.intent.action.ALL_APPS");
        if (editModeType != null && editModeType.intValue() == 0) {
            intent.putExtra(SettingsIntents.EXTRA_START_CUSTOMIZE_APPS, true);
        } else if (editModeType != null && editModeType.intValue() == 1) {
            intent.putExtra(SettingsIntents.EXTRA_START_CUSTOMIZE_GAMES, true);
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 30);
            sb.append("AppsViewActivity not found :  ");
            sb.append(valueOf);
            Log.e(TAG, sb.toString());
        }
    }

    public void onCreateAddContent(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            onShowAppsView();
            TransitionManager.go(new Scene((ViewGroup) findViewById(16908290)), new Slide(GravityCompat.END));
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent}
     arg types: [java.lang.String, int]
     candidates:
      ClspMth{android.content.Intent.putExtra(java.lang.String, int):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, int[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Bundle):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.io.Serializable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent} */
    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Util.forceLandscapeOrientation(this);
        if (!(getFragmentManager().findFragmentByTag(TAG_EDIT_MODE_FRAGMENT) == null || getFragmentManager().getBackStackEntryCount() == 0)) {
            getFragmentManager().popBackStack();
        }
        sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        if (!this.mIsBlockedForData && getIntent().getBooleanExtra(CHECK_FOR_EDIT_MODE, true)) {
            checkIntentForEditMode();
            getIntent().putExtra(CHECK_FOR_EDIT_MODE, false);
        }
    }

    public void finish() {
        Fragment fragment = getFragmentManager().findFragmentByTag(TAG_APPS_VIEW_FRAGMENT);
        if (fragment == null || !fragment.isResumed()) {
            super.finish();
            return;
        }
        Scene scene = new Scene((ViewGroup) findViewById(16908290));
        scene.setEnterAction(new AppsViewActivity$$Lambda$0(this, fragment));
        Slide slide = new Slide(GravityCompat.END);
        slide.addListener(new Transition.TransitionListener() {
            public void onTransitionStart(Transition transition) {
                AppsViewActivity.this.getWindow().setDimAmount(0.0f);
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                AppsViewActivity.super.finish();
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
    public final /* synthetic */ void lambda$finish$0$AppsViewActivity(Fragment fragment) {
        getFragmentManager().beginTransaction().remove(fragment).commitNow();
    }

    private void checkIntentForEditMode() {
        Intent intent = getIntent();
        AppsViewFragment fragment = (AppsViewFragment) getFragmentManager().findFragmentByTag(TAG_APPS_VIEW_FRAGMENT);
        if (intent != null && intent.getExtras() != null && fragment != null) {
            if (intent.getBooleanExtra(SettingsIntents.EXTRA_START_CUSTOMIZE_APPS, false)) {
                fragment.startEditMode(0);
            } else if (intent.getBooleanExtra(SettingsIntents.EXTRA_START_CUSTOMIZE_GAMES, false)) {
                fragment.startEditMode(1);
            }
        }
    }

    private void onShowAppsView() {
        getFragmentManager().beginTransaction().add(16908290, new AppsViewFragment(), TAG_APPS_VIEW_FRAGMENT).commit();
    }
}
