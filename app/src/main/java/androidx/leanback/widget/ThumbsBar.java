package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.leanback.C0364R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ThumbsBar extends LinearLayout {
    final SparseArray<Bitmap> mBitmaps;
    int mHeroThumbHeightInPixel;
    int mHeroThumbWidthInPixel;
    int mMeasuredMarginInPixel;
    int mNumOfThumbs;
    int mThumbHeightInPixel;
    int mThumbWidthInPixel;
    private boolean mIsUserSets;

    public ThumbsBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbsBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mNumOfThumbs = -1;
        this.mBitmaps = new SparseArray<>();
        this.mIsUserSets = false;
        this.mThumbWidthInPixel = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_transport_thumbs_width);
        this.mThumbHeightInPixel = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_transport_thumbs_height);
        this.mHeroThumbHeightInPixel = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_transport_hero_thumbs_width);
        this.mHeroThumbWidthInPixel = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_transport_hero_thumbs_height);
        this.mMeasuredMarginInPixel = context.getResources().getDimensionPixelSize(C0364R.dimen.lb_playback_transport_thumbs_margin);
    }

    private static int roundUp(int num, int divisor) {
        return ((num + divisor) - 1) / divisor;
    }

    public int getHeroIndex() {
        return getChildCount() / 2;
    }

    public void setThumbSize(int width, int height) {
        this.mThumbHeightInPixel = height;
        this.mThumbWidthInPixel = width;
        int heroIndex = getHeroIndex();
        for (int i = 0; i < getChildCount(); i++) {
            if (heroIndex != i) {
                View child = getChildAt(i);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                boolean changed = false;
                if (lp.height != height) {
                    lp.height = height;
                    changed = true;
                }
                if (lp.width != width) {
                    lp.width = width;
                    changed = true;
                }
                if (changed) {
                    child.setLayoutParams(lp);
                }
            }
        }
    }

    public void setHeroThumbSize(int width, int height) {
        this.mHeroThumbHeightInPixel = height;
        this.mHeroThumbWidthInPixel = width;
        int heroIndex = getHeroIndex();
        for (int i = 0; i < getChildCount(); i++) {
            if (heroIndex == i) {
                View child = getChildAt(i);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                boolean changed = false;
                if (lp.height != height) {
                    lp.height = height;
                    changed = true;
                }
                if (lp.width != width) {
                    lp.width = width;
                    changed = true;
                }
                if (changed) {
                    child.setLayoutParams(lp);
                }
            }
        }
    }

    public void setThumbSpace(int spaceInPixel) {
        this.mMeasuredMarginInPixel = spaceInPixel;
        requestLayout();
    }

    public void setNumberOfThumbs(int numOfThumbs) {
        this.mIsUserSets = true;
        this.mNumOfThumbs = numOfThumbs;
        setNumberOfThumbsInternal();
    }

    private void setNumberOfThumbsInternal() {
        while (getChildCount() > this.mNumOfThumbs) {
            removeView(getChildAt(getChildCount() - 1));
        }
        while (getChildCount() < this.mNumOfThumbs) {
            addView(createThumbView(this), new LinearLayout.LayoutParams(this.mThumbWidthInPixel, this.mThumbHeightInPixel));
        }
        int heroIndex = getHeroIndex();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
            if (heroIndex == i) {
                lp.width = this.mHeroThumbWidthInPixel;
                lp.height = this.mHeroThumbHeightInPixel;
            } else {
                lp.width = this.mThumbWidthInPixel;
                lp.height = this.mThumbHeightInPixel;
            }
            child.setLayoutParams(lp);
        }
    }

    private int calculateNumOfThumbs(int widthInPixel) {
        int nonHeroThumbNum = roundUp(widthInPixel - this.mHeroThumbWidthInPixel, this.mThumbWidthInPixel + this.mMeasuredMarginInPixel);
        if (nonHeroThumbNum < 2) {
            nonHeroThumbNum = 2;
        } else if ((nonHeroThumbNum & 1) != 0) {
            nonHeroThumbNum++;
        }
        return nonHeroThumbNum + 1;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int numOfThumbs;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (!this.mIsUserSets && this.mNumOfThumbs != (numOfThumbs = calculateNumOfThumbs(width))) {
            this.mNumOfThumbs = numOfThumbs;
            setNumberOfThumbsInternal();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int heroIndex = getHeroIndex();
        View heroView = getChildAt(heroIndex);
        int heroLeft = (getWidth() / 2) - (heroView.getMeasuredWidth() / 2);
        int heroRight = (getWidth() / 2) + (heroView.getMeasuredWidth() / 2);
        heroView.layout(heroLeft, getPaddingTop(), heroRight, getPaddingTop() + heroView.getMeasuredHeight());
        int heroCenter = getPaddingTop() + (heroView.getMeasuredHeight() / 2);
        for (int i = heroIndex - 1; i >= 0; i--) {
            int heroLeft2 = heroLeft - this.mMeasuredMarginInPixel;
            View child = getChildAt(i);
            child.layout(heroLeft2 - child.getMeasuredWidth(), heroCenter - (child.getMeasuredHeight() / 2), heroLeft2, (child.getMeasuredHeight() / 2) + heroCenter);
            heroLeft = heroLeft2 - child.getMeasuredWidth();
        }
        for (int i2 = heroIndex + 1; i2 < this.mNumOfThumbs; i2++) {
            int heroRight2 = heroRight + this.mMeasuredMarginInPixel;
            View child2 = getChildAt(i2);
            child2.layout(heroRight2, heroCenter - (child2.getMeasuredHeight() / 2), child2.getMeasuredWidth() + heroRight2, (child2.getMeasuredHeight() / 2) + heroCenter);
            heroRight = heroRight2 + child2.getMeasuredWidth();
        }
    }

    /* access modifiers changed from: protected */
    public View createThumbView(ViewGroup parent) {
        return new ImageView(parent.getContext());
    }

    public void clearThumbBitmaps() {
        for (int i = 0; i < getChildCount(); i++) {
            setThumbBitmap(i, null);
        }
        this.mBitmaps.clear();
    }

    public Bitmap getThumbBitmap(int index) {
        return this.mBitmaps.get(index);
    }

    public void setThumbBitmap(int index, Bitmap bitmap) {
        this.mBitmaps.put(index, bitmap);
        ((ImageView) getChildAt(index)).setImageBitmap(bitmap);
    }
}
