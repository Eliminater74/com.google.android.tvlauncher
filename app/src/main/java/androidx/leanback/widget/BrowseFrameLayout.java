package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

public class BrowseFrameLayout extends FrameLayout {
    private OnFocusSearchListener mListener;
    private OnChildFocusListener mOnChildFocusListener;
    private View.OnKeyListener mOnDispatchKeyListener;

    public BrowseFrameLayout(Context context) {
        this(context, null, 0);
    }

    public BrowseFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrowseFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public OnFocusSearchListener getOnFocusSearchListener() {
        return this.mListener;
    }

    public void setOnFocusSearchListener(OnFocusSearchListener listener) {
        this.mListener = listener;
    }

    public OnChildFocusListener getOnChildFocusListener() {
        return this.mOnChildFocusListener;
    }

    public void setOnChildFocusListener(OnChildFocusListener listener) {
        this.mOnChildFocusListener = listener;
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        OnChildFocusListener onChildFocusListener = this.mOnChildFocusListener;
        if (onChildFocusListener == null || !onChildFocusListener.onRequestFocusInDescendants(direction, previouslyFocusedRect)) {
            return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
        }
        return true;
    }

    public View focusSearch(View focused, int direction) {
        View view;
        OnFocusSearchListener onFocusSearchListener = this.mListener;
        if (onFocusSearchListener == null || (view = onFocusSearchListener.onFocusSearch(focused, direction)) == null) {
            return super.focusSearch(focused, direction);
        }
        return view;
    }

    public void requestChildFocus(View child, View focused) {
        OnChildFocusListener onChildFocusListener = this.mOnChildFocusListener;
        if (onChildFocusListener != null) {
            onChildFocusListener.onRequestChildFocus(child, focused);
        }
        super.requestChildFocus(child, focused);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean consumed = super.dispatchKeyEvent(event);
        View.OnKeyListener onKeyListener = this.mOnDispatchKeyListener;
        if (onKeyListener == null || consumed) {
            return consumed;
        }
        return onKeyListener.onKey(getRootView(), event.getKeyCode(), event);
    }

    public void setOnDispatchKeyListener(View.OnKeyListener listener) {
        this.mOnDispatchKeyListener = listener;
    }

    public interface OnChildFocusListener {
        void onRequestChildFocus(View view, View view2);

        boolean onRequestFocusInDescendants(int i, Rect rect);
    }

    public interface OnFocusSearchListener {
        View onFocusSearch(View view, int i);
    }
}
