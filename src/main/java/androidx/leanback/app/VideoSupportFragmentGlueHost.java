package androidx.leanback.app;

import android.view.SurfaceHolder;
import androidx.leanback.media.SurfaceHolderGlueHost;

public class VideoSupportFragmentGlueHost extends PlaybackSupportFragmentGlueHost implements SurfaceHolderGlueHost {
    private final VideoSupportFragment mFragment;

    public VideoSupportFragmentGlueHost(VideoSupportFragment fragment) {
        super(fragment);
        this.mFragment = fragment;
    }

    public void setSurfaceHolderCallback(SurfaceHolder.Callback callback) {
        this.mFragment.setSurfaceHolderCallback(callback);
    }
}
