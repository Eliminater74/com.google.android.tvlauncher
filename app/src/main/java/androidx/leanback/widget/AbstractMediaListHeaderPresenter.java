package androidx.leanback.widget;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.C0364R;

public abstract class AbstractMediaListHeaderPresenter extends RowPresenter {
    private final Context mContext;
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;

    public AbstractMediaListHeaderPresenter(Context context, int mThemeResId) {
        this.mBackgroundColor = 0;
        this.mContext = new ContextThemeWrapper(context.getApplicationContext(), mThemeResId);
        setHeaderPresenter(null);
    }

    public AbstractMediaListHeaderPresenter() {
        this.mBackgroundColor = 0;
        this.mContext = null;
        setHeaderPresenter(null);
    }

    /* access modifiers changed from: protected */
    public abstract void onBindMediaListHeaderViewHolder(ViewHolder viewHolder, Object obj);

    public boolean isUsingDefaultSelectEffect() {
        return false;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    /* access modifiers changed from: protected */
    public RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        Context context = this.mContext;
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(C0364R.layout.lb_media_list_header, parent, false);
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
        ViewHolder vh = new ViewHolder(view);
        if (this.mBackgroundColorSet) {
            vh.view.setBackgroundColor(this.mBackgroundColor);
        }
        return vh;
    }

    /* access modifiers changed from: protected */
    public void onBindRowViewHolder(RowPresenter.ViewHolder vh, Object item) {
        super.onBindRowViewHolder(vh, item);
        onBindMediaListHeaderViewHolder((ViewHolder) vh, item);
    }

    public void setBackgroundColor(int color) {
        this.mBackgroundColorSet = true;
        this.mBackgroundColor = color;
    }

    public static class ViewHolder extends RowPresenter.ViewHolder {
        private final TextView mHeaderView;

        public ViewHolder(View view) {
            super(view);
            this.mHeaderView = (TextView) view.findViewById(C0364R.C0366id.mediaListHeader);
        }

        public TextView getHeaderView() {
            return this.mHeaderView;
        }
    }
}
