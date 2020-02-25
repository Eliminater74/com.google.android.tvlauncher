package android.support.p001v4.media;

import android.media.VolumeProvider;
import android.os.Build;
import android.support.annotation.RestrictTo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v4.media.VolumeProviderCompat */
public abstract class VolumeProviderCompat {
    public static final int VOLUME_CONTROL_ABSOLUTE = 2;
    public static final int VOLUME_CONTROL_FIXED = 0;
    public static final int VOLUME_CONTROL_RELATIVE = 1;
    private final int mControlType;
    private final int mMaxVolume;
    private Callback mCallback;
    private int mCurrentVolume;
    private VolumeProvider mVolumeProviderFwk;

    public VolumeProviderCompat(int volumeControl, int maxVolume, int currentVolume) {
        this.mControlType = volumeControl;
        this.mMaxVolume = maxVolume;
        this.mCurrentVolume = currentVolume;
    }

    public final int getCurrentVolume() {
        return this.mCurrentVolume;
    }

    public final void setCurrentVolume(int currentVolume) {
        this.mCurrentVolume = currentVolume;
        if (Build.VERSION.SDK_INT >= 21) {
            ((VolumeProvider) getVolumeProvider()).setCurrentVolume(currentVolume);
        }
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onVolumeChanged(this);
        }
    }

    public final int getVolumeControl() {
        return this.mControlType;
    }

    public final int getMaxVolume() {
        return this.mMaxVolume;
    }

    public void onSetVolumeTo(int volume) {
    }

    public void onAdjustVolume(int direction) {
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public Object getVolumeProvider() {
        if (this.mVolumeProviderFwk == null && Build.VERSION.SDK_INT >= 21) {
            this.mVolumeProviderFwk = new VolumeProvider(this.mControlType, this.mMaxVolume, this.mCurrentVolume) {
                public void onSetVolumeTo(int volume) {
                    VolumeProviderCompat.this.onSetVolumeTo(volume);
                }

                public void onAdjustVolume(int direction) {
                    VolumeProviderCompat.this.onAdjustVolume(direction);
                }
            };
        }
        return this.mVolumeProviderFwk;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.VolumeProviderCompat$ControlType */
    public @interface ControlType {
    }

    /* renamed from: android.support.v4.media.VolumeProviderCompat$Callback */
    public static abstract class Callback {
        public abstract void onVolumeChanged(VolumeProviderCompat volumeProviderCompat);
    }
}
