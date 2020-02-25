package android.support.p004v7.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.view.TintableBackgroundView;
import android.support.p001v4.view.ViewCompat;
import android.support.p004v7.app.AlertDialog;
import android.support.p004v7.appcompat.C0233R;
import android.support.p004v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

/* renamed from: android.support.v7.widget.AppCompatSpinner */
public class AppCompatSpinner extends Spinner implements TintableBackgroundView {
    private static final int[] ATTRS_ANDROID_SPINNERMODE = {16843505};
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final int MODE_DIALOG = 0;
    private static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "AppCompatSpinner";
    final Rect mTempRect;
    private final AppCompatBackgroundHelper mBackgroundTintHelper;
    private final Context mPopupContext;
    private final boolean mPopupSet;
    int mDropDownWidth;
    private ForwardingListener mForwardingListener;
    private SpinnerPopup mPopup;
    private SpinnerAdapter mTempAdapter;

    public AppCompatSpinner(Context context) {
        this(context, (AttributeSet) null);
    }

    public AppCompatSpinner(Context context, int mode) {
        this(context, null, C0233R.attr.spinnerStyle, mode);
    }

    public AppCompatSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, C0233R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        this(context, attrs, defStyleAttr, mode, null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005a, code lost:
        if (r2 == null) goto L_0x0063;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AppCompatSpinner(android.content.Context r7, android.util.AttributeSet r8, int r9, int r10, android.content.res.Resources.Theme r11) {
        /*
            r6 = this;
            r6.<init>(r7, r8, r9)
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r6.mTempRect = r0
            int[] r0 = android.support.p004v7.appcompat.C0233R.styleable.Spinner
            r1 = 0
            android.support.v7.widget.TintTypedArray r0 = android.support.p004v7.widget.TintTypedArray.obtainStyledAttributes(r7, r8, r0, r9, r1)
            android.support.v7.widget.AppCompatBackgroundHelper r2 = new android.support.v7.widget.AppCompatBackgroundHelper
            r2.<init>(r6)
            r6.mBackgroundTintHelper = r2
            if (r11 == 0) goto L_0x0022
            android.support.v7.view.ContextThemeWrapper r2 = new android.support.v7.view.ContextThemeWrapper
            r2.<init>(r7, r11)
            r6.mPopupContext = r2
            goto L_0x0034
        L_0x0022:
            int r2 = android.support.p004v7.appcompat.C0233R.styleable.Spinner_popupTheme
            int r2 = r0.getResourceId(r2, r1)
            if (r2 == 0) goto L_0x0032
            android.support.v7.view.ContextThemeWrapper r3 = new android.support.v7.view.ContextThemeWrapper
            r3.<init>(r7, r2)
            r6.mPopupContext = r3
            goto L_0x0034
        L_0x0032:
            r6.mPopupContext = r7
        L_0x0034:
            r2 = -1
            if (r10 != r2) goto L_0x0063
            r2 = 0
            int[] r3 = android.support.p004v7.widget.AppCompatSpinner.ATTRS_ANDROID_SPINNERMODE     // Catch:{ Exception -> 0x0051 }
            android.content.res.TypedArray r3 = r7.obtainStyledAttributes(r8, r3, r9, r1)     // Catch:{ Exception -> 0x0051 }
            r2 = r3
            boolean r3 = r2.hasValue(r1)     // Catch:{ Exception -> 0x0051 }
            if (r3 == 0) goto L_0x004a
            int r3 = r2.getInt(r1, r1)     // Catch:{ Exception -> 0x0051 }
            r10 = r3
        L_0x004a:
        L_0x004b:
            r2.recycle()
            goto L_0x0063
        L_0x004f:
            r1 = move-exception
            goto L_0x005d
        L_0x0051:
            r3 = move-exception
            java.lang.String r4 = "AppCompatSpinner"
            java.lang.String r5 = "Could not read android:spinnerMode"
            android.util.Log.i(r4, r5, r3)     // Catch:{ all -> 0x004f }
            if (r2 == 0) goto L_0x0063
            goto L_0x004b
        L_0x005d:
            if (r2 == 0) goto L_0x0062
            r2.recycle()
        L_0x0062:
            throw r1
        L_0x0063:
            r2 = 1
            if (r10 == 0) goto L_0x00a0
            if (r10 == r2) goto L_0x0069
            goto L_0x00b3
        L_0x0069:
            android.support.v7.widget.AppCompatSpinner$DropdownPopup r3 = new android.support.v7.widget.AppCompatSpinner$DropdownPopup
            android.content.Context r4 = r6.mPopupContext
            r3.<init>(r4, r8, r9)
            android.content.Context r4 = r6.mPopupContext
            int[] r5 = android.support.p004v7.appcompat.C0233R.styleable.Spinner
            android.support.v7.widget.TintTypedArray r1 = android.support.p004v7.widget.TintTypedArray.obtainStyledAttributes(r4, r8, r5, r9, r1)
            int r4 = android.support.p004v7.appcompat.C0233R.styleable.Spinner_android_dropDownWidth
            r5 = -2
            int r4 = r1.getLayoutDimension(r4, r5)
            r6.mDropDownWidth = r4
            int r4 = android.support.p004v7.appcompat.C0233R.styleable.Spinner_android_popupBackground
            android.graphics.drawable.Drawable r4 = r1.getDrawable(r4)
            r3.setBackgroundDrawable(r4)
            int r4 = android.support.p004v7.appcompat.C0233R.styleable.Spinner_android_prompt
            java.lang.String r4 = r0.getString(r4)
            r3.setPromptText(r4)
            r1.recycle()
            r6.mPopup = r3
            android.support.v7.widget.AppCompatSpinner$1 r4 = new android.support.v7.widget.AppCompatSpinner$1
            r4.<init>(r6, r3)
            r6.mForwardingListener = r4
            goto L_0x00b3
        L_0x00a0:
            android.support.v7.widget.AppCompatSpinner$DialogPopup r1 = new android.support.v7.widget.AppCompatSpinner$DialogPopup
            r1.<init>()
            r6.mPopup = r1
            android.support.v7.widget.AppCompatSpinner$SpinnerPopup r1 = r6.mPopup
            int r3 = android.support.p004v7.appcompat.C0233R.styleable.Spinner_android_prompt
            java.lang.String r3 = r0.getString(r3)
            r1.setPromptText(r3)
        L_0x00b3:
            int r1 = android.support.p004v7.appcompat.C0233R.styleable.Spinner_android_entries
            java.lang.CharSequence[] r1 = r0.getTextArray(r1)
            if (r1 == 0) goto L_0x00cb
            android.widget.ArrayAdapter r3 = new android.widget.ArrayAdapter
            r4 = 17367048(0x1090008, float:2.5162948E-38)
            r3.<init>(r7, r4, r1)
            int r4 = android.support.p004v7.appcompat.C0233R.layout.support_simple_spinner_dropdown_item
            r3.setDropDownViewResource(r4)
            r6.setAdapter(r3)
        L_0x00cb:
            r0.recycle()
            r6.mPopupSet = r2
            android.widget.SpinnerAdapter r2 = r6.mTempAdapter
            if (r2 == 0) goto L_0x00da
            r6.setAdapter(r2)
            r2 = 0
            r6.mTempAdapter = r2
        L_0x00da:
            android.support.v7.widget.AppCompatBackgroundHelper r2 = r6.mBackgroundTintHelper
            r2.loadFromAttributes(r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.widget.AppCompatSpinner.<init>(android.content.Context, android.util.AttributeSet, int, int, android.content.res.Resources$Theme):void");
    }

    public Context getPopupContext() {
        return this.mPopupContext;
    }

    public void setPopupBackgroundDrawable(Drawable background) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setBackgroundDrawable(background);
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setPopupBackgroundDrawable(background);
        }
    }

    public void setPopupBackgroundResource(@DrawableRes int resId) {
        setPopupBackgroundDrawable(AppCompatResources.getDrawable(getPopupContext(), resId));
    }

    public Drawable getPopupBackground() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            return spinnerPopup.getBackground();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getPopupBackground();
        }
        return null;
    }

    public int getDropDownVerticalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            return spinnerPopup.getVerticalOffset();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownVerticalOffset();
        }
        return 0;
    }

    public void setDropDownVerticalOffset(int pixels) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setVerticalOffset(pixels);
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownVerticalOffset(pixels);
        }
    }

    public int getDropDownHorizontalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            return spinnerPopup.getHorizontalOffset();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownHorizontalOffset();
        }
        return 0;
    }

    public void setDropDownHorizontalOffset(int pixels) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setHorizontalOffset(pixels);
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownHorizontalOffset(pixels);
        }
    }

    public int getDropDownWidth() {
        if (this.mPopup != null) {
            return this.mDropDownWidth;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownWidth();
        }
        return 0;
    }

    public void setDropDownWidth(int pixels) {
        if (this.mPopup != null) {
            this.mDropDownWidth = pixels;
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownWidth(pixels);
        }
    }

    public void setAdapter(SpinnerAdapter adapter) {
        if (!this.mPopupSet) {
            this.mTempAdapter = adapter;
            return;
        }
        super.setAdapter(adapter);
        if (this.mPopup != null) {
            Context popupContext = this.mPopupContext;
            if (popupContext == null) {
                popupContext = getContext();
            }
            this.mPopup.setAdapter(new DropDownAdapter(adapter, popupContext.getTheme()));
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null && spinnerPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        ForwardingListener forwardingListener = this.mForwardingListener;
        if (forwardingListener == null || !forwardingListener.onTouch(this, event)) {
            return super.onTouchEvent(event);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mPopup != null && View.MeasureSpec.getMode(widthMeasureSpec) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), compatMeasureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(widthMeasureSpec)), getMeasuredHeight());
        }
    }

    public boolean performClick() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup == null) {
            return super.performClick();
        }
        if (spinnerPopup.isShowing()) {
            return true;
        }
        showPopup();
        return true;
    }

    public CharSequence getPrompt() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getHintText() : super.getPrompt();
    }

    public void setPrompt(CharSequence prompt) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setPromptText(prompt);
        } else {
            super.setPrompt(prompt);
        }
    }

    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundResource(resId);
        }
    }

    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundDrawable(background);
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ColorStateList getSupportBackgroundTintList() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSupportBackgroundTintList(@Nullable ColorStateList tint) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.setSupportBackgroundTintList(tint);
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.setSupportBackgroundTintMode(tintMode);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.applySupportBackgroundTint();
        }
    }

    /* access modifiers changed from: package-private */
    public int compatMeasureContentWidth(SpinnerAdapter adapter, Drawable background) {
        if (adapter == null) {
            return 0;
        }
        int width = 0;
        View itemView = null;
        int itemType = 0;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int start = Math.max(0, getSelectedItemPosition());
        int end = Math.min(adapter.getCount(), start + 15);
        for (int i = Math.max(0, start - (15 - (end - start))); i < end; i++) {
            int positionType = adapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }
            itemView = adapter.getView(i, itemView, this);
            if (itemView.getLayoutParams() == null) {
                itemView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            itemView.measure(widthMeasureSpec, heightMeasureSpec);
            width = Math.max(width, itemView.getMeasuredWidth());
        }
        if (background == null) {
            return width;
        }
        background.getPadding(this.mTempRect);
        return width + this.mTempRect.left + this.mTempRect.right;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final SpinnerPopup getInternalPopup() {
        return this.mPopup;
    }

    /* access modifiers changed from: package-private */
    public void showPopup() {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mPopup.show(getTextDirection(), getTextAlignment());
        } else {
            this.mPopup.show(-1, -1);
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        SpinnerPopup spinnerPopup = this.mPopup;
        ss.mShowDropdown = spinnerPopup != null && spinnerPopup.isShowing();
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        ViewTreeObserver vto;
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.mShowDropdown && (vto = getViewTreeObserver()) != null) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    if (!AppCompatSpinner.this.getInternalPopup().isShowing()) {
                        AppCompatSpinner.this.showPopup();
                    }
                    ViewTreeObserver vto = AppCompatSpinner.this.getViewTreeObserver();
                    if (vto == null) {
                        return;
                    }
                    if (Build.VERSION.SDK_INT >= 16) {
                        vto.removeOnGlobalLayoutListener(this);
                    } else {
                        vto.removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
    }

    @VisibleForTesting
            /* renamed from: android.support.v7.widget.AppCompatSpinner$SpinnerPopup */
    interface SpinnerPopup {
        void dismiss();

        Drawable getBackground();

        CharSequence getHintText();

        int getHorizontalOffset();

        void setHorizontalOffset(int i);

        int getVerticalOffset();

        void setVerticalOffset(int i);

        boolean isShowing();

        void setAdapter(ListAdapter listAdapter);

        void setBackgroundDrawable(Drawable drawable);

        void setPromptText(CharSequence charSequence);

        void show(int i, int i2);
    }

    /* renamed from: android.support.v7.widget.AppCompatSpinner$SavedState */
    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean mShowDropdown;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            this.mShowDropdown = in.readByte() != 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeByte(this.mShowDropdown ? (byte) 1 : 0);
        }
    }

    /* renamed from: android.support.v7.widget.AppCompatSpinner$DropDownAdapter */
    private static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        public DropDownAdapter(@Nullable SpinnerAdapter adapter, @Nullable Resources.Theme dropDownTheme) {
            this.mAdapter = adapter;
            if (adapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter) adapter;
            }
            if (dropDownTheme == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 23 && (adapter instanceof ThemedSpinnerAdapter)) {
                ThemedSpinnerAdapter themedAdapter = (ThemedSpinnerAdapter) adapter;
                if (themedAdapter.getDropDownViewTheme() != dropDownTheme) {
                    themedAdapter.setDropDownViewTheme(dropDownTheme);
                }
            } else if (adapter instanceof ThemedSpinnerAdapter) {
                ThemedSpinnerAdapter themedAdapter2 = (ThemedSpinnerAdapter) adapter;
                if (themedAdapter2.getDropDownViewTheme() == null) {
                    themedAdapter2.setDropDownViewTheme(dropDownTheme);
                }
            }
        }

