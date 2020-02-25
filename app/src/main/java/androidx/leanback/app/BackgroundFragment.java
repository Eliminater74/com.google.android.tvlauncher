package androidx.leanback.app;

import android.app.Fragment;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class BackgroundFragment extends Fragment {
    private BackgroundManager mBackgroundManager;

    /* access modifiers changed from: package-private */
    public BackgroundManager getBackgroundManager() {
        return this.mBackgroundManager;
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundManager(BackgroundManager backgroundManager) {
        this.mBackgroundManager = backgroundManager;
    }

    public void onStart() {
        super.onStart();
        BackgroundManager backgroundManager = this.mBackgroundManager;
        if (backgroundManager != null) {
            backgroundManager.onActivityStart();
        }
    }

    public void onResume() {
        super.onResume();
        BackgroundManager backgroundManager = this.mBackgroundManager;
        if (backgroundManager != null) {
            backgroundManager.onResume();
        }
    }

    public void onStop() {
        BackgroundManager backgroundManager = this.mBackgroundManager;
        if (backgroundManager != null) {
            backgroundManager.onStop();
        }
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        BackgroundManager backgroundManager = this.mBackgroundManager;
        if (backgroundManager != null) {
            backgroundManager.detach();
        }
    }
}
