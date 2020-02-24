package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.preference.Preference;

public abstract class TwoStatePreference extends Preference {
    protected boolean mChecked;
    private boolean mCheckedSet;
    private boolean mDisableDependentsState;
    private CharSequence mSummaryOff;
    private CharSequence mSummaryOn;

    public TwoStatePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TwoStatePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TwoStatePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoStatePreference(Context context) {
        this(context, null);
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        super.onClick();
        boolean newValue = !isChecked();
        if (callChangeListener(Boolean.valueOf(newValue))) {
            setChecked(newValue);
        }
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public void setChecked(boolean checked) {
        boolean changed = this.mChecked != checked;
        if (changed || !this.mCheckedSet) {
            this.mChecked = checked;
            this.mCheckedSet = true;
            persistBoolean(checked);
            if (changed) {
                notifyDependencyChange(shouldDisableDependents());
                notifyChanged();
            }
        }
    }

    public boolean shouldDisableDependents() {
        if ((this.mDisableDependentsState ? this.mChecked : !this.mChecked) || super.shouldDisableDependents()) {
            return true;
        }
        return false;
    }

    public void setSummaryOn(CharSequence summary) {
        this.mSummaryOn = summary;
        if (isChecked()) {
            notifyChanged();
        }
    }

    public CharSequence getSummaryOn() {
        return this.mSummaryOn;
    }

    public void setSummaryOn(int summaryResId) {
        setSummaryOn(getContext().getString(summaryResId));
    }

    public void setSummaryOff(CharSequence summary) {
        this.mSummaryOff = summary;
        if (!isChecked()) {
            notifyChanged();
        }
    }

    public CharSequence getSummaryOff() {
        return this.mSummaryOff;
    }

    public void setSummaryOff(int summaryResId) {
        setSummaryOff(getContext().getString(summaryResId));
    }

    public boolean getDisableDependentsState() {
        return this.mDisableDependentsState;
    }

    public void setDisableDependentsState(boolean disableDependentsState) {
        this.mDisableDependentsState = disableDependentsState;
    }

    /* access modifiers changed from: protected */
    public Object onGetDefaultValue(TypedArray a, int index) {
        return Boolean.valueOf(a.getBoolean(index, false));
    }

    /* access modifiers changed from: protected */
    public void onSetInitialValue(Object defaultValue) {
        if (defaultValue == null) {
            defaultValue = false;
        }
        setChecked(getPersistedBoolean(((Boolean) defaultValue).booleanValue()));
    }

    /* access modifiers changed from: protected */
    public void syncSummaryView(PreferenceViewHolder holder) {
        syncSummaryView(holder.findViewById(16908304));
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void syncSummaryView(View view) {
        if (view instanceof TextView) {
            TextView summaryView = (TextView) view;
            boolean useDefaultSummary = true;
            if (this.mChecked && !TextUtils.isEmpty(this.mSummaryOn)) {
                summaryView.setText(this.mSummaryOn);
                useDefaultSummary = false;
            } else if (!this.mChecked && !TextUtils.isEmpty(this.mSummaryOff)) {
                summaryView.setText(this.mSummaryOff);
                useDefaultSummary = false;
            }
            if (useDefaultSummary) {
                CharSequence summary = getSummary();
                if (!TextUtils.isEmpty(summary)) {
                    summaryView.setText(summary);
                    useDefaultSummary = false;
                }
            }
            int newVisibility = 8;
            if (!useDefaultSummary) {
                newVisibility = 0;
            }
            if (newVisibility != summaryView.getVisibility()) {
                summaryView.setVisibility(newVisibility);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            return superState;
        }
        SavedState myState = new SavedState(superState);
        myState.mChecked = isChecked();
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
        setChecked(myState.mChecked);
    }

    static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean mChecked;

        SavedState(Parcel source) {
            super(source);
            this.mChecked = source.readInt() != 1 ? false : true;
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.mChecked ? 1 : 0);
        }
    }
}
