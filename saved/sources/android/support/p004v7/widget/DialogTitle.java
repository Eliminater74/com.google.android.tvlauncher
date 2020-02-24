package android.support.p004v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.p004v7.appcompat.C0233R;
import android.text.Layout;
import android.util.AttributeSet;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v7.widget.DialogTitle */
public class DialogTitle extends AppCompatTextView {
    public DialogTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DialogTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogTitle(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int lineCount;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        if (layout != null && (lineCount = layout.getLineCount()) > 0 && layout.getEllipsisCount(lineCount - 1) > 0) {
            setSingleLine(false);
            setMaxLines(2);
            TypedArray a = getContext().obtainStyledAttributes(null, C0233R.styleable.TextAppearance, 16842817, 16973892);
            int textSize = a.getDimensionPixelSize(C0233R.styleable.TextAppearance_android_textSize, 0);
            if (textSize != 0) {
                setTextSize(0, (float) textSize);
            }
            a.recycle();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
