package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.leanback.C0364R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class PlaybackTransportRowView extends LinearLayout {
    private OnUnhandledKeyListener mOnUnhandledKeyListener;

    public PlaybackTransportRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaybackTransportRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: package-private */
    public OnUnhandledKeyListener getOnUnhandledKeyListener() {
        return this.mOnUnhandledKeyListener;
    }

    /* access modifiers changed from: package-private */
    public void setOnUnhandledKeyListener(OnUnhandledKeyListener listener) {
        this.mOnUnhandledKeyListener = listener;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (super.dispatchKeyEvent(event)) {
            return true;
        }
        OnUnhandledKeyListener onUnhandledKeyListener = this.mOnUnhandledKeyListener;
        if (onUnhandledKeyListener == null || !onUnhandledKeyListener.onUnhandledKey(event)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        View focused = findFocus();
        if (focused != null && focused.requestFocus(direction, previouslyFocusedRect)) {
            return true;
        }
        View progress = findViewById(C0364R.C0366id.playback_progress);
        if (progress == null || !progress.isFocusable() || !progress.requestFocus(direction, previouslyFocusedRect)) {
            return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
        }
        return true;
    }

    public View focusSearch(View focused, int direction) {
        View view;
        if (focused != null) {
            if (direction == 33) {
                for (int index = indexOfChild(getFocusedChild()) - 1; index >= 0; index--) {
                    View view2 = getChildAt(index);
                    if (view2.hasFocusable()) {
                        return view2;
                    }
                }
            } else if (direction == 130) {
                int index2 = indexOfChild(getFocusedChild());
                do {
                    index2++;
                    if (index2 < getChildCount()) {
                        view = getChildAt(index2);
                    }
                } while (!view.hasFocusable());
                return view;
            } else if ((direction == 17 || direction == 66) && (getFocusedChild() instanceof ViewGroup)) {
                return FocusFinder.getInstance().findNextFocus((ViewGroup) getFocusedChild(), focused, direction);
            }
        }
        return super.focusSearch(focused, direction);
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public interface OnUnhandledKeyListener {
        boolean onUnhandledKey(KeyEvent keyEvent);
    }
}
