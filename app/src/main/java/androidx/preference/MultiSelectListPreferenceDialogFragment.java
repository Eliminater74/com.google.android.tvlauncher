package androidx.preference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class MultiSelectListPreferenceDialogFragment extends PreferenceDialogFragment {
    private static final String SAVE_STATE_CHANGED = "MultiSelectListPreferenceDialogFragment.changed";
    private static final String SAVE_STATE_ENTRIES = "MultiSelectListPreferenceDialogFragment.entries";
    private static final String SAVE_STATE_ENTRY_VALUES = "MultiSelectListPreferenceDialogFragment.entryValues";
    private static final String SAVE_STATE_VALUES = "MultiSelectListPreferenceDialogFragment.values";
    CharSequence[] mEntries;
    CharSequence[] mEntryValues;
    Set<String> mNewValues = new HashSet();
    boolean mPreferenceChanged;

    @Deprecated
    public static MultiSelectListPreferenceDialogFragment newInstance(String key) {
        MultiSelectListPreferenceDialogFragment fragment = new MultiSelectListPreferenceDialogFragment();
        Bundle b = new Bundle(1);
        b.putString("key", key);
        fragment.setArguments(b);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            MultiSelectListPreference preference = getListPreference();
            if (preference.getEntries() == null || preference.getEntryValues() == null) {
                throw new IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
            }
            this.mNewValues.clear();
            this.mNewValues.addAll(preference.getValues());
            this.mPreferenceChanged = false;
            this.mEntries = preference.getEntries();
            this.mEntryValues = preference.getEntryValues();
            return;
        }
        this.mNewValues.clear();
        this.mNewValues.addAll(savedInstanceState.getStringArrayList(SAVE_STATE_VALUES));
        this.mPreferenceChanged = savedInstanceState.getBoolean(SAVE_STATE_CHANGED, false);
        this.mEntries = savedInstanceState.getCharSequenceArray(SAVE_STATE_ENTRIES);
        this.mEntryValues = savedInstanceState.getCharSequenceArray(SAVE_STATE_ENTRY_VALUES);
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVE_STATE_VALUES, new ArrayList(this.mNewValues));
        outState.putBoolean(SAVE_STATE_CHANGED, this.mPreferenceChanged);
        outState.putCharSequenceArray(SAVE_STATE_ENTRIES, this.mEntries);
        outState.putCharSequenceArray(SAVE_STATE_ENTRY_VALUES, this.mEntryValues);
    }

    private MultiSelectListPreference getListPreference() {
        return (MultiSelectListPreference) getPreference();
    }

    /* access modifiers changed from: protected */
    public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        int entryCount = this.mEntryValues.length;
        boolean[] checkedItems = new boolean[entryCount];
        for (int i = 0; i < entryCount; i++) {
            checkedItems[i] = this.mNewValues.contains(this.mEntryValues[i].toString());
        }
        builder.setMultiChoiceItems(this.mEntries, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    MultiSelectListPreferenceDialogFragment.this.mPreferenceChanged |= MultiSelectListPreferenceDialogFragment.this.mNewValues.add(MultiSelectListPreferenceDialogFragment.this.mEntryValues[which].toString());
                    return;
                }
                MultiSelectListPreferenceDialogFragment.this.mPreferenceChanged |= MultiSelectListPreferenceDialogFragment.this.mNewValues.remove(MultiSelectListPreferenceDialogFragment.this.mEntryValues[which].toString());
            }
        });
    }

    @Deprecated
    public void onDialogClosed(boolean positiveResult) {
        MultiSelectListPreference preference = getListPreference();
        if (positiveResult && this.mPreferenceChanged) {
            Set<String> values = this.mNewValues;
            if (preference.callChangeListener(values)) {
                preference.setValues(values);
            }
        }
        this.mPreferenceChanged = false;
    }
}
