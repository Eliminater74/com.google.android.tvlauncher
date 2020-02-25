package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.p001v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MultiSelectListPreference extends DialogPreference {
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private Set<String> mValues;

    public MultiSelectListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mValues = new HashSet();
        TypedArray a = context.obtainStyledAttributes(attrs, C0731R.styleable.MultiSelectListPreference, defStyleAttr, defStyleRes);
        this.mEntries = TypedArrayUtils.getTextArray(a, C0731R.styleable.MultiSelectListPreference_entries, C0731R.styleable.MultiSelectListPreference_android_entries);
        this.mEntryValues = TypedArrayUtils.getTextArray(a, C0731R.styleable.MultiSelectListPreference_entryValues, C0731R.styleable.MultiSelectListPreference_android_entryValues);
        a.recycle();
    }

    public MultiSelectListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MultiSelectListPreference(Context context, AttributeSet attrs) {
        this(context, attrs, TypedArrayUtils.getAttr(context, C0731R.attr.dialogPreferenceStyle, 16842897));
    }

    public MultiSelectListPreference(Context context) {
        this(context, null);
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public void setEntries(CharSequence[] entries) {
        this.mEntries = entries;
    }

    public void setEntries(@ArrayRes int entriesResId) {
        setEntries(getContext().getResources().getTextArray(entriesResId));
    }

    public CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    public void setEntryValues(CharSequence[] entryValues) {
        this.mEntryValues = entryValues;
    }

    public void setEntryValues(@ArrayRes int entryValuesResId) {
        setEntryValues(getContext().getResources().getTextArray(entryValuesResId));
    }

    public Set<String> getValues() {
        return this.mValues;
    }

    public void setValues(Set<String> values) {
        this.mValues.clear();
        this.mValues.addAll(values);
        persistStringSet(values);
        notifyChanged();
    }

    public int findIndexOfValue(String value) {
        CharSequence[] charSequenceArr;
        if (value == null || (charSequenceArr = this.mEntryValues) == null) {
            return -1;
        }
        for (int i = charSequenceArr.length - 1; i >= 0; i--) {
            if (this.mEntryValues[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public boolean[] getSelectedItems() {
        CharSequence[] entries = this.mEntryValues;
        int entryCount = entries.length;
        Set<String> values = this.mValues;
        boolean[] result = new boolean[entryCount];
        for (int i = 0; i < entryCount; i++) {
            result[i] = values.contains(entries[i].toString());
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public Object onGetDefaultValue(TypedArray a, int index) {
        CharSequence[] defaultValues = a.getTextArray(index);
        Set<String> result = new HashSet<>();
        for (CharSequence defaultValue : defaultValues) {
            result.add(defaultValue.toString());
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public void onSetInitialValue(Object defaultValue) {
        setValues(getPersistedStringSet((Set) defaultValue));
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            return superState;
        }
        SavedState myState = new SavedState(superState);
        myState.mValues = getValues();
        return myState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (state == null || !state.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        setValues(myState.mValues);
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        Set<String> mValues;

        SavedState(Parcel source) {
            super(source);
            int size = source.readInt();
            this.mValues = new HashSet();
            String[] strings = new String[size];
            source.readStringArray(strings);
            Collections.addAll(this.mValues, strings);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.mValues.size());
            Set<String> set = this.mValues;
            dest.writeStringArray((String[]) set.toArray(new String[set.size()]));
        }
    }
}
