package com.google.android.tvlauncher.trace;

public final class AppTrace {
    private static final String APP_TRACE_IMPL_CLASS_NAME = "com.google.android.tvlauncher.trace.TraceAdapterImpl";
    private static TraceAdapter sInstance;

    public static void beginSection(String section) {
    }

    public static void endSection() {
    }

    public static TraceTag beginAsyncSection(String section) {
        return null;
    }

    public static void endAsyncSection(TraceTag tag) {
    }

    protected interface TraceAdapter {
        TraceTag beginAsyncSection(String str);

        void beginSection(String str);

        void endAsyncSection(TraceTag traceTag);

        void endSection();
    }

    public interface TraceTag {
    }
}
