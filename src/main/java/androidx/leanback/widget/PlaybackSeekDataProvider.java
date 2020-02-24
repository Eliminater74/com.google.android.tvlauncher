package androidx.leanback.widget;

import android.graphics.Bitmap;

public class PlaybackSeekDataProvider {

    public static class ResultCallback {
        public void onThumbnailLoaded(Bitmap bitmap, int index) {
        }
    }

    public long[] getSeekPositions() {
        return null;
    }

    public void getThumbnail(int index, ResultCallback callback) {
    }

    public void reset() {
    }
}
