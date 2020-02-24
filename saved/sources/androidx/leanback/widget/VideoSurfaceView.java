package androidx.leanback.widget;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.view.SurfaceView;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class VideoSurfaceView extends SurfaceView {
    public VideoSurfaceView(Context context) {
        super(context);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTransitionVisibility(int visibility) {
    }
}
