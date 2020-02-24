package androidx.leanback.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.C0364R;
import androidx.leanback.widget.SearchOrbView;
import androidx.leanback.widget.TitleViewAdapter;

public class TitleView extends FrameLayout implements TitleViewAdapter.Provider {
    private int flags;
    private ImageView mBadgeView;
    private boolean mHasSearchListener;
    private SearchOrbView mSearchOrbView;
    private TextView mTextView;
    private final TitleViewAdapter mTitleViewAdapter;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, C0364R.attr.browseTitleViewStyle);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.flags = 6;
        this.mHasSearchListener = false;
        this.mTitleViewAdapter = new TitleViewAdapter() {
            public View getSearchAffordanceView() {
                return TitleView.this.getSearchAffordanceView();
            }

            public void setOnSearchClickedListener(View.OnClickListener listener) {
                TitleView.this.setOnSearchClickedListener(listener);
            }

            public void setAnimationEnabled(boolean enable) {
                TitleView.this.enableAnimation(enable);
            }

            public Drawable getBadgeDrawable() {
                return TitleView.this.getBadgeDrawable();
            }

            public SearchOrbView.Colors getSearchAffordanceColors() {
                return TitleView.this.getSearchAffordanceColors();
            }

            public CharSequence getTitle() {
                return TitleView.this.getTitle();
            }

            public void setBadgeDrawable(Drawable drawable) {
                TitleView.this.setBadgeDrawable(drawable);
            }

            public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
                TitleView.this.setSearchAffordanceColors(colors);
            }

            public void setTitle(CharSequence titleText) {
                TitleView.this.setTitle(titleText);
            }

            public void updateComponentsVisibility(int flags) {
                TitleView.this.updateComponentsVisibility(flags);
            }
        };
        View rootView = LayoutInflater.from(context).inflate(C0364R.layout.lb_title_view, this);
        this.mBadgeView = (ImageView) rootView.findViewById(C0364R.C0366id.title_badge);
        this.mTextView = (TextView) rootView.findViewById(C0364R.C0366id.title_text);
        this.mSearchOrbView = (SearchOrbView) rootView.findViewById(C0364R.C0366id.title_orb);
        setClipToPadding(false);
        setClipChildren(false);
    }

    public void setTitle(CharSequence titleText) {
        this.mTextView.setText(titleText);
        updateBadgeVisibility();
    }

    public CharSequence getTitle() {
        return this.mTextView.getText();
    }

    public void setBadgeDrawable(Drawable drawable) {
        this.mBadgeView.setImageDrawable(drawable);
        updateBadgeVisibility();
    }

    public Drawable getBadgeDrawable() {
        return this.mBadgeView.getDrawable();
    }

    public void setOnSearchClickedListener(View.OnClickListener listener) {
        this.mHasSearchListener = listener != null;
        this.mSearchOrbView.setOnOrbClickedListener(listener);
        updateSearchOrbViewVisiblity();
    }

    public View getSearchAffordanceView() {
        return this.mSearchOrbView;
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
        this.mSearchOrbView.setOrbColors(colors);
    }

    public SearchOrbView.Colors getSearchAffordanceColors() {
        return this.mSearchOrbView.getOrbColors();
    }

    public void enableAnimation(boolean enable) {
        SearchOrbView searchOrbView = this.mSearchOrbView;
        searchOrbView.enableOrbColorAnimation(enable && searchOrbView.hasFocus());
    }

    public void updateComponentsVisibility(int flags2) {
        this.flags = flags2;
        if ((flags2 & 2) == 2) {
            updateBadgeVisibility();
        } else {
            this.mBadgeView.setVisibility(8);
            this.mTextView.setVisibility(8);
        }
        updateSearchOrbViewVisiblity();
    }

    private void updateSearchOrbViewVisiblity() {
        int visibility = 4;
        if (this.mHasSearchListener && (this.flags & 4) == 4) {
            visibility = 0;
        }
        this.mSearchOrbView.setVisibility(visibility);
    }

    private void updateBadgeVisibility() {
        if (this.mBadgeView.getDrawable() != null) {
            this.mBadgeView.setVisibility(0);
            this.mTextView.setVisibility(8);
            return;
        }
        this.mBadgeView.setVisibility(8);
        this.mTextView.setVisibility(0);
    }

    public TitleViewAdapter getTitleViewAdapter() {
        return this.mTitleViewAdapter;
    }
}
