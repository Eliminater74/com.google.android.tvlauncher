package com.google.android.libraries.gcoreclient.clearcut.impl;

import com.google.android.libraries.gcoreclient.clearcut.GcoreClearcutLogger;
import com.google.android.libraries.gcoreclient.clearcut.GcoreCounters;
import com.google.android.libraries.gcoreclient.clearcut.GcoreCountersFactory;
import com.google.android.libraries.gcoreclient.common.api.support.GcoreWrapper;

class GcoreCountersFactoryImpl implements GcoreCountersFactory {
    private final GcoreWrapper wrapper;

    GcoreCountersFactoryImpl(GcoreWrapper wrapper2) {
        this.wrapper = wrapper2;
    }

    public GcoreCounters createCounters(GcoreClearcutLogger logger, String logSourceName, int maxSamplesPerCounter) {
        return new GcoreCountersImpl(logger, logSourceName, maxSamplesPerCounter, this.wrapper);
    }
}
