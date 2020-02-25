package com.google.android.tvlauncher.inputs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.OemConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class RefreshTifInputsTask extends AsyncTask<Void, Void, Void> {
    private static final boolean DEBUG = false;
    private static final String TAG = "RefreshTifInputsTask";
    private final TvInputEntry.InputsComparator mComp;
    @SuppressLint({"StaticFieldLeak"})
    private final Context mContext;
    private final Map<String, TvInputEntry> mInputs = new HashMap();
    private final LoadedDataCallback mLoadedDataCallback;
    private final Map<String, TvInputInfo> mPhysicalTunerInputs = new LinkedHashMap();
    private final TvInputManager mTvManager;
    private final Map<String, TvInputInfo> mVirtualTunerInputs = new HashMap();
    private final List<TvInputEntry> mVisibleInputs = new ArrayList();
    private boolean mIsBundledTunerVisible;

    RefreshTifInputsTask(TvInputManager tvInputManager, Context context, LoadedDataCallback loadedDataCallback) {
        this.mTvManager = tvInputManager;
        this.mContext = context;
        this.mLoadedDataCallback = loadedDataCallback;
        this.mComp = new TvInputEntry.InputsComparator(this.mContext);
    }

    static final /* synthetic */ boolean lambda$doInBackground$0$RefreshTifInputsTask(TvInputEntry input) {
        return input.getNumChildren() > 0;
    }

    /* access modifiers changed from: protected */
    public Void doInBackground(Void... voids) {
        TvInputManager tvInputManager;
        List<TvInputInfo> serviceInputs;
        if (!(isCancelled() || (tvInputManager = this.mTvManager) == null || (serviceInputs = tvInputManager.getTvInputList()) == null)) {
            for (int i = 0; i < serviceInputs.size() && !isCancelled(); i++) {
                inputAddedInternal(serviceInputs.get(i));
            }
            this.mVisibleInputs.removeIf(RefreshTifInputsTask$$Lambda$0.$instance);
            this.mVisibleInputs.sort(this.mComp);
        }
        return null;
    }

    private void inputAddedInternal(TvInputInfo info) {
        if (info == null) {
            return;
        }
        if (info.isPassthroughInput()) {
            addInputEntryInternal(info);
        } else if (TifInputsManager.isPhysicalTuner(this.mContext.getPackageManager(), info)) {
            this.mPhysicalTunerInputs.put(info.getId(), info);
            if (TifInputsManager.getInstance(this.mContext).shouldShowPhysicalTunersSeparately()) {
                addInputEntryInternal(info);
            } else if (!info.isHidden(this.mContext)) {
                showBundledTunerInputInternal();
            }
        } else {
            this.mVirtualTunerInputs.put(info.getId(), info);
            if (!info.isHidden(this.mContext)) {
                showBundledTunerInputInternal();
            }
        }
    }

    private void addInputEntryInternal(TvInputInfo input) {
        TvInputInfo parentInfo;
        try {
            int state = this.mTvManager.getInputState(input.getId());
            TvInputEntry parentEntry = null;
            if (this.mInputs.get(input.getId()) == null) {
                if (!(input.getParentId() == null || (parentInfo = this.mTvManager.getTvInputInfo(input.getParentId())) == null)) {
                    parentEntry = this.mInputs.get(parentInfo.getId());
                    if (parentEntry == null) {
                        parentEntry = new TvInputEntry(parentInfo, null, this.mTvManager.getInputState(parentInfo.getId()));
                        parentEntry.init(this.mContext);
                        this.mInputs.put(parentInfo.getId(), parentEntry);
                    }
                    parentEntry.setNumChildren(parentEntry.getNumChildren() + 1);
                }
                TvInputEntry entry = new TvInputEntry(input, parentEntry, state);
                entry.init(this.mContext);
                this.mInputs.put(input.getId(), entry);
                if (entry.getInfo().isHidden(this.mContext)) {
                    return;
                }
                if (parentEntry == null || !parentEntry.getInfo().isHidden(this.mContext)) {
                    this.mVisibleInputs.add(entry);
                    if (parentEntry != null && parentEntry.getInfo().getParentId() == null && !this.mVisibleInputs.contains(parentEntry)) {
                        this.mVisibleInputs.add(parentEntry);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            String valueOf = String.valueOf(input.getId());
            Log.e(TAG, valueOf.length() != 0 ? "Failed to get state for Input, dropping entry. Id = ".concat(valueOf) : new String("Failed to get state for Input, dropping entry. Id = "));
        }
    }

    private void addHomeInput() {
        TvInputEntry homeInput = new TvInputEntry("com.google.android.tvlauncher.input.home", -7, this.mContext.getString(C1188R.string.input_title_home), null);
        homeInput.init(this.mContext);
        this.mVisibleInputs.add(homeInput);
    }

    private void showBundledTunerInputInternal() {
        if (!this.mIsBundledTunerVisible) {
            String label = OemConfiguration.get(this.mContext).getBundledTunerTitle();
            TvInputEntry bundledTuner = new TvInputEntry("com.google.android.tvlauncher.input.bundled_tuner", -3, !TextUtils.isEmpty(label) ? label : this.mContext.getResources().getString(C1188R.string.input_title_bundled_tuner), OemConfiguration.get(this.mContext).getBundledTunerBannerUri());
            bundledTuner.init(this.mContext);
            this.mVisibleInputs.add(bundledTuner);
            this.mIsBundledTunerVisible = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Void aVoid) {
        LoadedDataCallback loadedDataCallback = this.mLoadedDataCallback;
        if (loadedDataCallback != null) {
            loadedDataCallback.onDataLoaded(this.mPhysicalTunerInputs, this.mVirtualTunerInputs, this.mVisibleInputs, this.mInputs, this.mIsBundledTunerVisible);
        }
    }

    interface LoadedDataCallback {
        void onDataLoaded(Map<String, TvInputInfo> map, Map<String, TvInputInfo> map2, List<TvInputEntry> list, Map<String, TvInputEntry> map3, boolean z);
    }
}
