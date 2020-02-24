package com.google.android.libraries.gcoreclient.clearcut;

import android.content.Context;
import com.google.android.libraries.gcoreclient.tasks.GcoreTask;

public interface GcoreBootCountClient {

    public interface Factory {
        GcoreBootCountClient createBootCountClient(Context context);
    }

    GcoreTask<Integer> getBootCount();
}
