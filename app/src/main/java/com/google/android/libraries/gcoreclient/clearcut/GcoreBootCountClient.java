package com.google.android.libraries.gcoreclient.clearcut;

import android.content.Context;

import com.google.android.libraries.gcoreclient.tasks.GcoreTask;

public interface GcoreBootCountClient {

    GcoreTask<Integer> getBootCount();

    public interface Factory {
        GcoreBootCountClient createBootCountClient(Context context);
    }
}
