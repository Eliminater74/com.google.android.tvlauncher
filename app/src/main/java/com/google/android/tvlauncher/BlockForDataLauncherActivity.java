package com.google.android.tvlauncher;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.libraries.social.analytics.visualelement.VisualElementTag;
import com.google.android.tvlauncher.analytics.LoggingActivity;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.util.OemConfiguration;

public abstract class BlockForDataLauncherActivity extends LoggingActivity {
    private static final String BLOCKING_FRAGMENT_TAG = "blocking_fragment_tag";
    protected boolean mIsBlockedForData;
    private boolean mContentFragmentAdded;
    private OemConfiguration.OnDataLoadedListener mOnDataLoadedListener;

    public BlockForDataLauncherActivity(String name, VisualElementTag visualElementTag) {
        super(name, visualElementTag);
    }

    public abstract void onCreateAddContent(Bundle bundle);

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OemConfiguration oemConfiguration = OemConfiguration.get(this);
        if (!oemConfiguration.isDataLoaded()) {
            this.mIsBlockedForData = true;
            this.mOnDataLoadedListener = new BlockForDataLauncherActivity$$Lambda$0(this, savedInstanceState);
            oemConfiguration.registerOnDataLoadedListener(this.mOnDataLoadedListener);
            if (getFragmentManager().findFragmentByTag(BLOCKING_FRAGMENT_TAG) == null) {
                getFragmentManager().beginTransaction().add(16908290, new BlockForDataFragment(), BLOCKING_FRAGMENT_TAG).commit();
                return;
            }
            return;
        }
        lambda$onCreate$0$BlockForDataLauncherActivity(savedInstanceState);
    }

    private void removeBlockingFragment() {
        Fragment fragment = getFragmentManager().findFragmentByTag(BLOCKING_FRAGMENT_TAG);
        if (fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (!this.mIsBlockedForData) {
            removeBlockingFragment();
            if (!this.mContentFragmentAdded) {
                lambda$onCreate$0$BlockForDataLauncherActivity(null);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onCreateContinue */
    public void lambda$onCreate$0$BlockForDataLauncherActivity(Bundle savedInstanceState) {
        this.mIsBlockedForData = false;
        OemConfiguration.get(this).unregisterOnDataLoadedListener(this.mOnDataLoadedListener);
        LaunchItemsManagerProvider.getInstance(this).initIfNeeded();
        if (!getFragmentManager().isStateSaved()) {
            removeBlockingFragment();
            onCreateAddContent(savedInstanceState);
            this.mContentFragmentAdded = true;
        }
    }
}
