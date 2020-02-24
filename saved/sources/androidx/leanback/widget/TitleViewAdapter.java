package androidx.leanback.widget;

import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.leanback.widget.SearchOrbView;

public abstract class TitleViewAdapter {
    public static final int BRANDING_VIEW_VISIBLE = 2;
    public static final int FULL_VIEW_VISIBLE = 6;
    public static final int SEARCH_VIEW_VISIBLE = 4;

    public interface Provider {
        TitleViewAdapter getTitleViewAdapter();
    }

    public abstract View getSearchAffordanceView();

    public void setTitle(CharSequence titleText) {
    }

    public CharSequence getTitle() {
        return null;
    }

    public void setBadgeDrawable(Drawable drawable) {
    }

    public Drawable getBadgeDrawable() {
        return null;
    }

    public void setOnSearchClickedListener(View.OnClickListener listener) {
        View view = getSearchAffordanceView();
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
    }

    public SearchOrbView.Colors getSearchAffordanceColors() {
        return null;
    }

    public void setAnimationEnabled(boolean enable) {
    }

    public void updateComponentsVisibility(int flags) {
    }
}
