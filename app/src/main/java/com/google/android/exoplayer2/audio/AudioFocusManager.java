package com.google.android.exoplayer2.audio;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class AudioFocusManager {
    public static final int PLAYER_COMMAND_DO_NOT_PLAY = -1;
    public static final int PLAYER_COMMAND_PLAY_WHEN_READY = 1;
    public static final int PLAYER_COMMAND_WAIT_FOR_CALLBACK = 0;
    private static final int AUDIO_FOCUS_STATE_HAVE_FOCUS = 1;
    private static final int AUDIO_FOCUS_STATE_LOSS_TRANSIENT = 2;
    private static final int AUDIO_FOCUS_STATE_LOSS_TRANSIENT_DUCK = 3;
    private static final int AUDIO_FOCUS_STATE_LOST_FOCUS = -1;
    private static final int AUDIO_FOCUS_STATE_NO_FOCUS = 0;
    private static final String TAG = "AudioFocusManager";
    private static final float VOLUME_MULTIPLIER_DEFAULT = 1.0f;
    private static final float VOLUME_MULTIPLIER_DUCK = 0.2f;
    /* access modifiers changed from: private */
    public final PlayerControl playerControl;
    private final AudioManager audioManager;
    private final AudioFocusListener focusListener;
    /* access modifiers changed from: private */
    public int audioFocusState;
    /* access modifiers changed from: private */
    public float volumeMultiplier = VOLUME_MULTIPLIER_DEFAULT;
    @Nullable
    private AudioAttributes audioAttributes;
    private AudioFocusRequest audioFocusRequest;
    private int focusGain;
    private boolean rebuildAudioFocusRequest;

    public AudioFocusManager(Context context, PlayerControl playerControl2) {
        this.audioManager = (AudioManager) context.getApplicationContext().getSystemService("audio");
        this.playerControl = playerControl2;
        this.focusListener = new AudioFocusListener();
        this.audioFocusState = 0;
    }

    private static int convertAudioAttributesToFocusGain(@Nullable AudioAttributes audioAttributes2) {
        if (audioAttributes2 == null) {
            return 0;
        }
        switch (audioAttributes2.usage) {
            case 0:
                Log.m30w(TAG, "Specify a proper usage in the audio attributes for audio focus handling. Using AUDIOFOCUS_GAIN by default.");
                return 1;
            case 1:
            case 14:
                return 1;
            case 2:
            case 4:
                return 2;
            case 3:
                return 0;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
                return 3;
            case 11:
                return audioAttributes2.contentType == 1 ? 2 : 3;
            case 15:
            default:
                int i = audioAttributes2.usage;
                StringBuilder sb = new StringBuilder(37);
                sb.append("Unidentified audio usage: ");
                sb.append(i);
                Log.m30w(TAG, sb.toString());
                return 0;
            case 16:
                if (Util.SDK_INT >= 19) {
                    return 4;
                }
                return 2;
        }
    }

    public float getVolumeMultiplier() {
        return this.volumeMultiplier;
    }

    public int setAudioAttributes(@Nullable AudioAttributes audioAttributes2, boolean playWhenReady, int playerState) {
        if (!Util.areEqual(this.audioAttributes, audioAttributes2)) {
            this.audioAttributes = audioAttributes2;
            this.focusGain = convertAudioAttributesToFocusGain(audioAttributes2);
            int i = this.focusGain;
            Assertions.checkArgument(i == 1 || i == 0, "Automatic handling of audio focus is only available for USAGE_MEDIA and USAGE_GAME.");
            if (playWhenReady && (playerState == 2 || playerState == 3)) {
                return requestAudioFocus();
            }
        }
        if (playerState == 1) {
            return handleIdle(playWhenReady);
        }
        return handlePrepare(playWhenReady);
    }

    public int handlePrepare(boolean playWhenReady) {
        if (playWhenReady) {
            return requestAudioFocus();
        }
        return -1;
    }

    public int handleSetPlayWhenReady(boolean playWhenReady, int playerState) {
        if (playWhenReady) {
            return playerState == 1 ? handleIdle(playWhenReady) : requestAudioFocus();
        }
        abandonAudioFocus();
        return -1;
    }

    public void handleStop() {
        abandonAudioFocus(true);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public AudioManager.OnAudioFocusChangeListener getFocusListener() {
        return this.focusListener;
    }

    private int handleIdle(boolean playWhenReady) {
        return playWhenReady ? 1 : -1;
    }

    private int requestAudioFocus() {
        int focusRequestResult;
        int i;
        if (this.focusGain == 0) {
            if (this.audioFocusState != 0) {
                abandonAudioFocus(true);
            }
            return 1;
        }
        if (this.audioFocusState == 0) {
            if (Util.SDK_INT >= 26) {
                focusRequestResult = requestAudioFocusV26();
            } else {
                focusRequestResult = requestAudioFocusDefault();
            }
            if (focusRequestResult == 1) {
                i = 1;
            } else {
                i = 0;
            }
            this.audioFocusState = i;
        }
        int focusRequestResult2 = this.audioFocusState;
        if (focusRequestResult2 == 0) {
            return -1;
        }
        if (focusRequestResult2 == 2) {
            return 0;
        }
        return 1;
    }

    private void abandonAudioFocus() {
        abandonAudioFocus(false);
    }

    /* access modifiers changed from: private */
    public void abandonAudioFocus(boolean forceAbandon) {
        if (this.focusGain != 0 || this.audioFocusState != 0) {
            if (this.focusGain != 1 || this.audioFocusState == -1 || forceAbandon) {
                if (Util.SDK_INT >= 26) {
                    abandonAudioFocusV26();
                } else {
                    abandonAudioFocusDefault();
                }
                this.audioFocusState = 0;
            }
        }
    }

    private int requestAudioFocusDefault() {
        return this.audioManager.requestAudioFocus(this.focusListener, Util.getStreamTypeForAudioUsage(((AudioAttributes) Assertions.checkNotNull(this.audioAttributes)).usage), this.focusGain);
    }

    @RequiresApi(26)
    private int requestAudioFocusV26() {
        AudioFocusRequest.Builder builder;
        if (this.audioFocusRequest == null || this.rebuildAudioFocusRequest) {
            AudioFocusRequest audioFocusRequest2 = this.audioFocusRequest;
            if (audioFocusRequest2 == null) {
                builder = new AudioFocusRequest.Builder(this.focusGain);
            } else {
                builder = new AudioFocusRequest.Builder(audioFocusRequest2);
            }
            this.audioFocusRequest = builder.setAudioAttributes(((AudioAttributes) Assertions.checkNotNull(this.audioAttributes)).getAudioAttributesV21()).setWillPauseWhenDucked(willPauseWhenDucked()).setOnAudioFocusChangeListener(this.focusListener).build();
            this.rebuildAudioFocusRequest = false;
        }
        return this.audioManager.requestAudioFocus(this.audioFocusRequest);
    }

    private void abandonAudioFocusDefault() {
        this.audioManager.abandonAudioFocus(this.focusListener);
    }

    @RequiresApi(26)
    private void abandonAudioFocusV26() {
        AudioFocusRequest audioFocusRequest2 = this.audioFocusRequest;
        if (audioFocusRequest2 != null) {
            this.audioManager.abandonAudioFocusRequest(audioFocusRequest2);
        }
    }

    /* access modifiers changed from: private */
    public boolean willPauseWhenDucked() {
        AudioAttributes audioAttributes2 = this.audioAttributes;
        return audioAttributes2 != null && audioAttributes2.contentType == 1;
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface AudioFocusState {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerCommand {
    }

    public interface PlayerControl {
        void executePlayerCommand(int i);

        void setVolumeMultiplier(float f);
    }

    private class AudioFocusListener implements AudioManager.OnAudioFocusChangeListener {
        private AudioFocusListener() {
        }

        public void onAudioFocusChange(int focusChange) {
            float volumeMultiplier;
            if (focusChange != -3) {
                if (focusChange == -2) {
                    int unused = AudioFocusManager.this.audioFocusState = 2;
                } else if (focusChange == -1) {
                    int unused2 = AudioFocusManager.this.audioFocusState = -1;
                } else if (focusChange != 1) {
                    StringBuilder sb = new StringBuilder(38);
                    sb.append("Unknown focus change type: ");
                    sb.append(focusChange);
                    Log.m30w(AudioFocusManager.TAG, sb.toString());
                    return;
                } else {
                    int unused3 = AudioFocusManager.this.audioFocusState = 1;
                }
            } else if (AudioFocusManager.this.willPauseWhenDucked()) {
                int unused4 = AudioFocusManager.this.audioFocusState = 2;
            } else {
                int unused5 = AudioFocusManager.this.audioFocusState = 3;
            }
            int access$100 = AudioFocusManager.this.audioFocusState;
            if (access$100 == -1) {
                AudioFocusManager.this.playerControl.executePlayerCommand(-1);
                AudioFocusManager.this.abandonAudioFocus(true);
            } else if (access$100 != 0) {
                if (access$100 == 1) {
                    AudioFocusManager.this.playerControl.executePlayerCommand(1);
                } else if (access$100 == 2) {
                    AudioFocusManager.this.playerControl.executePlayerCommand(0);
                } else if (access$100 != 3) {
                    int access$1002 = AudioFocusManager.this.audioFocusState;
                    StringBuilder sb2 = new StringBuilder(38);
                    sb2.append("Unknown audio focus state: ");
                    sb2.append(access$1002);
                    throw new IllegalStateException(sb2.toString());
                }
            }
            if (AudioFocusManager.this.audioFocusState == 3) {
                volumeMultiplier = AudioFocusManager.VOLUME_MULTIPLIER_DUCK;
            } else {
                volumeMultiplier = AudioFocusManager.VOLUME_MULTIPLIER_DEFAULT;
            }
            if (AudioFocusManager.this.volumeMultiplier != volumeMultiplier) {
                float unused6 = AudioFocusManager.this.volumeMultiplier = volumeMultiplier;
                AudioFocusManager.this.playerControl.setVolumeMultiplier(volumeMultiplier);
            }
        }
    }
}
