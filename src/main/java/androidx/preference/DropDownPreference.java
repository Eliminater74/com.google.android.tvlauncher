package androidx.preference;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class DropDownPreference extends ListPreference {
    private final ArrayAdapter mAdapter;
    private final Context mContext;
    private final AdapterView.OnItemSelectedListener mItemSelectedListener;
    private Spinner mSpinner;

    public DropDownPreference(Context context) {
        this(context, null);
    }

    public DropDownPreference(Context context, AttributeSet attrs) {
        this(context, attrs, C0731R.attr.dropdownPreferenceStyle);
    }

    public DropDownPreference(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, 0);
    }

    public DropDownPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {
                if (position >= 0) {
                    String value = DropDownPreference.this.getEntryValues()[position].toString();
                    if (!value.equals(DropDownPreference.this.getValue()) && DropDownPreference.this.callChangeListener(value)) {
                        DropDownPreference.this.setValue(value);
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        this.mContext = context;
        this.mAdapter = createAdapter();
        updateEntries();
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        this.mSpinner.performClick();
    }

    public void setEntries(@NonNull CharSequence[] entries) {
        super.setEntries(entries);
        updateEntries();
    }

    /* access modifiers changed from: protected */
    public ArrayAdapter createAdapter() {
        return new ArrayAdapter(this.mContext, 17367049);
    }

    private void updateEntries() {
        this.mAdapter.clear();
        if (getEntries() != null) {
            for (CharSequence c : getEntries()) {
                this.mAdapter.add(c.toString());
            }
        }
    }

    public void setValueIndex(int index) {
        setValue(getEntryValues()[index].toString());
    }

    /* access modifiers changed from: protected */
    public void notifyChanged() {
        super.notifyChanged();
        ArrayAdapter arrayAdapter = this.mAdapter;
        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }
    }

    public void onBindViewHolder(PreferenceViewHolder view) {
        this.mSpinner = (Spinner) view.itemView.findViewById(C0731R.C0733id.spinner);
        this.mSpinner.setAdapter((SpinnerAdapter) this.mAdapter);
        this.mSpinner.setOnItemSelectedListener(this.mItemSelectedListener);
        this.mSpinner.setSelection(findSpinnerIndexOfValue(getValue()));
        super.onBindViewHolder(view);
    }

    private int findSpinnerIndexOfValue(String value) {
        CharSequence[] entryValues = getEntryValues();
        if (value == null || entryValues == null) {
            return -1;
        }
        for (int i = entryValues.length - 1; i >= 0; i--) {
            if (entryValues[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}
