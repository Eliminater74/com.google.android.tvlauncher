package android.support.p004v7.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

/* renamed from: android.support.v7.widget.AppCompatToggleButton */
public class AppCompatToggleButton extends ToggleButton {
    private final AppCompatTextHelper mTextHelper;

    public AppCompatToggleButton(Context context) {
        this(context, null);
    }

    public AppCompatToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 16842827);
    }

    public AppCompatToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTextHelper = new AppCompatTextHelper(this);
        this.mTextHelper.loadFromAttributes(attrs, defStyleAttr);
    }
}
