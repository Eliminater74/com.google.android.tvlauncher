package com.google.android.tvlauncher.inputs;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;

import androidx.leanback.preference.LeanbackPreferenceFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.FragmentEventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.logs.tvlauncher.config.TvLauncherConstants;
import com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputsPanelFragment extends LeanbackPreferenceFragment implements OnInputsChangedListener, Preference.OnPreferenceClickListener {
    private static final int INPUT_CLICK_TIMEOUT_MS = 3000;
    private static final String INPUT_GROUP_ID_EXTRA = "input_group_id";
    private static final int MSG_DELAYED_SELECTED_PREFERENCE_CLICK = 2;
    private static final int MSG_SMOOTH_SCROLL_TO_SELECTION = 3;
    private static final int SINGLE_INPUT_CLICK_TIMEOUT_MS = 2000;
    private static final String TAG = "InputsPanelFragment";
    private final List<String> mAllPreferenceIds = new ArrayList(20);
    private final Map<String, List<String>> mGroupIds = new HashMap(5);
    private final InteractionHandler mInteractionHandler = new InteractionHandler();
    private final InputPreference.OnPreferenceFocusedListener mOnPreferenceFocusedListener = new InputsPanelFragment$$Lambda$0(this);
    private Callbacks mCallbacks;
    private Context mContext;
    private FragmentEventLogger mEventLogger;
    private boolean mFirstInputRefreshDone;
    private String mSelectedPreferenceKey = null;

    static InputsPanelFragment newInstance() {
        return new InputsPanelFragment();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$0$InputsPanelFragment(String preferenceKey) {
        if (!TextUtils.equals(this.mSelectedPreferenceKey, preferenceKey)) {
            this.mSelectedPreferenceKey = preferenceKey;
            cancelPreferenceClickTimeout();
        }
    }

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(getPreferenceManager().getContext());
        this.mContext = getContext();
        screen.setTitle(OemConfiguration.get(this.mContext).getInputsPanelLabelText(this.mContext));
        setPreferenceScreen(screen);
        this.mEventLogger = new FragmentEventLogger(this);
        logImpression();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity != null) {
            if (activity instanceof Callbacks) {
                this.mCallbacks = (Callbacks) activity;
                this.mCallbacks.onInputsPanelFragmentAttached(this);
                return;
            }
            Log.e(TAG, "onAttach: parent activity is of wrong type");
        }
    }

    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key == null) {
            return super.onPreferenceTreeClick(preference);
        }
        int position = Integer.valueOf(key).intValue();
        LogEvent event = new ClickEvent(TvlauncherLogEnum.TvLauncherEventCode.SELECT_INPUT).setVisualElementTag(TvLauncherConstants.INPUT).setVisualElementIndex(position);
        populateLogInput(event.getInput(), position);
        this.mEventLogger.log(event);
        cancelPreferenceClickTimeout();
        Callbacks callbacks = this.mCallbacks;
        if (callbacks == null) {
            return true;
        }
        callbacks.onInputClicked(position);
        return true;
    }

    public void onResume() {
        super.onResume();
        InputsManagerUtil.getInputsManager(this.mContext).registerOnChangedListener(this);
        InputsManagerUtil.getInputsManager(this.mContext).loadInputs();
        if (this.mFirstInputRefreshDone) {
            handleIntent();
        }
    }

    public void onPause() {
        super.onPause();
        InputsManagerUtil.getInputsManager(this.mContext).unregisterOnChangedListener(this);
        cancelPreferenceClickTimeout();
    }

    public void onInputsChanged() {
        refreshInputPreferences(getPreferenceScreen());
        if (!this.mFirstInputRefreshDone) {
            this.mFirstInputRefreshDone = true;
            handleIntent();
        }
    }

    /* access modifiers changed from: package-private */
    public void cancelPreferenceClickTimeout() {
        this.mInteractionHandler.removeMessages(2);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public String getSelectedPreferenceKey() {
        return this.mSelectedPreferenceKey;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public InputPreference.OnPreferenceFocusedListener getOnPreferenceFocusedListener() {
        return this.mOnPreferenceFocusedListener;
    }

    /* access modifiers changed from: private */
    public void performSelectedPreferenceClick() {
        Preference pref;
        if (getPreferenceScreen() != null && (pref = getPreferenceScreen().findPreference(this.mSelectedPreferenceKey)) != null) {
            pref.performClick();
        }
    }

    /* access modifiers changed from: private */
    public void smoothScrollToSelection() {
        if (getListView() != null) {
            getListView().smoothScrollToPosition(Integer.valueOf(this.mSelectedPreferenceKey).intValue());
        }
    }

    private void setSelectedPreference(String preferenceId) {
        this.mSelectedPreferenceKey = preferenceId;
        this.mInteractionHandler.sendEmptyMessage(3);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean hasPreferenceClickTimeout() {
        return this.mInteractionHandler.hasMessages(2);
    }

    private void startPreferenceClickTimeout(int timeout) {
        this.mInteractionHandler.removeMessages(2);
        this.mInteractionHandler.sendEmptyMessageDelayed(2, (long) timeout);
    }

    /* access modifiers changed from: package-private */
    public void launchInputActivity(int inputIndex) {
        InputsManagerUtil.getInputsManager(this.mContext).launchInputActivity(inputIndex);
    }

    private void logImpression() {
        UserActionEvent event = new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.OPEN_INPUTS_VIEW);
        TvlauncherClientLog.InputCollection.Builder collection = event.getInputCollection();
        for (int i = 0; i < InputsManagerUtil.getInputsManager(this.mContext).getItemCount(); i++) {
            TvlauncherClientLog.Input.Builder input = TvlauncherClientLog.Input.newBuilder();
            populateLogInput(input, i);
            collection.addInputs(input);
        }
        this.mEventLogger.log(event);
    }

    private void refreshInputPreferences(PreferenceScreen screen) {
        boolean applyStandardStyleToInputStateIcons = OemConfiguration.get(this.mContext).shouldApplyStandardStyleToInputStateIcons();
        InputsManager inputsManager = InputsManagerUtil.getInputsManager(this.mContext);
        screen.removeAll();
        this.mGroupIds.clear();
        this.mAllPreferenceIds.clear();
        for (int i = 0; i < inputsManager.getItemCount(); i++) {
            InputPreference preference = new InputPreference(getPreferenceManager().getContext(), this.mOnPreferenceFocusedListener);
            if (inputsManager.getIconUri(i) == null) {
                preference.setIcon(inputsManager.getIcon(i));
            } else {
                Uri iconUri = inputsManager.getIconUri(i);
                Uri selectedIconUri = inputsManager.getSelectedIconUri(i);
                Uri activeIconUri = inputsManager.getActiveIconUri(i);
                Uri selectedActiveIconUri = inputsManager.getSelectedActiveIconUri(i);
                if (selectedIconUri == null) {
                    selectedIconUri = iconUri;
                }
                if (selectedActiveIconUri == null) {
                    selectedActiveIconUri = activeIconUri == null ? selectedIconUri : activeIconUri;
                }
                if (activeIconUri == null) {
                    activeIconUri = iconUri;
                }
                preference.setIconUri(iconUri);
                preference.setSelectedIconUri(selectedIconUri);
                preference.setActiveIconUri(activeIconUri);
                preference.setSelectedActiveIconUri(selectedActiveIconUri);
            }
            preference.setTitle(inputsManager.getLabel(i));
            preference.setSummary(inputsManager.getParentLabel(i));
            preference.setIsActive(false);
            preference.setState(inputsManager.getInputState(i));
            String key = Integer.toString(i);
            if (inputsManager.getInputId(i).equals("com.google.android.tvlauncher.input.home")) {
                preference.setApplyStandardStyleToInputStateIcons(true);
            } else {
                preference.setApplyStandardStyleToInputStateIcons(applyStandardStyleToInputStateIcons);
            }
            preference.setKey(key);
            preference.setOnPreferenceClickListener(this);
            screen.addPreference(preference);
            String groupId = inputsManager.getGroupId(i);
            if (groupId != null) {
                if (this.mGroupIds.containsKey(groupId)) {
                    this.mGroupIds.get(groupId).add(key);
                } else {
                    List<String> preferenceIds = new ArrayList<>(5);
                    preferenceIds.add(key);
                    this.mGroupIds.put(groupId, preferenceIds);
                }
            }
            this.mAllPreferenceIds.add(key);
        }
    }

    private void handleIntent() {
        List<String> preferenceIds;
        if (this.mAllPreferenceIds.size() != 0) {
            String groupId = null;
            if (!(getActivity() == null || getActivity().getIntent() == null)) {
                groupId = getActivity().getIntent().getStringExtra(INPUT_GROUP_ID_EXTRA);
            }
            if (groupId == null) {
                preferenceIds = this.mAllPreferenceIds;
            } else if (this.mGroupIds.containsKey(groupId)) {
                preferenceIds = this.mGroupIds.get(groupId);
            } else {
                return;
            }
            if (this.mSelectedPreferenceKey == null && groupId == null) {
                setSelectedPreference((String) preferenceIds.get(0));
                return;
            }
            String str = this.mSelectedPreferenceKey;
            if (str == null) {
                setSelectedPreference((String) preferenceIds.get(0));
            } else {
                int pos = preferenceIds.indexOf(str);
                if (pos >= 0) {
                    setSelectedPreference((String) preferenceIds.get((pos + 1) % preferenceIds.size()));
                } else {
                    setSelectedPreference((String) preferenceIds.get(0));
                }
            }
            if (preferenceIds.size() == 1) {
                startPreferenceClickTimeout(2000);
            } else {
                startPreferenceClickTimeout(3000);
            }
        }
    }

    private void populateLogInput(TvlauncherClientLog.Input.Builder input, int position) {
        TvlauncherClientLog.Input.Type type = LogEvent.inputType(InputsManagerUtil.getInputsManager(this.mContext).getInputType(position));
        if (type != null) {
            input.setType(type);
        }
        String label = InputsManagerUtil.getInputsManager(this.mContext).getLabel(position);
        if (!TextUtils.isEmpty(label)) {
            input.setDisplayName(label);
        }
    }

    interface Callbacks {
        void onInputClicked(int i);

        void onInputsPanelFragmentAttached(InputsPanelFragment inputsPanelFragment);
    }

    private static class InteractionHandler extends Handler {
        private final WeakReference<InputsPanelFragment> mInputsPanelFragment;

        private InteractionHandler(InputsPanelFragment inputsPanelFragment) {
            this.mInputsPanelFragment = new WeakReference<>(inputsPanelFragment);
        }

        public void handleMessage(Message m) {
            InputsPanelFragment inputsPanelFragment = this.mInputsPanelFragment.get();
            if (inputsPanelFragment != null) {
                int i = m.what;
                if (i == 2) {
                    inputsPanelFragment.performSelectedPreferenceClick();
                } else if (i == 3) {
                    inputsPanelFragment.smoothScrollToSelection();
                }
            }
        }
    }
}
