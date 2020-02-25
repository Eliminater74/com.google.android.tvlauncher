package com.google.android.libraries.gcoreclient.clearcut.impl;

import android.content.Context;

import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogger;

public final class GcoreClearcutLoggerFactoryImpl extends BaseClearcutLoggerFactoryImpl {
    public /* bridge */ /* synthetic */ GcoreClearcutLogger getGcoreClearcutLogger(Context context, String str, String str2) {
        return super.getGcoreClearcutLogger(context, str, str2);
    }

    public GcoreClearcutLogger getAnonymousGcoreClearcutLogger(Context context, String logSourceName) {
        return new GcoreClearcutLoggerImpl(ClearcutLogger.anonymousLogger(context, logSourceName));
    }
}
