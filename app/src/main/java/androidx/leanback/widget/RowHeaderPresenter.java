package androidx.leanback.widget;

import android.graphics.Paint;
import android.support.annotation.RestrictTo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.C0364R;

public class RowHeaderPresenter extends Presenter {
    private final boolean mAnimateSelect;
    private final Paint mFontMeasurePaint;
    private final int mLayoutResourceId;
    private boolean mNullItemVisibilityGone;

    public RowHeaderPresenter() {
        this(C0364R.layout.lb_row_header);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public RowHeaderPresenter(int layoutResourceId) {
        this(layoutResourceId, true);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public RowHeaderPresenter(int layoutResourceId, boolean animateSelect) {
        this.mFontMeasurePaint = new Paint(1);
        this.mLayoutResourceId = layoutResourceId;
        this.mAnimateSelect = animateSelect;
    }

    protected static float getFontDescent(TextView textView, Paint fontMeasurePaint) {
        if (fontMeasurePaint.getTextSize() != textView.getTextSize()) {
            fontMeasurePaint.setTextSize(textView.getTextSize());
        }
        if (fontMeasurePaint.getTypeface() != textView.getTypeface()) {
            fontMeasurePaint.setTypeface(textView.getTypeface());
        }
        return fontMeasurePaint.descent();
    }

    public boolean isNullItemVisibilityGone() {
        return this.mNullItemVisibilityGone;
    }

    public void setNullItemVisibilityGone(boolean nullItemVisibilityGone) {
        this.mNullItemVisibilityGone = nullItemVisibilityGone;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(this.mLayoutResourceId, parent, false));
        if (this.mAnimateSelect) {
            setSelectLevel(viewHolder, 0.0f);
        }
        return viewHolder;
    }

    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        HeaderItem headerItem = item == null ? null : ((Row) item).getHeaderItem();
        ViewHolder vh = (ViewHolder) viewHolder;
        if (headerItem == null) {
            if (vh.mTitleView != null) {
                vh.mTitleView.setText((CharSequence) null);
            }
            if (vh.mDescriptionView != null) {
                vh.mDescriptionView.setText((CharSequence) null);
            }
            viewHolder.view.setContentDescription(null);
            if (this.mNullItemVisibilityGone) {
                viewHolder.view.setVisibility(8);
                return;
            }
            return;
        }
        if (vh.mTitleView != null) {
            vh.mTitleView.setText(headerItem.getName());
        }
        if (vh.mDescriptionView != null) {
            if (TextUtils.isEmpty(headerItem.getDescription())) {
                vh.mDescriptionView.setVisibility(8);
            } else {
                vh.mDescriptionView.setVisibility(0);
            }
            vh.mDescriptionView.setText(headerItem.getDescription());
        }
        viewHolder.view.setContentDescription(headerItem.getContentDescription());
        viewHolder.view.setVisibility(0);
    }

    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ViewHolder vh = (ViewHolder) viewHolder;
        if (vh.mTitleView != null) {
            vh.mTitleView.setText((CharSequence) null);
        }
        if (vh.mDescriptionView != null) {
            vh.mDescriptionView.setText((CharSequence) null);
        }
        if (this.mAnimateSelect) {
            setSelectLevel((ViewHolder) viewHolder, 0.0f);
        }
    }

    public final void setSelectLevel(ViewHolder holder, float selectLevel) {
        holder.mSelectLevel = selectLevel;
        onSelectLevelChanged(holder);
    }

    /* access modifiers changed from: protected */
    public void onSelectLevelChanged(ViewHolder holder) {
        if (this.mAnimateSelect) {
            holder.view.setAlpha(holder.mUnselectAlpha + (holder.mSelectLevel * (1.0f - holder.mUnselectAlpha)));
        }
    }

    public int getSpaceUnderBaseline(ViewHolder holder) {
        int space = holder.view.getPaddingBottom();
        if (holder.view instanceof TextView) {
            return space + ((int) getFontDescent((TextView) holder.view, this.mFontMeasurePaint));
        }
        return space;
    }

    public static class ViewHolder extends Presenter.ViewHolder {
        TextView mDescriptionView;
        int mOriginalTextColor;
        float mSelectLevel;
        RowHeaderView mTitleView;
        float mUnselectAlpha;

        public ViewHolder(View view) {
            super(view);
            this.mTitleView = (RowHeaderView) view.findViewById(C0364R.C0366id.row_header);
            this.mDescriptionView = (TextView) view.findViewById(C0364R.C0366id.row_header_description);
            initColors();
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public ViewHolder(RowHeaderView view) {
            super(view);
            this.mTitleView = view;
            initColors();
        }

        /* access modifiers changed from: package-private */
        public void initColors() {
            RowHeaderView rowHeaderView = this.mTitleView;
            if (rowHeaderView != null) {
                this.mOriginalTextColor = rowHeaderView.getCurrentTextColor();
            }
            this.mUnselectAlpha = this.view.getResources().getFraction(C0364R.fraction.lb_browse_header_unselect_alpha, 1, 1);
        }

        public final float getSelectLevel() {
            return this.mSelectLevel;
        }
    }
}
