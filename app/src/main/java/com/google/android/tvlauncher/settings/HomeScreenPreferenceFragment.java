package com.google.android.tvlauncher.settings;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import androidx.leanback.preference.LeanbackPreferenceFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.appsview.AppsViewActivity;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvrecommendations.shared.util.Constants;

public class HomeScreenPreferenceFragment extends LeanbackPreferenceFragment implements Preference.OnPreferenceChangeListener {
    private static final String REORDER_APPS_KEY = "reorderapps";
    private static final String REORDER_GAMES_KEY = "reordergames";

    public static Fragment newInstance() {
        return new HomeScreenPreferenceFragment();
    }

    public void onCreatePreferences(Bundle bundle, String s) {
        Context preferenceContext = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(preferenceContext);
        screen.setTitle(C1188R.string.settings_dialog_title);
        createGuideViewPreference(screen, preferenceContext);
        createAppsViewPreference(screen, preferenceContext);
        createAboutCategory(screen, preferenceContext);
        setPreferenceScreen(screen);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        SharedPreferences prefs = getContext().getSharedPreferences(TvDataManager.PREVIEW_MEDIA_PREF_FILE_NAME, 0);
        if (TvDataManager.ENABLE_PREVIEW_VIDEO_KEY.equals(preference.getKey())) {
            prefs.edit().putBoolean(preference.getKey(), ((Boolean) newValue).booleanValue()).apply();
            return true;
        } else if (!TvDataManager.ENABLE_PREVIEW_AUDIO_KEY.equals(preference.getKey())) {
            return false;
        } else {
            prefs.edit().putBoolean(preference.getKey(), ((Boolean) newValue).booleanValue()).apply();
            return true;
        }
    }

    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        if (REORDER_APPS_KEY.equals(key)) {
            AppsViewActivity.startAppsViewActivity(0, getContext());
            return true;
        } else if (!REORDER_GAMES_KEY.equals(key)) {
            return super.onPreferenceTreeClick(preference);
        } else {
            AppsViewActivity.startAppsViewActivity(1, getContext());
            return true;
        }
    }

    private void createAppsViewPreference(PreferenceScreen screen, Context preferenceContext) {
        PreferenceCategory appsCategory = new PreferenceCategory(preferenceContext);
        appsCategory.setTitle(C1188R.string.apps_view_title);
        screen.addPreference(appsCategory);
        Preference reorderAppsPref = new Preference(preferenceContext);
        reorderAppsPref.setKey(REORDER_APPS_KEY);
        reorderAppsPref.setTitle(C1188R.string.customize_app_order_action_title);
        screen.addPreference(reorderAppsPref);
        Preference reorderGamesPref = new Preference(preferenceContext);
        reorderGamesPref.setKey(REORDER_GAMES_KEY);
        reorderGamesPref.setTitle(C1188R.string.customize_game_order_action_title);
        screen.addPreference(reorderGamesPref);
        setPreferenceScreen(screen);
    }

    private void createGuideViewPreference(PreferenceScreen screen, Context preferenceContext) {
        PreferenceCategory guideCategory = new PreferenceCategory(preferenceContext);
        guideCategory.setTitle(C1188R.string.guide_view_title);
        screen.addPreference(guideCategory);
        Preference channelsPreference = new Preference(getPreferenceManager().getContext());
        channelsPreference.setTitle(C1188R.string.add_channels_title);
        channelsPreference.setFragment(ConfigureChannelsFragment.class.getName());
        channelsPreference.setPersistent(false);
        screen.addPreference(channelsPreference);
        SwitchPreference previewVideoEnablePref = new SwitchPreference(preferenceContext);
        previewVideoEnablePref.setKey(TvDataManager.ENABLE_PREVIEW_VIDEO_KEY);
        previewVideoEnablePref.setTitle(C1188R.string.home_screen_preview_video_enable);
        SharedPreferences preferences = getContext().getSharedPreferences(TvDataManager.PREVIEW_MEDIA_PREF_FILE_NAME, 0);
        previewVideoEnablePref.setChecked(preferences.getBoolean(TvDataManager.ENABLE_PREVIEW_VIDEO_KEY, true));
        previewVideoEnablePref.setPersistent(false);
        previewVideoEnablePref.setOnPreferenceChangeListener(this);
        screen.addPreference(previewVideoEnablePref);
        SwitchPreference previewAudioEnablePref = new SwitchPreference(preferenceContext);
        previewAudioEnablePref.setKey(TvDataManager.ENABLE_PREVIEW_AUDIO_KEY);
        previewAudioEnablePref.setTitle(C1188R.string.home_screen_preview_audio_enable);
        previewAudioEnablePref.setChecked(preferences.getBoolean(TvDataManager.ENABLE_PREVIEW_AUDIO_KEY, true));
        previewAudioEnablePref.setPersistent(false);
        previewAudioEnablePref.setOnPreferenceChangeListener(this);
        screen.addPreference(previewAudioEnablePref);
    }

    private void createAboutCategory(PreferenceScreen screen, final Context preferenceContext) {
        PreferenceCategory aboutCategory = new PreferenceCategory(preferenceContext);
        aboutCategory.setTitle(C1188R.string.open_source_licenses_title);
        screen.addPreference(aboutCategory);
        Preference launcherLicenses = new Preference(preferenceContext);
        launcherLicenses.setTitle(C1188R.string.app_name);
        launcherLicenses.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                HomeScreenPreferenceFragment.this.startActivity(new Intent(preferenceContext, OpenSourceActivity.class));
                return true;
            }
        });
        launcherLicenses.setPersistent(false);
        screen.addPreference(launcherLicenses);
        final Intent intent = new Intent(Constants.VIEW_LICENSES_INTENT_ACTION);
        intent.setPackage(Constants.TVRECOMMENDATIONS_PACKAGE_NAME);
        PackageManager pm = preferenceContext.getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 65536);
        if (info != null) {
            Preference tvrecsLicenses = new Preference(preferenceContext);
            tvrecsLicenses.setTitle(info.loadLabel(pm));
            tvrecsLicenses.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    HomeScreenPreferenceFragment.this.startActivity(intent);
                    return true;
                }
            });
            screen.addPreference(tvrecsLicenses);
        }
    }
}
