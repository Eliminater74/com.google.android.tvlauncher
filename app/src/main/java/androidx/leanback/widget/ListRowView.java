package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.leanback.C0364R;

public final class ListRowView extends LinearLayout {
    private HorizontalGridView mGridView;

    public ListRowView(Context context) {
        this(context, null);
    }

    public ListRowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(C0364R.layout.lb_list_row, this);
        this.mGridView = (HorizontalGridView) findViewById(C0364R.C0366id.row_content);
        this.mGridView.setHasFixedSize(false);
        setOrientation(1);
        setDescendantFocusability(262144);
    }

    public HorizontalGridView getGridView() {
        return this.mGridView;
    }
}
