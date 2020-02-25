package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.leanback.C0364R;

public class ImageCardView extends BaseCardView {
    public static final int CARD_TYPE_FLAG_CONTENT = 2;
    public static final int CARD_TYPE_FLAG_ICON_LEFT = 8;
    public static final int CARD_TYPE_FLAG_ICON_RIGHT = 4;
    public static final int CARD_TYPE_FLAG_IMAGE_ONLY = 0;
    public static final int CARD_TYPE_FLAG_TITLE = 1;
    private static final String ALPHA = "alpha";
    ObjectAnimator mFadeInAnimator;
    private boolean mAttachedToWindow;
    private ImageView mBadgeImage;
    private TextView mContentView;
    private ImageView mImageView;
    private ViewGroup mInfoArea;
    private TextView mTitleView;

    @Deprecated
    public ImageCardView(Context context, int themeResId) {
        this(new ContextThemeWrapper(context, themeResId));
    }

    public ImageCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buildImageCardView(attrs, defStyleAttr, C0364R.style.Widget_Leanback_ImageCardView);
    }

    public ImageCardView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ImageCardView(Context context, AttributeSet attrs) {
        this(context, attrs, C0364R.attr.imageCardViewStyle);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    private void buildImageCardView(AttributeSet attrs, int defStyleAttr, int defStyle) {
        setFocusable(true);
        setFocusableInTouchMode(true);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(C0364R.layout.lb_image_card_view, this);
        TypedArray cardAttrs = getContext().obtainStyledAttributes(attrs, C0364R.styleable.lbImageCardView, defStyleAttr, defStyle);
        int cardType = cardAttrs.getInt(C0364R.styleable.lbImageCardView_lbImageCardViewType, 0);
        boolean hasImageOnly = cardType == 0;
        boolean hasTitle = (cardType & 1) == 1;
        boolean hasContent = (cardType & 2) == 2;
        boolean hasIconRight = (cardType & 4) == 4;
        boolean hasIconLeft = !hasIconRight && (cardType & 8) == 8;
        this.mImageView = (ImageView) findViewById(C0364R.C0366id.main_image);
        if (this.mImageView.getDrawable() == null) {
            this.mImageView.setVisibility(4);
        }
        this.mFadeInAnimator = ObjectAnimator.ofFloat(this.mImageView, ALPHA, 1.0f);
        this.mFadeInAnimator.setDuration((long) this.mImageView.getResources().getInteger(17694720));
        this.mInfoArea = (ViewGroup) findViewById(C0364R.C0366id.info_field);
        if (hasImageOnly) {
            removeView(this.mInfoArea);
            cardAttrs.recycle();
            return;
        }
        if (hasTitle) {
            this.mTitleView = (TextView) inflater.inflate(C0364R.layout.lb_image_card_view_themed_title, this.mInfoArea, false);
            this.mInfoArea.addView(this.mTitleView);
        }
        if (hasContent) {
            this.mContentView = (TextView) inflater.inflate(C0364R.layout.lb_image_card_view_themed_content, this.mInfoArea, false);
            this.mInfoArea.addView(this.mContentView);
        }
        if (hasIconRight || hasIconLeft) {
            int layoutId = C0364R.layout.lb_image_card_view_themed_badge_right;
            if (hasIconLeft) {
                layoutId = C0364R.layout.lb_image_card_view_themed_badge_left;
            }
            this.mBadgeImage = (ImageView) inflater.inflate(layoutId, this.mInfoArea, false);
            this.mInfoArea.addView(this.mBadgeImage);
        }
        if (hasTitle && !hasContent && this.mBadgeImage != null) {
            RelativeLayout.LayoutParams relativeLayoutParams = (RelativeLayout.LayoutParams) this.mTitleView.getLayoutParams();
            if (hasIconLeft) {
                relativeLayoutParams.addRule(17, this.mBadgeImage.getId());
            } else {
                relativeLayoutParams.addRule(16, this.mBadgeImage.getId());
            }
            this.mTitleView.setLayoutParams(relativeLayoutParams);
        }
        if (hasContent) {
            RelativeLayout.LayoutParams relativeLayoutParams2 = (RelativeLayout.LayoutParams) this.mContentView.getLayoutParams();
            if (!hasTitle) {
                relativeLayoutParams2.addRule(10);
            }
            if (hasIconLeft) {
                relativeLayoutParams2.removeRule(16);
                relativeLayoutParams2.removeRule(20);
                relativeLayoutParams2.addRule(17, this.mBadgeImage.getId());
            }
            this.mContentView.setLayoutParams(relativeLayoutParams2);
        }
        ImageView imageView = this.mBadgeImage;
        if (imageView != null) {
            RelativeLayout.LayoutParams relativeLayoutParams3 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            if (hasContent) {
                relativeLayoutParams3.addRule(8, this.mContentView.getId());
            } else if (hasTitle) {
                relativeLayoutParams3.addRule(8, this.mTitleView.getId());
            }
            this.mBadgeImage.setLayoutParams(relativeLayoutParams3);
        }
        Drawable background = cardAttrs.getDrawable(C0364R.styleable.lbImageCardView_infoAreaBackground);
        if (background != null) {
            setInfoAreaBackground(background);
        }
        ImageView imageView2 = this.mBadgeImage;
        if (imageView2 != null && imageView2.getDrawable() == null) {
            this.mBadgeImage.setVisibility(8);
        }
        cardAttrs.recycle();
    }

    public final ImageView getMainImageView() {
        return this.mImageView;
    }

    public void setMainImageAdjustViewBounds(boolean adjustViewBounds) {
        ImageView imageView = this.mImageView;
        if (imageView != null) {
            imageView.setAdjustViewBounds(adjustViewBounds);
        }
    }

    public void setMainImageScaleType(ImageView.ScaleType scaleType) {
        ImageView imageView = this.mImageView;
        if (imageView != null) {
            imageView.setScaleType(scaleType);
        }
    }

    public void setMainImage(Drawable drawable, boolean fade) {
        ImageView imageView = this.mImageView;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
            if (drawable == null) {
                this.mFadeInAnimator.cancel();
                this.mImageView.setAlpha(1.0f);
                this.mImageView.setVisibility(4);
                return;
            }
            this.mImageView.setVisibility(0);
            if (fade) {
                fadeIn();
                return;
            }
            this.mFadeInAnimator.cancel();
            this.mImageView.setAlpha(1.0f);
        }
    }

    public void setMainImageDimensions(int width, int height) {
        ViewGroup.LayoutParams lp = this.mImageView.getLayoutParams();
        lp.width = width;
        lp.height = height;
        this.mImageView.setLayoutParams(lp);
    }

    public Drawable getMainImage() {
        ImageView imageView = this.mImageView;
        if (imageView == null) {
            return null;
        }
        return imageView.getDrawable();
    }

    public void setMainImage(Drawable drawable) {
        setMainImage(drawable, true);
    }

    public Drawable getInfoAreaBackground() {
        ViewGroup viewGroup = this.mInfoArea;
        if (viewGroup != null) {
            return viewGroup.getBackground();
        }
        return null;
    }

    public void setInfoAreaBackground(Drawable drawable) {
        ViewGroup viewGroup = this.mInfoArea;
        if (viewGroup != null) {
            viewGroup.setBackground(drawable);
        }
    }

    public void setInfoAreaBackgroundColor(@ColorInt int color) {
        ViewGroup viewGroup = this.mInfoArea;
        if (viewGroup != null) {
            viewGroup.setBackgroundColor(color);
        }
    }

    public CharSequence getTitleText() {
        TextView textView = this.mTitleView;
        if (textView == null) {
            return null;
        }
        return textView.getText();
    }

    public void setTitleText(CharSequence text) {
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(text);
        }
    }

    public CharSequence getContentText() {
        TextView textView = this.mContentView;
        if (textView == null) {
            return null;
        }
        return textView.getText();
    }

    public void setContentText(CharSequence text) {
        TextView textView = this.mContentView;
        if (textView != null) {
            textView.setText(text);
        }
    }

    public Drawable getBadgeImage() {
        ImageView imageView = this.mBadgeImage;
        if (imageView == null) {
            return null;
        }
        return imageView.getDrawable();
    }

    public void setBadgeImage(Drawable drawable) {
        ImageView imageView = this.mBadgeImage;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
            if (drawable != null) {
                this.mBadgeImage.setVisibility(0);
            } else {
                this.mBadgeImage.setVisibility(8);
            }
        }
    }

    private void fadeIn() {
        this.mImageView.setAlpha(0.0f);
        if (this.mAttachedToWindow) {
            this.mFadeInAnimator.start();
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        if (this.mImageView.getAlpha() == 0.0f) {
            fadeIn();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        this.mFadeInAnimator.cancel();
        this.mImageView.setAlpha(1.0f);
        super.onDetachedFromWindow();
    }
}