        public int getCount() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return 0;
            }
            return spinnerAdapter.getCount();
        }

        public Object getItem(int position) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getItem(position);
        }

        public long getItemId(int position) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return -1;
            }
            return spinnerAdapter.getItemId(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return getDropDownView(position, convertView, parent);
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(position, convertView, parent);
        }

        public boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            return spinnerAdapter != null && spinnerAdapter.hasStableIds();
        }

        public void registerDataSetObserver(DataSetObserver observer) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(observer);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(observer);
            }
        }

        public boolean areAllItemsEnabled() {
            ListAdapter adapter = this.mListAdapter;
            if (adapter != null) {
                return adapter.areAllItemsEnabled();
            }
            return true;
        }

        public boolean isEnabled(int position) {
            ListAdapter adapter = this.mListAdapter;
            if (adapter != null) {
                return adapter.isEnabled(position);
            }
            return true;
        }

        public int getItemViewType(int position) {
            return 0;
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean isEmpty() {
            return getCount() == 0;
        }
    }

    @VisibleForTesting
            /* renamed from: android.support.v7.widget.AppCompatSpinner$DialogPopup */
    class DialogPopup implements SpinnerPopup, DialogInterface.OnClickListener {
        @VisibleForTesting
        AlertDialog mPopup;
        private ListAdapter mListAdapter;
        private CharSequence mPrompt;

        DialogPopup() {
        }

        public void dismiss() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mPopup = null;
            }
        }

        public boolean isShowing() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog != null) {
                return alertDialog.isShowing();
            }
            return false;
        }

        public void setAdapter(ListAdapter adapter) {
            this.mListAdapter = adapter;
        }

        public void setPromptText(CharSequence hintText) {
            this.mPrompt = hintText;
        }

        public CharSequence getHintText() {
            return this.mPrompt;
        }

        public void show(int textDirection, int textAlignment) {
            if (this.mListAdapter != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AppCompatSpinner.this.getPopupContext());
                CharSequence charSequence = this.mPrompt;
                if (charSequence != null) {
                    builder.setTitle(charSequence);
                }
                this.mPopup = builder.setSingleChoiceItems(this.mListAdapter, AppCompatSpinner.this.getSelectedItemPosition(), this).create();
                ListView listView = this.mPopup.getListView();
                if (Build.VERSION.SDK_INT >= 17) {
                    listView.setTextDirection(textDirection);
                    listView.setTextAlignment(textAlignment);
                }
                this.mPopup.show();
            }
        }

        public void onClick(DialogInterface dialog, int which) {
            AppCompatSpinner.this.setSelection(which);
            if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                AppCompatSpinner.this.performItemClick(null, which, this.mListAdapter.getItemId(which));
            }
            dismiss();
        }

        public void setBackgroundDrawable(Drawable bg) {
            Log.e(AppCompatSpinner.TAG, "Cannot set popup background for MODE_DIALOG, ignoring");
        }

        public Drawable getBackground() {
            return null;
        }

        public int getVerticalOffset() {
            return 0;
        }

        public void setVerticalOffset(int px) {
            Log.e(AppCompatSpinner.TAG, "Cannot set vertical offset for MODE_DIALOG, ignoring");
        }

        public int getHorizontalOffset() {
            return 0;
        }

        public void setHorizontalOffset(int px) {
            Log.e(AppCompatSpinner.TAG, "Cannot set horizontal offset for MODE_DIALOG, ignoring");
        }
    }

    /* renamed from: android.support.v7.widget.AppCompatSpinner$DropdownPopup */
    private class DropdownPopup extends ListPopupWindow implements SpinnerPopup {
        private final Rect mVisibleRect = new Rect();
        ListAdapter mAdapter;
        private CharSequence mHintText;

        public DropdownPopup(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setAnchorView(AppCompatSpinner.this);
            setModal(true);
            setPromptPosition(0);
            setOnItemClickListener(new AdapterView.OnItemClickListener(AppCompatSpinner.this) {
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    AppCompatSpinner.this.setSelection(position);
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        AppCompatSpinner.this.performItemClick(v, position, DropdownPopup.this.mAdapter.getItemId(position));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        public void setAdapter(ListAdapter adapter) {
            super.setAdapter(adapter);
            this.mAdapter = adapter;
        }

        public CharSequence getHintText() {
            return this.mHintText;
        }

        public void setPromptText(CharSequence hintText) {
            this.mHintText = hintText;
        }

        /* access modifiers changed from: package-private */
        public void computeContentWidth() {
            int hOffset;
            Drawable background = getBackground();
            int hOffset2 = 0;
            if (background != null) {
                background.getPadding(AppCompatSpinner.this.mTempRect);
                hOffset2 = ViewUtils.isLayoutRtl(AppCompatSpinner.this) ? AppCompatSpinner.this.mTempRect.right : -AppCompatSpinner.this.mTempRect.left;
            } else {
                Rect rect = AppCompatSpinner.this.mTempRect;
                AppCompatSpinner.this.mTempRect.right = 0;
                rect.left = 0;
            }
            int spinnerPaddingLeft = AppCompatSpinner.this.getPaddingLeft();
            int spinnerPaddingRight = AppCompatSpinner.this.getPaddingRight();
            int spinnerWidth = AppCompatSpinner.this.getWidth();
            if (AppCompatSpinner.this.mDropDownWidth == -2) {
                int contentWidth = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter) this.mAdapter, getBackground());
                int contentWidthLimit = (AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.this.mTempRect.left) - AppCompatSpinner.this.mTempRect.right;
                if (contentWidth > contentWidthLimit) {
                    contentWidth = contentWidthLimit;
                }
                setContentWidth(Math.max(contentWidth, (spinnerWidth - spinnerPaddingLeft) - spinnerPaddingRight));
            } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
                setContentWidth((spinnerWidth - spinnerPaddingLeft) - spinnerPaddingRight);
            } else {
                setContentWidth(AppCompatSpinner.this.mDropDownWidth);
            }
            if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
                hOffset = hOffset2 + ((spinnerWidth - spinnerPaddingRight) - getWidth());
            } else {
                hOffset = hOffset2 + spinnerPaddingLeft;
            }
            setHorizontalOffset(hOffset);
        }

        public void show(int textDirection, int textAlignment) {
            ViewTreeObserver vto;
            boolean wasShowing = isShowing();
            computeContentWidth();
            setInputMethodMode(2);
            super.show();
            ListView listView = getListView();
            listView.setChoiceMode(1);
            if (Build.VERSION.SDK_INT >= 17) {
                listView.setTextDirection(textDirection);
                listView.setTextAlignment(textAlignment);
            }
            setSelection(AppCompatSpinner.this.getSelectedItemPosition());
            if (!wasShowing && (vto = AppCompatSpinner.this.getViewTreeObserver()) != null) {
                final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        DropdownPopup dropdownPopup = DropdownPopup.this;
                        if (!dropdownPopup.isVisibleToUser(AppCompatSpinner.this)) {
                            DropdownPopup.this.dismiss();
                            return;
                        }
                        DropdownPopup.this.computeContentWidth();
                        DropdownPopup.super.show();
                    }
                };
                vto.addOnGlobalLayoutListener(layoutListener);
                setOnDismissListener(new PopupWindow.OnDismissListener() {
                    public void onDismiss() {
                        ViewTreeObserver vto = AppCompatSpinner.this.getViewTreeObserver();
                        if (vto != null) {
                            vto.removeGlobalOnLayoutListener(layoutListener);
                        }
                    }
                });
            }
        }

        /* access modifiers changed from: package-private */
        public boolean isVisibleToUser(View view) {
            return ViewCompat.isAttachedToWindow(view) && view.getGlobalVisibleRect(this.mVisibleRect);
        }
    }
}
