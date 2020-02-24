package com.android.ahat.progress;

public class NullProgress implements Progress {
    public void start(String description, long duration) {
    }

    public void advance() {
    }

    public void advance(long n) {
    }

    public void update(long current) {
    }

    public void done() {
    }
}
