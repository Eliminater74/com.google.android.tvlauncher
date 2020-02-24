package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.p004v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.leanback.C0364R;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.leanback.widget.VerticalGridView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Picker extends FrameLayout {
    private int mAlphaAnimDuration;
    private final OnChildViewHolderSelectedListener mColumnChangeListener;
    final List<VerticalGridView> mColumnViews;
    ArrayList<PickerColumn> mColumns;
    private Interpolator mDecelerateInterpolator;
    private float mFocusedAlpha;
    private float mInvisibleColumnAlpha;
    private ArrayList<PickerValueListener> mListeners;
    private int mPickerItemLayoutId;
    private int mPickerItemTextViewId;
    private ViewGroup mPickerView;
    private int mSelectedColumn;
    private List<CharSequence> mSeparators;
    private float mUnfocusedAlpha;
    private float mVisibleColumnAlpha;
    private float mVisibleItems;
    private float mVisibleItemsActivated;

    public interface PickerValueListener {
        void onValueChanged(Picker picker, int i);
    }

    public final CharSequence getSeparator() {
        return this.mSeparators.get(0);
    }

    public final void setSeparator(CharSequence separator) {
        setSeparators(Arrays.asList(separator));
    }

    public final List<CharSequence> getSeparators() {
        return this.mSeparators;
    }

    public final void setSeparators(List<CharSequence> separators) {
        this.mSeparators.clear();
        this.mSeparators.addAll(separators);
    }

    @LayoutRes
    public final int getPickerItemLayoutId() {
        return this.mPickerItemLayoutId;
    }

    public final void setPickerItemLayoutId(@LayoutRes int pickerItemLayoutId) {
        this.mPickerItemLayoutId = pickerItemLayoutId;
    }

    @IdRes
    public final int getPickerItemTextViewId() {
        return this.mPickerItemTextViewId;
    }

    public final void setPickerItemTextViewId(@IdRes int textViewId) {
        this.mPickerItemTextViewId = textViewId;
    }

    public Picker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0364R.attr.pickerStyle);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, androidx.leanback.widget.picker.Picker, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public Picker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mColumnViews = new ArrayList();
        this.mVisibleItemsActivated = 3.0f;
        this.mVisibleItems = 1.0f;
        this.mSelectedColumn = 0;
        this.mSeparators = new ArrayList();
        this.mColumnChangeListener = new OnChildViewHolderSelectedListener() {
            public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                int colIndex = Picker.this.mColumnViews.indexOf((VerticalGridView) parent);
                Picker.this.updateColumnAlpha(colIndex, true);
                if (child != null) {
                    Picker.this.onColumnValueChanged(colIndex, Picker.this.mColumns.get(colIndex).getMinValue() + position);
                }
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, C0364R.styleable.lbPicker, defStyleAttr, 0);
        this.mPickerItemLayoutId = a.getResourceId(C0364R.styleable.lbPicker_pickerItemLayout, C0364R.layout.lb_picker_item);
        this.mPickerItemTextViewId = a.getResourceId(C0364R.styleable.lbPicker_pickerItemTextViewId, 0);
        a.recycle();
        setEnabled(true);
        setDescendantFocusability(262144);
        this.mFocusedAlpha = 1.0f;
        this.mUnfocusedAlpha = 1.0f;
        this.mVisibleColumnAlpha = 0.5f;
        this.mInvisibleColumnAlpha = 0.0f;
        this.mAlphaAnimDuration = 200;
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.5f);
        this.mPickerView = (ViewGroup) ((ViewGroup) LayoutInflater.from(getContext()).inflate(C0364R.layout.lb_picker, (ViewGroup) this, true)).findViewById(C0364R.C0366id.picker);
    }

    public PickerColumn getColumnAt(int colIndex) {
        ArrayList<PickerColumn> arrayList = this.mColumns;
        if (arrayList == null) {
            return null;
        }
        return arrayList.get(colIndex);
    }

    public int getColumnsCount() {
        ArrayList<PickerColumn> arrayList = this.mColumns;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public void setColumns(List<PickerColumn> columns) {
        if (this.mSeparators.size() != 0) {
            if (this.mSeparators.size() == 1) {
                CharSequence separator = this.mSeparators.get(0);
                this.mSeparators.clear();
                this.mSeparators.add("");
                for (int i = 0; i < columns.size() - 1; i++) {
                    this.mSeparators.add(separator);
                }
                this.mSeparators.add("");
            } else if (this.mSeparators.size() != columns.size() + 1) {
                throw new IllegalStateException("Separators size: " + this.mSeparators.size() + " mustequal the size of columns: " + columns.size() + " + 1");
            }
            this.mColumnViews.clear();
            this.mPickerView.removeAllViews();
            this.mColumns = new ArrayList<>(columns);
            if (this.mSelectedColumn > this.mColumns.size() - 1) {
                this.mSelectedColumn = this.mColumns.size() - 1;
            }
            LayoutInflater inflater = LayoutInflater.from(getContext());
            int totalCol = getColumnsCount();
            if (!TextUtils.isEmpty(this.mSeparators.get(0))) {
                TextView separator2 = (TextView) inflater.inflate(C0364R.layout.lb_picker_separator, this.mPickerView, false);
                separator2.setText(this.mSeparators.get(0));
                this.mPickerView.addView(separator2);
            }
            for (int i2 = 0; i2 < totalCol; i2++) {
                VerticalGridView columnView = (VerticalGridView) inflater.inflate(C0364R.layout.lb_picker_column, this.mPickerView, false);
                updateColumnSize(columnView);
                columnView.setWindowAlignment(0);
                columnView.setHasFixedSize(false);
                columnView.setFocusable(isActivated());
                columnView.setItemViewCacheSize(0);
                this.mColumnViews.add(columnView);
                this.mPickerView.addView(columnView);
                if (!TextUtils.isEmpty(this.mSeparators.get(i2 + 1))) {
                    TextView separator3 = (TextView) inflater.inflate(C0364R.layout.lb_picker_separator, this.mPickerView, false);
                    separator3.setText(this.mSeparators.get(i2 + 1));
                    this.mPickerView.addView(separator3);
                }
                columnView.setAdapter(new PickerScrollArrayAdapter(getPickerItemLayoutId(), getPickerItemTextViewId(), i2));
                columnView.setOnChildViewHolderSelectedListener(this.mColumnChangeListener);
            }
            return;
        }
        throw new IllegalStateException("Separators size is: " + this.mSeparators.size() + ". At least one separator must be provided");
    }

    public void setColumnAt(int columnIndex, PickerColumn column) {
        this.mColumns.set(columnIndex, column);
        VerticalGridView columnView = this.mColumnViews.get(columnIndex);
        PickerScrollArrayAdapter adapter = (PickerScrollArrayAdapter) columnView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        columnView.setSelectedPosition(column.getCurrentValue() - column.getMinValue());
    }

    public void setColumnValue(int columnIndex, int value, boolean runAnimation) {
        PickerColumn column = this.mColumns.get(columnIndex);
        if (column.getCurrentValue() != value) {
            column.setCurrentValue(value);
            notifyValueChanged(columnIndex);
            VerticalGridView columnView = this.mColumnViews.get(columnIndex);
            if (columnView != null) {
                int position = value - this.mColumns.get(columnIndex).getMinValue();
                if (runAnimation) {
                    columnView.setSelectedPositionSmooth(position);
                } else {
                    columnView.setSelectedPosition(position);
                }
            }
        }
    }

    private void notifyValueChanged(int columnIndex) {
        ArrayList<PickerValueListener> arrayList = this.mListeners;
        if (arrayList != null) {
            for (int i = arrayList.size() - 1; i >= 0; i--) {
                this.mListeners.get(i).onValueChanged(this, columnIndex);
            }
        }
    }

    public void addOnValueChangedListener(PickerValueListener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(listener);
    }

    public void removeOnValueChangedListener(PickerValueListener listener) {
        ArrayList<PickerValueListener> arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void updateColumnAlpha(int colIndex, boolean animate) {
        VerticalGridView column = this.mColumnViews.get(colIndex);
        int selected = column.getSelectedPosition();
        int i = 0;
        while (i < column.getAdapter().getItemCount()) {
            View item = column.getLayoutManager().findViewByPosition(i);
            if (item != null) {
                setOrAnimateAlpha(item, selected == i, colIndex, animate);
            }
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    public void setOrAnimateAlpha(View view, boolean selected, int colIndex, boolean animate) {
        boolean columnShownAsActivated = colIndex == this.mSelectedColumn || !hasFocus();
        if (selected) {
            if (columnShownAsActivated) {
                setOrAnimateAlpha(view, animate, this.mFocusedAlpha, -1.0f, this.mDecelerateInterpolator);
            } else {
                setOrAnimateAlpha(view, animate, this.mUnfocusedAlpha, -1.0f, this.mDecelerateInterpolator);
            }
        } else if (columnShownAsActivated) {
            setOrAnimateAlpha(view, animate, this.mVisibleColumnAlpha, -1.0f, this.mDecelerateInterpolator);
        } else {
            setOrAnimateAlpha(view, animate, this.mInvisibleColumnAlpha, -1.0f, this.mDecelerateInterpolator);
        }
    }

    private void setOrAnimateAlpha(View view, boolean animate, float destAlpha, float startAlpha, Interpolator interpolator) {
        view.animate().cancel();
        if (!animate) {
            view.setAlpha(destAlpha);
            return;
        }
        if (startAlpha >= 0.0f) {
            view.setAlpha(startAlpha);
        }
        view.animate().alpha(destAlpha).setDuration((long) this.mAlphaAnimDuration).setInterpolator(interpolator).start();
    }

    public void onColumnValueChanged(int columnIndex, int newValue) {
        PickerColumn column = this.mColumns.get(columnIndex);
        if (column.getCurrentValue() != newValue) {
            column.setCurrentValue(newValue);
            notifyValueChanged(columnIndex);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
     arg types: [int, android.util.TypedValue, int]
     candidates:
      ClspMth{android.content.res.Resources.getValue(java.lang.String, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
      ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException} */
    private float getFloat(int resourceId) {
        TypedValue buffer = new TypedValue();
        getContext().getResources().getValue(resourceId, buffer, true);
        return buffer.getFloat();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(View v, TextView textView2) {
            super(v);
            this.textView = textView2;
        }
    }

    class PickerScrollArrayAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final int mColIndex;
        private PickerColumn mData;
        private final int mResource;
        private final int mTextViewResourceId;

        PickerScrollArrayAdapter(int resource, int textViewResourceId, int colIndex) {
            this.mResource = resource;
            this.mColIndex = colIndex;
            this.mTextViewResourceId = textViewResourceId;
            this.mData = Picker.this.mColumns.get(this.mColIndex);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
         arg types: [int, android.view.ViewGroup, int]
         candidates:
          ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
          ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView;
            View v = LayoutInflater.from(parent.getContext()).inflate(this.mResource, parent, false);
            int i = this.mTextViewResourceId;
            if (i != 0) {
                textView = (TextView) v.findViewById(i);
            } else {
                textView = (TextView) v;
            }
            return new ViewHolder(v, textView);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!(holder.textView == null || this.mData == null)) {
                TextView textView = holder.textView;
                PickerColumn pickerColumn = this.mData;
                textView.setText(pickerColumn.getLabelFor(pickerColumn.getMinValue() + position));
            }
            Picker.this.setOrAnimateAlpha(holder.itemView, Picker.this.mColumnViews.get(this.mColIndex).getSelectedPosition() == position, this.mColIndex, false);
        }

        public void onViewAttachedToWindow(ViewHolder holder) {
            holder.itemView.setFocusable(Picker.this.isActivated());
        }

        public int getItemCount() {
            PickerColumn pickerColumn = this.mData;
            if (pickerColumn == null) {
                return 0;
            }
            return pickerColumn.getCount();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (!isActivated()) {
            return super.dispatchKeyEvent(event);
        }
        int keyCode = event.getKeyCode();
        if (keyCode != 23 && keyCode != 66) {
            return super.dispatchKeyEvent(event);
        }
        if (event.getAction() == 1) {
            performClick();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        int column = getSelectedColumn();
        if (column < 0 || column >= this.mColumnViews.size()) {
            return false;
        }
        return this.mColumnViews.get(column).requestFocus(direction, previouslyFocusedRect);
    }

    /* access modifiers changed from: protected */
    public int getPickerItemHeightPixels() {
        return getContext().getResources().getDimensionPixelSize(C0364R.dimen.picker_item_height);
    }

    private void updateColumnSize() {
        for (int i = 0; i < getColumnsCount(); i++) {
            updateColumnSize(this.mColumnViews.get(i));
        }
    }

    private void updateColumnSize(VerticalGridView columnView) {
        ViewGroup.LayoutParams lp = columnView.getLayoutParams();
        float itemCount = isActivated() ? getActivatedVisibleItemCount() : getVisibleItemCount();
        lp.height = (int) ((((float) getPickerItemHeightPixels()) * itemCount) + (((float) columnView.getVerticalSpacing()) * (itemCount - 1.0f)));
        columnView.setLayoutParams(lp);
    }

    private void updateItemFocusable() {
        boolean activated = isActivated();
        for (int i = 0; i < getColumnsCount(); i++) {
            VerticalGridView grid = this.mColumnViews.get(i);
            for (int j = 0; j < grid.getChildCount(); j++) {
                grid.getChildAt(j).setFocusable(activated);
            }
        }
    }

    public float getActivatedVisibleItemCount() {
        return this.mVisibleItemsActivated;
    }

    public void setActivatedVisibleItemCount(float visiblePickerItems) {
        if (visiblePickerItems <= 0.0f) {
            throw new IllegalArgumentException();
        } else if (this.mVisibleItemsActivated != visiblePickerItems) {
            this.mVisibleItemsActivated = visiblePickerItems;
            if (isActivated()) {
                updateColumnSize();
            }
        }
    }

    public float getVisibleItemCount() {
        return 1.0f;
    }

    public void setVisibleItemCount(float pickerItems) {
        if (pickerItems <= 0.0f) {
            throw new IllegalArgumentException();
        } else if (this.mVisibleItems != pickerItems) {
            this.mVisibleItems = pickerItems;
            if (!isActivated()) {
                updateColumnSize();
            }
        }
    }

    public void setActivated(boolean activated) {
        if (activated == isActivated()) {
            super.setActivated(activated);
            return;
        }
        super.setActivated(activated);
        boolean hadFocus = hasFocus();
        int column = getSelectedColumn();
        setDescendantFocusability(131072);
        if (!activated && hadFocus && isFocusable()) {
            requestFocus();
        }
        for (int i = 0; i < getColumnsCount(); i++) {
            this.mColumnViews.get(i).setFocusable(activated);
        }
        updateColumnSize();
        updateItemFocusable();
        if (activated && hadFocus && column >= 0) {
            this.mColumnViews.get(column).requestFocus();
        }
        setDescendantFocusability(262144);
    }

    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        for (int i = 0; i < this.mColumnViews.size(); i++) {
            if (this.mColumnViews.get(i).hasFocus()) {
                setSelectedColumn(i);
            }
        }
    }

    public void setSelectedColumn(int columnIndex) {
        if (this.mSelectedColumn != columnIndex) {
            this.mSelectedColumn = columnIndex;
            for (int i = 0; i < this.mColumnViews.size(); i++) {
                updateColumnAlpha(i, true);
            }
        }
        VerticalGridView columnView = this.mColumnViews.get(columnIndex);
        if (hasFocus()) {
            columnView.requestFocus();
        }
    }

    public int getSelectedColumn() {
        return this.mSelectedColumn;
    }
}
