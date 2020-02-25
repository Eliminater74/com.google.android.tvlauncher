package com.google.android.libraries.gcoreclient.clearcut;

import android.content.Context;

import javax.annotation.Nullable;

public interface GcoreClearcutLoggerFactory {
    GcoreClearcutLogger getAnonymousGcoreClearcutLogger(Context context, String str);

    GcoreClearcutLogger getGcoreClearcutLogger(Context context, String str, @Nullable String str2);
}
