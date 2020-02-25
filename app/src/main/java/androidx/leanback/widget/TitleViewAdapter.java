package androidx.leanback.widget;

import android.graphics.drawable.Drawable;
import android.view.View;

public abstract class TitleViewAdapter {
    public static final int BRANDING_VIEW_VISIBLE = 2;
    public static final int FULL_VIEW_VISIBLE = 6;
    public static final int SEARCH_VIEW_VISIBLE = 4;

    public abstract View getSearchAffordanceView();

    public CharSequence getTitle() {
        return null;
    }

    public void setTitle(CharSequence titleText) {
    }

    public Drawable getBadgeDrawable() {
        return null;
    }

    public void setBadgeDrawable(Drawable drawable) {
    }

    public void setOnSearchClickedListener(View.OnClickListener listener) {
        View view = getSearchAffordanceView();
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    public SearchOrbView.Colors getSearchAffordanceColors() {
        return null;
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
    }

    public void setAnimationEnabled(boolean enable) {
    }

    public void updateComponentsVisibility(int flags) {
    }

    public interface Provider {
        TitleViewAdapter getTitleViewAdapter();
    }
}
