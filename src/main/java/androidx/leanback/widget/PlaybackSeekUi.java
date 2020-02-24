package androidx.leanback.widget;

public interface PlaybackSeekUi {
    void setPlaybackSeekUiClient(Client client);

    public static class Client {
        public boolean isSeekEnabled() {
            return false;
        }

        public void onSeekStarted() {
        }

        public PlaybackSeekDataProvider getPlaybackSeekDataProvider() {
            return null;
        }

        public void onSeekPositionChanged(long pos) {
        }

        public void onSeekFinished(boolean cancelled) {
        }
    }
}
