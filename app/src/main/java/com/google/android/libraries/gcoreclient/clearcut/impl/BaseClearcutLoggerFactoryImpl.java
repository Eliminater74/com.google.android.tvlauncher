package com.google.android.libraries.gcoreclient.clearcut.impl;

import android.content.Context;

import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogger;
import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLoggerFactory;

abstract class BaseClearcutLoggerFactoryImpl implements GcoreClearcutLoggerFactory {
    BaseClearcutLoggerFactoryImpl() {
    }

    public GcoreClearcutLogger getGcoreClearcutLogger(Context context, String logSourceName, String uploadAccountName) {
        return new GcoreClearcutLoggerImpl(context, logSourceName, uploadAccountName);
    }
}
