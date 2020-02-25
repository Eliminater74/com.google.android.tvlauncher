package androidx.leanback.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.leanback.C0364R;

public class DetailsOverviewLogoPresenter extends Presenter {

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C0364R.layout.lb_fullwidth_details_overview_logo, parent, false);
    }

    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = onCreateView(parent);
        ViewHolder vh = new ViewHolder(view);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        vh.setSizeFromDrawableIntrinsic(lp.width == -2 && lp.height == -2);
        return vh;
    }

    public void setContext(ViewHolder viewHolder, FullWidthDetailsOverviewRowPresenter.ViewHolder parentViewHolder, FullWidthDetailsOverviewRowPresenter parentPresenter) {
        viewHolder.mParentViewHolder = parentViewHolder;
        viewHolder.mParentPresenter = parentPresenter;
    }

    public boolean isBoundToImage(ViewHolder viewHolder, DetailsOverviewRow row) {
        return (row == null || row.getImageDrawable() == null) ? false : true;
    }

    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        DetailsOverviewRow row = (DetailsOverviewRow) item;
        ImageView imageView = (ImageView) viewHolder.view;
        imageView.setImageDrawable(row.getImageDrawable());
        if (isBoundToImage((ViewHolder) viewHolder, row)) {
            ViewHolder vh = (ViewHolder) viewHolder;
            if (vh.isSizeFromDrawableIntrinsic()) {
                ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                lp.width = row.getImageDrawable().getIntrinsicWidth();
                lp.height = row.getImageDrawable().getIntrinsicHeight();
                if (imageView.getMaxWidth() > 0 || imageView.getMaxHeight() > 0) {
                    float maxScaleWidth = 1.0f;
                    if (imageView.getMaxWidth() > 0 && lp.width > imageView.getMaxWidth()) {
                        maxScaleWidth = ((float) imageView.getMaxWidth()) / ((float) lp.width);
                    }
                    float maxScaleHeight = 1.0f;
                    if (imageView.getMaxHeight() > 0 && lp.height > imageView.getMaxHeight()) {
                        maxScaleHeight = ((float) imageView.getMaxHeight()) / ((float) lp.height);
                    }
                    float scale = Math.min(maxScaleWidth, maxScaleHeight);
                    lp.width = (int) (((float) lp.width) * scale);
                    lp.height = (int) (((float) lp.height) * scale);
                }
                imageView.setLayoutParams(lp);
            }
            vh.mParentPresenter.notifyOnBindLogo(vh.mParentViewHolder);
        }
    }

    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    }

    public static class ViewHolder extends Presenter.ViewHolder {
        protected FullWidthDetailsOverviewRowPresenter mParentPresenter;
        protected FullWidthDetailsOverviewRowPresenter.ViewHolder mParentViewHolder;
        private boolean mSizeFromDrawableIntrinsic;

        public ViewHolder(View view) {
            super(view);
        }

        public FullWidthDetailsOverviewRowPresenter getParentPresenter() {
            return this.mParentPresenter;
        }

        public FullWidthDetailsOverviewRowPresenter.ViewHolder getParentViewHolder() {
            return this.mParentViewHolder;
        }

        public boolean isSizeFromDrawableIntrinsic() {
            return this.mSizeFromDrawableIntrinsic;
        }

        public void setSizeFromDrawableIntrinsic(boolean sizeFromDrawableIntrinsic) {
            this.mSizeFromDrawableIntrinsic = sizeFromDrawableIntrinsic;
        }
    }
}
