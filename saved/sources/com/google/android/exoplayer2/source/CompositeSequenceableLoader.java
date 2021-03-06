package com.google.android.exoplayer2.source;

public class CompositeSequenceableLoader implements SequenceableLoader {
    protected final SequenceableLoader[] loaders;

    public CompositeSequenceableLoader(SequenceableLoader[] loaders2) {
        this.loaders = loaders2;
    }

    public final long getBufferedPositionUs() {
        long bufferedPositionUs = Long.MAX_VALUE;
        for (SequenceableLoader loader : this.loaders) {
            long loaderBufferedPositionUs = loader.getBufferedPositionUs();
            if (loaderBufferedPositionUs != Long.MIN_VALUE) {
                bufferedPositionUs = Math.min(bufferedPositionUs, loaderBufferedPositionUs);
            }
        }
        if (bufferedPositionUs == Long.MAX_VALUE) {
            return Long.MIN_VALUE;
        }
        return bufferedPositionUs;
    }

    public final long getNextLoadPositionUs() {
        long nextLoadPositionUs = Long.MAX_VALUE;
        for (SequenceableLoader loader : this.loaders) {
            long loaderNextLoadPositionUs = loader.getNextLoadPositionUs();
            if (loaderNextLoadPositionUs != Long.MIN_VALUE) {
                nextLoadPositionUs = Math.min(nextLoadPositionUs, loaderNextLoadPositionUs);
            }
        }
        if (nextLoadPositionUs == Long.MAX_VALUE) {
            return Long.MIN_VALUE;
        }
        return nextLoadPositionUs;
    }

    public final void reevaluateBuffer(long positionUs) {
        for (SequenceableLoader loader : this.loaders) {
            loader.reevaluateBuffer(positionUs);
        }
    }

    public boolean continueLoading(long positionUs) {
        long j = positionUs;
        boolean madeProgress = false;
        while (true) {
            long nextLoadPositionUs = getNextLoadPositionUs();
            if (nextLoadPositionUs != Long.MIN_VALUE) {
                boolean madeProgressThisIteration = false;
                for (SequenceableLoader loader : this.loaders) {
                    long loaderNextLoadPositionUs = loader.getNextLoadPositionUs();
                    boolean isLoaderBehind = loaderNextLoadPositionUs != Long.MIN_VALUE && loaderNextLoadPositionUs <= j;
                    if (loaderNextLoadPositionUs == nextLoadPositionUs || isLoaderBehind) {
                        madeProgressThisIteration |= loader.continueLoading(j);
                    }
                }
                madeProgress |= madeProgressThisIteration;
                if (!madeProgressThisIteration) {
                    break;
                }
            } else {
                break;
            }
        }
        return madeProgress;
    }
}
