package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.leanback.C0364R;

public class VerticalGridView extends BaseGridView {
    public VerticalGridView(Context context) {
        this(context, null);
    }

    public VerticalGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mLayoutManager.setOrientation(1);
        initAttributes(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void initAttributes(Context context, AttributeSet attrs) {
        initBaseGridViewAttributes(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, C0364R.styleable.lbVerticalGridView);
        setColumnWidth(a);
        setNumColumns(a.getInt(C0364R.styleable.lbVerticalGridView_numberOfColumns, 1));
        a.recycle();
    }

    /* access modifiers changed from: package-private */
    public void setColumnWidth(TypedArray array) {
        if (array.peekValue(C0364R.styleable.lbVerticalGridView_columnWidth) != null) {
            setColumnWidth(array.getLayoutDimension(C0364R.styleable.lbVerticalGridView_columnWidth, 0));
        }
    }

    public void setNumColumns(int numColumns) {
        this.mLayoutManager.setNumRows(numColumns);
        requestLayout();
    }

    public void setColumnWidth(int width) {
        this.mLayoutManager.setRowHeight(width);
        requestLayout();
    }
}
