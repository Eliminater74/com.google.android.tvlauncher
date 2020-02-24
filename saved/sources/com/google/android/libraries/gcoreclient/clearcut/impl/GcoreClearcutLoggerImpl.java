package com.google.android.libraries.gcoreclient.clearcut.impl;

import android.content.Context;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogEventBuilder;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutMessageProducer;
import javax.annotation.Nullable;

public final class GcoreClearcutLoggerImpl extends BaseClearcutLoggerImpl {
    public /* bridge */ /* synthetic */ GcoreClearcutLogEventBuilder newEvent(GcoreClearcutMessageProducer gcoreClearcutMessageProducer) {
        return super.newEvent(gcoreClearcutMessageProducer);
    }

    public GcoreClearcutLoggerImpl(Context context, String logSourceName, @Nullable String uploadAccountName) {
        super(context, logSourceName, uploadAccountName);
    }

    GcoreClearcutLoggerImpl(ClearcutLogger anonymousClearcutLogger) {
        super(anonymousClearcutLogger);
    }
}
