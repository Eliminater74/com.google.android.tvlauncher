package androidx.leanback.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.leanback.C0364R;

public final class ListRowHoverCardView extends LinearLayout {
    private final TextView mDescriptionView;
    private final TextView mTitleView;

    public ListRowHoverCardView(Context context) {
        this(context, null);
    }

    public ListRowHoverCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListRowHoverCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(C0364R.layout.lb_list_row_hovercard, this);
        this.mTitleView = (TextView) findViewById(C0364R.C0366id.title);
        this.mDescriptionView = (TextView) findViewById(C0364R.C0366id.description);
    }

    public final CharSequence getTitle() {
        return this.mTitleView.getText();
    }

    public final void setTitle(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            this.mTitleView.setText(text);
            this.mTitleView.setVisibility(0);
            return;
        }
        this.mTitleView.setVisibility(8);
    }

    public final CharSequence getDescription() {
        return this.mDescriptionView.getText();
    }

    public final void setDescription(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            this.mDescriptionView.setText(text);
            this.mDescriptionView.setVisibility(0);
            return;
        }
        this.mDescriptionView.setVisibility(8);
    }
}
