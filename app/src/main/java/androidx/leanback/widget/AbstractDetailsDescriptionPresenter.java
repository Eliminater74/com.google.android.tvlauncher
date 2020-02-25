package androidx.leanback.widget;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.leanback.C0364R;

public abstract class AbstractDetailsDescriptionPresenter extends Presenter {
    /* access modifiers changed from: protected */
    public abstract void onBindDescription(ViewHolder viewHolder, Object obj);

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public final ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(C0364R.layout.lb_details_description, parent, false));
    }

    public final void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        ViewHolder vh = (ViewHolder) viewHolder;
        onBindDescription(vh, item);
        boolean hasTitle = true;
        if (TextUtils.isEmpty(vh.mTitle.getText())) {
            vh.mTitle.setVisibility(8);
            hasTitle = false;
        } else {
            vh.mTitle.setVisibility(0);
            vh.mTitle.setLineSpacing(((float) (vh.mTitleLineSpacing - vh.mTitle.getLineHeight())) + vh.mTitle.getLineSpacingExtra(), vh.mTitle.getLineSpacingMultiplier());
            vh.mTitle.setMaxLines(vh.mTitleMaxLines);
        }
        setTopMargin(vh.mTitle, vh.mTitleMargin);
        boolean hasSubtitle = true;
        if (TextUtils.isEmpty(vh.mSubtitle.getText())) {
            vh.mSubtitle.setVisibility(8);
            hasSubtitle = false;
        } else {
            vh.mSubtitle.setVisibility(0);
            if (hasTitle) {
                setTopMargin(vh.mSubtitle, (vh.mUnderTitleBaselineMargin + vh.mSubtitleFontMetricsInt.ascent) - vh.mTitleFontMetricsInt.descent);
            } else {
                setTopMargin(vh.mSubtitle, 0);
            }
        }
        if (TextUtils.isEmpty(vh.mBody.getText())) {
            vh.mBody.setVisibility(8);
            return;
        }
        vh.mBody.setVisibility(0);
        vh.mBody.setLineSpacing(((float) (vh.mBodyLineSpacing - vh.mBody.getLineHeight())) + vh.mBody.getLineSpacingExtra(), vh.mBody.getLineSpacingMultiplier());
        if (hasSubtitle) {
            setTopMargin(vh.mBody, (vh.mUnderSubtitleBaselineMargin + vh.mBodyFontMetricsInt.ascent) - vh.mSubtitleFontMetricsInt.descent);
        } else if (hasTitle) {
            setTopMargin(vh.mBody, (vh.mUnderTitleBaselineMargin + vh.mBodyFontMetricsInt.ascent) - vh.mTitleFontMetricsInt.descent);
        } else {
            setTopMargin(vh.mBody, 0);
        }
    }

    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    }

    public void onViewAttachedToWindow(Presenter.ViewHolder holder) {
        ((ViewHolder) holder).addPreDrawListener();
        super.onViewAttachedToWindow(holder);
    }

    public void onViewDetachedFromWindow(Presenter.ViewHolder holder) {
        ((ViewHolder) holder).removePreDrawListener();
        super.onViewDetachedFromWindow(holder);
    }

    private void setTopMargin(TextView textView, int topMargin) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
        lp.topMargin = topMargin;
        textView.setLayoutParams(lp);
    }

    public static class ViewHolder extends Presenter.ViewHolder {
        final TextView mBody;
        final Paint.FontMetricsInt mBodyFontMetricsInt = getFontMetricsInt(this.mBody);
        final int mBodyLineSpacing;
        final int mBodyMaxLines;
        final int mBodyMinLines;
        final TextView mSubtitle;
        final Paint.FontMetricsInt mSubtitleFontMetricsInt = getFontMetricsInt(this.mSubtitle);
        final TextView mTitle;
        final Paint.FontMetricsInt mTitleFontMetricsInt = getFontMetricsInt(this.mTitle);
        final int mTitleLineSpacing;
        final int mTitleMargin;
        final int mTitleMaxLines = this.mTitle.getMaxLines();
        final int mUnderSubtitleBaselineMargin;
        final int mUnderTitleBaselineMargin;
        private ViewTreeObserver.OnPreDrawListener mPreDrawListener;

        public ViewHolder(View view) {
            super(view);
            this.mTitle = (TextView) view.findViewById(C0364R.C0366id.lb_details_description_title);
            this.mSubtitle = (TextView) view.findViewById(C0364R.C0366id.lb_details_description_subtitle);
            this.mBody = (TextView) view.findViewById(C0364R.C0366id.lb_details_description_body);
            Paint.FontMetricsInt titleFontMetricsInt = getFontMetricsInt(this.mTitle);
            this.mTitleMargin = titleFontMetricsInt.ascent + view.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_description_title_baseline);
            this.mUnderTitleBaselineMargin = view.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_description_under_title_baseline_margin);
            this.mUnderSubtitleBaselineMargin = view.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_description_under_subtitle_baseline_margin);
            this.mTitleLineSpacing = view.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_description_title_line_spacing);
            this.mBodyLineSpacing = view.getResources().getDimensionPixelSize(C0364R.dimen.lb_details_description_body_line_spacing);
            this.mBodyMaxLines = view.getResources().getInteger(C0364R.integer.lb_details_description_body_max_lines);
            this.mBodyMinLines = view.getResources().getInteger(C0364R.integer.lb_details_description_body_min_lines);
            this.mTitle.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    ViewHolder.this.addPreDrawListener();
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void addPreDrawListener() {
            if (this.mPreDrawListener == null) {
                this.mPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        if (ViewHolder.this.mSubtitle.getVisibility() != 0 || ViewHolder.this.mSubtitle.getTop() <= ViewHolder.this.view.getHeight() || ViewHolder.this.mTitle.getLineCount() <= 1) {
                            int titleLines = ViewHolder.this.mTitle.getLineCount();
                            ViewHolder viewHolder = ViewHolder.this;
                            int maxLines = titleLines > 1 ? viewHolder.mBodyMinLines : viewHolder.mBodyMaxLines;
                            if (ViewHolder.this.mBody.getMaxLines() != maxLines) {
                                ViewHolder.this.mBody.setMaxLines(maxLines);
                                return false;
                            }
                            ViewHolder.this.removePreDrawListener();
                            return true;
                        }
                        ViewHolder.this.mTitle.setMaxLines(ViewHolder.this.mTitle.getLineCount() - 1);
                        return false;
                    }
                };
                this.view.getViewTreeObserver().addOnPreDrawListener(this.mPreDrawListener);
            }
        }

        /* access modifiers changed from: package-private */
        public void removePreDrawListener() {
            if (this.mPreDrawListener != null) {
                this.view.getViewTreeObserver().removeOnPreDrawListener(this.mPreDrawListener);
                this.mPreDrawListener = null;
            }
        }

        public TextView getTitle() {
            return this.mTitle;
        }

        public TextView getSubtitle() {
            return this.mSubtitle;
        }

        public TextView getBody() {
            return this.mBody;
        }

        private Paint.FontMetricsInt getFontMetricsInt(TextView textView) {
            Paint paint = new Paint(1);
            paint.setTextSize(textView.getTextSize());
            paint.setTypeface(textView.getTypeface());
            return paint.getFontMetricsInt();
        }
    }
}
