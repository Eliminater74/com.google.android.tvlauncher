package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarPreference extends Preference {
    private static final String TAG = "SeekBarPreference";
    boolean mAdjustable;
    int mMin;
    SeekBar mSeekBar;
    int mSeekBarValue;
    boolean mTrackingTouch;
    boolean mUpdatesContinuously;
    private int mMax;
    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener;
    private int mSeekBarIncrement;
    private View.OnKeyListener mSeekBarKeyListener;
    private TextView mSeekBarValueTextView;
    private boolean mShowSeekBarValue;

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser || (!SeekBarPreference.this.mUpdatesContinuously && SeekBarPreference.this.mTrackingTouch)) {
                    SeekBarPreference seekBarPreference = SeekBarPreference.this;
                    seekBarPreference.updateLabelValue(seekBarPreference.mMin + progress);
                    return;
                }
                SeekBarPreference.this.syncValueInternal(seekBar);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                SeekBarPreference.this.mTrackingTouch = true;
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekBarPreference.this.mTrackingTouch = false;
                if (seekBar.getProgress() + SeekBarPreference.this.mMin != SeekBarPreference.this.mSeekBarValue) {
                    SeekBarPreference.this.syncValueInternal(seekBar);
                }
            }
        };
        this.mSeekBarKeyListener = new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != 0) {
                    return false;
                }
                if ((!SeekBarPreference.this.mAdjustable && (keyCode == 21 || keyCode == 22)) || keyCode == 23 || keyCode == 66) {
                    return false;
                }
                if (SeekBarPreference.this.mSeekBar != null) {
                    return SeekBarPreference.this.mSeekBar.onKeyDown(keyCode, event);
                }
                Log.e(SeekBarPreference.TAG, "SeekBar view is null and hence cannot be adjusted.");
                return false;
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, C0731R.styleable.SeekBarPreference, defStyleAttr, defStyleRes);
        this.mMin = a.getInt(C0731R.styleable.SeekBarPreference_min, 0);
        setMax(a.getInt(C0731R.styleable.SeekBarPreference_android_max, 100));
        setSeekBarIncrement(a.getInt(C0731R.styleable.SeekBarPreference_seekBarIncrement, 0));
        this.mAdjustable = a.getBoolean(C0731R.styleable.SeekBarPreference_adjustable, true);
        this.mShowSeekBarValue = a.getBoolean(C0731R.styleable.SeekBarPreference_showSeekBarValue, false);
        this.mUpdatesContinuously = a.getBoolean(C0731R.styleable.SeekBarPreference_updatesContinuously, false);
        a.recycle();
    }

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, C0731R.attr.seekBarPreferenceStyle);
    }

    public SeekBarPreference(Context context) {
        this(context, null);
    }

    public void onBindViewHolder(PreferenceViewHolder view) {
        super.onBindViewHolder(view);
        view.itemView.setOnKeyListener(this.mSeekBarKeyListener);
        this.mSeekBar = (SeekBar) view.findViewById(C0731R.C0733id.seekbar);
        this.mSeekBarValueTextView = (TextView) view.findViewById(C0731R.C0733id.seekbar_value);
        if (this.mShowSeekBarValue) {
            this.mSeekBarValueTextView.setVisibility(0);
        } else {
            this.mSeekBarValueTextView.setVisibility(8);
            this.mSeekBarValueTextView = null;
        }
        SeekBar seekBar = this.mSeekBar;
        if (seekBar == null) {
            Log.e(TAG, "SeekBar view is null in onBindViewHolder.");
            return;
        }
        seekBar.setOnSeekBarChangeListener(this.mSeekBarChangeListener);
        this.mSeekBar.setMax(this.mMax - this.mMin);
        int i = this.mSeekBarIncrement;
        if (i != 0) {
            this.mSeekBar.setKeyProgressIncrement(i);
        } else {
            this.mSeekBarIncrement = this.mSeekBar.getKeyProgressIncrement();
        }
        this.mSeekBar.setProgress(this.mSeekBarValue - this.mMin);
        updateLabelValue(this.mSeekBarValue);
        this.mSeekBar.setEnabled(isEnabled());
    }

    /* access modifiers changed from: protected */
    public void onSetInitialValue(Object defaultValue) {
        if (defaultValue == null) {
            defaultValue = 0;
        }
        setValue(getPersistedInt(((Integer) defaultValue).intValue()));
    }

    /* access modifiers changed from: protected */
    public Object onGetDefaultValue(TypedArray a, int index) {
        return Integer.valueOf(a.getInt(index, 0));
    }

    public int getMin() {
        return this.mMin;
    }

    public void setMin(int min) {
        if (min > this.mMax) {
            min = this.mMax;
        }
        if (min != this.mMin) {
            this.mMin = min;
            notifyChanged();
        }
    }

    public final int getSeekBarIncrement() {
        return this.mSeekBarIncrement;
    }

    public final void setSeekBarIncrement(int seekBarIncrement) {
        if (seekBarIncrement != this.mSeekBarIncrement) {
            this.mSeekBarIncrement = Math.min(this.mMax - this.mMin, Math.abs(seekBarIncrement));
            notifyChanged();
        }
    }

    public int getMax() {
        return this.mMax;
    }

    public final void setMax(int max) {
        if (max < this.mMin) {
            max = this.mMin;
        }
        if (max != this.mMax) {
            this.mMax = max;
            notifyChanged();
        }
    }

    public boolean isAdjustable() {
        return this.mAdjustable;
    }

    public void setAdjustable(boolean adjustable) {
        this.mAdjustable = adjustable;
    }

    public boolean getUpdatesContinuously() {
        return this.mUpdatesContinuously;
    }

    public void setUpdatesContinuously(boolean updatesContinuously) {
        this.mUpdatesContinuously = updatesContinuously;
    }

    public boolean getShowSeekBarValue() {
        return this.mShowSeekBarValue;
    }

    public void setShowSeekBarValue(boolean showSeekBarValue) {
        this.mShowSeekBarValue = showSeekBarValue;
        notifyChanged();
    }

    private void setValueInternal(int seekBarValue, boolean notifyChanged) {
        if (seekBarValue < this.mMin) {
            seekBarValue = this.mMin;
        }
        if (seekBarValue > this.mMax) {
            seekBarValue = this.mMax;
        }
        if (seekBarValue != this.mSeekBarValue) {
            this.mSeekBarValue = seekBarValue;
            updateLabelValue(this.mSeekBarValue);
            persistInt(seekBarValue);
            if (notifyChanged) {
                notifyChanged();
            }
        }
    }

    public int getValue() {
        return this.mSeekBarValue;
    }

    public void setValue(int seekBarValue) {
        setValueInternal(seekBarValue, true);
    }

    /* access modifiers changed from: package-private */
    public void syncValueInternal(SeekBar seekBar) {
        int seekBarValue = this.mMin + seekBar.getProgress();
        if (seekBarValue == this.mSeekBarValue) {
            return;
        }
        if (callChangeListener(Integer.valueOf(seekBarValue))) {
            setValueInternal(seekBarValue, false);
            return;
        }
        seekBar.setProgress(this.mSeekBarValue - this.mMin);
        updateLabelValue(this.mSeekBarValue);
    }

    /* access modifiers changed from: package-private */
    public void updateLabelValue(int value) {
        TextView textView = this.mSeekBarValueTextView;
        if (textView != null) {
            textView.setText(String.valueOf(value));
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            return superState;
        }
        SavedState myState = new SavedState(superState);
        myState.mSeekBarValue = this.mSeekBarValue;
        myState.mMin = this.mMin;
        myState.mMax = this.mMax;
        return myState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!state.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        this.mSeekBarValue = myState.mSeekBarValue;
        this.mMin = myState.mMin;
        this.mMax = myState.mMax;
        notifyChanged();
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
        int mMax;
        int mMin;
        int mSeekBarValue;

        SavedState(Parcel source) {
            super(source);
            this.mSeekBarValue = source.readInt();
            this.mMin = source.readInt();
            this.mMax = source.readInt();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.mSeekBarValue);
            dest.writeInt(this.mMin);
            dest.writeInt(this.mMax);
        }
    }
}
