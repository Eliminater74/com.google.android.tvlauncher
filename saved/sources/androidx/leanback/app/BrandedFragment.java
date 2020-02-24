package androidx.leanback.app;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.C0364R;
import androidx.leanback.widget.SearchOrbView;
import androidx.leanback.widget.TitleHelper;
import androidx.leanback.widget.TitleViewAdapter;

@Deprecated
public class BrandedFragment extends Fragment {
    private static final String TITLE_SHOW = "titleShow";
    private Drawable mBadgeDrawable;
    private View.OnClickListener mExternalOnSearchClickedListener;
    private boolean mSearchAffordanceColorSet;
    private SearchOrbView.Colors mSearchAffordanceColors;
    private boolean mShowingTitle = true;
    private CharSequence mTitle;
    private TitleHelper mTitleHelper;
    private View mTitleView;
    private TitleViewAdapter mTitleViewAdapter;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onInflateTitleView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        TypedValue typedValue = new TypedValue();
        return inflater.inflate(parent.getContext().getTheme().resolveAttribute(C0364R.attr.browseTitleViewLayout, typedValue, true) ? typedValue.resourceId : C0364R.layout.lb_browse_title, parent, false);
    }

    public void installTitleView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View titleLayoutRoot = onInflateTitleView(inflater, parent, savedInstanceState);
        if (titleLayoutRoot != null) {
            parent.addView(titleLayoutRoot);
            setTitleView(titleLayoutRoot.findViewById(C0364R.C0366id.browse_title_group));
            return;
        }
        setTitleView(null);
    }

    public void setTitleView(View titleView) {
        this.mTitleView = titleView;
        View view = this.mTitleView;
        if (view == null) {
            this.mTitleViewAdapter = null;
            this.mTitleHelper = null;
            return;
        }
        this.mTitleViewAdapter = ((TitleViewAdapter.Provider) view).getTitleViewAdapter();
        this.mTitleViewAdapter.setTitle(this.mTitle);
        this.mTitleViewAdapter.setBadgeDrawable(this.mBadgeDrawable);
        if (this.mSearchAffordanceColorSet) {
            this.mTitleViewAdapter.setSearchAffordanceColors(this.mSearchAffordanceColors);
        }
        View.OnClickListener onClickListener = this.mExternalOnSearchClickedListener;
        if (onClickListener != null) {
            setOnSearchClickedListener(onClickListener);
        }
        if (getView() instanceof ViewGroup) {
            this.mTitleHelper = new TitleHelper((ViewGroup) getView(), this.mTitleView);
        }
    }

    public View getTitleView() {
        return this.mTitleView;
    }

    public TitleViewAdapter getTitleViewAdapter() {
        return this.mTitleViewAdapter;
    }

    /* access modifiers changed from: package-private */
    public TitleHelper getTitleHelper() {
        return this.mTitleHelper;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(TITLE_SHOW, this.mShowingTitle);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            this.mShowingTitle = savedInstanceState.getBoolean(TITLE_SHOW);
        }
        View view2 = this.mTitleView;
        if (view2 != null && (view instanceof ViewGroup)) {
            this.mTitleHelper = new TitleHelper((ViewGroup) view, view2);
            this.mTitleHelper.showTitle(this.mShowingTitle);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mTitleHelper = null;
    }

    public void showTitle(boolean show) {
        if (show != this.mShowingTitle) {
            this.mShowingTitle = show;
            TitleHelper titleHelper = this.mTitleHelper;
            if (titleHelper != null) {
                titleHelper.showTitle(show);
            }
        }
    }

    public void showTitle(int flags) {
        TitleViewAdapter titleViewAdapter = this.mTitleViewAdapter;
        if (titleViewAdapter != null) {
            titleViewAdapter.updateComponentsVisibility(flags);
        }
        showTitle(true);
    }

    public void setBadgeDrawable(Drawable drawable) {
        if (this.mBadgeDrawable != drawable) {
            this.mBadgeDrawable = drawable;
            TitleViewAdapter titleViewAdapter = this.mTitleViewAdapter;
            if (titleViewAdapter != null) {
                titleViewAdapter.setBadgeDrawable(drawable);
            }
        }
    }

    public Drawable getBadgeDrawable() {
        return this.mBadgeDrawable;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
        TitleViewAdapter titleViewAdapter = this.mTitleViewAdapter;
        if (titleViewAdapter != null) {
            titleViewAdapter.setTitle(title);
        }
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public void setOnSearchClickedListener(View.OnClickListener listener) {
        this.mExternalOnSearchClickedListener = listener;
        TitleViewAdapter titleViewAdapter = this.mTitleViewAdapter;
        if (titleViewAdapter != null) {
            titleViewAdapter.setOnSearchClickedListener(listener);
        }
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
        this.mSearchAffordanceColors = colors;
        this.mSearchAffordanceColorSet = true;
        TitleViewAdapter titleViewAdapter = this.mTitleViewAdapter;
        if (titleViewAdapter != null) {
            titleViewAdapter.setSearchAffordanceColors(this.mSearchAffordanceColors);
        }
    }

    public SearchOrbView.Colors getSearchAffordanceColors() {
        if (this.mSearchAffordanceColorSet) {
            return this.mSearchAffordanceColors;
        }
        TitleViewAdapter titleViewAdapter = this.mTitleViewAdapter;
        if (titleViewAdapter != null) {
            return titleViewAdapter.getSearchAffordanceColors();
        }
        throw new IllegalStateException("Fragment views not yet created");
    }

    public void setSearchAffordanceColor(int color) {
        setSearchAffordanceColors(new SearchOrbView.Colors(color));
    }

    public int getSearchAffordanceColor() {
        return getSearchAffordanceColors().color;
    }

    public void onStart() {
        super.onStart();
        if (this.mTitleViewAdapter != null) {
            showTitle(this.mShowingTitle);
            this.mTitleViewAdapter.setAnimationEnabled(true);
        }
    }

    public void onPause() {
        TitleViewAdapter titleViewAdapter = this.mTitleViewAdapter;
        if (titleViewAdapter != null) {
            titleViewAdapter.setAnimationEnabled(false);
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        TitleViewAdapter titleViewAdapter = this.mTitleViewAdapter;
        if (titleViewAdapter != null) {
            titleViewAdapter.setAnimationEnabled(true);
        }
    }

    public final boolean isShowingTitle() {
        return this.mShowingTitle;
    }
}
