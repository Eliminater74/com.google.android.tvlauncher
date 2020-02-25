package com.google.android.tvlauncher;

import android.content.Intent;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;

import com.google.android.tvlauncher.home.HomeFragment;
import com.google.android.tvlauncher.home.contentrating.ContentRatingsManager;
import com.google.android.tvlauncher.notifications.NotificationsUtils;
import com.google.android.tvlauncher.trace.AppTrace;
import com.google.android.tvlauncher.util.BootCompletedActivityHelper;
import com.google.android.tvlauncher.util.LaunchOnBootCompletedHelper;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

import java.util.Set;

public class MainActivity extends BlockForDataLauncherActivity {
    private static final long BLOCK_TIMEOUT = 4000;
    private static final int BOOT_COMPLETED_REQUEST = 0;
    private static final long CONTENT_RATINGS_MANAGER_STARTUP_DELAY_MILLIS = 2000;
    private static final boolean DEBUG = false;
    private static final String TAG = "TvLauncherMainActivity";
    private static final ConditionVariable sLock = new ConditionVariable(false);
    private final BootCompletedActivityHelper mBootCompletedHelper = new BootCompletedActivityHelper(this);
    private final LaunchOnBootCompletedHelper mLaunchOnBootCompletedHelper = new LaunchOnBootCompletedHelper(this);

    public MainActivity() {
        super("Home", TvLauncherConstants.HOME_PAGE);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppTrace.beginSection("onCreate");
        try {
            super.onCreate(savedInstanceState);
            AppTrace.endSection();
            new Handler().postDelayed(new MainActivity$$Lambda$0(this), 2000);
        } catch (Throwable th) {
            AppTrace.endSection();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onCreate$0$MainActivity() {
        ContentRatingsManager.getInstance(this).preload();
    }

    public void onCreateAddContent(Bundle savedInstanceState) {
        if (this.mLaunchOnBootCompletedHelper.isFirstLaunchAfterBoot()) {
            LaunchOnBootCompletedHelper launchOnBootCompletedHelper = this.mLaunchOnBootCompletedHelper;
            ConditionVariable conditionVariable = sLock;
            conditionVariable.getClass();
            launchOnBootCompletedHelper.loadLaunchOnBootFlagsAsync(MainActivity$$Lambda$1.get$Lambda(conditionVariable));
            sLock.block(BLOCK_TIMEOUT);
            if (this.mBootCompletedHelper.isBootCompletedActivityDone()) {
                startLaunchOnBootCompletedHelperIfNeeded();
            }
        }
        setContentView(C1188R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(C1188R.C1191id.home_view_container, new HomeFragment()).commit();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        AppTrace.beginSection("onStart");
        try {
            super.onStart();
        } finally {
            AppTrace.endSection();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        AppTrace.beginSection("onStop");
        try {
            super.onStop();
        } finally {
            AppTrace.endSection();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        AppTrace.beginSection("onResume");
        try {
            super.onResume();
            startBootCompletedActivityIfNeeded();
            NotificationsUtils.showUnshownNotifications(this);
        } finally {
            AppTrace.endSection();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        AppTrace.beginSection("onPause");
        try {
            super.onPause();
        } finally {
            AppTrace.endSection();
        }
    }

    public void onNewIntent(Intent intent) {
        AppTrace.beginSection("onNewIntent");
        try {
            super.onNewIntent(intent);
            Set<String> categories = intent.getCategories();
            if (categories != null && categories.contains("android.intent.category.HOME")) {
                MainBackHomeController.getInstance().onHomePressed(this);
            }
        } finally {
            AppTrace.endSection();
        }
    }

    public void onBackPressed() {
        MainBackHomeController.getInstance().onBackPressed(this);
    }

    public void onUserInteraction() {
        HomeFragment fragment = (HomeFragment) getFragmentManager().findFragmentById(C1188R.C1191id.home_view_container);
        if (fragment != null) {
            fragment.onUserInteraction();
        }
    }

    private void startBootCompletedActivityIfNeeded() {
        if (!this.mBootCompletedHelper.isBootCompletedActivityDone()) {
            Intent intent = this.mBootCompletedHelper.getBootCompletedIntent();
            if (intent != null) {
                startActivityForResult(intent, 0);
                return;
            }
            this.mBootCompletedHelper.onBootCompletedActivityDone();
            startLaunchOnBootCompletedHelperIfNeeded();
            return;
        }
        startLaunchOnBootCompletedHelperIfNeeded();
    }

    private void startLaunchOnBootCompletedHelperIfNeeded() {
        if (this.mLaunchOnBootCompletedHelper.isLoaded() && this.mLaunchOnBootCompletedHelper.isFirstLaunchAfterBoot() && this.mLaunchOnBootCompletedHelper.tryLaunchingOemPackage()) {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == -1) {
                this.mBootCompletedHelper.onBootCompletedActivityDone();
            }
            startLaunchOnBootCompletedHelperIfNeeded();
        }
    }
}
