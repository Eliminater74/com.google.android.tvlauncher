package androidx.leanback.preference;

import android.os.Build;
import android.os.Bundle;
import android.support.p001v4.app.Fragment;
import androidx.preference.DialogPreference;

public class LeanbackPreferenceDialogFragmentCompat extends Fragment {
    public static final String ARG_KEY = "key";
    private DialogPreference mPreference;

    public LeanbackPreferenceDialogFragmentCompat() {
        if (Build.VERSION.SDK_INT >= 21) {
            LeanbackPreferenceFragmentTransitionHelperApi21.addTransitions(this);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment rawFragment = getTargetFragment();
        if (!(rawFragment instanceof DialogPreference.TargetFragment)) {
            throw new IllegalStateException("Target fragment " + rawFragment + " must implement TargetFragment interface");
        }
    }

    public DialogPreference getPreference() {
        if (this.mPreference == null) {
            this.mPreference = (DialogPreference) ((DialogPreference.TargetFragment) getTargetFragment()).findPreference(getArguments().getString("key"));
        }
        return this.mPreference;
    }
}
