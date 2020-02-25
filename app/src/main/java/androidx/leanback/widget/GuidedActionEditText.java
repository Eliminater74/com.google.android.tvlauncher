package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.p001v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillValue;
import android.widget.EditText;
import android.widget.TextView;

public class GuidedActionEditText extends EditText implements ImeKeyMonitor, GuidedActionAutofillSupport {
    private final Drawable mNoPaddingDrawable;
    private final Drawable mSavedBackground;
    private GuidedActionAutofillSupport.OnAutofillListener mAutofillListener;
    private ImeKeyMonitor.ImeKeyListener mKeyListener;

    public GuidedActionEditText(Context ctx) {
        this(ctx, null);
    }

    public GuidedActionEditText(Context ctx, AttributeSet attrs) {
        this(ctx, attrs, 16842862);
    }

    public GuidedActionEditText(Context ctx, AttributeSet attrs, int defStyleAttr) {
        super(ctx, attrs, defStyleAttr);
        this.mSavedBackground = getBackground();
        this.mNoPaddingDrawable = new NoPaddingDrawable();
        setBackground(this.mNoPaddingDrawable);
    }

    public void setImeKeyListener(ImeKeyMonitor.ImeKeyListener listener) {
        this.mKeyListener = listener;
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        boolean result = false;
        ImeKeyMonitor.ImeKeyListener imeKeyListener = this.mKeyListener;
        if (imeKeyListener != null) {
            result = imeKeyListener.onKeyPreIme(this, keyCode, event);
        }
        if (!result) {
            return super.onKeyPreIme(keyCode, event);
        }
        return result;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName((isFocused() ? EditText.class : TextView.class).getName());
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            setBackground(this.mSavedBackground);
        } else {
            setBackground(this.mNoPaddingDrawable);
        }
        if (!focused) {
            setFocusable(false);
        }
    }

    public int getAutofillType() {
        return 1;
    }

    public void setOnAutofillListener(GuidedActionAutofillSupport.OnAutofillListener autofillListener) {
        this.mAutofillListener = autofillListener;
    }

    public void autofill(AutofillValue values) {
        super.autofill(values);
        GuidedActionAutofillSupport.OnAutofillListener onAutofillListener = this.mAutofillListener;
        if (onAutofillListener != null) {
            onAutofillListener.onAutofill(this);
        }
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, actionModeCallback));
    }

    static final class NoPaddingDrawable extends Drawable {
        NoPaddingDrawable() {
        }

        public boolean getPadding(Rect padding) {
            padding.set(0, 0, 0, 0);
            return true;
        }

        public void draw(Canvas canvas) {
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        public int getOpacity() {
            return -2;
        }
    }
}
