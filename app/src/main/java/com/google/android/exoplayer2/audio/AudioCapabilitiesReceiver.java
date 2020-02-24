package com.google.android.exoplayer2.audio;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class AudioCapabilitiesReceiver {
    @Nullable
    AudioCapabilities audioCapabilities;
    /* access modifiers changed from: private */
    public final Context context;
    @Nullable
    private final ExternalSurroundSoundSettingObserver externalSurroundSoundSettingObserver;
    private final Handler handler = new Handler(Util.getLooper());
    private final Listener listener;
    @Nullable
    private final BroadcastReceiver receiver;
    private boolean registered;

    public interface Listener {
        void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities);
    }

    public AudioCapabilitiesReceiver(Context context2, Listener listener2) {
        Context context3 = context2.getApplicationContext();
        this.context = context3;
        this.listener = (Listener) Assertions.checkNotNull(listener2);
        ExternalSurroundSoundSettingObserver externalSurroundSoundSettingObserver2 = null;
        this.receiver = Util.SDK_INT >= 21 ? new HdmiAudioPlugBroadcastReceiver() : null;
        Uri externalSurroundSoundUri = AudioCapabilities.getExternalSurroundSoundGlobalSettingUri();
        this.externalSurroundSoundSettingObserver = externalSurroundSoundUri != null ? new ExternalSurroundSoundSettingObserver(this.handler, context3.getContentResolver(), externalSurroundSoundUri) : externalSurroundSoundSettingObserver2;
    }

    public AudioCapabilities register() {
        if (this.registered) {
            return (AudioCapabilities) Assertions.checkNotNull(this.audioCapabilities);
        }
        this.registered = true;
        ExternalSurroundSoundSettingObserver externalSurroundSoundSettingObserver2 = this.externalSurroundSoundSettingObserver;
        if (externalSurroundSoundSettingObserver2 != null) {
            externalSurroundSoundSettingObserver2.register();
        }
        Intent stickyIntent = null;
        if (this.receiver != null) {
            stickyIntent = this.context.registerReceiver(this.receiver, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG"), null, this.handler);
        }
        this.audioCapabilities = AudioCapabilities.getCapabilities(this.context, stickyIntent);
        return this.audioCapabilities;
    }

    public void unregister() {
        if (this.registered) {
            this.audioCapabilities = null;
            BroadcastReceiver broadcastReceiver = this.receiver;
            if (broadcastReceiver != null) {
                this.context.unregisterReceiver(broadcastReceiver);
            }
            ExternalSurroundSoundSettingObserver externalSurroundSoundSettingObserver2 = this.externalSurroundSoundSettingObserver;
            if (externalSurroundSoundSettingObserver2 != null) {
                externalSurroundSoundSettingObserver2.unregister();
            }
            this.registered = false;
        }
    }

    /* access modifiers changed from: private */
    public void onNewAudioCapabilities(AudioCapabilities newAudioCapabilities) {
        if (this.registered && !newAudioCapabilities.equals(this.audioCapabilities)) {
            this.audioCapabilities = newAudioCapabilities;
            this.listener.onAudioCapabilitiesChanged(newAudioCapabilities);
        }
    }

    private final class HdmiAudioPlugBroadcastReceiver extends BroadcastReceiver {
        private HdmiAudioPlugBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!isInitialStickyBroadcast()) {
                AudioCapabilitiesReceiver.this.onNewAudioCapabilities(AudioCapabilities.getCapabilities(context, intent));
            }
        }
    }

    private final class ExternalSurroundSoundSettingObserver extends ContentObserver {
        private final ContentResolver resolver;
        private final Uri settingUri;

        public ExternalSurroundSoundSettingObserver(Handler handler, ContentResolver resolver2, Uri settingUri2) {
            super(handler);
            this.resolver = resolver2;
            this.settingUri = settingUri2;
        }

        public void register() {
            this.resolver.registerContentObserver(this.settingUri, false, this);
        }

        public void unregister() {
            this.resolver.unregisterContentObserver(this);
        }

        public void onChange(boolean selfChange) {
            AudioCapabilitiesReceiver audioCapabilitiesReceiver = AudioCapabilitiesReceiver.this;
            audioCapabilitiesReceiver.onNewAudioCapabilities(AudioCapabilities.getCapabilities(audioCapabilitiesReceiver.context));
        }
    }
}
