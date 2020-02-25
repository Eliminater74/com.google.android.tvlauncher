package com.google.android.exoplayer2;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

public abstract /* synthetic */ class Player$EventListener$$CC {
    public static void onTimelineChanged$$dflt$$(Player.EventListener eventListener, @Nullable Timeline timeline, Object manifest, int reason) {
    }

    public static void onTracksChanged$$dflt$$(Player.EventListener eventListener, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    public static void onLoadingChanged$$dflt$$(Player.EventListener eventListener, boolean isLoading) {
    }

    public static void onPlayerStateChanged$$dflt$$(Player.EventListener eventListener, boolean playWhenReady, int playbackState) {
    }

    public static void onRepeatModeChanged$$dflt$$(Player.EventListener eventListener, int repeatMode) {
    }

    public static void onShuffleModeEnabledChanged$$dflt$$(Player.EventListener eventListener, boolean shuffleModeEnabled) {
    }

    public static void onPlayerError$$dflt$$(Player.EventListener eventListener, ExoPlaybackException error) {
    }

    public static void onPositionDiscontinuity$$dflt$$(Player.EventListener eventListener, int reason) {
    }

    public static void onPlaybackParametersChanged$$dflt$$(Player.EventListener eventListener, PlaybackParameters playbackParameters) {
    }

    public static void onSeekProcessed$$dflt$$(Player.EventListener eventListener) {
    }
}
