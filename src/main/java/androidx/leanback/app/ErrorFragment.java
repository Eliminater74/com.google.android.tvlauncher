package androidx.leanback.app;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.C0364R;

@Deprecated
public class ErrorFragment extends BrandedFragment {
    private Drawable mBackgroundDrawable;
    private Button mButton;
    private View.OnClickListener mButtonClickListener;
    private String mButtonText;
    private Drawable mDrawable;
    private ViewGroup mErrorFrame;
    private ImageView mImageView;
    private boolean mIsBackgroundTranslucent = true;
    private CharSequence mMessage;
    private TextView mTextView;

    public void setDefaultBackground(boolean translucent) {
        this.mBackgroundDrawable = null;
        this.mIsBackgroundTranslucent = translucent;
        updateBackground();
        updateMessage();
    }

    public boolean isBackgroundTranslucent() {
        return this.mIsBackgroundTranslucent;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.mBackgroundDrawable = drawable;
        if (drawable != null) {
            int opacity = drawable.getOpacity();
            this.mIsBackgroundTranslucent = opacity == -3 || opacity == -2;
        }
        updateBackground();
        updateMessage();
    }

    public Drawable getBackgroundDrawable() {
        return this.mBackgroundDrawable;
    }

    public void setImageDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        updateImageDrawable();
    }

    public Drawable getImageDrawable() {
        return this.mDrawable;
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
        updateMessage();
    }

    public CharSequence getMessage() {
        return this.mMessage;
    }

    public void setButtonText(String text) {
        this.mButtonText = text;
        updateButton();
    }

    public String getButtonText() {
        return this.mButtonText;
    }

    public void setButtonClickListener(View.OnClickListener clickListener) {
        this.mButtonClickListener = clickListener;
        updateButton();
    }

    public View.OnClickListener getButtonClickListener() {
        return this.mButtonClickListener;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(C0364R.layout.lb_error_fragment, container, false);
        this.mErrorFrame = (ViewGroup) root.findViewById(C0364R.C0366id.error_frame);
        updateBackground();
        installTitleView(inflater, this.mErrorFrame, savedInstanceState);
        this.mImageView = (ImageView) root.findViewById(C0364R.C0366id.image);
        updateImageDrawable();
        this.mTextView = (TextView) root.findViewById(C0364R.C0366id.message);
        updateMessage();
        this.mButton = (Button) root.findViewById(C0364R.C0366id.button);
        updateButton();
        Paint.FontMetricsInt metrics = getFontMetricsInt(this.mTextView);
        setTopMargin(this.mTextView, metrics.ascent + container.getResources().getDimensionPixelSize(C0364R.dimen.lb_error_under_image_baseline_margin));
        setTopMargin(this.mButton, container.getResources().getDimensionPixelSize(C0364R.dimen.lb_error_under_message_baseline_margin) - metrics.descent);
        return root;
    }

    private void updateBackground() {
        ViewGroup viewGroup = this.mErrorFrame;
        if (viewGroup != null) {
            Drawable drawable = this.mBackgroundDrawable;
            if (drawable != null) {
                viewGroup.setBackground(drawable);
            } else {
                viewGroup.setBackgroundColor(viewGroup.getResources().getColor(this.mIsBackgroundTranslucent ? C0364R.color.lb_error_background_color_translucent : C0364R.color.lb_error_background_color_opaque));
            }
        }
    }

    private void updateMessage() {
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setText(this.mMessage);
            this.mTextView.setVisibility(TextUtils.isEmpty(this.mMessage) ? 8 : 0);
        }
    }

    private void updateImageDrawable() {
        ImageView imageView = this.mImageView;
        if (imageView != null) {
            imageView.setImageDrawable(this.mDrawable);
            this.mImageView.setVisibility(this.mDrawable == null ? 8 : 0);
        }
    }

    private void updateButton() {
        Button button = this.mButton;
        if (button != null) {
            button.setText(this.mButtonText);
            this.mButton.setOnClickListener(this.mButtonClickListener);
            this.mButton.setVisibility(TextUtils.isEmpty(this.mButtonText) ? 8 : 0);
            this.mButton.requestFocus();
        }
    }

    public void onStart() {
        super.onStart();
        this.mErrorFrame.requestFocus();
    }

    private static Paint.FontMetricsInt getFontMetricsInt(TextView textView) {
        Paint paint = new Paint(1);
        paint.setTextSize(textView.getTextSize());
        paint.setTypeface(textView.getTypeface());
        return paint.getFontMetricsInt();
    }

    private static void setTopMargin(TextView textView, int topMargin) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
        lp.topMargin = topMargin;
        textView.setLayoutParams(lp);
    }
}
