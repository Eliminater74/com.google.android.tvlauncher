package androidx.leanback.widget;

import android.view.View;

public interface GuidedActionAutofillSupport {

    void setOnAutofillListener(OnAutofillListener onAutofillListener);

    public interface OnAutofillListener {
        void onAutofill(View view);
    }
}
