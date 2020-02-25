package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.leanback.C0364R;

import java.util.List;

public class SearchEditText extends StreamingTextView {
    private static final boolean DEBUG = false;
    private static final String TAG = SearchEditText.class.getSimpleName();
    OnKeyboardDismissListener mKeyboardDismissListener;

    public SearchEditText(Context context) {
        this(context, null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs, C0364R.style.TextAppearance_Leanback_SearchTextEdit);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public /* bridge */ /* synthetic */ void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    public /* bridge */ /* synthetic */ void reset() {
        super.reset();
    }

    public /* bridge */ /* synthetic */ void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }

    public /* bridge */ /* synthetic */ void setFinalRecognizedText(CharSequence charSequence) {
        super.setFinalRecognizedText(charSequence);
    }

    public /* bridge */ /* synthetic */ void updateRecognizedText(String str, String str2) {
        super.updateRecognizedText(str, str2);
    }

    public /* bridge */ /* synthetic */ void updateRecognizedText(String str, List list) {
        super.updateRecognizedText(str, list);
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == 4 && this.mKeyboardDismissListener != null) {
            post(new Runnable() {
                public void run() {
                    if (SearchEditText.this.mKeyboardDismissListener != null) {
                        SearchEditText.this.mKeyboardDismissListener.onKeyboardDismiss();
                    }
                }
            });
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void setOnKeyboardDismissListener(OnKeyboardDismissListener listener) {
        this.mKeyboardDismissListener = listener;
    }

    public interface OnKeyboardDismissListener {
        void onKeyboardDismiss();
    }
}
