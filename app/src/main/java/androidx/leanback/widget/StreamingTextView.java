package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.p001v4.widget.TextViewCompat;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;

import androidx.leanback.C0364R;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StreamingTextView extends EditText {
    static final boolean ANIMATE_DOTS_FOR_PENDING = true;
    private static final String ACCESSIBILITY_CLASS_NAME = "androidx.leanback.widget.StreamingTextView";
    private static final boolean DEBUG = false;
    private static final boolean DOTS_FOR_PENDING = true;
    private static final boolean DOTS_FOR_STABLE = false;
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\S+");
    private static final Property<StreamingTextView, Integer> STREAM_POSITION_PROPERTY = new Property<StreamingTextView, Integer>(Integer.class, "streamPosition") {
        public Integer get(StreamingTextView view) {
            return Integer.valueOf(view.getStreamPosition());
        }

        public void set(StreamingTextView view, Integer value) {
            view.setStreamPosition(value.intValue());
        }
    };
    private static final long STREAM_UPDATE_DELAY_MILLIS = 50;
    private static final String TAG = "StreamingTextView";
    private static final float TEXT_DOT_SCALE = 1.3f;
    final Random mRandom = new Random();
    Bitmap mOneDot;
    int mStreamPosition;
    Bitmap mTwoDot;
    private ObjectAnimator mStreamingAnimation;

    public StreamingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StreamingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static boolean isLayoutRtl(View view) {
        if (Build.VERSION.SDK_INT < 17 || 1 != view.getLayoutDirection()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mOneDot = getScaledBitmap(C0364R.C0365drawable.lb_text_dot_one, TEXT_DOT_SCALE);
        this.mTwoDot = getScaledBitmap(C0364R.C0365drawable.lb_text_dot_two, TEXT_DOT_SCALE);
        reset();
    }

    private Bitmap getScaledBitmap(int resourceId, float scaled) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        return Bitmap.createScaledBitmap(bitmap, (int) (((float) bitmap.getWidth()) * scaled), (int) (((float) bitmap.getHeight()) * scaled), false);
    }

    public void reset() {
        this.mStreamPosition = -1;
        cancelStreamAnimation();
        setText("");
    }

    public void updateRecognizedText(String stableText, String pendingText) {
        if (stableText == null) {
            stableText = "";
        }
        SpannableStringBuilder displayText = new SpannableStringBuilder(stableText);
        if (pendingText != null) {
            int pendingTextStart = displayText.length();
            displayText.append((CharSequence) pendingText);
            addDottySpans(displayText, pendingText, pendingTextStart);
        }
        this.mStreamPosition = Math.max(stableText.length(), this.mStreamPosition);
        updateText(new SpannedString(displayText));
        startStreamAnimation();
    }

    /* access modifiers changed from: package-private */
    public int getStreamPosition() {
        return this.mStreamPosition;
    }

    /* access modifiers changed from: package-private */
    public void setStreamPosition(int streamPosition) {
        this.mStreamPosition = streamPosition;
        invalidate();
    }

    private void startStreamAnimation() {
        cancelStreamAnimation();
        int pos = getStreamPosition();
        int totalLen = length();
        int animLen = totalLen - pos;
        if (animLen > 0) {
            if (this.mStreamingAnimation == null) {
                this.mStreamingAnimation = new ObjectAnimator();
                this.mStreamingAnimation.setTarget(this);
                this.mStreamingAnimation.setProperty(STREAM_POSITION_PROPERTY);
            }
            this.mStreamingAnimation.setIntValues(pos, totalLen);
            this.mStreamingAnimation.setDuration(((long) animLen) * STREAM_UPDATE_DELAY_MILLIS);
            this.mStreamingAnimation.start();
        }
    }

    private void cancelStreamAnimation() {
        ObjectAnimator objectAnimator = this.mStreamingAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    private void addDottySpans(SpannableStringBuilder displayText, String text, int textStart) {
        Matcher m = SPLIT_PATTERN.matcher(text);
        while (m.find()) {
            int wordStart = m.start() + textStart;
            displayText.setSpan(new DottySpan(text.charAt(m.start()), wordStart), wordStart, m.end() + textStart, 33);
        }
    }

    private void addColorSpan(SpannableStringBuilder displayText, int color, String text, int textStart) {
        displayText.setSpan(new ForegroundColorSpan(color), textStart, text.length() + textStart, 33);
    }

    public void setFinalRecognizedText(CharSequence finalText) {
        updateText(finalText);
    }

    private void updateText(CharSequence displayText) {
        setText(displayText);
        bringPointIntoView(length());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    public void updateRecognizedText(String stableText, List<Float> list) {
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, actionModeCallback));
    }

    private class DottySpan extends ReplacementSpan {
        private final int mPosition;
        private final int mSeed;

        public DottySpan(int seed, int pos) {
            this.mSeed = seed;
            this.mPosition = pos;
        }

        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            Canvas canvas2 = canvas;
            Paint paint2 = paint;
            int width = (int) paint2.measureText(text, start, end);
            int dotWidth = StreamingTextView.this.mOneDot.getWidth();
            int sliceWidth = dotWidth * 2;
            int sliceCount = width / sliceWidth;
            int prop = (width % sliceWidth) / 2;
            boolean rtl = StreamingTextView.isLayoutRtl(StreamingTextView.this);
            StreamingTextView.this.mRandom.setSeed((long) this.mSeed);
            int oldAlpha = paint.getAlpha();
            int i = 0;
            while (i < sliceCount && this.mPosition + i < StreamingTextView.this.mStreamPosition) {
                float left = (float) ((i * sliceWidth) + prop + (dotWidth / 2));
                float dotLeft = rtl ? ((x + ((float) width)) - left) - ((float) dotWidth) : x + left;
                paint2.setAlpha((StreamingTextView.this.mRandom.nextInt(4) + 1) * 63);
                if (StreamingTextView.this.mRandom.nextBoolean()) {
                    canvas2.drawBitmap(StreamingTextView.this.mTwoDot, dotLeft, (float) (y - StreamingTextView.this.mTwoDot.getHeight()), paint2);
                } else {
                    canvas2.drawBitmap(StreamingTextView.this.mOneDot, dotLeft, (float) (y - StreamingTextView.this.mOneDot.getHeight()), paint2);
                }
                i++;
            }
            paint2.setAlpha(oldAlpha);
        }

        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetricsInt) {
            return (int) paint.measureText(text, start, end);
        }
    }
}
