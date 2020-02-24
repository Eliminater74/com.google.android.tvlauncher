package com.google.android.exoplayer2;

import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.source.MediaSource;

public interface ExoPlayer extends Player {

    @Deprecated
    public interface ExoPlayerComponent extends PlayerMessage.Target {
    }

    @Deprecated
    void blockingSendMessages(ExoPlayerMessage... exoPlayerMessageArr);

    PlayerMessage createMessage(PlayerMessage.Target target);

    Looper getPlaybackLooper();

    SeekParameters getSeekParameters();

    void prepare(MediaSource mediaSource);

    void prepare(MediaSource mediaSource, boolean z, boolean z2);

    void retry();

    @Deprecated
    void sendMessages(ExoPlayerMessage... exoPlayerMessageArr);

    void setForegroundMode(boolean z);

    void setSeekParameters(@Nullable SeekParameters seekParameters);

    @Deprecated
    public static final class ExoPlayerMessage {
        public final Object message;
        public final int messageType;
        public final PlayerMessage.Target target;

        @Deprecated
        public ExoPlayerMessage(PlayerMessage.Target target2, int messageType2, Object message2) {
            this.target = target2;
            this.messageType = messageType2;
            this.message = message2;
        }
    }
}
