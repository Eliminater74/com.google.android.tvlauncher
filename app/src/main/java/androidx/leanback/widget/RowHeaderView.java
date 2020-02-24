package androidx.leanback.widget;

import android.content.Context;
import android.support.p001v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.widget.TextView;
import androidx.leanback.C0364R;

public final class RowHeaderView extends TextView {
    public RowHeaderView(Context context) {
        this(context, null);
    }

    public RowHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, C0364R.attr.rowHeaderStyle);
    }

    public RowHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, actionModeCallback));
    }
}
