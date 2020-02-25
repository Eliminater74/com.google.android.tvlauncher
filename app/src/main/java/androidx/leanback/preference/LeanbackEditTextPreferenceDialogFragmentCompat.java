package androidx.leanback.preference;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.preference.DialogPreference;
import androidx.preference.EditTextPreference;

public class LeanbackEditTextPreferenceDialogFragmentCompat extends LeanbackPreferenceDialogFragmentCompat {
    public static final String EXTRA_IME_OPTIONS = "ime_option";
    public static final String EXTRA_INPUT_TYPE = "input_type";
    private static final int DEFAULT_IME_OPTIONS = 2;
    private static final int DEFAULT_INPUT_TYPE = 1;
    private static final String SAVE_STATE_IME_OPTIONS = "LeanbackEditPreferenceDialog.imeOptions";
    private static final String SAVE_STATE_INPUT_TYPE = "LeanbackEditPreferenceDialog.inputType";
    private static final String SAVE_STATE_MESSAGE = "LeanbackEditPreferenceDialog.message";
    private static final String SAVE_STATE_TEXT = "LeanbackEditPreferenceDialog.text";
    private static final String SAVE_STATE_TITLE = "LeanbackEditPreferenceDialog.title";
    private CharSequence mDialogMessage;
    private CharSequence mDialogTitle;
    private int mImeOptions;
    private int mInputType;
    private CharSequence mText;

    public static LeanbackEditTextPreferenceDialogFragmentCompat newInstance(String key) {
        Bundle args = new Bundle(1);
        args.putString("key", key);
        LeanbackEditTextPreferenceDialogFragmentCompat fragment = new LeanbackEditTextPreferenceDialogFragmentCompat();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            DialogPreference preference = getPreference();
            this.mDialogTitle = preference.getDialogTitle();
            this.mDialogMessage = preference.getDialogMessage();
            if (preference instanceof EditTextPreference) {
                this.mDialogTitle = preference.getDialogTitle();
                this.mDialogMessage = preference.getDialogMessage();
                this.mText = ((EditTextPreference) preference).getText();
                this.mInputType = preference.getExtras().getInt(EXTRA_INPUT_TYPE, 1);
                this.mImeOptions = preference.getExtras().getInt(EXTRA_IME_OPTIONS, 2);
                return;
            }
            throw new IllegalArgumentException("Preference must be a EditTextPreference");
        }
        this.mDialogTitle = savedInstanceState.getCharSequence(SAVE_STATE_TITLE);
        this.mDialogMessage = savedInstanceState.getCharSequence(SAVE_STATE_MESSAGE);
        this.mText = savedInstanceState.getCharSequence(SAVE_STATE_TEXT);
        this.mInputType = savedInstanceState.getInt(SAVE_STATE_INPUT_TYPE, 1);
        this.mImeOptions = savedInstanceState.getInt(SAVE_STATE_IME_OPTIONS, 2);
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(SAVE_STATE_TITLE, this.mDialogTitle);
        outState.putCharSequence(SAVE_STATE_MESSAGE, this.mDialogMessage);
        outState.putCharSequence(SAVE_STATE_TEXT, this.mText);
        outState.putInt(SAVE_STATE_INPUT_TYPE, this.mInputType);
        outState.putInt(SAVE_STATE_IME_OPTIONS, this.mImeOptions);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TypedValue tv = new TypedValue();
        getActivity().getTheme().resolveAttribute(C0572R.attr.preferenceTheme, tv, true);
        int theme = tv.resourceId;
        if (theme == 0) {
            theme = C0572R.style.PreferenceThemeOverlayLeanback;
        }
        View view = inflater.cloneInContext(new ContextThemeWrapper(getActivity(), theme)).inflate(C0572R.layout.leanback_edit_preference_fragment, container, false);
        if (!TextUtils.isEmpty(this.mDialogTitle)) {
            ((TextView) view.findViewById(C0572R.C0574id.decor_title)).setText(this.mDialogTitle);
        }
        if (!TextUtils.isEmpty(this.mDialogMessage)) {
            TextView messageView = (TextView) view.findViewById(16908299);
            messageView.setVisibility(0);
            messageView.setText(this.mDialogMessage);
        }
        EditText editText = (EditText) view.findViewById(16908291);
        editText.setInputType(this.mInputType);
        editText.setImeOptions(this.mImeOptions);
        if (!TextUtils.isEmpty(this.mText)) {
            editText.setText(this.mText);
        }
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId != 6 && actionId != 2 && actionId != 3 && actionId != 5 && actionId != 4) {
                    return false;
                }
                ((InputMethodManager) LeanbackEditTextPreferenceDialogFragmentCompat.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(textView.getWindowToken(), 0);
                ((EditTextPreference) LeanbackEditTextPreferenceDialogFragmentCompat.this.getPreference()).setText(textView.getText().toString());
                LeanbackEditTextPreferenceDialogFragmentCompat.this.getFragmentManager().popBackStack();
                return true;
            }
        });
        return view;
    }

    public void onStart() {
        super.onStart();
        EditText editText = (EditText) getView().findViewById(16908291);
        editText.requestFocus();
        ((InputMethodManager) getActivity().getSystemService("input_method")).showSoftInput(editText, 0);
    }
}
