package com.google.android.libraries.gcoreclient.clearcut.impl;

import android.content.Context;
import com.google.android.gms.clearcut.BootCount;
import com.google.android.gms.clearcut.BootCountClient;
import com.google.android.libraries.gcoreclient.clearcut.GcoreBootCountClient;
import com.google.android.libraries.gcoreclient.tasks.GcoreTask;
import com.google.android.libraries.gcoreclient.tasks.impl.GcoreTaskImpl;

public final class GcoreBootCountClientImpl implements GcoreBootCountClient {
    private final BootCountClient bootCountClient;

    public GcoreBootCountClientImpl(BootCountClient bootCountClient2) {
        this.bootCountClient = bootCountClient2;
    }

    public GcoreTask<Integer> getBootCount() {
        return GcoreTaskImpl.wrap(this.bootCountClient.getBootCount());
    }

    public static final class FactoryImpl implements GcoreBootCountClient.Factory {
        public GcoreBootCountClient createBootCountClient(Context context) {
            return new GcoreBootCountClientImpl(BootCount.getInstance(context));
        }
    }
}
