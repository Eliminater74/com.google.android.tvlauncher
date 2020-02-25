package com.google.android.tvlauncher.inputs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.C0841C;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.OemConfiguration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TifInputsManager implements InputsManager {
    static final String META_LABEL_SORT_KEY = "input_sort_key";
    static final String PERMISSION_ACCESS_ALL_EPG_DATA = "com.android.providers.tv.permission.ACCESS_ALL_EPG_DATA";
    private static final boolean DEBUG = false;
    private static final int MSG_INPUT_ADDED = 2;
    private static final int MSG_INPUT_MODIFIED = 4;
    private static final int MSG_INPUT_REMOVED = 3;
    private static final int MSG_INPUT_UPDATED = 1;
    private static final String TAG = "TifInputsManager";
    private static final String[] mPhysicalTunerBlackList = {"com.google.android.videos", "com.google.android.youtube.tv"};
    @SuppressLint({"StaticFieldLeak"})
    private static TifInputsManager sInputsManager = null;
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final InputsHandler mHandler = new InputsHandler(this);
    private final TvInputEntry.InputsComparator mComp;
    private final InputCallback mInputsCallback = new InputCallback();
    private final TvInputManager mTvManager;
    /* access modifiers changed from: private */
    public Map<String, TvInputEntry> mInputs = new HashMap();
    /* access modifiers changed from: private */
    public boolean mInputsLoaded;
    /* access modifiers changed from: private */
    public boolean mIsBundledTunerVisible;
    /* access modifiers changed from: private */
    public Map<String, TvInputInfo> mPhysicalTunerInputs = new LinkedHashMap();
    /* access modifiers changed from: private */
    public Map<String, TvInputInfo> mVirtualTunerInputs = new HashMap();
    /* access modifiers changed from: private */
    public List<TvInputEntry> mVisibleInputs = new ArrayList();
    private boolean mDisableDisconnectedInputs;
    private boolean mGetStateIconFromTVInput;
    private List<OnInputsChangedListener> mListeners = new ArrayList(2);
    private RefreshTifInputsTask.LoadedDataCallback mLoadedDataCallback = new RefreshTifInputsTask.LoadedDataCallback() {
        public void onDataLoaded(Map<String, TvInputInfo> physicalTunerInputs, Map<String, TvInputInfo> virtualTunerInputs, List<TvInputEntry> visibleInputs, Map<String, TvInputEntry> inputs, boolean isBundledTunerVisible) {
            Map unused = TifInputsManager.this.mPhysicalTunerInputs = physicalTunerInputs;
            Map unused2 = TifInputsManager.this.mVirtualTunerInputs = virtualTunerInputs;
            List unused3 = TifInputsManager.this.mVisibleInputs = visibleInputs;
            Map unused4 = TifInputsManager.this.mInputs = inputs;
            boolean unused5 = TifInputsManager.this.mIsBundledTunerVisible = isBundledTunerVisible;
            for (TvInputEntry entry : TifInputsManager.this.mVisibleInputs) {
                entry.preloadIcon(TifInputsManager.this.mContext);
            }
            TifInputsManager.this.notifyInputsChanged();
            boolean unused6 = TifInputsManager.this.mInputsLoaded = true;
        }
    };
    private AsyncTask mRefreshTask;
    private boolean mShowPhysicalTunersSeparately;
    private Map<Integer, Integer> mTypePriorities;

    @VisibleForTesting
    TifInputsManager(Context context) {
        OemConfiguration oemConfiguration = OemConfiguration.get(context);
        this.mGetStateIconFromTVInput = oemConfiguration.getStateIconFromTVInput();
        this.mDisableDisconnectedInputs = oemConfiguration.shouldDisableDisconnectedInputs();
        this.mShowPhysicalTunersSeparately = oemConfiguration.shouldShowPhysicalTunersSeparately();
        this.mContext = context;
        this.mComp = new TvInputEntry.InputsComparator(this.mContext);
        this.mTvManager = (TvInputManager) context.getSystemService("tv_input");
        OemConfiguration.get(context).addConfigurationPackageChangeListener(new OemConfiguration.OemConfigurationPackageChangeListener() {
            public void onOemConfigurationPackageChanged() {
                TifInputsManager.this.refreshInputsFromOemConfig();
            }

            public void onOemConfigurationPackageRemoved() {
                TifInputsManager.this.refreshInputsFromOemConfig();
            }
        });
        setupDeviceTypePriorities();
    }

    public static TifInputsManager getInstance(Context context) {
        if (sInputsManager == null) {
            sInputsManager = new TifInputsManager(context.getApplicationContext());
        }
        return sInputsManager;
    }

    @VisibleForTesting
    static void setInstance(TifInputsManager inputsManager) {
        sInputsManager = inputsManager;
    }

    static boolean isPhysicalTuner(PackageManager pkgMan, TvInputInfo input) {
        if (Arrays.asList(mPhysicalTunerBlackList).contains(input.getServiceInfo().packageName) || input.createSetupIntent() == null) {
            return false;
        }
        if (pkgMan.checkPermission(PERMISSION_ACCESS_ALL_EPG_DATA, input.getServiceInfo().packageName) == 0) {
            return true;
        }
        try {
            if ((pkgMan.getApplicationInfo(input.getServiceInfo().packageName, 0).flags & 129) == 0) {
                return false;
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void refreshInputs() {
        AsyncTask asyncTask = this.mRefreshTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
        this.mRefreshTask = new RefreshTifInputsTask(this.mTvManager, this.mContext, this.mLoadedDataCallback).execute(new Void[0]);
    }

    public void loadInputs() {
        refreshInputs();
    }

    public void loadInputsIfNeeded() {
        if (!this.mInputsLoaded) {
            refreshInputs();
        }
    }

    public void registerOnChangedListener(OnInputsChangedListener listener) {
        TvInputManager tvInputManager;
        if (!this.mListeners.contains(listener)) {
            this.mListeners.add(listener);
            if (this.mListeners.size() == 1 && (tvInputManager = this.mTvManager) != null) {
                tvInputManager.registerCallback(this.mInputsCallback, this.mHandler);
            }
        }
    }

    public void unregisterOnChangedListener(OnInputsChangedListener listener) {
        TvInputManager tvInputManager;
        this.mListeners.remove(listener);
        if (this.mListeners.isEmpty() && (tvInputManager = this.mTvManager) != null) {
            tvInputManager.unregisterCallback(this.mInputsCallback);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isRefreshTaskRunning() {
        AsyncTask asyncTask = this.mRefreshTask;
        return asyncTask != null && asyncTask.getStatus() == AsyncTask.Status.RUNNING;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldDisableDisconnectedInputs() {
        return this.mDisableDisconnectedInputs;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldShowPhysicalTunersSeparately() {
        return this.mShowPhysicalTunersSeparately;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldGetStateIconFromTVInput() {
        return this.mGetStateIconFromTVInput;
    }

    public int getInputType(int position) {
        return this.mVisibleInputs.get(position).getType();
    }

    public void launchInputActivity(int position) {
        TvInputEntry entry = this.mVisibleInputs.get(position);
        if (!entry.isDisconnected() || !this.mDisableDisconnectedInputs) {
            try {
                Intent intent = entry.getLaunchIntent();
                intent.addFlags(C0841C.ENCODING_PCM_MU_LAW);
                this.mContext.startActivity(intent);
            } catch (Throwable e) {
                Log.e(TAG, "Could not perform launch:", e);
                Toast.makeText(this.mContext, C1188R.string.failed_launch, 0).show();
            }
        } else {
            String toastText = OemConfiguration.get(this.mContext).getDisconnectedInputToastText();
            if (!TextUtils.isEmpty(toastText)) {
                Toast.makeText(this.mContext, toastText, 0).show();
            }
        }
    }

    public String getInputId(int position) {
        return this.mVisibleInputs.get(position).getId();
    }

    public int getItemCount() {
        return this.mVisibleInputs.size();
    }

    public boolean hasInputs() {
        return getItemCount() > 0;
    }

    public Drawable getIcon(int position) {
        TvInputEntry entry = this.mVisibleInputs.get(position);
        return entry.getImageDrawable(this.mContext, entry.getState());
    }

    public Uri getIconUri(int position) {
        return this.mVisibleInputs.get(position).getIconUri();
    }

    public Uri getSelectedIconUri(int position) {
        return null;
    }

    public Uri getActiveIconUri(int position) {
        return null;
    }

    public Uri getSelectedActiveIconUri(int position) {
        return null;
    }

    public String getGroupId(int position) {
        return null;
    }

    public String getLabel(int position) {
        return this.mVisibleInputs.get(position).getLabel();
    }

    public String getParentLabel(int position) {
        return this.mVisibleInputs.get(position).getParentLabel();
    }

    public int getInputState(int position) {
        TvInputEntry entry = this.mVisibleInputs.get(position);
        if (entry.isConnected()) {
            if (entry.isStandby()) {
                return 1;
            }
            return 0;
        } else if (entry.isDisconnected()) {
            return 2;
        } else {
            return entry.getState();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Handler getHandler() {
        return this.mHandler;
    }

    /* access modifiers changed from: private */
    public void notifyInputsChanged() {
        for (OnInputsChangedListener listener : this.mListeners) {
            listener.onInputsChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public void refreshInputsFromOemConfig() {
        OemConfiguration oemConfiguration = OemConfiguration.get(this.mContext);
        this.mGetStateIconFromTVInput = oemConfiguration.getStateIconFromTVInput();
        this.mDisableDisconnectedInputs = oemConfiguration.shouldDisableDisconnectedInputs();
        this.mShowPhysicalTunersSeparately = oemConfiguration.shouldShowPhysicalTunersSeparately();
        setupDeviceTypePriorities();
        refreshInputs();
        notifyInputsChanged();
    }

    private void hideBundledTunerInput() {
        if (this.mIsBundledTunerVisible) {
            this.mIsBundledTunerVisible = false;
            boolean isVisuallyChanged = false;
            for (int i = this.mVisibleInputs.size() - 1; i >= 0; i--) {
                if (this.mVisibleInputs.get(i).isBundledTuner()) {
                    this.mVisibleInputs.remove(i);
                    isVisuallyChanged = true;
                }
            }
            if (isVisuallyChanged) {
                notifyInputsChanged();
            }
        }
    }

    private void showBundledTunerInput() {
        if (!this.mIsBundledTunerVisible) {
            String label = OemConfiguration.get(this.mContext).getBundledTunerTitle();
            TvInputEntry bundledTuner = new TvInputEntry("com.google.android.tvlauncher.input.bundled_tuner", -3, !TextUtils.isEmpty(label) ? label : this.mContext.getResources().getString(C1188R.string.input_title_bundled_tuner), OemConfiguration.get(this.mContext).getBundledTunerBannerUri());
            bundledTuner.init(this.mContext);
            bundledTuner.preloadIcon(this.mContext);
            insertEntryIntoSortedList(bundledTuner, this.mVisibleInputs);
            notifyInputsChanged();
            this.mIsBundledTunerVisible = true;
        }
    }

    private void addInputEntry(TvInputInfo input) {
        int parentIndex;
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
                    insertEntryIntoSortedList(entry, this.mVisibleInputs);
                    if (!(entry.getParentEntry() == null || (parentIndex = getIndexInVisibleList(entry.getParentEntry().getId())) == -1)) {
                        this.mVisibleInputs.remove(parentIndex);
                    }
                    notifyInputsChanged();
                }
            }
        } catch (IllegalArgumentException e) {
            String valueOf = String.valueOf(input.getId());
            Log.e(TAG, valueOf.length() != 0 ? "Failed to get state for Input, dropping entry. Id = ".concat(valueOf) : new String("Failed to get state for Input, dropping entry. Id = "));
        }
    }

    private int getIndexInVisibleList(String id) {
        for (int i = 0; i < this.mVisibleInputs.size(); i++) {
            TvInputInfo info = this.mVisibleInputs.get(i).getInfo();
            if (info != null && TextUtils.equals(info.getId(), id)) {
                return i;
            }
        }
        return -1;
    }

    private void insertEntryIntoSortedList(TvInputEntry entry, List<TvInputEntry> list) {
        int i = 0;
        while (i < list.size() && this.mComp.compare(entry, list.get(i)) > 0) {
            i++;
        }
        if (!list.contains(entry)) {
            list.add(i, entry);
        }
    }

    /* access modifiers changed from: private */
    public void inputStateUpdated(String id, int state) {
        TvInputEntry entry = this.mInputs.get(id);
        if (entry != null) {
            entry.setState(state);
            if (getIndexInVisibleList(id) >= 0) {
                notifyInputsChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void inputEntryModified(TvInputInfo inputInfo) {
        String id;
        TvInputEntry entry;
        if (inputInfo.isHidden(this.mContext)) {
            refreshInputs();
            return;
        }
        CharSequence customLabel = inputInfo.loadCustomLabel(this.mContext);
        if (customLabel != null && (entry = this.mInputs.get((id = inputInfo.getId()))) != null) {
            entry.setLabel(customLabel.toString());
            if (getIndexInVisibleList(id) >= 0) {
                notifyInputsChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void inputAdded(String id) {
        TvInputManager tvInputManager = this.mTvManager;
        if (tvInputManager != null) {
            inputAdded(tvInputManager.getTvInputInfo(id));
        }
    }

    private void inputAdded(TvInputInfo info) {
        if (info == null) {
            return;
        }
        if (info.isPassthroughInput()) {
            addInputEntry(info);
        } else if (isPhysicalTuner(this.mContext.getPackageManager(), info)) {
            this.mPhysicalTunerInputs.put(info.getId(), info);
            if (this.mShowPhysicalTunersSeparately) {
                addInputEntry(info);
            } else if (!info.isHidden(this.mContext)) {
                showBundledTunerInput();
            }
        } else {
            this.mVirtualTunerInputs.put(info.getId(), info);
            if (!info.isHidden(this.mContext)) {
                showBundledTunerInput();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void inputRemoved(String id) {
        TvInputEntry entry = this.mInputs.get(id);
        if (entry == null || entry.getInfo() == null || !entry.getInfo().isPassthroughInput()) {
            removeTuner(id);
        } else {
            removeEntry(id);
        }
    }

    private void removeTuner(String id) {
        removeEntry(id);
        this.mVirtualTunerInputs.remove(id);
        this.mPhysicalTunerInputs.remove(id);
        if (!this.mVirtualTunerInputs.isEmpty()) {
            return;
        }
        if (this.mPhysicalTunerInputs.isEmpty() || this.mShowPhysicalTunersSeparately) {
            hideBundledTunerInput();
        }
    }

    private void removeEntry(String id) {
        TvInputEntry entry = this.mInputs.get(id);
        if (entry != null) {
            boolean isVisuallyChanged = false;
            Iterator<Map.Entry<String, TvInputEntry>> iterator = this.mInputs.entrySet().iterator();
            while (iterator.hasNext()) {
                TvInputEntry child = (TvInputEntry) iterator.next().getValue();
                if (child.getParentEntry() != null && TextUtils.equals(child.getParentEntry().getId(), entry.getId())) {
                    iterator.remove();
                }
            }
            for (int i = this.mVisibleInputs.size() - 1; i >= 0; i--) {
                TvInputEntry children = this.mVisibleInputs.get(i);
                if (children.getParentEntry() != null && TextUtils.equals(children.getParentEntry().getId(), id)) {
                    this.mVisibleInputs.remove(i);
                    isVisuallyChanged = true;
                }
            }
            this.mInputs.remove(id);
            int index = getIndexInVisibleList(id);
            if (index != -1) {
                this.mVisibleInputs.remove(index);
                isVisuallyChanged = true;
            }
            TvInputEntry parent = entry.getParentEntry();
            if (parent != null) {
                parent.setNumChildren(Math.max(0, parent.getNumChildren() - 1));
                if (parent.getNumChildren() == 0 && getIndexInVisibleList(parent.getId()) == -1 && !parent.getInfo().isHidden(this.mContext)) {
                    insertEntryIntoSortedList(parent, this.mVisibleInputs);
                    isVisuallyChanged = true;
                }
            }
            if (isVisuallyChanged) {
                notifyInputsChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getPriorityForType(int type) {
        Integer priority;
        Map<Integer, Integer> map = this.mTypePriorities;
        if (map == null || (priority = map.get(Integer.valueOf(type))) == null) {
            return Integer.MAX_VALUE;
        }
        return priority.intValue();
    }

    private void setupDeviceTypePriorities() {
        this.mTypePriorities = InputsManagerUtil.getInputsOrderMap(OemConfiguration.get(this.mContext).getHomeScreenInputsOrdering());
    }

    private static class InputsHandler extends Handler {
        private final WeakReference<TifInputsManager> mTifInputsManager;

        InputsHandler(TifInputsManager tifInputsManager) {
            this.mTifInputsManager = new WeakReference<>(tifInputsManager);
        }

        public void handleMessage(Message msg) {
            TifInputsManager tifInputsManager = this.mTifInputsManager.get();
            if (tifInputsManager != null) {
                int i = msg.what;
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            if (i == 4) {
                                if (tifInputsManager.isRefreshTaskRunning()) {
                                    tifInputsManager.refreshInputs();
                                } else {
                                    tifInputsManager.inputEntryModified((TvInputInfo) msg.obj);
                                }
                            }
                        } else if (tifInputsManager.isRefreshTaskRunning()) {
                            tifInputsManager.refreshInputs();
                        } else {
                            tifInputsManager.inputRemoved((String) msg.obj);
                        }
                    } else if (tifInputsManager.isRefreshTaskRunning()) {
                        tifInputsManager.refreshInputs();
                    } else {
                        tifInputsManager.inputAdded((String) msg.obj);
                    }
                } else if (tifInputsManager.isRefreshTaskRunning()) {
                    tifInputsManager.refreshInputs();
                } else {
                    tifInputsManager.inputStateUpdated((String) msg.obj, msg.arg1);
                }
            }
        }
    }

    private class InputCallback extends TvInputManager.TvInputCallback {
        private InputCallback() {
        }

        public void onInputStateChanged(String inputId, int state) {
            TifInputsManager.this.mHandler.sendMessage(TifInputsManager.this.mHandler.obtainMessage(1, state, 0, inputId));
        }

        public void onInputAdded(String inputId) {
            TifInputsManager.this.mHandler.sendMessage(TifInputsManager.this.mHandler.obtainMessage(2, inputId));
        }

        public void onInputRemoved(String inputId) {
            TifInputsManager.this.mHandler.sendMessage(TifInputsManager.this.mHandler.obtainMessage(3, inputId));
        }

        public void onTvInputInfoUpdated(TvInputInfo inputInfo) {
            TifInputsManager.this.mHandler.sendMessage(TifInputsManager.this.mHandler.obtainMessage(4, inputInfo));
        }
    }
}
