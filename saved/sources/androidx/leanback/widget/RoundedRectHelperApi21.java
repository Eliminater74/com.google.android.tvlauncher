package androidx.leanback.widget;

import android.graphics.Outline;
import android.support.annotation.RequiresApi;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewOutlineProvider;

@RequiresApi(21)
class RoundedRectHelperApi21 {
    private static final int MAX_CACHED_PROVIDER = 32;
    private static SparseArray<ViewOutlineProvider> sRoundedRectProvider;

    static final class RoundedRectOutlineProvider extends ViewOutlineProvider {
        private int mRadius;

        RoundedRectOutlineProvider(int radius) {
            this.mRadius = radius;
        }

        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) this.mRadius);
            outline.setAlpha(1.0f);
        }
    }

    public static void setClipToRoundedOutline(View view, boolean clip, int roundedCornerRadius) {
        if (clip) {
            if (sRoundedRectProvider == null) {
                sRoundedRectProvider = new SparseArray<>();
            }
            ViewOutlineProvider provider = sRoundedRectProvider.get(roundedCornerRadius);
            if (provider == null) {
                provider = new RoundedRectOutlineProvider(roundedCornerRadius);
                if (sRoundedRectProvider.size() < 32) {
                    sRoundedRectProvider.put(roundedCornerRadius, provider);
                }
            }
            view.setOutlineProvider(provider);
        } else {
            view.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        }
        view.setClipToOutline(clip);
    }

    private RoundedRectHelperApi21() {
    }
}
