package com.google.android.tvlauncher.appsview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.content.ContextCompat;
import android.support.p004v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.appsview.data.AppLinksDataManager;
import com.google.android.tvlauncher.util.IntentUtil;
import com.google.android.tvlauncher.util.OemPromotionApp;
import com.google.android.tvrecommendations.shared.util.Constants;
import java.util.ArrayList;
import java.util.List;

public class PromotionRowAdapter extends RecyclerView.Adapter<PromotionViewHolder> {
    /* access modifiers changed from: private */
    public RequestOptions mBannerRequestOptions;
    /* access modifiers changed from: private */
    public OnAppsViewActionListener mOnAppsViewActionListener;
    private List<OemPromotionApp> mPromotions = new ArrayList();

    PromotionRowAdapter(Context context) {
        Drawable placeholderBanner = new ColorDrawable(ContextCompat.getColor(context, C1188R.color.app_banner_background_color));
        this.mBannerRequestOptions = (RequestOptions) ((RequestOptions) ((RequestOptions) new RequestOptions().placeholder(placeholderBanner)).error(placeholderBanner)).diskCacheStrategy(DiskCacheStrategy.NONE);
    }

    class PromotionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnFocusChangeListener {
        private final BannerView mBannerView;
        private OemPromotionApp mPromotion;

        PromotionViewHolder(View itemView) {
            super(itemView);
            this.mBannerView = (BannerView) itemView;
            this.mBannerView.setOnClickListener(this);
            this.mBannerView.setOnFocusChangeListener(this);
        }

        /* access modifiers changed from: package-private */
        public void setPromotion(OemPromotionApp promotion) {
            this.mPromotion = promotion;
            BannerView bannerView = this.mBannerView;
            bannerView.resetAnimations(bannerView.isFocused());
            this.mBannerView.setTitle(promotion.getAppName());
            Glide.with(this.mBannerView.getContext()).setDefaultRequestOptions(PromotionRowAdapter.this.mBannerRequestOptions).load(promotion.getBannerUri()).into(new ImageViewTarget<Drawable>(this, this.mBannerView.getBannerImage()) {
                /* access modifiers changed from: protected */
                public void setResource(@Nullable Drawable resource) {
                    setDrawable(resource);
                }

                /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
                 method: com.bumptech.glide.request.target.ImageViewTarget.onResourceReady(java.lang.Object, com.bumptech.glide.request.transition.Transition):void
                 arg types: [android.graphics.drawable.Drawable, com.bumptech.glide.request.transition.Transition]
                 candidates:
                  com.google.android.tvlauncher.appsview.PromotionRowAdapter.PromotionViewHolder.1.onResourceReady(android.graphics.drawable.Drawable, com.bumptech.glide.request.transition.Transition):void
                  com.bumptech.glide.request.target.ImageViewTarget.onResourceReady(java.lang.Object, com.bumptech.glide.request.transition.Transition):void */
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition transition) {
                    if (!(resource instanceof BitmapDrawable) || !((BitmapDrawable) resource).getBitmap().hasAlpha()) {
                        ((ImageView) this.view).setBackground(null);
                    } else {
                        ((ImageView) this.view).setBackgroundColor(ContextCompat.getColor(((ImageView) this.view).getContext(), C1188R.color.app_banner_background_color));
                    }
                    super.onResourceReady((Object) resource, transition);
                }
            });
        }

        public void onClick(View v) {
            Intent intent;
            Context context = this.itemView.getContext();
            if (this.mPromotion.isVirtualApp()) {
                OemPromotionApp appLink = (OemPromotionApp) AppLinksDataManager.getInstance(context).getAppLink(this.mPromotion.getId());
                if (appLink == null) {
                    intent = new Intent(Constants.ACTION_ADD_APP_LINK);
                    intent.putExtra(Constants.EXTRA_APP_NAME, this.mPromotion.getAppName());
                    intent.putExtra(Constants.EXTRA_PACKAGE_NAME, this.mPromotion.getPackageName());
                    intent.putExtra(Constants.EXTRA_BANNER_URI, this.mPromotion.getBannerUri());
                    intent.putExtra(Constants.EXTRA_DATA_URI, this.mPromotion.getDataUri());
                    intent.putExtra(Constants.EXTRA_DEVELOPER, this.mPromotion.getDeveloper());
                    intent.putExtra(Constants.EXTRA_CATEGORY, this.mPromotion.getCategory());
                    intent.putExtra(Constants.EXTRA_DESCRIPTION, this.mPromotion.getDescription());
                    intent.putExtra(Constants.EXTRA_IS_GAME, this.mPromotion.isGame());
                    List<String> screenshots = this.mPromotion.getScreenshotUris();
                    if (screenshots.size() > 0) {
                        intent.putExtra(Constants.EXTRA_SCREENSHOTS, (String[]) screenshots.toArray(new String[screenshots.size()]));
                    }
                } else {
                    intent = IntentUtil.createVirtualAppIntent(appLink.getPackageName(), appLink.getDataUri());
                }
            } else {
                intent = this.itemView.getContext().getPackageManager().getLeanbackLaunchIntentForPackage(this.mPromotion.getPackageName());
                if (intent != null) {
                    intent.addFlags(270532608);
                } else {
                    intent = new Intent("android.intent.action.VIEW");
                    String valueOf = String.valueOf(this.mPromotion.getPackageName());
                    intent.setData(Uri.parse(valueOf.length() != 0 ? "market://details?id=".concat(valueOf) : new String("market://details?id=")));
                }
            }
            PromotionRowAdapter.this.mOnAppsViewActionListener.onLaunchApp(intent, this.itemView);
        }

        public void onFocusChange(View v, boolean hasFocus) {
            this.mBannerView.setFocusedState(hasFocus);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @NonNull
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PromotionViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1188R.layout.view_app_banner, parent, false));
    }

    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        holder.setPromotion(this.mPromotions.get(position));
    }

    public int getItemCount() {
        return this.mPromotions.size();
    }

    public long getItemId(int position) {
        return (long) this.mPromotions.get(position).getId().hashCode();
    }

    /* access modifiers changed from: package-private */
    public void setOnAppsViewActionListener(OnAppsViewActionListener listener) {
        this.mOnAppsViewActionListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setPromotions(List<OemPromotionApp> promotions) {
        this.mPromotions = promotions;
        notifyDataSetChanged();
    }
}
