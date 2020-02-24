package androidx.leanback.widget;

import android.support.annotation.RestrictTo;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.leanback.widget.RowPresenter;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class InvisibleRowPresenter extends RowPresenter {
    public InvisibleRowPresenter() {
        setHeaderPresenter(null);
    }

    /* access modifiers changed from: protected */
    public RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        RelativeLayout root = new RelativeLayout(parent.getContext());
        root.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
        return new RowPresenter.ViewHolder(root);
    }
}
