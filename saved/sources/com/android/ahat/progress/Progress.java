package com.android.ahat.progress;

public interface Progress {
    void advance();

    void advance(long j);

    void done();

    void start(String str, long j);

    void update(long j);
}
