package androidx.leanback.app;

import android.view.SurfaceHolder;
import androidx.leanback.media.SurfaceHolderGlueHost;

@Deprecated
public class VideoFragmentGlueHost extends PlaybackFragmentGlueHost implements SurfaceHolderGlueHost {
    private final VideoFragment mFragment;

    public VideoFragmentGlueHost(VideoFragment fragment) {
        super(fragment);
        this.mFragment = fragment;
    }

    public void setSurfaceHolderCallback(SurfaceHolder.Callback callback) {
        this.mFragment.setSurfaceHolderCallback(callback);
    }
}
