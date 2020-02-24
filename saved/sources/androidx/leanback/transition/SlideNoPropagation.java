package androidx.leanback.transition;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.transition.Slide;
import android.util.AttributeSet;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class SlideNoPropagation extends Slide {
    public SlideNoPropagation() {
    }

    public SlideNoPropagation(int slideEdge) {
        super(slideEdge);
    }

    public SlideNoPropagation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSlideEdge(int slideEdge) {
        super.setSlideEdge(slideEdge);
        setPropagation(null);
    }
}
