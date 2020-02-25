package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.view.View;

import androidx.leanback.C0364R;

@RestrictTo({RestrictTo.Scope.LIBRARY})
class MediaRowFocusView extends View {
    private final Paint mPaint;
    private final RectF mRoundRectF = new RectF();
    private int mRoundRectRadius;

    public MediaRowFocusView(Context context) {
        super(context);
        this.mPaint = createPaint(context);
    }

    public MediaRowFocusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPaint = createPaint(context);
    }

    public MediaRowFocusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPaint = createPaint(context);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mRoundRectRadius = getHeight() / 2;
        int drawOffset = ((this.mRoundRectRadius * 2) - getHeight()) / 2;
        this.mRoundRectF.set(0.0f, (float) (-drawOffset), (float) getWidth(), (float) (getHeight() + drawOffset));
        RectF rectF = this.mRoundRectF;
        int i = this.mRoundRectRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, this.mPaint);
    }

    private Paint createPaint(Context context) {
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(C0364R.color.lb_playback_media_row_highlight_color));
        return paint;
    }

    public int getRoundRectRadius() {
        return this.mRoundRectRadius;
    }
}
