package android.support.p001v4.media;

import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.media.AudioManagerCompat */
public final class AudioManagerCompat {
    public static final int AUDIOFOCUS_GAIN = 1;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE = 4;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
    private static final String TAG = "AudioManCompat";

    public static int requestAudioFocus(@NonNull AudioManager audioManager, @NonNull AudioFocusRequestCompat focusRequest) {
        if (audioManager == null) {
            throw new IllegalArgumentException("AudioManager must not be null");
        } else if (focusRequest == null) {
            throw new IllegalArgumentException("AudioFocusRequestCompat must not be null");
        } else if (Build.VERSION.SDK_INT >= 26) {
            return audioManager.requestAudioFocus(focusRequest.getAudioFocusRequest());
        } else {
            return audioManager.requestAudioFocus(focusRequest.getOnAudioFocusChangeListener(), focusRequest.getAudioAttributesCompat().getLegacyStreamType(), focusRequest.getFocusGain());
        }
    }

    public static int abandonAudioFocusRequest(@NonNull AudioManager audioManager, @NonNull AudioFocusRequestCompat focusRequest) {
        if (audioManager == null) {
            throw new IllegalArgumentException("AudioManager must not be null");
        } else if (focusRequest == null) {
            throw new IllegalArgumentException("AudioFocusRequestCompat must not be null");
        } else if (Build.VERSION.SDK_INT >= 26) {
            return audioManager.abandonAudioFocusRequest(focusRequest.getAudioFocusRequest());
        } else {
            return audioManager.abandonAudioFocus(focusRequest.getOnAudioFocusChangeListener());
        }
    }

    private AudioManagerCompat() {
    }
}
