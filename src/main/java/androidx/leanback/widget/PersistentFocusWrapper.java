package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;

class PersistentFocusWrapper extends FrameLayout {
    private static final boolean DEBUG = false;
    private static final String TAG = "PersistentFocusWrapper";
    private boolean mPersistFocusVertical = true;
    private int mSelectedPosition = -1;

    public PersistentFocusWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PersistentFocusWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: package-private */
    public int getGrandChildCount() {
        ViewGroup wrapper = (ViewGroup) getChildAt(0);
        if (wrapper == null) {
            return 0;
        }
        return wrapper.getChildCount();
    }

    public void clearSelection() {
        this.mSelectedPosition = -1;
        if (hasFocus()) {
            clearFocus();
        }
    }

    public void persistFocusVertical() {
        this.mPersistFocusVertical = true;
    }

    public void persistFocusHorizontal() {
        this.mPersistFocusVertical = false;
    }

    private boolean shouldPersistFocusFromDirection(int direction) {
        return (this.mPersistFocusVertical && (direction == 33 || direction == 130)) || (!this.mPersistFocusVertical && (direction == 17 || direction == 66));
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        if (hasFocus() || getGrandChildCount() == 0 || !shouldPersistFocusFromDirection(direction)) {
            super.addFocusables(views, direction, focusableMode);
        } else {
            views.add(this);
        }
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void requestChildFocus(android.view.View r3, android.view.View r4) {
        /*
            r2 = this;
            super.requestChildFocus(r3, r4)
            r0 = r4
        L_0x0004:
            if (r0 == 0) goto L_0x0014
            android.view.ViewParent r1 = r0.getParent()
            if (r1 == r3) goto L_0x0014
            android.view.ViewParent r1 = r0.getParent()
            r0 = r1
            android.view.View r0 = (android.view.View) r0
            goto L_0x0004
        L_0x0014:
            if (r0 != 0) goto L_0x0018
            r1 = -1
            goto L_0x001f
        L_0x0018:
            r1 = r3
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            int r1 = r1.indexOfChild(r0)
        L_0x001f:
            r2.mSelectedPosition = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.PersistentFocusWrapper.requestChildFocus(android.view.View, android.view.View):void");
    }

    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        int i;
        ViewGroup wrapper = (ViewGroup) getChildAt(0);
        if (wrapper == null || (i = this.mSelectedPosition) < 0 || i >= getGrandChildCount() || !wrapper.getChildAt(this.mSelectedPosition).requestFocus(direction, previouslyFocusedRect)) {
            return super.requestFocus(direction, previouslyFocusedRect);
        }
        return true;
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int mSelectedPosition;

        SavedState(Parcel in) {
            super(in);
            this.mSelectedPosition = in.readInt();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.mSelectedPosition);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mSelectedPosition = this.mSelectedPosition;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        this.mSelectedPosition = ((SavedState) state).mSelectedPosition;
        super.onRestoreInstanceState(((SavedState) state).getSuperState());
    }
}
