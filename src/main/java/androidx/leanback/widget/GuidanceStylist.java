package androidx.leanback.widget;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.C0364R;
import java.util.List;

public class GuidanceStylist implements FragmentAnimationProvider {
    private TextView mBreadcrumbView;
    private TextView mDescriptionView;
    private View mGuidanceContainer;
    private ImageView mIconView;
    private TextView mTitleView;

    public static class Guidance {
        private final String mBreadcrumb;
        private final String mDescription;
        private final Drawable mIconDrawable;
        private final String mTitle;

        public Guidance(String title, String description, String breadcrumb, Drawable icon) {
            this.mBreadcrumb = breadcrumb;
            this.mTitle = title;
            this.mDescription = description;
            this.mIconDrawable = icon;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public String getDescription() {
            return this.mDescription;
        }

        public String getBreadcrumb() {
            return this.mBreadcrumb;
        }

        public Drawable getIconDrawable() {
            return this.mIconDrawable;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Guidance guidance) {
        View guidanceView = inflater.inflate(onProvideLayoutId(), container, false);
        this.mTitleView = (TextView) guidanceView.findViewById(C0364R.C0366id.guidance_title);
        this.mBreadcrumbView = (TextView) guidanceView.findViewById(C0364R.C0366id.guidance_breadcrumb);
        this.mDescriptionView = (TextView) guidanceView.findViewById(C0364R.C0366id.guidance_description);
        this.mIconView = (ImageView) guidanceView.findViewById(C0364R.C0366id.guidance_icon);
        this.mGuidanceContainer = guidanceView.findViewById(C0364R.C0366id.guidance_container);
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(guidance.getTitle());
        }
        TextView textView2 = this.mBreadcrumbView;
        if (textView2 != null) {
            textView2.setText(guidance.getBreadcrumb());
        }
        TextView textView3 = this.mDescriptionView;
        if (textView3 != null) {
            textView3.setText(guidance.getDescription());
        }
        if (this.mIconView != null) {
            if (guidance.getIconDrawable() != null) {
                this.mIconView.setImageDrawable(guidance.getIconDrawable());
            } else {
                this.mIconView.setVisibility(8);
            }
        }
        View view = this.mGuidanceContainer;
        if (view != null && TextUtils.isEmpty(view.getContentDescription())) {
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(guidance.getBreadcrumb())) {
                builder.append(guidance.getBreadcrumb());
                builder.append(10);
            }
            if (!TextUtils.isEmpty(guidance.getTitle())) {
                builder.append(guidance.getTitle());
                builder.append(10);
            }
            if (!TextUtils.isEmpty(guidance.getDescription())) {
                builder.append(guidance.getDescription());
                builder.append(10);
            }
            this.mGuidanceContainer.setContentDescription(builder);
        }
        return guidanceView;
    }

    public void onDestroyView() {
        this.mBreadcrumbView = null;
        this.mDescriptionView = null;
        this.mIconView = null;
        this.mTitleView = null;
    }

    public int onProvideLayoutId() {
        return C0364R.layout.lb_guidance;
    }

    public TextView getTitleView() {
        return this.mTitleView;
    }

    public TextView getDescriptionView() {
        return this.mDescriptionView;
    }

    public TextView getBreadcrumbView() {
        return this.mBreadcrumbView;
    }

    public ImageView getIconView() {
        return this.mIconView;
    }

    public void onImeAppearing(@NonNull List<Animator> list) {
    }

    public void onImeDisappearing(@NonNull List<Animator> list) {
    }
}
