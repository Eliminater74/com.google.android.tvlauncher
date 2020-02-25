package androidx.leanback.preference;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.util.ArraySet;
import android.support.p004v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.TextView;

import androidx.leanback.widget.VerticalGridView;
import androidx.preference.DialogPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class LeanbackListPreferenceDialogFragment extends LeanbackPreferenceDialogFragment {
    private static final String SAVE_STATE_ENTRIES = "LeanbackListPreferenceDialogFragment.entries";
    private static final String SAVE_STATE_ENTRY_VALUES = "LeanbackListPreferenceDialogFragment.entryValues";
    private static final String SAVE_STATE_INITIAL_SELECTION = "LeanbackListPreferenceDialogFragment.initialSelection";
    private static final String SAVE_STATE_INITIAL_SELECTIONS = "LeanbackListPreferenceDialogFragment.initialSelections";
    private static final String SAVE_STATE_IS_MULTI = "LeanbackListPreferenceDialogFragment.isMulti";
    private static final String SAVE_STATE_MESSAGE = "LeanbackListPreferenceDialogFragment.message";
    private static final String SAVE_STATE_TITLE = "LeanbackListPreferenceDialogFragment.title";
    Set<String> mInitialSelections;
    private CharSequence mDialogMessage;
    private CharSequence mDialogTitle;
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private String mInitialSelection;
    private boolean mMulti;

    public static LeanbackListPreferenceDialogFragment newInstanceSingle(String key) {
        Bundle args = new Bundle(1);
        args.putString("key", key);
        LeanbackListPreferenceDialogFragment fragment = new LeanbackListPreferenceDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static LeanbackListPreferenceDialogFragment newInstanceMulti(String key) {
        Bundle args = new Bundle(1);
        args.putString("key", key);
        LeanbackListPreferenceDialogFragment fragment = new LeanbackListPreferenceDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i = 0;
        if (savedInstanceState == null) {
            DialogPreference preference = getPreference();
            this.mDialogTitle = preference.getDialogTitle();
            this.mDialogMessage = preference.getDialogMessage();
            if (preference instanceof ListPreference) {
                this.mMulti = false;
                this.mEntries = ((ListPreference) preference).getEntries();
                this.mEntryValues = ((ListPreference) preference).getEntryValues();
                this.mInitialSelection = ((ListPreference) preference).getValue();
            } else if (preference instanceof MultiSelectListPreference) {
                this.mMulti = true;
                this.mEntries = ((MultiSelectListPreference) preference).getEntries();
                this.mEntryValues = ((MultiSelectListPreference) preference).getEntryValues();
                this.mInitialSelections = ((MultiSelectListPreference) preference).getValues();
            } else {
                throw new IllegalArgumentException("Preference must be a ListPreference or MultiSelectListPreference");
            }
        } else {
            this.mDialogTitle = savedInstanceState.getCharSequence(SAVE_STATE_TITLE);
            this.mDialogMessage = savedInstanceState.getCharSequence(SAVE_STATE_MESSAGE);
            this.mMulti = savedInstanceState.getBoolean(SAVE_STATE_IS_MULTI);
            this.mEntries = savedInstanceState.getCharSequenceArray(SAVE_STATE_ENTRIES);
            this.mEntryValues = savedInstanceState.getCharSequenceArray(SAVE_STATE_ENTRY_VALUES);
            if (this.mMulti) {
                String[] initialSelections = savedInstanceState.getStringArray(SAVE_STATE_INITIAL_SELECTIONS);
                if (initialSelections != null) {
                    i = initialSelections.length;
                }
                this.mInitialSelections = new ArraySet(i);
                if (initialSelections != null) {
                    Collections.addAll(this.mInitialSelections, initialSelections);
                    return;
                }
                return;
            }
            this.mInitialSelection = savedInstanceState.getString(SAVE_STATE_INITIAL_SELECTION);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(SAVE_STATE_TITLE, this.mDialogTitle);
        outState.putCharSequence(SAVE_STATE_MESSAGE, this.mDialogMessage);
        outState.putBoolean(SAVE_STATE_IS_MULTI, this.mMulti);
        outState.putCharSequenceArray(SAVE_STATE_ENTRIES, this.mEntries);
        outState.putCharSequenceArray(SAVE_STATE_ENTRY_VALUES, this.mEntryValues);
        if (this.mMulti) {
            Set<String> set = this.mInitialSelections;
            outState.putStringArray(SAVE_STATE_INITIAL_SELECTIONS, (String[]) set.toArray(new String[set.size()]));
            return;
        }
        outState.putString(SAVE_STATE_INITIAL_SELECTION, this.mInitialSelection);
    }

    /* JADX INFO: Multiple debug info for r8v1 java.lang.CharSequence: [D('titleView' android.widget.TextView), D('message' java.lang.CharSequence)] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TypedValue tv = new TypedValue();
        getActivity().getTheme().resolveAttribute(C0572R.attr.preferenceTheme, tv, true);
        int theme = tv.resourceId;
        if (theme == 0) {
            theme = C0572R.style.PreferenceThemeOverlayLeanback;
        }
        View view = inflater.cloneInContext(new ContextThemeWrapper(getActivity(), theme)).inflate(C0572R.layout.leanback_list_preference_fragment, container, false);
        VerticalGridView verticalGridView = (VerticalGridView) view.findViewById(16908298);
        verticalGridView.setWindowAlignment(3);
        verticalGridView.setFocusScrollStrategy(0);
        verticalGridView.setAdapter(onCreateAdapter());
        verticalGridView.requestFocus();
        CharSequence title = this.mDialogTitle;
        if (!TextUtils.isEmpty(title)) {
            ((TextView) view.findViewById(C0572R.C0574id.decor_title)).setText(title);
        }
        CharSequence message = this.mDialogMessage;
        if (!TextUtils.isEmpty(message)) {
            TextView messageView = (TextView) view.findViewById(16908299);
            messageView.setVisibility(0);
            messageView.setText(message);
        }
        return view;
    }

    public RecyclerView.Adapter onCreateAdapter() {
        if (this.mMulti) {
            return new AdapterMulti(this.mEntries, this.mEntryValues, this.mInitialSelections);
        }
        return new AdapterSingle(this.mEntries, this.mEntryValues, this.mInitialSelection);
    }

    @Deprecated
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ViewGroup mContainer;
        private final OnItemClickListener mListener;
        private final TextView mTitleView;
        private final Checkable mWidgetView;

        public ViewHolder(@NonNull View view, @NonNull OnItemClickListener listener) {
            super(view);
            this.mWidgetView = (Checkable) view.findViewById(C0572R.C0574id.button);
            this.mContainer = (ViewGroup) view.findViewById(C0572R.C0574id.container);
            this.mTitleView = (TextView) view.findViewById(16908310);
            this.mContainer.setOnClickListener(this);
            this.mListener = listener;
        }

        public Checkable getWidgetView() {
            return this.mWidgetView;
        }

        public TextView getTitleView() {
            return this.mTitleView;
        }

        public ViewGroup getContainer() {
            return this.mContainer;
        }

        public void onClick(View v) {
            this.mListener.onItemClick(this);
        }

        public interface OnItemClickListener {
            void onItemClick(ViewHolder viewHolder);
        }
    }

    @Deprecated
    public class AdapterSingle extends RecyclerView.Adapter<ViewHolder> implements ViewHolder.OnItemClickListener {
        private final CharSequence[] mEntries;
        private final CharSequence[] mEntryValues;
        private CharSequence mSelectedValue;

        public AdapterSingle(CharSequence[] entries, CharSequence[] entryValues, CharSequence selectedValue) {
            this.mEntries = entries;
            this.mEntryValues = entryValues;
            this.mSelectedValue = selectedValue;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
         arg types: [int, android.view.ViewGroup, int]
         candidates:
          ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
          ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0572R.layout.leanback_list_preference_item_single, parent, false), this);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.getWidgetView().setChecked(this.mEntryValues[position].equals(this.mSelectedValue));
            holder.getTitleView().setText(this.mEntries[position]);
        }

        public int getItemCount() {
            return this.mEntries.length;
        }

        public void onItemClick(ViewHolder viewHolder) {
            int index = viewHolder.getAdapterPosition();
            if (index != -1) {
                CharSequence entry = this.mEntryValues[index];
                ListPreference preference = (ListPreference) LeanbackListPreferenceDialogFragment.this.getPreference();
                if (index >= 0) {
                    String value = this.mEntryValues[index].toString();
                    if (preference.callChangeListener(value)) {
                        preference.setValue(value);
                        this.mSelectedValue = entry;
                    }
                }
                LeanbackListPreferenceDialogFragment.this.getFragmentManager().popBackStack();
                notifyDataSetChanged();
            }
        }
    }

    @Deprecated
    public class AdapterMulti extends RecyclerView.Adapter<ViewHolder> implements ViewHolder.OnItemClickListener {
        private final CharSequence[] mEntries;
        private final CharSequence[] mEntryValues;
        private final Set<String> mSelections;

        public AdapterMulti(CharSequence[] entries, CharSequence[] entryValues, Set<String> initialSelections) {
            this.mEntries = entries;
            this.mEntryValues = entryValues;
            this.mSelections = new HashSet(initialSelections);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
         arg types: [int, android.view.ViewGroup, int]
         candidates:
          ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
          ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0572R.layout.leanback_list_preference_item_multi, parent, false), this);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.getWidgetView().setChecked(this.mSelections.contains(this.mEntryValues[position].toString()));
            holder.getTitleView().setText(this.mEntries[position]);
        }

        public int getItemCount() {
            return this.mEntries.length;
        }

        public void onItemClick(ViewHolder viewHolder) {
            int index = viewHolder.getAdapterPosition();
            if (index != -1) {
                String entry = this.mEntryValues[index].toString();
                if (this.mSelections.contains(entry)) {
                    this.mSelections.remove(entry);
                } else {
                    this.mSelections.add(entry);
                }
                MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) LeanbackListPreferenceDialogFragment.this.getPreference();
                if (multiSelectListPreference.callChangeListener(new HashSet(this.mSelections))) {
                    multiSelectListPreference.setValues(new HashSet(this.mSelections));
                    LeanbackListPreferenceDialogFragment.this.mInitialSelections = this.mSelections;
                } else if (this.mSelections.contains(entry)) {
                    this.mSelections.remove(entry);
                } else {
                    this.mSelections.add(entry);
                }
                notifyDataSetChanged();
            }
        }
    }
}
